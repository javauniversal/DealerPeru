package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by germangarcia on 19/05/16.
 */
public class Sincronizar {

    @SerializedName("territorios")
    private List<Territorios> territoriosList;

    @SerializedName("zonas")
    private List<Zona> zonaList;

    @SerializedName("puntos")
    private List<RequestGuardarEditarPunto> puntosList;

    @SerializedName("categorias_puntos")
    private List<CategoriasEstandar> categoriasEstandarList;

    @SerializedName("departamentos")
    private List<CategoriasEstandar> listDepartamento;

    @SerializedName("distritos")
    private List<Distrito> distritoList;

    @SerializedName("estado_comercial")
    private List<CategoriasEstandar> listEstadoComercial;

    @SerializedName("municipios")
    private List<Municipios> municipiosList;

    @SerializedName("nomenclaturas")
    private List<NomenclaturasCamilo> nomenclaturasCamiloList;

    @SerializedName("subcategorias_puntos")
    private List<Subcategorias> subcategoriasList;

    @SerializedName("refes_sims")
    private List<ReferenciasSims> referenciasSimsList;

    @SerializedName("refes_combo")
    private List<ReferenciasCombos> referenciasCombosList;

    @SerializedName("lista_precios")
    private List<ListaPrecio> listaPrecios;

    @SerializedName("fecha_sincroniza")
    private String fecha_sincroniza;

    public String getFecha_sincroniza() {
        return fecha_sincroniza;
    }

    public void setFecha_sincroniza(String fecha_sincroniza) {
        this.fecha_sincroniza = fecha_sincroniza;
    }

    public List<Territorios> getTerritoriosList() {
        return territoriosList;
    }

    public void setTerritoriosList(List<Territorios> territoriosList) {
        this.territoriosList = territoriosList;
    }

    public List<Zona> getZonaList() {
        return zonaList;
    }

    public void setZonaList(List<Zona> zonaList) {
        this.zonaList = zonaList;
    }

    public List<RequestGuardarEditarPunto> getPuntosList() {
        return puntosList;
    }

    public void setPuntosList(List<RequestGuardarEditarPunto> puntosList) {
        this.puntosList = puntosList;
    }

    public List<CategoriasEstandar> getCategoriasEstandarList() {
        return categoriasEstandarList;
    }

    public void setCategoriasEstandarList(List<CategoriasEstandar> categoriasEstandarList) {
        this.categoriasEstandarList = categoriasEstandarList;
    }

    public List<CategoriasEstandar> getListDepartamento() {
        return listDepartamento;
    }

    public void setListDepartamento(List<CategoriasEstandar> listDepartamento) {
        this.listDepartamento = listDepartamento;
    }

    public List<Distrito> getDistritoList() {
        return distritoList;
    }

    public void setDistritoList(List<Distrito> distritoList) {
        this.distritoList = distritoList;
    }

    public List<CategoriasEstandar> getListEstadoComercial() {
        return listEstadoComercial;
    }

    public void setListEstadoComercial(List<CategoriasEstandar> listEstadoComercial) {
        this.listEstadoComercial = listEstadoComercial;
    }

    public List<Municipios> getMunicipiosList() {
        return municipiosList;
    }

    public void setMunicipiosList(List<Municipios> municipiosList) {
        this.municipiosList = municipiosList;
    }

    public List<NomenclaturasCamilo> getNomenclaturasCamiloList() {
        return nomenclaturasCamiloList;
    }

    public void setNomenclaturasCamiloList(List<NomenclaturasCamilo> nomenclaturasCamiloList) {
        this.nomenclaturasCamiloList = nomenclaturasCamiloList;
    }

    public List<Subcategorias> getSubcategoriasList() {
        return subcategoriasList;
    }

    public void setSubcategoriasList(List<Subcategorias> subcategoriasList) {
        this.subcategoriasList = subcategoriasList;
    }

    public List<ReferenciasSims> getReferenciasSimsList() {
        return referenciasSimsList;
    }

    public void setReferenciasSimsList(List<ReferenciasSims> referenciasSimsList) {
        this.referenciasSimsList = referenciasSimsList;
    }

    public List<ListaPrecio> getListaPrecios() {
        return listaPrecios;
    }

    public void setListaPrecios(List<ListaPrecio> listaPrecios) {
        this.listaPrecios = listaPrecios;
    }

    public List<ReferenciasCombos> getReferenciasCombosList() {
        return referenciasCombosList;
    }

    public void setReferenciasCombosList(List<ReferenciasCombos> referenciasCombosList) {
        this.referenciasCombosList = referenciasCombosList;
    }

}
