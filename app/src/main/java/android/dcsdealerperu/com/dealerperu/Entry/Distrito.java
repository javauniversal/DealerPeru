package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

public class Distrito extends CategoriasEstandar {

    @SerializedName("id_muni")
    private int id_muni;

    @SerializedName("id_depto")
    private int id_depto;

    public int getId_muni() {
        return id_muni;
    }

    public void setId_muni(int id_muni) {
        this.id_muni = id_muni;
    }

    public int getId_depto() {
        return id_depto;
    }

    public void setId_depto(int id_depto) {
        this.id_depto = id_depto;
    }

    public Distrito(int id, String descripcion) {
        super(id, descripcion);
    }

}
