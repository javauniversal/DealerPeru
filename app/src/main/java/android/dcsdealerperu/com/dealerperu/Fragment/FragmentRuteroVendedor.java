package android.dcsdealerperu.com.dealerperu.Fragment;


import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class FragmentRuteroVendedor extends BaseVolleyFragment {

    private Spinner estado_visita;
    private Spinner tipo_frecuencia;
    private Spinner dia;

    public FragmentRuteroVendedor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rutero_vendedor, container, false);

        estado_visita = (Spinner) view.findViewById(R.id.spinner_estado_visita);
        tipo_frecuencia = (Spinner) view.findViewById(R.id.spinner_tipo_frecuencia);
        dia = (Spinner) view.findViewById(R.id.spinner_dia);
        LoadSpinners();

        return view;
    }

    private void LoadSpinners() {
        // llena Spinner de Estados Visita
        final List<CategoriasEstandar> ListaEstadosVista = new ArrayList<>();
        ListaEstadosVista.add(new CategoriasEstandar(0,"Seleccionar"));
        ListaEstadosVista.add(new CategoriasEstandar(1,"Visitado"));
        ListaEstadosVista.add(new CategoriasEstandar(2,"Sin Visitar"));

        ArrayAdapter<CategoriasEstandar> adapterEstadoVisita = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner,ListaEstadosVista);
        estado_visita.setAdapter(adapterEstadoVisita);

        // llena Spinner de Tipo Frecuencia
        final List<CategoriasEstandar> ListaTipoFrecuencia = new ArrayList<>();
        ListaTipoFrecuencia.add(new CategoriasEstandar(0,"Seleccionar"));
        ListaTipoFrecuencia.add(new CategoriasEstandar(1,"Semanal"));
        ListaTipoFrecuencia.add(new CategoriasEstandar(2,"Quincenal"));
        ListaTipoFrecuencia.add(new CategoriasEstandar(3,"Mensual"));

        ArrayAdapter<CategoriasEstandar> adapterTipoFrecuencia = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner,ListaTipoFrecuencia);
        tipo_frecuencia.setAdapter(adapterTipoFrecuencia);

        //llenar Spinner de Dia
        final List<CategoriasEstandar> ListaDia = new ArrayList<>();
        ListaDia.add(new CategoriasEstandar(0,"Seleccionar"));
        ListaDia.add(new CategoriasEstandar(1,"Lunes"));
        ListaDia.add(new CategoriasEstandar(2,"Martes"));
        ListaDia.add(new CategoriasEstandar(3,"Miercoles"));
        ListaDia.add(new CategoriasEstandar(4,"Jueves"));
        ListaDia.add(new CategoriasEstandar(5,"Viernes"));
        ListaDia.add(new CategoriasEstandar(6,"Sabado"));
        ListaDia.add(new CategoriasEstandar(7,"Domingo"));

        ArrayAdapter<CategoriasEstandar> adapterDia = new ArrayAdapter<>(getActivity(), R.layout.textview_spinner,ListaDia);
        dia.setAdapter(adapterDia);
    }

}
