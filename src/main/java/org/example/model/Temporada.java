package org.example.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Representa uma temporada de uma {@link Serie}.
 * Agrega uma lista de {@link Episodios}.
 */
public class Temporada{
    private List<Episodios> episodios;


    /**
     * Cria uma nova temporada.
     * @param titulo    título da temporada
     * @param descricao descrição da temporada
     * @param anoFinal  ano final da temporada
     */
    public Temporada (String titulo, String descricao, String anoFinal) {
        this.episodios = new ArrayList<>();

    }
    /**
     * Devolve a lista de episódios desta temporada.
     * @return lista de episódios
     */
    public List<Episodios> getEpisodios(){
        return episodios;
    }

    /**
     * Adiciona um episódio a esta temporada.
     * @param e episódio a adicionar
     */
    public void adicionarEpisodio(Episodios e) {
        this.episodios.add(e);
    }
}