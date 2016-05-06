package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Departamentos extends CategoriasEstandar implements Serializable {

    @SerializedName("ciudades")
    private List<Ciudad> ciudadList;

    public Departamentos(int id, String descripcion) {
        super(id, descripcion);
    }

    public List<Ciudad> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<Ciudad> ciudadList) {
        this.ciudadList = ciudadList;
    }

    @Override
    public String toString() {
        return descripcion;
    }

}
