package android.dcsdealerperu.com.dealerperu.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.AdapterMisPedidos;
import android.dcsdealerperu.com.dealerperu.Entry.MisPedidos;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.dcsdealerperu.com.dealerperu.R;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class ActReporteMisPedidos extends AppCompatActivity {

    private MisPedidos mDescribable;
    private Bundle bundle;
    private AdapterMisPedidos adapterMisPedidos;
    private DecimalFormat format;
    private String comentario;
    private SpotsDialog alertDialog;
    private RequestQueue rq;

    private SwipeMenuListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_mis_pedidos);

        format = new DecimalFormat("#,###.##");

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (MisPedidos)bundle.getSerializable("value");
        }
        alertDialog = new SpotsDialog(this, R.style.Custom);

        mListView = (SwipeMenuListView) findViewById(R.id.listView);
        //mListView = (SwipeMenuListView) findViewById(R.drawable.i);
        adapterMisPedidos = new AdapterMisPedidos(this, mDescribable.getResponseMisPedidosList());
        mListView.setAdapter(adapterMisPedidos);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(16, 98,138)));
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

                // create "open" item
                SwipeMenuItem openItem2 = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem2.setBackground(new ColorDrawable(Color.rgb(219, 68, 55)));
                // set item width
                openItem2.setWidth(dp2px(90));
                // set item title
                openItem2.setTitle("Cancelar");
                // set item title fontsize
                openItem2.setTitleSize(18);
                // set item title font color
                openItem2.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem2);
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
                        //Cancelar Pedido
                        LayoutInflater inflater = getLayoutInflater();
                        View dialoglayout = inflater.inflate(R.layout.dialog_comentario_aproba, null);

                        final EditText editTextComent = (EditText) dialoglayout.findViewById(R.id.EditComment);

                        android.support.v7.app.AlertDialog.Builder builder2 = new android.support.v7.app.AlertDialog.Builder(ActReporteMisPedidos.this);
                        builder2.setCancelable(false);
                        builder2.setTitle("Motivo de Cancelación");
                        builder2.setView(dialoglayout).setPositiveButton("Cancelar Pedido", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if (isValidNumber(editTextComent.getText().toString().trim())) {
                                    Toast.makeText(ActReporteMisPedidos.this, "El comentario es un campo requerido", Toast.LENGTH_LONG).show();
                                } else {
                                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ActReporteMisPedidos.this);
                                    builder.setCancelable(false);
                                    builder.setTitle("Alerta");
                                    builder.setMessage("¿ Estas seguro de cancelar el pedido ?");
                                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            comentario = editTextComent.getText().toString();
                                            cancelarPedido(position);
                                        }

                                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.show();

                                }

                            }
                        }).setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                        builder2.show();
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

    private boolean isValidNumber(String number){return number == null || number.length() == 0;}

    private void cancelarPedido(int position) {
        alertDialog.show();
        final int idpos =  mDescribable.getResponseMisPedidosList().get(position).getIdpos();
        final int idpedido =  mDescribable.getResponseMisPedidosList().get(position).getNpedido();

        String url = String.format("%1$s%2$s", getString(R.string.url_base),"cancelar_toma_pedido");
        rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // response
                        respuestaCancelarPedido(response);

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActReporteMisPedidos.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActReporteMisPedidos.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActReporteMisPedidos.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActReporteMisPedidos.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActReporteMisPedidos.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
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
                params.put("db", getResponseUserStatic().getBd());
                params.put("idpos", String.valueOf(idpos));
                params.put("idpedido", String.valueOf(idpedido));
                params.put("motivo", comentario);

                return params;
            }
        };

        rq.add(jsonRequest);
    }

    private void respuestaCancelarPedido(String response) {
        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                Charset.forName("UTF-8").encode(response);

                byte ptext[] = response.getBytes(Charset.forName("ISO-8859-1"));

                String value = new String(ptext, Charset.forName("UTF-8"));

                ResponseMarcarPedido responseMarcarPedido = gson.fromJson(value, ResponseMarcarPedido.class);

                if (responseMarcarPedido.getEstado() == 0) {
                    //Se guardó bien.!
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == -2) {
                    //Error al intentar guardar la cancelacion del pedido
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
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

    private void cargarDetalle(int position) {

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_detalle_mis_pedidos, null);

        TextView txt_idpos = (TextView) dialoglayout.findViewById(R.id.txt_idpos);
        txt_idpos.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getIdpos()));

        TextView txt_npedido = (TextView) dialoglayout.findViewById(R.id.txt_npedido);
        txt_npedido.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getNpedido()));

        TextView txt_cantidad = (TextView) dialoglayout.findViewById(R.id.txt_cantidad);
        txt_cantidad.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getCantidad()));

        TextView txt_total = (TextView) dialoglayout.findViewById(R.id.txt_total);
        txt_total.setText(String.format("$ %1$s", format.format(mDescribable.getResponseMisPedidosList().get(position).getTotal())));

        TextView txt_cant_picking = (TextView) dialoglayout.findViewById(R.id.txt_cant_picking);
        txt_cant_picking.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getCantidad_picking()));

        TextView txt_total_picking = (TextView) dialoglayout.findViewById(R.id.txt_total_picking);
        txt_total_picking.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getTotal_picking()));

        TextView txt_hora = (TextView) dialoglayout.findViewById(R.id.txt_hora);
        txt_hora.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getHora()));

        TextView txt_nombre_punto = (TextView) dialoglayout.findViewById(R.id.txt_nombre_punto);
        txt_nombre_punto.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getNombre_punto()));

        TextView txt_fecha = (TextView) dialoglayout.findViewById(R.id.txt_fecha);
        txt_fecha.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getFecha()));

        TextView txt_estado = (TextView) dialoglayout.findViewById(R.id.txt_estado);
        txt_estado.setText(String.format("%1$s", mDescribable.getResponseMisPedidosList().get(position).getEstado()));


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Detalle Pedido");
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
}
