package com.example.tiendaonline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendaonline.SOPORTE.CBaseDatos;
import com.example.tiendaonline.SOPORTE.CarritoListAdapter;
import com.example.tiendaonline.SOPORTE.CarritoManager;
import com.example.tiendaonline.SOPORTE.Producto;

import java.util.List;
public class CarritoActivity extends AppCompatActivity {

    private CBaseDatos baseDatos;
    private ListView listViewCarrito;
    private CarritoListAdapter adapter;
    private List<Producto> productosCarrito;
    private CarritoManager carritoManager;
    Button btnComprar;
    int codUsu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        codUsu = getIntent().getExtras().getInt("codUsu");

        baseDatos = new CBaseDatos(this, "dbtiendaFinal", null, 1);
        carritoManager = new CarritoManager(baseDatos);

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        productosCarrito = carritoManager.obtenerProductosCarrito(db);
        db.close();

        adapter = new CarritoListAdapter(this, R.layout.item_carrito, productosCarrito, baseDatos);
        listViewCarrito = findViewById(R.id.cart_list_view);
        listViewCarrito.setAdapter(adapter);

        btnComprar = findViewById(R.id.btncomprar);

    }

    public void goVenta(View vista) {
        Intent intent = new Intent(CarritoActivity.this, ActivityVenta.class);
        intent.putExtra("codUsu", codUsu);
        startActivity(intent);
    }
}