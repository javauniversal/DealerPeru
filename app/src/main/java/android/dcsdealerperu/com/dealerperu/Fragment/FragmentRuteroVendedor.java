package android.dcsdealerperu.com.dealerperu.Fragment;


import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Activity.ActReporteRutero;
import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.ListHome;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseCreatePunt;
import android.dcsdealerperu.com.dealerperu.Entry.Territorio;
import android.dcsdealerperu.com.dealerperu.Entry.Zona;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseHome.setResponseHomeListS;
import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class FragmentRuteroVendedor extends BaseVolleyFragment {


    private Spinner estado_visita;
    private Spinner tipo_frecuencia;
    private Spinner dia;
    private Spinner spinner_circuito;
    private Spinner spinner_ruta;
    private EditText idpos;
    private EditText cedula;

    private SpotsDialog alertDialog;

    private ResponseCreatePunt responseCreatePuntos;

    private int circuito, ruta, frecuencia, visita, sdia;

    public FragmentRuteroVendedor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rutero_vendedor, container, false);

        estado_visita = (Spinner) view.findViewById(R.id.spinner_estado_visita);
        tipo_frecuencia = (Spinner) view.findViewById(R.id.spinner_tipo_frecuencia);
        dia = (Spinner) view.findViewById(R.id.spinner_dia);

        spinner_circuito = (Spinner) view.findViewById(R.id.spinner_circuito);
        spinner_ruta = (Spinner) view.findViewById(R.id.spinner_ruta);

        idpos = (EditText) view.findViewById(R.id.edit_idpos);
        cedula = (EditText) view.findViewById(R.id.edit_cedula);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);
        LoadSpinners();
        // Accion del boton buscar
        FloatingActionButton btn_buscar = (FloatingActionButton) view.findViewById(R.id.cargar_reporte_rutero);
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsultarReporte();
            }
        });
        return view;
    }


    private void ConsultarReporte() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "consultar_rutero");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        mostrarReporte(response);
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

                params.put("idpos", idpos.getText().toString().trim());
                params.put("cedula", cedula.getText().toString().trim());
                params.put("circuito", String.valueOf(circuito));
                params.put("ruta", String.valueOf(ruta));
                params.put("estado_visita", String.valueOf(visita));
                params.put("tipo_frecuencia", String.valueOf(frecuencia));
                params.put("dia", String.valueOf(sdia));

                return params;

            }
        };
        addToQueue(jsonRequest);
    }

    private void mostrarReporte(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                ListHome responseHomeList = gson.fromJson(response, ListHome.class);

                setResponseHomeListS(responseHomeList);

                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), ActReporteRutero.class);
                bundle.putSerializable("value", responseHomeList);
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

    private void LoadSpinners() {
        // llena Spinner de Estados Visita
        final List<CategoriasEstandar> ListaEstadosVista = new ArrayList<>();
        ListaEstadosVista.add(new CategoriasEstandar(0, "Seleccionar"));
        ListaEstadosVista.add(new CategoriasEstandar(1, "Visitado"));
        ListaEstadosVista.add(new CategoriasEstandar(2, "Sin Visitar"));

        ArrayAdapter<CategoriasEstandar> adapterEstadoVisita = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, ListaEstadosVista);
        estado_visita.setAdapter(adapterEstadoVisita);
        estado_visita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                visita = ListaEstadosVista.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        // llena Spinner de Tipo Frecuencia
        final List<CategoriasEstandar> ListaTipoFrecuencia = new ArrayList<>();
        ListaTipoFrecuencia.add(new CategoriasEstandar(0, "Seleccionar"));
        ListaTipoFrecuencia.add(new CategoriasEstandar(1, "Semanal"));
        ListaTipoFrecuencia.add(new CategoriasEstandar(2, "Quincenal"));
        ListaTipoFrecuencia.add(new CategoriasEstandar(3, "Mensual"));

        ArrayAdapter<CategoriasEstandar> adapterTipoFrecuencia = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, ListaTipoFrecuencia);
        tipo_frecuencia.setAdapter(adapterTipoFrecuencia);
        tipo_frecuencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                frecuencia = ListaTipoFrecuencia.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        //llenar Spinner de Dia
        final List<CategoriasEstandar> ListaDia = new ArrayList<>();
        ListaDia.add(new CategoriasEstandar(0, "Seleccionar"));
        ListaDia.add(new CategoriasEstandar(1, "Lunes"));
        ListaDia.add(new CategoriasEstandar(2, "Martes"));
        ListaDia.add(new CategoriasEstandar(3, "Miercoles"));
        ListaDia.add(new CategoriasEstandar(4, "Jueves"));
        ListaDia.add(new CategoriasEstandar(5, "Viernes"));
        ListaDia.add(new CategoriasEstandar(6, "Sabado"));
        ListaDia.add(new CategoriasEstandar(7, "Domingo"));

        ArrayAdapter<CategoriasEstandar> adapterDia = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, ListaDia);
        dia.setAdapter(adapterDia);
        dia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sdia = ListaDia.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupGrid();
    }

    private void setupGrid() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cargar_filtros_puntos");
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

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void parseJSON(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                responseCreatePuntos = gson.fromJson(response, ResponseCreatePunt.class);

                loadTerritorio(responseCreatePuntos);

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
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void loadZona(final List<Zona> zonas) {
        ArrayAdapter<Zona> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, zonas);
        spinner_ruta.setAdapter(prec3);
        ;
        spinner_ruta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ruta = zonas.get(position).getId();
                //loadZona(territorioRes.getTerritorioList().get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }

}
