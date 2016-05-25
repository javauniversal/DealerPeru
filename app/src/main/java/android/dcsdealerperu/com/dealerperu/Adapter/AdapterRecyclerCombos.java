package android.dcsdealerperu.com.dealerperu.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Activity.ActDetalleComboPedir;
import android.dcsdealerperu.com.dealerperu.Activity.ActDetalleProducto;
import android.dcsdealerperu.com.dealerperu.DataBase.DBHelper;
import android.dcsdealerperu.com.dealerperu.Entry.ReferenciasCombos;
import android.dcsdealerperu.com.dealerperu.Entry.RowViewHolderCombo;
import android.dcsdealerperu.com.dealerperu.R;
import android.dcsdealerperu.com.dealerperu.Services.ConnectionDetector;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterRecyclerCombos extends RecyclerView.Adapter<RowViewHolderCombo> {

    private Activity context;
    private List<ReferenciasCombos> responseHomeList;
    private DecimalFormat format;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader1;
    private DisplayImageOptions options1;
    private DBHelper mydb;
    private int iduser;
    private int idpos;
    private ConnectionDetector connectionDetector;

    public AdapterRecyclerCombos(Activity context, List<ReferenciasCombos> responseHomeList, int idpos, int iduser) {
        super();
        this.context = context;
        this.responseHomeList = responseHomeList;
        this.iduser = iduser;
        this.idpos = idpos;
        format = new DecimalFormat("#,###.##");

        connectionDetector = new ConnectionDetector(context);

        mydb = new DBHelper(context);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        imageLoader1 = ImageLoader.getInstance();
        imageLoader1.init(config);

        //Setup options for ImageLoader so it will handle caching for us.
        options1 = new DisplayImageOptions.Builder()
                .cacheInMemory()
                .cacheOnDisc()
                .build();

    }

    @Override
    public RowViewHolderCombo onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_combos, parent, false);
        return new RowViewHolderCombo(v, context);

    }

    @Override
    public void onBindViewHolder(final RowViewHolderCombo holder, final int position) {

        final ReferenciasCombos items = responseHomeList.get(position);

        holder.txtReferencia.setText(items.getDescripcion());
        holder.txtValorR.setText(String.format("S/. %s", format.format(items.getPrecio_referencia())));
        holder.txtValorR2.setText(String.format("PVP: S/. %s", format.format(items.getPrecio_publico())));
        holder.txtcantidadGlo.setText(String.format("Cantidad %s", mydb.countReferenceProduct(iduser, idpos, items.getId())));

        loadeImagenView(holder.img_producto, items.getImg());

        holder.img_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (connectionDetector.isConnected()) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, ActDetalleProducto.class);
                    bundle.putSerializable("value", responseHomeList.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Esta opción solo es permitida si tiene internet", Toast.LENGTH_LONG).show();
                }


            }
        });

        holder.btnpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, ActDetalleComboPedir.class);
                bundle.putSerializable("value", responseHomeList.get(position));
                bundle.putInt("idPos", idpos);
                bundle.putInt("idUser", iduser);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }

    private void loadeImagenView(ImageView img_producto, String img) {

        ImageLoadingListener listener = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String arg0, View arg1) {
                // TODO Auto-generated method stub
                //Inicia metodo
                //holder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingCancelled(String arg0, View arg1) {
                // TODO Auto-generated method stub
                //Cancelar
                //holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                //Completado
                //holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                // TODO Auto-generated method stub
                //Error al cargar la imagen.
                //holder.progressBar.setVisibility(View.GONE);
            }
        };

        imageLoader1.displayImage(img, img_producto, options1, listener);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (responseHomeList == null) {
            return 0;
        } else {
            return responseHomeList.size();
        }
    }

}
