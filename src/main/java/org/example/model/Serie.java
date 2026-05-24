package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * Representa uma série na plataforma.
 * Herda de {@link Recurso} e agrega uma lista de {@link Temporada}.
 * Cada temporada contém os seus episódios.
 * Implementa {@link Serializable} para persistência de dados.
 */
public class Serie extends Recurso implements Serializable {

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


    public double getClassificacaoMedia() {
        double soma = 0;
        int count = 0;
        for (Temporada t : temporadas) {
            for (Episodios e : t.getEpisodios()) {
                for (Classificacao c : e.getClassificacoes()) {
                    soma += c.getNota();
                    count++;
                }
            }
        }
        return count == 0 ? 0.0 : soma / count;
    }

    /**
     * Devolve a classificação qualitativa da série com base na média.
     * Fraco: média menor que 5 | Médio: entre 5 e 7.5 | Bom: maior que 7.5.
     *
     * @return "Fraco", "Médio" ou "Bom", ou "Sem classificação" se não houver notas
     */
    public String getClassificacaoQualitativa() {
        double media = getClassificacaoMedia();
        if (media == 0.0) return "Sem classificação";
        if (media < 5) return "Fraco";
        if (media <= 7.5) return "Médio";
        return "Bom";
    }

    /**
     * Devolve uma representação textual da série
     * com título, ano, descrição e classificação qualitativa.
     *
     * @return string com os dados da série
     */
    @Override
    public String toString() {
        return super.toString() + " [" + temporadas.size() + " temporada(s)] ["
                + getClassificacaoQualitativa()
                + (getClassificacaoMedia() == 0.0 ? "" : String.format(" - %.1f/10", getClassificacaoMedia()))
                + "]";
    }
}
