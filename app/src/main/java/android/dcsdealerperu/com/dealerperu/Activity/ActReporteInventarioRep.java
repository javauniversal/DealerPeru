package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.AdapterDialogMisBajas;
import android.dcsdealerperu.com.dealerperu.Adapter.ExpandableListAdapterReporteInv;
import android.dcsdealerperu.com.dealerperu.Adapter.ExpandableListAdapterReporteInvPed;
import android.dcsdealerperu.com.dealerperu.Adapter.ExpandableListDataPumpInvePed;
import android.dcsdealerperu.com.dealerperu.Adapter.ExpandableListInventarioRep;
import android.dcsdealerperu.com.dealerperu.Entry.ListResponseInventario;
import android.dcsdealerperu.com.dealerperu.Entry.MisBajasDetalle1;
import android.dcsdealerperu.com.dealerperu.Entry.MisBajasDetalle2;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActReporteInventarioRep extends AppCompatActivity {

    private ListResponseInventario mDescribable;
    private Bundle bundle;

    private ExpandableListAdapterReporteInv expandableListAdapter;
    private ExpandableListView expandableListView;
    private ArrayList<String> expandableListTitle;
    private HashMap<String, List<MisBajasDetalle2>> expandableListDetail;
    private int tipo;
    private SwipeMenuListView mListView;


    private ExpandableListAdapterReporteInvPed expandableListAdapter2;
    private HashMap<String, List<MisBajasDetalle1>> expandableListDetail2;
    private AdapterDialogMisBajas adapterDialogMisBajas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_inventario_rep);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (ListResponseInventario) bundle.getSerializable("value");
            tipo = bundle.getInt("tipo");
        }

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        if(tipo == 2) {
            expandableListDetail = ExpandableListInventarioRep.getData(mDescribable.getReferencias());
            expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

            expandableListAdapter = new ExpandableListAdapterReporteInv(this, expandableListTitle, expandableListDetail, tipo);
            expandableListView.setAdapter(expandableListAdapter);
        }else if(tipo == 1){
            expandableListDetail2 = ExpandableListDataPumpInvePed.getData(mDescribable.getReferencias());
            expandableListTitle = new ArrayList<>(expandableListDetail2.keySet());

            expandableListAdapter2 = new ExpandableListAdapterReporteInvPed(this, expandableListTitle, expandableListDetail2, tipo);
            expandableListView.setAdapter(expandableListAdapter2);
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                    LayoutInflater inflater = getLayoutInflater();
                    View dialoglayout = inflater.inflate(R.layout.dialog_mis_bajas, null);


                    mListView = (SwipeMenuListView) dialoglayout.findViewById(R.id.listView);
                    adapterDialogMisBajas = new AdapterDialogMisBajas(ActReporteInventarioRep.this, mDescribable.getReferencias().get(groupPosition).getDetalle_referencias().get(childPosition).getDetalle_series());
                    mListView.setAdapter(adapterDialogMisBajas);

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActReporteInventarioRep.this);
                    builder.setCancelable(false);
                    builder.setTitle("Detalle Series");
                    builder.setView(dialoglayout).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();
                    return false;
                }
            });
        }

    }

}
