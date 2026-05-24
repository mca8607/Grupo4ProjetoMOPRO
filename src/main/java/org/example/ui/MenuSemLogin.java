package org.example.ui;

import org.example.model.DB;
import org.example.utils.Utils;

/**
 * Menu sem autenticação (visitante).
 * Permite listar atores, listar filmes e pesquisar conteúdos.
 */
public class MenuSemLogin {
    private DB imdb;
    private String opcao;


    /**
     * Cria o menu do visitante.
     * @param imdb a base de dados da aplicação
     */
    public MenuSemLogin(DB imdb) {
        this.imdb = imdb;
    }


    /**
     * Executa o menu em loop até o utilizador escolher voltar.
     */
    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#                MENU VISITANTE                 #");
            System.out.println("#################################################");
            System.out.println("#                                               #");
            System.out.println("#  1. Listar atores                             #");
            System.out.println("#  2. Listar filmes                             #");
            System.out.println("#  3. Pesquisar (Filmes/Atores)                 #"); // Nova opção
            System.out.println("#                                               #");
            System.out.println("#  0. Voltar                                    #");
            System.out.println("#                                               #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");

            switch (opcao) {
                case "1":
                    System.out.println(imdb.listarAtores());
                    break;
                case "2":
                    System.out.println(imdb.listarFilmes());
                    break;
                case "3":
                    // Chama o novo menu de pesquisa
                    MenuPesquisa pesquisaUI = new MenuPesquisa(imdb);
                    pesquisaUI.run();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
        while (!opcao.equals("0"));
    }
}