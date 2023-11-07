package com.android.ecoweather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<Noticia> noticias;

    public void setNewsList(List<Noticia> noticias) {
        this.noticias = noticias;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticia, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Noticia noticia = noticias.get(position);

        holder.textViewTitulo.setText(noticia.getTitulo());
        holder.textViewDescricao.setText(noticia.getDescricao());

        RequestOptions requestOptions = new RequestOptions().error(R.drawable.img_error);

        Glide.with(holder.itemView.getContext())
                .load(noticia.getUrlImagem())
                .apply(requestOptions)
                .into(holder.imageViewNoticia);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetalhesNoticiaActivity.class);

                Noticia noticia = noticias.get(position);
                intent.putExtra("titulo", noticia.getTitulo());
                intent.putExtra("descricao", noticia.getDescricao());
                intent.putExtra("conteudo", noticia.getConteudo());
                intent.putExtra("imageUrl", noticia.getUrlImagem());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticias != null ? noticias.size() : 0;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitulo;
        TextView textViewDescricao;
        ImageView imageViewNoticia;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewDescricao = itemView.findViewById(R.id.textViewDescricao);
            imageViewNoticia = itemView.findViewById(R.id.imageViewNoticia);
        }
    }
}
