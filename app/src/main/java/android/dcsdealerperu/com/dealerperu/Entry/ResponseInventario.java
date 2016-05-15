package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class ResponseInventario implements Serializable {

    @SerializedName("tipon")
    private int tipon;

    @SerializedName("tipo")
    private  String tipo;

    @SerializedName("producto")
    private String producto;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("id_referencia")
    private int id_referencia;

    @SerializedName("detalle_series")
    private List<MisBajasDetalle2> detalle_series;

    public int getTipon() {
        return tipon;
    }

    public void setTipon(int tipon) {
        this.tipon = tipon;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_referencia() {
        return id_referencia;
    }

    public void setId_referencia(int id_referencia) {
        this.id_referencia = id_referencia;
    }

    public List<MisBajasDetalle2> getDetalle_series() {
        return detalle_series;
    }

    public void setDetalle_series(List<MisBajasDetalle2> detalle_series) {
        this.detalle_series = detalle_series;
    }
}
