package com.example.tareagrupal3.db;

import android.content.Context;

import com.example.tareagrupal3.modelo.Articulo;
import com.example.tareagrupal3.modelo.Cliente;
import com.example.tareagrupal3.modelo.Detalle;
import com.example.tareagrupal3.modelo.Direccion;
import com.example.tareagrupal3.modelo.Pedido;
import com.example.tareagrupal3.modelo.Cliente;
import com.example.tareagrupal3.modelo.Direccion;

import java.util.ArrayList;

public class DBHelper {

    private DBAdapter dbAdapter;

    public DBHelper(Context context){
        dbAdapter = new DBAdapter(context);
    }

    public boolean isOpen(){
        return dbAdapter.isOpen();
    }

    public void Actualizar_Cliente(Cliente cliente){
        dbAdapter.open();
        dbAdapter.Actualizar_Cliente(cliente);
        dbAdapter.close();
    }

    public Cliente Traer_Cliente(int codigo){
        dbAdapter.open();
        Cliente cliente = dbAdapter.Traer_Cliente(codigo);
        dbAdapter.close();
        return cliente;
    }

    public ArrayList<Cliente> Traer_Clientes(){
        dbAdapter.open();
        ArrayList<Cliente> clientes = dbAdapter.Traer_Clientes();
        dbAdapter.close();
        return clientes;
    }

    public void Insertar_Direccion(Direccion direccion){
        dbAdapter.open();
        dbAdapter.Insertar_Direccion(direccion);
        dbAdapter.close();
    }

    public void Actualizar_Direccion(Direccion direccion){
        dbAdapter.open();
        dbAdapter.Actualizar_Direccion(direccion);
        dbAdapter.close();
    }

    public void Eliminar_Direccion(Direccion direccion){
        dbAdapter.open();
        dbAdapter.Eliminar_Direccion(direccion);
        dbAdapter.close();
    }

    public Direccion Traer_Direccion(int codigo){
        dbAdapter.open();
        Direccion direccion = dbAdapter.Traer_Direccion(codigo);
        dbAdapter.close();
        return direccion;
    }

    public ArrayList<Direccion> Traer_Direcciones(){
        dbAdapter.open();
        ArrayList<Direccion> direcciones = dbAdapter.Traer_Direcciones();
        dbAdapter.close();
        return direcciones;
    }

    public void Insertar_Pedido(Pedido pedido){
        dbAdapter.open();
        dbAdapter.Insertar_Pedido(pedido);
        dbAdapter.close();
    }

    public void Actualizar_Pedido(Pedido pedido){
        dbAdapter.open();
        dbAdapter.Actualizar_Pedido(pedido);
        dbAdapter.close();
    }

    public void Eliminar_Pedido(Pedido pedido){
        dbAdapter.open();
        dbAdapter.Eliminar_Pedido(pedido);
        dbAdapter.close();
    }

    public Pedido Traer_Pedido(int codigo){
        dbAdapter.open();
        Pedido pedido = dbAdapter.Traer_Pedido(codigo);
        dbAdapter.close();
        return pedido;
    }

    public ArrayList<Pedido> Traer_Pedidos(){
        dbAdapter.open();
        ArrayList<Pedido> pedidos = dbAdapter.Traer_Pedidos();
        dbAdapter.close();
        return pedidos;
    }

    public void Insertar_Detalle(Detalle detalle){
        dbAdapter.open();
        dbAdapter.Insertar_Detalle(detalle);
        dbAdapter.close();
    }

    public void Actualizar_Detalle(Detalle detalle){
        dbAdapter.open();
        dbAdapter.Actualizar_Detalle(detalle);
        dbAdapter.close();
    }

    public void Eliminar_Detalle(Detalle detalle){
        dbAdapter.open();
        dbAdapter.Eliminar_Detalle(detalle);
        dbAdapter.close();
    }

    public Detalle Traer_Detalle(int idArticulo, int idPedido){
        dbAdapter.open();
        Detalle detalle = dbAdapter.Traer_Detalle(idArticulo, idPedido);
        dbAdapter.close();
        return detalle;
    }

    public ArrayList<Detalle> Traer_Detalles(int codigo){
        dbAdapter.open();
        ArrayList<Detalle> detalles = dbAdapter.Traer_Detalles(codigo);
        dbAdapter.close();
        return detalles;
    }

    public boolean Actualizar_Stock(int idArticulo, int cantidadantes, int cantidadnueva){
        dbAdapter.open();
        Boolean result = dbAdapter.Actualizar_Stock(idArticulo, cantidadantes, cantidadnueva);
        dbAdapter.close();
        return result;
    }

    public ArrayList<Articulo> Traer_Articulos(){
        dbAdapter.open();
        ArrayList<Articulo> articulos = dbAdapter.Traer_Articulos();
        dbAdapter.close();
        return articulos;
    }

    public Articulo Traer_Articulo(int codigo){
        dbAdapter.open();
        Articulo articulo = dbAdapter.Traer_Articulo(codigo);
        dbAdapter.close();
        return articulo;
    }
}
