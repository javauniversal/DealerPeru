package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.ExpandableListAdapter;
import android.dcsdealerperu.com.dealerperu.Adapter.ExpandableListDataPump;
import android.dcsdealerperu.com.dealerperu.Entry.Detalle;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.dcsdealerperu.com.dealerperu.R;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActMarcarVisita extends AppCompatActivity implements View.OnClickListener {

    private ResponseMarcarPedido mDescribable = new ResponseMarcarPedido();
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
    private Bundle bundle;
    private ArrayList<String> expandableListTitle;
    private HashMap<String, List<Detalle>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_visita);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text_idpos = (TextView) findViewById(R.id.text_idpos);
        text_razon = (TextView) findViewById(R.id.text_razon);
        text_ruta = (TextView) findViewById(R.id.text_ruta);
        text_direccion = (TextView) findViewById(R.id.text_direccion);
        text_departamento = (TextView) findViewById(R.id.text_departamento);
        text_provincia = (TextView) findViewById(R.id.text_provincia);
        text_distrito = (TextView) findViewById(R.id.text_distrito);
        text_circuito = (TextView) findViewById(R.id.text_circuito);

        btn_pedidos_pendientes = (Button) findViewById(R.id.btn_pedidos_pendientes);
        btn_pedidos_pendientes.setOnClickListener(this);

        btn_no_venta = (Button) findViewById(R.id.btn_no_venta);
        btn_no_venta.setOnClickListener(this);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (ResponseMarcarPedido)bundle.getSerializable("value");
        }

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
                Intent intent = new Intent(this, ActNoVenta.class);
                bundle.putSerializable("value", mDescribable);
                intent.putExtras(bundle);
                startActivity(intent);

                break;

            case R.id.btn_pedidos_pendientes:

                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_pedidos_pendientes, null);

                expandableListView = (ExpandableListView) dialoglayout.findViewById(R.id.expandableListView);

                expandableListDetail = ExpandableListDataPump.getData(mDescribable.getPedidosList());
                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

                expandableListAdapter = new ExpandableListAdapter(this, expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
