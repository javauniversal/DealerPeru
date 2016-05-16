package android.dcsdealerperu.com.dealerperu.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.dcsdealerperu.com.dealerperu.Adapter.AppAdapterRutero;
import android.dcsdealerperu.com.dealerperu.Entry.ListHome;
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

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseHome.getResponseHomeListS;

public class ActReporteRutero extends AppCompatActivity {
    private static final String DESCRIBABLE_KEY = "describable_key";
    private ListHome mDescribable = new ListHome();
    private SpotsDialog alertDialog;
    private AppAdapterRutero appAdapterRutero;
    private SwipeMenuListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_rutero);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //recycler = (RecyclerView) findViewById(R.id.recycler_view);

        mListView = (SwipeMenuListView) findViewById(R.id.listView);
        //mListView = (SwipeMenuListView) findViewById(R.drawable.i);
        appAdapterRutero = new AppAdapterRutero(this, (ListHome) getResponseHomeListS());
        mListView.setAdapter(appAdapterRutero);
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
        View dialoglayout = inflater.inflate(R.layout.dialog_detalle, null);

        TextView txt_id_numero = (TextView) dialoglayout.findViewById(R.id.txt_id_numero);
        txt_id_numero.setText(String.format("%1$s", getResponseHomeListS().get(position).getIdpos()));

        TextView txt_visitado = (TextView) dialoglayout.findViewById(R.id.txt_visitado);
        txt_visitado.setText(String.format("%1$s", getResponseHomeListS().get(position).getTipo_visita()));

        TextView txt_direccion = (TextView) dialoglayout.findViewById(R.id.txt_direccion);
        txt_direccion.setText(String.format("%1$s", getResponseHomeListS().get(position).getDireccion()));

        TextView txt_departamento = (TextView) dialoglayout.findViewById(R.id.txt_departamento);
        txt_departamento.setText(String.format("%1$s", getResponseHomeListS().get(position).getDepartamento()));

        TextView txt_ciudad = (TextView) dialoglayout.findViewById(R.id.txt_ciudad);
        txt_ciudad.setText(String.format("%1$s", getResponseHomeListS().get(position).getDepartamento()));

        TextView txt_circuito = (TextView) dialoglayout.findViewById(R.id.txt_circuito);
        txt_circuito.setText(String.format("%1$s", getResponseHomeListS().get(position).getCircuito()));

        TextView txt_ruta = (TextView) dialoglayout.findViewById(R.id.txt_ruta);
        txt_ruta.setText(String.format("%1$s", getResponseHomeListS().get(position).getRuta()));

        TextView txt_telefono = (TextView) dialoglayout.findViewById(R.id.txt_telefono);
        txt_telefono.setText(String.format("%1$s", getResponseHomeListS().get(position).getTel()));

        TextView txt_dias = (TextView) dialoglayout.findViewById(R.id.txt_dias);
        txt_dias.setText(String.format("%1$s", getResponseHomeListS().get(position).getDetalle()));

        TextView txt_hora_visita = (TextView) dialoglayout.findViewById(R.id.txt_hora_visita);
        txt_hora_visita.setText(String.format("%1$s", getResponseHomeListS().get(position).getTipo_visita()));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Detalle");
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
