package android.dcsdealerperu.com.dealerperu.Fragment;


import android.app.FragmentManager;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Activity.ActBuscarPunto;
import android.dcsdealerperu.com.dealerperu.Activity.ActMainPeru;
import android.dcsdealerperu.com.dealerperu.Entry.RequesGuardarPunto;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.dcsdealerperu.com.dealerperu.R;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FragmentDatosPersonales extends BaseVolleyFragment implements View.OnClickListener{

    private EditText edit_nombres;
    private EditText edit_cedula;
    private EditText edit_nom_cli;
    private EditText edit_correo_edit;
    private EditText edit_tel_edit;
    private EditText edit_cel_edit;
    private Button btn_siguiente_per;
    private FragmentDireccion fragmentDireccion;
    private Switch switch1;
    private int venta_recarga = 2;

    public FragmentDatosPersonales() {
        // Required empty public constructor
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

        if (RequesGuardarPunto.getRequesGuardarPuntoStatic() != null) {
            cargarDataPre();
        }

        return view;

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
                    fManager.beginTransaction().replace(R.id.contentPanel, fragmentDireccion).commit();

                }

                break;
        }
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

        RequesGuardarPunto.setRequesGuardarPuntoStatic(objeto);

    }

}
