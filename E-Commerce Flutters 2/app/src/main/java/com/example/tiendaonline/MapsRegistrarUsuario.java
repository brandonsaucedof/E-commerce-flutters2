package com.example.tiendaonline;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tiendaonline.databinding.ActivityMapsRegistrarUsuarioBinding;

public class MapsRegistrarUsuario extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsRegistrarUsuarioBinding binding;
    CUsuarios us = new CUsuarios();
    CEncriptar enc = new CEncriptar();
    LatLng Coor;
    View Vista1;
    Button btnGua;
    String nom, ape, usu, con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsRegistrarUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnGua = findViewById(R.id.btnGuardar);

        nom =getIntent().getExtras().getString("nombre");
        ape =getIntent().getExtras().getString("apellido");
        usu =getIntent().getExtras().getString("usuario");
        con =getIntent().getExtras().getString("contra");

        btnGua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Guardar(Vista1);
            }
        });
    }
    public void GoHome(View Vista)
    {
        Intent Obj = new Intent(this, MainActivity.class);
        startActivity(Obj);
    }

    public void Guardar(View Vista)
    {
        String conEn = enc.encryptString(con);
        us.Guardar(this, nom, ape, usu, conEn, Coor.latitude, Coor.longitude);
        GoHome(Vista);
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

        //Zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //Obt un lat y lon
        Coor = new LatLng(-17.757414244844448, -63.17400632438509);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Coor));
        //Enfocar camara
        CameraPosition Camara = new CameraPosition.Builder().target(Coor).zoom(11).bearing(90).tilt(45).build();
        //aplicar
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(Camara));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                googleMap.clear();
                Coor = latLng;
                //Crear una marca
                MarkerOptions Marca = new MarkerOptions();
                Marca.position(Coor);
                Marca.title(usu+"");
                Marca.snippet(nom + ape);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(Coor));
                googleMap.addMarker(Marca);
            }
        });
    }
}