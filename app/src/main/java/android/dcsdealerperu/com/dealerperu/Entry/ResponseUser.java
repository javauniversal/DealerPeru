package android.dcsdealerperu.com.dealerperu.Entry;


import com.google.gson.annotations.SerializedName;

public class ResponseUser {

    @SerializedName("id")
    private int id;

    @SerializedName("cedula")
    private int cedula;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("apellido")
    private String apellido;

    @SerializedName("user")
    private String user;

    @SerializedName("estado")
    private int estado;

    @SerializedName("bd")
    private String bd;

    @SerializedName("id_distri")
    private String id_distri;

    public static ResponseUser responseUserStatic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public String getId_distri() {
        return id_distri;
    }

    public void setId_distri(String id_distri) {
        this.id_distri = id_distri;
    }

    public static ResponseUser getResponseUserStatic() {
        return responseUserStatic;
    }

    public static void setResponseUserStatic(ResponseUser responseUserStatic) {
        ResponseUser.responseUserStatic = responseUserStatic;
    }
}
