package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MisBajasDetalle2 implements Serializable {

    @SerializedName("serie")
    private String serie;

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public MisBajasDetalle2(String sku) {
        this.serie = sku;
    }
}
