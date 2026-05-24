package org.example.model;
/**
 * Classe abstrata que representa um conteúdo multimédia com duração.
 * Serve de base para futuras extensões do modelo de dados.
 */
public abstract class Media {

    /** Duração do conteúdo em minutos. */
    private int duracao;

    /**
     * Cria um novo conteúdo multimédia com a duração indicada.
     * @param duracao duração em minutos
     */
    public Media(int duracao) {
        this.duracao = duracao;
    }

    /**
     * Devolve a duração do conteúdo em minutos.
     * @return duração em minutos
     */
    public int getDuracao() {
        return duracao;
    }
}
