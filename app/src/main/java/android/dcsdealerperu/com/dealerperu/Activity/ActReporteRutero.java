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

        TextView txt_nombre = (TextView) dialoglayout.findViewById(R.id.nombre_punto);
        txt_nombre.setText(String.format("%1$s", getResponseHomeListS().get(position).getRazon()));

        TextView txt_visitado = (TextView) dialoglayout.findViewById(R.id.txt_visitado);
        String visita;
        if (getResponseHomeListS().get(position).getTipo_visita() == 0)
            visita = "No";
        else
            visita = "Si";

        txt_visitado.setText(String.format("%1$s", visita));

        TextView txt_direccion = (TextView) dialoglayout.findViewById(R.id.txt_direccion);
        txt_direccion.setText(String.format("%1$s", getResponseHomeListS().get(position).getDireccion()));

        TextView txt_distrito = (TextView) dialoglayout.findViewById(R.id.txt_distrito);
        txt_distrito.setText(String.format("%1$s", getResponseHomeListS().get(position).getDistrito()));

        TextView txt_telefono = (TextView) dialoglayout.findViewById(R.id.txt_telefono);
        txt_telefono.setText(String.format("%1$s", getResponseHomeListS().get(position).getTel()));

        TextView txt_dias = (TextView) dialoglayout.findViewById(R.id.txt_dias);
        txt_dias.setText(String.format("%1$s", getResponseHomeListS().get(position).getDetalle()));

        String fecha_hora = getResponseHomeListS().get(position).getFecha_ult()+"  "+getResponseHomeListS().get(position).getHora_ult();
        if(fecha_hora.trim().isEmpty())
            fecha_hora = "N/A";

        TextView txt_hora_visita = (TextView) dialoglayout.findViewById(R.id.txt_hora_visita);
        txt_hora_visita.setText(String.format("%1$s",fecha_hora));

        TextView txt_stock_c = (TextView) dialoglayout.findViewById(R.id.txt_stock_c);
        txt_stock_c.setText(String.format("%1$s", getResponseHomeListS().get(position).getStock_combo()));

        TextView txt_stock_s = (TextView) dialoglayout.findViewById(R.id.txt_stock_s);
        txt_stock_s.setText(String.format("%1$s", getResponseHomeListS().get(position).getStock_sim()));

        TextView txt_dias_s = (TextView) dialoglayout.findViewById(R.id.txt_dias_s);
        txt_dias_s.setText(String.format("%1$s", getResponseHomeListS().get(position).getDias_inve_sim()));

        TextView txt_dias_c = (TextView) dialoglayout.findViewById(R.id.txt_dias_c);
        txt_dias_c.setText(String.format("%1$s", getResponseHomeListS().get(position).getDias_inve_combo()));

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
