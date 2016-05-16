package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

public class Descripcion {

    @SerializedName("tipo_via")
    private TipoVia tipoViaList;

    @SerializedName("tipo_vivienda")
    private TipoVivienda tipoViviendaList;

    @SerializedName("tipo_interior")
    private TipoInterior tipoInteriorList;

    @SerializedName("tipo_urbanizacion")
    private TipoUrbanizacion tipoUrbanizacionList;

    @SerializedName("tipo_ciudad")
    private TipoCiudad tipoCiudadList;

    public TipoVia getTipoViaList() {
        return tipoViaList;
    }

    public void setTipoViaList(TipoVia tipoViaList) {
        this.tipoViaList = tipoViaList;
    }

    public TipoVivienda getTipoViviendaList() {
        return tipoViviendaList;
    }

    public void setTipoViviendaList(TipoVivienda tipoViviendaList) {
        this.tipoViviendaList = tipoViviendaList;
    }

    public TipoInterior getTipoInteriorList() {
        return tipoInteriorList;
    }

    public void setTipoInteriorList(TipoInterior tipoInteriorList) {
        this.tipoInteriorList = tipoInteriorList;
    }

    public TipoUrbanizacion getTipoUrbanizacionList() {
        return tipoUrbanizacionList;
    }

    public void setTipoUrbanizacionList(TipoUrbanizacion tipoUrbanizacionList) {
        this.tipoUrbanizacionList = tipoUrbanizacionList;
    }

    public TipoCiudad getTipoCiudadList() {
        return tipoCiudadList;
    }

    public void setTipoCiudadList(TipoCiudad tipoCiudadList) {
        this.tipoCiudadList = tipoCiudadList;
    }
}
