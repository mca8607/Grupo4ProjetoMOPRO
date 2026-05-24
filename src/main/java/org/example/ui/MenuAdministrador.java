package org.example.ui;

import org.example.model.DB;
import org.example.utils.Utils;

/**
 * Menu principal do Administrador.
 * Permite gerir atores, filmes, séries, listar utilizadores e consultar listagens ordenadas.
 */
public class MenuAdministrador {
    private DB imdb;
    private String opcao;

    /**
     * Cria o menu do administrador.
     * @param imdb a base de dados da aplicação
     */
    public MenuAdministrador(DB imdb) {
        this.imdb = imdb;
    }


    /**
     * Executa o menu em loop até o administrador escolher voltar.
     */
    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#                     MENU                      #");
            System.out.println("#################################################");
            System.out.println("#                                               #");
            System.out.println("#  1. Gerir atores                              #");
            System.out.println("#  2. Gerir filmes                              #");
            System.out.println("#  3. Gerir séries                              #");
            System.out.println("#  4. Listar utilizadores brutos                #");
            System.out.println("#  5. Consultar listagens ordenadas             #");
            System.out.println("#                                               #");
            System.out.println("#  0. Voltar                                    #");
            System.out.println("#                                               #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");

            switch (opcao) {
                case "1":
                    MenuGerirAtores uiAtores = new MenuGerirAtores(imdb);
                    uiAtores.run();
                    break;
                case "2":
                    MenuGerirFilmes uiFilmes = new MenuGerirFilmes(imdb);
                    uiFilmes.run();
                    break;
                case "3":
                    MenuGerirSeries uiSeries = new MenuGerirSeries(imdb);
                    uiSeries.run();
                    break;
                case "4":
                    System.out.println(imdb.listarUtilizadores());
                    break;
                case "5":
                    MenuListagens uiListagens = new MenuListagens(imdb);
                    uiListagens.run();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        while (!opcao.equals("0"));
    }
}
