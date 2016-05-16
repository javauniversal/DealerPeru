package android.dcsdealerperu.com.dealerperu.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.dcsdealerperu.com.dealerperu.Activity.DialogRechazar;
import android.dcsdealerperu.com.dealerperu.Entry.AceptarPedido;
import android.dcsdealerperu.com.dealerperu.Entry.DestallePedido;
import android.dcsdealerperu.com.dealerperu.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.List;

public class AdapterAceptPedido extends BaseAdapter {

    private Activity actx;
    private List<AceptarPedido> data;
    private Format format;
    protected DialogRechazar dialog;

    public AdapterAceptPedido(Activity actx, List<AceptarPedido> data){
        this.actx = actx;
        this.data = data;
        format = new DecimalFormat("#,###.##");
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
    public AceptarPedido getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(actx, R.layout.item_aceptar_pedido, null);
        new ViewHolder(convertView);

        final ViewHolder holder = (ViewHolder) convertView.getTag();

        final AceptarPedido referencias = getItem(position);

        holder.txtNumeroComprobante.setText(String.format("Número de Pedido: %1$s",referencias.getNroPedido()));
        //holder.txtNumero_pedido.setText(String.format("%1$s",referencias.getNroPedido()));
        holder.txtCantidadPedida.setText(String.format("%1$s",referencias.getCant_pedido_p()));
        holder.txtTotalPedido.setText(String.format("S/. %1$s", format.format(referencias.getTotal_pedido_p())));
        holder.txt_idpos.setText(String.format("%1$s",referencias.getIdpos()));
        holder.txtFechaEstimada.setText(String.format("%1$s",referencias.getFecha_entrega()));

        holder.txtDetalleLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDetalle(position, referencias);
            }
        });

        holder.radioAceptar.setChecked(referencias.indicadorChekend);

        holder.radioDevolver.setChecked(referencias.indicadorChekendDevolver);

        holder.radioDevolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new DialogRechazar(actx, "Observación");
                dialog.setCancelable(false);
                dialog.show();
                Button acceptButton = dialog.getButtonAccept();
                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isValidNumber(dialog.getEmail().getText().toString().trim())) {
                            dialog.getEmail().setError("Este campo es obligatorio");
                            dialog.getEmail().requestFocus();
                        } else {
                            referencias.setComentarioRechazo(dialog.getEmail().getText().toString());
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = group.findViewById(checkedId);
                int radioId = group.indexOfChild(radioButton);

                if (radioId == 0) {
                    referencias.marcaProducto = "devolver";
                    referencias.indicadorChekendDevolver = true;
                    referencias.indicadorChekend = false;
                } else {

                    if (referencias.marcaProducto.equals("devolver")) {
                        Toast.makeText(actx, "La observación", Toast.LENGTH_LONG).show();
                    }

                    referencias.marcaProducto = "aceptar";
                    referencias.indicadorChekend = true;
                    referencias.indicadorChekendDevolver = false;
                }

            }
        });


        return convertView;
    }

    private boolean isValidNumber(String number){return number == null || number.length() == 0;}

    private void DialogDetalle(final int position, final AceptarPedido referencias) {

        LayoutInflater inflater = actx.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_aceptar_pedido, null);

        ListView listView = (ListView) dialoglayout.findViewById(R.id.listView2);

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                if (referencias.getDestallePedidoList() == null) {
                    return 0;
                } else {
                    return referencias.getDestallePedidoList().size();
                }
            }

            @Override
            public DestallePedido getItem(int position) {
                return referencias.getDestallePedidoList().get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = View.inflate(actx, R.layout.item_detalle_aceptar_pedido, null);
                    holder.txtReferencia = (TextView) convertView.findViewById(R.id.txtReferencia);
                    holder.txtTipoProducto = (TextView) convertView.findViewById(R.id.txtTipoProducto);
                    holder.txtCantidadSol = (TextView) convertView.findViewById(R.id.txtCantidadSol);
                    holder.txtCantidaDes = (TextView) convertView.findViewById(R.id.txtCantidaDes);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                DestallePedido aceptarPedido = getItem(position);

                holder.txtReferencia.setText(String.format("%1$s", aceptarPedido.getRefePro()));
                holder.txtTipoProducto.setText(String.format("%1$s", aceptarPedido.getTipo_pro()));
                holder.txtCantidadSol.setText(String.format("%1$s", aceptarPedido.getCant_pedido()));
                holder.txtCantidaDes.setText(String.format("%1$s", aceptarPedido.getCant_pedido_p()));

                return convertView;
            }

            class ViewHolder {

                TextView txtReferencia;
                TextView txtTipoProducto;
                TextView txtCantidadSol;
                TextView txtCantidaDes;
            }
        });

        AlertDialog.Builder builder2 = new AlertDialog.Builder(actx);
        builder2.setCancelable(false);
        builder2.setTitle("");
        builder2.setView(dialoglayout).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder2.show();

    }

    class ViewHolder {

        TextView txtNumeroComprobante;
        //TextView txtNumero_pedido;
        TextView txtCantidadPedida;
        TextView txtTotalPedido;
        TextView txt_idpos;
        TextView txtFechaEstimada;
        TextView txtDetalleLink;
        RadioGroup radioGroup;
        RadioButton radioAceptar;
        RadioButton radioDevolver;

        public ViewHolder(View view) {

            txtNumeroComprobante = (TextView) view.findViewById(R.id.txtNumeroComprobante);
            txtCantidadPedida = (TextView) view.findViewById(R.id.txtCantidadPedida);
            txtTotalPedido = (TextView) view.findViewById(R.id.txtTotalPedido);
            txt_idpos = (TextView) view.findViewById(R.id.txt_idpos);
            txtFechaEstimada = (TextView) view.findViewById(R.id.txtFechaEstimada);
            txtDetalleLink = (TextView) view.findViewById(R.id.txtDetalleLink);

            radioGroup = (RadioGroup) view.findViewById(R.id.radio);

            radioAceptar = (RadioButton) view.findViewById(R.id.radioAceptar);
            radioDevolver = (RadioButton) view.findViewById(R.id.radioDevolver);

            view.setTag(this);
        }
    }

}
