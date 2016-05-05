package android.dcsdealerperu.com.dealerperu.Activity;

import android.app.FragmentManager;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentCrearPunto;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentHome;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentInventario;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentProducto;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentRutero;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentSimcard;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentSolPedido;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentVentas;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentVisitaPdv;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class ActMainPeru extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private FragmentCrearPunto fragmentCrearPunto;
    private FragmentHome fragmentHome;
    private FragmentInventario fragmentInventario;
    private FragmentProducto fragmentProducto;
    private FragmentRutero fragmentRutero;
    private FragmentSimcard fragmentSimcard;
    private FragmentSolPedido fragmentSolPedido;
    private FragmentVentas fragmentVentas;
    private FragmentVisitaPdv fragmentVisitaPdv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_peru);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        TextView txt_sub = (TextView) header.findViewById(R.id.txt_sub);
        txt_sub.setText(String.format("%1$s %2$s", getResponseUserStatic().getNombre(), getResponseUserStatic().getApellido()));

        FragmentManager fManager = getFragmentManager();
        fragmentHome = new FragmentHome();
        fManager.beginTransaction().replace(R.id.contentPanel, fragmentHome).commit();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_main_peru, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fManager = getFragmentManager();

        if (id == R.id.nav_home) {

            toolbar.setTitle("Inicio");
            if (fragmentHome == null)
                fragmentHome = new FragmentHome();

            fManager.beginTransaction().replace(R.id.contentPanel, fragmentHome).commit();

        } else if(id == R.id.nav_sol_producto) {

            toolbar.setTitle("Solicitar Producto");
            if (fragmentSolPedido == null)
                fragmentSolPedido = new FragmentSolPedido();

            fManager.beginTransaction().replace(R.id.contentPanel, fragmentSolPedido).commit();

        } else if(id == R.id.nav_crear_punto) {

            toolbar.setTitle("Crear Punto");
            if (fragmentCrearPunto == null)
                fragmentCrearPunto = new FragmentCrearPunto();

            fManager.beginTransaction().replace(R.id.contentPanel, fragmentCrearPunto).commit();

        } else if(id == R.id.nav_pdv) {

            toolbar.setTitle("Visita PDV");
            if (fragmentVisitaPdv == null)
                fragmentVisitaPdv = new FragmentVisitaPdv();

            fManager.beginTransaction().replace(R.id.contentPanel, fragmentVisitaPdv).commit();

        } else if(id == R.id.nav_simcard) {

            toolbar.setTitle("Venta Simcard");
            if (fragmentSimcard == null)
                fragmentSimcard = new FragmentSimcard();

            fManager.beginTransaction().replace(R.id.contentPanel, fragmentSimcard).commit();

        } else if(id == R.id.nav_producto) {

            toolbar.setTitle("Venta Producto");
            if (fragmentProducto == null)
                fragmentProducto = new FragmentProducto();

            fManager.beginTransaction().replace(R.id.contentPanel, fragmentProducto).commit();

        } else if(id == R.id.nav_reporte) {

            toolbar.setTitle("Mi inventario");
            if (fragmentInventario == null)
                fragmentInventario = new FragmentInventario();

            fManager.beginTransaction().replace(R.id.contentPanel, fragmentInventario).commit();

        } else if(id == R.id.nav_ventas) {

            toolbar.setTitle("Mis ventas");
            if (fragmentVentas == null)
                fragmentVentas = new FragmentVentas();

            fManager.beginTransaction().replace(R.id.contentPanel, fragmentVentas).commit();

        } else if(id == R.id.nav_rutero) {

            toolbar.setTitle("Mi Rutero");
            if (fragmentRutero == null)
                fragmentRutero = new FragmentRutero();

            fManager.beginTransaction().replace(R.id.contentPanel, fragmentRutero).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
