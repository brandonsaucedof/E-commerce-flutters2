package com.example.tiendaonline;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tiendaonline.databinding.ActivityMapaTodoLosUsuariosBinding;

import java.nio.channels.FileChannel;

public class MapaTodoLosUsuarios extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapaTodoLosUsuariosBinding binding;
    String [][] Usuario;
    LatLng coor;
    int nro;
    View Vista1;
    Button btnReg;
    CUsuarios usu = new CUsuarios();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapaTodoLosUsuariosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Usuario = usu.cargarMarcadores(this);
        nro = Usuario.length;
        btnReg = findViewById(R.id.btnRegresar);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoHome(Vista1);
            }
        });
    }
    public void GoHome(View Vista)
    {
        Intent Obj = new Intent(this, MainActivity.class);
        startActivity(Obj);
    }
    public void crearMarcadores(GoogleMap googleMap, int i2, String[][] d)
    {

        for (int i=0 ; i<i2 ; i++)
        {
            LatLng sydney = new LatLng(Double.parseDouble(d[i][5]), Double.parseDouble(d[i][6]));
            if(d[i][3].equals("admin"))
            {
                mMap.addMarker(new MarkerOptions().position(sydney).title(d[i][1] +" "+ d[i][2]).snippet("Usuario: " + d[i][3] + "\n" + d[i][5]+d[i][6]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            }
            else
            {
                mMap.addMarker(new MarkerOptions().position(sydney).title(d[i][1] +" " + d[i][2]).snippet("Usuario: " + d[i][3] + "\n" + d[i][5]+d[i][6]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            }

        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        coor = new LatLng(Double.parseDouble(Usuario[0][5]), Double.parseDouble(Usuario[0][6]));
        CameraPosition Camara = new CameraPosition.Builder().target(coor).zoom(13).bearing(90).tilt(45).build();
        //aplicar
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(Camara));

        crearMarcadores(mMap, nro, Usuario);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coor));


    }
}