package android.dcsdealerperu.com.dealerperu.Adapter;

import android.dcsdealerperu.com.dealerperu.Entry.MisBajasDetalle2;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseInventario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Josue on 14/05/16.
 */
public class ExpandableListInventarioRep {

    public static HashMap<String, List<MisBajasDetalle2>> getData(List<ResponseInventario> data) {
        int elements = 0;

        HashMap<String, List<MisBajasDetalle2>> expandableListDetail = new LinkedHashMap<>();


        if (data != null) {

            for (int i = 0; i < data.size(); i++) {

                if (data.get(i).getDetalle_series() != null) {

                    List<MisBajasDetalle2> technology = new ArrayList<>();

                    elements = data.get(i).getDetalle_series().size();

                    for (int a = 0; a < elements; a++) {
                        technology.add(new MisBajasDetalle2(data.get(i).getDetalle_series().get(a).getSerie()));
                    }

                    expandableListDetail.put(data.get(i).getTipo() + " - " + data.get(i).getProducto() + " - " + data.get(i).getCantidad(), technology);


                }
            }
        }
        return expandableListDetail;
    }

}
