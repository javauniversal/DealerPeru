package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 6/05/16.
 */
public class BuscarPunto {

    @SerializedName("idpos")
    private int idpos;

    @SerializedName("cedula")
    private String cedula;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("depto")
    private int depto;

    @SerializedName("ciudad")
    private int ciudad;

    @SerializedName("distrito")
    private int distrito;

    @SerializedName("circuito")
    private int circuito;

    @SerializedName("ruta")
    private int ruta;

    @SerializedName("est_comercial")
    private int est_comercial;

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getCircuito() {
        return circuito;
    }

    public void setCircuito(int circuito) {
        this.circuito = circuito;
    }

    public int getRuta() {
        return ruta;
    }

    public void setRuta(int ruta) {
        this.ruta = ruta;
    }

    public int getEst_comercial() {
        return est_comercial;
    }

    public void setEst_comercial(int est_comercial) {
        this.est_comercial = est_comercial;
    }
}
