package android.dcsdealerperu.com.dealerperu.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.dcsdealerperu.com.dealerperu.Entry.Referencia;
import android.dcsdealerperu.com.dealerperu.Entry.ReferenciasSims;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseUser;
import android.dcsdealerperu.com.dealerperu.Entry.TimeService;
import android.dcsdealerperu.com.dealerperu.Entry.Tracing;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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



        db.execSQL(sqlIntro);
        db.execSQL(sqlCarrito);
        db.execSQL(sqlTimeServices);
        db.execSQL(sqlTraking);
        db.execSQL(sqlLoginUsuario);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS intro");
        db.execSQL("DROP TABLE IF EXISTS carrito_pedido");
        db.execSQL("DROP TABLE IF EXISTS time_services");
        db.execSQL("DROP TABLE IF EXISTS tracing");
        db.execSQL("DROP TABLE IF EXISTS login");
        this.onCreate(db);

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
            values.put("password", data.getPassword());

            db.insert("login", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }

        return true;

    }

    public ResponseUser getUserLogin(String responseUser) {

        Cursor cursor;
        ResponseUser indicador = new ResponseUser();

        String sql = "SELECT * FROM login WHERE user = "+responseUser;
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, null);

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
        String sql = "SELECT * FROM login WHERE user = "+ user +" password = "+password;
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = true;
        }

        return indicador;

    }

    public boolean validateLoginUser(int user) {
        Cursor cursor;
        boolean indicador = false;
        String sql = "SELECT * FROM login WHERE user ="+ user;
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, null);
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

