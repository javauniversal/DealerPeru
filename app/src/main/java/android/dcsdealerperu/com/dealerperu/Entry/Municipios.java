package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 19/05/16.
 */
public class Municipios {

    @SerializedName("id_muni")
    private int id_muni;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("departamento")
    private int departamento;

    @SerializedName("estado_accion")
    private int estado_accion;

    public int getId_muni() {
        return id_muni;
    }

    public void setId_muni(int id_muni) {
        this.id_muni = id_muni;
    }

    public String getNombre() {
        return descripcion;
    }

    public void setNombre(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public int getEstado_accion() {
        return estado_accion;
    }

    public void setEstado_accion(int estado_accion) {
        this.estado_accion = estado_accion;
    }
}
