package com.example.tareagrupal3.modelo;

public class Detalle {

    private int idArticulo;
    private int idPedido;
    private int cantidad;

    public Detalle() {
    }

    public Detalle(int idArticulo, int idPedido, int cantidad) {
        this.idArticulo = idArticulo;
        this.idPedido = idPedido;
        this.cantidad = cantidad;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
