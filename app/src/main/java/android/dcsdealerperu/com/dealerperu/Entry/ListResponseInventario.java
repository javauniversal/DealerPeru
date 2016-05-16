package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListResponseInventario implements Serializable {

    @SerializedName("referencias")
    private List<ResponseInventario> referencias;

    public List<ResponseInventario> getReferencias(){
        return referencias;
    }

    public void setReferencias(List<ResponseInventario> referencias) {
        this.referencias = referencias;
    }

}

