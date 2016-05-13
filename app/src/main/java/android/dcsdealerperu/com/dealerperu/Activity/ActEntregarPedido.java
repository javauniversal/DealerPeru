package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.AdapterEntregaPedido;
import android.dcsdealerperu.com.dealerperu.Entry.AprobarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.ListPedidoEntrega;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseEntregarPedido;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.dcsdealerperu.com.dealerperu.R;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.Serializable;
import java.util.List;

public class ActEntregarPedido extends AppCompatActivity {

    private Bundle bundle;
    private List<ResponseEntregarPedido> mDescribable;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entregar_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (List<ResponseEntregarPedido>)bundle.getSerializable("value");
        }

        mListView = (ListView) findViewById(R.id.listView);


        AdapterEntregaPedido adapterEntregaPedido = new AdapterEntregaPedido(this, mDescribable);
        mListView.setAdapter(adapterEntregaPedido);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                Intent intent = new Intent(ActEntregarPedido.this, ActEntregarPorPedido.class);
                bundle.putSerializable("value", mDescribable.get(position));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }


}
