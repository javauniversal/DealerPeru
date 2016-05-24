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
import android.dcsdealerperu.com.dealerperu.Entry.DetallePedido;
import android.dcsdealerperu.com.dealerperu.Entry.Distrito;
import android.dcsdealerperu.com.dealerperu.Entry.NoVisita;
import android.dcsdealerperu.com.dealerperu.Entry.Nomenclatura;
import android.dcsdealerperu.com.dealerperu.Entry.PedidosEntrega;
import android.dcsdealerperu.com.dealerperu.Entry.PedidosEntregaSincronizar;
import android.dcsdealerperu.com.dealerperu.Entry.Referencia;
import android.dcsdealerperu.com.dealerperu.Entry.ReferenciasCombos;
import android.dcsdealerperu.com.dealerperu.Entry.ReferenciasSims;
import android.dcsdealerperu.com.dealerperu.Entry.RequestGuardarEditarPunto;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseCreatePunt;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseEntregarPedido;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseHome;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.dcsdealerperu.com.dealerperu.Entry.ResponseUser;
import android.dcsdealerperu.com.dealerperu.Entry.Sincronizar;
import android.dcsdealerperu.com.dealerperu.Entry.SincronizarPedidos;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
                " tipo_documento INT, tipo_interior INT, tipo_urbanizacion INT, tipo_via INT, tipo_vivienda INT, vende_recargas INT, zona INT, latitud REAL, longitud REAL, estado_visita INT) ";

        String sqlEstadoComercial = "CREATE TABLE estado_comercial (id INT, descripcion TEXT, estado_accion INT)";

        String sqlMunicipios = "CREATE TABLE municipios (id_muni INT, descripcion TEXT, departamento INT, estado_accion INT)";

        String sqlNomenclaturas = "CREATE TABLE nomenclaturas (id INT, nombre INT, letras TEXT, tipo_nom INT, estado_accion INT)";

        String sqlSubcategoriasPuntos = "CREATE TABLE subcategorias_puntos (id INT, descripcion TEXT, id_categoria INT, estado_accion INT)";

        String sqlDistritos = "CREATE TABLE distritos (id INT, descripcion TEXT, id_muni INT, id_depto INT, estado_accion INT)";

        String sqlRefesSim = "CREATE TABLE referencia_simcard (id INT, pn TEXT, stock INT, producto TEXT, dias_inve REAL, ped_sugerido TEXT, precio_referencia REAL, precio_publico REAL, quiebre INT, estado_accion INT )";

        String sqlRefesCombo = "CREATE TABLE referencia_combo (id INT, descripcion TEXT, precioventa REAL, speech TEXT, pantalla TEXT, cam_frontal TEXT, cam_tras TEXT, flash TEXT, banda TEXT, " +
                " memoria TEXT, expandible TEXT, bateria TEXT, bluetooth TEXT, tactil TEXT, tec_fisico TEXT, carrito_compras TEXT, correo TEXT, enrutador TEXT, radio TEXT, wifi TEXT, gps TEXT, so TEXT, " +
                " web TEXT, precio_referencia REAL, precio_publico REAL, img TEXT, estado_accion INT  )";

        String sqlListaPrecio = "CREATE TABLE lista_precios (id_referencia INT, idpos INT, valor_referencia REAl, valor_directo REAL, tipo_pro INT, estado_accion INT )";

        String sqlReferencia = "CREATE TABLE detalle_combo (id INT, pn TEXT, producto TEXT, descripcion TEXT, precio_referencia REAL, precio_publico REAL, dias_inve REAL, stock INT, ped_sugerido REAL, img TEXT, estado_accion INT, id_padre INT )";

        String sqlNoVisita = "CREATE TABLE no_visita (idpos INT, motivo INT, observacion TEXT, latitud REAL, longitud REAL, iduser INT, iddis INT, db TEXT, perfil INT, fecha TEXT, hora TEXT)";

        String sqlCabezaPedido = "CREATE TABLE cabeza_pedido (id integer primary key AUTOINCREMENT,  iduser INT, iddistri TEXT, db TEXT, idpos INT, latitud REAL, longitud REAL, fecha TEXT, hora TEXT)";

        String sqlDetallePedido = "CREATE TABLE detalle_pedido (id integer primary key AUTOINCREMENT, idCabeza INT, id_producto INT, cantidad_pedida INT, tipo_producto INT, referencia INT) ";

        String sqlPedidosEntrega= "CREATE TABLE pedido_entrega (idpos INT, razon TEXT, nombre_cli TEXT, barrio TEXT, cel TEXT, estado_comercial TEXT, email TEXT, direccion TEXT, departamento TEXT, munucipio TEXT, " +
                " distrito TEXT, id_circuito TEXT, circuito TEXT, idruta TEXT, ruta TEXT, tel TEXT, detalle TEXT, tipo_visita INT, rutero INT, latitud REAL, longitud REAL, fecha_ult TEXT, hora_ult TEXT, persona_ultima TEXT) ";

        String sqlDetallepedidoEntregar = "CREATE TABLE pedido_repartidor (idpos INT, razon_social TEXT, territorio TEXT, zona TEXT, direccion TEXT) ";

        String sqlDetallepedidoEntregarROW = "CREATE TABLE pedidos_grupo (nroPedido INT, cant_pedido INT, cant_pedido_p INT, total_pedido_p INT, fecha_pedido TEXT, " +
                "  hora_pedido TEXT, estado TEXT, fecha_entrega TEXT, igv REAL, sub_total REAL, nombre_usu TEXT, idpos INT, total_impueto_igv REAL) ";

        String sqlDetallepedidoEntregarNUMERO = "CREATE TABLE deta_pedido (nroPedido INT, nombre_sku TEXT, id_sku INT, cant_pedido INT, total_pedido RELA, cant_pedido_p INT, total_pedido_p REAL, tipo_pro TEXT) ";

        String sqlEntregaPedido = "CREATE TABLE entrega_pedido (id integer primary key AUTOINCREMENT, latitud REAL, longitud REAL, iduser INT, iddis INT, db TEXT, idpos INT, obs TEXT, idpedido INT, indicador INT, fecha_pedido fecha TEXT, hora_pedido TEXT) ";


        db.execSQL(sqlDetallepedidoEntregar);
        db.execSQL(sqlDetallepedidoEntregarROW);
        db.execSQL(sqlDetallepedidoEntregarNUMERO);

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

        db.execSQL(sqlRefesSim);
        db.execSQL(sqlListaPrecio);
        db.execSQL(sqlRefesCombo);
        db.execSQL(sqlReferencia);
        db.execSQL(sqlNoVisita);
        db.execSQL(sqlCabezaPedido);
        db.execSQL(sqlDetallePedido);
        db.execSQL(sqlPedidosEntrega);
        db.execSQL(sqlEntregaPedido);

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
        db.execSQL("DROP TABLE IF EXISTS no_visita");
        db.execSQL("DROP TABLE IF EXISTS cabeza_pedido");
        db.execSQL("DROP TABLE IF EXISTS detalle_pedido");
        db.execSQL("DROP TABLE IF EXISTS pedido_entrega");
        db.execSQL("DROP TABLE IF EXISTS pedido_repartidor");
        db.execSQL("DROP TABLE IF EXISTS pedidos_grupo");
        db.execSQL("DROP TABLE IF EXISTS deta_pedido");
        db.execSQL("DROP TABLE IF EXISTS entrega_pedido");
        this.onCreate(db);

    }

    public boolean validarPedidosDuplicados(int idPedido, int idPos) {
        Cursor cursor;
        boolean indicador = false;

        String[] args = new String[] {String.valueOf(idPos), String.valueOf(idPedido)};

        String sql = "SELECT * FROM entrega_pedido WHERE idpos = ? AND idpedido = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, args);
        if (cursor.moveToFirst()) {
            indicador = true;
        }

        return indicador;

    }

    public boolean deleteAllPedidosEntrega(int id_pos, int id_pedidos) {
        SQLiteDatabase db = this.getWritableDatabase();
        int a = db.delete("entrega_pedido", "idpos = ? AND idpedido = ?", new String[]{String.valueOf(id_pos), String.valueOf(id_pedidos)});

        db.close();
        return a > 0;

    }

    public boolean insetEntregaPedido(double latitud, double longitud, int iduser, int iddis, String db_web, int idpos, String obs,  int idpedido, String indicador) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {

            values.put("latitud", latitud);
            values.put("longitud", longitud);
            values.put("iduser", iduser);
            values.put("iddis", iddis);
            values.put("db", db_web);
            values.put("idpos", idpos);
            values.put("obs", obs);
            values.put("idpedido", idpedido);
            values.put("indicador", indicador);
            values.put("fecha_pedido", getDatePhoneFecha());
            values.put("hora_pedido", getDatePhoneHora());

            db.insert("entrega_pedido", null, values);

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public List<PedidosEntregaSincronizar> sincronizarsEntregaPedido() {

        List<PedidosEntregaSincronizar> pedidosEntregaSincronizars = new ArrayList<>();
        String sql = "SELECT * FROM entrega_pedido ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        PedidosEntregaSincronizar pedidosEntregaSincronizar;
        if (cursor.moveToFirst()) {
            do {

                pedidosEntregaSincronizar = new PedidosEntregaSincronizar();
                pedidosEntregaSincronizar.setLatitud(cursor.getDouble(1));
                pedidosEntregaSincronizar.setLongitud(cursor.getDouble(2));
                pedidosEntregaSincronizar.setIduser(cursor.getInt(3));
                pedidosEntregaSincronizar.setIddis(cursor.getInt(4));
                pedidosEntregaSincronizar.setDb(cursor.getString(5));
                pedidosEntregaSincronizar.setIdpos(cursor.getInt(6));
                pedidosEntregaSincronizar.setObs(cursor.getString(7));
                pedidosEntregaSincronizar.setIdpedido(cursor.getInt(8));
                pedidosEntregaSincronizar.setIndicador(cursor.getString(9));
                pedidosEntregaSincronizar.setFecha_pedido(cursor.getString(10));
                pedidosEntregaSincronizar.setHora_pedido(cursor.getString(11));

                pedidosEntregaSincronizars.add(pedidosEntregaSincronizar);
            } while(cursor.moveToNext());
        }
        return pedidosEntregaSincronizars;
    }

    public boolean insertEntregaPedidos(List<ResponseHome> data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();
        ContentValues values2 = new ContentValues();
        ContentValues values3 = new ContentValues();

        try {

            for (int i = 0; i < data.size(); i++) {

                values.put("idpos", data.get(i).getIdpos());
                values.put("razon", data.get(i).getRazon());
                values.put("nombre_cli", data.get(i).getNombre_cli());
                values.put("barrio", data.get(i).getBarrio());
                values.put("cel", data.get(i).getCel());
                values.put("estado_comercial", data.get(i).getEstado_comercial());
                values.put("email", data.get(i).getEmail());
                values.put("direccion", data.get(i).getDireccion());
                values.put("departamento", data.get(i).getDepartamento());
                values.put("munucipio", data.get(i).getMunucipio());
                values.put("distrito", data.get(i).getDistrito());
                values.put("id_circuito", data.get(i).getId_circuito());
                values.put("circuito", data.get(i).getCircuito());
                values.put("idruta", data.get(i).getIdruta());
                values.put("ruta", data.get(i).getRuta());
                values.put("tel", data.get(i).getTel());
                values.put("detalle", data.get(i).getDetalle());
                values.put("tipo_visita", data.get(i).getTipo_visita());
                values.put("rutero", data.get(i).getRutero());
                values.put("latitud", data.get(i).getLatitud());
                values.put("longitud", data.get(i).getLongitud());
                values.put("hora_ult", data.get(i).getHora_ult());
                values.put("persona_ultima", data.get(i).getPersona_ultima());

                db.insert("pedido_entrega", null, values);

                values1.put("idpos", data.get(i).getList().getIdpos());
                values1.put("razon_social", data.get(i).getList().getRazon_social());
                values1.put("territorio",  data.get(i).getList().getTerritorio());
                values1.put("zona", data.get(i).getList().getZona());
                values1.put("direccion", data.get(i).getList().getDireccion());

                db.insert("pedido_repartidor", null, values1);

                for (int g = 0; g < data.get(i).getList().getPedidosEntregaList().size(); g++) {

                    values2.put("nroPedido", data.get(i).getList().getPedidosEntregaList().get(g).getNroPedido());
                    values2.put("cant_pedido", data.get(i).getList().getPedidosEntregaList().get(g).getCant_pedido());
                    values2.put("cant_pedido_p", data.get(i).getList().getPedidosEntregaList().get(g).getCant_pedido_p());
                    values2.put("total_pedido_p", data.get(i).getList().getPedidosEntregaList().get(g).getTotal_pedido_p());
                    values2.put("fecha_pedido", data.get(i).getList().getPedidosEntregaList().get(g).getFecha_pedido());
                    values2.put("hora_pedido", data.get(i).getList().getPedidosEntregaList().get(g).getHora_pedido());
                    values2.put("estado", data.get(i).getList().getPedidosEntregaList().get(g).getEstado());
                    values2.put("fecha_entrega", data.get(i).getList().getPedidosEntregaList().get(g).getFecha_entrega());
                    values2.put("igv", data.get(i).getList().getPedidosEntregaList().get(g).getIgv());
                    values2.put("sub_total", data.get(i).getList().getPedidosEntregaList().get(g).getSub_total());
                    values2.put("nombre_usu", data.get(i).getList().getPedidosEntregaList().get(g).getNombre_usu());
                    values2.put("idpos", data.get(i).getList().getPedidosEntregaList().get(g).getIdpos());
                    values2.put("total_impueto_igv", data.get(i).getList().getPedidosEntregaList().get(g).getTotal_impueto_igv());

                    db.insert("pedidos_grupo", null, values2);

                    for (int a = 0; a < data.get(i).getList().getPedidosEntregaList().get(g).getDetallePedidoList().size(); a++) {

                        values3.put("nroPedido", data.get(i).getList().getPedidosEntregaList().get(g).getDetallePedidoList().get(a).getNroPedido());
                        values3.put("nombre_sku", data.get(i).getList().getPedidosEntregaList().get(g).getDetallePedidoList().get(a).getNombre_sku());
                        values3.put("id_sku", data.get(i).getList().getPedidosEntregaList().get(g).getDetallePedidoList().get(a).getId_sku());
                        values3.put("cant_pedido", data.get(i).getList().getPedidosEntregaList().get(g).getDetallePedidoList().get(a).getCant_pedido());
                        values3.put("total_pedido", data.get(i).getList().getPedidosEntregaList().get(g).getDetallePedidoList().get(a).getTotal_pedido());
                        values3.put("cant_pedido_p", data.get(i).getList().getPedidosEntregaList().get(g).getDetallePedidoList().get(a).getCant_pedido_p());
                        values3.put("total_pedido_p", data.get(i).getList().getPedidosEntregaList().get(g).getDetallePedidoList().get(a).getTotal_pedido_p());
                        values3.put("tipo_pro", data.get(i).getList().getPedidosEntregaList().get(g).getDetallePedidoList().get(a).getTipo_pro());

                        db.insert("deta_pedido", null, values3);
                    }
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

    public List<ResponseHome> getVisitasRutero() {

        List<ResponseHome> responseHomeList = new ArrayList<>();

        String sql = "SELECT * FROM pedido_entrega ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        ResponseHome responseHome;

        if (cursor.moveToFirst()) {
            do {
                responseHome = new ResponseHome();
                responseHome.setIdpos(cursor.getInt(0));
                responseHome.setRazon(cursor.getString(1));
                responseHome.setNombre_cli(cursor.getString(2));
                responseHome.setBarrio(cursor.getString(3));
                responseHome.setCel(cursor.getString(4));
                responseHome.setEstado_comercial(cursor.getString(5));
                responseHome.setEmail(cursor.getString(6));
                responseHome.setDireccion(cursor.getString(7));
                responseHome.setDepartamento(cursor.getString(8));
                responseHome.setMunucipio(cursor.getString(9));
                responseHome.setDistrito(cursor.getString(10));
                responseHome.setId_circuito(cursor.getString(11));
                responseHome.setCircuito(cursor.getString(12));
                responseHome.setIdruta(cursor.getString(13));
                responseHome.setRuta(cursor.getString(14));
                responseHome.setTel(cursor.getString(15));
                responseHome.setDetalle(cursor.getString(16));
                responseHome.setTipo_visita(cursor.getInt(17));
                responseHome.setRutero(cursor.getInt(18));
                responseHome.setLatitud(cursor.getDouble(19));
                responseHome.setLongitud(cursor.getDouble(20));
                responseHome.setFecha_ult(cursor.getString(21));
                responseHome.setHora_ult(cursor.getString(22));
                responseHome.setPersona_ultima(cursor.getString(23));

                String sqlpedido_repartidor = "SELECT idpos, razon_social, territorio, zona, direccion  FROM pedido_repartidor WHERE idpos = ? ";
                Cursor cursor_repartidor = db.rawQuery(sqlpedido_repartidor, new String[]{String.valueOf(cursor.getInt(0))});

                ResponseEntregarPedido responseEntregarPedido;
                if (cursor_repartidor.moveToFirst()) {
                    do {
                        responseEntregarPedido = new ResponseEntregarPedido();
                        responseEntregarPedido.setIdpos(cursor_repartidor.getInt(0));
                        responseEntregarPedido.setRazon_social(cursor_repartidor.getString(1));
                        responseEntregarPedido.setTerritorio(cursor_repartidor.getString(2));
                        responseEntregarPedido.setZona(cursor_repartidor.getString(3));
                        responseEntregarPedido.setDireccion(cursor_repartidor.getString(4));

                    } while(cursor_repartidor.moveToNext());

                    String sqlpedido_grupo = "SELECT nroPedido, cant_pedido, cant_pedido_p, total_pedido_p, fecha_pedido, hora_pedido, estado, fecha_entrega, igv, sub_total, nombre_usu, idpos, total_impueto_igv FROM pedidos_grupo WHERE idpos = ? ";
                    Cursor cursor_grupo = db.rawQuery(sqlpedido_grupo, new String[]{String.valueOf(cursor.getInt(0))});
                    List<PedidosEntrega> pedidosEntregaList = new ArrayList<>();
                    PedidosEntrega pedidosEntrega;
                    if (cursor_grupo.moveToFirst()) {
                        do {
                            pedidosEntrega = new PedidosEntrega();
                            pedidosEntrega.setNroPedido(cursor_grupo.getInt(0));
                            pedidosEntrega.setCant_pedido(cursor_grupo.getInt(1));
                            pedidosEntrega.setCant_pedido_p(cursor_grupo.getInt(2));
                            pedidosEntrega.setTotal_pedido_p(cursor_grupo.getDouble(3));
                            pedidosEntrega.setFecha_pedido(cursor_grupo.getString(4));
                            pedidosEntrega.setHora_pedido(cursor_grupo.getString(5));
                            pedidosEntrega.setEstado(cursor_grupo.getString(6));
                            pedidosEntrega.setFecha_entrega(cursor_grupo.getString(7));
                            pedidosEntrega.setIgv(cursor_grupo.getDouble(8));
                            pedidosEntrega.setSub_total(cursor_grupo.getDouble(9));
                            pedidosEntrega.setNombre_usu(cursor_grupo.getString(10));
                            pedidosEntrega.setIdpos(cursor_grupo.getInt(11));
                            pedidosEntrega.setTotal_impueto_igv(cursor_grupo.getDouble(12));

                            String sqldeta_pedido = "SELECT nroPedido, nombre_sku, id_sku, cant_pedido, total_pedido, cant_pedido_p, total_pedido_p, tipo_pro FROM deta_pedido WHERE nroPedido = ? ";
                            Cursor cursor_deta_pedido = db.rawQuery(sqldeta_pedido, new String[]{String.valueOf(cursor_grupo.getInt(0))});
                            List<DetallePedido> detallePedidoList = new ArrayList<>();
                            DetallePedido detallePedido;
                            if (cursor_deta_pedido.moveToFirst()) {
                                do {
                                    detallePedido = new DetallePedido();

                                    detallePedido.setNroPedido(cursor_deta_pedido.getInt(0));
                                    detallePedido.setNombre_sku(cursor_deta_pedido.getString(1));
                                    detallePedido.setCant_pedido(cursor_deta_pedido.getInt(2));
                                    detallePedido.setTotal_pedido(cursor_deta_pedido.getDouble(3));
                                    detallePedido.setCant_pedido_p(cursor_deta_pedido.getInt(4));
                                    detallePedido.setTotal_pedido_p(cursor_deta_pedido.getDouble(5));
                                    detallePedido.setTipo_pro(cursor_deta_pedido.getString(6));

                                    detallePedidoList.add(detallePedido);

                                } while(cursor_deta_pedido.moveToNext());

                                pedidosEntrega.setDetallePedidoList(detallePedidoList);
                            }

                            pedidosEntregaList.add(pedidosEntrega);
                        } while(cursor_grupo.moveToNext());
                    }

                    responseEntregarPedido.setPedidosEntregaList(pedidosEntregaList);

                    responseHome.setList(responseEntregarPedido);
                }

                responseHomeList.add(responseHome);

            } while(cursor.moveToNext());

        }

        return responseHomeList;

    }

    public List<ResponseHome> getBuscarPuntoLocal(String nit_punto, String nombre_punto, String responsable, int depto, int ciudad, int distrito, int circuito, int ruta, int est_comercial) {

        List<ResponseHome> responseHomeList = new ArrayList<>();

        String condicion = "";

        String sql = "SELECT mpt.idpos, mpt.nombre_punto AS razon, mpt.nombre_cliente AS nombre_cli, mpt.texto_direccion direccion FROM punto AS mpt " +
                "   WHERE mpt.idpos >= 0 %1$s ORDER BY mpt.idpos" ;

        if(!nit_punto.isEmpty()) {
            condicion += " AND mpt.cedula = '"+nit_punto+"' ";
        }
        if(!responsable.isEmpty()) {
            condicion += " AND mpt.nombre_cliente = '"+responsable+"' ";
        }

        if(!nombre_punto.isEmpty()) {
            condicion += " AND mpt.nombre_punto LIKE '%"+nombre_punto+"%' ";
        }

        if(depto != 0) {
            condicion += " AND mpt.depto = "+depto;
        }

        if(ciudad != 0) {
            condicion += " AND mpt.ciudad = "+ciudad;
        }

        if(distrito != 0) {
            condicion += " AND mpt.distrito = "+distrito;
        }

        if(circuito != 0) {
            condicion += " AND mpt.territorio_punto = "+circuito;
        }

        if(ruta != 0) {
            condicion += " AND mpt.zona = "+ruta;
        }

        if(est_comercial != 0) {
            condicion += " AND mpt.estado_com = "+est_comercial;
        }

        String sqlFinal = String.format(sql, condicion);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlFinal, null);

        ResponseHome responseHome;

        if (cursor.moveToFirst()) {
            do {

                responseHome = new ResponseHome();

                responseHome.setIdpos(cursor.getInt(0));
                responseHome.setRazon(cursor.getString(1));
                responseHome.setDireccion(cursor.getString(3));

                responseHomeList.add(responseHome);

            } while (cursor.moveToNext());

        }
        return responseHomeList;
    }

    public int ultimoRegistro(String table){
        int _id = 0;
        String sql = "SELECT id FROM "+ table +" ORDER BY id DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                _id = Integer.parseInt(cursor.getString(0));
            } while(cursor.moveToNext());
        }
        return _id;
    }

    public boolean insertPedidoOffLine(List<ReferenciasSims> data, int iduser, String iddistri, String bd, int idpos, double latitud, double longitud) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues values2 = new ContentValues();
        try {

            values.put("iduser", iduser);
            values.put("iddistri", iddistri);
            values.put("db", bd);
            values.put("idpos", idpos);
            values.put("latitud", latitud);
            values.put("longitud", longitud);
            values.put("fecha", getDatePhoneFecha());
            values.put("hora", getDatePhoneHora());

            db.insert("cabeza_pedido", null, values);
            int id_auto = ultimoRegistro("cabeza_pedido");
            for (int i = 0; i < data.size(); i++) {

                values2.put("idCabeza", id_auto);
                values2.put("id_producto", data.get(i).getId());
                values2.put("cantidad_pedida", data.get(i).getCantidadPedida());
                values2.put("tipo_producto", data.get(i).getTipo_producto());
                values2.put("referencia", data.get(i).getId());

                db.insert("detalle_pedido", null, values2);
            }

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }

        return true;
    }

    private String getDatePhoneFecha() {

        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formatteDate = df.format(date);

        return formatteDate;

    }

    private String getDatePhoneHora() {

        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formatteDate = df.format(date);

        return formatteDate;

    }

    public boolean uptadeCabezaPedidoLocal(int idPosorigen, String idPosFinal){

        ContentValues valores = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        valores.put("idpos", idPosFinal);

        String[] args = new String[]{String.valueOf(idPosorigen)};
        int p = db.update("cabeza_pedido", valores, "idpos = ?", args);
        db.close();
        return p > 0;
    }

    public List<SincronizarPedidos> sincronizarPedido() {

        List<SincronizarPedidos> sincronizarPedidosArrayList = new ArrayList<>();

        String sql = "SELECT id, iduser, iddistri, db, idpos, latitud, longitud, fecha, hora FROM cabeza_pedido";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        SincronizarPedidos sincronizarPedidos;

        if (cursor.moveToFirst()) {
            do {

                sincronizarPedidos = new SincronizarPedidos();

                sincronizarPedidos.setAutoincrement(cursor.getInt(0));
                sincronizarPedidos.setIduser(cursor.getInt(1));
                sincronizarPedidos.setIddistri(cursor.getString(2));
                sincronizarPedidos.setBd(cursor.getString(3));
                sincronizarPedidos.setIdpos(cursor.getInt(4));
                sincronizarPedidos.setLatitud(cursor.getDouble(5));
                sincronizarPedidos.setLongitud(cursor.getDouble(6));
                sincronizarPedidos.setFecha_visita(cursor.getString(7));
                sincronizarPedidos.setHora_visita(cursor.getString(8));

                String sql_detalle = "SELECT idCabeza, id_producto, cantidad_pedida, tipo_producto, referencia FROM detalle_pedido WHERE idCabeza = ?";

                Cursor cursor_detall = db.rawQuery(sql_detalle, new String[] {String.valueOf(cursor.getInt(0))});
                List<ReferenciasSims> referenciasSimsList = new ArrayList<>();
                ReferenciasSims referenciasSims;

                if (cursor_detall.moveToFirst()) {
                    do {
                        referenciasSims = new ReferenciasSims();
                        referenciasSims.setId_auto_carrito(cursor_detall.getInt(0));
                        referenciasSims.setId(cursor_detall.getInt(1));

                        referenciasSims.setCantidadPedida(cursor_detall.getInt(2));
                        referenciasSims.setTipo_producto(cursor_detall.getInt(3));

                        referenciasSimsList.add(referenciasSims);
                    } while (cursor_detall.moveToNext());

                    sincronizarPedidos.setReferenciasSimsList(referenciasSimsList);
                }

                sincronizarPedidosArrayList.add(sincronizarPedidos);

            } while (cursor.moveToNext());

        }
        return sincronizarPedidosArrayList;
    }

    public List<NoVisita> sincronizarNoVisita() {

        List<NoVisita> noVisitaList = new ArrayList<>();
        String sql = "SELECT idpos, motivo, observacion, latitud, longitud, iduser, iddis, fecha, hora FROM no_visita ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        NoVisita noVisita;
        if (cursor.moveToFirst()) {
            do {
                noVisita = new NoVisita();
                noVisita.setIdpos(cursor.getInt(0));
                noVisita.setMotivo(cursor.getInt(1));
                noVisita.setObservacion(cursor.getString(2));
                noVisita.setLatitud(cursor.getDouble(3));
                noVisita.setLongitud(cursor.getDouble(4));
                noVisita.setIduser(cursor.getInt(5));
                noVisita.setIdids(cursor.getInt(6));
                noVisita.setFecha_visita(cursor.getString(7));
                noVisita.setHora_visita(cursor.getString(8));
                noVisitaList.add(noVisita);
            } while(cursor.moveToNext());
        }
        return noVisitaList;
    }

    public boolean insertNoVisita(NoVisita data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {

            values.put("idpos", data.getIdpos());
            values.put("motivo", data.getMotivo());
            values.put("observacion", data.getObservacion());
            values.put("latitud", data.getLatitud());
            values.put("longitud", data.getLongitud());
            values.put("iduser", data.getIduser());
            values.put("iddis", data.getIdids());
            values.put("db", data.getDb());
            values.put("perfil", data.getPerfil());
            values.put("fecha", getDatePhoneFecha());
            values.put("hora", getDatePhoneHora());

            db.insert("no_visita", null, values);

        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean insertLisPrecios(Sincronizar data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            for(int i = 0; i < data.getListaPrecios().size(); i++ ) {

                if (data.getListaPrecios().get(i).getEstado_accion() == 1) {
                    //Insertar.
                    values.put("id_referencia", data.getListaPrecios().get(i).getId_referencia());
                    values.put("idpos", data.getListaPrecios().get(i).getIdpos());
                    values.put("valor_referencia", data.getListaPrecios().get(i).getValor_referencia());
                    values.put("valor_directo", data.getListaPrecios().get(i).getValor_directo());
                    values.put("tipo_pro", data.getListaPrecios().get(i).getTipo_pro());
                    values.put("estado_accion", data.getListaPrecios().get(i).getEstado_accion());

                    db.insert("lista_precios", null, values);
                } else if (data.getListaPrecios().get(i).getEstado_accion() == 2) {
                    //Actualizar.
                    values.put("id_referencia", data.getListaPrecios().get(i).getId_referencia());
                    values.put("idpos", data.getListaPrecios().get(i).getIdpos());
                    values.put("valor_referencia", data.getListaPrecios().get(i).getValor_referencia());
                    values.put("valor_directo", data.getListaPrecios().get(i).getValor_directo());
                    values.put("tipo_pro", data.getListaPrecios().get(i).getTipo_pro());
                    values.put("estado_accion", data.getListaPrecios().get(i).getEstado_accion());


                    int p = db.update("lista_precios", values, String.format("id = %1$s", data.getListaPrecios().get(i).getId_referencia()), null);
                } else if (data.getListaPrecios().get(i).getEstado_accion() == 0) {
                    //Eliminar.
                    int a = db.delete("lista_precios", "id_referencia = ? ", new String[]{String.valueOf(data.getListaPrecios().get(i).getId_referencia())});
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

    public boolean insertReferenciaCombos(Sincronizar data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues values2 = new ContentValues();

        try {
            for(int i = 0; i < data.getReferenciasCombosList().size(); i++ ) {

                if (data.getReferenciasCombosList().get(i).getEstado_accion() == 1) {
                    //Insertar.

                    values.put("id", data.getReferenciasCombosList().get(i).getId());
                    values.put("descripcion", data.getReferenciasCombosList().get(i).getDescripcion());
                    values.put("precioventa", data.getReferenciasCombosList().get(i).getPrecioventa());
                    values.put("speech", data.getReferenciasCombosList().get(i).getSpeech());
                    values.put("pantalla", data.getReferenciasCombosList().get(i).getPantalla());
                    values.put("cam_frontal", data.getReferenciasCombosList().get(i).getCam_frontal());
                    values.put("cam_tras", data.getReferenciasCombosList().get(i).getCam_tras());
                    values.put("flash", data.getReferenciasCombosList().get(i).getFlash());
                    values.put("banda", data.getReferenciasCombosList().get(i).getBanda());
                    values.put("memoria", data.getReferenciasCombosList().get(i).getMemoria());
                    values.put("expandible", data.getReferenciasCombosList().get(i).getExpandible());
                    values.put("bateria", data.getReferenciasCombosList().get(i).getBateria());
                    values.put("bluetooth", data.getReferenciasCombosList().get(i).getBluetooth());
                    values.put("tactil", data.getReferenciasCombosList().get(i).getTactil());
                    values.put("tec_fisico", data.getReferenciasCombosList().get(i).getTec_fisico());
                    values.put("carrito_compras", data.getReferenciasCombosList().get(i).getCarrito_compras());
                    values.put("correo", data.getReferenciasCombosList().get(i).getCorreo());
                    values.put("enrutador", data.getReferenciasCombosList().get(i).getEnrutador());
                    values.put("radio", data.getReferenciasCombosList().get(i).getRadio());
                    values.put("wifi", data.getReferenciasCombosList().get(i).getWifi());
                    values.put("gps", data.getReferenciasCombosList().get(i).getGps());
                    values.put("so", data.getReferenciasCombosList().get(i).getSo());
                    values.put("web", data.getReferenciasCombosList().get(i).getWeb());
                    values.put("precio_referencia", data.getReferenciasCombosList().get(i).getPrecio_referencia());
                    values.put("precio_publico", data.getReferenciasCombosList().get(i).getPrecio_publico());
                    values.put("img", data.getReferenciasCombosList().get(i).getImg());
                    values.put("estado_accion", data.getReferenciasCombosList().get(i).getEstado_accion());

                    db.insert("referencia_combo", null, values);

                    for (int l = 0; l < data.getReferenciasCombosList().get(i).getReferenciaLis().size(); l++) {

                        if (data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getEstado_accion() == 1) {

                            values2.put("id", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getId());
                            values2.put("pn", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getPn());
                            values2.put("producto", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getProducto());
                            values2.put("descripcion", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getDescripcion());
                            values2.put("precio_referencia", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getPrecio_referencia());
                            values2.put("precio_publico", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getPrecio_publico());
                            values2.put("dias_inve", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getDias_inve());
                            values2.put("stock", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getStock());
                            values2.put("ped_sugerido", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getPed_sugerido());
                            values2.put("img", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getUrl_imagen());
                            values2.put("estado_accion", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getEstado_accion());
                            values2.put("id_padre", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getId_padre());

                            db.insert("detalle_combo", null, values2);

                        }
                    }

                } else if (data.getReferenciasCombosList().get(i).getEstado_accion() == 2) {
                    //Actualizar.
                    values.put("id", data.getReferenciasCombosList().get(i).getId());
                    values.put("descripcion", data.getReferenciasCombosList().get(i).getDescripcion());
                    values.put("precioventa", data.getReferenciasCombosList().get(i).getPrecioventa());
                    values.put("speech", data.getReferenciasCombosList().get(i).getSpeech());
                    values.put("pantalla", data.getReferenciasCombosList().get(i).getPantalla());
                    values.put("cam_frontal", data.getReferenciasCombosList().get(i).getCam_frontal());
                    values.put("cam_tras", data.getReferenciasCombosList().get(i).getCam_tras());
                    values.put("flash", data.getReferenciasCombosList().get(i).getFlash());
                    values.put("banda", data.getReferenciasCombosList().get(i).getBanda());
                    values.put("memoria", data.getReferenciasCombosList().get(i).getMemoria());
                    values.put("expandible", data.getReferenciasCombosList().get(i).getExpandible());
                    values.put("bateria", data.getReferenciasCombosList().get(i).getBateria());
                    values.put("bluetooth", data.getReferenciasCombosList().get(i).getBluetooth());
                    values.put("tactil", data.getReferenciasCombosList().get(i).getTactil());
                    values.put("tec_fisico", data.getReferenciasCombosList().get(i).getTec_fisico());
                    values.put("carrito_compras", data.getReferenciasCombosList().get(i).getCarrito_compras());
                    values.put("correo", data.getReferenciasCombosList().get(i).getCorreo());
                    values.put("enrutador", data.getReferenciasCombosList().get(i).getEnrutador());
                    values.put("radio", data.getReferenciasCombosList().get(i).getRadio());
                    values.put("wifi", data.getReferenciasCombosList().get(i).getWifi());
                    values.put("gps", data.getReferenciasCombosList().get(i).getGps());
                    values.put("so", data.getReferenciasCombosList().get(i).getSo());
                    values.put("web", data.getReferenciasCombosList().get(i).getWeb());
                    values.put("precio_referencia", data.getReferenciasCombosList().get(i).getPrecio_referencia());
                    values.put("precio_publico", data.getReferenciasCombosList().get(i).getPrecio_publico());
                    values.put("img", data.getReferenciasCombosList().get(i).getImg());
                    values.put("estado_accion", data.getReferenciasCombosList().get(i).getEstado_accion());

                    int p = db.update("referencia_combo", values, "id = %1$s", new String[]{String.valueOf(data.getReferenciasCombosList().get(i).getId())});

                    for (int l = 0; l < data.getReferenciasCombosList().get(i).getReferenciaLis().size(); l++) {
                        if (data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getEstado_accion() == 2) {

                            values2.put("id", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getId());
                            values2.put("pn", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getPn());
                            values2.put("producto", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getProducto());
                            values2.put("descripcion", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getDescripcion());
                            values2.put("precio_referencia", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getPrecio_referencia());
                            values2.put("precio_publico", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getPrecio_publico());
                            values2.put("dias_inve", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getDias_inve());
                            values2.put("stock", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getStock());
                            values2.put("ped_sugerido", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getPed_sugerido());
                            values2.put("img", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getUrl_imagen());
                            values2.put("estado_accion", data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getEstado_accion());

                            int pl = db.update("detalle_combo", values2, "id = ?", new String[]{String.valueOf(data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getId())});
                        }
                    }

                } else if (data.getReferenciasCombosList().get(i).getEstado_accion() == 0) {
                    //Eliminar.
                    int a = db.delete("referencia_combo", "id = ? ", new String[]{String.valueOf(data.getReferenciasCombosList().get(i).getId())});
                    for (int l = 0; l < data.getReferenciasCombosList().get(i).getReferenciaLis().size(); l++) {
                        if (data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getEstado_accion() == 0) {
                            int al = db.delete("detalle_combo", "id = ? ", new String[]{String.valueOf(data.getReferenciasCombosList().get(i).getReferenciaLis().get(l).getId())});
                        }
                    }
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

    public boolean insertReferenciaSim(Sincronizar data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            for(int i = 0; i < data.getReferenciasSimsList().size(); i++ ) {

                if (data.getReferenciasSimsList().get(i).getEstado_accion() == 1) {
                    //Insertar.
                    values.put("id", data.getReferenciasSimsList().get(i).getId());
                    values.put("pn", data.getReferenciasSimsList().get(i).getPn());
                    values.put("stock", data.getReferenciasSimsList().get(i).getStock());
                    values.put("producto", data.getReferenciasSimsList().get(i).getProducto());
                    values.put("dias_inve", data.getReferenciasSimsList().get(i).getDias_inve());
                    values.put("ped_sugerido", data.getReferenciasSimsList().get(i).getPed_sugerido());
                    values.put("precio_referencia", data.getReferenciasSimsList().get(i).getPrecio_referencia());
                    values.put("precio_publico", data.getReferenciasSimsList().get(i).getPrecio_publico());
                    values.put("quiebre", data.getReferenciasSimsList().get(i).getQuiebre());
                    values.put("estado_accion", data.getReferenciasSimsList().get(i).getEstado_accion());

                    db.insert("referencia_simcard", null, values);
                } else if (data.getReferenciasSimsList().get(i).getEstado_accion() == 2) {
                    //Actualizar.
                    values.put("id", data.getReferenciasSimsList().get(i).getId());
                    values.put("pn", data.getReferenciasSimsList().get(i).getPn());
                    values.put("stock", data.getReferenciasSimsList().get(i).getStock());
                    values.put("producto", data.getReferenciasSimsList().get(i).getProducto());
                    values.put("dias_inve", data.getReferenciasSimsList().get(i).getDias_inve());
                    values.put("ped_sugerido", data.getReferenciasSimsList().get(i).getPed_sugerido());
                    values.put("precio_referencia", data.getReferenciasSimsList().get(i).getPrecio_referencia());
                    values.put("precio_publico", data.getReferenciasSimsList().get(i).getPrecio_publico());
                    values.put("quiebre", data.getReferenciasSimsList().get(i).getQuiebre());
                    values.put("estado_accion", data.getReferenciasSimsList().get(i).getEstado_accion());

                    int p = db.update("referencia_simcard", values, String.format("id = %1$s", data.getReferenciasSimsList().get(i).getId()), null);
                } else if (data.getReferenciasSimsList().get(i).getEstado_accion() == 0) {
                    //Eliminar.
                    int a = db.delete("referencia_simcard", "id = ? ", new String[]{String.valueOf(data.getReferenciasSimsList().get(i).getId())});
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

                if (indicardor == 1)
                    values.put("idpos", (int) (Math.random()*100+1));
                else
                    values.put("idpos",  data.getPuntosList().get(i).getIdpos());

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
                values.put("estado_visita", data.getPuntosList().get(i).getEstadoVisita());

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

    public List<ReferenciasSims> getSimcardLocal(String indicardor) {

        List<ReferenciasSims> referenciasSimses = new ArrayList<>();

        String sql = "SELECT refe.id, refe.pn, 0 stock, refe.producto, 0 dias_inve, 0 ped_sugerido, lprecio.valor_referencia, lprecio.valor_directo, 0 quiebre " +
                " FROM " +
                "  referencia_simcard refe LEFT JOIN lista_precios lprecio ON lprecio.id_referencia = refe.id AND lprecio.idpos = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[] {indicardor});
        ReferenciasSims referenciasSims;

        if (cursor.moveToFirst()) {
            do {

                referenciasSims = new ReferenciasSims();

                referenciasSims.setId(cursor.getInt(0));
                referenciasSims.setPn(cursor.getString(1));
                referenciasSims.setStock(cursor.getInt(2));
                referenciasSims.setProducto(cursor.getString(3));
                referenciasSims.setDias_inve(cursor.getInt(4));
                referenciasSims.setPed_sugerido(cursor.getString(5));
                referenciasSims.setPrecio_referencia(cursor.getDouble(6));
                referenciasSims.setPrecio_publico(cursor.getDouble(7));
                referenciasSims.setQuiebre(cursor.getInt(8));

                referenciasSimses.add(referenciasSims);

            } while (cursor.moveToNext());

        }
        return referenciasSimses;
    }

    public List<ReferenciasCombos> getProductosCombos(String indicardor) {

        List<ReferenciasCombos> referenciasComboses = new ArrayList<>();

        String sql = "SELECT refe.id, refe.descripcion, refe.precioventa, refe.speech, refe.pantalla, refe.cam_frontal, refe.cam_tras, refe.flash, refe.banda, refe.memoria, " +
                " refe.expandible, refe.bateria, refe.bluetooth, refe.tactil, refe.tec_fisico, refe.carrito_compras, refe.correo, refe.enrutador, refe.radio, refe.wifi, refe.gps, " +
                " refe.so, refe.web, 0 quiebre, lprecio.valor_referencia, lprecio.valor_directo, refe.img " +
                " FROM " +
                "   referencia_combo refe LEFT JOIN lista_precios lprecio ON lprecio.id_referencia = refe.id AND lprecio.idpos = ? AND " +
                "lprecio.tipo_pro = 2";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[] {indicardor});
        ReferenciasCombos referenciasCombos;

        if (cursor.moveToFirst()) {
            do {

                referenciasCombos = new ReferenciasCombos();

                referenciasCombos.setId(cursor.getInt(0));
                referenciasCombos.setDescripcion(cursor.getString(1));
                referenciasCombos.setPrecio_referencia(cursor.getDouble(24));
                referenciasCombos.setPrecio_publico(cursor.getDouble(25));
                referenciasCombos.setImg(cursor.getString(26));

                String sqlDestall = "SELECT deta.id, deta.pn, 0 stock, deta.producto, 0 dias_inve, 0 ped_sugerido, deta.descripcion, 0 stock_seguridad, lprecio.valor_referencia, " +
                        " lprecio.valor_directo, deta.img, 0 quiebre " +
                        "   FROM " +
                        "     detalle_combo deta INNER JOIN lista_precios lprecio ON lprecio.id_referencia = deta.id " +
                        "   WHERE " +
                        "     deta.id_padre = ? AND " +
                        "     lprecio.tipo_pro = 2 GROUP BY deta.id ";

                Cursor cursor_detalle = db.rawQuery(sqlDestall, new String[] {String.valueOf(cursor.getInt(0))});
                List<Referencia> referenciaList = new ArrayList<>();
                Referencia referencia;

                if (cursor_detalle.moveToFirst()) {

                    do {
                        referencia = new Referencia();
                        referencia.setId(cursor_detalle.getInt(0));
                        referencia.setStock(cursor_detalle.getInt(2));
                        referencia.setDias_inve(cursor_detalle.getDouble(4));
                        referencia.setProducto(cursor_detalle.getString(3));
                        referencia.setPn(cursor_detalle.getString(1));

                        referenciaList.add(referencia);
                    } while (cursor_detalle.moveToNext());
                }

                referenciasCombos.setReferenciaLis(referenciaList);

                referenciasComboses.add(referenciasCombos);

            } while (cursor.moveToNext());

        }

        return referenciasComboses;

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
                requestGuardarEditarPunto.setDescripcion_vivienda(cursor.getString(9));
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
                requestGuardarEditarPunto.setSubcategoria(cursor.getInt(23));

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

        String sqlDepartamento = "SELECT 0 AS id, 'SELECCIONAR' AS descripcion UNION SELECT id, descripcion FROM departamento ";

        Cursor cursor_departamento = db.rawQuery(sqlDepartamento, null);

        ResponseCreatePunt responseCreatePunt = new ResponseCreatePunt();

        List<Departamentos> departamentosList = new ArrayList<>();
        Departamentos departamento;
        if (cursor_departamento.moveToFirst()) {
            do {

                departamento = new Departamentos(cursor_departamento.getInt(0), cursor_departamento.getString(1));

                String[] args = new String[] {String.valueOf(cursor_departamento.getInt(0))};
                String sqlProvicia = "SELECT 0 AS id_muni, 'SELECCIONAR' AS descripcion, 0 AS departamento UNION SELECT id_muni, descripcion, departamento FROM municipios WHERE departamento = ?";
                Cursor cursor_provincia = db.rawQuery(sqlProvicia, args);
                List<Ciudad> ciudadList = new ArrayList<>();
                Ciudad provincia;
                if (cursor_provincia.moveToFirst()) {
                    do {
                        provincia = new Ciudad(cursor_provincia.getInt(0), cursor_provincia.getString(1));
                        provincia.setDepartamento(cursor_provincia.getInt(2));

                        String[] args2 = new String[] {String.valueOf(cursor_provincia.getInt(0)), String.valueOf(cursor_departamento.getInt(0))};
                        String sqlDistrito = "SELECT 0 AS id, 'SELECCIONAR' AS descripcion, 0 AS id_muni, 0 AS id_depto UNION SELECT id, descripcion, id_muni, id_depto FROM distritos WHERE id_muni = ? AND id_depto = ? ";
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
        String sqlTerritorio = "SELECT 0 AS id, 'SELECCIONAR' AS descripcion UNION SELECT id, descripcion FROM territorio ";
        Cursor cursor_Territorio = db.rawQuery(sqlTerritorio, null);
        List<Territorio> territorioList = new ArrayList<>();
        Territorio territorio;
        if (cursor_Territorio.moveToFirst()) {
            do {
                territorio = new Territorio();
                territorio.setId(cursor_Territorio.getInt(0));
                territorio.setDescripcion(cursor_Territorio.getString(1));

                String[] args3 = new String[] {String.valueOf(cursor_Territorio.getInt(0))};
                String sqlZona = "SELECT 0 AS id, 'SELECCIONAR' AS descripcion, 0 AS id_territorio, 0 AS estado UNION SELECT id, descripcion, id_territorio, estado FROM zona WHERE id_territorio = ? ";
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

        categoriasEstandarList1.add(0, new CategoriasEstandar(0, "SELECCIONAR"));
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

    public ResponseMarcarPedido getPuntoLocal(String responseUser) {

        Cursor cursor;
        ResponseMarcarPedido puntoPedido = new ResponseMarcarPedido();


        String sql = "SELECT pos.idpos, pos.nombre_punto, terri.descripcion terri_des, zona.descripcion zona_des, pos.texto_direccion, depa.descripcion depa_des, muni.descripcion provi_des, distri.descripcion distri_des, " +
                "   pos.estado_visita " +
                " FROM " +
                "   punto pos INNER JOIN " +
                "   territorio terri ON terri.id = pos.territorio_punto " +
                " INNER JOIN zona ON zona.id = pos.zona AND zona.id_territorio = terri.id " +
                " LEFT JOIN departamento depa ON depa.id = pos.depto " +
                " LEFT JOIN municipios muni ON muni.id_muni = pos.ciudad AND muni.departamento = depa.id " +
                " LEFT JOIN distritos distri ON distri.id = pos.distrito AND distri.id_depto = depa.id AND distri.id_muni = muni.id_muni " +
                "  WHERE " +
                "    pos.idpos = ? OR pos.id_tabla = ? ";

        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, new String[] {responseUser, responseUser});

        if (cursor.moveToFirst()) {
            puntoPedido.setEstado(cursor.getInt(8));
            puntoPedido.setId_pos(cursor.getInt(0));
            puntoPedido.setRazon_social(cursor.getString(1));
            puntoPedido.setDireccion(cursor.getString(4));
            puntoPedido.setZona(cursor.getString(3));
            puntoPedido.setTerritorio(cursor.getString(2));
            puntoPedido.setDepto(cursor.getString(5));
            puntoPedido.setProvincia(cursor.getString(6));
            puntoPedido.setDistrito(cursor.getString(7));
        }

        return puntoPedido;
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
                values.put("latitud", data.getLatitud());
                values.put("longitud", data.getLongitud());

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

