package org.example.ui;

import org.example.model.*;
import org.example.utils.Data;
import org.example.utils.Utils;

public class MenuFonteInfo {
    private DB imdb;
    private String opcao;

    public MenuFonteInfo(DB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#              FONTE DA INFORMAÇÃO              #");
            System.out.println("#################################################");
            System.out.println("#                                               #");
            System.out.println("#  1. Carregar dados demo                       #");
            System.out.println("#  2. Carregar de ficheiro                      #");
            System.out.println("#                                               #");
            System.out.println("#  0. Sair                                      #");
            System.out.println("#                                               #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");
            switch (opcao) {
                case "1":
                    imdb = construir();
                    System.out.println("Carregada DB com dados demo");
                    System.out.println("----------------------------");
                    System.out.println("| CREDENCIAIS DEMO:        |");
                    System.out.println("----------------------------");
                    System.out.println("| -> Admin:                |");
                    System.out.println("|    - admin/admin         |");
                    System.out.println("| -> Espectadores:         |");
                    System.out.println("|    - ana/abc             |");
                    System.out.println("|    - pedro/qwerty        |");
                    System.out.println("----------------------------");
                    break;
                case "2":
                    // Completar
                    break;
                // Completar
            }
            if (imdb != null) {
                System.out.println(imdb);
                MenuInicial uiMenu = new MenuInicial(imdb);
                uiMenu.run();
            }
        } while (!opcao.equals("0"));
    }

    private static DB construir() {
        // Construção da empresa
        DB imdb = new DB("www.imdb.com");

        // Utilizadores
        Admin admin = criarAdmin(imdb, "admin@example.com", "admin", "admin");
        Espectador ana = criarEspectador("ana@example.com", "ana", "abc", imdb);
        Espectador pedro = criarEspectador("pedro@example.com", "pedro", "qwerty", imdb);

        // Atores
        Ator pierceBrosnan = criarAtor(imdb, "Pierce Brosnan", new Data(1953, 5, 16));
        Ator tomHardy = criarAtor(imdb, "Tom Hardy", new Data(1977, 9, 15));
        Ator helenMirren = criarAtor(imdb, "Helen Mirren", new Data(1945, 7, 26));
        Ator jonathanPrice = criarAtor(imdb, "Jonathan Price", new Data(1947, 6, 1));
        Ator cillianMurphy = criarAtor(imdb, "Cillian Murphy", new Data(1976, 5, 25));

        // Completar

        return imdb;
    }

    private static Ator criarAtor(DB imdb, String nome, Data dataNascimento) {
        Ator ator = new Ator(nome, dataNascimento);
        imdb.adicionarAtor(ator);
        System.out.println("Ator '" + nome + "' criado com sucesso");
        return ator;
    }

    private static Espectador criarEspectador(String email, String nome, String password, DB imdb) {
        Espectador espectador = new Espectador(email, nome, password);
        imdb.adicionarUtilizador(espectador);
        System.out.println("Espectador '" + nome + "' criado com sucesso");
        return espectador;
    }

    private static Admin criarAdmin(DB imdb, String email, String nome, String password) {
        Admin admin = new Admin(email, nome, password);
        imdb.adicionarUtilizador(admin);
        System.out.println("Administrador '" + nome + "' criado com sucesso");
        return admin;
    }
}
