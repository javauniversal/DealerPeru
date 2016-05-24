package android.dcsdealerperu.com.dealerperu.Fragment;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Activity.ActPlanificarOrdenar;
import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.ListResponsePlaniVisita;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class FragmentPlanificar extends BaseVolleyFragment implements View.OnClickListener {

    private FloatingActionButton btnBuscar;
    private Spinner spinner_planificacion;
    private Spinner spinner_tipo;
    private List<CategoriasEstandar> dataSpinnerPla = new ArrayList<>();
    private List<CategoriasEstandar> dataSpinnerTip = new ArrayList<>();
    private int idPlanificacion;
    private int idtipo;
    private LinearLayout liner_tipo;
    private SpotsDialog alertDialog;

    public FragmentPlanificar() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_planificar, container, false);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        btnBuscar = (FloatingActionButton) view.findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(this);

        spinner_planificacion = (Spinner) view.findViewById(R.id.spinner_planificacion);
        spinner_tipo = (Spinner) view.findViewById(R.id.spinner_tipo);

        liner_tipo = (LinearLayout) view.findViewById(R.id.liner_tipo);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadPlanificacion();
        loadTipo();

    }

    private void loadPlanificacion() {

        dataSpinnerPla = new ArrayList<>();

        dataSpinnerPla.add(new CategoriasEstandar(0, "SELECCIONAR"));
        dataSpinnerPla.add(new CategoriasEstandar(1, "Planificación Manual"));
        dataSpinnerPla.add(new CategoriasEstandar(2, "Planificación días de Inventario"));
        dataSpinnerPla.add(new CategoriasEstandar(3, "Planificación Promedio de Ventas"));

        ArrayAdapter<CategoriasEstandar> adapterEstados = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, dataSpinnerPla);
        spinner_planificacion.setAdapter(adapterEstados);
        spinner_planificacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idPlanificacion = dataSpinnerPla.get(position).getId();
                if (idPlanificacion == 2 || idPlanificacion == 3) {
                    liner_tipo.setVisibility(View.VISIBLE);
                } else {
                    liner_tipo.setVisibility(View.GONE);
                    spinner_tipo.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void loadTipo() {

        dataSpinnerTip = new ArrayList<>();

        dataSpinnerTip.add(new CategoriasEstandar(0, "SELECCIONAR"));
        dataSpinnerTip.add(new CategoriasEstandar(1, "Combos"));
        dataSpinnerTip.add(new CategoriasEstandar(2, "Simcard"));

        ArrayAdapter<CategoriasEstandar> adapterEstados = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, dataSpinnerTip);
        spinner_tipo.setAdapter(adapterEstados);
        spinner_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idtipo = dataSpinnerTip.get(position).getId();
                if (idPlanificacion == 2 || idPlanificacion == 3) {
                    liner_tipo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuscar:

                if (idPlanificacion == 0) {
                    Toast.makeText(getActivity(), "Por favor seleccione un parametro", Toast.LENGTH_LONG).show();
                } else if (liner_tipo.getVisibility() == View.VISIBLE && idtipo == 0) {
                    Toast.makeText(getActivity(), "Por favor selecciones un tipo", Toast.LENGTH_LONG).show();
                } else {
                    getPlanificar();
                }

                break;
        }
    }

    private void getPlanificar() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "planifica_visita");
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
                        alertDialog.dismiss();
                        onConnectionFailed(error_string);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("tipo", String.valueOf(idPlanificacion));
                params.put("valor", String.valueOf(idtipo));
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

                ListResponsePlaniVisita responseMarcarPedido = gson.fromJson(response, ListResponsePlaniVisita.class);

                //Activity Detalle
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), ActPlanificarOrdenar.class);
                bundle.putSerializable("value", responseMarcarPedido);
                intent.putExtras(bundle);
                startActivity(intent);

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        } else {
            alertDialog.dismiss();
            Toast.makeText(getActivity(), "No se encontraron datos", Toast.LENGTH_LONG).show();
        }
    }
}
