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

    /**
     * Cria um género com a descrição indicada.
     *
     * @param descricao descrição do género
     */
    Genero(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Devolve a descrição do género.
     * @return descrição do género
     */
    public String getDescricao() {
        return descricao;
    }
    /**
     * Devolve a descrição do género como representação textual.
     * @return descrição do género
     */
    @Override
    public String toString() {
        return descricao;
    }
}