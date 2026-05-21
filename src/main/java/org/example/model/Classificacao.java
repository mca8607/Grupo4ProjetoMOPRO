package org.example.model;
import java.io.Serializable;

public class Classificacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private Espectador espectador;
    private int nota;
    private String comentario;


    public Classificacao(Espectador espectador, int nota, String comentario) {
        if (nota < 1 || nota > 10) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 10.");
        }
        this.espectador = espectador;
        this.nota = nota;
        this.comentario = comentario;
    }

    public Espectador getEspectador() {
        return espectador;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    @Override
    public String toString() {
        String c = (comentario != null && !comentario.isBlank()) ? " | \"" + comentario + "\"" : "";
        return espectador.getNome() + " -> " + nota + "/10" + c;
    }
}
