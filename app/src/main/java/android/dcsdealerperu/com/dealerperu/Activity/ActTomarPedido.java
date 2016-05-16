package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.TabsAdapter;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentCombos;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentSimcardP;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ActTomarPedido extends AppCompatActivity {

    private Bundle bundle;
    private ResponseMarcarPedido thumbs = new ResponseMarcarPedido();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomar_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            thumbs = (ResponseMarcarPedido) bundle.getSerializable("value");
        }


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager());

        tabsAdapter.addFragment(new FragmentSimcardP(thumbs.getId_pos()), "SIMCARD");

        tabsAdapter.addFragment(new FragmentCombos(thumbs.getId_pos()), "COMBOS");


        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}
