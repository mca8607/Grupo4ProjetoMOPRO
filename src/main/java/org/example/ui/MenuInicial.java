package org.example.ui;

import org.example.model.Admin;
import org.example.model.Espectador;
import org.example.model.DB;
import org.example.model.UtilizadorRegistado;
import org.example.utils.Utils;

public class MenuInicial {
    private DB imdb;
    private String opcao;

    public MenuInicial(DB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#                     MENU                      #");
            System.out.println("#################################################");
            System.out.println("#                                               #");
            System.out.println("#  1. Ver sem login                             #");
            System.out.println("#  2. Login                                     #");
            System.out.println("#                                               #");
            System.out.println("#  0. Voltar                                    #");
            System.out.println("#                                               #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");

            if (opcao.equals("1")) {
                MenuSemLogin ui = new MenuSemLogin(imdb);
                ui.run();
            } else if (opcao.equals("2")) {
                UtilizadorRegistado ur = null;
                while (ur == null) {
                    String username = Utils.readLineFromConsole("Introduza o username: ");
                    String password = Utils.readLineFromConsole("Introduza a password: ");
                    try {
                        ur = imdb.login(username, password);
                        if (ur == null) {
                            throw new Exception("Credenciais inválidas");
                        }
                        System.out.println("Fez login como: " + ur);
                        if (ur instanceof Admin) {
                            MenuAdministrador ui = new MenuAdministrador(imdb);
                            ui.run();
                        } else if (ur instanceof Espectador) {
                            System.out.println("User");
                            MenuUtilizadorRegistado ui = new MenuUtilizadorRegistado(imdb, (Espectador) ur);
                            ui.run();
                        }
                    } catch (Exception e) {
                        System.out.println("ERRO: " + e.getMessage());
                    }
                }
            } else if (!opcao.equals("0")) {
                System.out.println("Opção inválida");
            }
        }
        while (!opcao.equals("0"));
    }
}
