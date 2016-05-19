package android.dcsdealerperu.com.dealerperu.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.AdapterAproPunto;
import android.dcsdealerperu.com.dealerperu.Entry.AprobarPunto;
import android.dcsdealerperu.com.dealerperu.R;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class ActReporteAprobacionPdv extends AppCompatActivity {

    private Bundle bundle;
    private List<AprobarPunto> mDescribable;
    private SwipeMenuListView mListView;
    private AdapterAproPunto actDetalleAproPunto;
    private RequestQueue rq;
    public static final String TAG = "MyTag";
    private SpotsDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_aprobacion_pdv);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alertDialog = new SpotsDialog(this, R.style.Custom);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (List<AprobarPunto>) bundle.getSerializable("value");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mListView = (SwipeMenuListView) findViewById(R.id.listView);
        //mListView = (SwipeMenuListView) findViewById(R.drawable.i);
        actDetalleAproPunto = new AdapterAproPunto(this, mDescribable);
        mListView.setAdapter(actDetalleAproPunto);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(16, 98, 138)));
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
                switch (index) {
                    case 0:
                        loadDetallePunto(position);
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
                //loadDetallePunto(position);
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

                return false;
            }
        });
    }

    private void loadDetallePunto(int position) {
        mDescribable.get(position).getEstado();
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_detalle_aprobaciones_pdvs, null);

        TextView txt_idpos = (TextView) dialoglayout.findViewById(R.id.txt_idpos);
        txt_idpos.setText(String.format("%1$s", mDescribable.get(position).getIdpdv()));

        TextView txt_npedido = (TextView) dialoglayout.findViewById(R.id.txt_nombre_punto);
        txt_npedido.setText(String.format("%1$s", mDescribable.get(position).getNombre_punto()));

        TextView txt_cantidad = (TextView) dialoglayout.findViewById(R.id.txt_tipo_sol);
        txt_cantidad.setText(String.format("%1$s", mDescribable.get(position).getSolicitud()));

        TextView txt_total = (TextView) dialoglayout.findViewById(R.id.txt_nsoli);
        txt_total.setText(String.format("%1$s", mDescribable.get(position).getId_soli()));

        TextView txt_cant_picking = (TextView) dialoglayout.findViewById(R.id.txt_estado);
        txt_cant_picking.setText(String.format("%1$s", mDescribable.get(position).getEstado()));

        TextView txt_total_picking = (TextView) dialoglayout.findViewById(R.id.txt_vendedor);
        txt_total_picking.setText(String.format("%1$s", mDescribable.get(position).getNombre_vende()));

        TextView txt_hora = (TextView) dialoglayout.findViewById(R.id.txt_fecha_sol);
        txt_hora.setText(String.format("%1$s", mDescribable.get(position).getFecha()));

        TextView txt_nombre_punto = (TextView) dialoglayout.findViewById(R.id.txt_fecha_accion);
        txt_nombre_punto.setText(String.format("%1$s", mDescribable.get(position).getFecha_accion()));

        TextView txt_fecha = (TextView) dialoglayout.findViewById(R.id.txt_hora_accion);
        txt_fecha.setText(String.format("%1$s", mDescribable.get(position).getHora_accion()));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Detalle Aprobacion");
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
