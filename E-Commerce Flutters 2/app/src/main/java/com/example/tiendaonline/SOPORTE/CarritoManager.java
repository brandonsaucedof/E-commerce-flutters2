package com.example.tiendaonline.SOPORTE;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CarritoManager {

    private CBaseDatos baseDatos;

    public CarritoManager(CBaseDatos baseDatos) {
        this.baseDatos = baseDatos;
    }

    public List<Producto> obtenerProductosCarrito(SQLiteDatabase db) {
        List<Producto> productos = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM Carrito", null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int codProducto = cursor.getInt(cursor.getColumnIndex("codProducto"));
            @SuppressLint("Range") int cantidad = cursor.getInt(cursor.getColumnIndex("Cantidad"));

            Producto producto = obtenerProductoCarrito(db, codProducto, cantidad);
            if (producto != null) {
                productos.add(producto);
            }
        }

        cursor.close();

        return productos;
    }

    private Producto obtenerProductoCarrito(SQLiteDatabase db, int codProducto, int cantidad) {
        Cursor cursor = db.rawQuery("SELECT producto, precioUnidad, talla, img, Categoria.Categoria, Marca.Marca " +
                "FROM Productos " +
                "JOIN Categoria ON Productos.codCategoria = Categoria.codCategoria " +
                "JOIN Marca ON Productos.codMarca = Marca.codMarca " +
                "WHERE codProducto = ?", new String[]{String.valueOf(codProducto)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String nombreProducto = cursor.getString(cursor.getColumnIndex("producto"));
            @SuppressLint("Range") int precioProducto = cursor.getInt(cursor.getColumnIndex("precioUnidad"));
            @SuppressLint("Range") String talla = cursor.getString(cursor.getColumnIndex("talla"));
            @SuppressLint("Range") String nombreImagen = cursor.getString(cursor.getColumnIndex("img"));
            @SuppressLint("Range") String categoria = cursor.getString(cursor.getColumnIndex("Categoria"));
            @SuppressLint("Range") String marca = cursor.getString(cursor.getColumnIndex("Marca"));

            cursor.close();
            return new Producto(codProducto, nombreProducto, precioProducto, talla, nombreImagen, categoria, marca);
        } else {
            cursor.close();
            return null;
        }
    }
}
