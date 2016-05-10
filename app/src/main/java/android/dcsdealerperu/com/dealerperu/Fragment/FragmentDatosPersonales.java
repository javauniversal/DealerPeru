package android.dcsdealerperu.com.dealerperu.Fragment;


import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Activity.ActBuscarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.GuardarEditarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.RequesGuardarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.RequestGuardarEditarPunto;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;


public class FragmentDatosPersonales extends BaseVolleyFragment implements View.OnClickListener{

    private EditText edit_nombres;
    private EditText edit_cedula;
    private EditText edit_nom_cli;
    private EditText edit_correo_edit;
    private EditText edit_tel_edit;
    private EditText edit_cel_edit;
    private Button btn_siguiente_per;
    private Button btn_cancelar_per;
    private Spinner spinnerTipoDocumento;
    private FragmentDireccion fragmentDireccion;
    private Switch switch1;
    private int venta_recarga = 2;
    private int editaPunto = 0;
    private int tipoDocumento = 0;
    private String accion;
    private RequestGuardarEditarPunto requestGuardarEditarPunto;

    private GuardarEditarPunto guardarEditarPunto;
    private RequestQueue rq;
    private List<CategoriasEstandar> ListaTipoDoc = new ArrayList<>();
    RequestGuardarEditarPunto datos;

    private SpotsDialog alertDialog;

