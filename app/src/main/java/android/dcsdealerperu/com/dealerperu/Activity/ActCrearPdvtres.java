package android.dcsdealerperu.com.dealerperu.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.DataBase.DBHelper;
import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.RequestGuardarEditarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseInsert;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.dcsdealerperu.com.dealerperu.Entry.Sincronizar;
import android.dcsdealerperu.com.dealerperu.Entry.Subcategorias;
import android.dcsdealerperu.com.dealerperu.Entry.Territorio;
import android.dcsdealerperu.com.dealerperu.Entry.Zona;
import android.dcsdealerperu.com.dealerperu.R;
import android.dcsdealerperu.com.dealerperu.Services.ConnectionDetector;
import android.dcsdealerperu.com.dealerperu.Services.GpsServices;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.DataDireccionForm.getCategoriasList;
import static android.dcsdealerperu.com.dealerperu.Entry.DataDireccionForm.getEstadoComunList;
import static android.dcsdealerperu.com.dealerperu.Entry.DataDireccionForm.getTerritorioList;
import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class ActCrearPdvtres extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner_estado_comercial;
    private Spinner spinner_circuito;
    private Spinner spinner_ruta;
    private Spinner spinner_categoria;
    private Spinner spinner_sub_categoria;
    private EditText edit_codigo_cum;
    private EditText edit_referencia;
    private int estado_comercial;
    private int estado_categoria;
    private int estado_sub_categoria;
    private int estado_territorio;
    private int estado_ruta;
    private GpsServices gpsServices;
    private RequestGuardarEditarPunto mDescribable;
    private RequestQueue rq;
    public static final String TAG = "MyTag";
    private SpotsDialog alertDialog;
    private ConnectionDetector connectionDetector;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pdvtres);
        connectionDetector = new ConnectionDetector(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (connectionDetector.isConnected()) {
            toolbar.setTitle("Crear Punto");
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            toolbar.setBackgroundColor(Color.RED);
            toolbar.setTitle("Crear Punto Offline");
        }

        setSupportActionBar(toolbar);

        connectionDetector = new ConnectionDetector(this);

        mydb = new DBHelper(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (RequestGuardarEditarPunto) bundle.getSerializable("value");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spinner_estado_comercial = (Spinner) findViewById(R.id.spinner_estado_comercial);
        spinner_circuito = (Spinner) findViewById(R.id.spinner_circuito);
        spinner_ruta = (Spinner) findViewById(R.id.spinner_ruta);
        spinner_categoria = (Spinner) findViewById(R.id.spinner_categoria);
        spinner_sub_categoria = (Spinner) findViewById(R.id.spinner_sub_categoria);
        edit_codigo_cum = (EditText) findViewById(R.id.edit_codigo_cum);
        edit_referencia = (EditText) findViewById(R.id.edit_referencia);

        Button btn_guardar = (Button) findViewById(R.id.btn_guardar);
        btn_guardar.setOnClickListener(this);
        Button btn_regresar_ref = (Button) findViewById(R.id.btn_regresar_ref);
        btn_regresar_ref.setOnClickListener(this);

        alertDialog = new SpotsDialog(this, R.style.Custom);

        gpsServices = new GpsServices(this);

        loadComercial(getEstadoComunList());
        loadCircuito(getTerritorioList());
        loadCategoria(getCategoriasList());

        if (mDescribable.getAccion().equals("Editar"))
            dataEditReference();

    }

    private void dataEditReference() {

        setEstadoComercial(getEstadoComunList(), spinner_estado_comercial, mDescribable.getEstado_com());
        setCircuito(getTerritorioList(), spinner_circuito, mDescribable.getTerritorio());
        setRuta(getTerritorioList(), spinner_ruta, mDescribable.getZona());
        setCategoria(getCategoriasList(), spinner_categoria, mDescribable.getCategoria());
        setSubCategoria(getCategoriasList(), spinner_sub_categoria, mDescribable.getSubcategoria());

        edit_referencia.setText(mDescribable.getRef_direccion());

        edit_codigo_cum.setText(mDescribable.getCodigo_cum());

    }

    private void loadCategoria(final List<CategoriasEstandar> categoriasList) {
        ArrayAdapter<CategoriasEstandar> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, categoriasList);
        spinner_categoria.setAdapter(prec3);
        spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                loadSubCategoria(categoriasList.get(position).getListSubCategoria());

                estado_categoria = categoriasList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void loadSubCategoria(final List<Subcategorias> listSubCategoria) {
        ArrayAdapter<Subcategorias> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, listSubCategoria);
        spinner_sub_categoria.setAdapter(prec3);
        spinner_sub_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                estado_sub_categoria = listSubCategoria.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void loadCircuito(final List<Territorio> territorioList) {

        ArrayAdapter<Territorio> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, territorioList);
        spinner_circuito.setAdapter(prec3);
        spinner_circuito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                estado_territorio = territorioList.get(position).getId();

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
        spinner_estado_comercial.setAdapter(prec3);
        spinner_estado_comercial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                estado_comercial = estadoComunList.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void setSubCategoria(List<CategoriasEstandar> categoriasList, Spinner spinner_sub_categoria, int subcategoria) {
        for (int i = 0; i < categoriasList.size(); i++) {
            for (int a = 0; a < categoriasList.get(i).getListSubCategoria().size(); a++) {
                if (categoriasList.get(i).getListSubCategoria().get(a).getId() == subcategoria) {
                    spinner_sub_categoria.setSelection(i);
                    break;
                }
            }
        }
    }

    private void setCategoria(List<CategoriasEstandar> categoriasList, Spinner spinner_categoria, int categoria) {
        for (int i = 0; i < categoriasList.size(); i++) {
            if (categoriasList.get(i).getId() == categoria) {
                spinner_categoria.setSelection(i);
                break;
            }
        }
    }

    private void setRuta(List<Territorio> territorioList, Spinner spinner_ruta, int zona) {
        for (int i = 0; i < territorioList.size(); i++) {
            for (int a = 0; a < territorioList.get(i).getZonaList().size(); a++) {
                if (territorioList.get(i).getZonaList().get(a).getId() == zona) {
                    spinner_ruta.setSelection(i);
                    break;
                }
            }
        }
    }

    private void setCircuito(List<Territorio> territorioList, Spinner spinner_circuito, int territorio) {
        for (int i = 0; i < territorioList.size(); i++) {
            if (territorioList.get(i).getId() == territorio) {
                spinner_circuito.setSelection(i);
                break;
            }
        }
    }

    private void setEstadoComercial(List<CategoriasEstandar> estadoComunList, Spinner spinner_estado_comercial, int estado_com) {
        for (int i = 0; i < estadoComunList.size(); i++) {
            if (estadoComunList.get(i).getId() == estado_com) {
                spinner_estado_comercial.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_guardar:

                if (!validarCampos()) {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                    dialogo1.setTitle("Confirmar");
                    dialogo1.setMessage("¿ Desea guardar los datos del punto ?");
                    dialogo1.setCancelable(false);

                    dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {

                            if (connectionDetector.isConnected()) {
                                dialogo1.dismiss();
                                guardarDataPedido();
                            } else {
                                dialogo1.dismiss();
                                guardarLocal();
                            }
                        }

                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            dialogo1.dismiss();
                        }
                    });

                    dialogo1.show();
                }

                break;

            case R.id.btn_regresar_ref:
                finish();
                break;
        }
    }

    private void guardarLocal() {

        mDescribable.setCodigo_cum(edit_codigo_cum.getText().toString().trim());

        mDescribable.setEstado_com(estado_comercial);
        mDescribable.setCategoria(estado_categoria);
        mDescribable.setSubcategoria(estado_sub_categoria);
        mDescribable.setTerritorio(estado_territorio);
        mDescribable.setZona(estado_ruta);
        mDescribable.setRef_direccion(edit_referencia.getText().toString());
        mDescribable.setLatitud(gpsServices.getLatitude());
        mDescribable.setLongitud(gpsServices.getLongitude());

        List<RequestGuardarEditarPunto> puntoList = new ArrayList<>();
        Sincronizar sincronizar = new Sincronizar();
        mDescribable.setAccion("Sincronizar");

        puntoList.add(mDescribable);
        sincronizar.setPuntosList(puntoList);

        if(mydb.insertPunto(sincronizar, 1)) {

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Punto Offline");
            dialogo1.setMessage(mDescribable.getNombre_punto());
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    startActivity(new Intent(ActCrearPdvtres.this, ActMainPeru.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            });
            dialogo1.setNegativeButton("Vender", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    //Vender ();
                    //buscarIdPunto(Integer.parseInt(responseInsert.getIdpos()));
                }
            });

            dialogo1.show();
        } else {
            Toast.makeText(this, "El punto no se pudo crear Offline comuniquese con el administrador", Toast.LENGTH_LONG).show();
        }

    }

    private void guardarDataPedido() {

        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_punto");
        rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSONGuardar(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActCrearPdvtres.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActCrearPdvtres.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActCrearPdvtres.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActCrearPdvtres.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActCrearPdvtres.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                mDescribable.setCodigo_cum(edit_codigo_cum.getText().toString().trim());

                mDescribable.setEstado_com(estado_comercial);
                mDescribable.setCategoria(estado_categoria);
                mDescribable.setSubcategoria(estado_sub_categoria);
                mDescribable.setTerritorio(estado_territorio);
                mDescribable.setZona(estado_ruta);
                mDescribable.setRef_direccion(edit_referencia.getText().toString());

                String parJSON = new Gson().toJson(mDescribable, RequestGuardarEditarPunto.class);

                params.put("datos", parJSON);
                params.put("idpos", String.valueOf(mDescribable.getIdpos())); //Params Editar
                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));
                params.put("latitud", String.valueOf(gpsServices.getLatitude()));
                params.put("longitud", String.valueOf(gpsServices.getLongitude()));

                params.put("accion", mDescribable.getAccion());

                return params;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);
    }

    private boolean isValidNumber(String number) {
        return number == null || number.length() == 0;
    }

    private void parseJSONGuardar(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {
                Charset.forName("UTF-8").encode(response);
                byte ptext[] = response.getBytes(Charset.forName("ISO-8859-1"));
                String value = new String(ptext, Charset.forName("UTF-8"));

                final ResponseInsert responseInsert = gson.fromJson(value, ResponseInsert.class);

                if (responseInsert.getId() == 0) {

                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                    dialogo1.setTitle(responseInsert.getMsg());
                    dialogo1.setMessage(mDescribable.getNombre_punto() + "\n" + "ID PDV: " + responseInsert.getIdpos());
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            startActivity(new Intent(ActCrearPdvtres.this, ActMainPeru.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                        }
                    });
                    dialogo1.setNegativeButton("Vender", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            //Vender ();
                            buscarIdPunto(Integer.parseInt(responseInsert.getIdpos()));
                        }
                    });

                    dialogo1.show();

                } else if (responseInsert.getId() == -1) {
                    Toast.makeText(this, responseInsert.getMsg(), Toast.LENGTH_LONG).show();
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

    public void buscarIdPunto(final int idPos) {

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "buscar_punto_visita");
        rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSONPunto(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActCrearPdvtres.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActCrearPdvtres.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActCrearPdvtres.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActCrearPdvtres.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActCrearPdvtres.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
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
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));
                params.put("idpos", String.valueOf(idPos));

                return params;
            }
        };

        rq.add(jsonRequest);

    }

    public void parseJSONPunto(String response) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                ResponseMarcarPedido responseMarcarPedido = gson.fromJson(response, ResponseMarcarPedido.class);

                if (responseMarcarPedido.getEstado() == -1) {
                    //No tiene permisos del punto
                    Toast.makeText(ActCrearPdvtres.this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == -2) {
                    //El punto no existe
                    Toast.makeText(ActCrearPdvtres.this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else {
                    //Activity Detalle
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(ActCrearPdvtres.this, ActMarcarVisita.class);
                    bundle.putSerializable("value", responseMarcarPedido);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
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

    public boolean validarCampos() {

        boolean indicadorValidate = false;

        if (estado_territorio == 0) {
            spinner_circuito.setFocusable(true);
            spinner_circuito.setFocusableInTouchMode(true);
            spinner_circuito.requestFocus();
            Toast.makeText(this, "El campo circuito es obligatorio", Toast.LENGTH_SHORT).show();
            indicadorValidate = true;
        } else if (estado_ruta == 0) {
            spinner_ruta.setFocusable(true);
            spinner_ruta.setFocusableInTouchMode(true);
            spinner_ruta.requestFocus();
            Toast.makeText(this, "El campo ruta es obligatorio", Toast.LENGTH_SHORT).show();
            indicadorValidate = true;
        } else if (estado_comercial == 0) {
            spinner_estado_comercial.setFocusable(true);
            spinner_estado_comercial.setFocusableInTouchMode(true);
            spinner_estado_comercial.requestFocus();
            Toast.makeText(this, "El campo estado comercial es obligatorio", Toast.LENGTH_SHORT).show();
            indicadorValidate = true;
        } else if (estado_categoria == 0) {
            spinner_categoria.setFocusable(true);
            spinner_categoria.setFocusableInTouchMode(true);
            spinner_categoria.requestFocus();
            Toast.makeText(this, "El campo categoria es obligatorio", Toast.LENGTH_SHORT).show();
            indicadorValidate = true;
        } else if (estado_sub_categoria == 0) {
            spinner_sub_categoria.setFocusable(true);
            spinner_sub_categoria.setFocusableInTouchMode(true);
            spinner_sub_categoria.requestFocus();
            Toast.makeText(this, "El campo sub categoria es obligatorio", Toast.LENGTH_SHORT).show();
            indicadorValidate = true;
        }

        return indicadorValidate;

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
