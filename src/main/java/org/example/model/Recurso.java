package org.example.model;

public abstract class Recurso implements Pesquisavel {
    private String titulo;
    private String descricao;
    private String dataLancamento;

    public Recurso(String titulo, String descricao, String dataLancamento) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLancamento = dataLancamento;
    }

    public String getTitulo() { return titulo; }

    // Implementação da interface Pesquisavel
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