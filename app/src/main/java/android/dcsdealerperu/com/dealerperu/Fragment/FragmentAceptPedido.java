package android.dcsdealerperu.com.dealerperu.Fragment;

import android.dcsdealerperu.com.dealerperu.Adapter.AdapterAceptPedido;
import android.dcsdealerperu.com.dealerperu.Entry.AceptarComprobante;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.dcsdealerperu.com.dealerperu.R;
import android.widget.ListView;
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
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;


public class FragmentAceptPedido extends BaseVolleyFragment {

    private SpotsDialog alertDialog;
    private ListView listView;
    private AdapterAceptPedido adapterAceptPedido;
    private AceptarComprobante aceptarComprobante;

    public FragmentAceptPedido() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_acept_pedido, container, false);

        listView = (ListView) view.findViewById(R.id.listView);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item2 = menu.add("Guardar");
        item2.setIcon(R.drawable.ic_save_white_24dp); // sets icon
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //Guardar Aceptacion de pedidos validaciones
                for (int i = 0; i < aceptarComprobante.getAceptarPedidoList().size(); i++ ) {
                    if (aceptarComprobante.getAceptarPedidoList().get(i).marcaProducto.equals("")) {
                        Toast.makeText(getActivity(), "El pedido numero "+aceptarComprobante.getAceptarPedidoList().get(i).getNroPedido(), Toast.LENGTH_LONG ).show();
                        break;
                    }
                }

                return true;
            }

        });

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        getAceptarPedido();
    }

    private void getAceptarPedido() {

        alertDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "datos_aceptar");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(final String response) {
                        parseJSONPedido(response);
                    }
                },
                new Response.ErrorListener(){
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
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void parseJSONPedido(String response) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                aceptarComprobante = gson.fromJson(response, AceptarComprobante.class);

                adapterAceptPedido = new AdapterAceptPedido(getActivity(), aceptarComprobante.getAceptarPedidoList());
                listView.setAdapter(adapterAceptPedido);

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
