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
