package org.example.exceptions;

/**
 * Exceção lançada quando um utilizador tenta classificar um recurso
 * (filme ou episódio) que já foi classificado por ele anteriormente.
 */
public class ClassificacaoDuplicadaException extends Exception {

    /**
     * Constrói uma nova exceção com a mensagem de detalhe especificada.
     *
     * @param mensagem O motivo detalhado da exceção.
     */
    public ClassificacaoDuplicadaException(String mensagem) {
        super(mensagem);
    }
}