package android.dcsdealerperu.com.dealerperu.Adapter;

import android.dcsdealerperu.com.dealerperu.Entry.Detalle;
import android.dcsdealerperu.com.dealerperu.Entry.Pedidos;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPump {

    public static HashMap<String, List<Detalle>> getData(List<Pedidos> data) {
        int elements = 0;

        HashMap<String, List<Detalle>> expandableListDetail = new LinkedHashMap<>();

        if(data != null) {

            for (int i = 0; i < data.size(); i++) {

                if (data.get(i).getDetalles() != null) {

                    List<Detalle> technology = new ArrayList<>();

                    elements = data.get(i).getDetalles().size();

                    for (int a = 0; a < elements; a++) {
                        technology.add(new Detalle(data.get(i).getDetalles().get(a).getNombre_usuario(), data.get(i).getDetalles().get(a).getHora(), data.get(i).getDetalles().get(a).getPn(), data.get(i).getDetalles().get(a).getCantidad(), data.get(i).getDetalles().get(a).getTotal()));
                    }

                    expandableListDetail.put(data.get(i).getId()+" - "+data.get(i).getEstado()+" - "+data.get(i).getFecha(), technology);
                }
            }
        }
        return expandableListDetail;
    }
}
