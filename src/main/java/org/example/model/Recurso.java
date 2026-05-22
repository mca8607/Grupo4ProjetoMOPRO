package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Recurso implements Pesquisavel, Serializable {
    private static final long serialVersionUID = 1L;
    private String titulo;
    private String descricao;
    private String dataLancamento;
    private List<Genero> generos;

    public Recurso(String titulo, String descricao, String dataLancamento, List<Genero> generos) {
        if (generos == null || generos.isEmpty()) {
            throw new IllegalArgumentException("Um recurso tem de ter pelo menos um género associado.");
        }
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLancamento = dataLancamento;
        this.generos = new ArrayList<>(generos);
    }

    public String getTitulo() { return titulo; }
    public String getDataLancamento() { return dataLancamento; }
    public List<Genero> getGeneros() { return generos; }

    public void adicionarGenero(Genero g) {
        if (!generos.contains(g)) {
            generos.add(g);
        }
    }

    @Override
    public boolean correspondePesquisa(String texto) {
        if (texto == null || this.titulo == null) return false;
        // Verifica se o título contém o texto pesquisado (ignora maiúsculas/minúsculas)
        return this.titulo.toLowerCase().contains(texto.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", titulo, dataLancamento, descricao);
    }
}