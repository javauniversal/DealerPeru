package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseHome implements Serializable {

    @SerializedName("idpos")
    private int idpos;

    @SerializedName("razon")
    private String razon;

    @SerializedName("nombre_cli")
    private String nombre_cli;

    @SerializedName("barrio")
    private String barrio;

    @SerializedName("cel")
    private String cel;

    @SerializedName("estado_comercial")
    private String estado_comercial;

    @SerializedName("email")
    private String email;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("departamento")
    private String departamento;

    @SerializedName("munucipio")
    private String munucipio;

    @SerializedName("id_circuito")
    private String id_circuito;

    @SerializedName("circuito")
    private String circuito;

    @SerializedName("idruta")
    private String idruta;

    @SerializedName("ruta")
    private String ruta;

    @SerializedName("tel")
    private String tel;

    @SerializedName("detalle")
    private String detalle;

    @SerializedName("tipo_visita")
    private int tipo_visita;



    @SerializedName("rutero")
    private int rutero;

    public static List<ResponseHome> responseHomeListS;

    public static List<ResponseHome> getResponseHomeListS() {
        return responseHomeListS;
    }

    public static void setResponseHomeListS(List<ResponseHome> responseHomeListS) {
        ResponseHome.responseHomeListS = responseHomeListS;
    }

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getNombre_cli() {
        return nombre_cli;
    }

    public void setNombre_cli(String nombre_cli) {
        this.nombre_cli = nombre_cli;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getEstado_comercial() {
        return estado_comercial;
    }

    public void setEstado_comercial(String estado_comercial) {
        this.estado_comercial = estado_comercial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunucipio() {
        return munucipio;
    }

    public void setMunucipio(String munucipio) {
        this.munucipio = munucipio;
    }

    public String getId_circuito() {
        return id_circuito;
    }

    public void setId_circuito(String id_circuito) {
        this.id_circuito = id_circuito;
    }

    public String getCircuito() {
        return circuito;
    }

    public void setCircuito(String circuito) {
        this.circuito = circuito;
    }

    public String getIdruta() {
        return idruta;
    }

    public void setIdruta(String idruta) {
        this.idruta = idruta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getTipo_visita() {
        return tipo_visita;
    }

    public void setTipo_visita(int tipo_visita) {
        this.tipo_visita = tipo_visita;
    }

    public int getRutero() {
        return rutero;
    }

    public void setRutero(int rutero) {
        this.rutero = rutero;
    }
}
