package com.example.tiendaonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityRegistrarUsuario extends AppCompatActivity {

    EditText Nom, Ape, Usu, Con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            Nom = findViewById(R.id.NombreUsuReg);
            Ape = findViewById(R.id.ApellidoUsuReg);
            Usu = findViewById(R.id.UsuarioUsuReg);
            Con = findViewById(R.id.contraUsuReg);
            return insets;
        });
    }
    public void GoMapReg(View Vista)
    {
        Intent obj = new Intent(this, MapsRegistrarUsuario.class);
        obj.putExtra("nombre", Nom.getText().toString());
        obj.putExtra("apellido", Ape.getText().toString());
        obj.putExtra("usuario", Usu.getText().toString());
        obj.putExtra("contra", Con.getText().toString());
        startActivity(obj);
    }
}