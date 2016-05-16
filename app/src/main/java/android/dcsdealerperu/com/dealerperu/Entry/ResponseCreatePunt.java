package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseCreatePunt implements Serializable {

    @SerializedName("territorios")
    private List<Territorio> territorioList;

    @SerializedName("departamentos")
    private List<Departamentos> departamentosList;

    @SerializedName("estados_com")
    private List<CategoriasEstandar> estadoComunList;

    @SerializedName("categorias")
    private List<CategoriasEstandar> categoriasList;

    @SerializedName("nomenclaturas")
    private Nomenclatura nomenclaturaList;

    public Nomenclatura getNomenclaturaList() {
        return nomenclaturaList;
    }

    public void setNomenclaturaList(Nomenclatura nomenclaturaList) {
        this.nomenclaturaList = nomenclaturaList;
    }

    public List<Territorio> getTerritorioList() {
        return territorioList;
    }

    public void setTerritorioList(List<Territorio> territorioList) {
        this.territorioList = territorioList;
    }

    public List<Departamentos> getDepartamentosList() {
        return departamentosList;
    }

    public void setDepartamentosList(List<Departamentos> departamentosList) {
        this.departamentosList = departamentosList;
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

}
