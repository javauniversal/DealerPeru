package android.dcsdealerperu.com.dealerperu.Fragment;


import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.dcsdealerperu.com.dealerperu.R;
import android.widget.Button;
import android.widget.EditText;


public class FragmentDatosPersonales extends BaseVolleyFragment implements View.OnClickListener{

    private EditText edit_nombres;
    private EditText edit_cedula;
    private EditText edit_nom_cli;
    private EditText edit_correo_edit;
    private EditText edit_tel_edit;
    private EditText edit_cel_edit;
    private Button btn_siguiente;
    private FragmentDireccion fragmentDireccion;

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

        btn_siguiente = (Button) view.findViewById(R.id.btn_siguiente);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_siguiente.setOnClickListener(this);

    }

    private boolean isValidNumber(String number){return number == null || number.length() == 0;}

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
            case R.id.btn_siguiente:

                if (!validarCampos()) {
                   // Siguiente
                    FragmentManager fManager = getFragmentManager();
                    fragmentDireccion = new FragmentDireccion();
                    fManager.beginTransaction().replace(R.id.contentPanel, fragmentDireccion).commit();
                }

                break;
        }
    }
}
