package org.example.model;

import org.example.utils.Data;

public class Ator implements Pesquisavel {
    private String nome;
    private Data dataNascimento;

    public Ator(String nome, Data dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public int getNumFilmes(DB db) {
        int count = 0;
        for (Recurso r : db.getLstRecursos()) {
            if (r instanceof Filme) {
                if (r.correspondePesquisa(this.nome)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public boolean correspondePesquisa(String texto) {
        if (texto == null || this.nome == null) return false;

        return this.nome.toLowerCase().contains(texto.toLowerCase());
    }

    @Override
    public String toString() {
        return nome + " [" + dataNascimento + "]";
    }

    public boolean temNome(String nome) {
        return this.nome.equals(nome);
    }
}