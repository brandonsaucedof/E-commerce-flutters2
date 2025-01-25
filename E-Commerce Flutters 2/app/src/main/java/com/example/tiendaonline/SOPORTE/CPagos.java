package com.example.tiendaonline.SOPORTE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.OutputStreamWriter;

public class CPagos {
    int Vis=0;
    public String Archivo = "Factura.txt";
    public void Vector_Archivo(Context Contexto, int codVenta, String fec, String Usu, String Nom, String Ape, int CodUsu )
    {
        try
        {

            //dar datos de venta generales

            OutputStreamWriter obj = new OutputStreamWriter(Contexto.openFileOutput(Vis+"_"+Archivo, Context.MODE_APPEND));
            obj.write("-------------------------- FACTURA --------------------------" + "\n" +
                          "Compra realizada en la fecha: " + fec + "\n" +
                          "Por el usuario: " + Usu + "\n" +
                          "------------------------------------------------------------" + "\n");






            //Ingresar productos a la factura
            CBaseDatos Obj1 = new CBaseDatos(Contexto,"dbtiendaFinal",null,1);
            SQLiteDatabase Obj2 = Obj1.getReadableDatabase();


            Cursor Lista = Obj2.rawQuery("Select d.codDetalle, p.producto, d.precioProducto, v.precioTotal  from DetalleVenta d join Venta v on d.codVenta = v.codVenta join Productos p on d.codProducto = p.codProducto where d.codVenta = "+ codVenta, null);
            int NroF = Lista.getCount();

            String[][] Datos = new String[NroF][4];
            int VisDato = 0;

            if(Lista.moveToFirst())
            {
                do
                {

                    Datos[VisDato][0] = Lista.getString(0);
                    Datos[VisDato][1] = Lista.getString(1);
                    Datos[VisDato][2] = Lista.getString(2);
                    Datos[VisDato][3] = Lista.getString(3);


                    obj.write(Datos[VisDato][0] + " " + Datos[VisDato][1] + "                                " + Datos[VisDato][2] + "\n");
                    VisDato++;



                } while (Lista.moveToNext());
                obj.write("------------------------------------------------------------" + "\n");
                obj.write("Precio Total:                                   " + Datos[0][3] + "\n");
                obj.write("                                                            " + "\n");
                obj.write("                                                            " + "\n");
                obj.write(" Esta factura es de prueba" + "\n");
            }






            obj.flush();
            obj.close();
            Obj2.close();
            //para ver donde se guarda
            Log.d("TAG1", "Fichero guardado en: " + Contexto.getFilesDir() + "/" + Vis+"_"+Archivo);
            Vis++;



        }
        catch(Exception ex){}
    }
    public void GuardarDetalleVenta(Context Contexto, int can, int precioProducto, int codVen, int codPro)
    {
        CBaseDatos Obj1 = new CBaseDatos(Contexto,"dbtiendaFinal",null,1);
        SQLiteDatabase Obj2 = Obj1.getWritableDatabase();
        ContentValues Reg = new ContentValues();
        Reg.put("cantidad", can);
        Reg.put("precioProducto", precioProducto);
        Reg.put("codVenta", codVen);
        Reg.put("codProducto", codPro);

        Obj2.insert("DetalleVenta",null,Reg);
        Obj1.close();
    }
    public void GuardarVenta(Context Contexto, String fecha, int precioTotal ,int codUsuario)
    {
        CBaseDatos Obj1 = new CBaseDatos(Contexto,"dbtiendaFinal",null,1);
        SQLiteDatabase Obj2 = Obj1.getWritableDatabase();
        ContentValues Reg = new ContentValues();
        Reg.put("fechaVenta", fecha);
        Reg.put("precioTotal", precioTotal);
        Reg.put("codUsuario", codUsuario);

        Obj2.insert("Venta",null,Reg);
        Obj1.close();
    }
    public int buscarCodVenta(Context Contexto)
    {
        CBaseDatos Obj1 = new CBaseDatos(Contexto, "dbtiendaFinal", null, 1);
        SQLiteDatabase Obj2 = Obj1.getReadableDatabase();
        int maxCodVenta = -1;

        Cursor Lista = Obj2.rawQuery("Select MAX(codVenta) from Venta", null);

        if (Lista.moveToFirst()) {
            maxCodVenta = Lista.getInt(0); // Obtiene el valor de la primera columna
        }

        Lista.close(); // Asegúrate de cerrar el cursor
        Obj2.close();  // Asegúrate de cerrar la base de datos
        return maxCodVenta;
    }
    public int[][] buscarCodProducto(Context Contexto)
    {
        CBaseDatos Obj1 = new CBaseDatos(Contexto, "dbtiendaFinal", null, 1);
        SQLiteDatabase Obj2 = Obj1.getReadableDatabase();

        Cursor Lista = Obj2.rawQuery("Select * from Carrito", null);
        int NRO = Lista.getCount();
        int [][] codigo = new int[NRO][3];
        int VisDato = 0;

        if (Lista.moveToFirst()) {
            do
            {
                codigo[VisDato][0] = Lista.getInt(0);
                codigo[VisDato][1] = Lista.getInt(1);
                codigo[VisDato][2] = Lista.getInt(2);
                VisDato++;

            } while (Lista.moveToNext());
        }

        Lista.close(); // Asegúrate de cerrar el cursor
        Obj2.close();  // Asegúrate de cerrar la base de datos
        return codigo;
    }
    public int buscarDatosProducto(Context Contexto, int codProdu)
    {
        CBaseDatos Obj1 = new CBaseDatos(Contexto, "dbtiendaFinal", null, 1);
        SQLiteDatabase Obj2 = Obj1.getReadableDatabase();

        String[] args = {String.valueOf(codProdu)};

        Cursor Lista = Obj2.rawQuery("Select * from Productos where codProducto = ?", args);

        int precio = -1;


        if (Lista.moveToFirst()) {
            do
            {
                precio = Lista.getInt(3);

            } while (Lista.moveToNext());
        }

        Lista.close(); // Asegúrate de cerrar el cursor
        Obj2.close();  // Asegúrate de cerrar la base de datos
        return precio;
    }
    public String[] buscarUsuario(Context Contexto, int codUsu)
    {
        CBaseDatos Obj1 = new CBaseDatos(Contexto, "dbtiendaFinal", null, 1);
        SQLiteDatabase Obj2 = Obj1.getReadableDatabase();

        String[] args = {String.valueOf(codUsu)};

        Cursor Lista = Obj2.rawQuery("Select * from Usuario where codUsuario = ?", args);
        int NRO = Lista.getCount();
        String[] usuario = new String[7];
        int VisDato = 0;

        if (Lista.moveToFirst()) {
            do
            {
                usuario[VisDato] = Lista.getString(3);//Usuario
                VisDato++;
                usuario[VisDato] = Lista.getString(1);//Nombre
                VisDato++;
                usuario[VisDato] = Lista.getString(2);//Apellido
                VisDato++;

            } while (Lista.moveToNext());
        }

        Lista.close(); // Asegúrate de cerrar el cursor
        Obj2.close();  // Asegúrate de cerrar la base de datos
        return usuario;
    }

