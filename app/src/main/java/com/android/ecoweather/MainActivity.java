package com.android.ecoweather;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        textViewTemperatura.setText(temp + "°C");
        if(temp > 30) {
            textViewTipoClima.setText("Ensolarado");
        } else if (temp < 30 && temp > 20) {
            textViewTipoClima.setText("Parcialmente Ensolarado");
        } else {
            textViewTipoClima.setText("Nublado");
        }

        contTemperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this,
                        ClimaActivity.class);
                startActivity(it);
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,
                        ProfileActivity.class);
                startActivity(it);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this,
                        LembreteActivity.class);
                startActivity(it);
            }
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