package android.dcsdealerperu.com.dealerperu.Fragment;


import android.dcsdealerperu.com.dealerperu.Entry.LiquidacionRepartidor;
import android.dcsdealerperu.com.dealerperu.R;
import android.dcsdealerperu.com.dealerperu.Services.ConnectionDetector;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class FragmentHomeRepartidor extends BaseVolleyFragment {
    private SpotsDialog alertDialog;
    private TextView saldo;
    private TextView fecha;
    private TextView valor;
    private TextView txtInicio;
    private TextView txtFinal;
    private DecimalFormat format;
    private ProgressBar mProgress;

    public FragmentHomeRepartidor() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_repartidor, container, false);
        saldo = (TextView) view.findViewById(R.id.saldo_liquida);
        fecha = (TextView) view.findViewById(R.id.fecha);
        valor = (TextView) view.findViewById(R.id.valor);
        txtInicio = (TextView) view.findViewById(R.id.txtInicio);
        txtFinal = (TextView) view.findViewById(R.id.txtFinal);
        mProgress = (ProgressBar) view.findViewById(R.id.progressBar);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        format = new DecimalFormat("#,###.##");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());

        if (connectionDetector.isConnected()) {
            consultaLiquidacion();
        }

    }

    private void  consultaLiquidacion() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "liquidacion");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        CargarInforme(response);
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

                return params;

            }
        };

        addToQueue(jsonRequest);
    }

    private void CargarInforme(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {
                LiquidacionRepartidor liquidacionRepartidor = gson.fromJson(response,LiquidacionRepartidor.class);

                saldo.setText(String.format("S/. %s", format.format(liquidacionRepartidor.getSaldo_ventas())));
                fecha.setText(String.valueOf(liquidacionRepartidor.getFecha()));
                valor.setText(String.format("S/. %s", format.format(liquidacionRepartidor.getValor())));


                // Llenar ProgressBar.!
                int cantidad_peds = liquidacionRepartidor.getTotal_peds();
                int entregados = liquidacionRepartidor.getPeds_entregados();
                txtInicio.setText(String.valueOf(liquidacionRepartidor.getPeds_entregados()));
                txtFinal.setText(String.valueOf(liquidacionRepartidor.getTotal_peds()));

                float promedio = (float) entregados / (float)cantidad_peds;
                promedio = promedio * 100;
                mProgress.setProgress((int) promedio);

                alertDialog.dismiss();
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

}
