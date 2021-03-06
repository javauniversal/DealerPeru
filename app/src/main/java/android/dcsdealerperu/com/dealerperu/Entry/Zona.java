package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Zona implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    public String descripcion;

    @SerializedName("id_territorio")
    private int id_territorio;

    @SerializedName("estado")
    private int estado;

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_territorio() {
        return id_territorio;
    }

    public void setId_territorio(int id_territorio) {
        this.id_territorio = id_territorio;
    }

    @Override
    public String toString() {
        return descripcion;
    }

}
