package android.dcsdealerperu.com.dealerperu.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.dcsdealerperu.com.dealerperu.Activity.ActResponAvanBusqueda;
import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.GuardarEditarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.RequesGuardarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseCreatePunt;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseInsert;
import android.dcsdealerperu.com.dealerperu.Entry.Subcategorias;
import android.dcsdealerperu.com.dealerperu.Entry.Territorio;
import android.dcsdealerperu.com.dealerperu.Entry.Zona;
import android.dcsdealerperu.com.dealerperu.R;
import android.dcsdealerperu.com.dealerperu.Services.GpsServices;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.RequesGuardarPunto.getRequesGuardarPuntoStatic;
import static android.dcsdealerperu.com.dealerperu.Entry.RequesGuardarPunto.setRequesGuardarPuntoStatic;
import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

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
    private int estado_comercial;
    private int estado_categoria;
    private int estado_sub_categoria;
    private int estado_territorio;
    private int estado_ruta;
    private GpsServices gpsServices;

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

        gpsServices = new GpsServices(getActivity());

        return view;
    }

    /*private void dataEditReference() {

        setEstadoComercial(responseCreatePunt.getEstadoComunList(), spinner_estado_comercial, getDataDireccionFormStatic().getRequestGuardarEditarPunto().getEstado_com());
        setCircuito(responseCreatePunt.getTerritorioList(), spinner_circuito, getDataDireccionFormStatic().getRequestGuardarEditarPunto().getTerritorio());
        setRuta(responseCreatePunt.getTerritorioList(), spinner_ruta, getDataDireccionFormStatic().getRequestGuardarEditarPunto().getZona());
        setCategoria(responseCreatePunt.getCategoriasList(), spinner_categoria, getDataDireccionFormStatic().getRequestGuardarEditarPunto().getCategoria());
        setSubCategoria(responseCreatePunt.getCategoriasList(), spinner_sub_categoria, getDataDireccionFormStatic().getRequestGuardarEditarPunto().getSubcategoria());

        edit_referencia.setText(getDataDireccionFormStatic().getRequestGuardarEditarPunto().getRef_direccion());

    }*/

    private void setSubCategoria(List<CategoriasEstandar> categoriasList, Spinner spinner_sub_categoria, int subcategoria) {
        for(int i = 0; i < categoriasList.size(); i++) {
            for(int a = 0; a < categoriasList.get(i).getListSubCategoria().size(); a++) {
                if(categoriasList.get(i).getListSubCategoria().get(a).getId() == subcategoria) {
                    spinner_sub_categoria.setSelection(i);
                    break;
                }
            }
        }
    }

    private void setCategoria(List<CategoriasEstandar> categoriasList, Spinner spinner_categoria, int categoria) {
        for(int i = 0; i < categoriasList.size(); i++) {
            if(categoriasList.get(i).getId() == categoria) {
                spinner_categoria.setSelection(i);
                break;
            }
        }
    }

    private void setRuta(List<Territorio> territorioList, Spinner spinner_ruta, int zona) {
        for(int i = 0; i < territorioList.size(); i++) {
            for(int a = 0; a < territorioList.get(i).getZonaList().size(); a++) {
                if(territorioList.get(i).getZonaList().get(a).getId() == zona) {
                    spinner_ruta.setSelection(i);
                    break;
                }
            }
        }
    }

    private void setCircuito(List<Territorio> territorioList, Spinner spinner_circuito, int territorio) {
        for(int i = 0; i < territorioList.size(); i++) {
            if(territorioList.get(i).getId() == territorio) {
                spinner_circuito.setSelection(i);
                break;
            }
        }
    }

    private void setEstadoComercial(List<CategoriasEstandar> estadoComunList, Spinner spinner_estado_comercial, int estado_com) {
        for(int i = 0; i < estadoComunList.size(); i++) {
            if(estadoComunList.get(i).getId() == estado_com) {
                spinner_estado_comercial.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*loadComercial(responseCreatePunt.getEstadoComunList());
        loadCircuito(responseCreatePunt.getTerritorioList());
        loadCategoria(responseCreatePunt.getCategoriasList());

        if (getDataDireccionFormStatic().getAccion().equals("Editar")) {
            dataEditReference();
        }*/

    }

    private void loadCategoria(final List<CategoriasEstandar> categoriasList) {
        ArrayAdapter<CategoriasEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, categoriasList);
        spinner_categoria.setAdapter(prec3);
        spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                loadSubCategoria(categoriasList.get(position).getListSubCategoria());

                estado_categoria = categoriasList.get(position).getId();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadSubCategoria(final List<Subcategorias> listSubCategoria) {
        ArrayAdapter<Subcategorias> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, listSubCategoria);
        spinner_sub_categoria.setAdapter(prec3);
        spinner_sub_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                estado_sub_categoria = listSubCategoria.get(position).getId();

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

                estado_territorio = territorioList.get(position).getId();

                loadRuta(territorioList.get(position).getZonaList());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadRuta(final List<Zona> zonaList) {

        ArrayAdapter<Zona> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, zonaList);
        spinner_ruta.setAdapter(prec3);
        spinner_ruta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                estado_ruta = zonaList.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadComercial(final List<CategoriasEstandar> estadoComunList) {

        ArrayAdapter<CategoriasEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, estadoComunList);
        spinner_estado_comercial.setAdapter(prec3);
        spinner_estado_comercial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                estado_comercial = estadoComunList.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_guardar:

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                dialogo1.setTitle("Confirmar");
                dialogo1.setMessage("¿ Desea guardar los datos del punto ?");
                dialogo1.setCancelable(false);

                dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        //setupGrid();
                    }

                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });

                dialogo1.show();

                break;

            case R.id.btn_regresar_ref:
                break;
        }
    }

    /*private void setupGrid() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_punto");
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

                GuardarEditarPunto gep = new GuardarEditarPunto();

                gep.setNombre_punto(getRequesGuardarPuntoStatic().getNombre_punto());
                gep.setTipo_documento(getRequesGuardarPuntoStatic().getTipo_documento());
                gep.setCedula(getRequesGuardarPuntoStatic().getCedula());
                gep.setNombre_cliente(getRequesGuardarPuntoStatic().getNombre_cliente());
                gep.setEmail(getRequesGuardarPuntoStatic().getEmail().trim());

                gep.setTelefono(getRequesGuardarPuntoStatic().getTelefono());

                gep.setCelular(getRequesGuardarPuntoStatic().getCelular());
                gep.setVende_recargas(getRequesGuardarPuntoStatic().getVenta_recarga());

                gep.setDepto(getDataDireccionFormStatic().getDepartamento());
                gep.setCiudad(getDataDireccionFormStatic().getProvincia());
                gep.setDistrito(getDataDireccionFormStatic().getDistrito());
                gep.setTexto_direccion(getDataDireccionFormStatic().getDir_concatenada());

                int valor;

                if (isValidNumber(edit_codigo_cum.getText().toString().trim()))
                    valor = 0;
                else
                    valor = Integer.parseInt(edit_codigo_cum.getText().toString().trim());

                gep.setCodigo_cum(valor);

                gep.setTipo_via(getDataDireccionFormStatic().getTipo_via());
                gep.setNombre_via(getDataDireccionFormStatic().getNombre_via());
                gep.setNro_via(getDataDireccionFormStatic().getNumero_puesta());
                gep.setNombre_mzn(getDataDireccionFormStatic().getNombre_manzana());
                gep.setLote(getDataDireccionFormStatic().getLote());
                gep.setTipo_interior(getDataDireccionFormStatic().getTipo_interior());
                gep.setNro_interior(getDataDireccionFormStatic().getNombre_interior());
                gep.setTipo_vivienda(getDataDireccionFormStatic().getTipo_vivienda());
                gep.setDescripcion_vivienda(getDataDireccionFormStatic().getNombre_vivienda());
                gep.setTipo_urbanizacion(getDataDireccionFormStatic().getTipo_urbanizacion());
                gep.setNum_int_urbanizacion(getDataDireccionFormStatic().getNombre_urbanizacion());
                gep.setTipo_ciudad(getDataDireccionFormStatic().getCiudad_prueba());
                gep.setDes_tipo_ciudad(getDataDireccionFormStatic().getNombre_ciudad_prueba());

                gep.setEstado_com(estado_comercial);
                gep.setCategoria(estado_categoria);
                gep.setSubcategoria(estado_sub_categoria);
                gep.setTerritorio(estado_territorio);
                gep.setZona(estado_ruta);
                gep.setRef_direccion(edit_referencia.getText().toString());

                String parJSON = new Gson().toJson(gep, GuardarEditarPunto.class);

                params.put("datos", parJSON);
                params.put("idpos", String.valueOf(getDataDireccionFormStatic().getEditaPunto()));
                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));
                params.put("latitud", String.valueOf(gpsServices.getLatitude()));
                params.put("longitud", String.valueOf(gpsServices.getLongitude()));

                params.put("accion", getDataDireccionFormStatic().getAccion());

                return params;
            }
        };

        addToQueue(jsonRequest);
    }

    private boolean isValidNumber(String number){return number == null || number.length() == 0;}

    private void parseJSON(String response) {
        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                Charset.forName("UTF-8").encode(response);

                byte ptext[] = response.getBytes(Charset.forName("ISO-8859-1"));

                String value = new String(ptext, Charset.forName("UTF-8"));

                final ResponseInsert responseInsert = gson.fromJson(value, ResponseInsert.class);

                if (responseInsert.getId() == 0) {

                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                    dialogo1.setTitle(responseInsert.getMsg());
                    dialogo1.setMessage(getRequesGuardarPuntoStatic().getNombre_punto() + "\n" + "ID POS: " +responseInsert.getIdpos());
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {

                            RequesGuardarPunto requesGuardarPunto = new RequesGuardarPunto();
                            requesGuardarPunto = null;
                            setRequesGuardarPuntoStatic(requesGuardarPunto);

                        }
                    });
                    dialogo1.setNegativeButton("Vender", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            //Vender ();

                            ActResponAvanBusqueda actBusqueda =  new ()ActResponAvanBusqueda();

                            actBusqueda.buscarIdPos(Integer.parseInt(responseInsert.getIdpos()));

                            RequesGuardarPunto requesGuardarPunto = new RequesGuardarPunto();
                            requesGuardarPunto = null;
                            setRequesGuardarPuntoStatic(requesGuardarPunto);
                        }
                    });

                    dialogo1.show();

                } else if (responseInsert.getId() == -1) {
                    Toast.makeText(getActivity(), responseInsert.getMsg(), Toast.LENGTH_LONG).show();
                }

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        }
        else {
            alertDialog.dismiss();
        }
    }*/
}
