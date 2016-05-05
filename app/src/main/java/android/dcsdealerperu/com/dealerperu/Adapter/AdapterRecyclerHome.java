package android.dcsdealerperu.com.dealerperu.Adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseHome;
import android.dcsdealerperu.com.dealerperu.Entry.RowViewHolderHome;
import android.dcsdealerperu.com.dealerperu.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterRecyclerHome extends RecyclerView.Adapter<RowViewHolderHome> {

    private Activity context;
    private List<ResponseHome> responseHomeList;

    public AdapterRecyclerHome(Activity context, List<ResponseHome> responseHomeList) {
        super();
        this.context = context;
        this.responseHomeList = responseHomeList;
    }

    @Override
    public RowViewHolderHome onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rutero, parent, false);
        return new RowViewHolderHome(v, context);

    }

    @Override
    public void onBindViewHolder(RowViewHolderHome holder, int position) {
        final ResponseHome items = responseHomeList.get(position);

        holder.txt_rutero.setText(items.getNombre_cli());

        holder.txt_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = context.getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_detalle, null);

                TextView txt_id_numero = (TextView) dialoglayout.findViewById(R.id.txt_id_numero);
                txt_id_numero.setText(String.format("%1$s", items.getIdpos()));

                TextView txt_visitado = (TextView) dialoglayout.findViewById(R.id.txt_visitado);
                txt_visitado.setText(String.format("%1$s", items.getTipo_visita()));

                TextView txt_direccion = (TextView) dialoglayout.findViewById(R.id.txt_direccion);
                txt_direccion.setText(String.format("%1$s", items.getDireccion()));

                TextView txt_departamento = (TextView) dialoglayout.findViewById(R.id.txt_departamento);
                txt_departamento.setText(String.format("%1$s", items.getDepartamento()));

                TextView txt_ciudad = (TextView) dialoglayout.findViewById(R.id.txt_ciudad);
                txt_ciudad.setText(String.format("%1$s", items.getDepartamento()));

                TextView txt_circuito = (TextView) dialoglayout.findViewById(R.id.txt_circuito);
                txt_circuito.setText(String.format("%1$s", items.getCircuito()));

                TextView txt_ruta = (TextView) dialoglayout.findViewById(R.id.txt_ruta);
                txt_ruta.setText(String.format("%1$s", items.getRuta()));

                TextView txt_telefono = (TextView) dialoglayout.findViewById(R.id.txt_telefono);
                txt_telefono.setText(String.format("%1$s", items.getTel()));

                TextView txt_dias = (TextView) dialoglayout.findViewById(R.id.txt_dias);
                txt_dias.setText(String.format("%1$s", items.getDetalle()));

                TextView txt_hora_visita = (TextView) dialoglayout.findViewById(R.id.txt_hora_visita);
                txt_hora_visita.setText(String.format("%1$s", items.getTipo_visita()));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle("Detalle");
                builder.setView(dialoglayout).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        holder.action_rutero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (responseHomeList == null) {
            return 0;
        } else {
            return responseHomeList.size();
        }
    }

}
