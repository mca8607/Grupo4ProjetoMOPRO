package org.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma série na plataforma.
 * Herda de {@link Recurso} e agrega uma lista de {@link Temporada}.
 * Cada temporada contém os seus episódios.
 */
public class Serie extends Recurso{

    /** Lista de temporadas desta série. */
    private List<Temporada> temporadas;


    /**
     * Cria uma nova série.
     * @param titulo    título da série
     * @param descricao descrição ou sinopse
     * @param anoFinal  ano de lançamento
     * @param generos   lista de géneros (mínimo um)
     */
    public Serie (String titulo, String descricao, String anoFinal, List<Genero> generos) {
        super(titulo, descricao, anoFinal, generos);
        this.temporadas = new ArrayList<>();
    }

    /**
     * Adiciona uma temporada à série.
     * @param t temporada a adicionar
     */
    public void adicionarTemporadas(Temporada t) {
        this.temporadas.add(t);
    }


    /**
     * Exibe a ficha da série no ecrã com o título.
     */
    public void exibeFicha(){
        System.out.println(getTitulo());
    }


    /**
     * Devolve a lista de temporadas da série.
     * @return lista de temporadas
     */
    public List<Temporada> getTemporadas() {
        return temporadas;
    }

}
