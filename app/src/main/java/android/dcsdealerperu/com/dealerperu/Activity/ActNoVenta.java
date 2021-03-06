package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.DataBase.DBHelper;
import android.dcsdealerperu.com.dealerperu.Entry.Motivos;
import android.dcsdealerperu.com.dealerperu.Entry.NoVisita;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class ActNoVenta extends AppCompatActivity implements View.OnClickListener {

    private Bundle bundle;
    private ResponseMarcarPedido thumbs = new ResponseMarcarPedido();
    private Spinner spinner_motivos;
    private EditText EditComment;
    private Button btn_guardar_no;
    private Button btn_guardar;
    private int id_estado;
    private RequestQueue rq;
    public static final String TAG = "MyTag";
    private SpotsDialog alertDialog;
    private GpsServices gpsServices;
    private ConnectionDetector connectionDetector;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectionDetector = new ConnectionDetector(this);
        setContentView(R.layout.activity_no_venta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (connectionDetector.isConnected()) {
            toolbar.setTitle("No venta");
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            toolbar.setBackgroundColor(Color.RED);
            toolbar.setTitle("No venta Offline");
        }

        setSupportActionBar(toolbar);

        mydb = new DBHelper(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gpsServices = new GpsServices(this);

        alertDialog = new SpotsDialog(this, R.style.Custom);

        spinner_motivos = (Spinner) findViewById(R.id.spinner_motivos);
        EditComment = (EditText) findViewById(R.id.EditComment);

        btn_guardar_no = (Button) findViewById(R.id.btn_guardar_no);
        btn_guardar_no.setOnClickListener(this);

        btn_guardar = (Button) findViewById(R.id.btn_guardar);
        btn_guardar.setOnClickListener(this);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            thumbs = (ResponseMarcarPedido) bundle.getSerializable("value");
            if (connectionDetector.isConnected()) {
                loadCausa(thumbs.getMotivosList());
            } else {
                List<Motivos> motivosList = new ArrayList<>();

                motivosList.add(new Motivos(1, "STOCK SUFICIENTE"));
                motivosList.add(new Motivos(2, "CERRADO"));
                motivosList.add(new Motivos(3, "NO SE ENCUENTRA EL DUEÑO"));
                motivosList.add(new Motivos(4, "FALTA DE CAPITAL"));
                motivosList.add(new Motivos(5, "NO EXISTE EL PDV"));

                thumbs.setMotivosList(motivosList);
                loadCausa(thumbs.getMotivosList());
            }
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadCausa(final List<Motivos> thumbs) {

        ArrayAdapter<Motivos> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, thumbs);
        spinner_motivos.setAdapter(prec3);
        spinner_motivos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_estado = thumbs.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private boolean isValidNumber(String number) {
        return number == null || number.length() == 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_guardar_no:

                onBackPressed();

                break;

            case R.id.btn_guardar:

                if (isValidNumber(EditComment.getText().toString().trim())) {
                    EditComment.setFocusable(true);
                    EditComment.setFocusableInTouchMode(true);
                    EditComment.requestFocus();
                    EditComment.setText("");
                    EditComment.setError("Este campo es obligatorio");
                } else {

                    if (connectionDetector.isConnected()) {
                        guardarVisita();
                    } else {
                        //Guardar no visita local...
                        NoVisita noVisita = new NoVisita();
                        noVisita.setIdpos(thumbs.getId_pos());
                        noVisita.setMotivo(id_estado);
                        noVisita.setObservacion(EditComment.getText().toString());
                        noVisita.setLatitud(gpsServices.getLatitude());
                        noVisita.setLongitud(gpsServices.getLongitude());
                        noVisita.setIduser(getResponseUserStatic().getId());
                        noVisita.setIdids(Integer.parseInt(getResponseUserStatic().getId_distri()));
                        noVisita.setDb(getResponseUserStatic().getBd());
                        noVisita.setPerfil(getResponseUserStatic().getPerfil());


                        if (mydb.insertNoVisita(noVisita)) {
                            Toast.makeText(this, "Se guardo con exito la visita", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(this, ActMainPeru.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                        } else {
                            Toast.makeText(this, "Problemas al guardar la visita", Toast.LENGTH_LONG).show();
                        }
                    }

                }

                break;
        }
    }

    private void guardarVisita() {

        alertDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_no_venta");
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
                            Toast.makeText(ActNoVenta.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActNoVenta.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActNoVenta.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActNoVenta.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActNoVenta.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("idpos", String.valueOf(thumbs.getId_pos()));
                params.put("motivo", String.valueOf(id_estado));
                params.put("observacion", EditComment.getText().toString());
                params.put("latitud", String.valueOf(gpsServices.getLatitude()));
                params.put("longitud", String.valueOf(gpsServices.getLongitude()));

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));

                return params;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                ResponseMarcarPedido responseMarcarPedido = gson.fromJson(response, ResponseMarcarPedido.class);

                if (responseMarcarPedido.getEstado() == -1) {
                    //Error
                    Toast.makeText(this, "No se pudo Guardar la visita", Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == -2) {
                    //No tiene permiso
                    Toast.makeText(this, "No tiene permiso para marcar visita", Toast.LENGTH_LONG).show();
                } else {
                    // ok
                    Toast.makeText(this, "Se guardo con exito la visita", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, ActMainPeru.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
