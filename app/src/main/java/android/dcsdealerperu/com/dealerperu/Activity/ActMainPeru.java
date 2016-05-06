package android.dcsdealerperu.com.dealerperu.Activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmenMarcarvisita;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentCrearPunto;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentHome;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentInventario;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentProducto;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentRutero;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentRuteroVendedor;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentSimcard;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentVentas;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentVisitaPdv;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

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
    private FragmentRuteroVendedor fragmentRuteroVendedor;
    private FragmentSimcard fragmentSimcard;
    private FragmenMarcarvisita fragmenMarcarvisita;
    private FragmentVentas fragmentVentas;
    private FragmentVisitaPdv fragmentVisitaPdv;
    private SpotsDialog alertDialog;
    private RequestQueue rq;
    private CoordinatorLayout coordinatorLayout;
    public static final String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_peru);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);

        alertDialog = new SpotsDialog(this, R.style.Custom);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.mensaje_id);

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        final FragmentManager fManager = getFragmentManager();

        if (id == R.id.nav_home) {

            toolbar.setTitle("Inicio");
            if (fragmentHome == null)
                fragmentHome = new FragmentHome();

            fManager.beginTransaction().replace(R.id.contentPanel, fragmentHome).commit();

        } else if(id == R.id.nav_marcar_visita) {

            toolbar.setTitle("Marcar Visita");

            implementarFragmetMarcarPunto(fManager);

        } else if(id == R.id.nav_gestion_pdv) {

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

        } else if(id == R.id.nav_rutero_vendedor){
            toolbar.setTitle("Mi Rutero");
            if (fragmentRuteroVendedor == null)
                fragmentRuteroVendedor = new FragmentRuteroVendedor();

            fManager.beginTransaction().replace(R.id.contentPanel, fragmentRuteroVendedor).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private boolean isValidNumber(String number){return number == null || number.length() == 0;}

    private void implementarFragmetMarcarPunto(final FragmentManager fManager) {

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_marcar_visita, null);

        final EditText buscar = (EditText) dialoglayout.findViewById(R.id.edit_pos);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Consultar Punto");
        builder.setView(dialoglayout).setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (isValidNumber(buscar.getText().toString().trim())) {

                } else {
                    buscarIdPos(buscar, fManager);
                }


            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    private void buscarIdPos(final EditText buscar, final FragmentManager fManager) {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base),"buscar_punto_visita");
        rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSONVisita(response, fManager);

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActMainPeru.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActMainPeru.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActMainPeru.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActMainPeru.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActMainPeru.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));
                params.put("idpos", buscar.getText().toString().trim());

                return params;
            }
        };

        rq.add(jsonRequest);
    }

    private void parseJSONVisita(String response, FragmentManager fManager) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                ResponseMarcarPedido responseMarcarPedido = gson.fromJson(response, ResponseMarcarPedido.class);

                if (responseMarcarPedido.getEstado() == -1) {
                    //No tiene permisos del punto
                    Snackbar.make(coordinatorLayout, responseMarcarPedido.getMsg(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (responseMarcarPedido.getEstado() == -2) {
                    //El punto no existe
                    Snackbar.make(coordinatorLayout, responseMarcarPedido.getMsg(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {



                    fragmenMarcarvisita = new FragmenMarcarvisita();
                    fManager.beginTransaction().replace(R.id.contentPanel, fragmenMarcarvisita.newInstance(responseMarcarPedido)).commit();
                }

            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                alertDialog.dismiss();
            } finally {
                alertDialog.dismiss();
            }
        } else {
            alertDialog.dismiss();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (rq != null) {
            rq.cancelAll(TAG);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rq != null) {
            rq.cancelAll(TAG);
        }
    }

}
