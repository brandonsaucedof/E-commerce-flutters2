package com.example.tiendaonline.SOPORTE;
public class Producto {
    private int codProducto;
    private String nombreProducto;
    private int precioProducto;
    private String talla;
    private String nombreImagen;
    private String categoria;
    private String marca;

    // Constructor
    public Producto(int codProducto, String nombreProducto, int precioProducto, String talla, String nombreImagen, String categoria, String marca) {
        this.codProducto = codProducto;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.talla = talla;
        this.nombreImagen = nombreImagen;
        this.categoria = categoria;
        this.marca = marca;
    }

    // Getters
    public int getCodProducto() {
        return codProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getPrecioProducto() {
        return precioProducto;
    }

    public String getTalla() {
        return talla;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void remove(Producto producto) {
    }
}
