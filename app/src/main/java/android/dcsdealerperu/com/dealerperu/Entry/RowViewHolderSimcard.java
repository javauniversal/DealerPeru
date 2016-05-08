package android.dcsdealerperu.com.dealerperu.Entry;

import android.content.Context;
import android.dcsdealerperu.com.dealerperu.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class RowViewHolderSimcard extends RecyclerView.ViewHolder {

    public TextView txt_referemcia;
    public TextView txtStock;
    public TextView txtDias;
    public TextView txtPedidoSug;

    public RowViewHolderSimcard(View itemView, Context context) {
        super(itemView);

        this.txt_referemcia = (TextView) itemView.findViewById(R.id.txt_referemcia);
        this.txtStock = (TextView) itemView.findViewById(R.id.txtStock);
        //this.txtDias = (TextView) itemView.findViewById(R.id.txtDias);
        //this.txtPedidoSug = (TextView) itemView.findViewById(R.id.txtPedidoSug);


    }

}
