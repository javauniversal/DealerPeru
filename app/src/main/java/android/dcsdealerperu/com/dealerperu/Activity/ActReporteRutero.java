package android.dcsdealerperu.com.dealerperu.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.AppAdapterRutero;
import android.dcsdealerperu.com.dealerperu.Entry.ListHome;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.dcsdealerperu.com.dealerperu.R;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseHome.getResponseHomeListS;
import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class ActReporteRutero extends AppCompatActivity {
    private static final String DESCRIBABLE_KEY = "describable_key";
    private ListHome mDescribable = new ListHome();
    private SpotsDialog alertDialog;
    private AppAdapterRutero appAdapterRutero;
    private SwipeMenuListView mListView;

    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_rutero);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //recycler = (RecyclerView) findViewById(R.id.recycler_view);
        alertDialog = new SpotsDialog(this, R.style.Custom);

        mListView = (SwipeMenuListView) findViewById(R.id.listView);
        //mListView = (SwipeMenuListView) findViewById(R.drawable.i);
        appAdapterRutero = new AppAdapterRutero(this, (ListHome) getResponseHomeListS());
        mListView.setAdapter(appAdapterRutero);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(16, 98, 138)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Detalle");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                SwipeMenuItem openItem2 = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem2.setBackground(new ColorDrawable(Color.rgb(251, 192, 45)));
                // set item width
                openItem2.setWidth(dp2px(90));
                // set item title
                openItem2.setTitle("Editar");
                // set item title fontsize
                openItem2.setTitleSize(18);
                // set item title font color
                openItem2.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem2);

                SwipeMenuItem openItem3 = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem3.setBackground(new ColorDrawable(Color.rgb(216, 216, 216)));
                // set item width
                openItem3.setWidth(dp2px(90));
                // set item title
                openItem3.setTitle("Visitar");
                // set item title fontsize
                openItem3.setTitleSize(18);
                // set item title font color
                openItem3.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem3);
            }
        };

// set creator
        mListView.setMenuCreator(creator);

// step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                //AddProductCar item = mAppList.get(position);
                switch (index) {
                    case 0:
                        cargarDetalle(position);
                        break;
                    case 1:
                        EditarPunto(position);
                        break;
                    case 2:
                        buscarIdPos(getResponseHomeListS().get(position).getIdpos());
                        break;
                }
                return false;
            }
        });

// set SwipeListener
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

// test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //deletePrduct(position);
                return false;
            }
        });
    }

    private void EditarPunto(int position) {
        int idpos = 0;
        idpos = getResponseHomeListS().get(position).getIdpos();
        //Activity Principal, Para acceder al fragment
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, ActMainPeru.class);
        bundle.putInt("edit_punto", idpos);
        bundle.putInt("accion", 1);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    private void cargarDetalle(int position) {

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_detalle, null);

        TextView txt_id_numero = (TextView) dialoglayout.findViewById(R.id.txt_id_numero);
        txt_id_numero.setText(String.format("%1$s", getResponseHomeListS().get(position).getIdpos()));

        TextView txt_nombre = (TextView) dialoglayout.findViewById(R.id.nombre_punto);
        txt_nombre.setText(String.format("%1$s", getResponseHomeListS().get(position).getRazon()));

        TextView txt_visitado = (TextView) dialoglayout.findViewById(R.id.txt_visitado);
        String visita;
        if (getResponseHomeListS().get(position).getTipo_visita() == 0)
            visita = "No";
        else
            visita = "Si";

        txt_visitado.setText(String.format("%1$s", visita));

        TextView txt_direccion = (TextView) dialoglayout.findViewById(R.id.txt_direccion);
        txt_direccion.setText(String.format("%1$s", getResponseHomeListS().get(position).getDireccion()));

        TextView txt_distrito = (TextView) dialoglayout.findViewById(R.id.txt_distrito);
        txt_distrito.setText(String.format("%1$s", getResponseHomeListS().get(position).getDistrito()));

        TextView txt_telefono = (TextView) dialoglayout.findViewById(R.id.txt_telefono);
        txt_telefono.setText(String.format("%1$s", getResponseHomeListS().get(position).getTel()));

        TextView txt_dias = (TextView) dialoglayout.findViewById(R.id.txt_dias);
        txt_dias.setText(String.format("%1$s", getResponseHomeListS().get(position).getDetalle()));

        String fecha_hora = getResponseHomeListS().get(position).getFecha_ult()+"  "+getResponseHomeListS().get(position).getHora_ult();
        if(fecha_hora.trim().isEmpty())
            fecha_hora = "N/A";

        TextView txt_hora_visita = (TextView) dialoglayout.findViewById(R.id.txt_hora_visita);
        txt_hora_visita.setText(String.format("%1$s",fecha_hora));

        TextView txt_stock_c = (TextView) dialoglayout.findViewById(R.id.txt_stock_c);
        txt_stock_c.setText(String.format("%1$s", getResponseHomeListS().get(position).getStock_combo()));

        TextView txt_stock_s = (TextView) dialoglayout.findViewById(R.id.txt_stock_s);
        txt_stock_s.setText(String.format("%1$s", getResponseHomeListS().get(position).getStock_sim()));

        TextView txt_dias_s = (TextView) dialoglayout.findViewById(R.id.txt_dias_s);
        txt_dias_s.setText(String.format("%1$s", getResponseHomeListS().get(position).getDias_inve_sim()));

        TextView txt_dias_c = (TextView) dialoglayout.findViewById(R.id.txt_dias_c);
        txt_dias_c.setText(String.format("%1$s", getResponseHomeListS().get(position).getDias_inve_combo()));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Detalle");
        builder.setView(dialoglayout).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private void buscarIdPos(final int idPos) {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "buscar_punto_visita");
        rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSONVisita(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActReporteRutero.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActReporteRutero.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActReporteRutero.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActReporteRutero.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActReporteRutero.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));
                params.put("idpos", String.valueOf(idPos));

                return params;

            }
        };

        rq.add(jsonRequest);
    }

    private void parseJSONVisita(String response) {

        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                ResponseMarcarPedido responseMarcarPedido = gson.fromJson(response, ResponseMarcarPedido.class);

                if (responseMarcarPedido.getEstado() == -1) {
                    //No tiene permisos del punto
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == -2) {
                    //El punto no existe
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else {
                    //Activity Detalle
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(this, ActMarcarVisita.class);
                    bundle.putSerializable("value", responseMarcarPedido);
                    bundle.putString("page", "marcar_rutero");
                    intent.putExtras(bundle);
                    startActivity(intent);
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

}
