package org.example.model;
import org.example.exceptions.JaVistoException;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um filme na plataforma.
 * Herda de {@link Recurso} e implementa {@link MarcavelComoVisto}.
 * Agrega classificações e regista os espectadores que o viram.
 */
public class Filme extends Recurso implements MarcavelComoVisto {

    /** Duração do filme (ex: "140 min"). */
    private String duracao;

    /** Lista de classificações atribuídas ao filme. */
    private List<Classificacao> classificacoes;

    /** Lista de espectadores que marcaram o filme como visto. */
    private List<Espectador> espectadoresQueViram;

    /** Lista de atores que participam no filme. */
    private List<Ator> atores;



    /**
     * Cria um novo filme.
     *
     * @param titulo         título do filme
     * @param descricao      descrição ou sinopse
     * @param dataLancamento ano de lançamento
     * @param duracao        duração
     * @param generos        lista de géneros (mínimo um)
     */
    public Filme(String titulo, String descricao, String dataLancamento, String duracao, List<Genero> generos) {
        super(titulo, descricao, dataLancamento, generos);
        this.duracao = duracao;
        this.classificacoes = new ArrayList<>();
        this.espectadoresQueViram = new ArrayList<>();
        this.atores = new ArrayList<>();
    }


    /**
     * Adiciona um ator ao filme, se ainda não estiver associado.
     * @param a ator a adicionar
     */
    public void adicionarAtor(Ator a) {
        if (!atores.contains(a)) {
            atores.add(a);
        }
    }

    /**
     * Devolve a lista de atores do filme.
     * @return lista de atores
     */
    public List<Ator> getAtores() {
        return atores;
    }

    /**
     * Verifica se um determinado ator participa neste filme.
     * @param a o ator a verificar
     * @return true se o ator pertence ao filme, false caso contrário
     */
    public boolean temAtor(Ator a) {
        return atores.contains(a);
    }



    /**
     * Adiciona uma classificação ao filme.
     * @param c classificação a adicionar
     */
    public void adicionarClassificacao(Classificacao c) {
        classificacoes.add(c);
    }


    /**
     * Devolve todas as classificações do filme.
     * @return lista de classificações
     */
    public List<Classificacao> getClassificacoes() {
        return classificacoes;
    }


    /**
     * Calcula a classificação média do filme com base nas notas atribuídas.
     * @return média das notas, ou 0.0 se não houver classificações
     */
    public double getClassificacaoMedia() {
        if (classificacoes.isEmpty()) return 0.0;
        double soma = 0;
        for (Classificacao c : classificacoes) soma += c.getNota();
        return soma / classificacoes.size();
    }


    /**
     * Verifica se um determinado espectador já classificou este filme.
     * @param espectador o espectador a verificar
     * @return true se já classificou, false caso contrário
     */
    public boolean temClassificacaoDe(Espectador espectador) {
        for (Classificacao c : classificacoes) {
            if (c.getEspectador().equals(espectador)) return true;
        }
        return false;
    }


    /**
     * Devolve a classificação qualitativa do filme com base na média.
     * Fraco: média menor que 4 | Médio: entre 4 e 8 | Bom: maior que 8.
     *
     * @return "Fraco", "Médio" ou "Bom", ou "Sem classificação" se não houver notas
     */
    public String getClassificacaoQualitativa() {
        if (classificacoes.isEmpty()) return "Sem classificação";
        double media = getClassificacaoMedia();
        if (media < 4) return "Fraco";
        if (media <= 8) return "Médio";
        return "Bom";
    }


    /**
     * Verifica se o espectador já marcou este filme como visto.
     * @param espectador o espectador a verificar
     * @return true se já viu, false caso contrário
     */
    @Override
    public boolean isVisto(Espectador espectador) {
        return espectadoresQueViram.contains(espectador);
    }


    /**
     * Marca este filme como visto pelo espectador e regista também o filme na sua lista de vistos.
     * @param espectador o espectador que viu o filme
     * @throws JaVistoException se o espectador já tiver marcado este filme como visto
     */
    @Override
    public void marcarComoVisto(Espectador espectador) throws JaVistoException {
        if (espectadoresQueViram.contains(espectador)) {
            throw new JaVistoException(
                    "O filme \"" + getTitulo() + "\" já foi marcado como visto por " + espectador.getNome() + ".");
        }
        espectadoresQueViram.add(espectador);
        espectador.adicionarVisto(this);
    }

    /**
     * Devolve uma representação textual do filme
     * com o título, ano, descrição, duração e classificação qualitativa.
     *
     * @return string com os dados do filme
     */
    @Override
    public String toString() {
        return super.toString() + " [Duração: " + duracao + "] ["
                + getClassificacaoQualitativa()
                + (classificacoes.isEmpty() ? "" : String.format(" - %.1f/10", getClassificacaoMedia()))
                + "]";
    }
}