package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 19/05/16.
 */
public class Puntos {

    @SerializedName("idpos")
    private int idpos;

    @SerializedName("razon")
    private String razon;

    @SerializedName("cedula")
    private int cedula;

    @SerializedName("depto")
    private int depto;

    @SerializedName("ciudad")
    private int ciudad;

    @SerializedName("distrito")
    private int distrito;

    @SerializedName("zona")
    private int zona;

    @SerializedName("territorio")
    private int territorio;

    @SerializedName("detalle_direccion")
    private String detalle_direccion;

    @SerializedName("latitud")
    private double latitud;

    @SerializedName("longitud")
    private double longitud;

    @SerializedName("estado_accion")
    private int estado_accion;

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public int getDepto() {
        return depto;
    }

    public void setDepto(int depto) {
        this.depto = depto;
    }

    public int getCiudad() {
        return ciudad;
    }

    public void setCiudad(int ciudad) {
        this.ciudad = ciudad;
    }

    public int getDistrito() {
        return distrito;
    }

    public void setDistrito(int distrito) {
        this.distrito = distrito;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    public int getTerritorio() {
        return territorio;
    }

    public void setTerritorio(int territorio) {
        this.territorio = territorio;
    }

    public String getDetalle_direccion() {
        return detalle_direccion;
    }

    public void setDetalle_direccion(String detalle_direccion) {
        this.detalle_direccion = detalle_direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getEstado_accion() {
        return estado_accion;
    }

    public void setEstado_accion(int estado_accion) {
        this.estado_accion = estado_accion;
    }
}
