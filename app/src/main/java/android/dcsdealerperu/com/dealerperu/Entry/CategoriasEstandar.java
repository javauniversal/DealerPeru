package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoriasEstandar implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    public String descripcion;

    @SerializedName("subcategorias")
    public List<Subcategorias> listSubCategoria;

    public List<Subcategorias> getListSubCategoria() {
        return listSubCategoria;
    }

    public void setListSubCategoria(List<Subcategorias> listSubCategoria) {
        this.listSubCategoria = listSubCategoria;
    }

    public CategoriasEstandar(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
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

    @Override
    public String toString() {
        return descripcion;
    }

}
