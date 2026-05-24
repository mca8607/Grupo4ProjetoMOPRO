package org.example.exceptions;

/**
 * Exceção lançada quando um utilizador tenta marcar como visto um recurso
 * que já se encontra registado como visualizado no seu histórico pessoal.
 */
public class JaVistoException extends Exception{

    /**
     * Constrói uma nova exceção com a mensagem de detalhe especificada.
     *
     * @param mensagem O motivo detalhado da exceção.
     */
    public JaVistoException(String mensagem) {
        super(mensagem);
    }
}
