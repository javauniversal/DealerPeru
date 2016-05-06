package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Nomenclatura {

    @SerializedName("tipo_via")
    private List<TipoVia> tipoViaList;

    @SerializedName("tipo_vivienda")
    private List<TipoVivienda> tipoViviendaList;

    @SerializedName("tipo_interior")
    private List<TipoInterior> tipoInteriorList;

    @SerializedName("tipo_urbanizacion")
    private List<TipoUrbanizacion> tipoUrbanizacionList;

    @SerializedName("tipo_ciudad")
    private List<TipoCiudad> tipoCiudadList;

    public List<TipoVia> getTipoViaList() {
        return tipoViaList;
    }

    public void setTipoViaList(List<TipoVia> tipoViaList) {
        this.tipoViaList = tipoViaList;
    }

    public List<TipoVivienda> getTipoViviendaList() {
        return tipoViviendaList;
    }

    public void setTipoViviendaList(List<TipoVivienda> tipoViviendaList) {
        this.tipoViviendaList = tipoViviendaList;
    }

    public List<TipoInterior> getTipoInteriorList() {
        return tipoInteriorList;
    }

    public void setTipoInteriorList(List<TipoInterior> tipoInteriorList) {
        this.tipoInteriorList = tipoInteriorList;
    }

    public List<TipoUrbanizacion> getTipoUrbanizacionList() {
        return tipoUrbanizacionList;
    }

    public void setTipoUrbanizacionList(List<TipoUrbanizacion> tipoUrbanizacionList) {
        this.tipoUrbanizacionList = tipoUrbanizacionList;
    }

    public List<TipoCiudad> getTipoCiudadList() {
        return tipoCiudadList;
    }

    public void setTipoCiudadList(List<TipoCiudad> tipoCiudadList) {
        this.tipoCiudadList = tipoCiudadList;
    }
}
