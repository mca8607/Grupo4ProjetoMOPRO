package org.example.model;

import org.example.utils.Data;
import java.io.Serializable;

/**
 * Representa um ator que pode participar em filmes e episódios.
 * Implementa {@link Pesquisavel} para suporte à pesquisa por nome.
 * Implementa {@link Serializable} para persistência de dados.
 */
public class Ator implements Pesquisavel, Serializable {
    /** Nome do ator. */
    private String nome;
    /** Data de nascimento do ator. */
    private Data dataNascimento;

    /**
     * Cria um novo ator.
     * @param nome            nome do ator
     * @param dataNascimento  data de nascimento do ator
     */
    public Ator(String nome, Data dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    /**
     * Devolve o nome do ator.
     * @return nome do ator
     */
    public String getNome() {
        return nome;
    }


    /**
     * Conta o número de filmes em que este ator participou,
     * verificando os recursos da base de dados fornecida.
     *
     * @param db a base de dados onde procurar
     * @return número de filmes em que o ator participou
     */
    public int getNumFilmes(DB db) {
        int count = 0;
        for (Recurso r : db.getLstRecursos()) {
            if (r instanceof Filme) {
                Filme f = (Filme) r;
                if (f.temAtor(this)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Verifica se o nome do ator contém o texto pesquisado,
     * ignorando maiúsculas e minúsculas.
     * @param texto texto a pesquisar
     * @return true se o nome contiver o texto, false caso contrário
     */
    @Override
    public boolean correspondePesquisa(String texto) {
        if (texto == null || this.nome == null) return false;

        return this.nome.toLowerCase().contains(texto.toLowerCase());
    }

    /**
     * Devolve uma representação textual do ator com nome e data de nascimento.
     * @return string com o nome e a data de nascimento do ator
     */
    @Override
    public String toString() {
        return nome + " [" + dataNascimento + "]";
    }

    /**
     * Verifica se o ator tem o nome indicado.
     * @param nome nome a comparar
     * @return true se o nome coincidir, false caso contrário
     */
    public boolean temNome(String nome) {
        return this.nome.equals(nome);
    }
}