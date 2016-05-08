package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Josue on 7/05/16.
 */
public class ResponseMisBajas implements Serializable{

    @SerializedName("mis_bajas")
    private List<MisBajas> misBajas;

    public List<MisBajas> getMisBajas() {
        return misBajas;
    }

    public void setMisBajas(List<MisBajas> misBajas) {
        this.misBajas = misBajas;
    }
}
