package android.dcsdealerperu.com.dealerperu.Fragment;


import android.dcsdealerperu.com.dealerperu.DataBase.DBHelper;
import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.ListCategoria;
import android.dcsdealerperu.com.dealerperu.Entry.ListPuntosSincronizar;
import android.dcsdealerperu.com.dealerperu.Entry.ListUpdateservice;
import android.dcsdealerperu.com.dealerperu.Entry.NoVisita;
import android.dcsdealerperu.com.dealerperu.Entry.RequestGuardarEditarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.SincronizarPedidos;
import android.dcsdealerperu.com.dealerperu.Entry.listSincronizarNoVenta;
import android.dcsdealerperu.com.dealerperu.Entry.listSincronizarPedidos;
import android.dcsdealerperu.com.dealerperu.R;
import android.dcsdealerperu.com.dealerperu.Services.ConnectionDetector;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHomeSuperPrin extends BaseVolleyFragment {

    private int  mPosition;
    private Spinner spinner_vendedor;
    private SpotsDialog alertDialog;
    private int vendedor;
    private DBHelper mydb;
    private ConnectionDetector connectionDetector;

    private FragmentHomeSuper fragmentHomeSuper;

    public FragmentHomeSuperPrin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_super_prin, container, false);
        spinner_vendedor = (Spinner) view.findViewById(R.id.spinner_vendedor);
        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);
        loadVendedor();
        FloatingActionButton btn_buscar = (FloatingActionButton) view.findViewById(R.id.buscar_vendedor);
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashBoardVendedor();
            }
        });

        connectionDetector = new ConnectionDetector(getActivity());

        mydb = new DBHelper(getActivity());

        setHasOptionsMenu(true);

        return view;
    }

    private void dashBoardVendedor() {

        if(vendedor != 0) {
            FragmentManager fManager = getFragmentManager();
            fragmentHomeSuper = new FragmentHomeSuper();
            Bundle args = new Bundle();
            args.putInt("id_vendedor", vendedor);
            fragmentHomeSuper.setArguments(args);
            fManager.beginTransaction().replace(R.id.contentPanel, fragmentHomeSuper).commit();
        } else {
            Toast.makeText(getActivity(),"Por favor selecciona un vendedor",Toast.LENGTH_LONG).show();
        }
    }

    private void loadVendedor() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cargar_vendedores_supervisor");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                       CargarVendedores(response);
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
                params.put("inicio", String.valueOf(1));

                return params;
            }
        };
        addToQueue(jsonRequest);
    }

    private void CargarVendedores(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                final ListCategoria listCategoria = gson.fromJson(response, ListCategoria.class);
                ArrayAdapter<CategoriasEstandar> adapterEstado = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, listCategoria);
                spinner_vendedor.setAdapter(adapterEstado);
                spinner_vendedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        vendedor = listCategoria.get(position).getId();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }

                });

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        } else {
            alertDialog.dismiss();
            Toast.makeText(getContext(), "No se encontraron datos para mostrar", Toast.LENGTH_SHORT).show();
        }
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
                    if (puntoList.size() > 0) {
                        setPuntoSincronizar(puntoList);
                    } else {
                        Toast.makeText(getActivity(), "No tiene Puntos para sincronizar!", Toast.LENGTH_LONG).show();
                        List<SincronizarPedidos> sincronizarPedidosList = mydb.sincronizarPedido();
                        if (sincronizarPedidosList.size() > 0) {
                            setPedidosSincroinzar();
                        } else {
                            Toast.makeText(getActivity(), "No tengo pedidos para sincronizar!", Toast.LENGTH_LONG).show();
                            List<NoVisita> noVisitaList = mydb.sincronizarNoVisita();
                            if (noVisitaList.size() > 0) {
                                setSincroinizarNoVisita();
                            } else {
                                Toast.makeText(getActivity(), "No tiene datos para sincronizar", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "No tiene acceso a internet para sincronizar", Toast.LENGTH_LONG).show();
                }

                return true;
            }

        });
    }

    private void setSincroinizarNoVisita() {

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_no_venta_offline");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSONNoVenta(response);
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

                List<NoVisita> noVisitaList = mydb.sincronizarNoVisita();

                String parJSON = new Gson().toJson(noVisitaList, listSincronizarNoVenta.class);

                params.put("datos", parJSON);

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));

                return params;

            }
        };

        addToQueue(jsonRequest);
    }

    private void parseJSONNoVenta(String response) {
        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                Charset.forName("UTF-8").encode(response);

                byte ptext[] = response.getBytes(Charset.forName("ISO-8859-1"));

                String value = new String(ptext, Charset.forName("UTF-8"));

                ListUpdateservice noVentaSincronizar = gson.fromJson(value, ListUpdateservice.class);

                for (int i = 0; i < noVentaSincronizar.size(); i++) {

                    if (noVentaSincronizar.get(i).getEstado().equals("-1")) {
                        Toast.makeText(getActivity(), noVentaSincronizar.get(i).getMsg(), Toast.LENGTH_LONG).show();
                    } else if (noVentaSincronizar.get(i).getEstado().equals("0")) {
                        if (mydb.deleteObject("no_visita"))
                            Toast.makeText(getActivity(), noVentaSincronizar.get(i).getMsg(), Toast.LENGTH_LONG).show();
                    }
                }


            } catch (IllegalStateException ex) {
                ex.printStackTrace();
            } finally {

            }
        } else {

        }
    }

    private void setPuntoSincronizar(final List<RequestGuardarEditarPunto> puntoList) {

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

                    } else if (sincronizar.get(i).getEstado().equals("0")) {
                        // Se guardo exitosamente
                        if (mydb.updatePuntoId(sincronizar.get(i).getId_movil(), String.valueOf(sincronizar.get(i).getId_db()))) {
                            //Se buscan los pedidos relacionados con el punto y se actualizan.

                            // update id cabezapedidos...
                            mydb.uptadeCabezaPedidoLocal(sincronizar.get(i).getIdreferecia(), String.valueOf(sincronizar.get(i).getId_db()));

                            Toast.makeText(getActivity(), "Puntos sincronizados", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getActivity(), "Problemas al actualizar el id del punto", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                List<SincronizarPedidos> sincronizarPedidosList = mydb.sincronizarPedido();
                if (sincronizarPedidosList.size() > 0) {
                    setPedidosSincroinzar();
                }

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
            } finally {

            }
        }
    }

    private void setPedidosSincroinzar() {

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_pedido_offline");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSONPedido(response);
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

                List<SincronizarPedidos> sincronizarPedidosList = mydb.sincronizarPedido();

                String parJSON = new Gson().toJson(sincronizarPedidosList, listSincronizarPedidos.class);

                params.put("datos", parJSON);
                params.put("bd", getResponseUserStatic().getBd());
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));

                return params;

            }
        };

        addToQueue(jsonRequest);
    }

    private void parseJSONPedido(String response) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {

            final ListUpdateservice sincronizar = gson.fromJson(response, ListUpdateservice.class);

            for (int i = 0; i < sincronizar.size(); i++) {
                if (sincronizar.get(i).getEstado().equals("-1")) {
                    Toast.makeText(getActivity(), sincronizar.get(i).getMsg(), Toast.LENGTH_LONG).show();
                } else if (sincronizar.get(i).getEstado().equals("0")) {
                    if (mydb.deleteObject("cabeza_pedido"))
                        mydb.deleteObject("detalle_pedido");
                    Toast.makeText(getActivity(), sincronizar.get(i).getMsg(), Toast.LENGTH_LONG).show();
                }
            }
        }

        //Llamar no visitas...
        List<NoVisita> noVisitaList = mydb.sincronizarNoVisita();
        if (noVisitaList.size() > 0) {
            setSincroinizarNoVisita();
        }

    }
}
