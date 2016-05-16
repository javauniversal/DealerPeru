package android.dcsdealerperu.com.dealerperu.Adapter;

import android.content.Context;
import android.dcsdealerperu.com.dealerperu.Entry.MisBajasDetalle2;
import android.dcsdealerperu.com.dealerperu.R;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Josue on 14/05/16.
 */
public class ExpandableListAdapterReporteInv extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<MisBajasDetalle2>> expandableListDetail;
    private DecimalFormat format;
    private int tipoinve;

    public ExpandableListAdapterReporteInv(Context context, List<String> expandableListTitle, HashMap<String, List<MisBajasDetalle2>> expandableListDetail, int tipo) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.tipoinve = tipo;

        format = new DecimalFormat("#,###");

    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(final int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (tipoinve == 2) // agrupar Primero por referencia.!
        {
            final MisBajasDetalle2 expandedListText = (MisBajasDetalle2) getChild(listPosition, expandedListPosition);

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item_bajas, null);
            }

            TextView txt_referencia = (TextView) convertView.findViewById(R.id.txt_referencia);
            TextView txt_cantidad = (TextView) convertView.findViewById(R.id.txt_cantidad);


            txt_referencia.setText(expandedListText.getSerie());
            txt_cantidad.setText("");
        }


        return convertView;
    }


    @Override
    public int getChildrenCount(int listPosition) {

        if (this.expandableListDetail.get(this.expandableListTitle.get(listPosition)) == null) {
            return 0;
        } else {
            return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
        }

    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {

        if (this.expandableListTitle == null) {
            return 0;
        } else {
            return this.expandableListTitle.size();
        }
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group_bajas, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

}