package com.example.tareagrupal3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tareagrupal3.modelo.Articulo;
import com.example.tareagrupal3.modelo.Cliente;
import com.example.tareagrupal3.modelo.Detalle;
import com.example.tareagrupal3.modelo.Direccion;
import com.example.tareagrupal3.modelo.Pedido;
import com.example.tareagrupal3.modelo.Cliente;
import com.example.tareagrupal3.modelo.Direccion;

import java.util.ArrayList;

public class DBAdapter {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB_TareaSemana11";

    //    Nombre de las Tablas
    private static final String TABLE_CLIENTE = "table_cliente";
    private static final String TABLE_DIRECCION = "table_direccion";
    private static final String TABLE_PEDIDO = "table_pedido";
    private static final String TABLE_ARTICULO = "table_articulo";
    private static final String TABLE_DETALLE = "table_detalle";

    //    Nombres de los campos por cada tabla
    private static final String KEY_IDCLI = "idCliente";
    private static final String KEY_NOMBRE = "nombre";

    private static final String KEY_IDDIR = "idDireccion";
    private static final String KEY_NUMERO = "numero";
    private static final String KEY_CALLE = "calle";
    private static final String KEY_COMUNA = "comuna";
    private static final String KEY_CIUDAD = "ciudad";

    private static final String KEY_IDPED = "idPedido";
    private static final String KEY_FECHA = "fecha_envio";

    private static final String KEY_IDART = "idArticulo";
    private static final String KEY_DESCRIPCION = "descripcion";
    private static final String KEY_STOCK = "stock";

    private static final String KEY_CANTIDAD = "cantidad";

    private static final String TABLE_CREATE_CLIENTE = "CREATE TABLE " + TABLE_CLIENTE +
            "(" +
            KEY_IDCLI + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NOMBRE + " TEXT NOT NULL " +
            ")";

    private static final String TABLE_CREATE_DIRECCION = "CREATE TABLE " + TABLE_DIRECCION +
            "(" +
            KEY_IDDIR + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NUMERO + " TEXT NOT NULL, " +
            KEY_CALLE + " TEXT NOT NULL, " +
            KEY_COMUNA + " TEXT NOT NULL, " +
            KEY_CIUDAD + " TEXT NOT NULL, " +
            KEY_IDCLI + " INTEGER NOT NULL " +
            ")";

    private static final String TABLE_CREATE_PEDIDO = "CREATE TABLE " + TABLE_PEDIDO +
            "(" +
            KEY_IDPED + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_FECHA + " TEXT NOT NULL, " +
            KEY_IDCLI + " INTEGER NOT NULL, " +
            KEY_IDDIR + " INTEGER NOT NULL " +
            ")";

    private static final String TABLE_CREATE_ARTICULO = "CREATE TABLE " + TABLE_ARTICULO +
            "(" +
            KEY_IDART + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DESCRIPCION + " TEXT NOT NULL, " +
            KEY_STOCK + " INTEGER NOT NULL " +
            ")";

    private static final String TABLE_CREATE_DETALLE = "CREATE TABLE " + TABLE_DETALLE +
            "(" +
            KEY_IDART + " INTEGER NOT NULL, " +
            KEY_IDPED + " INTEGER NOT NULL, " +
            KEY_CANTIDAD + " INTEGER NOT NULL, " +
            "PRIMARY KEY(" +  KEY_IDART + "," + KEY_IDPED + ")" +
            ")";

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private static Context context;

    public DBAdapter(Context context){
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE_CLIENTE);
            db.execSQL(TABLE_CREATE_DIRECCION);
            db.execSQL(TABLE_CREATE_PEDIDO);
            db.execSQL(TABLE_CREATE_ARTICULO);
            db.execSQL(TABLE_CREATE_DETALLE);

            Cliente cliente = new Cliente("");
            ContentValues values = new ContentValues();
            values.put(KEY_NOMBRE, cliente.getNombre());
            db.insert(TABLE_CLIENTE, null, values);

            Articulo articulo1 = new Articulo("Audífono Halion S1 Scorpion", 25);
            ContentValues values1 = new ContentValues();
            values1.put(KEY_DESCRIPCION, articulo1.getDescripcion());
            values1.put(KEY_STOCK, articulo1.getStock());
            db.insert(TABLE_ARTICULO, null, values1);

            Articulo articulo2 = new Articulo("Teclado Gamer Metálico RGB Aoas M888", 20);
            ContentValues values2 = new ContentValues();
            values2.put(KEY_DESCRIPCION, articulo2.getDescripcion());
            values2.put(KEY_STOCK, articulo2.getStock());
            db.insert(TABLE_ARTICULO, null, values2);

