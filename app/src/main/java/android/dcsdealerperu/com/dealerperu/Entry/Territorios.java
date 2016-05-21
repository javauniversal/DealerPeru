package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 19/05/16.
 */
public class Territorios extends CategoriasEstandar {

    @SerializedName("nombre_territorio")
    private int nombre_territorio;

    @SerializedName("estado")
    private int estado;

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getNombre_territorio() {
        return nombre_territorio;
    }

    public void setNombre_territorio(int nombre_territorio) {
        this.nombre_territorio = nombre_territorio;
    }

    public Territorios(int id, String descripcion) {
        super(id, descripcion);
    }

}
