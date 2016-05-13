package android.dcsdealerperu.com.dealerperu.Fragment;


import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Activity.ActReporteBajas;
import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.ListCategoria;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseCreatePunt;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMisBajas;
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
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class FragmentBajasSupervisor extends BaseVolleyFragment implements DatePickerDialog.OnDateSetListener {

    private boolean fecha_idicador;
    private Date dia_inicial;
    private Date dia_final;
    private EditText edit_fecha_inicial;
    private EditText edit_fecha_final;
    private EditText edit_idpos;
    private Spinner spinner_vendedor;
    private Spinner spinner_solicitud;
    private Spinner spinner_estado;
    private Spinner spinner_circuito;
    private Spinner spinner_ruta;
    private SpotsDialog alertDialog;
    private int estado;
    private int solicitud;
    private int estado_vendedor;
    private int circuito;
    private int ruta;
    private ResponseCreatePunt responseCreatePunto;

    public FragmentBajasSupervisor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bajas_supervisor, container, false);
        spinner_vendedor = (Spinner) view.findViewById(R.id.spinner_vendedor);
        spinner_solicitud = (Spinner) view.findViewById(R.id.spinner_solicitud);
        spinner_estado = (Spinner) view.findViewById(R.id.spinner_estado);
        spinner_circuito = (Spinner) view.findViewById(R.id.spinner_circuito);
        spinner_ruta = (Spinner) view.findViewById(R.id.spinner_ruta);
        edit_fecha_inicial = (EditText) view.findViewById(R.id.edit_fecha_ini);
        edit_fecha_final = (EditText) view.findViewById(R.id.edit_fecha_fin);
        edit_idpos = (EditText) view.findViewById(R.id.edit_idpos);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        FloatingActionButton btn_buscar = (FloatingActionButton) view.findViewById(R.id.cargar_reporte_pedidos);
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValidNumber(edit_fecha_inicial.getText().toString())) {
                    Toast.makeText(getActivity(), "La fecha inicial es un campo requerido", Toast.LENGTH_LONG).show();
                } else if (isValidNumber(edit_fecha_final.getText().toString())) {
                    Toast.makeText(getActivity(), "La fecha final es un campo requerido", Toast.LENGTH_LONG).show();
                } else {
                    if (checkDate(dia_inicial, dia_final)) {
                        Toast.makeText(getActivity(), "La fecha no puede superar más de 5 días de rango", Toast.LENGTH_LONG).show();
                    } else {
                        ConsultarReporte();
                    }
                }
            }
        });


        edit_fecha_inicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        FragmentBajasSupervisor.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                //Inicial
                fecha_idicador = true;

                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        edit_fecha_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        FragmentBajasSupervisor.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                fecha_idicador = false;

                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


            }
        });

        // Cargar Spinner Vendedor.!
        loadVendedor();
        // Cargar Spinner Circuito, Ruta.!
        setupGrid();
        // Cargar Spinner Estado
        loadEstado();
        return view;
    }

    private void ConsultarReporte() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "reporte_bajas");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(final String response) {
                        mostrarReporte(response);

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

                params.put("fecha_ini", edit_fecha_inicial.getText().toString().trim());
                params.put("fecha_fin", edit_fecha_final.getText().toString().trim());
                params.put("idpos", edit_idpos.getText().toString().trim());

                params.put("circuito", String.valueOf(circuito));
                params.put("vendedor", String.valueOf(estado_vendedor));
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

                ResponseMisBajas misBajas = gson.fromJson(response,ResponseMisBajas.class);

                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), ActReporteBajas.class);
                bundle.putSerializable("value", misBajas);
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

    private void loadEstado() {

        final List<CategoriasEstandar> listaEstados = new ArrayList<>();
        listaEstados.add(new CategoriasEstandar(-1,"Seleccionar"));
        listaEstados.add(new CategoriasEstandar(0,"Pendiente"));
        listaEstados.add(new CategoriasEstandar(1,"Aceptado"));
        listaEstados.add(new CategoriasEstandar(2,"Rechazado"));

        ArrayAdapter<CategoriasEstandar> adapterEstado = new ArrayAdapter<>(getActivity(),R.layout.textview_spinner,listaEstados);
        spinner_estado.setAdapter(adapterEstado);
        spinner_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estado = listaEstados.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }

    private boolean isValidNumber(String number){return number == null || number.length() == 0;}


    private void loadVendedor() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cargar_vendedores_supervisor");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(final String response) {
                        CargarVendedores(response);
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

    private void CargarVendedores(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                final ListCategoria listCategoria = gson.fromJson(response, ListCategoria.class);
                ArrayAdapter<CategoriasEstandar> adapterEstado = new ArrayAdapter<>(getActivity(),R.layout.textview_spinner,listCategoria);
                spinner_vendedor.setAdapter(adapterEstado);
                spinner_vendedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        estado_vendedor = listCategoria.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }

                });

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

                responseCreatePunto = gson.fromJson(response, ResponseCreatePunt.class);
                loadTerritorio(responseCreatePunto);

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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        monthOfYear = (monthOfYear+1);

        if (fecha_idicador) {
            edit_fecha_inicial.setText(year+"/"+monthOfYear+"/"+dayOfMonth);
            dia_inicial = converFecha(year, monthOfYear, dayOfMonth);
        } else {
            edit_fecha_final.setText(year+"/"+monthOfYear+"/"+dayOfMonth);
            dia_final = converFecha(year, monthOfYear, dayOfMonth);
        }
    }

    public Date converFecha(int year, int mes, int dia){

        Calendar calendar = new GregorianCalendar(year, mes, dia);
        Date fecha = new Date(calendar.getTimeInMillis());

        return fecha;
    }

    public boolean checkDate(Date startDate, Date endDate) {

        long milisegundos = 24 * 60 * 60 * 1000;

        long sum = (endDate.getTime() - startDate.getTime()) / milisegundos;

        if (sum > 5) {
            return true;
        } else {
            return false;
        }
    }

}
