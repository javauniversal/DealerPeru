package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Entry.AprobarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseEntregarPedido;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.dcsdealerperu.com.dealerperu.R;

import java.util.List;

public class ActEntregarPedido extends AppCompatActivity {

    private Bundle bundle;
    private List<ResponseEntregarPedido> mDescribable;

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

    }




}
