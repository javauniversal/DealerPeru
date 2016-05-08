package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 7/05/16.
 */
public class ReferenciasSims {

    @SerializedName("id")
    private int id;

    @SerializedName("pn")
    private String pn;

    @SerializedName("stock")
    private int stock;

    @SerializedName("producto")
    private String producto;

    @SerializedName("dias_inve")
    private double dias_inve;

    @SerializedName("ped_sugerido")
    private String ped_sugerido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getDias_inve() {
        return dias_inve;
    }

    public void setDias_inve(double dias_inve) {
        this.dias_inve = dias_inve;
    }

    public String getPed_sugerido() {
        return ped_sugerido;
    }

    public void setPed_sugerido(String ped_sugerido) {
        this.ped_sugerido = ped_sugerido;
    }
}
