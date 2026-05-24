package org.example.model;
import org.example.exceptions.JaVistoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Representa um episódio de uma {@link Temporada} de uma {@link Serie}.
 * Implementa {@link MarcavelComoVisto} para suporte à marcação como visto
 * e agrega classificações dos espectadores.
 */
public class Episodios implements MarcavelComoVisto, Serializable {
    private static final long serialVersionUID = 1L;

    /** Título do episódio. */
    private String titulo;

    /** Lista de classificações atribuídas ao episódio. */
    private List<Classificacao> classificacoes;

    /** Lista de espectadores que marcaram o episódio como visto. */
    private List<Espectador> espectadoresQueViram;


    /**
     * Cria um novo episódio.
     * @param titulo título do episódio
     */
    public Episodios(String titulo) {
        this.titulo = titulo;
        this.classificacoes = new ArrayList<>();
        this.espectadoresQueViram = new ArrayList<>();
    }

    /**
     * Devolve o título do episódio.
     * @return título
     */
    public String getTitulo() {
        return titulo;
    }


    /**
     * Adiciona uma classificação ao episódio.
     * @param c classificação a adicionar
     */
    public void adicionarClassificacao(Classificacao c) {
        classificacoes.add(c);
    }


    /**
     * Devolve todas as classificações do episódio.
     * @return lista de classificações
     */
    public List<Classificacao> getClassificacoes() {
        return classificacoes;
    }



    /**
     * Calcula a classificação média do episódio.
     * @return média das notas, ou 0.0 se não houver classificações
     */
    public double getClassificacaoMedia() {
        if (classificacoes.isEmpty()) return 0.0;
        double soma = 0;
        for (Classificacao c : classificacoes) soma += c.getNota();
        return soma / classificacoes.size();
    }

    /**
     * Verifica se um espectador já classificou este episódio.
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
     * Verifica se o espectador já marcou este episódio como visto.
     * @param espectador o espectador a verificar
     * @return true se já viu, false caso contrário
     */
    @Override
    public boolean isVisto(Espectador espectador) {
        return espectadoresQueViram.contains(espectador);
    }


    /**
     * Marca este episódio como visto pelo espectador.
     * Regista também o episódio na lista de vistos do espectador.
     *
     * @param espectador o espectador que viu o episódio
     * @throws JaVistoException se o espectador já tiver marcado este episódio como visto
     */
    @Override
    public void marcarComoVisto(Espectador espectador) throws JaVistoException {
        if (espectadoresQueViram.contains(espectador)) {
            throw new JaVistoException(
                    "O episódio \"" + titulo + "\" já foi marcado como visto por " + espectador.getNome() + ".");
        }
        espectadoresQueViram.add(espectador);
        espectador.adicionarVisto(this);
    }

    /**
     * Devolve o título do episódio como representação textual.
     * @return título do episódio
     */
    @Override
    public String toString() {
        return titulo;
    }
}