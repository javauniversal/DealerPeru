package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by germangarcia on 16/05/16.
 */
public class ResponseRutero {

    @SerializedName("cant_ventas_sim")
    private int cant_ventas_sim;

    @SerializedName("cant_ventas_combo")
    private int cant_ventas_combo;

    @SerializedName("cant_cumplimiento_sim")
    private int cant_cumplimiento_sim;

    @SerializedName("cant_cumplimiento_combo")
    private int cant_cumplimiento_combo;

    @SerializedName("cant_quiebre_sim_mes")
    private int cant_quiebre_sim_mes;

    @SerializedName("cant_quiebre_combo_mes")
    private int cant_quiebre_combo_mes;

    @SerializedName("visita")
    private List<ResponseHome> responseHomeList;

    public int getCant_ventas_sim() {
        return cant_ventas_sim;
    }

    public void setCant_ventas_sim(int cant_ventas_sim) {
        this.cant_ventas_sim = cant_ventas_sim;
    }

    public int getCant_ventas_combo() {
        return cant_ventas_combo;
    }

    public void setCant_ventas_combo(int cant_ventas_combo) {
        this.cant_ventas_combo = cant_ventas_combo;
    }

    public int getCant_cumplimiento_sim() {
        return cant_cumplimiento_sim;
    }

    public void setCant_cumplimiento_sim(int cant_cumplimiento_sim) {
        this.cant_cumplimiento_sim = cant_cumplimiento_sim;
    }

    public int getCant_cumplimiento_combo() {
        return cant_cumplimiento_combo;
    }

    public void setCant_cumplimiento_combo(int cant_cumplimiento_combo) {
        this.cant_cumplimiento_combo = cant_cumplimiento_combo;
    }

    public List<ResponseHome> getResponseHomeList() {
        return responseHomeList;
    }

    public void setResponseHomeList(List<ResponseHome> responseHomeList) {
        this.responseHomeList = responseHomeList;
    }

    public int getCant_quiebre_sim_mes() {
        return cant_quiebre_sim_mes;
    }

    public void setCant_quiebre_sim_mes(int cant_quiebre_sim_mes) {
        this.cant_quiebre_sim_mes = cant_quiebre_sim_mes;
    }


    public int getCant_quiebre_combo_mes() {
        return cant_quiebre_combo_mes;
    }

    public void setCant_quiebre_combo_mes(int cant_quiebre_combo_mes) {
        this.cant_quiebre_combo_mes = cant_quiebre_combo_mes;
    }

}
