package com.android.ecoweather;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTemperatura, textViewTipoClima;
    private FloatingActionButton floatingActionButton;
    private ConstraintLayout contTemperatura;
    private ImageButton menuButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IniciarComponentes();

        long temp = Math.round(Math.random()*20)+15;
        textViewTemperatura.setText(temp + "ºC");
        if(temp > 30) {
            textViewTipoClima.setText("Ensolarado");
        } else if (temp < 30 && temp > 20) {
            textViewTipoClima.setText("Parcialmente Ensolarado");
        } else {
            textViewTipoClima.setText("Nublado");
        }

        contTemperatura.setOnClickListener(v -> {
            Intent it = new Intent(this, ClimaActivity.class);
            startActivity(it);
        });

        menuButton.setOnClickListener(v -> {
            Intent it = new Intent(this,  ProfileActivity.class);
            startActivity(it);
        });

        floatingActionButton.setOnClickListener(v -> {
            Intent it = new Intent(this, Tome.class);
            startActivity(it);
        });
    }

    private void IniciarComponentes() {
        textViewTemperatura = findViewById(R.id.textViewTemperatura);
        textViewTipoClima = findViewById(R.id.textViewTipoClima);
        contTemperatura = findViewById(R.id.contTemperatura);
        menuButton = findViewById(R.id.menuButton);
        floatingActionButton = findViewById(R.id.fabinho);
    }
}