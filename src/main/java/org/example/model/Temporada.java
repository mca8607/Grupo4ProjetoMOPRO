package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Temporada{
    private List<Episodios> episodios;

    public Temporada (String titulo, String descricao, String anoFinal) {
        this.episodios = new ArrayList<>();

    }

    public List<Episodios> getEpisodios(){
        return episodios;
    }

    public void adicionarEpisodio(Episodios e) {
        this.episodios.add(e);
    }
}