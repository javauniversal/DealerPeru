package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 5/05/16.
 */
public class TipoCiudad extends CategoriasEstandar {

    @SerializedName("siglas")
    private String siglas;

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public TipoCiudad(int id, String descripcion) {
        super(id, descripcion);
    }

}
