package org.example.ui;

import org.example.model.Ator;
import org.example.model.DB;
import org.example.utils.Utils;


/**
 * Classe responsável pela remoção de um ator da base de dados.
 * Lista os atores existentes, pede o nome do ator a remover
 * e remove-o após confirmação.
 */
public class RemoverAtor {
    private DB imdb;

    /**
     * Cria a classe de remoção de ator.
     * @param imdb a base de dados da aplicação
     */
    public RemoverAtor(DB imdb) {
        this.imdb = imdb;
    }


    /**
     * Executa o fluxo de remoção: lê o nome do ator, apresenta-o
     * e remove-o da base de dados após confirmação.
     */
    public void run() {
        System.out.println("Remover ator");
        Ator ator = introduzDados();
        apresentaDados(ator);
        if (Utils.confirma("Confirma que pretende remover? (S/N)")) {
            imdb.removerAtor(ator);
            System.out.println("Ator eliminado com sucesso");
            System.out.println(imdb.listarAtores());
        }
    }

    /**
     * Apresenta os dados do ator no ecrã.
     * @param ator o ator a apresentar
     */
    private void apresentaDados(Ator ator) {
        System.out.println(ator);
    }


    /**
     * Lista os atores existentes e lê o nome do ator a remover.
     * @return o {@link Ator} encontrado, ou null se não existir
     */
    private Ator introduzDados() {
        System.out.println(imdb.listarAtores());
        String nomeRemover = Utils.readLineFromConsole("Escolha o ator a remover (introduza o nome): ");
        return imdb.pesquisaAtor(nomeRemover);
    }
}
