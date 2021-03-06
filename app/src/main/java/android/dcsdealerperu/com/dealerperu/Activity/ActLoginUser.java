package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.DataBase.DBHelper;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseUser;
import android.dcsdealerperu.com.dealerperu.Entry.TimeService;
import android.dcsdealerperu.com.dealerperu.R;
import android.dcsdealerperu.com.dealerperu.Services.ConnectionDetector;
import android.dcsdealerperu.com.dealerperu.Services.GpsServices;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.Coordenadas.setLatitud;
import static android.dcsdealerperu.com.dealerperu.Entry.Coordenadas.setLongitud;
import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.setResponseUserStatic;

public class ActLoginUser extends AppCompatActivity implements View.OnClickListener {

    private TextView editUsuario;
    private TextView editPassword;
    private CoordinatorLayout coordinatorLayout;
    private SpotsDialog alertDialog;
    private RequestQueue rq;
    public static final String TAG = "MyTag";
    private GpsServices gpsServices;
    private EditText edit_correo_edit;
    protected DialogEmail dialog;
    private DBHelper mydb;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gpsServices = new GpsServices(this);

        mydb = new DBHelper(this);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        connectionDetector = new ConnectionDetector(this);

        alertDialog = new SpotsDialog(this, R.style.Custom);

        editUsuario = (EditText) findViewById(R.id.editUsuario);
        editPassword = (EditText) findViewById(R.id.editPassword);
        TextView link_pass = (TextView) findViewById(R.id.link_pass);
        link_pass.setOnClickListener(this);

        //editUsuario.setText("repartidor");
        //editPassword.setText("pro_123");

