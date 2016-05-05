package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

public class Zona {

    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    public String descripcion;

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

    @SerializedName("id_territorio")
    private int id_territorio;

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
