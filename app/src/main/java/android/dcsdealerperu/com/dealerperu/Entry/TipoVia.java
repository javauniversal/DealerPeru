package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

public class TipoVia extends CategoriasEstandar {

    @SerializedName("siglas")
    private String siglas;

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public TipoVia(int id, String descripcion) {
        super(id, descripcion);
    }
}
