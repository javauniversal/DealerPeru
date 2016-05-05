package android.dcsdealerperu.com.dealerperu.Entry;

import android.content.Context;
import android.dcsdealerperu.com.dealerperu.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class RowViewHolderHome extends RecyclerView.ViewHolder {

    public ImageView action_rutero;
    public TextView txt_rutero;
    public TextView txt_detalle;

    public RowViewHolderHome(View itemView, Context context) {
        super(itemView);

        this.txt_rutero = (TextView) itemView.findViewById(R.id.txt_rutero);

        this.txt_detalle = (TextView) itemView.findViewById(R.id.txt_detalle);

        this.action_rutero = (ImageView) itemView.findViewById(R.id.action_rutero);

    }

}
