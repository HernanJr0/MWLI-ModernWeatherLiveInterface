package com.android.ecoweather;

public class Noticia {
    private String titulo;
    private String descricao;
    private String conteudo;
    private String imagem;

    public Noticia(String titulo, String descricao, String conteudo, String urlImagem) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.conteudo = conteudo;
        this.imagem = urlImagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getUrlImagem() {
        return imagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.imagem = urlImagem;
    }
}
