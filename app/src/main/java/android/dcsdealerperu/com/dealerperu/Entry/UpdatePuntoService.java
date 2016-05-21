package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 21/05/16.
 */
public class UpdatePuntoService {

    @SerializedName("id_movil")
    private String id_movil;

    @SerializedName("id_db")
    private int id_db;

    @SerializedName("estado")
    private String estado;

    @SerializedName("msg")
    private String msg;

    public String getId_movil() {
        return id_movil;
    }

    public void setId_movil(String id_movil) {
        this.id_movil = id_movil;
    }

    public int getId_db() {
        return id_db;
    }

    public void setId_db(int id_db) {
        this.id_db = id_db;
    }

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
}
