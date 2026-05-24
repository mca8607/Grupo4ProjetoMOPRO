package org.example.ui;

import org.example.model.*;
import org.example.utils.Data;
import org.example.utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu inicial que permite escolher a fonte de informação da aplicação.
 * Oferece as opções de carregar dados demo, carregar de ficheiro
 * ou guardar os dados atuais para ficheiro.
 */
public class MenuFonteInfo {
    private DB imdb;
    private String opcao;

    /** Nome do ficheiro usado para guardar e carregar a base de dados. */
    private static final String FICHEIRO = "imdb.dat";

    /**
     * Cria o menu com a base de dados fornecida.
     * @param imdb a base de dados da aplicação
     */
    public MenuFonteInfo(DB imdb) {
        this.imdb = imdb;
    }


    /**
     * Executa o menu em loop até o utilizador avançar para o menu principal.
     */
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

    /**
     * Guarda o estado atual da base de dados no ficheiro {@value FICHEIRO}.
     * Apresenta mensagem de sucesso ou erro consoante o resultado.
     */
    private void guardarEmFicheiro() {
        try {
            this.imdb.guardar(FICHEIRO);
            System.out.println("Estado atual da plataforma guardado com sucesso em '" + FICHEIRO + "'.");
        } catch (IOException e) {
            System.out.println("ERRO: Falha ao guardar os dados no ficheiro: " + e.getMessage());
        }
    }

    /**
     * Carrega a base de dados a partir do ficheiro {@value FICHEIRO}.
     * Substitui os dados em memória pelos dados carregados.
     * Apresenta mensagem de sucesso ou erro consoante o resultado.
     */
    private void carregarDeFicheiro() {
        try {
            DB carregado = DB.carregar(FICHEIRO);
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


    /**
     * Preenche a base de dados com dados de demonstração.
     * Cria utilizadores, atores, filmes com géneros, séries com temporadas e episódios,
     * e associa atores a filmes e episódios.
     */
    private void popularDadosDemo() {
        // 1. Utilizadores
        criarAdmin("admin@example.com", "admin", "admin");
        criarEspectador("ana@example.com", "ana", "abc");
        criarEspectador("pedro@example.com", "pedro", "qwerty");


        // 2. Atores
        Ator pierceBrosnan = new Ator("Pierce Brosnan", new Data(1953, 5, 16));
        Ator tomHardy = new Ator("Tom Hardy", new Data(1977, 9, 15));
        Ator helenMirren = new Ator("Helen Mirren", new Data(1945, 7, 26));
        imdb.adicionarAtor(pierceBrosnan);
        imdb.adicionarAtor(tomHardy);
        imdb.adicionarAtor(helenMirren);


        // 3. Filmes com atores associados
        List<Genero> generoDrama = new ArrayList<>();
        generoDrama.add(Genero.DRAMA);
        generoDrama.add(Genero.THRILLER);
        Filme padrinho = new Filme("O Padrinho", "Drama criminal épico", "1972", "175 min", generoDrama);
        padrinho.adicionarAtor(helenMirren);
        imdb.adicionarRecurso(padrinho);

        List<Genero> generoSciFi = new ArrayList<>();
        generoSciFi.add(Genero.SCI_FI);
        generoSciFi.add(Genero.ACTION);
        Filme inception = new Filme("Inception", "Thriller de ficção científica", "2010", "148 min", generoSciFi);
        inception.adicionarAtor(tomHardy);
        imdb.adicionarRecurso(inception);

        List<Genero> generoAcao = new ArrayList<>();
        generoAcao.add(Genero.ACTION);
        generoAcao.add(Genero.DRAMA);
        Filme darkKnight = new Filme("The Dark Knight", "Ação/Drama", "2008", "152 min", generoAcao);
        darkKnight.adicionarAtor(tomHardy);
        darkKnight.adicionarAtor(helenMirren);
        imdb.adicionarRecurso(darkKnight);


        // 4. Série com temporada, episódios e atores associados
        List<Genero> generoSerie = new ArrayList<>();
        generoSerie.add(Genero.HORROR);
        generoSerie.add(Genero.SCI_FI);
        Serie strangerThings = new Serie("Stranger Things", "Suspense e Ficção Científica", "2016", generoSerie);

        Temporada temp1 = new Temporada("Temporada 1", "", "2016");
        Episodios ep1 = new Episodios("Capítulo 1: A Desaparição de Will Byers");
        Episodios ep2 = new Episodios("Capítulo 2: A Estranha");
        ep1.adicionarAtor(pierceBrosnan);
        ep2.adicionarAtor(pierceBrosnan);
        ep2.adicionarAtor(helenMirren);
        temp1.adicionarEpisodio(ep1);
        temp1.adicionarEpisodio(ep2);
        strangerThings.adicionarTemporadas(temp1);

        imdb.adicionarRecurso(strangerThings);
    }


    /**
     * Cria um espectador e adiciona-o à base de dados.
     * @param email    endereço de email
     * @param nome     nome de utilizador
     * @param password palavra-passe
     */
    private void criarEspectador(String email, String nome, String password) {
        Espectador espectador = new Espectador(email, nome, password);
        imdb.adicionarUtilizador(espectador);
    }


    /**
     * Cria um administrador e adiciona-o à base de dados.
     * @param email    endereço de email
     * @param nome     nome de utilizador
     * @param password palavra-passe
     */
    private void criarAdmin(String email, String nome, String password) {
        Admin admin = new Admin(email, nome, password);
        imdb.adicionarUtilizador(admin);
    }
}

