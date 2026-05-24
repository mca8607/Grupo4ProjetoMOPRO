package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que representa um recurso da plataforma (Filme ou Série).
 * Implementa {@link Pesquisavel} para suporte à pesquisa por título.
 * Implementa {@link Serializable} para persistência de dados.
 * Todo o recurso deve ter pelo menos um género associado.
 */
public abstract class Recurso implements Pesquisavel, Serializable {
    private static final long serialVersionUID = 1L;

    /** Título do recurso. */
    private String titulo;

    /** Descrição ou sinopse do recurso. */
    private String descricao;

    /** Ano de lançamento do recurso. */
    private String dataLancamento;

    /** Lista de géneros associados ao recurso (mínimo um). */
    private List<Genero> generos;


    /**
     * Cria um novo recurso.
     *
     * @param titulo         título do recurso
     * @param descricao      descrição ou sinopse
     * @param dataLancamento ano de lançamento
     * @param generos        lista de géneros (não pode ser null nem vazia)
     * @throws IllegalArgumentException se a lista de géneros for null ou vazia
     */
    public Recurso(String titulo, String descricao, String dataLancamento, List<Genero> generos) {
        if (generos == null || generos.isEmpty()) {
            throw new IllegalArgumentException("Um recurso tem de ter pelo menos um género associado.");
        }
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLancamento = dataLancamento;
        this.generos = new ArrayList<>(generos);
    }

    /**
     * Devolve o título do recurso.
     * @return título
     */
    public String getTitulo() { return titulo; }

    /**
     * Devolve o ano de lançamento do recurso.
     * @return ano de lançamento
     */
    public String getDataLancamento() { return dataLancamento; }

    /**
     * Devolve a lista de géneros associados ao recurso.
     * @return lista de géneros
     */
    public List<Genero> getGeneros() { return generos; }


    /**
     * Adiciona um género ao recurso, se ainda não estiver associado.
     * @param g género a adicionar
     */

    public void adicionarGenero(Genero g) {
        if (!generos.contains(g)) {
            generos.add(g);
        }
    }

    /**
     * Verifica se o título do recurso contém o texto pesquisado,
     * ignorando maiúsculas e minúsculas.
     * @param texto texto a pesquisar
     * @return true se o título contiver o texto, false caso contrário
     */
    @Override
    public boolean correspondePesquisa(String texto) {
        if (texto == null || this.titulo == null) return false;
        // Verifica se o título contém o texto pesquisado (ignora maiúsculas/minúsculas)
        return this.titulo.toLowerCase().contains(texto.toLowerCase());
    }


    /**
     * Devolve uma representação textual do recurso com o título, ano de lançamento e descrição.
     * @return string com os dados do recurso
     */
    @Override
    public String toString() {
        return String.format("%s (%s) - %s", titulo, dataLancamento, descricao);
    }
}