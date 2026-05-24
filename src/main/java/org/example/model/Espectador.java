package org.example.model;

import org.example.exceptions.ClassificacaoDuplicadaException;
import org.example.exceptions.RecursoNaoVistoException;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um espectador registado na plataforma.
 * Para além das funcionalidades base de {@link UtilizadorRegistado},
 * pode classificar e comentar filmes e episódios que já tenha visto.
 * Herda de {@link UtilizadorRegistado}.
 */
public class Espectador extends UtilizadorRegistado {

    /** Lista de classificações feitas por este espectador. */
    private List<Classificacao> classificacoes;


    /**
     * Cria um novo espectador.
     *
     * @param email    endereço de email
     * @param nome     nome de utilizador
     * @param password palavra-passe
     */
    public Espectador(String email, String nome, String password) {
        super(email, nome, password);
        this.classificacoes = new ArrayList<>();
    }


    /**
     * Devolve a lista de classificações feitas por este espectador.
     * @return lista de classificações
     */
    public List<Classificacao> getClassificacoes() {
        return classificacoes;
    }



    /**
     * Classifica um filme com uma nota (1 a 10) e um comentário opcional.
     * O espectador só pode classificar um filme que já tenha visto, e apenas uma vez por filme.
     *
     * @param filme      o filme a classificar
     * @param nota       nota de 1 a 10
     * @param comentario comentário (pode ser vazio)
     * @throws RecursoNaoVistoException        se o espectador ainda não viu o filme
     * @throws ClassificacaoDuplicadaException se o espectador já classificou este filme
     */
    public void classificarFilme(Filme filme, int nota, String comentario)
            throws RecursoNaoVistoException, ClassificacaoDuplicadaException {
        if (!jaViu(filme)) {
            throw new RecursoNaoVistoException(
                    "Não pode classificar \"" + filme.getTitulo() + "\" sem o ter visto.");
        }
        if (filme.temClassificacaoDe(this)) {
            throw new ClassificacaoDuplicadaException(
                    "Já classificou o filme \"" + filme.getTitulo() + "\" anteriormente.");
        }
        Classificacao nova = new Classificacao(this, nota, comentario);
        classificacoes.add(nova);
        filme.adicionarClassificacao(nova);
    }



    /**
     * Classifica um episódio com uma nota (1 a 10) e um comentário opcional.
     * O espectador só pode classificar um episódio que já tenha visto, e apenas uma vez por episódio.
     *
     * @param episodio   o episódio a classificar
     * @param nota       nota de 1 a 10
     * @param comentario comentário (pode ser vazio)
     * @throws RecursoNaoVistoException        se o espectador ainda não viu o episódio
     * @throws ClassificacaoDuplicadaException se o espectador já classificou este episódio
     */
    public void classificarEpisodio(Episodios episodio, int nota, String comentario)
            throws RecursoNaoVistoException, ClassificacaoDuplicadaException {
        if (!jaViu(episodio)) {
            throw new RecursoNaoVistoException(
                    "Não pode classificar \"" + episodio.getTitulo() + "\" sem o ter visto.");
        }
        if (episodio.temClassificacaoDe(this)) {
            throw new ClassificacaoDuplicadaException(
                    "Já classificou o episódio \"" + episodio.getTitulo() + "\" anteriormente.");
        }
        Classificacao nova = new Classificacao(this, nota, comentario);
        classificacoes.add(nova);
        episodio.adicionarClassificacao(nova);
    }

    /**
     * Devolve uma representação textual do espectador,
     * indicando o nome, email e o papel de espectador.
     * @return string com os dados do espectador
     */
    @Override
    public String toString() {
        return super.toString() + " [Espectador]";
    }
}