package android.dcsdealerperu.com.dealerperu.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Activity.ActCarritoPedido;
import android.dcsdealerperu.com.dealerperu.Activity.ActTomarPedido;
import android.dcsdealerperu.com.dealerperu.Activity.SpacesItemDecoration;
import android.dcsdealerperu.com.dealerperu.Adapter.AdapterRecyclerCombos;
import android.dcsdealerperu.com.dealerperu.Entry.ReferenciasCombos;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseVenta;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

@SuppressLint("ValidFragment")
public class FragmentCombos extends BaseVolleyFragment {

    private int mPosition;
    private RecyclerView.Adapter adapter2;
    private RecyclerView recycler2;
    private SpotsDialog alertDialog;
    private GridLayoutManager gridLayoutManagerVertical;
    private ResponseVenta responseVenta;
    private List<ReferenciasCombos> filterList;


    public FragmentCombos(int position) {
        mPosition = position;
    }

    public FragmentCombos() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_combos, container, false);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        recycler2 = (RecyclerView) rootView.findViewById(R.id.recycler_view2);

        gridLayoutManagerVertical = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        getCombo();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        adapter2 = new AdapterRecyclerCombos(getActivity(), responseVenta.getReferenciasCombosList());
        recycler2.setAdapter(adapter2);

        adapter2.notifyDataSetChanged();

        MenuItem item2 = menu.add("Carrito");
        item2.setIcon(R.drawable.ic_shopping_cart_white_24dp); // sets icon
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), ActCarritoPedido.class);
                bundle.putInt("id_punto", mPosition);
                bundle.putInt("id_usuario", getResponseUserStatic().getId());
                intent.putExtras(bundle);
                startActivity(intent);

                return true;
            }

        });

        // Implementing ActionBar Search inside a fragment
        MenuItem item = menu.add("Search");
        item.setIcon(R.drawable.abc_ic_search_api_mtrl_alpha); // sets icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView sv = new SearchView(((ActTomarPedido) getActivity()).getSupportActionBar().getThemedContext());
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv.findViewById(id);

        // implementing the listener
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //doSearch(s);
                return s.length() < 4;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText = newText.toLowerCase();

                filterList = getNewListFromFilter(newText);

                //gridLayoutManagerVertical = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);

                adapter2 = new AdapterRecyclerCombos(getActivity(), filterList);
                recycler2.setAdapter(adapter2);

                //recycler2.setLayoutManager(gridLayoutManagerVertical);
                //recycler2.addItemDecoration(new SpacesItemDecoration(20));


                return true;
            }
        });

        item.setActionView(sv);
    }

    private List<ReferenciasCombos> getNewListFromFilter(CharSequence query) {

        query = query.toString().toLowerCase();

        List<ReferenciasCombos> filteredListCategoria = new ArrayList<>();

        for (int i = 0; i < responseVenta.getReferenciasCombosList().size(); i++) {
            if (responseVenta.getReferenciasCombosList().get(i).getDescripcion().toLowerCase().contains(query)) {
                filteredListCategoria.add(responseVenta.getReferenciasCombosList().get(i));
            }
        }

        return filteredListCategoria;
    }

    private void getCombo() {

        alertDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cargar_toma_pedido_com");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSONCombo(response);
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

                        alertDialog.dismiss();

                        onConnectionFailed(error_string);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));
                params.put("idpos", String.valueOf(mPosition));

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void parseJSONCombo(String response) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                responseVenta = gson.fromJson(response, ResponseVenta.class);

                adapter2 = new AdapterRecyclerCombos(getActivity(), responseVenta.getReferenciasCombosList());
                recycler2.setAdapter(adapter2);
                recycler2.setLayoutManager(gridLayoutManagerVertical);
                recycler2.addItemDecoration(new SpacesItemDecoration(20));

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        } else {
            alertDialog.dismiss();
        }

    }


}