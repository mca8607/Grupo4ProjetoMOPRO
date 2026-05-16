package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Serie extends Recurso{
    private List<Temporada> temporadas;
    public Serie (String titulo, String descricao, String anoFinal) {
        super(titulo, descricao, anoFinal);
        this.temporadas = new ArrayList<>();
    }

    public void adicionarTemporadas(Temporada t) {
        this.temporadas.add(t);
    }
}
