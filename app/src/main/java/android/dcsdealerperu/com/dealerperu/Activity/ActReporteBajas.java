package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.AdapterDialogMisBajas;
import android.dcsdealerperu.com.dealerperu.Adapter.ExpandableListAdapterMisBajas;
import android.dcsdealerperu.com.dealerperu.Adapter.ExpandableListDataPumpMisBajas;
import android.dcsdealerperu.com.dealerperu.Entry.MisBajasDetalle1;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMisBajas;
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

public class ActReporteBajas extends AppCompatActivity {

    private ResponseMisBajas mDescribable;
    private Bundle bundle;

    private ExpandableListAdapterMisBajas expandableListAdapter;
    private ExpandableListView expandableListView;
    private ArrayList<String> expandableListTitle;
    private HashMap<String, List<MisBajasDetalle1>> expandableListDetail;
    private AdapterDialogMisBajas adapterDialogMisBajas;

    private SwipeMenuListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_bajas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (ResponseMisBajas) bundle.getSerializable("value");
        }

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        expandableListDetail = ExpandableListDataPumpMisBajas.getData(mDescribable.getMisBajas());
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

        expandableListAdapter = new ExpandableListAdapterMisBajas(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_mis_bajas, null);


                mListView = (SwipeMenuListView) dialoglayout.findViewById(R.id.listView);
                adapterDialogMisBajas = new AdapterDialogMisBajas(ActReporteBajas.this, mDescribable.getMisBajas().get(groupPosition).getMisBajasDetalle1List().get(childPosition).getMisBajasDetalle2List());
                mListView.setAdapter(adapterDialogMisBajas);

                AlertDialog.Builder builder = new AlertDialog.Builder(ActReporteBajas.this);
                builder.setCancelable(false);
                builder.setTitle("Detalle Series");
                builder.setView(dialoglayout).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();
                //Toast.makeText(ActReporteBajas.this,mDescribable.getMisBajas().get(groupPosition).getMisBajasDetalle1List().get(childPosition).getMisBajasDetalle2List().get(0).getSerie(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }

}
