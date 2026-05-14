package org.example.model;

public abstract class Recurso {
    private String titulo;
    private String descricao;
    private String dataLancamento;

    public Recurso(String titulo, String descricao, String dataLancamento) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLancamento = dataLancamento;
    }
}

