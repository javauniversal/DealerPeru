package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 7/05/16.
 */
public class ReferenciasSims {

    private int id_auto_carrito;

    @SerializedName("id")
    private int id;

    @SerializedName("pn")
    private String pn;

    @SerializedName("stock")
    private int stock;

    @SerializedName("producto")
    private String producto;

    @SerializedName("dias_inve")
    private double dias_inve;

    @SerializedName("ped_sugerido")
    private String ped_sugerido;

    public int cantidadPedida;

    public int id_punto;

    public int id_usuario;

    public int tipo_producto;

    public String url_imagen;

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public int getTipo_producto() {
        return tipo_producto;
    }

    public void setTipo_producto(int tipo_producto) {
        this.tipo_producto = tipo_producto;
    }

    public int getId_punto() {
        return id_punto;
    }

    public void setId_punto(int id_punto) {
        this.id_punto = id_punto;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getCantidadPedida() {
        return cantidadPedida;
    }

    public void setCantidadPedida(int cantidadPedida) {
        this.cantidadPedida = cantidadPedida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getDias_inve() {
        return dias_inve;
    }

    public void setDias_inve(double dias_inve) {
        this.dias_inve = dias_inve;
    }

    public String getPed_sugerido() {
        return ped_sugerido;
    }

    public void setPed_sugerido(String ped_sugerido) {
        this.ped_sugerido = ped_sugerido;
    }

    public int getId_auto_carrito() {
        return id_auto_carrito;
    }

    public void setId_auto_carrito(int id_auto_carrito) {
        this.id_auto_carrito = id_auto_carrito;
    }
}
