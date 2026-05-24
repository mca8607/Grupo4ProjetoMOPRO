package org.example.model;
import java.io.Serializable;

/**
 * Representa uma classificação atribuída por um {@link Espectador}
 * a um filme ou episódio.
 * Inclui a nota (1 a 10) e um comentário opcional.
 */
public class Classificacao implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Espectador que atribuiu a classificação. */
    private Espectador espectador;
    /** Nota atribuída (entre 1 e 10). */
    private int nota;
    /** Comentário opcional associado à classificação. */
    private String comentario;

    /**
     * Cria uma nova classificação.
     *
     * @param espectador o espectador que classifica
     * @param nota       nota de 1 a 10
     * @param comentario comentário opcional (pode ser null ou vazio)
     * @throws IllegalArgumentException se a nota não estiver entre 1 e 10
     */
    public Classificacao(Espectador espectador, int nota, String comentario) {
        if (nota < 1 || nota > 10) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 10.");
        }
        this.espectador = espectador;
        this.nota = nota;
        this.comentario = comentario;
    }

    /**
     * Devolve o espectador que fez a classificação.
     * @return espectador
     */
    public Espectador getEspectador() {
        return espectador;
    }

    /**
     * Devolve a nota atribuída.
     * @return nota (1 a 10)
     */
    public int getNota() {
        return nota;
    }

    /**
     * Devolve o comentário associado à classificação.
     * @return comentário, ou null se não houver
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Devolve uma representação textual da classificação com o nome do espectador, a nota e o comentário (se existir).
     * @return string com os dados da classificação
     */
    @Override
    public String toString() {
        String c = (comentario != null && !comentario.isBlank()) ? " | \"" + comentario + "\"" : "";
        return espectador.getNome() + " -> " + nota + "/10" + c;
    }
}