        Button btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(this);

    }

    private void enviarCorreo() {
        alertDialog.show();
        String url = getString(R.string.url_base_correo); //"http://192.168.2.24/web/movistar_peru/distribuidorbt/modulos/login/controlador.php";
        rq = Volley.newRequestQueue(this);

        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        RespuestaCorreo(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //Toast.makeText(ActLoginUser.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                            Toast.makeText(ActLoginUser.this, "Hemos enviado un correo con la recuperación de la contraseña", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActLoginUser.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActLoginUser.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActLoginUser.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActLoginUser.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("accion", "Recover");
                params.put("correo", edit_correo_edit.getText().toString().trim());

                return params;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(70000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);
    }

    private void RespuestaCorreo(String response) {

        if (!response.equals("")) {

            switch (response) {
                case "1":
                    Toast.makeText(this, "Se enviaron los datos para recuperar la contraseña al correo indicado", Toast.LENGTH_LONG).show();
                    break;
                case "0":
                    Toast.makeText(this, "Error al intentar enviar el correo, intente nuevamente", Toast.LENGTH_LONG).show();
                    break;
                case "2":
                    Toast.makeText(this, "El correo: " + edit_correo_edit.getText() + " no se encuentra registrado", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(this, "Error al enviar el correo", Toast.LENGTH_LONG).show();
                    break;
            }

        } else {
            Toast.makeText(this, "No pudo enviar el correo", Toast.LENGTH_LONG).show();
        }

        alertDialog.dismiss();
    }

    private boolean isValidNumber(String number) {
        return number == null || number.length() == 0;
    }

    private boolean isValidNumberEmail(String number) {

        Pattern Plantilla = null;
        Matcher Resultado = null;
        Plantilla = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");

        Resultado = Plantilla.matcher(number);

        return Resultado.find();
    }

    private void loginServices() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "login_user");
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
                            Toast.makeText(ActLoginUser.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActLoginUser.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActLoginUser.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActLoginUser.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActLoginUser.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("user", editUsuario.getText().toString().trim());
                params.put("pass", editPassword.getText().toString().trim());

                return params;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);
    }

    private void parseJSON(String response) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                final ResponseUser login = gson.fromJson(response, ResponseUser.class);

                if (login.getPerfil() == 0) {
                    Snackbar.make(coordinatorLayout, "El usuario no tiene permisos", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {

                    new Thread(new Runnable() {
                        public void run() {

                            if (!mydb.validateLoginUser(editUsuario.getText().toString(), editPassword.getText().toString())) {
                                login.setPassword(editPassword.getText().toString());
                                login.setFechaSincro("1999-01-01 00:00:00");
                                mydb.insertLoginUser(login);
                            }

                            setResponseUserStatic(login);

                            mydb.deleteAllServiTime();

                            TimeService timeService = new TimeService();

                            timeService.setIdUser(login.getId());
                            timeService.setTraking(login.getIntervalo());
                            timeService.setTimeservice(login.getCantidad_envios());
                            timeService.setIdDistri(login.getId_distri());
                            timeService.setDataBase(login.getBd());
                            timeService.setFechainicial(login.getHora_inicial());
                            timeService.setFechaFinal(login.getHora_final());

                            mydb.insertTimeServices(timeService);

                            runOnUiThread(new Runnable() {
                                public void run() {

                                    setLatitud(gpsServices.getLatitude());
                                    setLongitud(gpsServices.getLongitude());

                                    startActivity(new Intent(ActLoginUser.this, ActMainPeru.class));
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    finish();
                                }
                            });
                        }

                    }).start();

                }

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        } else {
            Snackbar.make(coordinatorLayout, "Usuario/Password incorrectos", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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

    @Override
    public void onResume() {
        super.onResume();

        gpsServices = new GpsServices(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIngresar:

                if (gpsServices.getLatitude() == 0.0) {
                    gpsServices.showSettingsAlert();
                } else {
                    if (isValidNumber(editUsuario.getText().toString().trim())) {
                        editUsuario.setError("Campo requerido");
                        editUsuario.requestFocus();
                    } else if (isValidNumber(editPassword.getText().toString().trim())) {
                        editPassword.setError("Campo requerido");
                        editPassword.requestFocus();
                    } else {
                        if (connectionDetector.isConnected()){
                            loginServices();
                        } else {

                            if (mydb.validateLoginUser(editUsuario.getText().toString(), editPassword.getText().toString())) {
                                //Existe el usuario
                                setLatitud(gpsServices.getLatitude());
                                setLongitud(gpsServices.getLongitude());

                                ResponseUser login = mydb.getUserLogin(editUsuario.getText().toString());

                                setResponseUserStatic(login);

                                mydb.deleteAllServiTime();

                                TimeService timeService = new TimeService();

                                timeService.setIdUser(login.getId());
                                timeService.setTraking(login.getIntervalo());
                                timeService.setTimeservice(login.getCantidad_envios());
                                timeService.setIdDistri(login.getId_distri());
                                timeService.setDataBase(login.getBd());
                                timeService.setFechainicial(login.getHora_inicial());
                                timeService.setFechaFinal(login.getHora_final());

                                mydb.insertTimeServices(timeService);

                                startActivity(new Intent(this, ActMainPeru.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                finish();

                            } else {
                                Snackbar.make(coordinatorLayout, "Usuario no estan registrados Offline", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            }

                        }
                    }
                }

                break;
            case R.id.link_pass:

                dialog = new DialogEmail(ActLoginUser.this, "Recuperación de Contraseña");
                dialog.show();
                Button acceptButton = dialog.getButtonAccept();
                acceptButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        edit_correo_edit = (EditText) dialog.findViewById(R.id.edit_correo_edit);

                        if (isValidNumber(edit_correo_edit.getText().toString().trim())) {
                            edit_correo_edit.setError("Campo requerido");
                            edit_correo_edit.requestFocus();
                        } else if (!isValidNumberEmail(edit_correo_edit.getText().toString().trim())) {
                            edit_correo_edit.setError("El correo ingresado no es valido");
                            edit_correo_edit.requestFocus();
                        } else {
                            enviarCorreo();
                        }
                    }
                });

                break;
        }
    }
}
