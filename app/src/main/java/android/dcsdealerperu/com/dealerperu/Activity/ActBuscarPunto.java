package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Entry.BuscarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.Ciudad;
import android.dcsdealerperu.com.dealerperu.Entry.Departamentos;
import android.dcsdealerperu.com.dealerperu.Entry.Distrito;
import android.dcsdealerperu.com.dealerperu.Entry.ListHome;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseCreatePunt;
import android.dcsdealerperu.com.dealerperu.Entry.Territorio;
import android.dcsdealerperu.com.dealerperu.Entry.Zona;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.dcsdealerperu.com.dealerperu.R;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseHome.setResponseHomeListS;
import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class ActBuscarPunto extends AppCompatActivity {

    private SpotsDialog alertDialog;
    private RequestQueue rq;
    public static final String TAG = "MyTag";
    private Spinner spinner_depto;
    private Spinner spinner_provincia;
    private Spinner spinner_distrito;
    private Spinner spinner_circuito;
    private Spinner spinner_ruta;
    private Spinner spinner_est_comercial;
    private EditText edit_idpos;
    private EditText edit_cedula;
    private EditText edit_nombre;
    private int departamento;
    private int ciudad_pro;
    private int distrito;
    private int estado_circuito;
    private int estado_ruta;
    private int estado_comercial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_punto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alertDialog = new SpotsDialog(this, R.style.Custom);

        setupGrid();

        spinner_depto = (Spinner) findViewById(R.id.spinner_depto);
        spinner_provincia = (Spinner) findViewById(R.id.spinner_provincia);
        spinner_distrito = (Spinner) findViewById(R.id.spinner_distrito);
        spinner_circuito = (Spinner) findViewById(R.id.spinner_circuito);
        spinner_ruta = (Spinner) findViewById(R.id.spinner_ruta);
        spinner_est_comercial = (Spinner) findViewById(R.id.spinner_est_comercial);
        edit_idpos = (EditText) findViewById(R.id.edit_idpos);
        edit_cedula = (EditText) findViewById(R.id.edit_cedula);
        edit_nombre = (EditText) findViewById(R.id.edit_nombre);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarPunto();
            }
        });
    }

    private void buscarPunto() {
        buscarPuntoJSO();
    }

    private void buscarPuntoJSO() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base),"consultar_puntos");
        rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSONBuscar(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActBuscarPunto.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActBuscarPunto.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActBuscarPunto.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActBuscarPunto.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActBuscarPunto.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                BuscarPunto buscarPunto = new BuscarPunto();

                int values = 0;

                if (!isValidNumber(edit_idpos.getText().toString().trim()))
                    values = Integer.parseInt(edit_idpos.getText().toString().trim());

                params.put("idpos", String.valueOf(values));
                params.put("cedula", edit_cedula.getText().toString());
                params.put("nombre", edit_nombre.getText().toString());
                params.put("depto", String.valueOf(departamento));
                params.put("ciudad", String.valueOf(ciudad_pro));
                params.put("distrito", String.valueOf(distrito));
                params.put("circuito", String.valueOf(estado_circuito));
                params.put("ruta", String.valueOf(estado_ruta));
                params.put("est_comercial", String.valueOf(estado_comercial));

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));


                return params;
            }
        };

        rq.add(jsonRequest);
    }

    private void parseJSONBuscar(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                ListHome responseHomeList = gson.fromJson(response, ListHome.class);

                setResponseHomeListS(responseHomeList);

                Bundle bundle = new Bundle();
                Intent intent = new Intent(this, ActDetalleBuscarPunto.class);
                bundle.putSerializable("value", responseHomeList);
                intent.putExtras(bundle);
                startActivity(intent);

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        }
    }

    private boolean isValidNumber(String number){return number == null || number.length() == 0;}

    private void setupGrid() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base),"cargar_filtros_puntos");
        rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSON(response);

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActBuscarPunto.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActBuscarPunto.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActBuscarPunto.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActBuscarPunto.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActBuscarPunto.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());

                return params;
            }
        };

        rq.add(jsonRequest);
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                ResponseCreatePunt responseCreatePunt = gson.fromJson(response, ResponseCreatePunt.class);

                loadDepartamento(responseCreatePunt);
                loadComercial(responseCreatePunt.getEstadoComunList());
                loadCircuito(responseCreatePunt.getTerritorioList());

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        }
    }

    private void loadCircuito(final List<Territorio> territorioList) {

        ArrayAdapter<Territorio> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, territorioList);
        spinner_circuito.setAdapter(prec3);
        spinner_circuito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                estado_circuito = territorioList.get(position).getId();

                loadRuta(territorioList.get(position).getZonaList());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadRuta(final List<Zona> zonaList) {

        ArrayAdapter<Zona> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, zonaList);
        spinner_ruta.setAdapter(prec3);
        spinner_ruta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estado_ruta = zonaList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadComercial(final List<CategoriasEstandar> estadoComunList) {

        ArrayAdapter<CategoriasEstandar> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, estadoComunList);
        spinner_est_comercial.setAdapter(prec3);
        spinner_est_comercial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                estado_comercial = estadoComunList.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }


    private void loadDepartamento(final ResponseCreatePunt responseCreatePunt) {

        ArrayAdapter<Departamentos> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, responseCreatePunt.getDepartamentosList());
        spinner_depto.setAdapter(prec3);
        spinner_depto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departamento = responseCreatePunt.getDepartamentosList().get(position).getId();
                loadCiudad(responseCreatePunt.getDepartamentosList().get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadCiudad(final Departamentos departamentos) {

        ArrayAdapter<Ciudad> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, departamentos.getCiudadList());
        spinner_provincia.setAdapter(prec3);
        spinner_provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ciudad_pro = departamentos.getCiudadList().get(position).getId();

                loadDistrito(departamentos.getCiudadList().get(position).getDistritoList());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadDistrito(final List<Distrito> distritoList) {

        ArrayAdapter<Distrito> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, distritoList);
        spinner_distrito.setAdapter(prec3);
        spinner_distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                distrito = distritoList.get(position).getId();
                //loadDistrito(departamentos.getCiudadList().get(position).getDistritoList());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (rq != null) {
            rq.cancelAll(TAG);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rq != null) {
            rq.cancelAll(TAG);
        }
    }

}
