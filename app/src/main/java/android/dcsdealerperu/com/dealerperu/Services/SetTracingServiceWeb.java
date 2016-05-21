package android.dcsdealerperu.com.dealerperu.Services;

import android.app.Service;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.DataBase.DBHelper;
import android.dcsdealerperu.com.dealerperu.Entry.ListTracing;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.dcsdealerperu.com.dealerperu.Entry.Tracing;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.IBinder;
import android.util.Log;
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

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SetTracingServiceWeb extends Service {

    private DBHelper mydb;
    private static final String TAG = MonitoringService.class.getSimpleName();
    TimerTask timerTask;
    private RequestQueue rq;

    public SetTracingServiceWeb() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Servicio creado enviar web service...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Timer timer = new Timer();
        mydb = new DBHelper(getApplicationContext());

        if (timerTask == null) {

            timerTask = new TimerTask() {
                @Override
                public void run() {

                    List<Tracing> seguimientoList = mydb.getTracingService();
                    if (seguimientoList.size() > 0)
                        loginServices(seguimientoList);

                }
            };

            //Tiempo Recuperado desde la base de datos
            timer.scheduleAtFixedRate(timerTask, 0, 300000);
        }


        return START_STICKY;
    }

    private void loginServices(final List<Tracing> seguimientoList) {
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "servicio_offline_pos");
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
                            Toast.makeText(getApplicationContext(), "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                String parJSON = new Gson().toJson(seguimientoList, ListTracing.class);

                params.put("datos", parJSON);

                return params;
            }
        };

        rq.add(jsonRequest);
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                Charset.forName("UTF-8").encode(response);

                byte ptext[] = response.getBytes(Charset.forName("ISO-8859-1"));

                String value = new String(ptext, Charset.forName("UTF-8"));

                ResponseMarcarPedido responseMarcarPedido = gson.fromJson(value, ResponseMarcarPedido.class);

                if (responseMarcarPedido.getEstado() == -1) {
                    Toast.makeText(getApplicationContext(), "No se pudo guardar el seguimiento.", Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == 0) {
                    mydb.deleteAllServiTime();
                }

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
            } finally {

            }
        } else {

        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