    public void ReducirCan(Context Contexto, int can, int codProducto)
    {
        CBaseDatos Obj1 = new CBaseDatos(Contexto, "dbtiendaFinal", null, 1);
        SQLiteDatabase Obj2 = Obj1.getReadableDatabase();

        String consultaSQL = "UPDATE Productos SET cantidadStock = cantidadStock - ? WHERE codProducto = ?";

        // Ejecuta la consulta SQL
        Obj2.execSQL(consultaSQL, new Object[]{can, codProducto});

        // Cierra la base de datos
        Obj2.close();
    }
    public void limpiarCarrito(Context Contexto)
    {
        CBaseDatos Obj1 = new CBaseDatos(Contexto, "dbtiendaFinal", null, 1);
        SQLiteDatabase Obj2 = Obj1.getReadableDatabase();

        String consultaSQL = "DELETE FROM Carrito";

        // Ejecuta la consulta SQL
        Obj2.execSQL(consultaSQL);

        // Cierra la base de datos
        Obj2.close();
    }

    public String[][] BuscarDatosFacturacion(Context Contexto)
    {
        CBaseDatos Obj1 = new CBaseDatos(Contexto, "dbtiendaFinal", null, 1);
        SQLiteDatabase Obj2 = Obj1.getReadableDatabase();


        Cursor Lista = Obj2.rawQuery("Select p.Producto, p.precioUnidad, (c.Cantidad * p.precioUnidad) from Carrito c join Productos p on c.codProducto = p.codProducto ", null);
        String[][] datosFactura = new String[Lista.getCount()][3];
        int VisDato = 0;

        if (Lista.moveToFirst()) {
            do
            {
                datosFactura[VisDato][0] = Lista.getString(0);
                datosFactura[VisDato][1] = Lista.getString(1);
                datosFactura[VisDato][2] = Lista.getString(2);
                VisDato++;

            } while (Lista.moveToNext());
        }

        Lista.close(); // Asegúrate de cerrar el cursor
        Obj2.close();  // Asegúrate de cerrar la base de datos
        return datosFactura;
    }
}
