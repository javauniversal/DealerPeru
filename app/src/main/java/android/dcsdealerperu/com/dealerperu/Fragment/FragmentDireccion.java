package android.dcsdealerperu.com.dealerperu.Fragment;

import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.Ciudad;
import android.dcsdealerperu.com.dealerperu.Entry.DataDireccionForm;
import android.dcsdealerperu.com.dealerperu.Entry.Departamentos;
import android.dcsdealerperu.com.dealerperu.Entry.Distrito;
import android.dcsdealerperu.com.dealerperu.Entry.RequestGuardarEditarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseCreatePunt;
import android.dcsdealerperu.com.dealerperu.Entry.TipoCiudad;
import android.dcsdealerperu.com.dealerperu.Entry.TipoInterior;
import android.dcsdealerperu.com.dealerperu.Entry.TipoUrbanizacion;
import android.dcsdealerperu.com.dealerperu.Entry.TipoVia;
import android.dcsdealerperu.com.dealerperu.Entry.TipoVivienda;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class FragmentDireccion extends BaseVolleyFragment implements View.OnClickListener {

    //region Variables Globales
    private Button btn_siguiente_dir;
    private Button btn_regresar_dir;
    private FragmentReferencia fragmentReferencia;
    private Spinner spinner_departamento;
    private Spinner spinner_provincia;
    private Spinner spinner_distrito;
    private Spinner spinner_tipo;
    private Spinner spinner_tipo_interior;
    private Spinner spinner_urbanizacion;
    private Spinner spinner_ciudad_poblado;
    private Spinner spinner_tipo_vivienda;
    private SpotsDialog alertDialog;
    private LinearLayout layouttipovia;
    private LinearLayout liner_tipo_vivienda;
    private LinearLayout liner_tipo_interior;
    private LinearLayout liner_urbanizacion;
    private LinearLayout liner_ciudad_poblado;
    private int estado_cliente;
    private EditText edit_numero_interior;
    private EditText edit_numero_vivienda;
    private EditText edit_urbanizacion;
    private EditText edit_nombre_via;
    private EditText edit_numero_puerta;
    private EditText edit_manzana;
    private EditText edit_lote;
    private EditText edit_descripcion;
    private TextView direccionConcat;
    private String data1 = "", data2 = "", data3 = "", data4 = "", data5 = "", data6 = "", data7 = "", data8 = "", data9 = "", data10 = "", data11 = "", data12 = "", data13 = "", accion = "";
    private int estado_vivienda;
    private int estado_interior;
    private int estado_urbanizacion;
    private int estado_ciudad_poblado;
    private int departamento, ciudad_pro, distrito, editaPunto = 0;
    ResponseCreatePunt responseCreatePunt;
    private RequestGuardarEditarPunto requestGuardarEditarPunto = new RequestGuardarEditarPunto();
    //endregion

    public FragmentDireccion() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_direccion, container, false);

        if(getArguments() != null) {
            editaPunto = getArguments().getInt("edit_punto");
            accion = getArguments().getString("accion");
            requestGuardarEditarPunto = (RequestGuardarEditarPunto) getArguments().getSerializable("datos_punto");
        }

        btn_siguiente_dir = (Button) view.findViewById(R.id.btn_siguiente_dir);
        btn_regresar_dir = (Button) view.findViewById(R.id.btn_regresar_dir);
        spinner_departamento = (Spinner) view.findViewById(R.id.spinner_departamento);
        spinner_provincia = (Spinner) view.findViewById(R.id.spinner_provincia);
        spinner_distrito = (Spinner) view.findViewById(R.id.spinner_distrito);
        spinner_tipo = (Spinner) view.findViewById(R.id.spinner_tipo);
        spinner_tipo_interior = (Spinner) view.findViewById(R.id.spinner_tipo_interior);
        spinner_urbanizacion = (Spinner) view.findViewById(R.id.spinner_urbanizacion);
        spinner_ciudad_poblado = (Spinner) view.findViewById(R.id.spinner_ciudad_poblado);
        spinner_tipo_vivienda = (Spinner) view.findViewById(R.id.spinner_tipo_vivienda);
        layouttipovia = (LinearLayout) view.findViewById(R.id.layouttipovia);
        liner_tipo_vivienda = (LinearLayout) view.findViewById(R.id.liner_tipo_vivienda);
        liner_tipo_interior = (LinearLayout) view.findViewById(R.id.liner_tipo_interior);
        liner_urbanizacion = (LinearLayout) view.findViewById(R.id.liner_urbanizacion);
        liner_ciudad_poblado = (LinearLayout) view.findViewById(R.id.liner_ciudad_poblado);
        edit_numero_interior = (EditText) view.findViewById(R.id.edit_numero_interior);
        edit_numero_vivienda = (EditText) view.findViewById(R.id.edit_numero_vivienda);
        edit_urbanizacion = (EditText) view.findViewById(R.id.edit_urbanizacion);
        edit_nombre_via = (EditText) view.findViewById(R.id.edit_nombre_via);
        edit_numero_puerta = (EditText) view.findViewById(R.id.edit_numero_puerta);
        edit_manzana = (EditText) view.findViewById(R.id.edit_manzana);
        edit_lote = (EditText) view.findViewById(R.id.edit_lote);
        direccionConcat = (TextView) view.findViewById(R.id.direccionConcat);
        edit_descripcion = (EditText) view.findViewById(R.id.edit_descripcion);

        edit_nombre_via.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                data2 = s.toString();
                ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
            }
        });

        edit_numero_puerta.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                data3 = s.toString();
                ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
            }
        });

        edit_manzana.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                data4 = s.toString();
                ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
            }

        });

        edit_lote.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                data5 = s.toString();
                ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
            }

        });

        edit_numero_vivienda.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                data7 = s.toString();
                ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
            }
        });

        edit_numero_interior.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                data9 = s.toString();
                ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
            }

        });

        edit_urbanizacion.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                data11 = s.toString();
                ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
            }

        });

        edit_descripcion.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                data13 = s.toString();
                ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
            }

        });

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_siguiente_dir.setOnClickListener(this);
        btn_regresar_dir.setOnClickListener(this);

        setupGrid();

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

                loadDepartamento(responseCreatePunt);
                loadVia(responseCreatePunt.getNomenclaturaList().getTipoViaList());
                loadInterior(responseCreatePunt.getNomenclaturaList().getTipoInteriorList());
                loadUrbanizacion(responseCreatePunt.getNomenclaturaList().getTipoUrbanizacionList());
                loadCiudadTipo(responseCreatePunt.getNomenclaturaList().getTipoCiudadList());
                loadTipoVivienda(responseCreatePunt.getNomenclaturaList().getTipoViviendaList());

                // Entra Para ver si va a editar el punto..!
                if(accion.equals("Editar")) {
                    CargarDatosEditaPunto();
                }
            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        }
    }

    private void CargarDatosEditaPunto() {
        SetearSpinners();
    }

    private void SetearSpinners() {

        setSpinerDepartamento(responseCreatePunt.getDepartamentosList(), spinner_departamento, requestGuardarEditarPunto.getDepto());

        setSpinerProvincia(responseCreatePunt.getDepartamentosList(), spinner_provincia, requestGuardarEditarPunto.getCiudad());

        setSpinerDistrito(responseCreatePunt.getDepartamentosList(), spinner_provincia, requestGuardarEditarPunto.getDistrito());

        setSpinerTipoVia(responseCreatePunt.getNomenclaturaList().getTipoViaList(), spinner_tipo, requestGuardarEditarPunto.getViaEnt());

        setSpinerTipoViVienda(responseCreatePunt.getNomenclaturaList().getTipoViviendaList(), spinner_tipo_vivienda, requestGuardarEditarPunto.getVivendaEnt());

        setSpinerTipoInterior(responseCreatePunt.getNomenclaturaList().getTipoInteriorList(), spinner_tipo_interior, requestGuardarEditarPunto.getInterEnt());

        setSpinerUrbanizacion(responseCreatePunt.getNomenclaturaList().getTipoUrbanizacionList(), spinner_urbanizacion, requestGuardarEditarPunto.getUrbaEnt());

        setSpinerCiuPoblado(responseCreatePunt.getNomenclaturaList().getTipoCiudadList(), spinner_ciudad_poblado, requestGuardarEditarPunto.getCiuEnt());

    }

    private void setSpinerCiuPoblado(List<TipoCiudad> tipoCiudadList, Spinner spinner_ciudad_poblado, CategoriasEstandar ciuEnt) {
        for(int i = 0; i < tipoCiudadList.size(); i++) {
            if(tipoCiudadList.get(i).getId() == ciuEnt.getId()) {
                spinner_ciudad_poblado.setSelection(i);
                break;
            }
        }
    }

    private void setSpinerUrbanizacion(List<TipoUrbanizacion> tipoUrbanizacionList, Spinner spinner_urbanizacion, CategoriasEstandar urbaEnt) {
        for(int i = 0; i < tipoUrbanizacionList.size(); i++) {
            if(tipoUrbanizacionList.get(i).getId() == urbaEnt.getId()) {
                spinner_urbanizacion.setSelection(i);
                break;
            }
        }
    }

    private void setSpinerTipoInterior(List<TipoInterior> tipoInteriorList, Spinner spinner_tipo_interior, CategoriasEstandar interEnt) {
        for(int i = 0; i < tipoInteriorList.size(); i++) {
            if(tipoInteriorList.get(i).getId() == interEnt.getId()) {
                spinner_tipo_interior.setSelection(i);
                break;
            }
        }
    }

    private void setSpinerTipoViVienda(List<TipoVivienda> tipoViviendaList, Spinner spinner_tipo_vivienda, CategoriasEstandar vivendaEnt) {
        for(int i = 0; i < tipoViviendaList.size(); i++) {
            if(tipoViviendaList.get(i).getId() == vivendaEnt.getId()) {
                spinner_tipo_vivienda.setSelection(i);
                break;
            }
        }
    }

    private void setSpinerTipoVia(List<TipoVia> tipoViaList, Spinner spinner_tipo, CategoriasEstandar tipo_via) {
        for(int i = 0; i < tipoViaList.size(); i++) {
            if(tipoViaList.get(i).getId() == tipo_via.getId()) {
                spinner_tipo.setSelection(i);
                break;
            }
        }
    }

    private void setSpinerDepartamento(List<Departamentos> departamentosList, Spinner spinner, int id) {
        for(int i = 0; i < departamentosList.size(); i++) {
            if(departamentosList.get(i).getId() == id) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void setSpinerProvincia(List<Departamentos> departamentosList, Spinner spinner, int id_pro) {

        for(int i = 0; i < departamentosList.size(); i++) {
            for (int f = 0; f < departamentosList.get(i).getCiudadList().size(); f++) {
                if(departamentosList.get(i).getCiudadList().get(f).getId() == id_pro) {
                    spinner.setSelection(f);
                    break;
                }
            }

        }
    }

    private void setSpinerDistrito(List<Departamentos> departamentosList, Spinner spinner, int id_depto) {

        for(int i = 0; i < departamentosList.size(); i++) {
            for(int t = 0; t < departamentosList.get(i).getCiudadList().size(); t++) {
                for (int f = 0; f < departamentosList.get(i).getCiudadList().get(t).getDistritoList().size(); f++) {
                    if(departamentosList.get(i).getCiudadList().get(t).getDistritoList().get(f).getId() == id_depto) {
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    private void loadTipoVivienda(final List<TipoVivienda> tipoViviendaList) {

        ArrayAdapter<TipoVivienda> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, tipoViviendaList);
        spinner_tipo_vivienda.setAdapter(prec3);
        spinner_tipo_vivienda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                estado_vivienda = tipoViviendaList.get(position).getId();

                if (estado_vivienda == 0) {
                    liner_tipo_vivienda.setVisibility(View.GONE);
                    data6 = "";
                    data7 = "";
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                } else {
                    liner_tipo_vivienda.setVisibility(View.VISIBLE);
                    data6 = tipoViviendaList.get(position).getSiglas();
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);

                    if(accion.equals("Editar")) {
                        edit_numero_vivienda.setText(String.format("%1$s", requestGuardarEditarPunto.getVivendaEnt().descripcion));
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadCiudadTipo(final List<TipoCiudad> tipoCiudadList) {

        ArrayAdapter<TipoCiudad> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, tipoCiudadList);
        spinner_ciudad_poblado.setAdapter(prec3);
        spinner_ciudad_poblado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                estado_ciudad_poblado = tipoCiudadList.get(position).getId();
                if (estado_ciudad_poblado == 0) {
                    liner_ciudad_poblado.setVisibility(View.GONE);
                    data12 = "";
                    data13 = "";
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                } else {
                    liner_ciudad_poblado.setVisibility(View.VISIBLE);
                    data12 = tipoCiudadList.get(position).getSiglas();
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);

                    if(accion.equals("Editar")) {
                        edit_descripcion.setText(String.format("%1$s", requestGuardarEditarPunto.getUrbaEnt().descripcion));
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }

    private void loadUrbanizacion(final List<TipoUrbanizacion> tipoUrbanizacionList) {

        ArrayAdapter<TipoUrbanizacion> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, tipoUrbanizacionList);
        spinner_urbanizacion.setAdapter(prec3);
        spinner_urbanizacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                estado_urbanizacion = tipoUrbanizacionList.get(position).getId();
                if (estado_urbanizacion == 0) {
                    liner_urbanizacion.setVisibility(View.GONE);
                    data10 = "";
                    data11 = "";
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                } else {
                    liner_urbanizacion.setVisibility(View.VISIBLE);
                    data10 = tipoUrbanizacionList.get(position).getSiglas();
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);

                    if(accion.equals("Editar")) {
                        edit_urbanizacion.setText(String.format("%1$s", requestGuardarEditarPunto.getUrbaEnt().descripcion));
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }

    private void loadInterior(final List<TipoInterior> tipoInteriorList) {
        ArrayAdapter<TipoInterior> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, tipoInteriorList);
        spinner_tipo_interior.setAdapter(prec3);
        spinner_tipo_interior.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                estado_interior = tipoInteriorList.get(position).getId();
                if (estado_interior == 0) {
                    liner_tipo_interior.setVisibility(View.GONE);
                    data8 = "";
                    data9 = "";
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                } else {
                    liner_tipo_interior.setVisibility(View.VISIBLE);
                    data8 = tipoInteriorList.get(position).getSiglas();
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);

                    if(accion.equals("Editar")) {
                        edit_numero_interior.setText(String.format("%1$s", requestGuardarEditarPunto.getInterEnt().descripcion));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadVia(final List<TipoVia> estadoComunList) {

        ArrayAdapter<TipoVia> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, estadoComunList);
        spinner_tipo.setAdapter(prec3);
        spinner_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estado_cliente = estadoComunList.get(position).getId();

                if (estado_cliente == 0) {
                    layouttipovia.setVisibility(View.GONE);
                    data1 = "";
                    data2 = "";
                    data3 = "";
                    data4 = "";
                    data5 = "";
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);
                } else {
                    layouttipovia.setVisibility(View.VISIBLE);
                    data1 = estadoComunList.get(position).getSiglas();
                    ConcatProducts(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13);

                    if(accion.equals("Editar")) {

                        edit_nombre_via.setText(String.format("%1$s", requestGuardarEditarPunto.getViaEnt().descripcion));
                        edit_numero_puerta.setText(String.format("%1$s", requestGuardarEditarPunto.getViaEnt().descripcion1));
                        edit_manzana.setText(String.format("%1$s", requestGuardarEditarPunto.getViaEnt().descripcion2));
                        edit_lote.setText(String.format("%1$s", requestGuardarEditarPunto.getViaEnt().descripcion3));

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }

    private void loadDepartamento(final ResponseCreatePunt responseCreatePunt) {

        ArrayAdapter<Departamentos> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, responseCreatePunt.getDepartamentosList());
        spinner_departamento.setAdapter(prec3);
        spinner_departamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departamento = responseCreatePunt.getDepartamentosList().get(position).getId();
                loadCiudad(responseCreatePunt.getDepartamentosList().get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadCiudad(final Departamentos departamentos) {

        ArrayAdapter<Ciudad> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, departamentos.getCiudadList());
        spinner_provincia.setAdapter(prec3);
        spinner_provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ciudad_pro = departamentos.getCiudadList().get(position).getId();
                loadDistrito(departamentos.getCiudadList().get(position).getDistritoList());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadDistrito(final List<Distrito> distritoList) {

        ArrayAdapter<Distrito> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, distritoList);
        spinner_distrito.setAdapter(prec3);
        spinner_distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                distrito = distritoList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_siguiente_dir:

                if (!validarCampos()) {

                    DataDireccionForm objet = new DataDireccionForm();

                    /*objet.setDepartamento(departamento);
                    objet.setProvincia(ciudad_pro);
                    objet.setDistrito(distrito);
                    objet.setTipo_via(estado_cliente);
                    objet.setNombre_via(edit_nombre_via.getText().toString());
                    objet.setNombre_manzana(edit_manzana.getText().toString());

                    int valor;

                    if (isValidNumber(edit_numero_puerta.getText().toString().trim()))
                        valor = 0;
                    else
                        valor = Integer.parseInt(edit_numero_puerta.getText().toString().trim());

                    objet.setNumero_puesta(valor);

                    objet.setLote(edit_lote.getText().toString());
                    objet.setTipo_vivienda(estado_vivienda);
                    objet.setNombre_vivienda(edit_numero_vivienda.getText().toString());
                    objet.setTipo_interior(estado_interior);
                    objet.setNombre_interior(edit_numero_interior.getText().toString());
                    objet.setTipo_urbanizacion(estado_urbanizacion);
                    objet.setNombre_urbanizacion(edit_urbanizacion.getText().toString());
                    objet.setCiudad_prueba(estado_ciudad_poblado);
                    objet.setNombre_ciudad_prueba(edit_descripcion.getText().toString());
                    objet.setDir_concatenada(direccionConcat.getText().toString());

                    objet.setAccion(accion);
                    objet.setEditaPunto(editaPunto);

                    objet.setRequestGuardarEditarPunto(requestGuardarEditarPunto);

                    DataDireccionForm.setDataDireccionFormStatic(objet);

                    FragmentManager fManager = getFragmentManager();
                    fragmentReferencia = new FragmentReferencia();
                    fManager.beginTransaction().replace(R.id.contentPanel, fragmentReferencia.newInstance(responseCreatePunt)).commit();

                }

                break;

            case R.id.btn_regresar_dir:

                FragmentManager fManager = getFragmentManager();
                FragmentDatosPersonales fragmentDatosPersonales = new FragmentDatosPersonales();
                if(accion.equals("Editar")) {
                    Bundle args = new Bundle();
                    args.putSerializable("datos_punto", requestGuardarEditarPunto);
                    args.putInt("idpos", editaPunto);
                    args.putString("accion", accion);
                    fragmentDatosPersonales.setArguments(args);
                }

                fManager.beginTransaction().replace(R.id.contentPanel, fragmentDatosPersonales).commit();
                */

                }
                break;

        }
    }

    private boolean isValidNumber(String number){return number == null || number.length() == 0;}

    private boolean isValidTipoVia() {

        if (estado_cliente == 0) {
            if (estado_vivienda == 0 && estado_interior == 0 && estado_urbanizacion == 0)
                return true;
        }

        return false;
    }

    private boolean isValidTipoVia2(String trim) {

        if (estado_cliente != 0) {
            if (isValidNumber(trim)) {
                return true;
            }
        }

        return false;
    }

    public boolean validarCampos() {

        boolean indicadorValidate = false;

        if (isValidTipoVia()) {
            Toast.makeText(getActivity(), "Ingrese datos en algunas de estas opciones Vivienda, Interior o Urbanización", Toast.LENGTH_LONG).show();
            indicadorValidate = true;
        } else if(isValidTipoVia2(edit_nombre_via.getText().toString().trim())) {
            edit_nombre_via.setFocusable(true);
            edit_nombre_via.setFocusableInTouchMode(true);
            edit_nombre_via.requestFocus();
            edit_nombre_via.setText("");
            edit_nombre_via.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidTipoVia2(edit_numero_puerta.getText().toString().trim())) {
            edit_numero_puerta.setFocusable(true);
            edit_numero_puerta.setFocusableInTouchMode(true);
            edit_numero_puerta.requestFocus();
            edit_numero_puerta.setText("");
            edit_numero_puerta.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (liner_tipo_vivienda.getVisibility() == View.VISIBLE && isValidNumber(edit_numero_vivienda.getText().toString().trim())) {
            edit_numero_vivienda.setFocusable(true);
            edit_numero_vivienda.setFocusableInTouchMode(true);
            edit_numero_vivienda.requestFocus();
            edit_numero_vivienda.setText("");
            edit_numero_vivienda.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (liner_tipo_interior.getVisibility() == View.VISIBLE && isValidNumber(edit_numero_interior.getText().toString().trim())) {
            edit_numero_interior.setFocusable(true);
            edit_numero_interior.setFocusableInTouchMode(true);
            edit_numero_interior.requestFocus();
            edit_numero_interior.setText("");
            edit_numero_interior.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (liner_urbanizacion.getVisibility() == View.VISIBLE && isValidNumber(edit_urbanizacion.getText().toString().trim())) {
            edit_urbanizacion.setFocusable(true);
            edit_urbanizacion.setFocusableInTouchMode(true);
            edit_urbanizacion.requestFocus();
            edit_urbanizacion.setText("");
            edit_urbanizacion.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (liner_ciudad_poblado.getVisibility() == View.VISIBLE && isValidNumber(edit_descripcion.getText().toString().trim())) {
            edit_descripcion.setFocusable(true);
            edit_descripcion.setFocusableInTouchMode(true);
            edit_descripcion.requestFocus();
            edit_descripcion.setText("");
            edit_descripcion.setError("Este campo es obligatorio");
            indicadorValidate = true;
        }

        return indicadorValidate;

    }

    public void ConcatProducts(String data1, String data2, String data3, String data4, String data5, String data6,
                                 String data7, String data8, String data9, String data10 ,String data11, String data12, String data13) {

        StringBuilder value = new StringBuilder();

        if (!data1.isEmpty()) {
            value.append(data1);
        }
        if (!data2.isEmpty()) {
            value.append(" ");
            value.append(data2);
        }
        if (!data3.isEmpty()) {
            value.append(" ");
            value.append(data3);
        }
        if (!data4.isEmpty()) {
            value.append(" ");
            value.append(data4);
        }
        if (!data5.isEmpty()) {
            value.append(" ");
            value.append(data5);
        }
        if (!data6.isEmpty()) {
            value.append(" ");
            value.append(data6);
        }
        if (!data7.isEmpty()) {
            value.append(" ");
            value.append(data7);
        }
        if (!data8.isEmpty()) {
            value.append(" ");
            value.append(data8);
        }
        if (!data9.isEmpty()) {
            value.append(" ");
            value.append(data9);
        }
        if (!data10.isEmpty()) {
            value.append(" ");
            value.append(data10);
        }
        if (!data11.isEmpty()) {
            value.append(" ");
            value.append(data11);
        }
        if (!data12.isEmpty()) {
            value.append(" ");
            value.append(data12);
        }
        if (!data13.isEmpty()) {
            value.append(" ");
            value.append(data13);
        }

         direccionConcat.setText(value.toString());

    }
}
