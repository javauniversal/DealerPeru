package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseMarcarPedido implements Serializable {

    @SerializedName("estado")
    private int estado;

    @SerializedName("id_pos")
    private int id_pos;

    @SerializedName("razon_social")
    private String razon_social;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("zona")
    private String zona;

    @SerializedName("territorio")
    private String territorio;

    @SerializedName("depto")
    private String depto;

    @SerializedName("provincia")
    private String provincia;

    @SerializedName("distrito")
    private String distrito;

    @SerializedName("pedidos")
    private List<Pedidos> pedidosList;

    @SerializedName("tiene_dir")
    private int tiene_dir;

    @SerializedName("msg")
    private String msg;

    @SerializedName("motivos")
    private List<Motivos> motivosList;

    public List<Motivos> getMotivosList() {
        return motivosList;
    }

    public void setMotivosList(List<Motivos> motivosList) {
        this.motivosList = motivosList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getId_pos() {
        return id_pos;
    }

    public void setId_pos(int id_pos) {
        this.id_pos = id_pos;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getTerritorio() {
        return territorio;
    }

    public void setTerritorio(String territorio) {
        this.territorio = territorio;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public List<Pedidos> getPedidosList() {
        return pedidosList;
    }

    public void setPedidosList(List<Pedidos> pedidosList) {
        this.pedidosList = pedidosList;
    }

    public int getTiene_dir() {
        return tiene_dir;
    }

    public void setTiene_dir(int tiene_dir) {
        this.tiene_dir = tiene_dir;
    }
}
