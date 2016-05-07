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

    public AppAdapterRutero(Activity actx, ListHome data){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_list_app, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.title.setText(data.get(position).getNombre_cli());
        holder.sub.setText(data.get(position).getRazon());

        return convertView;
    }


    class ViewHolder {

        TextView title;
        TextView sub;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            sub = (TextView) view.findViewById(R.id.sub);

            view.setTag(this);
        }
    }

}
