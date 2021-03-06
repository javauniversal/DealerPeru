package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.DataBase.DBHelper;
import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.Ciudad;
import android.dcsdealerperu.com.dealerperu.Entry.Departamentos;
import android.dcsdealerperu.com.dealerperu.Entry.Distrito;
import android.dcsdealerperu.com.dealerperu.Entry.ListEntregarPedido;
import android.dcsdealerperu.com.dealerperu.Entry.ListHome;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseCreatePunt;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseHome;
import android.dcsdealerperu.com.dealerperu.Entry.Territorio;
import android.dcsdealerperu.com.dealerperu.Entry.Zona;
import android.dcsdealerperu.com.dealerperu.R;
import android.dcsdealerperu.com.dealerperu.Services.ConnectionDetector;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class ActBusquedaAvan extends AppCompatActivity {

    private SpotsDialog alertDialog;
    private RequestQueue rq;
    public static final String TAG = "MyTag";
    private Spinner spinner_depto;
    private Spinner spinner_provincia;
    private Spinner spinner_distrito;
    private Spinner spinner_circuito;
    private Spinner spinner_ruta;
    private Spinner spinner_est_comercial;
    private int departamento;
    private int ciudad_pro;
    private int distrito;
    private int estado_circuito;
    private int estado_ruta;
    private int estado_comercial;
    private Bundle bundle;

    private EditText edit_nombre_punto;
    private EditText edit_nit_punto;
    private EditText edit_nombre_cliente;
    private String accion = "";
    private String consulta = "";
    private ConnectionDetector connectionDetector;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_avan);
        connectionDetector = new ConnectionDetector(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (connectionDetector.isConnected()) {
            toolbar.setTitle("Buscar PDVS");
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            toolbar.setBackgroundColor(Color.RED);
            toolbar.setTitle("Buscar PDVS Offline");
        }

        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            accion = bundle.getString("value");
        }

        spinner_depto = (Spinner) findViewById(R.id.spinner_depto);
        spinner_provincia = (Spinner) findViewById(R.id.spinner_provincia);
        spinner_distrito = (Spinner) findViewById(R.id.spinner_distrito);
        spinner_circuito = (Spinner) findViewById(R.id.spinner_circuito);
        spinner_ruta = (Spinner) findViewById(R.id.spinner_ruta);
        spinner_est_comercial = (Spinner) findViewById(R.id.spinner_est_comercial);

        edit_nombre_punto = (EditText) findViewById(R.id.edit_nombre_punto);
        edit_nit_punto = (EditText) findViewById(R.id.edit_nit_punto);
        edit_nombre_cliente = (EditText) findViewById(R.id.edit_nombre_cliente);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarFormt();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        connectionDetector = new ConnectionDetector(this);

        mydb = new DBHelper(this);

        alertDialog = new SpotsDialog(this, R.style.Custom);

        setupGrid();
    }

    private void buscarPuntoJSO() {

        alertDialog.show();
        consulta = "consultar_puntos_avanzados";
        if (accion.equals("Repartidor"))
            consulta = "datos_entrega";

        String url = String.format("%1$s%2$s", getString(R.string.url_base), consulta);
        rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
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
                            Toast.makeText(ActBusquedaAvan.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActBusquedaAvan.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActBusquedaAvan.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActBusquedaAvan.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActBusquedaAvan.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("nit_punto", edit_nit_punto.getText().toString());
                params.put("nombre_punto", edit_nombre_punto.getText().toString());
                params.put("responsable", edit_nombre_cliente.getText().toString());
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
                if (!accion.equals("Repartidor")) {

                    ListHome responseHomeList = gson.fromJson(response, ListHome.class);

                    setResponseHomeListS(responseHomeList);

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(this, ActResponAvanBusqueda.class);
                    bundle.putSerializable("value", responseHomeList);
                    intent.putExtras(bundle);
                    startActivity(intent);

                } else if (accion.equals("Repartidor")) {

                    ListEntregarPedido responseEntregarPedido = gson.fromJson(response, ListEntregarPedido.class);

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(this, ActEntregarPedido.class);
                    bundle.putSerializable("value", responseEntregarPedido);
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

            Toast.makeText(this, "No se encontraron puntos", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
        }
    }

    private void validarFormt() {
        if (isValidNumber(edit_nombre_punto.getText().toString()) && isValidNumber(edit_nit_punto.getText().toString()) && isValidNumber(edit_nombre_cliente.getText().toString()) && isValiSpinner()) {
            Toast.makeText(this, "Ingrese al menos un parámetro", Toast.LENGTH_LONG).show();
        } else {
            if (connectionDetector.isConnected()) {
                buscarPuntoJSO();
            } else {
                // query de consultar...
                List<ResponseHome> responseHomeList = mydb.getBuscarPuntoLocal(edit_nit_punto.getText().toString(), edit_nombre_punto.getText().toString(), edit_nombre_cliente.getText().toString(), departamento, ciudad_pro, distrito, estado_circuito, estado_ruta, estado_comercial);

                if (responseHomeList.size() > 0) {
                    setResponseHomeListS(responseHomeList);

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(this, ActResponAvanBusqueda.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                } else {
                    Toast.makeText(this, "No se encontraron puntos", Toast.LENGTH_LONG).show();
                }

            }


        }
    }

    private boolean isValiSpinner() {

        if (estado_circuito == 0 && departamento == 0 && ciudad_pro == 0 && distrito == 0 && estado_circuito == 0 && estado_ruta == 0 && estado_comercial == 0)
            return true;
        else
            return false;

    }

    private boolean isValidNumber(String number) {
        return number == null || number.length() == 0;
    }

    private void setupGrid() {

        ResponseCreatePunt responseCreatePunt = mydb.getDepartamentos();

        loadDepartamento(responseCreatePunt);
        loadComercial(responseCreatePunt.getEstadoComunList());
        loadCircuito(responseCreatePunt.getTerritorioList());

        /*alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cargar_filtros_puntos");
        rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSON(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActBusquedaAvan.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActBusquedaAvan.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActBusquedaAvan.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActBusquedaAvan.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActBusquedaAvan.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
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

        rq.add(jsonRequest);*/
    }

    /*private void parseJSON(String response) {
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
    }*/

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
            public void onNothingSelected(AdapterView<?> parent) {
            }

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
            public void onNothingSelected(AdapterView<?> parent) {
            }

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
            public void onNothingSelected(AdapterView<?> parent) {
            }

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
            public void onNothingSelected(AdapterView<?> parent) {
            }

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
            public void onNothingSelected(AdapterView<?> parent) {
            }

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
            public void onNothingSelected(AdapterView<?> parent) {
            }

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
