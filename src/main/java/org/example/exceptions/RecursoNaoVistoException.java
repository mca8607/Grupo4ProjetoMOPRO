package org.example.exceptions;

/**
 * Exceção lançada quando um utilizador tenta submeter uma classificação ou
 * avaliação para um recurso que ainda não visualizou.
 */
public class RecursoNaoVistoException extends Exception {

    /**
     * Constrói uma nova exceção com a mensagem de detalhe especificada.
     *
     * @param mensagem O motivo detalhado da exceção.
     */
    public RecursoNaoVistoException(String mensagem) {
        super(mensagem);
    }
}