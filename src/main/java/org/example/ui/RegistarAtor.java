package org.example.ui;

import org.example.model.Ator;
import org.example.model.DB;
import org.example.utils.Data;
import org.example.utils.Utils;

/**
 * Classe responsável pelo registo de um novo ator na base de dados.
 * Lê os dados do teclado, apresenta-os para confirmação e guarda-os.
 */

public class RegistarAtor {
    private DB imdb;


    /**
     * Cria a classe de registo de ator.
     * @param imdb a base de dados da aplicação
     */
    public RegistarAtor(DB imdb) {
        this.imdb = imdb;
    }


    /**
     * Executa o fluxo de registo: lê os dados, apresenta-os e adiciona o ator à base de dados após confirmação.
     */

    public void run() {

        System.out.println("Novo Ator:");

        Ator novoAtor = introduzDados();
        apresentaDados(novoAtor);
        if (Utils.confirma("Confirma os dados? (S/N)")) {
            imdb.adicionarAtor(novoAtor);
            System.out.println("Ator adicionado com sucesso");
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
     * Lê os dados do novo ator a partir do teclado.
     * @return novo objeto {@link Ator} com os dados introduzidos
     */
    private static Ator introduzDados() {
        String nome = Utils.readLineFromConsole("Introduza o nome do ator: ");
        Data dataNascimento = Utils.readDateFromConsole("Introduza o data de nascimento: ");
        return new Ator(nome, dataNascimento);
    }
}
