package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmenMarcarvisita;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentCrearPunto;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentHome;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentMisBajas;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentMisPedidos;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentRuteroVendedor;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class ActMainPeru extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    FragmentManager fragmentManager;

    private int editaPunto;
    private int accionNav;
    private Bundle bundle;
    private String accion = "Guardar";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_peru);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 1 supervisor
        // 2 vendedor
        // 3 repartidor

        if (getResponseUserStatic().getPerfil() == 2) {

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_vendedor);

        } else if (getResponseUserStatic().getPerfil() == 3) {

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_repartidor);

        } else if (getResponseUserStatic().getPerfil() == 1) {

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_supervisor);

        }

        View header = navigationView.getHeaderView(0);

        TextView txt_sub = (TextView) header.findViewById(R.id.txt_sub);
        txt_sub.setText(String.format("%1$s %2$s", getResponseUserStatic().getNombre(), getResponseUserStatic().getApellido()));

        // Accion Para la Edicion del
        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {

            editaPunto = bundle.getInt("edit_punto");
            accionNav = bundle.getInt("accion");

            if(accionNav == 1) {
                accion = "Editar";
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_gestion_pdv));
            }

            if (accionNav == 2 ) {
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_marcar_visita));
            }

        }else{
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home));
        }


    }


    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            finishAffinity();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Pulse otra vez para salir", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Class fragmentClass = null;

        if (id == R.id.nav_home) {
            toolbar.setTitle("Inicio");
            editaPunto = 0; accion = "Guardar";
            fragmentClass = FragmentHome.class;

        } else if(id == R.id.nav_marcar_visita) {
            toolbar.setTitle("Marcar Visita");
            editaPunto = 0; accion = "Guardar";
            fragmentClass = FragmenMarcarvisita.class;

        } else if(id == R.id.nav_gestion_pdv) {
            toolbar.setTitle("Gestión PDV");
            fragmentClass = FragmentCrearPunto.class;

        } else if(id == R.id.nav_rutero_vendedor) {
            toolbar.setTitle("Mi Rutero");
            editaPunto = 0; accion = "Guardar";
            fragmentClass = FragmentRuteroVendedor.class;

        }else if(id == R.id.nav_pedido_vendedor) {
            toolbar.setTitle("Mis Pedidos");
            editaPunto = 0; accion = "Guardar";
            fragmentClass = FragmentMisPedidos.class;

        }else if(id == R.id.nav_baja_vendedor) {
            toolbar.setTitle("Mis Bajas");
            editaPunto = 0; accion = "Guardar";
            fragmentClass = FragmentMisBajas.class;

        }


        try {

            Fragment fragment = (Fragment) fragmentClass.newInstance();
            if(id == R.id.nav_gestion_pdv)
            {
                Bundle args = new Bundle();
                args.putInt("idpos", editaPunto);
                args.putString("accion", accion);
                fragment.setArguments(args);
            }
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
