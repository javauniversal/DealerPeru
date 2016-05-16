package android.dcsdealerperu.com.dealerperu.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Activity.ActMarcarVisita;
import android.dcsdealerperu.com.dealerperu.Adapter.AppAdapterRutero;
import android.dcsdealerperu.com.dealerperu.Entry.ListHome;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

@SuppressLint("ValidFragment")
public class FavoriteTabFragment extends BaseVolleyFragment {

    private int mPosition;
    private AppAdapterRutero appAdapterRutero;
    private ListView mListView;
    private SpotsDialog alertDialog;

    public FavoriteTabFragment(int position) {
        mPosition = position;
    }

    public FavoriteTabFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_rutero_menu, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listView);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupGrid();
    }

    private void llenarData(final ListHome listHome) {

        appAdapterRutero = new AppAdapterRutero(getActivity(), listHome);
        mListView.setAdapter(appAdapterRutero);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_detalle, null);

                TextView txt_id_numero = (TextView) dialoglayout.findViewById(R.id.txt_id_numero);
                txt_id_numero.setText(String.format("%1$s", listHome.get(position).getIdpos()));

                TextView txt_visitado = (TextView) dialoglayout.findViewById(R.id.txt_visitado);
                String visita;
                if (listHome.get(position).getTipo_visita() == 0)
                    visita = "No";
                else
                    visita = "Si";

                txt_visitado.setText(String.format("%1$s", visita));

                TextView txt_direccion = (TextView) dialoglayout.findViewById(R.id.txt_direccion);
                txt_direccion.setText(String.format("%1$s", listHome.get(position).getDireccion()));

                TextView txt_departamento = (TextView) dialoglayout.findViewById(R.id.txt_departamento);
                txt_departamento.setText(String.format("%1$s", listHome.get(position).getDepartamento()));

                TextView txt_ciudad = (TextView) dialoglayout.findViewById(R.id.txt_ciudad);
                txt_ciudad.setText(String.format("%1$s", listHome.get(position).getDepartamento()));

                TextView txt_circuito = (TextView) dialoglayout.findViewById(R.id.txt_circuito);
                txt_circuito.setText(String.format("%1$s", listHome.get(position).getCircuito()));

                TextView txt_ruta = (TextView) dialoglayout.findViewById(R.id.txt_ruta);
                txt_ruta.setText(String.format("%1$s", listHome.get(position).getRuta()));

                TextView txt_telefono = (TextView) dialoglayout.findViewById(R.id.txt_telefono);
                txt_telefono.setText(String.format("%1$s", listHome.get(position).getTel()));

                TextView txt_dias = (TextView) dialoglayout.findViewById(R.id.txt_dias);
                txt_dias.setText(String.format("%1$s", listHome.get(position).getDetalle()));

                TextView txt_hora_visita = (TextView) dialoglayout.findViewById(R.id.txt_hora_visita);
                txt_hora_visita.setText(String.format("%1$s", listHome.get(position).getTipo_visita()));

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Detalle");
                builder.setView(dialoglayout).setPositiveButton("Visitar Punto", new DialogInterface.OnClickListener() {
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

    private void buscarIdPos(final int idPos) {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base),"buscar_punto_visita");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(final String response) {
                        parseJSONVisita(response);
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
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(final String response) {
                        parseJSON(response);
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

    private void parseJSON(String response) {

          Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                ListHome listHome = gson.fromJson(response, ListHome.class);
                llenarData(listHome);

            }  catch (IllegalStateException ex) {
                ex.printStackTrace();
            } finally {
            }
        } else {
        }

    }

}