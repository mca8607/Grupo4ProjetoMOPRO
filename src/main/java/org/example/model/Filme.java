package org.example.model;
import org.example.exceptions.JaVistoException;
import java.util.ArrayList;
import java.util.List;

public class Filme extends Recurso implements MarcavelComoVisto {

    private String duracao;
    private List<Classificacao> classificacoes;
    private List<Espectador> espectadoresQueViram;

    public Filme(String titulo, String descricao, String dataLancamento, String duracao) {
        super(titulo, descricao, dataLancamento);
        this.duracao = duracao;
        this.classificacoes = new ArrayList<>();
        this.espectadoresQueViram = new ArrayList<>();
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
                    "O filme \"" + getTitulo() + "\" já foi marcado como visto por " + espectador.getNome() + ".");
        }
        espectadoresQueViram.add(espectador);
        espectador.adicionarVisto(this);
    }

    @Override
    public String toString() {
        return super.toString() + " [Duração: " + duracao + "]";
    }
}