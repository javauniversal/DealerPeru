package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.AdapterEntregaPedido;
import android.dcsdealerperu.com.dealerperu.Adapter.AdapterEntregaPedidoPedido;
import android.dcsdealerperu.com.dealerperu.Entry.PedidosEntrega;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseEntregarPedido;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.dcsdealerperu.com.dealerperu.R;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.List;

public class ActEntregarPorPedido extends AppCompatActivity {

    private Bundle bundle;
    private ResponseEntregarPedido mDescribable;
    private SwipeMenuListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_entregar_por_o);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (ResponseEntregarPedido)bundle.getSerializable("value");
        }

        mListView = (SwipeMenuListView) findViewById(R.id.listView);

        AdapterEntregaPedidoPedido adapterEntregaPedido = new AdapterEntregaPedidoPedido(this, mDescribable.getPedidosEntregaList());
        mListView.setAdapter(adapterEntregaPedido);

    }

}
