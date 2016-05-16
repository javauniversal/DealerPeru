package android.dcsdealerperu.com.dealerperu.Adapter;

import android.app.Activity;
import android.dcsdealerperu.com.dealerperu.Entry.ListHome;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseHome;
import android.dcsdealerperu.com.dealerperu.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppAdapterRutero extends BaseAdapter {

    private Activity actx;
    ListHome data;

    public AppAdapterRutero(Activity actx, ListHome data) {
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
    public ResponseHome getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_list_app, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        final ResponseHome responseHome = getItem(position);

        holder.title.setText(responseHome.getRazon());
        holder.sub.setText(responseHome.getDireccion());

        if (responseHome.getTipo_visita() == 1) {
            holder.img_estado_visita.setImageResource(R.drawable.ic_radio_button_checked_black_24dp);
        } else {
            holder.img_estado_visita.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
        }
        if (responseHome.getRutero() == 1) {
            holder.image_departamento.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    class ViewHolder {

        TextView title;
        TextView sub;
        ImageView img_estado_visita;
        ImageView image_departamento;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            sub = (TextView) view.findViewById(R.id.sub);

            img_estado_visita = (ImageView) view.findViewById(R.id.image_estado_visita);
            image_departamento = (ImageView) view.findViewById(R.id.image_departamento);

            view.setTag(this);
        }
    }

}
