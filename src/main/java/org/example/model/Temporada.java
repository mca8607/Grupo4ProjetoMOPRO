package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * Representa uma temporada de uma {@link Serie}.
 * Agrega uma lista de {@link Episodios}.
 */
public class Temporada implements Serializable{
    /** Título da temporada. */
    private String titulo;

    /** Lista de episódios desta temporada. */
    private List<Episodios> episodios;

    /**
     * Cria uma nova temporada.
     * @param titulo    título da temporada
     * @param descricao descrição da temporada
     * @param anoFinal  ano final da temporada
     */
    public Temporada (String titulo, String descricao, String anoFinal) {
        this.episodios = new ArrayList<>();
        this.titulo = titulo;

    }
    /**
     * Devolve a lista de episódios desta temporada.
     * @return lista de episódios
     */
    public List<Episodios> getEpisodios(){
        return episodios;
    }

    /**
     * Devolve o título da temporada.
     * @return título da temporada
     */
    public String getTitulo() {
        return titulo;
    }


    /**
     * Adiciona um episódio a esta temporada.
     * @param e episódio a adicionar
     */
    public void adicionarEpisodio(Episodios e) {
        this.episodios.add(e);
    }

    /**
     * Devolve uma representação textual da temporada com o seu título.
     * @return string com o título da temporada
     */
    @Override
    public String toString() {
        return (titulo != null && !titulo.isBlank()) ? titulo : "Temporada sem título";
    }
}