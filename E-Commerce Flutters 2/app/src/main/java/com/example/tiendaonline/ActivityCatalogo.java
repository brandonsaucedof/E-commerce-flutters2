package com.example.tiendaonline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendaonline.SOPORTE.CBaseDatos;

public class ActivityCatalogo extends AppCompatActivity {

    private CBaseDatos baseDatos;
    private int contadorProductos = 0;
    int codUsu;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        codUsu = getIntent().getExtras().getInt("codUsu");

        // Inicializar la instancia de la base de datos
        baseDatos = new CBaseDatos(this, "dbtiendaFinal", null, 1);

        // Obtener una instancia de la base de datos en modo lectura
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        // Realizar una consulta para obtener los productos
        Cursor cursor = db.rawQuery("SELECT * FROM Productos", null);

        // Iterar sobre el cursor para obtener los datos de cada producto
        while (cursor.moveToNext() && contadorProductos < 8) { // Mostrar solo los primeros ocho productos
            // Obtener los datos del producto del cursor
            @SuppressLint("Range") String nombreProducto = cursor.getString(cursor.getColumnIndex("producto"));
            @SuppressLint("Range") int precioProducto = cursor.getInt(cursor.getColumnIndex("precioUnidad"));
            @SuppressLint("Range") String nombreImagen = cursor.getString(cursor.getColumnIndex("img"));

            // Mostrar los datos del producto en las vistas correspondientes
            mostrarProducto(nombreProducto, precioProducto, nombreImagen, contadorProductos, cursor.getInt(cursor.getColumnIndex("codProducto")));
            contadorProductos++;
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();
    }

    private void mostrarProducto(String nombreProducto, int precioProducto, String nombreImagen, int posicion, int productoId) {
        // Obtener referencias a las vistas correspondientes al producto en la posición dada
        int[] imageViewIds = {R.id.imageViewProducto, R.id.imageViewProducto2, R.id.imageViewProducto3, R.id.imageViewProducto4, R.id.imageViewProducto5, R.id.imageViewProducto6, R.id.imageViewProducto7, R.id.imageViewProducto8};
        int[] textViewNombreIds = {R.id.textViewNombreProducto, R.id.textViewNombreProducto2, R.id.textViewNombreProducto3, R.id.textViewNombreProducto4, R.id.textViewNombreProducto5, R.id.textViewNombreProducto6, R.id.textViewNombreProducto7, R.id.textViewNombreProducto8};
        int[] textViewPrecioIds = {R.id.textViewPrecioProducto, R.id.textViewPrecioProducto2, R.id.textViewPrecioProducto3, R.id.textViewPrecioProducto4, R.id.textViewPrecioProducto5, R.id.textViewPrecioProducto6, R.id.textViewPrecioProducto7, R.id.textViewPrecioProducto8};

        ImageView imageView = findViewById(imageViewIds[posicion]);
        TextView textViewNombre = findViewById(textViewNombreIds[posicion]);
        TextView textViewPrecio = findViewById(textViewPrecioIds[posicion]);

        // Establecer la imagen del producto
        int idImagen = getResources().getIdentifier(nombreImagen, "drawable", getPackageName());
        imageView.setImageResource(idImagen);

        // Establecer el nombre del producto y el precio
        textViewNombre.setText(nombreProducto);
        textViewPrecio.setText("$" + precioProducto);

        // Obtener el contenedor del producto actual
        LinearLayout productoLayout = findViewById(getResources().getIdentifier("layoutProducto" + (posicion + 1), "id", getPackageName()));

        // Establecer un OnClickListener para el contenedor del producto actual
        productoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir la nueva actividad
                Intent intent = new Intent(ActivityCatalogo.this, ActivityDetalProducto.class);

                // Pasar el ID del producto como extra al Intent
                intent.putExtra("productoId", productoId);
                intent.putExtra("codUsu", codUsu);

                // Iniciar la nueva actividad
                startActivity(intent);
            }
        });
    }}