package android.dcsdealerperu.com.dealerperu.Adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.dcsdealerperu.com.dealerperu.DataBase.DBHelper;
import android.dcsdealerperu.com.dealerperu.Entry.ReferenciasSims;
import android.dcsdealerperu.com.dealerperu.Entry.RowViewHolderSimcard;
import android.dcsdealerperu.com.dealerperu.R;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.dcsdealerperu.com.dealerperu.Entry.ResponseUser.getResponseUserStatic;
import static android.dcsdealerperu.com.dealerperu.Entry.ResponseVenta.getId_posStacti;

public class AdapterRecyclerSimcard extends RecyclerView.Adapter<RowViewHolderSimcard> {

    private Activity context;
    private List<ReferenciasSims> responseHomeList;
    private DBHelper mydb;

    public AdapterRecyclerSimcard(Activity context, List<ReferenciasSims> responseHomeList) {
        super();
        this.context = context;
        this.responseHomeList = responseHomeList;

        mydb = new DBHelper(context);

    }

    @Override
    public RowViewHolderSimcard onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simcard, parent, false);
        return new RowViewHolderSimcard(v, context);

    }

    @Override
    public void onBindViewHolder(final RowViewHolderSimcard holder, int position) {

        final ReferenciasSims items = responseHomeList.get(position);

        holder.txt_referemcia.setText(items.getProducto());
        holder.txtStock.setText(String.format("STOCK  %s", items.getStock()));

        holder.btnCatalogoSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = context.getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_sim_pedido, null);

                TextView stock = (TextView) dialoglayout.findViewById(R.id.txtStock);
                TextView dias = (TextView) dialoglayout.findViewById(R.id.txtDias);
                final EditText numeroCan = (EditText) dialoglayout.findViewById(R.id.editCantidad);

                stock.setText(String.format("%s", items.getStock()));
                dias.setText(String.format("%s", items.getDias_inve()));
                numeroCan.setText(String.format("%s", items.getPed_sugerido()));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle(items.getProducto());
                builder.setView(dialoglayout);

                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (isValidNumber(numeroCan.getText().toString())) {
                            Toast.makeText(context, "La cantidad es un campo requerido", Toast.LENGTH_SHORT).show();
                        } else {
                            int cantidad_dig = Integer.parseInt(numeroCan.getText().toString());
                            if (cantidad_dig <= 0) {
                                Toast.makeText(context, "la cantidad digitada tiene que ser mayor a 0", Toast.LENGTH_SHORT).show();
                            } else {
                                //Guardar

                                items.setCantidadPedida(cantidad_dig);
                                items.setId_punto(getId_posStacti());
                                items.setId_usuario(getResponseUserStatic().getId());
                                items.setTipo_producto(1); // Simcard

                                String resultado = mydb.insertCarritoPedido(items);

                                if (resultado.equals("inserto")) {
                                    Toast.makeText(context, "Pedido guardado correctamente", Toast.LENGTH_SHORT).show();

                                    holder.txtCantidadPedida.setVisibility(View.VISIBLE);
                                    holder.txtCantidadPedida.setText(String.format("Cantidad %s", cantidad_dig));

                                } else if (resultado.equals("no inserto")) {
                                    Toast.makeText(context, "Problemas al guardar el pedido", Toast.LENGTH_SHORT).show();
                                } else if (resultado.equals("update")) {
                                    Toast.makeText(context, "Pedido actualizado correctamente", Toast.LENGTH_SHORT).show();
                                    holder.txtCantidadPedida.setVisibility(View.VISIBLE);
                                    holder.txtCantidadPedida.setText(String.format("Cantidad %s", cantidad_dig));
                                } else if (resultado.equals("no update")) {
                                    Toast.makeText(context, "Pedido no se pudo actualizar", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();

            }
        });

    }

    private boolean isValidNumber(String number) {
        return number == null || number.length() == 0;
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
