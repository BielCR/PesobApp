package com.example.pesobapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class TelaFogo extends AppCompatActivity implements OnMapReadyCallback {
    private ImageButton btnHome, btnInf;
    private TextView gpsText;
    private Button testeBtn;

    //atributos relacionados a posicao
    private LocationListener gpsObservador;
    private Location posicaoAtual;
    private LocationManager configGPS;
    private double lat, longi;

    //atributos do google maps
    private GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_fogo);

        //pegando os valores da intent
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitude", 0);
        longi = intent.getDoubleExtra("longitude", 0);

        gpsText = (TextView) findViewById(R.id.textoGps);
        testeBtn = (Button) findViewById(R.id.btnteste);
        configuraGPS();
        mostraMapa();

        //iniciando as intents
        btnHome = (ImageButton) findViewById(R.id.homeBtn);
        Intent home = new Intent(this, MainActivity.class);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(home);
            }
        });

        btnInf = (ImageButton) findViewById(R.id.infoBtn);
        Intent inf = new Intent(this, InfoBrigada.class);
        btnInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(inf);
            }
        });


    }

    private void configuraGPS() {
        gpsObservador = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lat = location.getLatitude();
                longi = location.getLongitude();
                gpsText.setText(location.getLatitude() + " / " + location.getLongitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };

        configGPS = (LocationManager) getSystemService(LOCATION_SERVICE);

        //verificando se possui permissoes para gps
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TelaFogo.this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        } else {
            iniciaGPS();
        }
    }

    //permissao concedida
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            iniciaGPS();
        }
    }

    @SuppressLint("MissingPermission")
    private void iniciaGPS() {
        configGPS.requestLocationUpdates(configGPS.GPS_PROVIDER, 1000, (float) 2, gpsObservador);

    }

    private void configuraGMaps() {
        SupportMapFragment fragmentomapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaGoogle);
        fragmentomapa.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googlemap) {
        googleMap = googlemap;

        LatLng atual = new LatLng(lat, longi);
        googleMap.addMarker(new MarkerOptions().position(atual));
        CameraPosition c = new CameraPosition(atual, 15, 0, 0);
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(c));
    }

    private void mostraMapa() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                configuraGMaps();
            }
        }, 5000);
    }
}