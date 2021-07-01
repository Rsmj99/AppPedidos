package com.example.tareagrupal3.modelo;

import java.io.Serializable;

public class Pedido implements Serializable {

    private int idPedido;
    private String fecha_envio;
    private int idCliente;
    private int idDireccion;

    public Pedido() {
    }

    public Pedido(String fecha_envio, int idCliente, int idDireccion) {
        this.fecha_envio = fecha_envio;
        this.idCliente = idCliente;
        this.idDireccion = idDireccion;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(String fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }
}
