package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.DataBase.DBHelper;
import android.dcsdealerperu.com.dealerperu.Entry.ListSincronizarRepartidor;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseHome;
import android.dcsdealerperu.com.dealerperu.Entry.Sincronizar;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmenEntregarPedido;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmenMarcarvisita;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentAceptPedido;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentBajasSupervisor;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentHome;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentHomeRep;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentHomeSuperPrin;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentInventarioRepartidor;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentMisBajas;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentMisPedidos;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentPedidosSupervisor;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentPlanificar;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentReporteAprobacionPdv;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentReportePedidosRepartidor;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmentRuteroVendedor;
import android.dcsdealerperu.com.dealerperu.Fragment.FragmenteAproPdv;
import android.dcsdealerperu.com.dealerperu.R;
import android.dcsdealerperu.com.dealerperu.Services.ConnectionDetector;
import android.dcsdealerperu.com.dealerperu.Services.MonitoringService;
import android.dcsdealerperu.com.dealerperu.Services.SetTracingServiceWeb;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class ActMainPeru extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    FragmentManager fragmentManager;
    private DBHelper mydb;
    private int editaPunto;
    private String accion = "Guardar";
    private SpotsDialog alertDialog;
    private RequestQueue rq;
    public static final String TAG = "MyTag";
    private String responseGlobal;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_peru);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);

        //Colocar en una Estacion de radio..
        startService(new Intent(this, MonitoringService.class));

        startService(new Intent(this, SetTracingServiceWeb.class));

        fragmentManager = getSupportFragmentManager();

        connectionDetector = new ConnectionDetector(this);

        alertDialog = new SpotsDialog(this, R.style.Custom);

        mydb = new DBHelper(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 1 supervisor
        // 2 vendedor
        // 3 repartidor

        if(getResponseUserStatic() != null) {
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
        } else {
            Intent intent = new Intent(this, ActLoginUser.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }


        View header = navigationView.getHeaderView(0);

        TextView txt_sub = (TextView) header.findViewById(R.id.txt_sub);
        txt_sub.setText(String.format("%1$s %2$s", getResponseUserStatic().getNombre(), getResponseUserStatic().getApellido()));

        // Accion Para la Edicion del
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            editaPunto = bundle.getInt("edit_punto");
            int accionNav = bundle.getInt("accion");

            if (accionNav == 1) {
                accion = "Editar";
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_gestion_pdv));
            }

            if (accionNav == 2) {
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_marcar_visita));
            }

            if (accionNav == 3) {
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_entregar_pedido));
            }

        } else {
            if(getResponseUserStatic().getPerfil() == 2) {
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home));
            } else if(getResponseUserStatic().getPerfil() == 3){
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home_repartidor));
            } else if(getResponseUserStatic().getPerfil() == 1){
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home_super));
            }
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

        if (connectionDetector.isConnected()) {
            if (getResponseUserStatic().getPerfil() == 2) {
                //Vendedor
                offLineDataVendedor();
            } else if (getResponseUserStatic().getPerfil() == 3) {
                //Repartidor
                offLineDataRepartidor();
            } else if (getResponseUserStatic().getPerfil() == 1) {
                //Supervisor
            }
        }

        int id = item.getItemId();

        Class fragmentClass = null;

        if (id == R.id.nav_home) {
            toolbar.setTitle("Inicio Vendedor");
            editaPunto = 0;
            accion = "Guardar";
            fragmentClass = FragmentHome.class;

        } else if (id == R.id.nav_home_repartidor) {
            toolbar.setTitle("Inicio Repartidor");
            editaPunto = 0;
            accion = "Guardar";
            fragmentClass = FragmentHomeRep.class;

        }else if (id == R.id.nav_home_super) {
            toolbar.setTitle("Inicio Supervisor");
            editaPunto = 0;
            accion = "Guardar";
            fragmentClass = FragmentHomeSuperPrin.class;

        } else if (id == R.id.nav_marcar_visita) {
            toolbar.setTitle("Marcar Visita");
            editaPunto = 0;
            accion = "Guardar";
            fragmentClass = FragmenMarcarvisita.class;

        } else if (id == R.id.nav_planificar_punto) {
            if (connectionDetector.isConnected()) {
                toolbar.setTitle("Planificar Visita");
                fragmentClass = FragmentPlanificar.class;
            } else {
                Toast.makeText(this, "Esta opción solo es permitida si tiene internet", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_acpetar_pedido) {
            toolbar.setTitle("Aceptar Pedido");
            if (connectionDetector.isConnected()) {
                fragmentClass = FragmentAceptPedido.class;
            } else {
                Toast.makeText(this, "Esta opción solo es permitida si tiene internet", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_gestion_pdv) {
            toolbar.setTitle("Gestión PDVS");

            if (connectionDetector.isConnected()) {
                cargarVistaPunto();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Offline");
                builder.setMessage("¿ Estas seguro crear un punto Offline ?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cargarVistaPunto();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }

        } else if (id == R.id.nav_rutero_vendedor) {
            if (connectionDetector.isConnected()) {
                toolbar.setTitle("Mi Rutero");
                editaPunto = 0;
                accion = "Guardar";
                fragmentClass = FragmentRuteroVendedor.class;
            } else {
                Toast.makeText(this, "Esta opción solo es permitida si tiene internet", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_pedido_vendedor) {

            if (connectionDetector.isConnected()) {
                toolbar.setTitle("Mis Pedidos");
                editaPunto = 0;
                accion = "Guardar";
                fragmentClass = FragmentMisPedidos.class;
            } else {
                Toast.makeText(this, "Esta opción solo es permitida si tiene internet", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_pedido_super) {
            toolbar.setTitle("Reporte de Pedidos");
            editaPunto = 0;
            accion = "Guardar";
            fragmentClass = FragmentPedidosSupervisor.class;

        } else if (id == R.id.nav_baja_vendedor) {
            if (connectionDetector.isConnected()) {
                toolbar.setTitle("Mis Bajas");
                editaPunto = 0;
                accion = "Guardar";
                fragmentClass = FragmentMisBajas.class;
            } else {
                Toast.makeText(this, "Esta opción solo es permitida si tiene internet", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_pdv_aprp) {
            toolbar.setTitle("Aprobación PDVS");
            editaPunto = 0;
            accion = "Guardar";
            fragmentClass = FragmenteAproPdv.class;
        } else if (id == R.id.nav_aprobaciones_super) {
            toolbar.setTitle("Reporte Aprobación PDVS");
            editaPunto = 0;
            accion = "Guardar";
            fragmentClass = FragmentReporteAprobacionPdv.class;
        } else if (id == R.id.nav_cerrar_sesion) {

            /*Intent intent = new Intent(this, ActLoginUser.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();*/

        } else if (id == R.id.nav_entregar_pedido) {
            toolbar.setTitle("Entregar Pedido");

            if (connectionDetector.isConnected()) {
                editaPunto = 0;
                accion = "Guardar";
                fragmentClass = FragmenEntregarPedido.class;
            } else {
                Toast.makeText(this, "Esta opción solo es permitida si tiene internet", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_bajas_super) {
            toolbar.setTitle("Reporte de Bajas");
            editaPunto = 0;
            accion = "Guardar";
            fragmentClass = FragmentBajasSupervisor.class;
        } else if (id == R.id.nav_inventario) {
            toolbar.setTitle("Mi Inventario");
            if (connectionDetector.isConnected()) {
                editaPunto = 0;
                accion = "Guardar";
                fragmentClass = FragmentInventarioRepartidor.class;
            } else {
                Toast.makeText(this, "Esta opción solo es permitida si tiene internet", Toast.LENGTH_LONG).show();
            }

        }else if (id == R.id.nav_mis_pedidos_rep) {

            if (connectionDetector.isConnected()) {
                toolbar.setTitle("Mis Pedidos");
                editaPunto = 0;
                accion = "Guardar";
                fragmentClass = FragmentReportePedidosRepartidor.class;
            } else {
                Toast.makeText(this, "Esta opción solo es permitida si tiene internet", Toast.LENGTH_LONG).show();
            }
        }
        try {

            Fragment fragment = (Fragment) fragmentClass.newInstance();

            fragmentManager.beginTransaction().replace(R.id.contentPanel, fragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void offLineDataRepartidor() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "servicio_offline");
        rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSONRepartidor(response);
                    }
                },
                new Response.ErrorListener() {
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

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("fecha", String.valueOf(mydb.getUserLogin(getResponseUserStatic().getUser()).getFechaSincro()));
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));
                params.put("db", getResponseUserStatic().getBd());

                return params;
            }
        };

        rq.add(jsonRequest);
    }

    private void parseJSONRepartidor(final String response) {
        new Thread(new Runnable() {
            public void run() {

                responseGlobal = response;
                Gson gson = new Gson();
                byte[] gzipBuff = new byte[0];
                try {
                    gzipBuff = android.dcsdealerperu.com.dealerperu.Adapter.Base64.decode(responseGlobal);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                responseGlobal = ZipUtils.unzipString(new String(gzipBuff));

                ListSincronizarRepartidor sincronizar = gson.fromJson(responseGlobal, ListSincronizarRepartidor.class);

                mydb.deleteObject("pedido_entrega");
                mydb.deleteObject("pedido_repartidor");
                mydb.deleteObject("pedidos_grupo");
                mydb.deleteObject("deta_pedido");
                mydb.insertEntregaPedidos(sincronizar);

                runOnUiThread(new Runnable() {
                    public void run() {
                        if (alertDialog != null)
                            alertDialog.dismiss();

                    }
                });
            }
        }).start();
    }

    private void offLineDataVendedor() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "servicio_offline");
        rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
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

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("fecha", String.valueOf(mydb.getUserLogin(getResponseUserStatic().getUser()).getFechaSincro()));
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));
                params.put("db", getResponseUserStatic().getBd());

                return params;
            }
        };

        rq.add(jsonRequest);
    }

    private void cargarVistaPunto() {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, ActCrearPdvuno.class);
        bundle.putString("accion", accion);
        bundle.putInt("idpos", editaPunto);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void parseJSON(final String response) {

        new Thread(new Runnable() {
            public void run() {

                responseGlobal = response;
                Gson gson = new Gson();
                byte[] gzipBuff = new byte[0];
                try {
                    gzipBuff = android.dcsdealerperu.com.dealerperu.Adapter.Base64.decode(responseGlobal);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                responseGlobal = ZipUtils.unzipString(new String(gzipBuff));

                Sincronizar sincronizar = gson.fromJson(responseGlobal, Sincronizar.class);
                mydb.updateFechaSincro(sincronizar.getFecha_sincroniza(), getResponseUserStatic().getId());

                if (sincronizar.getTerritoriosList().size() > 0) {
                    mydb.deleteObject("territorio");
                    mydb.insertTerritorio(sincronizar);
                }

                if (sincronizar.getZonaList().size() > 0) {
                    mydb.deleteObject("zona");
                    mydb.insertZona(sincronizar);
                }

                if (sincronizar.getPuntosList().size() > 0) {
                    mydb.deleteObject("punto");
                    mydb.insertPunto(sincronizar, 0);
                }

                mydb.insertCategoria(sincronizar);
                mydb.insertDepartamento(sincronizar);
                mydb.insertDistritos(sincronizar);
                mydb.insertEstadoComercial(sincronizar);
                mydb.insertMunicipios(sincronizar);
                mydb.insertNomenclaturas(sincronizar);
                mydb.insertSubcategoriasPuntos(sincronizar);

                mydb.insertReferenciaSim(sincronizar);
                mydb.insertReferenciaCombos(sincronizar);
                mydb.insertLisPrecios(sincronizar);

                runOnUiThread(new Runnable() {
                    public void run() {

                        if (alertDialog != null)
                            alertDialog.dismiss();

                    }
                });
            }
        }).start();

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
