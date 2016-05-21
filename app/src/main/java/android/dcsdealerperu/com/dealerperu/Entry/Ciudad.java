package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Ciudad extends CategoriasEstandar implements Serializable {

    @SerializedName("id_muni")
    private int id_muni;

    @SerializedName("id_depto")
    private int departamento;

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    @SerializedName("distritos")
    private List<Distrito> distritoList;

    public int getId_muni() {
        return id_muni;
    }

    public void setId_muni(int id_muni) {
        this.id_muni = id_muni;
    }

    public List<Distrito> getDistritoList() {
        return distritoList;
    }

    public void setDistritoList(List<Distrito> distritoList) {
        this.distritoList = distritoList;
    }

    public Ciudad(int id, String descripcion) {
        super(id, descripcion);
    }

    @Override
    public String toString() {
        return descripcion;
    }

}
