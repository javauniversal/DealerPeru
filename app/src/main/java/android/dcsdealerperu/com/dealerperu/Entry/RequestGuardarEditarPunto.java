package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RequestGuardarEditarPunto implements Serializable {

    private String idAuto;

    @SerializedName("idpos")
    private int idpos;

    @SerializedName("nombre_punto")
    private String nombre_punto;

    @SerializedName("tipo_documento")
    private int tipo_documento;

    @SerializedName("cedula")
    public String cedula;

    @SerializedName("nombre_cliente")
    public String nombre_cliente;

    @SerializedName("email")
    public String email;

    @SerializedName("depto")
    public int depto;

    @SerializedName("ciudad")
    public int ciudad;

    @SerializedName("distrito")
    public int distrito;

    @SerializedName("telefono")
    public String telefono;

    @SerializedName("celular")
    public String celular;

    @SerializedName("estado_com")
    public int estado_com;

    @SerializedName("zona")
    public int zona;

    @SerializedName("territorio")
    public int territorio;

    @SerializedName("categoria")
    public int categoria;

    @SerializedName("subcategoria")
    public int subcategoria;

    @SerializedName("vende_recargas")
    public int vende_recargas;

    @SerializedName("texto_direccion")
    public String texto_direccion;

    @SerializedName("ref_direccion")
    public String ref_direccion;

    @SerializedName("codigo_cum")
    public String codigo_cum;

    @SerializedName("tipo_via")
    public int tipo_via;

    @SerializedName("descripcion")
    public String descripcion;

    @SerializedName("nombre_via")
    public String nombre_via;

    @SerializedName("nro_via")
    public String nro_via;

    @SerializedName("lote")
    public String lote;

    @SerializedName("nombre_mzn")
    public String nombre_mzn;

    @SerializedName("tipo_interior")
    public int tipo_interior;

    @SerializedName("nro_interior")
    public String nro_interior;

    @SerializedName("tipo_vivienda")
    public int tipo_vivienda;

    @SerializedName("descripcion_vivienda")
    public String descripcion_vivienda;

    @SerializedName("tipo_urbanizacion")
    public int tipo_urbanizacion;

    @SerializedName("num_int_urbanizacion")
    public String num_int_urbanizacion;

    @SerializedName("tipo_ciudad")
    public int tipo_ciudad;

    @SerializedName("des_tipo_ciudad")
    public String des_tipo_ciudad;

    @SerializedName("tipo_via_")
    public CategoriasEstandar viaEnt;

    @SerializedName("tipo_interior_")
    public CategoriasEstandar interEnt;

    @SerializedName("tipo_vivienda_")
    public CategoriasEstandar vivendaEnt;

    @SerializedName("tipo_urbanizacion_")
    public CategoriasEstandar urbaEnt;

    @SerializedName("tipo_ciudad_")
    public CategoriasEstandar ciuEnt;

    @SerializedName("accion")
    private String accion;

    @SerializedName("latitud")
    private double latitud;

    @SerializedName("longitud")
    private double longitud;

    @SerializedName("estado_visita")
    private double estadoVisita;

    public double getEstadoVisita() {
        return estadoVisita;
    }

    public void setEstadoVisita(double estadoVisita) {
        this.estadoVisita = estadoVisita;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    private List<Territorio> territorioList;

    private List<CategoriasEstandar> estadoComunList;

    private List<CategoriasEstandar> categoriasList;

    public List<Territorio> getTerritorioList() {
        return territorioList;
    }

    public void setTerritorioList(List<Territorio> territorioList) {
        this.territorioList = territorioList;
    }

    public List<CategoriasEstandar> getEstadoComunList() {
        return estadoComunList;
    }

    public void setEstadoComunList(List<CategoriasEstandar> estadoComunList) {
        this.estadoComunList = estadoComunList;
    }

    public List<CategoriasEstandar> getCategoriasList() {
        return categoriasList;
    }

    public void setCategoriasList(List<CategoriasEstandar> categoriasList) {
        this.categoriasList = categoriasList;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public CategoriasEstandar getViaEnt() {
        return viaEnt;
    }

    public void setViaEnt(CategoriasEstandar viaEnt) {
        this.viaEnt = viaEnt;
    }

    public CategoriasEstandar getInterEnt() {
        return interEnt;
    }

    public void setInterEnt(CategoriasEstandar interEnt) {
        this.interEnt = interEnt;
    }

    public CategoriasEstandar getVivendaEnt() {
        return vivendaEnt;
    }

    public void setVivendaEnt(CategoriasEstandar vivendaEnt) {
        this.vivendaEnt = vivendaEnt;
    }

    public CategoriasEstandar getUrbaEnt() {
        return urbaEnt;
    }

    public void setUrbaEnt(CategoriasEstandar urbaEnt) {
        this.urbaEnt = urbaEnt;
    }

    public CategoriasEstandar getCiuEnt() {
        return ciuEnt;
    }

    public void setCiuEnt(CategoriasEstandar ciuEnt) {
        this.ciuEnt = ciuEnt;
    }

    public String getNombre_punto() {
        return nombre_punto;
    }

    public void setNombre_punto(String nombre_punto) {
        this.nombre_punto = nombre_punto;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDepto() {
        return depto;
    }

    public void setDepto(int depto) {
        this.depto = depto;
    }

    public int getCiudad() {
        return ciudad;
    }

    public void setCiudad(int ciudad) {
        this.ciudad = ciudad;
    }

    public int getDistrito() {
        return distrito;
    }

    public void setDistrito(int distrito) {
        this.distrito = distrito;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public int getEstado_com() {
        return estado_com;
    }

    public void setEstado_com(int estado_com) {
        this.estado_com = estado_com;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(int subcategoria) {
        this.subcategoria = subcategoria;
    }

    public int getTerritorio() {
        return territorio;
    }

    public void setTerritorio(int territorio) {
        this.territorio = territorio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    public String getTexto_direccion() {
        return texto_direccion;
    }

    public void setTexto_direccion(String texto_direccion) {
        this.texto_direccion = texto_direccion;
    }

    public int getVende_recargas() {
        return vende_recargas;
    }

    public void setVende_recargas(int vende_recargas) {
        this.vende_recargas = vende_recargas;
    }

    public String getCodigo_cum() {
        return codigo_cum;
    }

    public void setCodigo_cum(String codigo_cum) {
        this.codigo_cum = codigo_cum;
    }

    public String getRef_direccion() {
        return ref_direccion;
    }

    public void setRef_direccion(String ref_direccion) {
        this.ref_direccion = ref_direccion;
    }

    public int getTipo_via() {
        return tipo_via;
    }

    public void setTipo_via(int tipo_via) {
        this.tipo_via = tipo_via;
    }

    public String getNombre_via() {
        return nombre_via;
    }

    public void setNombre_via(String nombre_via) {
        this.nombre_via = nombre_via;
    }

    public String getNro_via() {
        return nro_via;
    }

    public void setNro_via(String nro_via) {
        this.nro_via = nro_via;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNombre_mzn() {
        return nombre_mzn;
    }

    public void setNombre_mzn(String nombre_mzn) {
        this.nombre_mzn = nombre_mzn;
    }

    public int getTipo_interior() {
        return tipo_interior;
    }

    public void setTipo_interior(int tipo_interior) {
        this.tipo_interior = tipo_interior;
    }

    public String getNro_interior() {
        return nro_interior;
    }

    public void setNro_interior(String nro_interior) {
        this.nro_interior = nro_interior;
    }

    public int getTipo_vivienda() {
        return tipo_vivienda;
    }

    public void setTipo_vivienda(int tipo_vivienda) {
        this.tipo_vivienda = tipo_vivienda;
    }

    public String getDescripcion_vivienda() {
        return descripcion_vivienda;
    }

    public void setDescripcion_vivienda(String descripcion_vivienda) {
        this.descripcion_vivienda = descripcion_vivienda;
    }

    public int getTipo_urbanizacion() {
        return tipo_urbanizacion;
    }

    public void setTipo_urbanizacion(int tipo_urbanizacion) {
        this.tipo_urbanizacion = tipo_urbanizacion;
    }

    public String getNum_int_urbanizacion() {
        return num_int_urbanizacion;
    }

    public void setNum_int_urbanizacion(String num_int_urbanizacion) {
        this.num_int_urbanizacion = num_int_urbanizacion;
    }

    public int getIdpos() {
        return idpos;
    }

    public void setIdpos(int idpos) {
        this.idpos = idpos;
    }

    public int getTipo_ciudad() {
        return tipo_ciudad;
    }

    public void setTipo_ciudad(int tipo_ciudad) {
        this.tipo_ciudad = tipo_ciudad;
    }

    public String getDes_tipo_ciudad() {
        return des_tipo_ciudad;
    }

    public void setDes_tipo_ciudad(String des_tipo_ciudad) {
        this.des_tipo_ciudad = des_tipo_ciudad;
    }

    public int getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(int tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public String getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(String idAuto) {
        this.idAuto = idAuto;
    }

}
