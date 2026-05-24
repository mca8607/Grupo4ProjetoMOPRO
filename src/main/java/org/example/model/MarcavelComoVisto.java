package org.example.model;
import org.example.exceptions.JaVistoException;

/**
 * Interface que define o contrato para recursos que podem ser marcados
 * como vistos por um {@link Espectador}.
 * Deve ser implementada por {@link Filme} e {@link Episodios}.
 */
public interface MarcavelComoVisto {

    /**
     * Verifica se o espectador já marcou este recurso como visto.
     * @param espectador o espectador a verificar
     * @return true se já viu, false caso contrário
     */
    boolean isVisto(Espectador espectador);

    /**
     * Marca este recurso como visto pelo espectador.
     * Regista também o recurso na lista de vistos do espectador.
     * @param espectador o espectador que viu o recurso
     * @throws JaVistoException se o recurso já tiver sido marcado
     *  como visto por este espectador
     */
    void marcarComoVisto(Espectador espectador) throws JaVistoException;
}