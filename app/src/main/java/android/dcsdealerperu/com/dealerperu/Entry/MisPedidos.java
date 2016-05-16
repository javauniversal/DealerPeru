package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MisPedidos implements Serializable {

    @SerializedName("reporte")
    private List<ResponseMisPedidos> responseMisPedidosList;

    public List<ResponseMisPedidos> getResponseMisPedidosList() {
        return responseMisPedidosList;
    }

    public void setResponseMisPedidosList(List<ResponseMisPedidos> responseMisPedidosList) {
        this.responseMisPedidosList = responseMisPedidosList;
    }


}
