package com.example.tiendaonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText usu, con;
    String conEncriptada;
    CEncriptar enc = new CEncriptar();
    CUsuarios us = new CUsuarios();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            usu = findViewById(R.id.et_username);
            con = findViewById(R.id.et_password);

            return insets;
        });
    }

    public void Login(View Vista)
    {

        if (us.BuscarUsuario(this, usu.getText().toString(), con.getText().toString())) {
            Toast.makeText(this, "Logeo exitoso", Toast.LENGTH_LONG).show();
            Intent obj2 = new Intent(this, ActivityCatalogo.class);
            obj2.putExtra("codUsu", us.buscarCodUsuario(this, usu.getText().toString(), con.getText().toString()));
            startActivity(obj2);
            if(usu.getText().toString().equals("admin"))
            {
                Intent obj = new Intent(this, MapaTodoLosUsuarios.class);
                startActivity(obj);

            }
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
        }
    }

    public void GoRegistrar(View Vista)
    {
        Intent obj = new Intent(this, ActivityRegistrarUsuario.class);
        startActivity(obj);
    }
}