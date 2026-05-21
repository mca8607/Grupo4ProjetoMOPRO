package org.example.ui;

import org.example.model.DB;
import org.example.utils.Utils;

public class MenuAdministrador {
    private DB imdb;
    private String opcao;

    public MenuAdministrador(DB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#                     MENU                      #");
            System.out.println("#################################################");
            System.out.println("#                                               #");
            System.out.println("#  1. Gerir atores                              #");
            // Completar
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
                    MenuPesquisa uiPesquisa = new MenuPesquisa(imdb);
                    uiPesquisa.run();
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
