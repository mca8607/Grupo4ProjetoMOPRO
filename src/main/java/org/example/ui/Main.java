package org.example.ui;

import org.example.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principal da aplicação.
 * Inicializa a base de dados, carrega automaticamente os dados de demonstração
 * requisitados pelo enunciado no arranque e lança o menu inicial.
 */
public class Main {

    /**
     * Método de entrada da aplicação.
     * Cria a base de dados, povoa-a automaticamente com o cenário de teste
     * e inicia a interface do utilizador.
     *
     * @param args Argumentos da linha de comandos (não utilizados).
     */
    public static void main(String[] args) {
        /**
         *  Inicializa a base de dados central
          */
        DB imdb = new DB("www.imdb.com");

        /**
         * Criar automaticamente o conjunto de objetos ao arrancar
          */
        carregarDadosDemo(imdb);

        /**
         *  Arranca diretamente com o Menu Inicial
          */
        MenuInicial menuInicial = new MenuInicial(imdb);
        menuInicial.run();
    }

    /**
     * Alimenta a base de dados automaticamente com o cenário de teste predefinido.
     *
     * @param imdb A instância da base de dados do sistema.
     */
    private static void carregarDadosDemo(DB imdb) {
        try {
            /**
             * Criar utilizadores de teste
             */
            Espectador ana = new Espectador("ana@email.com", "ana", "abc");
            Espectador pedro = new Espectador("pedro@email.com", "pedro", "qwerty");
            Admin admin = new Admin("admin@email.com", "admin", "admin");

            imdb.adicionarUtilizador(ana);
            imdb.adicionarUtilizador(pedro);
            imdb.adicionarUtilizador(admin);

            /**
             *  Configurar géneros para o Filme 1
              */
            List<Genero> generosPadrinho = new ArrayList<>();
            generosPadrinho.add(Genero.DRAMA);
            generosPadrinho.add(Genero.THRILLER);

            Filme padrinho = new Filme("O Padrinho", "Drama criminal", "1972", "175 min", generosPadrinho);

            /**
             * Configurar géneros para o Filme 2
              */
            List<Genero> generosInception = new ArrayList<>();
            generosInception.add(Genero.SCI_FI);
            generosInception.add(Genero.ACTION);

            Filme inception = new Filme("Inception", "Thriller sci-fi", "2010", "148 min", generosInception);

            /**
             *  Adicionar os filmes válidos à BD
              */
            imdb.adicionarRecurso(padrinho);
            imdb.adicionarRecurso(inception);

            System.out.println("[INFO] Dados de demonstração carregados com sucesso no arranque.");
        } catch (Exception e) {
            System.err.println("[ERRO] Falha ao carregar os dados automáticos: " + e.getMessage());
        }
    }
}