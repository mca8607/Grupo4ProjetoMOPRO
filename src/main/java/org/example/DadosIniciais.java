package org.example;

import org.example.model.*;
import org.example.utils.Data;
import java.util.ArrayList;
import java.util.List;

public class DadosIniciais {

    public static void carregar(DB db) {
        List<Genero> generosAcao = new ArrayList<>();
        generosAcao.add(Genero.ACTION);
        generosAcao.add(Genero.THRILLER);

        List<Genero> generosDrama = new ArrayList<>();
        generosDrama.add(Genero.DRAMA);

        Ator ator1 = new Ator("Leonardo DiCaprio", new Data(1974, 11, 11));
        Ator ator2 = new Ator("Marlon Brando", new Data(1924, 4, 3));

        db.adicionarAtor(ator1);
        db.adicionarAtor(ator2);

        Filme inception = new Filme("Inception", "Sonhos dentro de sonhos", "2010", "148 min", generosAcao);
        Filme padrinho = new Filme("O Padrinho", "Máfia italiana", "1972", "175 min", generosDrama);

        db.adicionarRecurso(inception);
        db.adicionarRecurso(padrinho);
    }
}