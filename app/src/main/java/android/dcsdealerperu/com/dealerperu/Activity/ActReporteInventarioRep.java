package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.ExpandableListAdapterReporteInv;
import android.dcsdealerperu.com.dealerperu.Adapter.ExpandableListInventarioRep;
import android.dcsdealerperu.com.dealerperu.Entry.ListResponseInventario;
import android.dcsdealerperu.com.dealerperu.Entry.MisBajasDetalle2;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

        expandableListDetail = ExpandableListInventarioRep.getData(mDescribable.getReferencias());
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

        expandableListAdapter = new ExpandableListAdapterReporteInv(this, expandableListTitle, expandableListDetail, tipo);
        expandableListView.setAdapter(expandableListAdapter);

    }

}
