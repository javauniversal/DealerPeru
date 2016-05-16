package android.dcsdealerperu.com.dealerperu.Adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.dcsdealerperu.com.dealerperu.DataBase.DBHelper;
import android.dcsdealerperu.com.dealerperu.Entry.Referencia;
import android.dcsdealerperu.com.dealerperu.R;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;
import static android.dcsdealerperu.com.dealerperu.Entry.ResponseVenta.getId_posStacti;

public class AdapterListReferencia extends BaseAdapter {

    private Activity actx;
    private List<Referencia> data;
    private DBHelper mydb;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader1;
    private DisplayImageOptions options1;

    public AdapterListReferencia(Activity actx, List<Referencia> data) {
        this.actx = actx;
        this.data = data;
        mydb = new DBHelper(actx);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(actx).build();
        imageLoader1 = ImageLoader.getInstance();
        imageLoader1.init(config);

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
    public Referencia getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_list_carrito, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        final Referencia referencias = getItem(position);

        holder.txtReferencia.setText(referencias.getProducto());
        holder.txtPn.setText(String.format("%1$s", referencias.getPn()));

        holder.imageView2.setVisibility(View.GONE);
        holder.txtcantidad.setVisibility(View.GONE);

        loadeImagenView(holder.profile_image, referencias.getUrl_imagen());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = actx.getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_sim_pedido, null);

                TextView stock = (TextView) dialoglayout.findViewById(R.id.txtStock);
                TextView dias = (TextView) dialoglayout.findViewById(R.id.txtDias);
                final EditText numeroCan = (EditText) dialoglayout.findViewById(R.id.editCantidad);

                stock.setText(String.format("%s", referencias.getStock()));
                dias.setText(String.format("%s", referencias.getDias_inve()));
                numeroCan.setText(String.format("%s", referencias.getPed_sugerido()));

                AlertDialog.Builder builder = new AlertDialog.Builder(actx);
                builder.setCancelable(false);
                builder.setTitle(referencias.getProducto());
                builder.setView(dialoglayout);

                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (isValidNumber(numeroCan.getText().toString())) {
                            Toast.makeText(actx, "La cantidad es un campo requerido", Toast.LENGTH_SHORT).show();
                        } else {
                            int cantidad_dig = Integer.parseInt(numeroCan.getText().toString());
                            if (cantidad_dig <= 0) {
                                Toast.makeText(actx, "la cantidad digitada tiene que ser mayor a 0", Toast.LENGTH_SHORT).show();
                            } else {
                                //Guardar

                                referencias.setCantidadPedida(cantidad_dig);
                                referencias.setId_punto(getId_posStacti());
                                referencias.setId_usuario(getResponseUserStatic().getId());
                                referencias.setTipo_producto(2); // Combo
                                referencias.setUrl_imagen(referencias.getUrl_imagen());

                                String resultado = mydb.insertCarritoPedidoCombos(referencias);

                                if (resultado.equals("inserto")) {
                                    Toast.makeText(actx, "Pedido guardado correctamente", Toast.LENGTH_SHORT).show();

                                    //holder.txtCantidadPedida.setVisibility(View.VISIBLE);
                                    //holder.txtCantidadPedida.setText(String.format("Cantidad %s", cantidad_dig));

                                } else if (resultado.equals("no inserto")) {
                                    Toast.makeText(actx, "Problemas al guardar el pedido", Toast.LENGTH_SHORT).show();
                                } else if (resultado.equals("update")) {
                                    Toast.makeText(actx, "Pedido actualizado correctamente", Toast.LENGTH_SHORT).show();
                                    //holder.txtCantidadPedida.setVisibility(View.VISIBLE);
                                    //holder.txtCantidadPedida.setText(String.format("Cantidad %s", cantidad_dig));
                                } else if (resultado.equals("no update")) {
                                    Toast.makeText(actx, "Pedido no se pudo actualizar", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });

        return convertView;

    }

    private boolean isValidNumber(String number) {
        return number == null || number.length() == 0;
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
        ImageView profile_image;
        ImageView imageView2;

        public ViewHolder(View view) {

            txtReferencia = (TextView) view.findViewById(R.id.txtReferencia);
            txtPn = (TextView) view.findViewById(R.id.txtPn);
            txtcantidad = (TextView) view.findViewById(R.id.txtcantidad);

            profile_image = (ImageView) view.findViewById(R.id.profile_image);
            imageView2 = (ImageView) view.findViewById(R.id.imageView2);

            view.setTag(this);
        }
    }

}
