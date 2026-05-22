package org.example.ui;

import org.example.model.*;
import org.example.utils.Data;
import org.example.utils.Utils;
import java.io.IOException;

public class MenuFonteInfo {
    private DB imdb;
    private String opcao;

    private static final String FICHEIRO = "imdb.dat";

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
            System.out.println("#  3. Guardar em ficheiro                       #");
            System.out.println("#                                               #");
            System.out.println("#  0. Avançar para o Menu Principal             #");
            System.out.println("#                                               #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");
            switch (opcao) {
                case "1":
                    popularDadosDemo();
                    System.out.println("Dados demo carregados com sucesso no sistema.");
                    break;
                case "2":
                    carregarDeFicheiro();
                    break;
                case "3":
                    guardarEmFicheiro();
                    break;
                case "0":
                    MenuInicial menuInicial = new MenuInicial(this.imdb);
                    menuInicial.run();
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (!opcao.equals("0"));
    }

    private void guardarEmFicheiro() {
        try {
            this.imdb.guardar(FICHEIRO);
            System.out.println("Estado atual da plataforma guardado com sucesso em '" + FICHEIRO + "'.");
        } catch (IOException e) {
            System.out.println("ERRO: Falha ao guardar os dados no ficheiro: " + e.getMessage());
        }
    }

    private void carregarDeFicheiro() {
        try {
            // Recarrega o objeto DB a partir do ficheiro serializado
            DB carregado = DB.carregar(FICHEIRO);

            // Limpa as listas em memória e restaura os dados guardados
            this.imdb.getLstAtores().clear();
            this.imdb.getLstUtilizadores().clear();
            this.imdb.getLstRecursos().clear();

            this.imdb.getLstAtores().addAll(carregado.getLstAtores());
            this.imdb.getLstUtilizadores().addAll(carregado.getLstUtilizadores());
            this.imdb.getLstRecursos().addAll(carregado.getLstRecursos());

            System.out.println("Dados carregados com sucesso a partir de '" + FICHEIRO + "'.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERRO: Não foi possível carregar o ficheiro. Pode ainda não ter sido criado. (" + e.getMessage() + ")");
        }
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

        // 3. Filmes
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