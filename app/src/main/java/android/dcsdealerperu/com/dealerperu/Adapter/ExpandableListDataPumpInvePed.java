package android.dcsdealerperu.com.dealerperu.Adapter;

import android.dcsdealerperu.com.dealerperu.Entry.MisBajasDetalle1;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseInventario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPumpInvePed {

    public static HashMap<String, List<MisBajasDetalle1>> getData(List<ResponseInventario> data) {
        int elements = 0;

        HashMap<String, List<MisBajasDetalle1>> expandableListDetail = new LinkedHashMap<>();


        if(data != null) {

            for (int i = 0; i < data.size(); i++) {

                if (data.get(i).getDetalle_referencias() != null) {

                    List<MisBajasDetalle1> technology = new ArrayList<>();

                    elements = data.get(i).getDetalle_referencias().size();

                    for (int a = 0; a < elements; a++) {
                        technology.add(new MisBajasDetalle1(data.get(i).getDetalle_referencias().get(a).getProducto(), data.get(i).getDetalle_referencias().get(a).getCantidad()));
                    }

                    expandableListDetail.put(data.get(i).getN_pedido()+ " - "+data.get(i).getNombre_punto()+" - "+data.get(i).getEstado(), technology);

                }
            }
        }
        return expandableListDetail;
    }
}
