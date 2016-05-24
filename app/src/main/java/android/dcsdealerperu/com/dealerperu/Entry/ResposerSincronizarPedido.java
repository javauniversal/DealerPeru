package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 22/05/16.
 */
public class ResposerSincronizarPedido {

    @SerializedName("estado")
    private String estado;

    @SerializedName("msg")
    private String msg;

    @SerializedName("nropedido")
    private int nropedido;

    @SerializedName("idpos")
    private int idpos;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getNropedido() {
        return nropedido;
    }

    public void setNropedido(int nropedido) {
        this.nropedido = nropedido;
    }

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }
}
