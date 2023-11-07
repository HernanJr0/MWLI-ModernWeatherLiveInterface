package com.android.ecoweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetalhesNoticiaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_noticia);

        Intent intent = getIntent();
        String titulo = intent.getStringExtra("titulo");
        String descricao = intent.getStringExtra("descricao");
        String conteudo = intent.getStringExtra("conteudo");
        String imageUrl = intent.getStringExtra("imageUrl");

        ImageButton imageButton = findViewById(R.id.backButton);
        ImageView imageView = findViewById(R.id.imageViewDetalhesNoticia);
        TextView tituloTextView = findViewById(R.id.textViewTituloDetalhesNoticia);
        TextView descricaoTextView = findViewById(R.id.textViewDescricaoDetalhesNoticia);
        TextView conteudoTextView = findViewById(R.id.textViewConteudoDetalhesNoticia);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.logo_1)
                .error(R.drawable.img_error);

        Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);

        tituloTextView.setText(titulo);
        conteudoTextView.setText(conteudo);
        descricaoTextView.setText(descricao);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}