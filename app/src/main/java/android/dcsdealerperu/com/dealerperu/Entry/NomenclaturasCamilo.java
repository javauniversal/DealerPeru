package android.dcsdealerperu.com.dealerperu.Entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 19/05/16.
 */
public class NomenclaturasCamilo {

    @SerializedName("id")
    private int id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("letras")
    private String letras;

    @SerializedName("tipo_nom")
    private int tipo_nom;

    @SerializedName("estado_accion")
    private int estado_accion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLetras() {
        return letras;
    }

    public void setLetras(String letras) {
        this.letras = letras;
    }

    public int getTipo_nom() {
        return tipo_nom;
    }

    public void setTipo_nom(int tipo_nom) {
        this.tipo_nom = tipo_nom;
    }

    public int getEstado_accion() {
        return estado_accion;
    }

    public void setEstado_accion(int estado_accion) {
        this.estado_accion = estado_accion;
    }
}
