package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.TabsAdapter;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentCombos;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentSimcardP;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.dcsdealerperu.com.dealerperu.R;

public class ActInventariar extends AppCompatActivity {

    private Bundle bundle;
    private  ResponseMarcarPedido thumbs = new ResponseMarcarPedido();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventariar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            thumbs = (ResponseMarcarPedido)bundle.getSerializable("value");
        }


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager());

        tabsAdapter.addFragment(new FragmentSimcardP(thumbs.getId_pos()), "SIMCARD");

        tabsAdapter.addFragment(new FragmentCombos(thumbs.getId_pos()), "COMBOS");


        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
