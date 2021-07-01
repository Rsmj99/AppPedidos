package com.example.tareagrupal3.modelo;

public class Articulo {
    private int idArticulo;
    private String descripcion;
    private int stock;

    public Articulo() {
    }

    public Articulo(String descripcion, int stock) {
        this.descripcion = descripcion;
        this.stock = stock;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
