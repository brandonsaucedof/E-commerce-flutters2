package com.example.tiendaonline;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendaonline.SOPORTE.CBaseDatos;
import com.example.tiendaonline.SOPORTE.DetailProd;

public class ActivityDetalProducto extends AppCompatActivity {

    private CBaseDatos baseDatos;
    private int productoId;
    private DetailProd detailProd;
    int codUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        codUsu = getIntent().getExtras().getInt("codUsu");

        baseDatos = new CBaseDatos(this, "dbtiendaFinal", null, 1);
        productoId = getIntent().getIntExtra("productoId", -1);
        detailProd = new DetailProd(baseDatos);

        if (productoId != -1) {
            detailProd.mostrarDetallesProducto(this, productoId);
        }

        Button botonCarrito = findViewById(R.id.btncomprar);
        botonCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productoId != -1) {
                    detailProd.addToCart(ActivityDetalProducto.this, productoId, codUsu);
                }
            }
        });
    }

    public void mostrarDetallesProducto(String nombreProducto, int precioProducto, String talla, String nombreImagen, String categoria, String marca) {
        TextView textViewNombreProducto = findViewById(R.id.textViewNombreProducto);
        TextView textViewPrecioProducto = findViewById(R.id.textViewPrecioProducto);
        ImageView imageViewProducto = findViewById(R.id.imageView);
        TextView textViewTalla = findViewById(R.id.textViewTalla);
        TextView textViewCategoria = findViewById(R.id.textViewCategoria);
        TextView textViewMarca = findViewById(R.id.textViewMarca);

        textViewNombreProducto.setText(nombreProducto);
        textViewPrecioProducto.setText("$" + precioProducto);
        textViewTalla.setText("Talla: " + talla);
        textViewCategoria.setText("Categoría: " + categoria);
        textViewMarca.setText("Marca: " + marca);

        int idImagen = getResources().getIdentifier(nombreImagen, "drawable", getPackageName());
        imageViewProducto.setImageResource(idImagen);
    }
}