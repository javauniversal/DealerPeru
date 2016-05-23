package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by germangarcia on 22/05/16.
 */
public class SincronizarPedidos {

    @SerializedName("autoincrement")
    private int autoincrement;

    @SerializedName("iduser")
    private int iduser;

    @SerializedName("iddistri")
    private String iddistri;

    @SerializedName("bd")
    private String bd;

    @SerializedName("idpos")
    private int idpos;

    @SerializedName("latitud")
    private double latitud;

    @SerializedName("longitud")
    private double longitud;

    @SerializedName("fecha_visita")
    private String fecha_visita;

    @SerializedName("hora_visita")
    private String hora_visita;

    public String getFecha_visita() {
        return fecha_visita;
    }

    public void setFecha_visita(String fecha_visita) {
        this.fecha_visita = fecha_visita;
    }

    public String getHora_visita() {
        return hora_visita;
    }

    public void setHora_visita(String hora_visita) {
        this.hora_visita = hora_visita;
    }

    @SerializedName("list_detalle")
    private List<ReferenciasSims> referenciasSimsList;

    public int getAutoincrement() {
        return autoincrement;
    }

    public void setAutoincrement(int autoincrement) {
        this.autoincrement = autoincrement;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getIddistri() {
        return iddistri;
    }

    public void setIddistri(String iddistri) {
        this.iddistri = iddistri;
    }

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
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

    public List<ReferenciasSims> getReferenciasSimsList() {
        return referenciasSimsList;
    }

    public void setReferenciasSimsList(List<ReferenciasSims> referenciasSimsList) {
        this.referenciasSimsList = referenciasSimsList;
    }
}
