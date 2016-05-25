package android.dcsdealerperu.com.dealerperu.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Activity.ActMainPeru;
import android.dcsdealerperu.com.dealerperu.Activity.ActMarcarVisita;
import android.dcsdealerperu.com.dealerperu.Activity.ActTomarPedido;
import android.dcsdealerperu.com.dealerperu.Adapter.AppAdapterRutero;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseHome;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseRutero;
import android.dcsdealerperu.com.dealerperu.R;
import android.dcsdealerperu.com.dealerperu.Services.ConnectionDetector;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

@SuppressLint("ValidFragment")
public class FavoriteTabFragment extends BaseVolleyFragment {

    private int mPosition;
    private AppAdapterRutero appAdapterRutero;
    private ListView mListView;
    private SpotsDialog alertDialog;
    private int vendedor;
    private ConnectionDetector connectionDetector;

    public FavoriteTabFragment(int position,int vende) {
        mPosition = position;
        vendedor = vende;
    }

    public FavoriteTabFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_rutero_menu, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listView);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        connectionDetector = new ConnectionDetector(getActivity());

        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Implementing ActionBar Search inside a fragment
        MenuItem item = menu.add("Search");
        item.setIcon(R.drawable.abc_ic_search_api_mtrl_alpha); // sets icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView sv = new SearchView(((ActTomarPedido) getActivity()).getSupportActionBar().getThemedContext());
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv.findViewById(id);
        textView.setHint("Buscar...");
        textView.setHintTextColor(getResources().getColor(R.color.color_gris));
        textView.setTextColor(getResources().getColor(R.color.actionBarColorText));

        // implementing the listener
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //doSearch(s);
                return s.length() < 4;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });

        item.setActionView(sv);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (connectionDetector.isConnected()) {
            setupGrid();
        }

    }

    private void llenarData(final List<ResponseHome> listHome) {

        appAdapterRutero = new AppAdapterRutero(getActivity(), listHome, "rutero");
        mListView.setAdapter(appAdapterRutero);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_detalle, null);

                TextView txt_id_numero = (TextView) dialoglayout.findViewById(R.id.txt_id_numero);
                txt_id_numero.setText(String.format("%1$s", listHome.get(position).getIdpos()));

                TextView txt_nombre = (TextView) dialoglayout.findViewById(R.id.nombre_punto);
                txt_nombre.setText(String.format("%1$s", listHome.get(position).getRazon()));

                TextView txt_visitado = (TextView) dialoglayout.findViewById(R.id.txt_visitado);
                String visita;
                if (listHome.get(position).getTipo_visita() == 0)
                    visita = "No";
                else
                    visita = "Si";

                txt_visitado.setText(String.format("%1$s", visita));

                TextView txt_direccion = (TextView) dialoglayout.findViewById(R.id.txt_direccion);
                txt_direccion.setText(String.format("%1$s", listHome.get(position).getDireccion()));

                TextView txt_distrito = (TextView) dialoglayout.findViewById(R.id.txt_distrito);
                txt_distrito.setText(String.format("%1$s", listHome.get(position).getDistrito()));

                TextView txt_telefono = (TextView) dialoglayout.findViewById(R.id.txt_telefono);
                txt_telefono.setText(String.format("%1$s", listHome.get(position).getTel()));

                TextView txt_dias = (TextView) dialoglayout.findViewById(R.id.txt_dias);
                txt_dias.setText(String.format("%1$s", listHome.get(position).getDetalle()));

                String fecha_hora = listHome.get(position).getFecha_ult()+"  "+listHome.get(position).getHora_ult();
                if(fecha_hora.trim().isEmpty())
                    fecha_hora = "N/A";

                TextView txt_hora_visita = (TextView) dialoglayout.findViewById(R.id.txt_hora_visita);
                txt_hora_visita.setText(String.format("%1$s",fecha_hora));

                TextView txt_stock_c = (TextView) dialoglayout.findViewById(R.id.txt_stock_c);
                txt_stock_c.setText(String.format("%1$s", listHome.get(position).getStock_combo()));

                TextView txt_stock_s = (TextView) dialoglayout.findViewById(R.id.txt_stock_s);
                txt_stock_s.setText(String.format("%1$s", listHome.get(position).getStock_sim()));

                TextView txt_dias_s = (TextView) dialoglayout.findViewById(R.id.txt_dias_s);
                txt_dias_s.setText(String.format("%1$s", listHome.get(position).getDias_inve_sim()));

                TextView txt_dias_c = (TextView) dialoglayout.findViewById(R.id.txt_dias_c);
                txt_dias_c.setText(String.format("%1$s", listHome.get(position).getDias_inve_combo()));


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Detalle");
                builder.setView(dialoglayout).setPositiveButton("Visitar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        buscarIdPos(listHome.get(position).getIdpos());
                    }

                }).setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editarPunto(listHome.get(position).getIdpos());
                    }
                }).setNeutralButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();

            }
        });
    }

    private void buscarIdPos(final int idPos) {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "buscar_punto_visita");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSONVisita(response);
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
                params.put("idpos", String.valueOf(idPos));

                return params;

            }
        };

        addToQueue(jsonRequest);
    }

    private void parseJSONVisita(String response) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                ResponseMarcarPedido responseMarcarPedido = gson.fromJson(response, ResponseMarcarPedido.class);

                if (responseMarcarPedido.getEstado() == -1) {
                    //No tiene permisos del punto
                    Toast.makeText(getActivity(), responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == -2) {
                    //El punto no existe
                    Toast.makeText(getActivity(), responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else {
                    //Activity Detalle
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(getActivity(), ActMarcarVisita.class);
                    bundle.putSerializable("value", responseMarcarPedido);
                    bundle.putString("page", "marcar_rutero");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

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

    private void setupGrid() {

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "rutero");
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

                if(vendedor == 0) {
                    params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                } else {
                    params.put("iduser", String.valueOf(vendedor));
                }

                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void parseJSON(String response) {

        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                ResponseRutero listHome = gson.fromJson(response, ResponseRutero.class);

                llenarData(listHome.getResponseHomeList());

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
            } finally {

            }
        } else {
            Toast.makeText(getActivity(), "No se encontraron datos para mostrar", Toast.LENGTH_LONG).show();
        }

    }

    private void editarPunto(int idpos){
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), ActMainPeru.class);
        bundle.putInt("edit_punto", idpos);
        bundle.putInt("accion", 1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}