            Articulo articulo3 = new Articulo("Mouse Gamer AOAS V02", 30);
            ContentValues values3 = new ContentValues();
            values3.put(KEY_DESCRIPCION, articulo3.getDescripcion());
            values3.put(KEY_STOCK, articulo3.getStock());
            db.insert(TABLE_ARTICULO, null, values3);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIRECCION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEDIDO);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICULO);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETALLE);
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLiteException {
        try {
            db = databaseHelper.getWritableDatabase();
        } catch (SQLiteException e){
            Toast.makeText(context, "Error al Abrir la Base de Datos", Toast.LENGTH_LONG).show();
        }
        return this;
    }

    public boolean isCreated(){
        if (db != null){
            return db.isOpen();
        }
        return false;
    }

    public boolean isOpen(){
        if (db == null){
            return false;
        }
        return db.isOpen();
    }

    public void close(){
        databaseHelper.close();
        db.close();
    }

    public void Actualizar_Cliente(Cliente cliente){
        String where = KEY_IDCLI + "=" + cliente.getIdCliente();
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, cliente.getNombre());
        db.update(TABLE_CLIENTE, values, where, null);
    }

    public Cliente Traer_Cliente(int codigo){
        String query = "SELECT * FROM " + TABLE_CLIENTE + " WHERE " + KEY_IDCLI + " = " + codigo;
        Cliente cliente = null;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                cliente = new Cliente();
                cliente.setIdCliente(cursor.getInt(0));
                cliente.setNombre(cursor.getString(1));
            }
            cursor.close();
        } catch (Exception ex){

        }
        return cliente;
    }

    public ArrayList<Cliente> Traer_Clientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        try{
            String query = "SELECT * FROM " + TABLE_CLIENTE;
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(cursor.getInt(0));
                    cliente.setNombre(cursor.getString(1));
                    clientes.add(cliente);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return clientes;
        }catch (Exception e){
            return null;
        }
    }

    public void Insertar_Direccion(Direccion direccion){
        ContentValues values = new ContentValues();
        values.put(KEY_NUMERO, direccion.getNumero());
        values.put(KEY_CALLE, direccion.getCalle());
        values.put(KEY_COMUNA, direccion.getComuna());
        values.put(KEY_CIUDAD, direccion.getCiudad());
        values.put(KEY_IDCLI, direccion.getIdCliente());
        db.insert(TABLE_DIRECCION, null, values);
    }

    public void Actualizar_Direccion(Direccion direccion){
        String where = KEY_IDDIR + "=" + direccion.getIdDireccion();
        ContentValues values = new ContentValues();
        values.put(KEY_NUMERO, direccion.getNumero());
        values.put(KEY_CALLE, direccion.getCalle());
        values.put(KEY_COMUNA, direccion.getComuna());
        values.put(KEY_CIUDAD, direccion.getCiudad());
        values.put(KEY_IDCLI, direccion.getIdCliente());
        db.update(TABLE_DIRECCION, values, where, null);
    }

    public void Eliminar_Direccion(Direccion direccion){
        String where = KEY_IDDIR + "=" + direccion.getIdDireccion();
        db.delete(TABLE_DIRECCION, where, null);
    }

    public Direccion Traer_Direccion(int codigo){
        String query = "SELECT * FROM " + TABLE_DIRECCION + " WHERE " + KEY_IDDIR + " = " + codigo;
        Direccion direccion = null;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                direccion = new Direccion();
                direccion.setIdDireccion(cursor.getInt(0));
                direccion.setNumero(cursor.getString(1));
                direccion.setCalle(cursor.getString(2));
                direccion.setComuna(cursor.getString(3));
                direccion.setCiudad(cursor.getString(4));
                direccion.setIdCliente(cursor.getInt(5));
            }
            cursor.close();
        } catch (Exception ex){

        }
        return direccion;
    }

    public ArrayList<Direccion> Traer_Direcciones(){
        ArrayList<Direccion> direcciones = new ArrayList<>();
        try{
            String query = "SELECT * FROM " + TABLE_DIRECCION;
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(cursor.getInt(0));
                    direccion.setNumero(cursor.getString(1));
                    direccion.setCalle(cursor.getString(2));
                    direccion.setComuna(cursor.getString(3));
                    direccion.setCiudad(cursor.getString(4));
                    direccion.setIdCliente(cursor.getInt(5));
                    direcciones.add(direccion);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return direcciones;
        }catch (Exception e){
            return null;
        }
    }

    public void Insertar_Pedido(Pedido pedido){
        ContentValues values = new ContentValues();
        values.put(KEY_FECHA, pedido.getFecha_envio());
        values.put(KEY_IDCLI, pedido.getIdCliente());
        values.put(KEY_IDDIR, pedido.getIdDireccion());
        db.insert(TABLE_PEDIDO, null, values);
    }

    public void Actualizar_Pedido(Pedido pedido){
        String where = KEY_IDPED + "=" + pedido.getIdPedido();
        ContentValues values = new ContentValues();
        values.put(KEY_FECHA, pedido.getFecha_envio());
        values.put(KEY_IDCLI, pedido.getIdCliente());
        values.put(KEY_IDDIR, pedido.getIdDireccion());
        db.update(TABLE_PEDIDO, values, where, null);
    }

    public void Eliminar_Pedido(Pedido pedido){
        String where = KEY_IDPED + "=" + pedido.getIdPedido();
        db.delete(TABLE_PEDIDO, where, null);
    }

    public Pedido Traer_Pedido(int codigo){
        String query = "SELECT * FROM " + TABLE_PEDIDO + " WHERE " + KEY_IDPED + " = " + codigo;
        Pedido pedido = null;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                pedido = new Pedido();
                pedido.setIdPedido(cursor.getInt(0));
                pedido.setFecha_envio(cursor.getString(1));
                pedido.setIdCliente(cursor.getInt(2));
                pedido.setIdDireccion(cursor.getInt(3));
            }
            cursor.close();
        } catch (Exception ex){

        }
        return pedido;
    }

    public ArrayList<Pedido> Traer_Pedidos(){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        try{
            String query = "SELECT * FROM " + TABLE_PEDIDO;
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(cursor.getInt(0));
                    pedido.setFecha_envio(cursor.getString(1));
                    pedido.setIdCliente(cursor.getInt(2));
                    pedido.setIdDireccion(cursor.getInt(3));
                    pedidos.add(pedido);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return pedidos;
        }catch (Exception e){
            return null;
        }
    }

    public void Insertar_Detalle(Detalle detalle){
        ContentValues values = new ContentValues();
        values.put(KEY_IDART, detalle.getIdArticulo());
        values.put(KEY_IDPED, detalle.getIdPedido());
        values.put(KEY_CANTIDAD, detalle.getCantidad());
        db.insert(TABLE_DETALLE, null, values);
    }

    public void Actualizar_Detalle(Detalle detalle){
        String where = KEY_IDPED + "=" + detalle.getIdPedido();
        ContentValues values = new ContentValues();
        values.put(KEY_IDART, detalle.getIdArticulo());
        values.put(KEY_IDPED, detalle.getIdPedido());
        values.put(KEY_CANTIDAD, detalle.getCantidad());
        db.update(TABLE_DETALLE, values, where, null);
    }

    public void Eliminar_Detalle(Detalle detalle){
        String where = KEY_IDART + "=" + detalle.getIdArticulo() + " AND " + KEY_IDPED + "=" + detalle.getIdPedido();
        db.delete(TABLE_DETALLE, where, null);
    }

    public Detalle Traer_Detalle(int idArticulo, int idPedido){
        String query = "SELECT * FROM " + TABLE_DETALLE + " WHERE " + KEY_IDART + "=" + idArticulo + " AND " + KEY_IDPED + "=" + idPedido;
        Detalle detalle = null;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                detalle = new Detalle();
                detalle.setIdArticulo(cursor.getInt(0));
                detalle.setIdPedido(cursor.getInt(1));
                detalle.setCantidad(cursor.getInt(2));
            }
            cursor.close();
        } catch (Exception ex){

        }
        return detalle;
    }

    public ArrayList<Detalle> Traer_Detalles(int codigo){
        ArrayList<Detalle> detalles = new ArrayList<>();
        try{
            String query = "SELECT * FROM " + TABLE_DETALLE + " WHERE " + KEY_IDPED + "=" + codigo;
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    Detalle detalle = new Detalle();
                    detalle.setIdArticulo(cursor.getInt(0));
                    detalle.setIdPedido(cursor.getInt(1));
                    detalle.setCantidad(cursor.getInt(2));
                    detalles.add(detalle);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return detalles;
        }catch (Exception e){
            return null;
        }
    }

    public boolean Actualizar_Stock(int idArticulo, int cantidadantes, int cantidadnueva){
        String where = KEY_IDART + "=" + idArticulo;
        ContentValues values = new ContentValues();
        Articulo articulo = Traer_Articulo(idArticulo);
        int stock = articulo.getStock();
        if (stock+cantidadantes>=cantidadnueva){
            values.put(KEY_STOCK, stock+cantidadantes-cantidadnueva);
            return 1==db.update(TABLE_ARTICULO, values, where, null);
        }
        return false;
    }

    public ArrayList<Articulo> Traer_Articulos(){
        ArrayList<Articulo> articulos = new ArrayList<>();
        try{
            String query = "SELECT * FROM " + TABLE_ARTICULO;
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    Articulo articulo = new Articulo();
                    articulo.setIdArticulo(cursor.getInt(0));
                    articulo.setDescripcion(cursor.getString(1));
                    articulo.setStock(cursor.getInt(2));
                    articulos.add(articulo);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return articulos;
        }catch (Exception e){
            return null;
        }
    }

    public Articulo Traer_Articulo(int codigo){
        String query = "SELECT * FROM " + TABLE_ARTICULO + " WHERE " + KEY_IDART + " = " + codigo;
        Articulo articulo = null;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                articulo = new Articulo();
                articulo.setIdArticulo(cursor.getInt(0));
                articulo.setDescripcion(cursor.getString(1));
                articulo.setStock(cursor.getInt(2));
            }
            cursor.close();
        } catch (Exception ex){

        }
        return articulo;
    }
}
