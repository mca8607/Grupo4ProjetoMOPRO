package org.example.model;

/**
 * Interface que define o contrato para objetos pesquisáveis por texto.
 * Implementada por {@link Ator} (pesquisa por nome)
 * e {@link Recurso} (pesquisa por título).
 */
public interface Pesquisavel {
    /**
     * Verifica se este objeto corresponde ao texto pesquisado.
     * A comparação deve ignorar maiúsculas e minúsculas.
     *
     * @param texto texto a pesquisar
     * @return true se o objeto corresponder ao texto, false caso contrário
     */
    boolean correspondePesquisa(String texto);
}