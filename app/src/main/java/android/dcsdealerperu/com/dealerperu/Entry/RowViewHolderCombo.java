package android.dcsdealerperu.com.dealerperu.Entry;

import android.content.Context;
import android.dcsdealerperu.com.dealerperu.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class RowViewHolderCombo extends RecyclerView.ViewHolder {

    public TextView txtReferencia;
    public TextView txtValorR;
    public TextView txtValorR2;
    public TextView txtTaPantalla;
    public TextView txtCamara;
    public TextView txtFlash;
    public TextView txtCamaraFron;
    public TextView txtMemoria;
    public TextView txtBateria;
    public ImageView img_producto;

    public RowViewHolderCombo(View itemView, Context context) {
        super(itemView);

        this.txtReferencia = (TextView) itemView.findViewById(R.id.txtReferencia);
        this.txtValorR = (TextView) itemView.findViewById(R.id.txtValorR);
        this.txtValorR2 = (TextView) itemView.findViewById(R.id.txtValorR2);

        //this.txtTaPantalla = (TextView) itemView.findViewById(R.id.txtTaPantalla);
        //this.txtCamara = (TextView) itemView.findViewById(R.id.txtCamara);
        //this.txtFlash = (TextView) itemView.findViewById(R.id.txtFlash);
        //this.txtCamaraFron = (TextView) itemView.findViewById(R.id.txtCamaraFron);
        //this.txtMemoria = (TextView) itemView.findViewById(R.id.txtMemoria);
        //this.txtBateria = (TextView) itemView.findViewById(R.id.txtBateria);
        this.img_producto = (ImageView) itemView.findViewById(R.id.img_producto);

    }

}
