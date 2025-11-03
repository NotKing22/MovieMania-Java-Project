package com.moviemania.MovieMania.dto;

public class ReviewDTO {
    private String tituloFilme;
    private int nota;

    public ReviewDTO(String tituloFilme, int nota) {
        this.tituloFilme = tituloFilme;
        this.nota = nota;
    }

    public String getTituloFilme() { return tituloFilme; }
    public int getNota() { return nota; }
}
