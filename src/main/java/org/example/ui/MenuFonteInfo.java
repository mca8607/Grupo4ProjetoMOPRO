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
                    popularDadosDemo();
                    System.out.println("Carregada DB com dados demo (Atores, Utilizadores e Filmes)");
                    System.out.println("----------------------------");
                    System.out.println("| CREDENCIAIS DEMO:        |");
                    System.out.println("----------------------------");
                    System.out.println("| -> Admin:                |");
                    System.out.println("|    - admin/admin         |");
                    System.out.println("| -> Espectadores:         |");
                    System.out.println("|    - ana/abc             |");
                    System.out.println("|    - pedro/qwerty        |");
                    System.out.println("----------------------------");

                    // Após carregar, avança para o menu inicial
                    MenuInicial uiMenu = new MenuInicial(imdb);
                    uiMenu.run();
                    break;
                case "2":
                    System.out.println("Funcionalidade de ficheiro ainda não implementada.");
                    break;
                case "0":
                    System.out.println("A sair...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (!opcao.equals("0"));
    }

    private void popularDadosDemo() {
        // 1. Utilizadores
        criarAdmin("admin@example.com", "admin", "admin");
        criarEspectador("ana@example.com", "ana", "abc");
        criarEspectador("pedro@example.com", "pedro", "qwerty");

        // 2. Atores
        criarAtor("Pierce Brosnan", new Data(1953, 5, 16));
        criarAtor("Tom Hardy", new Data(1977, 9, 15));
        criarAtor("Helen Mirren", new Data(1945, 7, 26));

        // 3. Filmes (Nova Hierarquia)
        imdb.adicionarRecurso(new Filme("O Padrinho", "Drama criminal épico", "1972", "175 min"));
        imdb.adicionarRecurso(new Filme("Inception", "Thriller de ficção científica", "2010", "148 min"));
        imdb.adicionarRecurso(new Filme("The Dark Knight", "Ação/Drama", "2008", "152 min"));

        // 4. Séries
        Serie strangerThings = new Serie("Stranger Things", "Suspense e Ficção", "2025");
        imdb.adicionarRecurso(strangerThings);

        System.out.println("Dados demo carregados com sucesso.");
    }

    private void criarAtor(String nome, Data dataNascimento) {
        Ator ator = new Ator(nome, dataNascimento);
        imdb.adicionarAtor(ator);
        System.out.println("Ator '" + nome + "' criado.");
    }

    private void criarEspectador(String email, String nome, String password) {
        Espectador espectador = new Espectador(email, nome, password);
        imdb.adicionarUtilizador(espectador);
    }

    private void criarAdmin(String email, String nome, String password) {
        Admin admin = new Admin(email, nome, password);
        imdb.adicionarUtilizador(admin);
    }
}