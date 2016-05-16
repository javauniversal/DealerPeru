package android.dcsdealerperu.com.dealerperu.Entry;

import android.content.Context;
import android.dcsdealerperu.com.dealerperu.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class RowViewHolderCombo extends RecyclerView.ViewHolder {

    public TextView txtReferencia;
    public TextView txtValorR;
    public TextView txtValorR2;
    public ImageView img_producto;
    public Button btnpedido;

    public RowViewHolderCombo(View itemView, Context context) {
        super(itemView);

        this.txtReferencia = (TextView) itemView.findViewById(R.id.txtReferencia);
        this.txtValorR = (TextView) itemView.findViewById(R.id.txtValorR);
        this.txtValorR2 = (TextView) itemView.findViewById(R.id.txtValorR2);
        this.btnpedido = (Button) itemView.findViewById(R.id.btnpedido);

        this.img_producto = (ImageView) itemView.findViewById(R.id.img_producto);

    }

}
