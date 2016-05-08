package android.dcsdealerperu.com.dealerperu.Fragment;


import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Activity.ActReporteMisPedidos;
import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.MisPedidos;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseCreatePunt;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMisPedidos;
import android.dcsdealerperu.com.dealerperu.Entry.Territorio;
import android.dcsdealerperu.com.dealerperu.Entry.Zona;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMisPedidos extends BaseVolleyFragment {

    private EditText fecha_inicial;
    private EditText fecha_final;
    private Spinner spinner_circuito;
    private Spinner spinner_ruta;
    private Spinner spinner_estado;
    private EditText numero_pedido;
    private EditText idpos;

    private SpotsDialog alertDialog;

    private ResponseMisPedidos responseMisPedidos;
    private ResponseCreatePunt responseCretePunt;

    private int circuito,ruta,estado;

    public FragmentMisPedidos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_pedidos, container, false);

        spinner_circuito = (Spinner) view.findViewById(R.id.spinner_circuito);
        spinner_ruta = (Spinner) view.findViewById(R.id.spinner_ruta);
        spinner_estado = (Spinner) view.findViewById(R.id.spinner_estado);

        fecha_inicial = (EditText) view.findViewById(R.id.edit_fecha_ini);
        fecha_final = (EditText) view.findViewById(R.id.edit_fecha_fin);
        idpos = (EditText) view.findViewById(R.id.edit_idpos);
        numero_pedido = (EditText) view.findViewById(R.id.edit_nro_pedido);
        idpos = (EditText) view.findViewById(R.id.edit_idpos);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        // Accion del boton buscar
        FloatingActionButton btn_buscar = (FloatingActionButton) view.findViewById(R.id.cargar_reporte_pedidos);
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsultarReporte();
            }
        });
        return view;
    }

    private void ConsultarReporte()
    {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "consultar_reporte_pedidos");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(final String response) {
                        mostrarReporte(response);
                        alertDialog.dismiss();
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

                params.put("fecha_ini", fecha_inicial.getText().toString().trim());
                params.put("fecha_fin", fecha_final.getText().toString().trim());
                params.put("idpos", idpos.getText().toString().trim());

                params.put("nro_ped", numero_pedido.getText().toString().trim());
                params.put("circuito", String.valueOf(circuito));
                params.put("ruta", String.valueOf(ruta));
                params.put("estado", String.valueOf(estado));
                return params;

            }
        };
        addToQueue(jsonRequest);
    }

    private void mostrarReporte(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                MisPedidos responseMisPedidos = gson.fromJson(response, MisPedidos.class);


                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), ActReporteMisPedidos.class);
                bundle.putSerializable("value", responseMisPedidos);
                intent.putExtras(bundle);
                startActivity(intent);


            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        }else{
            alertDialog.dismiss();
            Toast.makeText(getContext(),"No se encontraron datos para mostrar",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //llenar Spinner de Estados
        final List<CategoriasEstandar> ListaEstados = new ArrayList<>();
        ListaEstados.add(new CategoriasEstandar(-1,"Seleccionar"));
        ListaEstados.add(new CategoriasEstandar(0,"Pendiente"));
        ListaEstados.add(new CategoriasEstandar(1,"Aceptado"));
        ListaEstados.add(new CategoriasEstandar(2,"Picking"));
        ListaEstados.add(new CategoriasEstandar(3,"Cancelado"));
        ListaEstados.add(new CategoriasEstandar(4,"Despachado"));
        ListaEstados.add(new CategoriasEstandar(5,"Entregado"));
        ListaEstados.add(new CategoriasEstandar(6,"Rechazado por punto"));

        ArrayAdapter<CategoriasEstandar> adapterEstados = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner,ListaEstados);
        spinner_estado.setAdapter(adapterEstados);
        spinner_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estado = ListaEstados.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
        setupGrid();
    }

    private void setupGrid() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cargar_filtros_puntos");
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

    private void parseJSON(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                responseCretePunt = gson.fromJson(response, ResponseCreatePunt.class);
                loadTerritorio(responseCretePunt);

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        }else{
            alertDialog.dismiss();
            Toast.makeText(getContext(),"No se encontraron datos para mostrar",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadTerritorio(final ResponseCreatePunt territorioRes) {

        ArrayAdapter<Territorio> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, territorioRes.getTerritorioList());
        spinner_circuito.setAdapter(prec3);
        spinner_circuito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                circuito = territorioRes.getTerritorioList().get(position).getId();
                loadZona(territorioRes.getTerritorioList().get(position).getZonaList());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadZona(final List<Zona> zonas) {
        ArrayAdapter<Zona> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, zonas);
        spinner_ruta.setAdapter(prec3);;
        spinner_ruta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ruta = zonas.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }
}
