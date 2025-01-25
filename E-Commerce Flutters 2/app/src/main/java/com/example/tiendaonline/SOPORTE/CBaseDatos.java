package com.example.tiendaonline.SOPORTE;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CBaseDatos extends SQLiteOpenHelper {
    public CBaseDatos(@Nullable Context context,
                      @Nullable String name,
                      @Nullable SQLiteDatabase.CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table Usuario (codUsuario Integer Primary key autoincrement, Nombre text, Apellido text, Usuario text, contraseña text, lat REAL, long REAL)");

        db.execSQL("Create Table Productos (codProducto Integer Primary key autoincrement, producto text, fechaIngreso date, precioUnidad int, talla text, cantidadStock int, img Text, codCategoria int, codMarca int)");
        db.execSQL("Create Table Marca (codMarca int, Marca Text)");
        db.execSQL("Create Table Categoria (codCategoria int, Categoria Text)");
        db.execSQL("Create Table Carrito (codCart Integer Primary key autoincrement, codProducto int,Cantidad int)");
        db.execSQL("Create Table Venta (codVenta Integer Primary key autoincrement, fechaVenta date, precioTotal int, codUsuario int)");
        db.execSQL("Create Table DetalleVenta (codDetalle Integer Primary Key autoincrement, cantidad int, precioProducto int, codVenta int, codProducto int)");

        ContentValues reg = new ContentValues();

        reg.put("Nombre", "admin");
        reg.put("Apellido", "admin");
        reg.put("Usuario", "admin");
        reg.put("contraseña", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
        reg.put("lat", -17.78897008789782);
        reg.put("long", -63.170831484660475);
        db.insert("Usuario", null, reg);
        reg.clear();


        reg.put("Nombre", "admin");
        reg.put("Apellido", "admin");
        reg.put("Usuario", "admin");
        reg.put("contraseña", "admin");
        reg.put("direccion", "admin");
        db.insert("Usuario", null, reg);
        reg.clear();


        reg.put("codMarca", 0);
        reg.put("Marca", "Adilas");
        db.insert("Marca", null, reg);
        reg.clear();
        reg.put("codMarca", 1);
        reg.put("Marca", "Lorens");
        db.insert("Marca", null, reg);
        reg.clear();
        reg.put("codMarca", 2);
        reg.put("Marca", "Divs");
        db.insert("Marca", null, reg);
        reg.clear();


        reg.put("codCategoria", 0);
        reg.put("Categoria", "Chompa");
        db.insert("Categoria", null, reg);
        reg.clear();
        reg.put("codCategoria", 1);
        reg.put("Categoria", "Pantalones");
        db.insert("Categoria", null, reg);
        reg.clear();


        reg.put("producto", "Pantalon jean 01");
        reg.put("fechaIngreso", "24/05/2024");
        reg.put("precioUnidad", "25");
        reg.put("talla", "x");
        reg.put("cantidadStock", "15");
        reg.put("img", "pantalon_1");
        reg.put("codCategoria", 1);
        reg.put("codMarca", 1);
        db.insert("Productos", null, reg);
        reg.clear();
        reg.put("producto", "Pantalon Jean 02");
        reg.put("fechaIngreso", "12/05/2024");
        reg.put("precioUnidad", "20");
        reg.put("talla", "xl");
        reg.put("cantidadStock", "15");
        reg.put("img", "pantalon_2");
        reg.put("codCategoria", 1);
        reg.put("codMarca", 2);
        db.insert("Productos", null, reg);
        reg.clear();
        reg.put("producto", "Pantalon jean 03");
        reg.put("fechaIngreso", "05/08/2024");
        reg.put("precioUnidad", "40");
        reg.put("talla", "xxl");
        reg.put("cantidadStock", "15");
        reg.put("img", "pantalon_3");
        reg.put("codCategoria", 1);
        reg.put("codMarca", 0);
        db.insert("Productos", null, reg);
        reg.clear();
        reg.put("producto", "Pantalon jean 04");
        reg.put("fechaIngreso", "15/02/2024");
        reg.put("precioUnidad", "35");
        reg.put("talla", "x");
        reg.put("cantidadStock", "18");
        reg.put("img", "pantalon_4");
        reg.put("codCategoria", 1);
        reg.put("codMarca", 2);
        db.insert("Productos", null, reg);
        reg.clear();

        reg.put("producto", "Chompa 01");
        reg.put("fechaIngreso", "14/03/2024");
        reg.put("precioUnidad", "25");
        reg.put("talla", "x");
        reg.put("cantidadStock", "36");
        reg.put("img", "polera_1");
        reg.put("codCategoria", 0);
        reg.put("codMarca", 2);
        db.insert("Productos", null, reg);
        reg.clear();
        reg.put("producto", "Chompa 02");
        reg.put("fechaIngreso", "17/03/2024");
        reg.put("precioUnidad", "48");
        reg.put("talla", "x");
        reg.put("cantidadStock", "10");
        reg.put("img", "polera_2");
        reg.put("codCategoria", 0);
        reg.put("codMarca", 2);
        db.insert("Productos", null, reg);
        reg.clear();
        reg.put("producto", "Chompa 03");
        reg.put("fechaIngreso", "14/03/2024");
        reg.put("precioUnidad", "25");
        reg.put("talla", "x");
        reg.put("cantidadStock", "16");
        reg.put("img", "polera_3");
        reg.put("codCategoria", 0);
        reg.put("codMarca", 2);
        db.insert("Productos", null, reg);
        reg.clear();
        reg.put("producto", "Chompa 04");
        reg.put("fechaIngreso", "07/07/2024");
        reg.put("precioUnidad", "65");
        reg.put("talla", "x");
        reg.put("cantidadStock", "36");
        reg.put("img", "polera_4");
        reg.put("codCategoria", 0);
        reg.put("codMarca", 1);
        db.insert("Productos", null, reg);
        reg.clear();
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}