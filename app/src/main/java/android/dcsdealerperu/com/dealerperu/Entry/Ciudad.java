package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

public class Ciudad extends CategoriasEstandar {

    @SerializedName("id_depto")
    private int id_depto;

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
