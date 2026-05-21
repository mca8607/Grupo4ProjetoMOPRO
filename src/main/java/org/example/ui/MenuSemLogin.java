package org.example.ui;

import org.example.model.DB;
import org.example.utils.Utils;

public class MenuSemLogin {
    private DB imdb;
    private String opcao;

    public MenuSemLogin(DB imdb) {
        this.imdb = imdb;
    }

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