package org.example.ui;

import org.example.model.*;
import org.example.utils.Utils;

/**
 * Menu do utilizador registado (espectador).
 * Permite ver filmes, pesquisar, marcar como visto, classificar e gerir a lista pessoal.
 */
public class MenuUtilizadorRegistado {
    private DB imdb;
    private Espectador utilizador;
    private String opcao;

    /**
     * Cria o menu do utilizador registado.
     * @param imdb       a base de dados da aplicação
     * @param utilizador o espectador que fez login
     */
    public MenuUtilizadorRegistado(DB imdb, Espectador utilizador) {
        this.utilizador = utilizador;
        this.imdb = imdb;
    }

    /**
     * Executa o menu em loop até o utilizador escolher voltar.
     */
    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#          MENU UTILIZADOR REGISTADO            #");
            System.out.println("#################################################");
            System.out.println("#                                               #");
            System.out.println("#  1. Ver filmes disponíveis                    #");
            System.out.println("#  2. Pesquisar conteúdos                       #");
            System.out.println("#  3. Marcar filme como visto                   #");
            System.out.println("#  4. Classificar filme                         #");
            System.out.println("#  5. Consultar a minha lista pessoal           #");
            System.out.println("#  6. Adicionar filme à lista pessoal           #");
            System.out.println("#  7. Remover filme da lista pessoal            #");
            System.out.println("#                                               #");
            System.out.println("#  0. Voltar                                    #");
            System.out.println("#                                               #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");

            switch (opcao) {
                case "1":
                    System.out.println(imdb.listarFilmes());
                    break;
                case "2":
                    MenuPesquisa uiPesquisa = new MenuPesquisa(imdb);
                    uiPesquisa.run();
                    break;
                case "3":
                    marcarFilmeComoVisto();
                    break;
                case "4":
                    classificarFilme();
                    break;
                case "5":
                    System.out.println(utilizador.consultarListaPessoal());
                    break;
                case "6":
                    adicionarFilmeListaPessoal();
                    break;
                case "7":
                    removerFilmeListaPessoal();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        while (!opcao.equals("0"));
    }


    /**
     * Permite ao utilizador marcar um filme como visto.
     * Mostra a lista de filmes e pede o título.
     * Lança mensagem de erro se o filme já foi marcado como visto.
     */
    private void marcarFilmeComoVisto() {
        System.out.println(imdb.listarFilmes());
        String titulo = Utils.readLineFromConsole("Título do filme a marcar como visto: ");

        Filme filme = encontrarFilme(titulo);
        if (filme == null) {
            System.out.println("Filme não encontrado.");
            return;
        }

        try {
            filme.marcarComoVisto(utilizador);
            System.out.println("\"" + filme.getTitulo() + "\" marcado como visto!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }


    /**
     * Permite ao utilizador classificar um filme que já tenha visto.
     * Pede uma nota entre 1 e 10 e um comentário opcional.
     * Mostra uma mensagem de erro se o filme não foi visto ou já foi classificado.
     */
    private void classificarFilme() {
        System.out.println(imdb.listarFilmes());
        String titulo = Utils.readLineFromConsole("Título do filme a classificar: ");

        Filme filme = encontrarFilme(titulo);
        if (filme == null) {
            System.out.println("Filme não encontrado.");
            return;
        }

        int nota = 0;
        while (nota < 1 || nota > 10) {
            nota = Utils.readIntFromConsole("Nota (1 a 10): ");
            if (nota < 1 || nota > 10) {
                System.out.println("Nota inválida. Introduza um valor entre 1 e 10.");
            }
        }

        String comentario = Utils.readLineFromConsole("Comentário (pode deixar vazio): ");

        try {
            utilizador.classificarFilme(filme, nota, comentario);
            System.out.println("Classificação registada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }



    /**
     * Adiciona um filme à lista pessoal do utilizador.
     * Mostra a lista de filmes e pede o título.
     */
    private void adicionarFilmeListaPessoal() {
        System.out.println(imdb.listarFilmes());
        String titulo = Utils.readLineFromConsole("Título do filme a adicionar à lista: ");

        Filme filme = encontrarFilme(titulo);
        if (filme == null) {
            System.out.println("Filme não encontrado.");
            return;
        }

        utilizador.adicionarFilmeListaPessoal(filme);
        System.out.println("\"" + filme.getTitulo() + "\" adicionado à lista pessoal!");
    }


    /**
     * Remove um filme da lista pessoal do utilizador.
     * Mostra a lista pessoal atual e pede o título.
     */
    private void removerFilmeListaPessoal() {
        System.out.println(utilizador.consultarListaPessoal());
        String titulo = Utils.readLineFromConsole("Título do filme a remover da lista: ");

        Filme filme = encontrarFilme(titulo);
        if (filme == null) {
            System.out.println("Filme não encontrado.");
            return;
        }

        utilizador.removerFilmeListaPessoal(filme);
        System.out.println("\"" + filme.getTitulo() + "\" removido da lista pessoal!");
    }


    /**
     * Procura um filme na base de dados pelo título (ignora maiúsculas/minúsculas).
     * @param titulo título do filme a procurar
     * @return o filme encontrado ou null se não existir
     */

    private Filme encontrarFilme(String titulo) {
        for (Recurso r : imdb.getLstRecursos()) {
            if (r instanceof Filme && r.getTitulo().equalsIgnoreCase(titulo)) {
                return (Filme) r;
            }
        }
        return null;
    }
}

