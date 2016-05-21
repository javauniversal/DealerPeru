package android.dcsdealerperu.com.dealerperu.Fragment;

import android.dcsdealerperu.com.dealerperu.Adapter.TabsAdapter;
import android.dcsdealerperu.com.dealerperu.DataBase.DBHelper;
import android.dcsdealerperu.com.dealerperu.Entry.ListPuntosSincronizar;
import android.dcsdealerperu.com.dealerperu.Entry.ListUpdateservice;
import android.dcsdealerperu.com.dealerperu.Entry.RequestGuardarEditarPunto;
import android.dcsdealerperu.com.dealerperu.R;
import android.dcsdealerperu.com.dealerperu.Services.ConnectionDetector;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class FragmentHome extends BaseVolleyFragment {

    private DBHelper mydb;
    private ConnectionDetector connectionDetector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);

        TabsAdapter tabsAdapter = new TabsAdapter(getChildFragmentManager());
        tabsAdapter.addFragment(new FavoriteTabFragment2(1,0), "Dashboard");
        tabsAdapter.addFragment(new FavoriteTabFragment(2,0), "Rutero");

        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);

        setHasOptionsMenu(true);

        connectionDetector = new ConnectionDetector(getActivity());

        mydb = new DBHelper(getActivity());

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item2 = menu.add("Carrito");
        item2.setIcon(R.drawable.ic_cloud_upload_white_24dp); // sets icon
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Sincronizar...
                if (connectionDetector.isConnected()) {
                    List<RequestGuardarEditarPunto> puntoList = mydb.getPuntosSincronizar("Sincronizar");
                    if (puntoList.size() > 0)
                        setupGrid(puntoList);
                    else
                        Toast.makeText(getActivity(), "No tiene datos para sincronizar!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "No tiene acceso a internet para sincronizar", Toast.LENGTH_LONG).show();
                }

                return true;
            }

        });

    }

    private void setupGrid(final List<RequestGuardarEditarPunto> puntoList) {

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_punto");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        String error_string = "";

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            error_string = "Error de tiempo de espera";
                        } else if (error instanceof AuthFailureError) {
                            error_string = "Error Servidor";
                        } else if (error instanceof ServerError) {
                            error_string = "Server Error";
                        } else if (error instanceof NetworkError) {
                            error_string = "Error de red";
                        } else if (error instanceof ParseError) {
                            error_string = "Error al serializar los datos";
                        }

                        onConnectionFailed(error_string);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                String parJSON = new Gson().toJson(puntoList, ListPuntosSincronizar.class);

                params.put("datos", parJSON);

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));

                params.put("accion", "Sincronizar");

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void parseJSON(String response) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                final ListUpdateservice sincronizar = gson.fromJson(response, ListUpdateservice.class);

                for (int i = 0; i < sincronizar.size(); i++) {

                    if (sincronizar.get(i).getEstado().equals("-1")) {
                        // Error
                        if (mydb.deletePuntoError(sincronizar.get(i).getId_movil())) {
                            Toast.makeText(getActivity(), sincronizar.get(i).getMsg(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "No se pudo eliminar el punto con error", Toast.LENGTH_LONG).show();
                        }

                    } else if (sincronizar.get(i).getEstado().equals("0")){
                        // Se guardo exitosamente
                        if (mydb.updatePuntoId(sincronizar.get(i).getId_movil(), String.valueOf(sincronizar.get(i).getId_db()))) {
                            //Se buscan los pedidos relacionados con el punto y se actualizan.

                        } else {
                            Toast.makeText(getActivity(), "Problemas al actualizar el id del punto", Toast.LENGTH_LONG).show();
                        }
                    }
                }

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
            } finally {

            }
        }
    }

}
