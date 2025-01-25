package com.example.tiendaonline.SOPORTE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.tiendaonline.ActivityDetalProducto;
import com.example.tiendaonline.CarritoActivity;

public class DetailProd {

    private CBaseDatos baseDatos;

    public DetailProd(CBaseDatos baseDatos) {
        this.baseDatos = baseDatos;
    }

    public void mostrarDetallesProducto(ActivityDetalProducto activity, int productoId) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Productos WHERE codProducto = ?", new String[]{String.valueOf(productoId)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String nombreProducto = cursor.getString(cursor.getColumnIndex("producto"));
            @SuppressLint("Range") int precioProducto = cursor.getInt(cursor.getColumnIndex("precioUnidad"));
            @SuppressLint("Range") String talla = cursor.getString(cursor.getColumnIndex("talla"));
            @SuppressLint("Range") String nombreImagen = cursor.getString(cursor.getColumnIndex("img"));
            @SuppressLint("Range") int codCategoria = cursor.getInt(cursor.getColumnIndex("codCategoria"));
            @SuppressLint("Range") int codMarca = cursor.getInt(cursor.getColumnIndex("codMarca"));

            String categoria = obtenerCategoria(codCategoria);
            String marca = obtenerMarca(codMarca);

            activity.mostrarDetallesProducto(nombreProducto, precioProducto, talla, nombreImagen, categoria, marca);
        }

        cursor.close();
        db.close();
    }

    @SuppressLint("Range")
    private String obtenerCategoria(int codCategoria) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Categoria FROM Categoria WHERE codCategoria = ?", new String[]{String.valueOf(codCategoria)});
        String categoria = "";
        if (cursor.moveToFirst()) {
            categoria = cursor.getString(cursor.getColumnIndex("Categoria"));
        }
        cursor.close();
        db.close();
        return categoria;
    }

    @SuppressLint("Range")
    private String obtenerMarca(int codMarca) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Marca FROM Marca WHERE codMarca = ?", new String[]{String.valueOf(codMarca)});
        String marca = "";
        if (cursor.moveToFirst()) {
            marca = cursor.getString(cursor.getColumnIndex("Marca"));
        }
        cursor.close();
        db.close();
        return marca;
    }

    public void addToCart(ActivityDetalProducto activity, int productoId, int codUsu) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        int cantidad = 1;
        ContentValues values = new ContentValues();
        values.put("codProducto", productoId);
        values.put("cantidad", cantidad);
        long newRowId = db.insert("Carrito", null, values);
        if (newRowId != -1) {
            Toast.makeText(activity, "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, CarritoActivity.class);
            intent.putExtra("codUsu", codUsu);
            activity.startActivity(intent);
        } else {
            Toast.makeText(activity, "Error al agregar el producto al carrito", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
