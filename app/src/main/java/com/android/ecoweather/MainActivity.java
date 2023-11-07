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

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

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

        long temp = Math.round(Math.random() * 20) + 15;
        textViewTemperatura.setText(temp + "ºC");
        if (temp > 30) {
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
            Intent it = new Intent(this, ProfileActivity.class);
            startActivity(it);
        });

        floatingActionButton.setOnClickListener(v -> {
            Intent it = new Intent(this, Tome.class);
            startActivity(it);
        });

        String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "News.json");
        Log.i("data", "" + jsonFileString);

        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<Noticia>>() {
        }.getType();

        List<Noticia> news = gson.fromJson(jsonFileString, listUserType);
        for (int i = 0; i < news.size(); i++) {
            Log.i("data",
                    "> Item " + i +
                            "\n" + news.get(i) // valor de cada propriedade do objeto
            );
        }

    }

    private void IniciarComponentes() {
        textViewTemperatura = findViewById(R.id.textViewTemperatura);
        textViewTipoClima = findViewById(R.id.textViewTipoClima);
        contTemperatura = findViewById(R.id.contTemperatura);
        menuButton = findViewById(R.id.menuButton);
        floatingActionButton = findViewById(R.id.fabinho);
    }
}