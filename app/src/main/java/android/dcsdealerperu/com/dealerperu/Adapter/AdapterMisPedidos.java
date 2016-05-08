package android.dcsdealerperu.com.dealerperu.Adapter;

import android.app.Activity;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMisPedidos;
import android.dcsdealerperu.com.dealerperu.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterMisPedidos extends BaseAdapter {

    private Activity actx;
    List<ResponseMisPedidos> data;

    public AdapterMisPedidos(Activity actx, List<ResponseMisPedidos> data){
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

        ResponseMisPedidos resMis =  getItem(position);

        holder.fecha.setText(resMis.getFecha());
        holder.estado.setText(resMis.getEstado());
        holder.nombre_punto.setText(resMis.getNombre_punto());

        return convertView;
    }

    class ViewHolder {

        TextView fecha;
        TextView estado;
        TextView nombre_punto;

        public ViewHolder(View view) {
            fecha = (TextView) view.findViewById(R.id.fecha);
            estado = (TextView) view.findViewById(R.id.estado);
            nombre_punto = (TextView) view.findViewById(R.id.n_punto);



            view.setTag(this);
        }
    }
}
