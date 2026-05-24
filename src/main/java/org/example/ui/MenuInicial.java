package org.example.ui;

import org.example.model.*;
import org.example.utils.Utils;

/**
 * Interface de utilizador que representa o menu inicial da aplicação IMDB.
 * Permite aos utilizadores navegar entre a visualização anónima, a autenticação
 * no sistema ou terminar a execução do programa.
 */
public class MenuInicial {

    /** A base de dados central do sistema. */
    private DB imdb;

    /** A opção selecionada pelo utilizador no menu. */
    private String opcao;


    /**
     * Constrói uma nova instância do Menu Inicial associada à base de dados.
     * @param imdb A base de dados do sistema.
     */
    public MenuInicial(DB imdb) {
        this.imdb = imdb;
    }


    /**
     * Executa o ciclo principal do menu inicial, processando as escolhas do utilizador
     * e encaminhando para os respetivos submenus (público, administrador ou espectador).
     */
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
                        ur = null;
                    }
                }
            } else if (!opcao.equals("0")) {
                System.out.println("Opção inválida");
            }
        }
        while (!opcao.equals("0"));
    }
}
