package android.dcsdealerperu.com.dealerperu.Fragment;


import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseCreatePunt;
import android.dcsdealerperu.com.dealerperu.Entry.Subcategorias;
import android.dcsdealerperu.com.dealerperu.Entry.Territorio;
import android.dcsdealerperu.com.dealerperu.Entry.Zona;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.dcsdealerperu.com.dealerperu.R;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentReferencia extends BaseVolleyFragment implements View.OnClickListener {

    private static final String DESCRIBABLE_KEY = "describable_key";
    private Spinner spinner_estado_comercial;
    private Spinner spinner_circuito;
    private Spinner spinner_ruta;
    private Spinner spinner_categoria;
    private Spinner spinner_sub_categoria;
    private ResponseCreatePunt responseCreatePunt;
    private EditText edit_codigo_cum;
    private EditText edit_referencia;
    private Button btn_guardar;
    private Button btn_regresar_ref;

    private SpotsDialog alertDialog;

    public static FragmentReferencia newInstance(ResponseCreatePunt describable) {
        FragmentReferencia fragment = new FragmentReferencia();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DESCRIBABLE_KEY, describable);
        fragment.setArguments(bundle);

        return fragment;
    }

    public FragmentReferencia() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_referencia, container, false);

        spinner_estado_comercial = (Spinner) view.findViewById(R.id.spinner_estado_comercial);
        spinner_circuito = (Spinner) view.findViewById(R.id.spinner_circuito);
        spinner_ruta = (Spinner) view.findViewById(R.id.spinner_ruta);
        spinner_categoria = (Spinner) view.findViewById(R.id.spinner_categoria);
        spinner_sub_categoria = (Spinner) view.findViewById(R.id.spinner_sub_categoria);
        edit_codigo_cum = (EditText) view.findViewById(R.id.edit_codigo_cum);
        edit_referencia = (EditText) view.findViewById(R.id.edit_referencia);

        btn_guardar = (Button) view.findViewById(R.id.btn_guardar);
        btn_guardar.setOnClickListener(this);
        btn_regresar_ref = (Button) view.findViewById(R.id.btn_guardar);
        btn_regresar_ref.setOnClickListener(this);

        responseCreatePunt = (ResponseCreatePunt) getArguments().getSerializable(DESCRIBABLE_KEY);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadComercial(responseCreatePunt.getEstadoComunList());
        loadCircuito(responseCreatePunt.getTerritorioList());
        loadCategoria(responseCreatePunt.getCategoriasList());

    }

    private void loadCategoria(final List<CategoriasEstandar> categoriasList) {
        ArrayAdapter<CategoriasEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, categoriasList);
        spinner_categoria.setAdapter(prec3);
        spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                loadSubCategoria(categoriasList.get(position).getListSubCategoria());

                /*estado_ciudad_poblado = tipoCiudadList.get(position).getId();
                if (estado_ciudad_poblado == 0) {
                    liner_ciudad_poblado.setVisibility(View.GONE);
                    data12 = "";
                    data13 = "";
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                } else {
                    liner_ciudad_poblado.setVisibility(View.VISIBLE);
                    data12 = tipoCiudadList.get(position).getSiglas();
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadSubCategoria(List<Subcategorias> listSubCategoria) {
        ArrayAdapter<Subcategorias> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, listSubCategoria);
        spinner_sub_categoria.setAdapter(prec3);
        spinner_sub_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*estado_ciudad_poblado = tipoCiudadList.get(position).getId();
                if (estado_ciudad_poblado == 0) {
                    liner_ciudad_poblado.setVisibility(View.GONE);
                    data12 = "";
                    data13 = "";
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                } else {
                    liner_ciudad_poblado.setVisibility(View.VISIBLE);
                    data12 = tipoCiudadList.get(position).getSiglas();
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadCircuito(final List<Territorio> territorioList) {

        ArrayAdapter<Territorio> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, territorioList);
        spinner_circuito.setAdapter(prec3);
        spinner_circuito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                loadRuta(territorioList.get(position).getZonaList());

                /*estado_ciudad_poblado = tipoCiudadList.get(position).getId();
                if (estado_ciudad_poblado == 0) {
                    liner_ciudad_poblado.setVisibility(View.GONE);
                    data12 = "";
                    data13 = "";
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                } else {
                    liner_ciudad_poblado.setVisibility(View.VISIBLE);
                    data12 = tipoCiudadList.get(position).getSiglas();
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadRuta(List<Zona> zonaList) {

        ArrayAdapter<Zona> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, zonaList);
        spinner_ruta.setAdapter(prec3);
        spinner_ruta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*estado_ciudad_poblado = tipoCiudadList.get(position).getId();
                if (estado_ciudad_poblado == 0) {
                    liner_ciudad_poblado.setVisibility(View.GONE);
                    data12 = "";
                    data13 = "";
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                } else {
                    liner_ciudad_poblado.setVisibility(View.VISIBLE);
                    data12 = tipoCiudadList.get(position).getSiglas();
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadComercial(List<CategoriasEstandar> estadoComunList) {

        ArrayAdapter<CategoriasEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, estadoComunList);
        spinner_estado_comercial.setAdapter(prec3);
        spinner_estado_comercial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*estado_ciudad_poblado = tipoCiudadList.get(position).getId();
                if (estado_ciudad_poblado == 0) {
                    liner_ciudad_poblado.setVisibility(View.GONE);
                    data12 = "";
                    data13 = "";
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                } else {
                    liner_ciudad_poblado.setVisibility(View.VISIBLE);
                    data12 = tipoCiudadList.get(position).getSiglas();
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_guardar:



                break;

            case R.id.btn_regresar_ref:
                break;
        }
    }

    private void setupGrid() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "cargar_filtros_puntos");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(final String response) {
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener(){
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
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());

                return params;

            }
        };

        addToQueue(jsonRequest);

    }

    private void parseJSON(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                responseCreatePunt = gson.fromJson(response, ResponseCreatePunt.class);


            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        }
    }
}
