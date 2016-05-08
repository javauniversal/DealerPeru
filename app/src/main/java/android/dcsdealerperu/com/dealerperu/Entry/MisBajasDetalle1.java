package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class MisBajasDetalle1 implements Serializable{

    @SerializedName("sku")
    private String sku;

    @SerializedName("cant_sku")
    private int cant_sku;

    @SerializedName("mis_bajas_detalle2")
    private List<MisBajasDetalle2> misBajasDetalle2List;

    public MisBajasDetalle1(String sku, int cant_sku) {
        this.sku = sku;
        this.cant_sku = cant_sku;
    }

    public List<MisBajasDetalle2> getMisBajasDetalle2List() {
        return misBajasDetalle2List;
    }

    public void setMisBajasDetalle2List(List<MisBajasDetalle2> misBajasDetalle2List) {
        this.misBajasDetalle2List = misBajasDetalle2List;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCant_sku() {
        return cant_sku;
    }

    public void setCant_sku(int cant_sku) {
        this.cant_sku = cant_sku;
    }
}
