package android.dcsdealerperu.com.dealerperu.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Adapter.ItemAdapter;
import android.dcsdealerperu.com.dealerperu.Entry.ListResponsePlaniVisita;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;

public class ActPlanificarOrdenar extends AppCompatActivity {

    private List<Pair<Long, String>> mItemArray;
    private DragListView mDragListView;
    private Bundle bundle;
    private List<ResponseMarcarPedido> mDescribable = new ArrayList<>();
    private SpotsDialog alertDialog;
    private RequestQueue rq;
    public static final String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planificar_ordenar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alertDialog = new SpotsDialog(this, R.style.Custom);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (List<ResponseMarcarPedido>) bundle.getSerializable("value");
        }

        mDragListView = (DragListView) findViewById(R.id.drag_list_view);

        mDragListView.getRecyclerView().setVerticalScrollBarEnabled(true);
        mDragListView.setDragListListener(new DragListView.DragListListenerAdapter() {
            @Override
            public void onItemDragStarted(int position) {

            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {

            }
        });

        mItemArray = new ArrayList<>();
        for (int i = 0; i < mDescribable.size(); i++) {
            String texto_html = mDescribable.get(i).getId_pos() + " -- "+mDescribable.get(i).getRazon_social()+ "<br>";
            texto_html += "Direccion: "+mDescribable.get(i).getDireccion()+ "<br>";
            int stockCombo;
            int stockSimcard;
            boolean quiebre = false;

            if (mDescribable.get(i).getStock_combo() < mDescribable.get(i).getStock_seguridad_combo()) {
                stockCombo = mDescribable.get(i).getStock_seguridad_combo();
            } else {
                stockCombo = mDescribable.get(i).getStock_combo();
                quiebre = true;
            }

            if (mDescribable.get(i).getStock_sim() < mDescribable.get(i).getStock_seguridad_sim()) {
                stockSimcard = mDescribable.get(i).getStock_seguridad_sim();
            } else {
                stockSimcard = mDescribable.get(i).getStock_sim();
                quiebre = true;
            }

            if (stockCombo < stockSimcard) {
                texto_html += "Stock: " + String.valueOf(stockSimcard);
            } else {
                texto_html += "Stock: " + String.valueOf(stockCombo);
            }

            if (mDescribable.get(i).getDias_inve_combo() < mDescribable.get(i).getDias_inve_sim()) {
                if (mDescribable.get(i).getDias_inve_combo() == 0)
                    texto_html += " &nbsp; &nbsp; &nbsp; D. Inve: N/A";
                else
                    texto_html += " &nbsp; &nbsp; &nbsp; D. Inve: "+mDescribable.get(i).getDias_inve_combo()+"";
            } else {
                if (mDescribable.get(i).getDias_inve_sim() == 0)
                    texto_html += " &nbsp; &nbsp; &nbsp; D. Inve: N/A";
                else
                    texto_html += " &nbsp; &nbsp; &nbsp; D. Inve: "+mDescribable.get(i).getDias_inve_sim()+"";
            }

            if (quiebre) {
                texto_html += " &nbsp; &nbsp; &nbsp; Qui: SI";
            }else{
                texto_html += " &nbsp; &nbsp; &nbsp; Qui: NO";
            }
            mItemArray.add(new Pair<>(Long.valueOf(mDescribable.get(i).getId_pos()), texto_html));
        }

        FloatingActionButton btn_guardar = (FloatingActionButton) findViewById(R.id.guardar_planificacion);
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ActPlanificarOrdenar.this);
                builder.setCancelable(false);
                builder.setTitle("Alerta");
                builder.setMessage("¿ Estas seguro de guardar la planificacion ?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        loginServices();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        setupListRecyclerView();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_main_orden_pla, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ActPlanificarOrdenar.this);
            builder.setCancelable(false);
            builder.setTitle("Alerta");
            builder.setMessage("¿ Estas seguro de guardar la planificacion ?");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    loginServices();
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupListRecyclerView() {
        mDragListView.setLayoutManager(new LinearLayoutManager(this));
        ItemAdapter listAdapter = new ItemAdapter(mItemArray, R.layout.list_item_drog, R.id.image_drag, false);
        mDragListView.setAdapter(listAdapter, true);
        mDragListView.setCanDragHorizontally(false);
        mDragListView.setCustomDragItem(new MyDragItem(this, R.layout.list_item_drog));

    }

    private void loginServices() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "guardar_planificacion");
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
                            Toast.makeText(ActPlanificarOrdenar.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActPlanificarOrdenar.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActPlanificarOrdenar.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActPlanificarOrdenar.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActPlanificarOrdenar.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                List<ResponseMarcarPedido> list = new ArrayList<>();

                ResponseMarcarPedido responseMarcarPedido;

                for (int i = 0; i < mDragListView.getAdapter().getItemList().size(); i++) {
                    responseMarcarPedido = new ResponseMarcarPedido();

                    responseMarcarPedido.setId_pos((int) mDragListView.getRecyclerView().getAdapter().getItemId(i));
                    responseMarcarPedido.setPuntoPlanificacion(i);

                    list.add(responseMarcarPedido);
                }

                String parJSON = new Gson().toJson(list, ListResponsePlaniVisita.class);

                params.put("datos", parJSON);

                params.put("iduser", String.valueOf(getResponseUserStatic().getId()));
                params.put("iddis", getResponseUserStatic().getId_distri());
                params.put("db", getResponseUserStatic().getBd());
                params.put("perfil", String.valueOf(getResponseUserStatic().getPerfil()));

                return params;
            }
        };

        rq.add(jsonRequest);
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {

                Charset.forName("UTF-8").encode(response);

                byte ptext[] = response.getBytes(Charset.forName("ISO-8859-1"));

                String value = new String(ptext, Charset.forName("UTF-8"));

                ResponseMarcarPedido responseMarcarPedido = gson.fromJson(value, ResponseMarcarPedido.class);

                if (responseMarcarPedido.getEstado() == -1) {
                    //El punto no tiene regional
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else if (responseMarcarPedido.getEstado() == 0) {
                    //Error al intentar el pedido no tiene zona o territorio
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();

                    startActivity(new Intent(this, ActMainPeru.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();

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

    private static class MyDragItem extends DragItem {

        public MyDragItem(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindDragView(View clickedView, View dragView) {
            CharSequence text = ((TextView) clickedView.findViewById(R.id.text)).getText();
            ((TextView) dragView.findViewById(R.id.text)).setText(text);
            dragView.setBackgroundColor(dragView.getResources().getColor(R.color.list_item_background));
        }
    }

}
