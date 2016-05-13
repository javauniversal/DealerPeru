package android.dcsdealerperu.com.dealerperu.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.dcsdealerperu.com.dealerperu.Activity.ActEntregarPorPedido;
import android.dcsdealerperu.com.dealerperu.Entry.ReferenciasSims;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseEntregarPedido;
import android.dcsdealerperu.com.dealerperu.R;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
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

import java.io.Serializable;
import java.util.List;

public class AdapterEntregaPedido extends BaseAdapter {

    private Activity actx;
    List<ResponseEntregarPedido> data;

    public AdapterEntregaPedido(Activity actx, List<ResponseEntregarPedido> data){
        this.actx = actx;
        this.data = data;

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
    public ResponseEntregarPedido getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_entrega, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        final ResponseEntregarPedido referencias = getItem(position);

        holder.txt_idpos.setText(String.format("Id Pos: %1$s",referencias.getIdpos()));
        holder.txtRazo.setText(String.format("Razón Social: %1$s",referencias.getRazon_social()));
        holder.txt_circuito.setText(String.format("Circuito: %1$s",referencias.getTerritorio()));
        holder.txt_ruta.setText(String.format("Ruta: %1$s",referencias.getZona()));

        holder.txt_direccion.setText(String.format("Dirección: %1$s", referencias.getDireccion()));

        if (referencias.getDireccion().isEmpty())
            holder.txt_direccion.setVisibility(View.GONE);

        return convertView;
    }

    class ViewHolder {

        TextView txt_idpos;
        TextView txtRazo;
        TextView txt_circuito;
        TextView txt_ruta;
        TextView txt_direccion;


        public ViewHolder(View view) {

            txt_idpos = (TextView) view.findViewById(R.id.txt_idpos);
            txtRazo = (TextView) view.findViewById(R.id.txtRazo);
            txt_circuito = (TextView) view.findViewById(R.id.txt_circuito);
            txt_ruta = (TextView) view.findViewById(R.id.txt_ruta);
            txt_direccion = (TextView) view.findViewById(R.id.txt_direccion);

            view.setTag(this);
        }
    }

}
