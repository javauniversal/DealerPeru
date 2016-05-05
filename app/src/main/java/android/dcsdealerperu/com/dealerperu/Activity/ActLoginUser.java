package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseUser;
import android.dcsdealerperu.com.dealerperu.R;
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

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.setResponseUserStatic;

public class ActLoginUser extends AppCompatActivity {

    private TextView editUsuario;
    private TextView editPassword;
    private Button btnIngresar;
    private CoordinatorLayout coordinatorLayout;
    private SpotsDialog alertDialog;
    private RequestQueue rq;
    public static final String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        alertDialog = new SpotsDialog(this, R.style.Custom);

        editUsuario = (EditText) findViewById(R.id.editUsuario);
        editPassword = (EditText) findViewById(R.id.editPassword);

        editUsuario.setText("123");
        editPassword.setText("123");


        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidNumber(editUsuario.getText().toString().trim())) {
                    editUsuario.setError("Campo requerido");
                    editUsuario.requestFocus();
                } else if (isValidNumber(editPassword.getText().toString().trim())){
                    editPassword.setError("Campo requerido");
                    editPassword.requestFocus();
                }else {
                    loginServices();
                }
            }
        });
    }

    private boolean isValidNumber(String number){return number == null || number.length() == 0;}

    private void loginServices(){
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base),"login_user");
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

        rq.add(jsonRequest);
    }

    private void parseJSON(String response) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {
                ResponseUser login = gson.fromJson(response, ResponseUser.class);
                setResponseUserStatic(login);

                startActivity(new Intent(this, ActMainPeru.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();

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

}
