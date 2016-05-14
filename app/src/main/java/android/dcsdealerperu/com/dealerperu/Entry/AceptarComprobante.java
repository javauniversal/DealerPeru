package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by germangarcia on 14/05/16.
 */
public class AceptarComprobante {

    @SerializedName("nroCompobante")
    private int nroCompobante;

    @SerializedName("pedidos")
    private List<AceptarPedido> aceptarPedidoList;

    public int getNroCompobante() {
        return nroCompobante;
    }

    public void setNroCompobante(int nroCompobante) {
        this.nroCompobante = nroCompobante;
    }

    public List<AceptarPedido> getAceptarPedidoList() {
        return aceptarPedidoList;
    }

    public void setAceptarPedidoList(List<AceptarPedido> aceptarPedidoList) {
        this.aceptarPedidoList = aceptarPedidoList;
    }
}
