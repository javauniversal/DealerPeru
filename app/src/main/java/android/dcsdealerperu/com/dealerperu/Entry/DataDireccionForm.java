package android.dcsdealerperu.com.dealerperu.Entry;


import java.util.List;

public class DataDireccionForm {

    private static List<Territorio> territorioList;

    private static List<CategoriasEstandar> estadoComunList;

    private static List<CategoriasEstandar> categoriasList;

    public static List<Territorio> getTerritorioList() {
        return territorioList;
    }

    public static void setTerritorioList(List<Territorio> territorioList) {
        DataDireccionForm.territorioList = territorioList;
    }

    public static List<CategoriasEstandar> getEstadoComunList() {
        return estadoComunList;
    }

    public static void setEstadoComunList(List<CategoriasEstandar> estadoComunList) {
        DataDireccionForm.estadoComunList = estadoComunList;
    }

    public static List<CategoriasEstandar> getCategoriasList() {
        return categoriasList;
    }

    public static void setCategoriasList(List<CategoriasEstandar> categoriasList) {
        DataDireccionForm.categoriasList = categoriasList;
    }
}
