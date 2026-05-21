package org.example.model;
import org.example.exceptions.JaVistoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Episodios implements MarcavelComoVisto, Serializable {
    private static final long serialVersionUID = 1L;

    private String titulo;
    private List<Classificacao> classificacoes;
    private List<Espectador> espectadoresQueViram;


    public Episodios(String titulo) {
        this.titulo = titulo;
        this.classificacoes = new ArrayList<>();
        this.espectadoresQueViram = new ArrayList<>();
    }

    public String getTitulo() {
        return titulo;
    }

    public void adicionarClassificacao(Classificacao c) {
        classificacoes.add(c);
    }

    public List<Classificacao> getClassificacoes() {
        return classificacoes;
    }

    public double getClassificacaoMedia() {
        if (classificacoes.isEmpty()) return 0.0;
        double soma = 0;
        for (Classificacao c : classificacoes) soma += c.getNota();
        return soma / classificacoes.size();
    }

    public boolean temClassificacaoDe(Espectador espectador) {
        for (Classificacao c : classificacoes) {
            if (c.getEspectador().equals(espectador)) return true;
        }
        return false;
    }

    @Override
    public boolean isVisto(Espectador espectador) {
        return espectadoresQueViram.contains(espectador);
    }


    @Override
    public void marcarComoVisto(Espectador espectador) throws JaVistoException {
        if (espectadoresQueViram.contains(espectador)) {
            throw new JaVistoException(
                    "O episódio \"" + titulo + "\" já foi marcado como visto por " + espectador.getNome() + ".");
        }
        espectadoresQueViram.add(espectador);
        espectador.adicionarVisto(this);
    }

    @Override
    public String toString() {
        return titulo;
    }
}