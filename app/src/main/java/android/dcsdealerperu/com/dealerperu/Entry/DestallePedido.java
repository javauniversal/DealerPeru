package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 14/05/16.
 */
public class DestallePedido {

    @SerializedName("refePro")
    private String refePro;

    @SerializedName("idRefe")
    private int idRefe;

    @SerializedName("cant_pedido")
    private int cant_pedido;

    @SerializedName("total_pedido")
    private double total_pedido;

    @SerializedName("cant_pedido_p")
    private int cant_pedido_p;

    @SerializedName("total_pedido_p")
    private double total_pedido_p;

    @SerializedName("tipo_pro")
    private String tipo_pro;

    public String getRefePro() {
        return refePro;
    }

    public void setRefePro(String refePro) {
        this.refePro = refePro;
    }

    public int getIdRefe() {
        return idRefe;
    }

    public void setIdRefe(int idRefe) {
        this.idRefe = idRefe;
    }

    public int getCant_pedido() {
        return cant_pedido;
    }

    public void setCant_pedido(int cant_pedido) {
        this.cant_pedido = cant_pedido;
    }

    public double getTotal_pedido() {
        return total_pedido;
    }

    public void setTotal_pedido(double total_pedido) {
        this.total_pedido = total_pedido;
    }

    public int getCant_pedido_p() {
        return cant_pedido_p;
    }

    public void setCant_pedido_p(int cant_pedido_p) {
        this.cant_pedido_p = cant_pedido_p;
    }

    public double getTotal_pedido_p() {
        return total_pedido_p;
    }

    public void setTotal_pedido_p(double total_pedido_p) {
        this.total_pedido_p = total_pedido_p;
    }

    public String getTipo_pro() {
        return tipo_pro;
    }

    public void setTipo_pro(String tipo_pro) {
        this.tipo_pro = tipo_pro;
    }
}
