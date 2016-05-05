package android.dcsdealerperu.com.dealerperu.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.Ciudad;
import android.dcsdealerperu.com.dealerperu.Entry.Departamentos;
import android.dcsdealerperu.com.dealerperu.Entry.RequesGuardarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseCreatePunt;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseInsert;
import android.dcsdealerperu.com.dealerperu.Entry.Territorio;
import android.dcsdealerperu.com.dealerperu.Entry.Zona;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class FragmentCrearPunto extends BaseVolleyFragment {

    private SpotsDialog alertDialog;
    private Spinner sp_departamentos;
    private Spinner sp_ciudades;
    private Spinner sp_tipo_via;
    private Spinner sp_otros;
    private Spinner sp_estado_comercial;
    private Spinner sp_circuito;
    private Spinner sp_zona_ruta;
    private Spinner sp_categoria;
    private FloatingActionButton floatingActionButton;
    private EditText edit_nombres;
    private EditText edit_cedula;
    private EditText edit_nom_cli;
    private EditText edit_correo_edit;
    private EditText edit_tel_edit;
    private EditText edit_cel_edit;
    private EditText edit_barrio;
    private EditText edit_a;
    private EditText edit_b;
    private EditText edit_c;
    private EditText edit_descripcion;
    private int departamento;
    private int ciudad;
    private int tipo_via;
    private int otros_dir;
    private int territorio;
    private int id_zona_sp;
    private int id_categoria;
    private int estado_cliente;

    public FragmentCrearPunto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_crear_punto, container, false);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        //Spinner...
        sp_departamentos = (Spinner) view.findViewById(R.id.spinner_departamento);
        sp_ciudades = (Spinner) view.findViewById(R.id.spinner_ciudad);
        sp_tipo_via = (Spinner) view.findViewById(R.id.spinner_tipo_via);
        sp_otros = (Spinner) view.findViewById(R.id.spinner_otros);
        sp_estado_comercial = (Spinner) view.findViewById(R.id.spinner_estado_comercial);
        sp_circuito = (Spinner) view.findViewById(R.id.spinner_circuito);
        sp_zona_ruta = (Spinner) view.findViewById(R.id.spinner_ruta);
        sp_categoria = (Spinner) view.findViewById(R.id.spinner_categoria);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_guardar);

        //EditText
        edit_nombres = (EditText) view.findViewById(R.id.edit_nombres);
        edit_cedula = (EditText) view.findViewById(R.id.edit_cedula);
        edit_nom_cli = (EditText) view.findViewById(R.id.edit_nom_cli);
        edit_correo_edit = (EditText) view.findViewById(R.id.edit_correo_edit);
        edit_tel_edit = (EditText) view.findViewById(R.id.edit_tel_edit);
        edit_cel_edit = (EditText) view.findViewById(R.id.edit_cel_edit);
        edit_barrio = (EditText) view.findViewById(R.id.edit_barrio);
        edit_a = (EditText) view.findViewById(R.id.edit_a);
        edit_b = (EditText) view.findViewById(R.id.edit_b);
        edit_c = (EditText) view.findViewById(R.id.edit_c);
        edit_descripcion = (EditText) view.findViewById(R.id.edit_descripcion);


        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupGrid();

        setHasOptionsMenu(true);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarCampos()) {

                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                    dialogo1.setTitle("Confirmar");
                    dialogo1.setMessage("¿ Desea guardar los datos del punto ?");
                    dialogo1.setCancelable(false);

                    dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            guardarData();
                        }

                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            dialogo1.dismiss();
                        }
                    });

                    dialogo1.show();

                }
            }
        });
    }

    private void guardarData() {
        alertDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_punto");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(final String response) {
                        parseJSONResponse(response);
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

                        onConnectionFailed(error_string);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                RequesGuardarPunto objeto = new RequesGuardarPunto();

                objeto.setNombre_punto(edit_nombres.getText().toString());
                objeto.setCedula(edit_cedula.getText().toString());
                objeto.setNombre_cliente(edit_nom_cli.getText().toString());
                objeto.setEmail(edit_correo_edit.getText().toString());
                objeto.setTelefono(edit_tel_edit.getText().toString());
                objeto.setCelular(edit_cel_edit.getText().toString());
                objeto.setDepto(departamento);
                objeto.setCiudad(ciudad);
                objeto.setBarrio(edit_barrio.getText().toString());
                objeto.setTipo_via(tipo_via);
                objeto.setDir_1(edit_a.getText().toString());
                objeto.setDir_2(edit_b.getText().toString());
                objeto.setDir_3(edit_c.getText().toString());
                objeto.setOtro_dir(otros_dir);
                objeto.setDes_otro(edit_descripcion.getText().toString());
                objeto.setTerritorio(territorio);
                objeto.setZona(id_zona_sp);
                objeto.setCategoria(id_categoria);
                objeto.setEstado_com(estado_cliente);

                String parJSON = new Gson().toJson(objeto, RequesGuardarPunto.class);

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("datos", parJSON);

                return params;

            }
        };

        addToQueue(jsonRequest);
    }

    private void parseJSONResponse(String response) {

        Gson gson = new Gson();

        if (!response.equals("[]")) {
            try {

                ResponseInsert responseInsert = gson.fromJson(response, ResponseInsert.class);

                if (responseInsert.getId() == 0) {

                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                    dialogo1.setTitle(responseInsert.getMsg());
                    dialogo1.setMessage(edit_nombres.getText().toString() + "\n" + "ID POS: " +responseInsert.getIdpos());
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            aceptar();
                        }
                    });
                    dialogo1.setNegativeButton("Vender", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            //cancelar();
                        }
                    });

                    dialogo1.show();

                } else if (responseInsert.getId() == -1) {

                }

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Implementing ActionBar Search inside a fragment
        MenuItem item2 = menu.add("Filtrar");
        item2.setIcon(R.drawable.ic_search_white_24dp); // sets icon
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //DialogMenu();
                return true;
            }
        });

    }

    private void aceptar() {
        edit_nombres.setFocusable(true);
        edit_nombres.setFocusableInTouchMode(true);
        edit_nombres.requestFocus();
        edit_nombres.setText("");

        edit_cedula.setText("");
        edit_nom_cli.setText("");
        edit_correo_edit.setText("");
        edit_tel_edit.setText("");
        edit_cel_edit.setText("");
        edit_barrio.setText("");
        edit_a.setText("");
        edit_b.setText("");
        edit_c.setText("");
        edit_descripcion.setText("");
    }

    private boolean isValidNumber(String number){return number == null || number.length() == 0;}

    private boolean validarCampos() {

        boolean indicadorValidate = false;

        if (isValidNumber(edit_nombres.getText().toString().trim())){
            edit_nombres.setFocusable(true);
            edit_nombres.setFocusableInTouchMode(true);
            edit_nombres.requestFocus();
            edit_nombres.setText("");
            edit_nombres.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if(isValidNumber(edit_cedula.getText().toString().trim())) {
            edit_cedula.setFocusable(true);
            edit_cedula.setFocusableInTouchMode(true);
            edit_cedula.requestFocus();
            edit_cedula.setText("");
            edit_cedula.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidNumber(edit_nom_cli.getText().toString().trim())) {
            edit_nom_cli.setFocusable(true);
            edit_nom_cli.setFocusableInTouchMode(true);
            edit_nom_cli.requestFocus();
            edit_nom_cli.setText("");
            edit_nom_cli.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidNumber(edit_correo_edit.getText().toString().trim())) { // PENDIENTE VALIDAR CORREO FORMATO
            edit_correo_edit.setFocusable(true);
            edit_correo_edit.setFocusableInTouchMode(true);
            edit_correo_edit.requestFocus();
            edit_correo_edit.setText("");
            edit_correo_edit.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidNumber(edit_tel_edit.getText().toString().trim())) {
            edit_tel_edit.setFocusable(true);
            edit_tel_edit.setFocusableInTouchMode(true);
            edit_tel_edit.requestFocus();
            edit_tel_edit.setText("");
            edit_tel_edit.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidNumber(edit_cel_edit.getText().toString().trim())) {
            edit_cel_edit.setFocusable(true);
            edit_cel_edit.setFocusableInTouchMode(true);
            edit_cel_edit.requestFocus();
            edit_cel_edit.setText("");
            edit_cel_edit.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidNumber(edit_barrio.getText().toString().trim())){
            edit_barrio.setFocusable(true);
            edit_barrio.setFocusableInTouchMode(true);
            edit_barrio.requestFocus();
            edit_barrio.setText("");
            edit_barrio.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidNumber(edit_a.getText().toString().trim())) {
            edit_a.setFocusable(true);
            edit_a.setFocusableInTouchMode(true);
            edit_a.requestFocus();
            edit_a.setText("");
            edit_a.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidNumber(edit_b.getText().toString().trim())){
            edit_b.setFocusable(true);
            edit_b.setFocusableInTouchMode(true);
            edit_b.requestFocus();
            edit_b.setText("");
            edit_b.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidNumber(edit_c.getText().toString().trim())) {
            edit_c.setFocusable(true);
            edit_c.setFocusableInTouchMode(true);
            edit_c.requestFocus();
            edit_c.setText("");
            edit_c.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidNumber(edit_descripcion.getText().toString().trim())){
            edit_descripcion.setFocusable(true);
            edit_descripcion.setFocusableInTouchMode(true);
            edit_descripcion.requestFocus();
            edit_descripcion.setText("");
            edit_descripcion.setError("Este campo es obligatorio");
            indicadorValidate = true;
        }

        return indicadorValidate;

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

                ResponseCreatePunt responseCreatePunt = gson.fromJson(response, ResponseCreatePunt.class);

                loadDepartamento(responseCreatePunt);

                loadEstado(responseCreatePunt.getEstadoComunList());

                loadCircuito(responseCreatePunt.getTerritorioList());

                loadCategoria(responseCreatePunt.getCategoriasList());

                loadTipoVia();

                loadOtro();

            }  catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        }
    }

    private void loadEstado(final List<CategoriasEstandar> estadoComunList) {

        ArrayAdapter<CategoriasEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, estadoComunList);
        sp_estado_comercial.setAdapter(prec3);
        sp_estado_comercial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estado_cliente = estadoComunList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }

    private void loadCircuito(final List<Territorio> territorioList) {

        ArrayAdapter<Territorio> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, territorioList);
        sp_circuito.setAdapter(prec3);
        sp_circuito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                territorio = territorioList.get(position).getId();
                loadZonaRuta(territorioList.get(position).getZonaList());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }

    private void loadZonaRuta(final List<Zona> zonaList) {

        ArrayAdapter<Zona> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, zonaList);
        sp_zona_ruta.setAdapter(prec3);
        sp_zona_ruta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_zona_sp = zonaList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }

    private void loadTipoVia() {

        final List<CategoriasEstandar> categoriasEstandar = new ArrayList<>();

        categoriasEstandar.add(new CategoriasEstandar(6, "Autopista"));
        categoriasEstandar.add(new CategoriasEstandar(7, "Avenida"));
        categoriasEstandar.add(new CategoriasEstandar(14, "Bulevar"));
        categoriasEstandar.add(new CategoriasEstandar(16, "Calle"));
        categoriasEstandar.add(new CategoriasEstandar(17, "Carrera"));
        categoriasEstandar.add(new CategoriasEstandar(18, "Carretera"));
        categoriasEstandar.add(new CategoriasEstandar(20, "Circular"));
        categoriasEstandar.add(new CategoriasEstandar(21, "Circunvalar"));
        categoriasEstandar.add(new CategoriasEstandar(25, "Diagonal"));
        categoriasEstandar.add(new CategoriasEstandar(36, "Kilometro"));
        categoriasEstandar.add(new CategoriasEstandar(45, "Pasaje"));
        categoriasEstandar.add(new CategoriasEstandar(46, "Paseo"));
        categoriasEstandar.add(new CategoriasEstandar(47, "Peatonal"));
        categoriasEstandar.add(new CategoriasEstandar(61, "Transversal"));
        categoriasEstandar.add(new CategoriasEstandar(62, "Troncal"));
        categoriasEstandar.add(new CategoriasEstandar(65, "Variante"));
        categoriasEstandar.add(new CategoriasEstandar(67, "Via"));

        ArrayAdapter<CategoriasEstandar> prec1 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, categoriasEstandar);
        sp_tipo_via.setAdapter(prec1);
        sp_tipo_via.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo_via = categoriasEstandar.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void loadOtro() {

        final List<CategoriasEstandar> categoriasEstandar = new ArrayList<>();

        categoriasEstandar.add(new CategoriasEstandar(4, "Altillo"));
        categoriasEstandar.add(new CategoriasEstandar(5, "Apartamento"));
        categoriasEstandar.add(new CategoriasEstandar(10, "Barrio"));
        categoriasEstandar.add(new CategoriasEstandar(11, "Bis"));
        categoriasEstandar.add(new CategoriasEstandar(12, "Bloque"));
        categoriasEstandar.add(new CategoriasEstandar(13, "Bodega"));
        categoriasEstandar.add(new CategoriasEstandar(15, "Burubuja"));
        categoriasEstandar.add(new CategoriasEstandar(19, "Casa"));
        categoriasEstandar.add(new CategoriasEstandar(22, "Ciudadela"));
        categoriasEstandar.add(new CategoriasEstandar(23, "Consultorio"));
        categoriasEstandar.add(new CategoriasEstandar(24, "Deposito"));
        categoriasEstandar.add(new CategoriasEstandar(26, "Edificio"));
        categoriasEstandar.add(new CategoriasEstandar(27, "Entrada"));
        categoriasEstandar.add(new CategoriasEstandar(28, "Esquina"));
        categoriasEstandar.add(new CategoriasEstandar(29, "Etapa"));
        categoriasEstandar.add(new CategoriasEstandar(30, "Estacion"));
        categoriasEstandar.add(new CategoriasEstandar(31, "Exterior"));
        categoriasEstandar.add(new CategoriasEstandar(32, "Este"));
        categoriasEstandar.add(new CategoriasEstandar(33, "Finca"));
        categoriasEstandar.add(new CategoriasEstandar(34, "Garaje"));
        categoriasEstandar.add(new CategoriasEstandar(35, "Interior"));
        categoriasEstandar.add(new CategoriasEstandar(37, "Local"));
        categoriasEstandar.add(new CategoriasEstandar(38, "Lote"));
        categoriasEstandar.add(new CategoriasEstandar(39, "Manzana"));
        categoriasEstandar.add(new CategoriasEstandar(40, "Mezzanine"));
        categoriasEstandar.add(new CategoriasEstandar(41, "Modulo"));
        categoriasEstandar.add(new CategoriasEstandar(42, "Oficina"));
        categoriasEstandar.add(new CategoriasEstandar(43, "Parque"));
        categoriasEstandar.add(new CategoriasEstandar(44, "Parqueadero"));
        categoriasEstandar.add(new CategoriasEstandar(48, "Pent-House"));
        categoriasEstandar.add(new CategoriasEstandar(49, "Piso"));
        categoriasEstandar.add(new CategoriasEstandar(50, "Planta"));
        categoriasEstandar.add(new CategoriasEstandar(51, "Predio"));
        categoriasEstandar.add(new CategoriasEstandar(52, "Puesto"));
        categoriasEstandar.add(new CategoriasEstandar(53, "Semisotano"));
        categoriasEstandar.add(new CategoriasEstandar(54, "Sotano"));
        categoriasEstandar.add(new CategoriasEstandar(55, "Sector"));
        categoriasEstandar.add(new CategoriasEstandar(56, "Suite"));
        categoriasEstandar.add(new CategoriasEstandar(57, "Supermanzana"));
        categoriasEstandar.add(new CategoriasEstandar(58, "Sur"));
        categoriasEstandar.add(new CategoriasEstandar(59, "Terraza"));
        categoriasEstandar.add(new CategoriasEstandar(60, "Torre"));
        categoriasEstandar.add(new CategoriasEstandar(63, "Unidad"));
        categoriasEstandar.add(new CategoriasEstandar(64, "Urbanizacion"));
        categoriasEstandar.add(new CategoriasEstandar(66, "Zona"));

        ArrayAdapter<CategoriasEstandar> prec1 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, categoriasEstandar);
        sp_otros.setAdapter(prec1);
        sp_otros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                otros_dir = categoriasEstandar.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }

    private void loadCategoria(final List<CategoriasEstandar> responseCreatePunt){
        ArrayAdapter<CategoriasEstandar> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, responseCreatePunt);
        sp_categoria.setAdapter(prec3);
        sp_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_categoria = responseCreatePunt.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

    private void loadDepartamento(final ResponseCreatePunt responseCreatePunt) {

        ArrayAdapter<Departamentos> prec3 = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner, responseCreatePunt.getDepartamentosList());
        sp_departamentos.setAdapter(prec3);
        sp_departamentos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        sp_ciudades.setAdapter(prec3);
        sp_ciudades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ciudad = departamentos.getCiudadList().get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });
    }

}
