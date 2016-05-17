package android.dcsdealerperu.com.dealerperu.Fragment;


import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.ListCategoria;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.dcsdealerperu.com.dealerperu.R;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHomeSuperPrin extends BaseVolleyFragment {

    private int  mPosition;
    private Spinner spinner_vendedor;
    private SpotsDialog alertDialog;
    private int vendedor;

    private FragmentHomeSuper fragmentHomeSuper;

    public FragmentHomeSuperPrin() {
        // Required empty public constructor
    }

    public FragmentHomeSuperPrin(int i) {
        mPosition = i;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_super_prin, container, false);
        spinner_vendedor = (Spinner) view.findViewById(R.id.spinner_vendedor);
        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);
        loadVendedor();
        FloatingActionButton btn_buscar = (FloatingActionButton) view.findViewById(R.id.buscar_vendedor);
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashBoardVendedor();
            }
        });
        return view;
    }

    private void dashBoardVendedor() {

        if(vendedor != 0)
        {
            FragmentManager fManager = getFragmentManager();
            fragmentHomeSuper = new FragmentHomeSuper();
            Bundle args = new Bundle();
            args.putInt("id_vendedor", vendedor);
            fragmentHomeSuper.setArguments(args);
            fManager.beginTransaction().replace(R.id.contentPanel, fragmentHomeSuper).commit();
        }
        else
        {
            Toast.makeText(getActivity(),"Por favor selecciona un vendedor",Toast.LENGTH_LONG).show();
        }

    }

    private void loadVendedor() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cargar_vendedores_supervisor");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        CargarVendedores(response);
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
                params.put("inicio", String.valueOf(1));

                return params;

            }
        };
        addToQueue(jsonRequest);
    }

    private void CargarVendedores(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                final ListCategoria listCategoria = gson.fromJson(response, ListCategoria.class);
                ArrayAdapter<CategoriasEstandar> adapterEstado = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, listCategoria);
                spinner_vendedor.setAdapter(adapterEstado);
                spinner_vendedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        vendedor = listCategoria.get(position).getId();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }

                });

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        } else {
            alertDialog.dismiss();
            Toast.makeText(getContext(), "No se encontraron datos para mostrar", Toast.LENGTH_SHORT).show();
        }
    }

}
