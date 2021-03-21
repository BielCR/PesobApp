package com.example.pesobapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageButton btnFogo, btnInf;

    //atributos relacionados a posicao
    private LocationListener gpsObservador;
    private Location posicaoAtual;
    private LocationManager configGPS;
    private double lat, longi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting");

        //recebendo os valores do gps
        configuraGPS();



        //iniciando a intent
        Intent fogo = new Intent(this, TelaFogo.class);
        fogo.putExtra("latitude", lat);
        fogo.putExtra("longitude", longi);
        btnFogo = (ImageButton) findViewById(R.id.fogoBtn);
        btnFogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(fogo);
            }
        });

        btnInf = (ImageButton) findViewById(R.id.infoBtn);
        Intent inf = new Intent(this, InfoBrigada.class);
        inf.putExtra("latitude", lat);
        inf.putExtra("longitude", longi);
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
                posicaoAtual = location;
                lat = posicaoAtual.getLatitude();
                longi = posicaoAtual.getLongitude();

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
            ActivityCompat.requestPermissions(MainActivity.this, new String[]
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


}