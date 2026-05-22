package org.example.model;

import java.io.Serializable;

public enum Genero implements Serializable {
    ACTION("Action"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    HORROR("Horror"),
    SCI_FI("Sci-Fi"),
    THRILLER("Thriller"),
    ROMANCE("Romance"),
    DOCUMENTARY("Documentary");

    private final String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}