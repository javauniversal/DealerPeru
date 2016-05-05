package android.dcsdealerperu.com.dealerperu.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Activity.ActNoVenta;
import android.dcsdealerperu.com.dealerperu.Adapter.ExpandableListAdapter;
import android.dcsdealerperu.com.dealerperu.Adapter.ExpandableListDataPump;
import android.dcsdealerperu.com.dealerperu.Entry.Detalle;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.dcsdealerperu.com.dealerperu.R;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FragmenMarcarvisita extends BaseVolleyFragment implements View.OnClickListener {

    private static final String DESCRIBABLE_KEY = "describable_key";
    private ResponseMarcarPedido mDescribable;
    private TextView text_idpos;
    private TextView text_razon;
    private TextView text_ruta;
    private TextView text_direccion;
    private TextView text_departamento;
    private TextView text_provincia;
    private TextView text_distrito;
    private TextView text_circuito;
    private Button btn_pedidos_pendientes;
    private Button btn_no_venta;
    private ExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;

    private ArrayList<String> expandableListTitle;
    private HashMap<String, List<Detalle>> expandableListDetail;

    public static FragmenMarcarvisita newInstance(ResponseMarcarPedido describable) {
        FragmenMarcarvisita fragment = new FragmenMarcarvisita();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DESCRIBABLE_KEY, describable);
        fragment.setArguments(bundle);

        return fragment;
    }

    public FragmenMarcarvisita() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_marcarvisita, container, false);

        text_idpos = (TextView) view.findViewById(R.id.text_idpos);
        text_razon = (TextView) view.findViewById(R.id.text_razon);
        text_ruta = (TextView) view.findViewById(R.id.text_ruta);
        text_direccion = (TextView) view.findViewById(R.id.text_direccion);
        text_departamento = (TextView) view.findViewById(R.id.text_departamento);
        text_provincia = (TextView) view.findViewById(R.id.text_provincia);
        text_distrito = (TextView) view.findViewById(R.id.text_distrito);
        text_circuito = (TextView) view.findViewById(R.id.text_circuito);

        btn_pedidos_pendientes = (Button) view.findViewById(R.id.btn_pedidos_pendientes);
        btn_pedidos_pendientes.setOnClickListener(this);

        btn_no_venta = (Button) view.findViewById(R.id.btn_no_venta);
        btn_no_venta.setOnClickListener(this);



        mDescribable = (ResponseMarcarPedido) getArguments().getSerializable(DESCRIBABLE_KEY);


        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setDataPunto();
    }

    private void setDataPunto() {

        text_idpos.setText(String.format("%1$s", mDescribable.getId_pos()));
        text_razon.setText(String.format("%1$s", mDescribable.getRazon_social()));
        text_ruta.setText(String.format("%1$s", mDescribable.getZona()));
        text_direccion.setText(String.format("%1$s", mDescribable.getDireccion()));
        text_departamento.setText(String.format("%1$s", mDescribable.getDepto()));
        text_provincia.setText(String.format("%1$s", mDescribable.getProvincia()));
        text_distrito.setText(String.format("%1$s", mDescribable.getDistrito()));
        text_circuito.setText(String.format("%1$s", mDescribable.getTerritorio()));


        if (mDescribable.getPedidosList().size() > 0)
            btn_pedidos_pendientes.setVisibility(View.VISIBLE);

        if (mDescribable.getEstado() == 0)
            btn_no_venta.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_no_venta:

                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), ActNoVenta.class);
                bundle.putSerializable("value", mDescribable);
                intent.putExtras(bundle);
                startActivity(intent);

                break;

            case R.id.btn_pedidos_pendientes:

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_pedidos_pendientes, null);

                expandableListView = (ExpandableListView) dialoglayout.findViewById(R.id.expandableListView);

                expandableListDetail = ExpandableListDataPump.getData(mDescribable.getPedidosList());
                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

                expandableListAdapter = new ExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Pedidos PDV");
                builder.setView(dialoglayout).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();

                break;
        }
    }
}
