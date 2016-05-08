package android.dcsdealerperu.com.dealerperu.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.AdapterMisPedidos;
import android.dcsdealerperu.com.dealerperu.Entry.MisPedidos;
import android.dcsdealerperu.com.dealerperu.R;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import dmax.dialog.SpotsDialog;

public class ActReporteMisPedidos extends AppCompatActivity {

    private MisPedidos mDescribable;
    private Bundle bundle;
    private SpotsDialog alertDialog;
    private AdapterMisPedidos adapterMisPedidos;

    private SwipeMenuListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_mis_pedidos);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (MisPedidos)bundle.getSerializable("value");
        }

        mListView = (SwipeMenuListView) findViewById(R.id.listView);
        //mListView = (SwipeMenuListView) findViewById(R.drawable.i);
        adapterMisPedidos = new AdapterMisPedidos(this, mDescribable.getResponseMisPedidosList());
        mListView.setAdapter(adapterMisPedidos);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(16, 98,138)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Detalle");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
            }
        };

// set creator
        mListView.setMenuCreator(creator);

// step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                //AddProductCar item = mAppList.get(position);
                switch (index) {
                    case 0:
                        cargarDetalle(position);
                        break;
                }
                return false;
            }
        });

// set SwipeListener
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

// test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //deletePrduct(position);
                return false;
            }
        });
    }

    private void cargarDetalle(int position) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_detalle_mis_pedidos, null);

        TextView txt_idpos = (TextView) dialoglayout.findViewById(R.id.txt_idpos);
        txt_idpos.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getIdpos()));

        TextView txt_npedido = (TextView) dialoglayout.findViewById(R.id.txt_npedido);
        txt_npedido.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getNpedido()));

        TextView txt_cantidad = (TextView) dialoglayout.findViewById(R.id.txt_cantidad);
        txt_cantidad.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getCantidad()));

        TextView txt_total = (TextView) dialoglayout.findViewById(R.id.txt_total);
        txt_total.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getTotal()));

        TextView txt_cant_picking = (TextView) dialoglayout.findViewById(R.id.txt_cant_picking);
        txt_cant_picking.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getCantidad_picking()));

        TextView txt_total_picking = (TextView) dialoglayout.findViewById(R.id.txt_total_picking);
        txt_total_picking.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getTotal_picking()));

        TextView txt_hora = (TextView) dialoglayout.findViewById(R.id.txt_hora);
        txt_hora.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getHora()));

        TextView txt_nombre_punto = (TextView) dialoglayout.findViewById(R.id.txt_nombre_punto);
        txt_nombre_punto.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getNombre_punto()));

        TextView txt_fecha = (TextView) dialoglayout.findViewById(R.id.txt_fecha);
        txt_fecha.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getFecha()));

        TextView txt_estado = (TextView) dialoglayout.findViewById(R.id.txt_estado);
        txt_estado.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getEstado()));


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Detalle Pedido");
        builder.setView(dialoglayout).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
