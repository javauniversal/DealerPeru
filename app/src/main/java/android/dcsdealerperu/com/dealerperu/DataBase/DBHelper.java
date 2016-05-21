package android.dcsdealerperu.com.dealerperu.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.dcsdealerperu.com.dealerperu.Entry.CategoriasEstandar;
import android.dcsdealerperu.com.dealerperu.Entry.Ciudad;
import android.dcsdealerperu.com.dealerperu.Entry.Departamentos;
import android.dcsdealerperu.com.dealerperu.Entry.Distrito;
import android.dcsdealerperu.com.dealerperu.Entry.Nomenclatura;
import android.dcsdealerperu.com.dealerperu.Entry.Referencia;
import android.dcsdealerperu.com.dealerperu.Entry.ReferenciasSims;
import android.dcsdealerperu.com.dealerperu.Entry.RequestGuardarEditarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseCreatePunt;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseUser;
import android.dcsdealerperu.com.dealerperu.Entry.Sincronizar;
import android.dcsdealerperu.com.dealerperu.Entry.Subcategorias;
import android.dcsdealerperu.com.dealerperu.Entry.Territorio;
import android.dcsdealerperu.com.dealerperu.Entry.TimeService;
import android.dcsdealerperu.com.dealerperu.Entry.TipoCiudad;
import android.dcsdealerperu.com.dealerperu.Entry.TipoInterior;
import android.dcsdealerperu.com.dealerperu.Entry.TipoUrbanizacion;
import android.dcsdealerperu.com.dealerperu.Entry.TipoVia;
import android.dcsdealerperu.com.dealerperu.Entry.TipoVivienda;
import android.dcsdealerperu.com.dealerperu.Entry.Tracing;
import android.dcsdealerperu.com.dealerperu.Entry.Zona;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MydbDealerPeru.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlIntro = "CREATE TABLE intro (id integer primary key AUTOINCREMENT, idintro text )";

        String sqlTimeServices = "CREATE TABLE time_services (id integer primary key AUTOINCREMENT, idUser int, traking int, timeservice int, idDistri int, dataBase TEXT, fechainicial TEXT, fechafinal TEXT )";

        String sqlTraking = "CREATE TABLE tracing (id integer primary key AUTOINCREMENT, idUser INT, imei TEXT, dateTime TEXT, batteryLavel INT, temperatura INT, latitud REAL, " +
                            " longitud REAL, idDistri INT, dataBase TEXT, fechainicial TEXT, fechafinal TEXT )";

        String sqlCarrito = "CREATE TABLE carrito_pedido (id_carrito integer primary key AUTOINCREMENT, id_producto INT, pn_pro TEXT, stock INT, producto TEXT, dias_inve REAL, ped_sugerido TEXT, " +
                " cantidad_pedida INT, id_usuario TEXT, id_punto INT, latitud TEXT, longitud TEXT, tipo_producto INT, producto_img TEXT, precio_referencia REAL, precio_publico REAL, referencia INT)";


        String sqlLoginUsuario = "CREATE TABLE login (id INT, cedula INT, nombre TEXT, apellido TEXT, user TEXT, estado INT, bd TEXT, id_distri TEXT, perfil INT, igv REAL, intervalo INT, hora_inicial TEXT, " +
                " hora_final TEXT, cantidad_envios INT, fechaSincro, TEXT, password TEXT)";


        String sqltTrritorios = "CREATE TABLE territorio (id INT, descripcion TEXT, estado INT )";

        String sqlZonas = "CREATE TABLE zona (id INT, descripcion TEXT, id_territorio INT, estado INT )";

        String sqlCategoria = "CREATE TABLE categoria (id INT, descripcion TEXT, estado_accion INT )";

        String sqlDepartamento = "CREATE TABLE departamento (id INT, descripcion TEXT, estado_accion INT )";

        String sqlIPunto = "CREATE TABLE punto (id_tabla TEXT, accion TEXT, categoria INT, cedula TEXT, celular TEXT, ciudad INT, codigo_cum TEXT, depto INT, des_tipo_ciudad TEXT, " +
                " descripcion_vivienda TEXT, distrito INT, email TEXT, estado_com INT, idpos INT, lote TEXT, nombre_cliente TEXT, nombre_mzn TEXT, nombre_punto TEXT, nombre_via TEXT, " +
                " nro_interior TEXT, nro_via INT, num_int_urbanizacion TEXT, ref_direccion TEXT, subcategoria INT, telefono INT, territorio_punto INT, texto_direccion TEXT, tipo_ciudad INT, " +
                " tipo_documento INT, tipo_interior INT, tipo_urbanizacion INT, tipo_via INT, tipo_vivienda INT, vende_recargas INT, zona INT, latitud REAL, longitud REAL) ";

        String sqlEstadoComercial = "CREATE TABLE estado_comercial (id INT, descripcion TEXT, estado_accion INT)";

        String sqlMunicipios = "CREATE TABLE municipios (id_muni INT, descripcion TEXT, departamento INT, estado_accion INT)";

        String sqlNomenclaturas = "CREATE TABLE nomenclaturas (id INT, nombre INT, letras TEXT, tipo_nom INT, estado_accion INT)";

        String sqlSubcategoriasPuntos = "CREATE TABLE subcategorias_puntos (id INT, descripcion TEXT, id_categoria INT, estado_accion INT)";

        String sqlDistritos = "CREATE TABLE distritos (id INT, descripcion TEXT, id_muni INT, id_depto INT, estado_accion INT)";

        db.execSQL(sqlIntro);
        db.execSQL(sqlCarrito);
        db.execSQL(sqlTimeServices);
        db.execSQL(sqlTraking);
        db.execSQL(sqlLoginUsuario);

        db.execSQL(sqltTrritorios);
        db.execSQL(sqlZonas);
        db.execSQL(sqlCategoria);
        db.execSQL(sqlDepartamento);
        db.execSQL(sqlIPunto);

        db.execSQL(sqlEstadoComercial);
        db.execSQL(sqlMunicipios);
        db.execSQL(sqlNomenclaturas);
        db.execSQL(sqlSubcategoriasPuntos);
        db.execSQL(sqlDistritos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS intro");
        db.execSQL("DROP TABLE IF EXISTS carrito_pedido");
        db.execSQL("DROP TABLE IF EXISTS time_services");
        db.execSQL("DROP TABLE IF EXISTS tracing");
        db.execSQL("DROP TABLE IF EXISTS login");
        db.execSQL("DROP TABLE IF EXISTS territorio");
        db.execSQL("DROP TABLE IF EXISTS zona");
        db.execSQL("DROP TABLE IF EXISTS categoria");
        db.execSQL("DROP TABLE IF EXISTS departamento");
        db.execSQL("DROP TABLE IF EXISTS punto");
        this.onCreate(db);

    }

    public boolean insertDistritos(Sincronizar data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            for(int i = 0; i < data.getDistritoList().size(); i++ ) {

                if (data.getDistritoList().get(i).getEstado_accion() == 1) {
                    //Insertar.
                    values.put("id", data.getDistritoList().get(i).getId());
                    values.put("descripcion", data.getDistritoList().get(i).getDescripcion());
                    values.put("id_muni", data.getDistritoList().get(i).getId_muni());
                    values.put("id_depto", data.getDistritoList().get(i).getId_depto());
                    values.put("estado_accion", data.getDistritoList().get(i).getEstado_accion());

                    db.insert("distritos", null, values);
                } else if (data.getDistritoList().get(i).getEstado_accion() == 2) {
                    //Actualizar.
                    values.put("id", data.getDistritoList().get(i).getId());
                    values.put("descripcion", data.getDistritoList().get(i).getDescripcion());
                    values.put("id_muni", data.getDistritoList().get(i).getId_muni());
                    values.put("id_depto", data.getDistritoList().get(i).getId_depto());
                    values.put("estado_accion", data.getDistritoList().get(i).getEstado_accion());

                    int p = db.update("distritos", values, String.format("id = %1$s", data.getDistritoList().get(i).getId()), null);
                } else if (data.getDistritoList().get(i).getEstado_accion() == 0) {
                    //Eliminar.
                    int a = db.delete("distritos", "id = ? ", new String[]{String.valueOf(data.getDistritoList().get(i).getId())});
                }
            }
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean insertSubcategoriasPuntos(Sincronizar data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            for(int i = 0; i < data.getSubcategoriasList().size(); i++ ) {

                if (data.getSubcategoriasList().get(i).getEstado_accion() == 1) {
                    //Insertar.
                    values.put("id", data.getSubcategoriasList().get(i).getId());
                    values.put("descripcion", data.getSubcategoriasList().get(i).getDescripcion());
                    values.put("id_categoria", data.getSubcategoriasList().get(i).getId_categoria());
                    values.put("estado_accion", data.getSubcategoriasList().get(i).getEstado_accion());

                    db.insert("subcategorias_puntos", null, values);
                } else if (data.getSubcategoriasList().get(i).getEstado_accion() == 2) {
                    //Actualizar.
                    values.put("id", data.getSubcategoriasList().get(i).getId());
                    values.put("descripcion", data.getSubcategoriasList().get(i).getDescripcion());
                    values.put("id_categoria", data.getSubcategoriasList().get(i).getId_categoria());
                    values.put("estado_accion", data.getSubcategoriasList().get(i).getEstado_accion());

                    int p = db.update("subcategorias_puntos", values, String.format("id = %1$s", data.getSubcategoriasList().get(i).getId()), null);
                } else if (data.getSubcategoriasList().get(i).getEstado_accion() == 0) {
                    //Eliminar.
                    int a = db.delete("subcategorias_puntos", "id = ? ", new String[]{String.valueOf(data.getSubcategoriasList().get(i).getId())});
                }
            }
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean insertNomenclaturas(Sincronizar data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            for(int i = 0; i < data.getNomenclaturasCamiloList().size(); i++ ) {

                if (data.getNomenclaturasCamiloList().get(i).getEstado_accion() == 1) {
                    //Insertar.
                    values.put("id", data.getNomenclaturasCamiloList().get(i).getId());
                    values.put("nombre", data.getNomenclaturasCamiloList().get(i).getNombre());
                    values.put("letras", data.getNomenclaturasCamiloList().get(i).getLetras());
                    values.put("tipo_nom", data.getNomenclaturasCamiloList().get(i).getTipo_nom());
                    values.put("estado_accion", data.getNomenclaturasCamiloList().get(i).getEstado_accion());

                    db.insert("nomenclaturas", null, values);
                } else if (data.getNomenclaturasCamiloList().get(i).getEstado_accion() == 2) {
                    //Actualizar.
                    values.put("id", data.getNomenclaturasCamiloList().get(i).getId());
                    values.put("nombre", data.getNomenclaturasCamiloList().get(i).getNombre());
                    values.put("letras", data.getNomenclaturasCamiloList().get(i).getLetras());
                    values.put("tipo_nom", data.getNomenclaturasCamiloList().get(i).getTipo_nom());
                    values.put("estado_accion", data.getNomenclaturasCamiloList().get(i).getEstado_accion());

                    int p = db.update("nomenclaturas", values, String.format("id = %1$s", data.getNomenclaturasCamiloList().get(i).getId()), null);
                } else if (data.getNomenclaturasCamiloList().get(i).getEstado_accion() == 0) {
                    //Eliminar.
                    int a = db.delete("nomenclaturas", "id = ? ", new String[]{String.valueOf(data.getNomenclaturasCamiloList().get(i).getId())});
                }
            }
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean insertMunicipios(Sincronizar data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            for(int i = 0; i < data.getMunicipiosList().size(); i++ ) {

                if (data.getMunicipiosList().get(i).getEstado_accion() == 1) {
                    //Insertar.
                    values.put("id_muni", data.getMunicipiosList().get(i).getId_muni());
                    values.put("descripcion", data.getMunicipiosList().get(i).getNombre());
                    values.put("departamento", data.getMunicipiosList().get(i).getDepartamento());
                    values.put("estado_accion", data.getMunicipiosList().get(i).getEstado_accion());

                    db.insert("municipios", null, values);
                } else if (data.getMunicipiosList().get(i).getEstado_accion() == 2) {
                    //Actualizar.
                    values.put("id_muni", data.getMunicipiosList().get(i).getId_muni());
                    values.put("descripcion", data.getMunicipiosList().get(i).getNombre());
                    values.put("departamento", data.getMunicipiosList().get(i).getDepartamento());
                    values.put("estado_accion", data.getMunicipiosList().get(i).getEstado_accion());

                    int p = db.update("municipios", values, String.format("id_muni = %1$s", data.getMunicipiosList().get(i).getId_muni()), null);
                } else if (data.getMunicipiosList().get(i).getEstado_accion() == 0) {
                    //Eliminar.
                    int a = db.delete("municipios", "id_muni = ? ", new String[]{String.valueOf(data.getMunicipiosList().get(i).getId_muni())});
                }
            }
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean insertEstadoComercial(Sincronizar data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {

            for (int i = 0; i < data.getListEstadoComercial().size(); i++) {

                if (data.getListEstadoComercial().get(i).getEstado_accion() == 1) {
                    //Insertar.
                    values.put("id", data.getListEstadoComercial().get(i).getId());
                    values.put("descripcion", data.getListEstadoComercial().get(i).getDescripcion());
                    values.put("estado_accion", data.getListEstadoComercial().get(i).getEstado_accion());

                    db.insert("estado_comercial", null, values);
                } else if (data.getListEstadoComercial().get(i).getEstado_accion() == 2) {
                    //Actualizar.
                    values.put("id", data.getListEstadoComercial().get(i).getId());
                    values.put("descripcion", data.getListEstadoComercial().get(i).getDescripcion());
                    values.put("estado_accion", data.getListEstadoComercial().get(i).getEstado_accion());

                    int p = db.update("estado_comercial", values, String.format("id = %1$s", data.getListEstadoComercial().get(i).getId()), null);
                } else if (data.getListEstadoComercial().get(i).getEstado_accion() == 0) {
                    //Eliminar.
                    int a = db.delete("estado_comercial", "id = ? ", new String[]{String.valueOf(data.getListEstadoComercial().get(i).getId())});
                }
            }

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean deleteObject(String name_database) {
        SQLiteDatabase db = this.getWritableDatabase();
        int a = db.delete(name_database, null, null);

        db.close();
        return a > 0;

    }

    public boolean insertPunto(Sincronizar data, int indicardor) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getPuntosList().size(); i++) {

                values.put("id_tabla", UUID.randomUUID().toString());
                values.put("accion", data.getPuntosList().get(i).getAccion());
                values.put("categoria", data.getPuntosList().get(i).getCategoria());
                values.put("cedula", data.getPuntosList().get(i).getCedula());
                values.put("celular", data.getPuntosList().get(i).getCelular());
                values.put("ciudad", data.getPuntosList().get(i).getCiudad());
                values.put("codigo_cum", data.getPuntosList().get(i).getCodigo_cum());
                values.put("depto", data.getPuntosList().get(i).getDepto());
                values.put("des_tipo_ciudad", data.getPuntosList().get(i).getDes_tipo_ciudad());
                values.put("descripcion_vivienda", data.getPuntosList().get(i).getDescripcion_vivienda());
                values.put("distrito", data.getPuntosList().get(i).getDistrito());
                values.put("email", data.getPuntosList().get(i).getEmail());
                values.put("estado_com", data.getPuntosList().get(i).getEstado_com());
                values.put("idpos", data.getPuntosList().get(i).getIdpos());
                values.put("lote", data.getPuntosList().get(i).getLote());
                values.put("nombre_cliente", data.getPuntosList().get(i).getNombre_cliente());
                values.put("nombre_mzn", data.getPuntosList().get(i).getNombre_mzn());
                values.put("nombre_punto", data.getPuntosList().get(i).getNombre_punto());
                values.put("nombre_via", data.getPuntosList().get(i).getNombre_via());
                values.put("nro_interior", data.getPuntosList().get(i).getNro_interior());
                values.put("nro_via", data.getPuntosList().get(i).getNro_via());
                values.put("num_int_urbanizacion", data.getPuntosList().get(i).getNum_int_urbanizacion());
                values.put("ref_direccion", data.getPuntosList().get(i).getRef_direccion());
                values.put("subcategoria", data.getPuntosList().get(i).getSubcategoria());
                values.put("telefono", data.getPuntosList().get(i).getTelefono());
                values.put("territorio_punto", data.getPuntosList().get(i).getTerritorio());
                values.put("texto_direccion", data.getPuntosList().get(i).getTexto_direccion());
                values.put("tipo_ciudad", data.getPuntosList().get(i).getTipo_ciudad());
                values.put("tipo_documento", data.getPuntosList().get(i).getTipo_documento());
                values.put("tipo_interior", data.getPuntosList().get(i).getTipo_interior());
                values.put("tipo_urbanizacion", data.getPuntosList().get(i).getTipo_urbanizacion());
                values.put("tipo_via", data.getPuntosList().get(i).getTipo_via());
                values.put("tipo_vivienda", data.getPuntosList().get(i).getTipo_vivienda());
                values.put("vende_recargas", data.getPuntosList().get(i).getVende_recargas());
                values.put("zona", data.getPuntosList().get(i).getZona());
                values.put("latitud", data.getPuntosList().get(i).getLatitud());
                values.put("longitud", data.getPuntosList().get(i).getLongitud());

                db.insert("punto", null, values);

            }

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean deletePuntoError(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        int a = db.delete("punto", "id_tabla = ? ", new String[]{id});

        db.close();
        return a > 0;

    }

    public List<RequestGuardarEditarPunto> getPuntosSincronizar(String indicardor) {

        List<RequestGuardarEditarPunto> puntoList = new ArrayList<>();

        String[] args = new String[] {indicardor};
        String sql = "SELECT * FROM punto WHERE accion = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        RequestGuardarEditarPunto requestGuardarEditarPunto;

        if (cursor.moveToFirst()) {
            do {
                requestGuardarEditarPunto = new RequestGuardarEditarPunto();

                requestGuardarEditarPunto.setIdAuto(cursor.getString(0));
                requestGuardarEditarPunto.setAccion(cursor.getString(1));
                requestGuardarEditarPunto.setCategoria(cursor.getInt(2));
                requestGuardarEditarPunto.setCedula(cursor.getString(3));
                requestGuardarEditarPunto.setCelular(cursor.getString(4));
                requestGuardarEditarPunto.setCiudad(cursor.getInt(5));
                requestGuardarEditarPunto.setCodigo_cum(cursor.getString(6));
                requestGuardarEditarPunto.setDepto(cursor.getInt(7));
                requestGuardarEditarPunto.setTipo_ciudad(cursor.getInt(8));
                requestGuardarEditarPunto.setTipo_vivienda(cursor.getInt(9));
                requestGuardarEditarPunto.setDistrito(cursor.getInt(10));
                requestGuardarEditarPunto.setEmail(cursor.getString(11));
                requestGuardarEditarPunto.setEstado_com(cursor.getInt(12));
                requestGuardarEditarPunto.setIdpos(cursor.getInt(13));
                requestGuardarEditarPunto.setLote(cursor.getString(14));
                requestGuardarEditarPunto.setNombre_cliente(cursor.getString(15));
                requestGuardarEditarPunto.setNombre_mzn(cursor.getString(16));
                requestGuardarEditarPunto.setNombre_punto(cursor.getString(17));
                requestGuardarEditarPunto.setNombre_via(cursor.getString(18));
                requestGuardarEditarPunto.setNro_interior(cursor.getString(19));
                requestGuardarEditarPunto.setNro_via(cursor.getString(20));
                requestGuardarEditarPunto.setNum_int_urbanizacion(cursor.getString(21));
                requestGuardarEditarPunto.setRef_direccion(cursor.getString(22));
                requestGuardarEditarPunto.setCategoria(cursor.getInt(23));
                requestGuardarEditarPunto.setTelefono(cursor.getString(24));
                requestGuardarEditarPunto.setTerritorio(cursor.getInt(25));
                requestGuardarEditarPunto.setTexto_direccion(cursor.getString(26));
                requestGuardarEditarPunto.setTipo_ciudad(cursor.getInt(27));
                requestGuardarEditarPunto.setTipo_documento(cursor.getInt(28));
                requestGuardarEditarPunto.setTipo_interior(cursor.getInt(29));
                requestGuardarEditarPunto.setTipo_urbanizacion(cursor.getInt(30));
                requestGuardarEditarPunto.setTipo_via(cursor.getInt(31));
                requestGuardarEditarPunto.setTipo_vivienda(cursor.getInt(32));
                requestGuardarEditarPunto.setVende_recargas(cursor.getInt(33));
                requestGuardarEditarPunto.setZona(cursor.getInt(34));
                requestGuardarEditarPunto.setLatitud(cursor.getDouble(35));
                requestGuardarEditarPunto.setLongitud(cursor.getDouble(36));

                puntoList.add(requestGuardarEditarPunto);

            } while (cursor.moveToNext());

        }

        return puntoList;

    }

    public boolean insertDepartamento(Sincronizar data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {

            for (int i = 0; i < data.getListDepartamento().size(); i++) {

                if (data.getListDepartamento().get(i).getEstado_accion() == 1) {
                    //Insertar.
                    values.put("id", data.getListDepartamento().get(i).getId());
                    values.put("descripcion", data.getListDepartamento().get(i).getDescripcion());
                    values.put("estado_accion", data.getListDepartamento().get(i).getEstado_accion());

                    db.insert("departamento", null, values);
                } else if (data.getListDepartamento().get(i).getEstado_accion() == 2) {
                    //Actualizar.
                    values.put("id", data.getListDepartamento().get(i).getId());
                    values.put("descripcion", data.getListDepartamento().get(i).getDescripcion());
                    values.put("estado_accion", data.getListDepartamento().get(i).getEstado_accion());

                    int p = db.update("departamento", values, String.format("id = %1$s", data.getListDepartamento().get(i).getId()), null);
                } else if (data.getListDepartamento().get(i).getEstado_accion() == 0) {
                    //Eliminar.
                    int a = db.delete("departamento", "id = ? ", new String[]{String.valueOf(data.getListDepartamento().get(i).getId())});
                }
            }

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public ResponseCreatePunt getDepartamentos () {

        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDepartamento = "SELECT id, descripcion FROM departamento ";

        Cursor cursor_departamento = db.rawQuery(sqlDepartamento, null);

        ResponseCreatePunt responseCreatePunt = new ResponseCreatePunt();

        List<Departamentos> departamentosList = new ArrayList<>();
        Departamentos departamento;
        if (cursor_departamento.moveToFirst()) {
            do {

                departamento = new Departamentos(cursor_departamento.getInt(0), cursor_departamento.getString(1));

                String[] args = new String[] {String.valueOf(cursor_departamento.getInt(0))};
                String sqlProvicia = "SELECT id_muni, descripcion, departamento FROM municipios WHERE departamento = ?";
                Cursor cursor_provincia = db.rawQuery(sqlProvicia, args);
                List<Ciudad> ciudadList = new ArrayList<>();
                Ciudad provincia;
                if (cursor_provincia.moveToFirst()) {
                    do {
                        provincia = new Ciudad(cursor_provincia.getInt(0), cursor_provincia.getString(1));
                        provincia.setDepartamento(cursor_provincia.getInt(2));

                        String[] args2 = new String[] {String.valueOf(cursor_provincia.getInt(0)), String.valueOf(cursor_departamento.getInt(0))};
                        String sqlDistrito = "SELECT id, descripcion, id_muni, id_depto FROM distritos WHERE id_muni = ? AND id_depto = ? ";
                        Cursor cursor_distrito = db.rawQuery(sqlDistrito, args2);
                        List<Distrito> distritoList = new ArrayList<>();
                        Distrito distrito;
                        if (cursor_distrito.moveToFirst()) {
                            do {
                                distrito = new Distrito(cursor_distrito.getInt(0), cursor_distrito.getString(1));
                                distrito.setId_muni(cursor_distrito.getInt(2));
                                distrito.setId_depto(cursor_distrito.getInt(3));

                                distritoList.add(distrito);
                            } while (cursor_distrito.moveToNext());
                        }

                        provincia.setDistritoList(distritoList);

                        ciudadList.add(provincia);
                    } while (cursor_provincia.moveToNext());
                }

                departamento.setCiudadList(ciudadList);

                departamentosList.add(departamento);

            } while (cursor_departamento.moveToNext());

            responseCreatePunt.setDepartamentosList(departamentosList);
        }

        //Recuperamos los territorios
        String sqlTerritorio = "SELECT id, descripcion FROM territorio ";
        Cursor cursor_Territorio = db.rawQuery(sqlTerritorio, null);
        List<Territorio> territorioList = new ArrayList<>();
        Territorio territorio;
        if (cursor_Territorio.moveToFirst()) {
            do {
                territorio = new Territorio();
                territorio.setId(cursor_Territorio.getInt(0));
                territorio.setDescripcion(cursor_Territorio.getString(1));

                String[] args3 = new String[] {String.valueOf(cursor_Territorio.getInt(0))};
                String sqlZona = "SELECT id, descripcion, id_territorio, estado FROM zona WHERE id_territorio = ? ";
                Cursor cursor_zona = db.rawQuery(sqlZona, args3);
                List<Zona> zonaList = new ArrayList<>();
                Zona zona;
                if (cursor_zona.moveToFirst()) {
                    do {
                        zona = new Zona();

                        zona.setId(cursor_zona.getInt(0));
                        zona.setDescripcion(cursor_zona.getString(1));
                        zona.setId_territorio(cursor_zona.getInt(2));

                        zonaList.add(zona);

                    } while (cursor_zona.moveToNext());
                }

                territorio.setZonaList(zonaList);

                territorioList.add(territorio);

            } while (cursor_Territorio.moveToNext());
        }

        responseCreatePunt.setTerritorioList(territorioList);

        // Recuperamos las nomesclaturas
        // tipo via = 0.
        // Tipo interior = 1.
        // tipo vivienda = 2.
        // Tipo urbanización = 3.
        // Tipo ciudad Poblado = 4.

        String sqlNomenclatura= "SELECT id, nombre, letras, tipo_nom, estado_accion FROM nomenclaturas";
        Cursor cursor_nomencla = db.rawQuery(sqlNomenclatura, null);

        Nomenclatura nomenclatura = new Nomenclatura();

        List<TipoVia> tipoViaList = new ArrayList<>();
        TipoVia tipoVia;

        List<TipoVivienda> tipoViviendaList = new ArrayList<>();
        TipoVivienda tipoVivienda;

        List<TipoInterior> tipoInteriorList = new ArrayList<>();
        TipoInterior tipoInterior;

        List<TipoUrbanizacion> tipoUrbanizacionList = new ArrayList<>();
        TipoUrbanizacion tipoUrbanizacion;

        List<TipoCiudad> tipoCiudadList = new ArrayList<>();
        TipoCiudad tipoCiudad;

        if (cursor_nomencla.moveToFirst()) {
            do {

                if (cursor_nomencla.getInt(3) == 0) {
                    tipoVia = new TipoVia(cursor_nomencla.getInt(0), cursor_nomencla.getString(1));
                    tipoVia.setSiglas(cursor_nomencla.getString(2));
                    tipoViaList.add(tipoVia);
                }

                if (cursor_nomencla.getInt(3) == 1) {
                    tipoInterior = new TipoInterior(cursor_nomencla.getInt(0), cursor_nomencla.getString(1));
                    tipoInterior.setSiglas(cursor_nomencla.getString(2));
                    tipoInteriorList.add(tipoInterior);
                }

                if (cursor_nomencla.getInt(3) == 2) {
                    tipoVivienda = new TipoVivienda(cursor_nomencla.getInt(0), cursor_nomencla.getString(1));
                    tipoVivienda.setSiglas(cursor_nomencla.getString(2));
                    tipoViviendaList.add(tipoVivienda);
                }

                if (cursor_nomencla.getInt(3) == 3) {
                    tipoUrbanizacion = new TipoUrbanizacion(cursor_nomencla.getInt(0), cursor_nomencla.getString(1));
                    tipoUrbanizacion.setSiglas(cursor_nomencla.getString(2));
                    tipoUrbanizacionList.add(tipoUrbanizacion);
                }

                if (cursor_nomencla.getInt(3) == 4) {
                    tipoCiudad = new TipoCiudad(cursor_nomencla.getInt(0), cursor_nomencla.getString(1));
                    tipoCiudad.setSiglas(cursor_nomencla.getString(2));
                    tipoCiudadList.add(tipoCiudad);
                }

            } while (cursor_nomencla.moveToNext());
        }

        tipoViviendaList.add(0, new TipoVivienda(0, "SELECCIONAR"));
        tipoInteriorList.add(0, new TipoInterior(0, "SELECCIONAR"));
        tipoUrbanizacionList.add(0, new TipoUrbanizacion(0, "SELECCIONAR"));
        tipoCiudadList.add(0, new TipoCiudad(0, "SELECCIONAR"));

        nomenclatura.setTipoViaList(tipoViaList);
        nomenclatura.setTipoViviendaList(tipoViviendaList);
        nomenclatura.setTipoInteriorList(tipoInteriorList);
        nomenclatura.setTipoUrbanizacionList(tipoUrbanizacionList);
        nomenclatura.setTipoCiudadList(tipoCiudadList);

        responseCreatePunt.setNomenclaturaList(nomenclatura);

        //REcuperamos las categorias.
        String sqlCategoria = "SELECT id, descripcion, estado_accion FROM categoria";
        Cursor cursor_categoria = db.rawQuery(sqlCategoria, null);
        List<CategoriasEstandar> categoriasEstandarList = new ArrayList<>();
        CategoriasEstandar categoriasEstandar;
        if (cursor_categoria.moveToFirst()) {
            do {
                categoriasEstandar = new CategoriasEstandar(cursor_categoria.getInt(0), cursor_categoria.getString(1));

                String[] args4 = new String[] {String.valueOf(cursor_categoria.getInt(0))};
                String sqlCategoriaSub = "SELECT id, descripcion, id_categoria, estado_accion FROM subcategorias_puntos WHERE id_categoria = ?";
                Cursor cursor_categoria_sub = db.rawQuery(sqlCategoriaSub, args4);
                List<Subcategorias> subcategoriasList = new ArrayList<>();
                Subcategorias subcategorias;
                if (cursor_categoria_sub.moveToFirst()) {
                    do {
                        subcategorias = new Subcategorias();
                        subcategorias.setId(cursor_categoria_sub.getInt(0));
                        subcategorias.setDescripcion(cursor_categoria_sub.getString(1));
                        subcategorias.setId_categoria(cursor_categoria_sub.getInt(2));
                        subcategoriasList.add(subcategorias);
                    } while (cursor_categoria_sub.moveToNext());
                }

                categoriasEstandar.setListSubCategoria(subcategoriasList);

                categoriasEstandarList.add(categoriasEstandar);
            } while (cursor_categoria.moveToNext());
        }

        responseCreatePunt.setCategoriasList(categoriasEstandarList);

        //Recuperamos los estados comerciales
        String sqlEstadoCom = "SELECT id, descripcion FROM estado_comercial";
        Cursor cursor_estado_com = db.rawQuery(sqlEstadoCom, null);
        List<CategoriasEstandar> categoriasEstandarList1 = new ArrayList<>();
        CategoriasEstandar categoriasEstandar1;
        if (cursor_estado_com.moveToFirst()) {
            do {
                categoriasEstandar1 = new CategoriasEstandar(cursor_estado_com.getInt(0), cursor_estado_com.getString(1));
                categoriasEstandarList1.add(categoriasEstandar1);
            } while (cursor_estado_com.moveToNext());
        }

        responseCreatePunt.setEstadoComunList(categoriasEstandarList1);

        return responseCreatePunt;

    }

    public boolean insertCategoria(Sincronizar data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {

            for (int i = 0; i < data.getCategoriasEstandarList().size(); i++) {

                if (data.getCategoriasEstandarList().get(i).getEstado_accion() == 1) {
                    //Insertar.
                    values.put("id", data.getCategoriasEstandarList().get(i).getId());
                    values.put("descripcion", data.getCategoriasEstandarList().get(i).getDescripcion());
                    values.put("estado_accion", data.getCategoriasEstandarList().get(i).getEstado_accion());

                    db.insert("categoria", null, values);
                } else if (data.getCategoriasEstandarList().get(i).getEstado_accion() == 2) {
                    //Actualizar.
                    values.put("id", data.getCategoriasEstandarList().get(i).getId());
                    values.put("descripcion", data.getCategoriasEstandarList().get(i).getDescripcion());
                    values.put("estado_accion", data.getCategoriasEstandarList().get(i).getEstado_accion());

                    int p = db.update("categoria", values, String.format("id = %1$s", data.getCategoriasEstandarList().get(i).getId()), null);
                } else if (data.getCategoriasEstandarList().get(i).getEstado_accion() == 0) {
                    //Eliminar.
                    int a = db.delete("categoria", "id = ? ", new String[]{String.valueOf(data.getCategoriasEstandarList().get(i).getId())});
                }
            }

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean insertZona(Sincronizar data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            //deleteObject("zona");
            for (int i = 0; i < data.getZonaList().size(); i++) {
                values.put("id", data.getZonaList().get(i).getId());
                values.put("descripcion", data.getZonaList().get(i).getDescripcion());
                values.put("id_territorio", data.getZonaList().get(i).getId_territorio());
                values.put("estado", data.getZonaList().get(i).getEstado());

                db.insert("zona", null, values);
            }

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean insertTerritorio(Sincronizar data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            //deleteObject("territorio");
            for (int i = 0; i < data.getTerritoriosList().size(); i++) {

                values.put("id", data.getTerritoriosList().get(i).getId());
                values.put("descripcion", data.getTerritoriosList().get(i).getDescripcion());
                values.put("estado", data.getTerritoriosList().get(i).getEstado());

                db.insert("territorio", null, values);
            }

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean updatePuntoId(String idOrigen, String idDestino){

        ContentValues valores = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        valores.put("id_tabla", idDestino);
        valores.put("idpos", idDestino);
        valores.put("accion", "modificado");

        String[] args = new String[]{idOrigen};
        int p = db.update("punto", valores, "id_tabla = ?", args);
        db.close();
        return p > 0;
    }

    public boolean updateFechaSincro(String data, int idUsert){

        ContentValues valores = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        valores.put("fechaSincro", data);

        int p = db.update("login", valores, String.format("id = %1$s", idUsert), null);
        db.close();
        return p > 0;
    }

    public boolean insertLoginUser(ResponseUser data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {

            values.put("id", data.getId());
            values.put("cedula", data.getCedula());
            values.put("nombre", data.getNombre());
            values.put("apellido", data.getApellido());
            values.put("user", data.getUser());
            values.put("estado", data.getEstado());
            values.put("bd", data.getBd());
            values.put("id_distri", data.getId_distri());
            values.put("perfil", data.getPerfil());
            values.put("igv", data.getIgv());
            values.put("intervalo", data.getIntervalo());
            values.put("hora_inicial", data.getHora_inicial());
            values.put("hora_final", data.getHora_final());
            values.put("cantidad_envios", data.getCantidad_envios());
            values.put("fechaSincro", data.getFechaSincro());
            values.put("password", cryptWithMD5(data.getPassword()));

            db.insert("login", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }

        return true;
    }

    public static String cryptWithMD5(String pass){
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException ex) {
            //Logger.getLogger(CryptWithMD5.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public ResponseUser getUserLogin(String responseUser) {

        Cursor cursor;
        ResponseUser indicador = new ResponseUser();

        String[] args = new String[] {responseUser};

        String sql = "SELECT * FROM login WHERE user = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, args);

        if (cursor.moveToFirst()) {
            indicador.setId(cursor.getInt(0));
            indicador.setCedula(cursor.getInt(1));
            indicador.setNombre(cursor.getString(2));
            indicador.setApellido(cursor.getString(3));
            indicador.setUser(cursor.getString(4));
            indicador.setEstado(cursor.getInt(5));
            indicador.setBd(cursor.getString(6));
            indicador.setId_distri(cursor.getString(7));
            indicador.setPerfil(cursor.getInt(8));
            indicador.setIgv(cursor.getInt(9));
            indicador.setIntervalo(cursor.getInt(10));
            indicador.setHora_inicial(cursor.getString(11));
            indicador.setHora_final(cursor.getString(12));
            indicador.setCantidad_envios(cursor.getInt(13));
            indicador.setFechaSincro(cursor.getString(14));
            indicador.setPassword(cursor.getString(15));
        }

        return indicador;
    }

    public boolean validateLoginUser(String user, String password ) {
        Cursor cursor;
        boolean indicador = false;
        String[] args = new String[] {user, cryptWithMD5(password)};
        String sql = "SELECT * FROM login WHERE user =? AND password =?";
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, args);
        if (cursor.moveToFirst()) {
            indicador = true;
        }
        return indicador;
    }

    public boolean validateLoginUser(String user) {
        Cursor cursor;
        boolean indicador = false;

        String[] args = new String[] {user};

        String sql = "SELECT * FROM login WHERE user = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, args);
        if (cursor.moveToFirst()) {
            indicador = true;
        }

        return indicador;

    }

    public boolean insertIntro(String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put("idintro", data);
            db.insert("intro", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }

        return true;

    }

    public String insertCarritoPedidoCombos(Referencia data) {

        String resultado = "";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (validarProducto(data.getId())) {

            values.put("cantidad_pedida", data.getCantidadPedida());

            int p = db.update("carrito_pedido", values, String.format("id_producto = %1$s", data.getId()), null);
            db.close();

            if (p > 0)
                resultado = "update";
            else
                resultado = "no update";

            return resultado;

        } else {

            try {
                values.put("id_producto", data.getId());
                values.put("pn_pro", data.getPn());
                values.put("stock", data.getStock());
                values.put("producto", data.getProducto());
                values.put("dias_inve", data.getDias_inve());
                values.put("ped_sugerido", data.getPed_sugerido());
                values.put("cantidad_pedida", data.getCantidadPedida());
                values.put("id_usuario", data.getId_usuario());
                values.put("id_punto", data.getId_punto());
                values.put("tipo_producto", data.getTipo_producto());
                values.put("producto_img", data.getUrl_imagen());
                values.put("precio_referencia", data.getPrecio_referencia());
                values.put("precio_publico", data.getPrecio_publico());
                values.put("referencia", data.getReferencia());

                db.insert("carrito_pedido", null, values);

            } catch (SQLiteConstraintException e) {
                Log.d("data", "failure to insert word,", e);
                return resultado = "no inserto";
            } finally {
                db.close();
            }
        }

        return resultado = "inserto";
    }

    public int countReferenceProduct(int idUsuario, int idpos, int idreferen) {

        Cursor cursor;
        int indicador = 0;

        String sql = "SELECT SUM(cantidad_pedida) FROM carrito_pedido WHERE referencia = "+idreferen+" AND  id_usuario = "+idUsuario+ " AND id_punto = "+idpos+" ";
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = cursor.getInt(0);
        }
        return indicador;

    }

    public int countSimcardProduct(int idUsuario, int idpos, int idProduct) {

        Cursor cursor;
        int indicador = 0;

        String sql = "SELECT SUM(cantidad_pedida) FROM carrito_pedido WHERE id_producto = "+idProduct+" AND  id_usuario = "+idUsuario+ " AND id_punto = "+idpos+" ";
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = cursor.getInt(0);
        }
        return indicador;

    }

    public String insertCarritoPedido(ReferenciasSims data) {

        String resultado = "";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (validarProducto(data.getId())) {

            values.put("cantidad_pedida", data.getCantidadPedida());

            int p = db.update("carrito_pedido", values, String.format("id_producto = %1$s", data.getId()), null);
            db.close();

            if (p > 0)
                resultado = "update";
            else
                resultado = "no update";

            return resultado;

        } else {

            try {
                values.put("id_producto", data.getId());
                values.put("pn_pro", data.getPn());
                values.put("stock", data.getStock());
                values.put("producto", data.getProducto());
                values.put("dias_inve", data.getDias_inve());
                values.put("ped_sugerido", data.getPed_sugerido());
                values.put("cantidad_pedida", data.getCantidadPedida());
                values.put("id_usuario", data.getId_usuario());
                values.put("id_punto", data.getId_punto());
                values.put("tipo_producto", data.getTipo_producto());
                values.put("precio_referencia", data.getPrecio_referencia());
                values.put("precio_publico", data.getPrecio_publico());

                db.insert("carrito_pedido", null, values);

            } catch (SQLiteConstraintException e) {
                Log.d("data", "failure to insert word,", e);
                return resultado = "no inserto";
            } finally {
                db.close();
            }
        }

        return resultado = "inserto";
    }

    public boolean validarProducto(int id_producto) {

        Cursor cursor;
        boolean indicador = false;
        String sql = "SELECT id_producto FROM carrito_pedido WHERE id_producto = " + id_producto + " LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = true;
        }
        return indicador;

    }

    public boolean deleteCarritoProducto(int id, int id_pos, int id_usuario) {

        SQLiteDatabase db = this.getWritableDatabase();
        int a = db.delete("carrito_pedido", "id_carrito = ? AND id_punto = ? AND id_usuario = ?", new String[]{String.valueOf(id), String.valueOf(id_pos), String.valueOf(id_usuario)});

        db.close();
        return a > 0;

    }

    public boolean deleteAll(int id_pos, int id_usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        int a = db.delete("carrito_pedido", "id_punto = ? AND id_usuario = ?", new String[]{String.valueOf(id_pos), String.valueOf(id_usuario)});

        db.close();
        return a > 0;
    }

    public boolean insertTimeServices(TimeService timeService) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {

            values.put("idUser", timeService.getIdUser());
            values.put("traking", timeService.getTraking());
            values.put("timeservice", timeService.getTimeservice());
            values.put("idDistri", timeService.getIdDistri());
            values.put("dataBase", timeService.getDataBase());
            values.put("fechainicial", timeService.getFechainicial());
            values.put("fechafinal", timeService.getFechaFinal());

            db.insert("time_services", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }

        return true;

    }

    public boolean deleteAllServiTime() {
        SQLiteDatabase db = this.getWritableDatabase();
        int a = db.delete("tracing", null, null);

        db.close();
        return a > 0;
    }

    public TimeService getTimeService () {

        Cursor cursor;
        TimeService indicador = null;

        String sql = "SELECT idUser, traking, timeservice, idDistri, dataBase, fechainicial, fechafinal FROM time_services";
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            indicador = new TimeService();
            indicador.setIdUser(cursor.getInt(0));
            indicador.setTraking(cursor.getInt(1));
            indicador.setTimeservice(cursor.getInt(2));
            indicador.setIdDistri(cursor.getString(3));
            indicador.setDataBase(cursor.getString(4));
            indicador.setFechainicial(cursor.getString(5));
            indicador.setFechaFinal(cursor.getString(6));

        }

        return indicador;
    }

    public boolean insertTracing(Tracing tracing) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put("idUser", tracing.getIdUser());
            values.put("imei", tracing.getImei());
            values.put("dateTime", tracing.getDateTime());
            values.put("batteryLavel", tracing.getBatteryLavel());
            values.put("temperatura", tracing.getTemperatura());
            values.put("latitud", tracing.getLatitud());
            values.put("longitud", tracing.getLongitud());
            values.put("idDistri", tracing.getIdDistri());
            values.put("dataBase", tracing.getDataBase());

            db.insert("tracing", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public List<Tracing> getTracingService () {

        List<Tracing> tracingArrayList = new ArrayList<>();

        String sql = "SELECT idUser, imei, dateTime, batteryLavel, temperatura, latitud, longitud, idDistri, dataBase FROM tracing ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        Tracing tracing;

        if (cursor.moveToFirst()) {
            do {
                tracing = new Tracing();
                tracing.setIdUser(cursor.getInt(0));
                tracing.setImei(cursor.getString(1));
                tracing.setDateTime(cursor.getString(2));
                tracing.setBatteryLavel(cursor.getInt(3));
                tracing.setTemperatura(cursor.getInt(4));
                tracing.setLatitud(cursor.getDouble(5));
                tracing.setLongitud(cursor.getDouble(6));
                tracing.setIdDistri(cursor.getString(7));
                tracing.setDataBase(cursor.getString(8));

                tracingArrayList.add(tracing);

            } while (cursor.moveToNext());
        }

        return tracingArrayList;

    }

    public List<ReferenciasSims> getCarrito(int id_pos, int id_usuario) {

        List<ReferenciasSims> referenciasSimsList = new ArrayList<>();

        String sql = "SELECT id_carrito, id_producto, pn_pro, stock, producto, dias_inve, ped_sugerido, cantidad_pedida, id_usuario, id_punto, latitud, longitud, tipo_producto, producto_img, precio_referencia, precio_publico FROM carrito_pedido WHERE id_punto = " + id_pos + " AND id_usuario = " + id_usuario + " ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        ReferenciasSims referenciasSims;

        if (cursor.moveToFirst()) {
            do {
                referenciasSims = new ReferenciasSims();
                referenciasSims.setId_auto_carrito(cursor.getInt(0));
                referenciasSims.setId(cursor.getInt(1));
                referenciasSims.setPn(cursor.getString(2));
                referenciasSims.setStock(cursor.getInt(3));
                referenciasSims.setProducto(cursor.getString(4));
                referenciasSims.setDias_inve(cursor.getDouble(5));
                referenciasSims.setPed_sugerido(cursor.getString(6));
                referenciasSims.setCantidadPedida(cursor.getInt(7));
                referenciasSims.setId_usuario(cursor.getInt(8));
                referenciasSims.setId_punto(cursor.getInt(9));
                referenciasSims.setTipo_producto(cursor.getInt(12));
                referenciasSims.setUrl_imagen(cursor.getString(13));
                referenciasSims.setPrecio_referencia(cursor.getDouble(14));
                referenciasSims.setPrecio_publico(cursor.getDouble(15));

                referenciasSimsList.add(referenciasSims);

            } while (cursor.moveToNext());
        }

        return referenciasSimsList;

    }

    public boolean getIntro() {
        Cursor cursor;
        boolean indicador = false;
        String sql = "SELECT * FROM intro LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = true;
        }
        return indicador;
    }

    public int countReferenciaProducto(int id, int id_punto, int id_usuario) {
        Cursor cursor;
        int indicador = 0;

        String sql = "SELECT SUM(cantidad_pedida) FROM carrito_pedido WHERE id_producto = "+id+" AND  id_usuario = "+id_usuario+ " AND id_punto = "+id_punto+" ";
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = cursor.getInt(0);
        }
        return indicador;
    }

}

