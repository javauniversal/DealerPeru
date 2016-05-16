package android.dcsdealerperu.com.dealerperu.Fragment;

import android.annotation.SuppressLint;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseRutero;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

@SuppressLint("ValidFragment")
public class FavoriteTabFragment2 extends BaseVolleyFragment {

    private int mPosition;
    private ProgressBar mProgress;
    private ProgressBar progressBar2;
    private ProgressBar progressBarCumpli;
    private ProgressBar progressBar2Cumpli;
    private TextView txtFinal;
    private TextView txtPromedio;
    private TextView txtFinalPedido;
    private TextView txtPromediopedido;
    private TextView txtPorCum;
    private TextView txtPorEfec;
    private TextView txtFinalCumpli;
    private TextView txtFinalPedidoCumpli;
    private TextView txtCantidadCumpli;
    private TextView txtPromediopedidoCumpli;
    private TextView txtPorCumCumpli;
    private TextView txtPorCombos;
    private int mProgressStatus = 0;
    private int visitasTotal = 0;
    private int totalConPedido = 0;
    private Handler mHandler = new Handler();

    public FavoriteTabFragment2(int position) {
        mPosition = position;
    }

    public FavoriteTabFragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mProgress = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar) rootView.findViewById(R.id.progressBar2);
        progressBarCumpli = (ProgressBar) rootView.findViewById(R.id.progressBarCumpli);
        progressBar2Cumpli = (ProgressBar) rootView.findViewById(R.id.progressBar2Cumpli);
        txtFinal = (TextView) rootView.findViewById(R.id.txtFinal);
        txtPromedio = (TextView) rootView.findViewById(R.id.txtPromedio);
        txtFinalPedido = (TextView) rootView.findViewById(R.id.txtFinalPedido);
        txtPromediopedido = (TextView) rootView.findViewById(R.id.txtPromediopedido);
        txtPorCum = (TextView) rootView.findViewById(R.id.txtPorCum);
        txtPorEfec = (TextView) rootView.findViewById(R.id.txtPorEfec);
        txtFinalCumpli = (TextView) rootView.findViewById(R.id.txtFinalCumpli);
        txtFinalPedidoCumpli = (TextView) rootView.findViewById(R.id.txtFinalPedidoCumpli);
        txtCantidadCumpli = (TextView) rootView.findViewById(R.id.txtCantidadCumpli);
        txtPromediopedidoCumpli = (TextView) rootView.findViewById(R.id.txtPromediopedidoCumpli);
        txtPorCumCumpli = (TextView) rootView.findViewById(R.id.txtPorCumCumpli);
        txtPorCombos = (TextView) rootView.findViewById(R.id.txtPorCombos);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupGrid();
    }

    private void setupGrid() {

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "rutero");
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

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void parseJSON(String response) {

        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                final ResponseRutero listHome = gson.fromJson(response, ResponseRutero.class);

                txtFinal.setText(listHome.getResponseHomeList().size()+"");

                txtFinalCumpli.setText(listHome.getCant_cumplimiento_sim()+"");
                txtFinalPedidoCumpli.setText(listHome.getCant_cumplimiento_combo()+"");

                txtCantidadCumpli.setText(listHome.getCant_ventas_sim()+"");
                txtPromediopedidoCumpli.setText(listHome.getCant_ventas_combo()+"");

                new Thread(new Runnable() {
                    public void run() {

                        while (mProgressStatus < listHome.getResponseHomeList().size()) {

                            if (listHome.getResponseHomeList().get(mProgressStatus).getTipo_visita() == 1 || listHome.getResponseHomeList().get(mProgressStatus).getTipo_visita() == 2)
                                visitasTotal++;

                            if (listHome.getResponseHomeList().get(mProgressStatus).getTipo_visita() == 2)
                                totalConPedido++;

                            // Update the progress bar
                            mHandler.post(new Runnable() {
                                public void run() {
                                    //Visitas
                                    // Progress 1
                                    float promedio = (float) visitasTotal / (float) listHome.getResponseHomeList().size();
                                    promedio = promedio * 100;
                                    mProgress.setProgress((int) promedio);
                                    txtPromedio.setText(visitasTotal + "");

                                    // Progress 2
                                    txtFinalPedido.setText(visitasTotal + "");
                                    txtPromediopedido.setText(totalConPedido + "");
                                    float promedio2 = (float) totalConPedido / (float) visitasTotal;

                                    promedio2 = promedio2 * 100;

                                    progressBar2.setProgress((int) promedio2);

                                    txtPorCum.setText((int) promedio + "%");
                                    txtPorEfec.setText((int) promedio2 + "%");

                                    //Cumplimiento
                                    //Progress 1
                                    float progress3 = ((float) listHome.getCant_ventas_sim() / (float) listHome.getCant_cumplimiento_sim()) * 100;

                                    progressBarCumpli.setProgress(listHome.getCant_ventas_sim());
                                    progressBarCumpli.setProgress((int) progress3);
                                    txtPorCumCumpli.setText((int) progress3 +"%");

                                    //Progress 2
                                    float progress4 = ((float) listHome.getCant_ventas_combo() / (float) listHome.getCant_cumplimiento_combo()) * 100;

                                    progressBar2Cumpli.setMax(listHome.getCant_cumplimiento_sim());
                                    progressBar2Cumpli.setProgress((int) progress4);
                                    txtPorCombos.setText((int) progress4 +"%");

                                }
                            });

                            mProgressStatus++;
                        }
                    }
                }).start();

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
            } finally {

            }
        } else {

        }

    }

}