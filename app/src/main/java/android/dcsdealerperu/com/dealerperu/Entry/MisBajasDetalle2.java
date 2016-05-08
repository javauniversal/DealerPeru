package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Josue on 7/05/16.
 */
public class MisBajasDetalle2 implements Serializable{

    @SerializedName("serie")
    private String serie;

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }
}