    public static FragmentDatosPersonales newInstance(int idpos, String accion) {

        Bundle args = new Bundle();
        FragmentDatosPersonales fragment = new FragmentDatosPersonales();
        args.putInt("edit_punto",idpos);
        args.putString("accion",accion);
        fragment.setArguments(args);
        return fragment;
    }
    public FragmentDatosPersonales() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_datos_personales, container, false);

        edit_nombres = (EditText) view.findViewById(R.id.edit_nombres);
        edit_cedula = (EditText) view.findViewById(R.id.edit_cedula);
        edit_nom_cli = (EditText) view.findViewById(R.id.edit_nom_cli);
        edit_correo_edit = (EditText) view.findViewById(R.id.edit_correo_edit);
        edit_tel_edit = (EditText) view.findViewById(R.id.edit_tel_edit);
        edit_cel_edit = (EditText) view.findViewById(R.id.edit_cel_edit);
        switch1 = (Switch) view.findViewById(R.id.switch1);

        btn_siguiente_per = (Button) view.findViewById(R.id.btn_siguiente_per);

        btn_cancelar_per = (Button) view.findViewById(R.id.btn_siguiente_per);

        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);

        spinnerTipoDocumento = (Spinner) view.findViewById(R.id.spinner_tipo_documento);

        ListaTipoDoc.add(new CategoriasEstandar(1,"RUC"));
        ListaTipoDoc.add(new CategoriasEstandar(2,"DNI"));
        ListaTipoDoc.add(new CategoriasEstandar(2,"DNI"));

        ArrayAdapter<CategoriasEstandar> adapterEstados = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner,ListaTipoDoc);
        spinnerTipoDocumento.setAdapter(adapterEstados);
        spinnerTipoDocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoDocumento = ListaTipoDoc.get(position).getId();
                edit_cedula.setHint("");
                if(tipoDocumento == 1)
                {
                    edit_cedula.setHint("Ruc Responsable");
                }
                else
                {
                    edit_cedula.setHint("Dni Responsable");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

        if(getArguments() != null)
        {
            editaPunto = getArguments().getInt("idpos");
            accion = getArguments().getString("accion");
        }

        if(editaPunto != 0){
            CargarDatosPunto();
        }else
        {
            if (RequesGuardarPunto.getRequesGuardarPuntoStatic() != null) {
                cargarDataPre();
            }
        }

        return view;

    }

    private void CargarDatosPunto() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base),"consultar_info_puntos");
        rq = Volley.newRequestQueue(getActivity());
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // response
                        CargarDatos(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getActivity(), "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getActivity(), "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getActivity(), "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getActivity(), "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));
                params.put("idpos", String.valueOf(editaPunto)); // aqui va el idpos a consultar.!

                return params;
            }
        };

        rq.add(jsonRequest);
    }

    private void CargarDatos(String response) {
        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                datos = gson.fromJson(response, RequestGuardarEditarPunto.class);
                edit_nombres.setText(datos.getNombre_punto());
                edit_cedula.setText(datos.getCedula());
                edit_nom_cli.setText(datos.getNombre_cliente());
                edit_correo_edit.setText(datos.getEmail());
                edit_tel_edit.setText( String.valueOf((datos.getTelefono())));
                edit_cel_edit.setText( String.valueOf((datos.getCelular())));


                selectSpinnerValue(ListaTipoDoc,spinnerTipoDocumento,datos.getTipo_documento());
                if(datos.getVende_recargas() == 1) {
                    switch1.setChecked(true);
                }
                else{
                    switch1.setChecked(false);
                }

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        } else {
            alertDialog.dismiss();
        }
    }

    private void selectSpinnerValue(List<CategoriasEstandar> ListaEstado, Spinner spinner, int id)
    {
        for(int i = 0; i < ListaEstado.size(); i++){
            if(ListaEstado.get(i).getId() == id){
                spinner.setSelection(id);
                break;
            }
        }
    }

    private void cargarDataPre() {
        edit_nombres.setText(RequesGuardarPunto.getRequesGuardarPuntoStatic().getNombre_punto());
        edit_cedula.setText(RequesGuardarPunto.getRequesGuardarPuntoStatic().getCedula());
        edit_nom_cli.setText(RequesGuardarPunto.getRequesGuardarPuntoStatic().getNombre_cliente());
        edit_correo_edit.setText(RequesGuardarPunto.getRequesGuardarPuntoStatic().getEmail());
        edit_tel_edit.setText(RequesGuardarPunto.getRequesGuardarPuntoStatic().getTelefono());
        edit_cel_edit.setText(RequesGuardarPunto.getRequesGuardarPuntoStatic().getCelular());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        btn_siguiente_per.setOnClickListener(this);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    venta_recarga = 1;
                }else{
                    venta_recarga = 2;
                }
            }
        });

        btn_cancelar_per.setOnClickListener(this);

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
                buscarPunto();
                return true;
            }
        });

    }

    private void buscarPunto() {
        startActivity(new Intent(getActivity(), ActBuscarPunto.class));
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private boolean isValidNumber(String number){return number == null || number.length() == 0;}

    private boolean isValidNumberEmail(String number) {

        Pattern Plantilla = null;
        Matcher Resultado = null;
        Plantilla = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        System.out.println("Ingrese email:");

        Resultado = Plantilla.matcher(number);

        return Resultado.find();
    }

    public boolean validarCampos() {

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
        }else if (!isValidNumberEmail(edit_correo_edit.getText().toString().trim())) { // PENDIENTE VALIDAR CORREO FORMATO
            edit_correo_edit.setFocusable(true);
            edit_correo_edit.setFocusableInTouchMode(true);
            edit_correo_edit.requestFocus();
            edit_correo_edit.setError("Correo no valido");
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
        }

        return indicadorValidate;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_siguiente_per:

                if (!validarCampos()) {
                    // Siguiente
                    dataPersonal();

                    FragmentManager fManager = getFragmentManager();
                    fragmentDireccion = new FragmentDireccion();
                    if(editaPunto != 0)
                    {
                        Bundle args = new Bundle();
                        args.putSerializable("datos_punto", datos);
                        args.putInt("edit_punto", editaPunto);
                        args.putString("accion",accion);
                        fragmentDireccion.setArguments(args);
                    }
                    fManager.beginTransaction().replace(R.id.contentPanel, fragmentDireccion).commit();
                }
                break;
            case R.id.btn_cancelar_per:
                limpiarCampos();
                break;
        }
    }

    private void limpiarCampos() {

        edit_nombres.setText("");
        edit_cedula.setText("");
        edit_nom_cli.setText("");
        edit_correo_edit.setText("");
        edit_correo_edit.setText("");
        edit_tel_edit.setText("");
        edit_cel_edit.setText("");

    }

    private void dataPersonal() {

        RequesGuardarPunto objeto = new RequesGuardarPunto();

        objeto.setNombre_punto(edit_nombres.getText().toString());
        objeto.setCedula(edit_cedula.getText().toString());
        objeto.setNombre_cliente(edit_nom_cli.getText().toString());
        objeto.setEmail(edit_correo_edit.getText().toString().trim());
        objeto.setTelefono(edit_tel_edit.getText().toString());
        objeto.setCelular(edit_cel_edit.getText().toString());
        objeto.setVenta_recarga(venta_recarga);
        objeto.setTipo_documento(tipoDocumento);

        RequesGuardarPunto.setRequesGuardarPuntoStatic(objeto);

    }

}
