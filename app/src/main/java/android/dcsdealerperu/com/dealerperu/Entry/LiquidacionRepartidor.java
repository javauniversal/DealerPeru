package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Josue on 16/05/16.
 */
public class LiquidacionRepartidor implements Serializable {
    @SerializedName("saldo_ventas")
    private double saldo_ventas;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("valor")
    private double valor;

    @SerializedName("total_peds")
    private int total_peds;

    @SerializedName("peds_entregados")
    private int peds_entregados;

    public int getTotal_peds() {
        return total_peds;
    }

    public void setTotal_peds(int total_peds) {
        this.total_peds = total_peds;
    }

    public int getPeds_entregados() {
        return peds_entregados;
    }

    public void setPeds_entregados(int peds_entregados) {
        this.peds_entregados = peds_entregados;
    }

    public double getSaldo_ventas() {
        return saldo_ventas;
    }

    public void setSaldo_ventas(double saldo_ventas) {
        this.saldo_ventas = saldo_ventas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
