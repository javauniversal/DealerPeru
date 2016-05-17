package android.dcsdealerperu.com.dealerperu.Adapter;

import android.app.Activity;
import android.dcsdealerperu.com.dealerperu.Entry.ReferenciasSims;
import android.dcsdealerperu.com.dealerperu.R;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterCarrito extends BaseAdapter {

    private Activity actx;
    List<ReferenciasSims> data;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader1;
    private DisplayImageOptions options1;
    private DecimalFormat format;

    public AdapterCarrito(Activity actx, List<ReferenciasSims> data) {
        this.actx = actx;
        this.data = data;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(actx).build();
        imageLoader1 = ImageLoader.getInstance();
        imageLoader1.init(config);

        format = new DecimalFormat("#,###.##");

        //Setup options for ImageLoader so it will handle caching for us.
        options1 = new DisplayImageOptions.Builder()
                .cacheInMemory()
                .cacheOnDisc()
                .build();

    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public ReferenciasSims getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_list_carrito, null);
            new ViewHolder(convertView);
        //}

        ViewHolder holder = (ViewHolder) convertView.getTag();

        ReferenciasSims referenciasSims = getItem(position);

        holder.txtReferencia.setText(referenciasSims.getProducto());
        holder.txtcantidad.setText(String.format("Cantidad %1$s", referenciasSims.getCantidadPedida()));
        holder.txtPn.setText(String.format("%1$s", referenciasSims.getPn()));
        holder.precio.setText(String.format("S/. %s", format.format(referenciasSims.getCantidadPedida() * referenciasSims.getPrecio_referencia())));

        if (referenciasSims.getTipo_producto() == 1) {
            //Simcar
            holder.profile_image.setColorFilter(Color.rgb(16, 98, 138));

        } else {
            //Combo
            loadeImagenView(holder.profile_image, referenciasSims.getUrl_imagen());
        }

        return convertView;
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

    class ViewHolder {

        TextView txtReferencia;
        TextView txtcantidad;
        TextView txtPn;
        TextView precio;
        ImageView profile_image;

        public ViewHolder(View view) {

            txtReferencia = (TextView) view.findViewById(R.id.txtReferencia);
            txtPn = (TextView) view.findViewById(R.id.txtPn);
            txtcantidad = (TextView) view.findViewById(R.id.txtcantidad);
            precio = (TextView) view.findViewById(R.id.txtprecio);

            profile_image = (ImageView) view.findViewById(R.id.profile_image);

            view.setTag(this);
        }
    }

}
