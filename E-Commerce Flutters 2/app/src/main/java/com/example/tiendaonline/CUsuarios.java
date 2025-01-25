package com.example.tiendaonline;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.tiendaonline.SOPORTE.CBaseDatos;

public class CUsuarios {
    CEncriptar enc = new CEncriptar();
    public Boolean BuscarUsuario(Context Contexto, String usu, String con)
    {
        CBaseDatos Obj1 = new CBaseDatos(Contexto, "dbtiendaFinal", null, 1);
        SQLiteDatabase Obj2 = Obj1.getReadableDatabase();

        String[] Argumentos = new String[] {usu};
        Cursor Lista = Obj2.rawQuery("Select * from Usuario where Usuario = ?", Argumentos);

        boolean credencialesCorrectas = false;

        if (Lista.moveToFirst()) {
            String storedUsername = Lista.getString(3);
            String storedPasswordHash = Lista.getString(4);

            String inputPasswordHash = enc.encryptString(con);

            if (storedUsername.equals(usu) && storedPasswordHash.equals(inputPasswordHash)) {
                credencialesCorrectas = true;
            } else {
                Toast.makeText(Contexto, "Usuario o contraseña incorrecta, " , Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Contexto, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }

        Lista.close();
        Obj2.close();
        return credencialesCorrectas;
    }
    public void Guardar(Context Contexto, String Nom, String Ape, String Usu, String Con, Double lat, Double lon)
    {
        CBaseDatos Obj1 = new CBaseDatos(Contexto,"dbtiendaFinal",null,1);
        SQLiteDatabase Obj2 = Obj1.getWritableDatabase();
        ContentValues Reg = new ContentValues();
        Reg.put("Nombre", Nom);
        Reg.put("Apellido",Ape);
        Reg.put("Usuario", Usu);
        Reg.put("contraseña", Con);
        Reg.put("lat", lat);
        Reg.put("long", lon);

        Obj2.insert("Usuario",null,Reg);
        Obj1.close();
    }

    public String[][] cargarMarcadores(Context Contexto)
    {
        CBaseDatos Obj1 = new CBaseDatos(Contexto,"dbtiendaFinal",null,1);
        SQLiteDatabase Obj2 = Obj1.getReadableDatabase();

        Cursor Lista = Obj2.rawQuery("Select * from Usuario", null);
        int NroF = Lista.getCount();

        String[][] Datos = new String[NroF][7];
        int VisDato = 0;

        if(Lista.moveToFirst())
        {
            do
            {
                Datos[VisDato][0] = Lista.getString(0);
                Datos[VisDato][1] = Lista.getString(1);
                Datos[VisDato][2] = Lista.getString(2);
                Datos[VisDato][3] = Lista.getString(3);
                Datos[VisDato][4] = Lista.getString(4);
                Datos[VisDato][5] = Lista.getString(5);
                Datos[VisDato][6] = Lista.getString(6);
                VisDato++;
            } while (Lista.moveToNext());

        }
        Lista.close();
        return Datos;

    }
    public int buscarCodUsuario(Context Contexto, String usu, String con)
    {
        CBaseDatos Obj1 = new CBaseDatos(Contexto, "dbtiendaFinal", null, 1);
        SQLiteDatabase Obj2 = Obj1.getReadableDatabase();
        int storedCod = 0;

        String[] Argumentos = new String[] {usu};
        Cursor Lista = Obj2.rawQuery("Select * from Usuario where Usuario = ?", Argumentos);

        if (Lista.moveToFirst()) {
            String storedUsername = Lista.getString(3);
            String storedPasswordHash = Lista.getString(4);
            storedCod = Integer.parseInt(Lista.getString(0));

            String inputPasswordHash = enc.encryptString(con);

            if (storedUsername.equals(usu) && storedPasswordHash.equals(inputPasswordHash)) {

            } else {
                Toast.makeText(Contexto, "Usuario o contraseña incorrecta, " , Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Contexto, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }

        Lista.close();
        Obj2.close();
        return storedCod;
    }
}
