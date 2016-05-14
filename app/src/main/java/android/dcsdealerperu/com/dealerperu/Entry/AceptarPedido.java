package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by germangarcia on 14/05/16.
 */
public class AceptarPedido {

    @SerializedName("nroPedido")
    private int nroPedido;

    @SerializedName("cant_pedido_p")
    private int cant_pedido_p;

    @SerializedName("total_pedido_p")
    private double total_pedido_p;

    @SerializedName("fecha_pedido")
    private String fecha_pedido;

    @SerializedName("hora_pedido")
    private String hora_pedido;

    @SerializedName("idpos")
    private int idpos;

    @SerializedName("nombre_punto")
    private String nombre_punto;

    @SerializedName("estado_pedido")
    private int estado_pedido;

    @SerializedName("fecha_entrega")
    private String fecha_entrega;

    @SerializedName("nombre_depa")
    private String nombre_depa;

    @SerializedName("nombre_mun")
    private String nombre_mun;

    @SerializedName("nombre_dis")
    private String nombre_dis;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("id_compro")
    private int id_compro;

    @SerializedName("deta_pedido")
    private List<DestallePedido> destallePedidoList;

    public String marcaProducto = "";

    public List<DestallePedido> getDestallePedidoList() {
        return destallePedidoList;
    }

    public void setDestallePedidoList(List<DestallePedido> destallePedidoList) {
        this.destallePedidoList = destallePedidoList;
    }

    public int getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(int nroPedido) {
        this.nroPedido = nroPedido;
    }

    public int getCant_pedido_p() {
        return cant_pedido_p;
    }

    public void setCant_pedido_p(int cant_pedido_p) {
        this.cant_pedido_p = cant_pedido_p;
    }

    public double getTotal_pedido_p() {
        return total_pedido_p;
    }

    public void setTotal_pedido_p(double total_pedido_p) {
        this.total_pedido_p = total_pedido_p;
    }

    public String getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(String fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public String getHora_pedido() {
        return hora_pedido;
    }

    public void setHora_pedido(String hora_pedido) {
        this.hora_pedido = hora_pedido;
    }

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }

    public String getNombre_punto() {
        return nombre_punto;
    }

    public void setNombre_punto(String nombre_punto) {
        this.nombre_punto = nombre_punto;
    }

    public int getEstado_pedido() {
        return estado_pedido;
    }

    public void setEstado_pedido(int estado_pedido) {
        this.estado_pedido = estado_pedido;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getNombre_depa() {
        return nombre_depa;
    }

    public void setNombre_depa(String nombre_depa) {
        this.nombre_depa = nombre_depa;
    }

    public String getNombre_mun() {
        return nombre_mun;
    }

    public void setNombre_mun(String nombre_mun) {
        this.nombre_mun = nombre_mun;
    }

    public String getNombre_dis() {
        return nombre_dis;
    }

    public void setNombre_dis(String nombre_dis) {
        this.nombre_dis = nombre_dis;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getId_compro() {
        return id_compro;
    }

    public void setId_compro(int id_compro) {
        this.id_compro = id_compro;
    }
}
