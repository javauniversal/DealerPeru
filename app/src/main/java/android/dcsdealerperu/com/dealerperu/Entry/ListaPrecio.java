package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 21/05/16.
 */
public class ListaPrecio {

    @SerializedName("id_referencia")
    private int id_referencia;

    @SerializedName("idpos")
    private int idpos;

    @SerializedName("valor_referencia")
    private double valor_referencia;

    @SerializedName("valor_directo")
    private double valor_directo;

    @SerializedName("tipo_pro")
    private int tipo_pro;

    @SerializedName("estado_accion")
    private int estado_accion;

    public int getEstado_accion() {
        return estado_accion;
    }

    public void setEstado_accion(int estado_accion) {
        this.estado_accion = estado_accion;
    }

    public int getId_referencia() {
        return id_referencia;
    }

    public void setId_referencia(int id_referencia) {
        this.id_referencia = id_referencia;
    }

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }

    public double getValor_referencia() {
        return valor_referencia;
    }

    public void setValor_referencia(double valor_referencia) {
        this.valor_referencia = valor_referencia;
    }

    public double getValor_directo() {
        return valor_directo;
    }

    public void setValor_directo(double valor_directo) {
        this.valor_directo = valor_directo;
    }

    public int getTipo_pro() {
        return tipo_pro;
    }

    public void setTipo_pro(int tipo_pro) {
        this.tipo_pro = tipo_pro;
    }
}
