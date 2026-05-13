package org.example.model;

import org.example.utils.Data;

public class Ator {
    private String nome;
    private Data dataNascimento;

    public Ator(String nome, Data dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return nome + " [" + dataNascimento + "]";
    }

    public boolean temNome(String nome) {return this.nome.equals(nome);}
}
