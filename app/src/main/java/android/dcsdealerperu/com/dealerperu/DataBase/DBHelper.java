package android.dcsdealerperu.com.dealerperu.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.dcsdealerperu.com.dealerperu.Entry.Referencia;
import android.dcsdealerperu.com.dealerperu.Entry.ReferenciasSims;
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

        String sqlCarrito = "CREATE TABLE carrito_pedido (id_carrito integer primary key AUTOINCREMENT, id_producto INT, pn_pro TEXT, stock INT, producto TEXT, dias_inve REAL, ped_sugerido TEXT, " +
                " cantidad_pedida INT, id_usuario TEXT, id_punto INT, latitud TEXT, longitud TEXT, tipo_producto INT, producto_img TEXT)";

        db.execSQL(sqlIntro);
        db.execSQL(sqlCarrito);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS intro");
        db.execSQL("DROP TABLE IF EXISTS carrito_pedido");
        this.onCreate(db);

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

    public List<ReferenciasSims> getCarrito(int id_pos, int id_usuario) {

        List<ReferenciasSims> referenciasSimsList = new ArrayList<>();

        String sql = "SELECT id_carrito, id_producto, pn_pro, stock, producto, dias_inve, ped_sugerido, cantidad_pedida, id_usuario, id_punto, latitud, longitud, tipo_producto, producto_img FROM carrito_pedido WHERE id_punto = " + id_pos + " AND id_usuario = " + id_usuario + " ";
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

}

