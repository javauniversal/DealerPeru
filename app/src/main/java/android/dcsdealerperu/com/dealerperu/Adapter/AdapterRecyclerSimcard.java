package android.dcsdealerperu.com.dealerperu.Adapter;

import android.app.Activity;
import android.dcsdealerperu.com.dealerperu.Entry.ReferenciasSims;
import android.dcsdealerperu.com.dealerperu.Entry.RowViewHolderSimcard;
import android.dcsdealerperu.com.dealerperu.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AdapterRecyclerSimcard extends RecyclerView.Adapter<RowViewHolderSimcard> {

    private Activity context;
    private List<ReferenciasSims> responseHomeList;

    public AdapterRecyclerSimcard(Activity context, List<ReferenciasSims> responseHomeList) {
        super();
        this.context = context;
        this.responseHomeList = responseHomeList;
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
        holder.txtStock.setText(String.format("STOCK  %s",items.getStock()));
        //holder.txtDias.setText(items.getDias_inve()+"");
        //holder.txtPedidoSug.setText(items.getPed_sugerido());

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
