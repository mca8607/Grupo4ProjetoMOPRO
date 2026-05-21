package org.example.model;

import java.util.Objects;

public class Filme extends Recurso {
    private String duracao;

    public Filme(String titulo, String descricao, String dataLancamento, String duracao) {
        super(titulo, descricao, dataLancamento);
        this.duracao = duracao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Filme filme = (Filme) o;
        return Objects.equals(duracao, filme.duracao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), duracao);
    }


    @Override
    public String toString() {
        return super.toString() + " [Duração: " + duracao + "]";
    }
}