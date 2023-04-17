package com.android.mwli;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View inicio = findViewById(R.id.containerMain);
        View contTemp = findViewById(R.id.contTemperatura);
        ImageButton ibtnMenu = findViewById(R.id.menuButton);
        ImageButton ibtnSearch = findViewById(R.id.searchButton);

        contTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inicio.setBackgroundResource(R.color.black);
            }
        });

    }
}