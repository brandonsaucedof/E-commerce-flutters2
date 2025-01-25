package com.example.tiendaonline;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendaonline.SOPORTE.CBaseDatos;
import com.example.tiendaonline.SOPORTE.CPagos;
import com.example.tiendaonline.SOPORTE.CarritoListAdapter;
import com.example.tiendaonline.SOPORTE.CarritoManager;
import com.example.tiendaonline.SOPORTE.Producto;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityVenta extends AppCompatActivity {

    EditText numTar, fecVenTar, codTar;
    TextView pro, proPre, tot;
    int codUsu;
    int [][] codigosCarrito;
    String[][] DatosDeFactura;
    int codVenta;
    int totalP = 0;
    CPagos pago = new CPagos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_venta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            numTar = findViewById(R.id.numTarjeta);
            numTar.setText("");
            fecVenTar = findViewById(R.id.fechaVenTarjeta);
            fecVenTar.setText("");
            codTar = findViewById(R.id.codTarjeta);
            codTar.setText("");
            pro = findViewById(R.id.Productos);
            pro.setText("");
            proPre = findViewById(R.id.precioProductos);
            proPre.setText("");
            tot = findViewById(R.id.DineroPorPagar);
            tot.setText("");
            codUsu = getIntent().getExtras().getInt("codUsu");
            DatosDeFactura = pago.BuscarDatosFacturacion(this);


            for (int p = 0; p<DatosDeFactura.length ; p++)
            {
                String listaProducto = pro.getText().toString();
                pro.setText(listaProducto + DatosDeFactura[p][0] + "\n");
                String listaPrecio = proPre.getText().toString();
                proPre.setText(listaPrecio + DatosDeFactura[p][1] + "\n");

                totalP = totalP + Integer.parseInt(DatosDeFactura[p][2]);
                tot.setText("");
                tot.setText(totalP+"");
            }


            return insets;
        });
    }
    public void Pagar(View Vista)
    {
        Toast.makeText(this, "El cod de usu es: "+codUsu, Toast.LENGTH_LONG).show();
        Calendar calendar = Calendar.getInstance();
        Date fechaActual = calendar.getTime();

        // Formatear la fecha en AA/MM/DD
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        String fechaFormateada = formatter.format(fechaActual);
        Toast.makeText(this, "La fecha actual es: "+fechaFormateada, Toast.LENGTH_LONG).show();
        codigosCarrito = pago.buscarCodProducto(this);

        int precioTotal = -1;
        int canP;
        int preP;
        for (int a=0 ; a < codigosCarrito.length; a++)
        {
            preP = pago.buscarDatosProducto(this, codigosCarrito[a][1]);
            canP = codigosCarrito[a][2];
            precioTotal = precioTotal + (preP * canP);
        }
        Toast.makeText(this, "El precio total es: "+precioTotal, Toast.LENGTH_LONG).show();
        pago.GuardarVenta(this, fechaFormateada, precioTotal,codUsu);

        codVenta = pago.buscarCodVenta(this);
        Toast.makeText(this, "El codVenta es: "+codVenta, Toast.LENGTH_LONG).show();
        for (int i=0 ; i < codigosCarrito.length; i++)
        {
            int precioProductoActual = pago.buscarDatosProducto(this, codigosCarrito[i][1]);
            pago.GuardarDetalleVenta(this, codigosCarrito[i][2], precioProductoActual, codVenta, codigosCarrito[i][1]);
        }
        String[] datosUsuario = pago.buscarUsuario(this, codUsu);
        Toast.makeText(this, "El nombre del usuario es: "+datosUsuario[1], Toast.LENGTH_LONG).show();
        pago.Vector_Archivo(this, codVenta, fechaFormateada, datosUsuario[0], datosUsuario[1], datosUsuario[2], codUsu);
        pago.limpiarCarrito(this);

    }
    public void VerificarTarjeta(View Vista)
    {
        Boolean ver = false;
        if(numTar.getText().toString().length() >= 8 && codTar.getText().toString().length() >= 3 && fecVenTar.getText().toString().length() >= 5)
        {
            ver = true;
            Toast.makeText(this, "Cumple con los requisitos minimos para ser una tarjeta de credito", Toast.LENGTH_SHORT).show();
        }
    }
}