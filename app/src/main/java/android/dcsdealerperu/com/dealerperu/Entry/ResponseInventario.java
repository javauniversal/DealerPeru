package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class ResponseInventario implements Serializable {

    @SerializedName("tipon")
    private int tipon;

    @SerializedName("tipo")
    private String tipo;

    @SerializedName("producto")
    private String producto;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("id_referencia")
    private int id_referencia;

    @SerializedName("detalle_series")
    private List<MisBajasDetalle2> detalle_series;

    @SerializedName("n_pedido")
    private int n_pedido;

    @SerializedName("nombre_punto")
    private String nombre_punto;

    @SerializedName("estado")
    private String estado;

    @SerializedName("detalle_referencias")
    private List<MisBajasDetalle1> detalle_referencias;

    public int getN_pedido() {
        return n_pedido;
    }

    public void setN_pedido(int n_pedido) {
        this.n_pedido = n_pedido;
    }

    public String getNombre_punto() {
        return nombre_punto;
    }

    public void setNombre_punto(String nombre_punto) {
        this.nombre_punto = nombre_punto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<MisBajasDetalle1> getDetalle_referencias() {
        return detalle_referencias;
    }

    public void setDetalle_referencias(List<MisBajasDetalle1> detalle_referencias) {
        this.detalle_referencias = detalle_referencias;
    }

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
