package android.dcsdealerperu.com.dealerperu.Adapter;

import android.app.Activity;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMisPedidos;
import android.dcsdealerperu.com.dealerperu.R;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterMisPedidos extends BaseAdapter {

    private Activity actx;
    List<ResponseMisPedidos> data;

    public AdapterMisPedidos(Activity actx, List<ResponseMisPedidos> data) {
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
    public ResponseMisPedidos getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_mis_pedidos, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        ResponseMisPedidos resMis = getItem(position);

        holder.fecha.setText(Html.fromHtml("<b>Fecha:</b> "+resMis.getFecha()));
        holder.estado.setText(Html.fromHtml("<b>"+resMis.getEstado()+"</b>"));
        holder.nombre_punto.setText(Html.fromHtml(String.valueOf("<b>Pdv:</b> "+resMis.getIdpos() +" -- <b>Nombre: </b>"+resMis.getNombre_punto())));
        holder.direccion.setText(Html.fromHtml(String.valueOf("<b>Direccion:</b> "+resMis.getDireccion())));

        return convertView;
    }

    class ViewHolder {

        TextView fecha;
        TextView estado;
        TextView nombre_punto;
        TextView direccion;

        public ViewHolder(View view) {
            fecha = (TextView) view.findViewById(R.id.fecha);
            estado = (TextView) view.findViewById(R.id.estado);
            nombre_punto = (TextView) view.findViewById(R.id.idpos);
            direccion = (TextView) view.findViewById(R.id.direccion);


            view.setTag(this);
        }
    }
}
