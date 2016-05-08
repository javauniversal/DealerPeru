package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseMisPedidos implements Serializable {


    @SerializedName("npedido")
    private int npedido;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("total")
    private double total;

    @SerializedName("cantidad_picking")
    private int cantidad_picking;

    @SerializedName("total_picking")
    private double total_picking;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("hora")
    private String hora;

    @SerializedName("idpos")
    private int idpos;

    @SerializedName("estado")
    private String estado;

    @SerializedName("nombre_punto")
    private String nombre_punto;



    public int getNpedido() {
        return npedido;
    }

    public void setNpedido(int npedido) {
        this.npedido = npedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCantidad_picking() {
        return cantidad_picking;
    }

    public void setCantidad_picking(int cantidad_picking) {
        this.cantidad_picking = cantidad_picking;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre_punto() {
        return nombre_punto;
    }

    public double getTotal_picking() {
        return total_picking;
    }

    public void setTotal_picking(double total_picking) {
        this.total_picking = total_picking;
    }

    public void setNombre_punto(String nombre_punto) {
        this.nombre_punto = nombre_punto;
    }

}
