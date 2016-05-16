package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.AdapterListReferencia;
import android.dcsdealerperu.com.dealerperu.Entry.ReferenciasCombos;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.baoyz.swipemenulistview.SwipeMenuListView;

public class ActDetalleComboPedir extends AppCompatActivity {

    private Bundle bundle;
    private ReferenciasCombos mDescribable = new ReferenciasCombos();
    private SwipeMenuListView mListView;
    private AdapterListReferencia adapterListReferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_combo_pedir);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (ReferenciasCombos) bundle.getSerializable("value");

            mListView = (SwipeMenuListView) findViewById(R.id.listView);

            adapterListReferencia = new AdapterListReferencia(this, mDescribable.getReferenciaLis());
            mListView.setAdapter(adapterListReferencia);

        }

    }

}
