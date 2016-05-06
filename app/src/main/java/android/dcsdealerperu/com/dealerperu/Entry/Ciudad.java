package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Ciudad extends CategoriasEstandar implements Serializable {

    @SerializedName("id_muni")
    private int id_muni;

    @SerializedName("id_depto")
    private int id_depto;

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

    public int getId_depto() {
        return id_depto;
    }

    public void setId_depto(int id_depto) {
        this.id_depto = id_depto;
    }

    @Override
    public String toString() {
        return descripcion;
    }

}
