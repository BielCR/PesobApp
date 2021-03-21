package com.example.pesobapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class InfoBrigada extends AppCompatActivity {
    private static final String TAG = "InfoBrigada";
    private ImageButton btnFogo, btnHome;
    private double lat, longi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_brigada);
        Log.d(TAG, "onCreate: Starting");

        //pegando os valores da intent
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitude", 0);
        longi = intent.getDoubleExtra("longitude", 0);

        //iniciando a intent
        Intent fogo = new Intent(this, TelaFogo.class);
        fogo.putExtra("latitude", lat);
        fogo.putExtra("longitude",longi);
        btnFogo = (ImageButton) findViewById(R.id.fogoBtn);
        btnFogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(fogo);
            }
        });
        btnHome = (ImageButton) findViewById(R.id.homeBtn);
        Intent home = new Intent(this, MainActivity.class);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(home);
            }
        });


    }


}