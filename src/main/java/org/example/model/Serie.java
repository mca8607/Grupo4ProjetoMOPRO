package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Serie extends Recurso{
    private List<Temporada> temporadas;
    public Serie (String titulo, String descricao, String anoFinal, List<Genero> generos) {
        super(titulo, descricao, anoFinal, generos);
        this.temporadas = new ArrayList<>();
    }

    public void adicionarTemporadas(Temporada t) {
        this.temporadas.add(t);
    }
    public void exibeFicha(){
        System.out.println(getTitulo());
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }
}
