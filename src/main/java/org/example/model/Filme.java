package org.example.model;

public class Filme extends Recurso {
    private String duracao;

    public Filme(String titulo, String descricao, String dataLancamento, String duracao) {
        super(titulo, descricao, dataLancamento);
        this.duracao = duracao;
    }

    @Override
    public String toString() {
        return super.toString() + " [Duração: " + duracao + "]";
    }
}