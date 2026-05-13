package org.example.ui;

import org.example.model.DB;
import org.example.utils.Utils;

public class MenuGerirAtores {
    private DB imdb;
    private String opcao;


    public MenuGerirAtores(DB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#                     MENU                      #");
            System.out.println("#################################################");
            System.out.println("#                                               #");
            System.out.println("#  1. Ver atores                                #");
            System.out.println("#  2. Adicionar ator                            #");
            System.out.println("#  3. Remover ator                              #");
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
                    RegistarAtor reg = new RegistarAtor(imdb);
                    reg.run();
                    break;
                case "3":
                    RemoverAtor remover = new RemoverAtor(imdb);
                    remover.run();
                    break;
                case "0":
                    break;

                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
        while (!opcao.equals("0"));
    }
}
