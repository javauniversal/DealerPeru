package android.dcsdealerperu.com.dealerperu.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Activity.ActEntregarPedido;
import android.dcsdealerperu.com.dealerperu.Adapter.AppAdapterRutero;
import android.dcsdealerperu.com.dealerperu.Entry.ListEntregarPedido;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseHome;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseRutero;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRuteroRepartidor extends BaseVolleyFragment {

    private int mPosition;
    private ListView mListView;
    private SpotsDialog alertDialog;
    private AppAdapterRutero appAdapterRutero;

    public FragmentRuteroRepartidor(int position) {
        mPosition = position;
    }
    public FragmentRuteroRepartidor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rutero_repartidor, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);
        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        consultarRutero();
        return view;
    }

    private void consultarRutero()
    {
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "rutero_repartidor");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        cargarRutero(response);
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

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));

                return params;

            }
        };

        addToQueue(jsonRequest);
    }

    private void cargarRutero(String response) {

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

    private void llenarData(final List<ResponseHome> listHome) {
        appAdapterRutero = new AppAdapterRutero(getActivity(), listHome);
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
                builder.setView(dialoglayout).setPositiveButton("Entregar Pedido", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        buscarIdPos(listHome.get(position).getIdpos());
                    }

                }).setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();

            }
        });
    }
    private void buscarIdPos(final int idpos) {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "datos_entrega");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSONEntrega(response);
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

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));
                params.put("idpos", String.valueOf(idpos));

                return params;
            }
        };

        addToQueue(jsonRequest);

    }

    private void parseJSONEntrega(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                ListEntregarPedido responseEntregarPedido = gson.fromJson(response, ListEntregarPedido.class);

                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), ActEntregarPedido.class);
                bundle.putSerializable("value", responseEntregarPedido);
                intent.putExtras(bundle);
                startActivity(intent);

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

}
