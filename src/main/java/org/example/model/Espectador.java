package org.example.model;

import org.example.exceptions.ClassificacaoDuplicadaException;
import org.example.exceptions.RecursoNaoVistoException;

import java.util.ArrayList;
import java.util.List;

public class Espectador extends UtilizadorRegistado {

    private List<Classificacao> classificacoes;

    public Espectador(String email, String nome, String password) {
        super(email, nome, password);
        this.classificacoes = new ArrayList<>();
    }

    public List<Classificacao> getClassificacoes() {
        return classificacoes;
    }

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

    @Override
    public String toString() {
        return super.toString() + " [Espectador]";
    }
}