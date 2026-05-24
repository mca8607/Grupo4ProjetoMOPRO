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
            System.out.println("#  2. Ver séries disponíveis                    #");
            System.out.println("#  3. Pesquisar conteúdos                       #");
            System.out.println("#  4. Marcar filme como visto                   #");
            System.out.println("#  5. Marcar episódio como visto                #");
            System.out.println("#  6. Classificar filme                         #");
            System.out.println("#  7. Classificar episódio                      #");
            System.out.println("#  8. Consultar a minha lista pessoal           #");
            System.out.println("#  9. Adicionar à lista pessoal                 #");
            System.out.println("#  10. Remover da lista pessoal                 #");
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
                    System.out.println(imdb.listarSeries());
                    break;
                case "3":
                    MenuPesquisa uiPesquisa = new MenuPesquisa(imdb);
                    uiPesquisa.run();
                    break;
                case "4":
                    marcarFilmeComoVisto();
                    break;
                case "5":
                    marcarEpisodioComoVisto();
                    break;
                case "6":
                    classificarFilme();
                    break;
                case "7":
                    classificarEpisodio();
                    break;
                case "8":
                    System.out.println(utilizador.consultarListaPessoal());
                    break;
                case "9":
                    adicionarAListaPessoal();
                    break;
                case "10":
                    removerDaListaPessoal();
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
     * Permite ao utilizador marcar um episódio de uma série como visto.
     * Guia o utilizador pela escolha da série, temporada e episódio.
     */
    private void marcarEpisodioComoVisto() {
        Episodios episodio = escolherEpisodio();
        if (episodio == null) return;

        try {
            episodio.marcarComoVisto(utilizador);
            System.out.println("\"" + episodio.getTitulo() + "\" marcado como visto!");
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
     * Permite ao utilizador classificar um episódio que já tenha visto.
     * Guia o utilizador pela escolha da série, temporada e episódio.
     */
    private void classificarEpisodio() {
        Episodios episodio = escolherEpisodio();
        if (episodio == null) return;

        int nota = lerNota();
        String comentario = Utils.readLineFromConsole("Comentário (pode deixar vazio): ");

        try {
            utilizador.classificarEpisodio(episodio, nota, comentario);
            System.out.println("Classificação registada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }


    /**
     * Permite ao utilizador adicionar um filme ou episódio à lista pessoal.
     */
    private void adicionarAListaPessoal() {
        System.out.println("\n--- Adicionar à Lista Pessoal ---");
        System.out.println("1. Filme");
        System.out.println("2. Episódio");
        String tipo = Utils.readLineFromConsole("Escolha o tipo (1 ou 2): ");

        if (tipo.equals("1")) {
            System.out.println(imdb.listarFilmes());
            String titulo = Utils.readLineFromConsole("Título do filme a adicionar: ");
            Filme filme = encontrarFilme(titulo);
            if (filme == null) {
                System.out.println("Filme não encontrado.");
                return;
            }
            utilizador.adicionarFilmeListaPessoal(filme);
            System.out.println("\"" + filme.getTitulo() + "\" adicionado à lista pessoal!");
        } else if (tipo.equals("2")) {
            Episodios episodio = escolherEpisodio();
            if (episodio == null) return;
            utilizador.adicionarEpisodioListaPessoal(episodio);
            System.out.println("\"" + episodio.getTitulo() + "\" adicionado à lista pessoal!");
        } else {
            System.out.println("Opção inválida.");
        }
    }



    /**
     * Permite ao utilizador remover um filme ou episódio da lista pessoal.
     */
    private void removerDaListaPessoal() {
        System.out.println(utilizador.consultarListaPessoal());
        System.out.println("\n--- Remover da Lista Pessoal ---");
        System.out.println("1. Filme");
        System.out.println("2. Episódio");
        String tipo = Utils.readLineFromConsole("Escolha o tipo (1 ou 2): ");

        if (tipo.equals("1")) {
            String titulo = Utils.readLineFromConsole("Título do filme a remover: ");
            Filme filme = encontrarFilme(titulo);
            if (filme == null) {
                System.out.println("Filme não encontrado.");
                return;
            }
            utilizador.removerFilmeListaPessoal(filme);
            System.out.println("\"" + filme.getTitulo() + "\" removido da lista pessoal!");
        } else if (tipo.equals("2")) {
            Episodios episodio = escolherEpisodio();
            if (episodio == null) return;
            utilizador.removerEpisodioListaPessoal(episodio);
            System.out.println("\"" + episodio.getTitulo() + "\" removido da lista pessoal!");
        } else {
            System.out.println("Opção inválida.");
        }
    }


    /**
     * Guia o utilizador pela escolha de uma série, temporada e episódio.
     * @return o episódio selecionado, ou null se a navegação falhar
     */
    private Episodios escolherEpisodio() {
        System.out.println(imdb.listarSeries());
        String tituloSerie = Utils.readLineFromConsole("Título da série: ");

        Serie serie = null;
        for (Recurso r : imdb.getLstRecursos()) {
            if (r instanceof Serie && r.getTitulo().equalsIgnoreCase(tituloSerie)) {
                serie = (Serie) r;
                break;
            }
        }

        if (serie == null) {
            System.out.println("Série não encontrada.");
            return null;
        }

        if (serie.getTemporadas().isEmpty()) {
            System.out.println("Esta série não tem temporadas.");
            return null;
        }

        System.out.println("Temporadas disponíveis:");
        for (int i = 0; i < serie.getTemporadas().size(); i++) {
            System.out.println("  " + (i + 1) + ". " + serie.getTemporadas().get(i));
        }

        int numTemp = Utils.readIntFromConsole("Escolha o número da temporada: ");
        if (numTemp < 1 || numTemp > serie.getTemporadas().size()) {
            System.out.println("Temporada inválida.");
            return null;
        }

        Temporada temporada = serie.getTemporadas().get(numTemp - 1);

        if (temporada.getEpisodios().isEmpty()) {
            System.out.println("Esta temporada não tem episódios.");
            return null;
        }

        System.out.println("Episódios disponíveis:");
        for (int i = 0; i < temporada.getEpisodios().size(); i++) {
            System.out.println("  " + (i + 1) + ". " + temporada.getEpisodios().get(i));
        }

        int numEp = Utils.readIntFromConsole("Escolha o número do episódio: ");
        if (numEp < 1 || numEp > temporada.getEpisodios().size()) {
            System.out.println("Episódio inválido.");
            return null;
        }

        return temporada.getEpisodios().get(numEp - 1);
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


    /**
     * Lê e valida uma nota entre 1 e 10 do teclado.
     * @return nota válida entre 1 e 10
     */
    private int lerNota() {
        int nota = 0;
        while (nota < 1 || nota > 10) {
            nota = Utils.readIntFromConsole("Nota (1 a 10): ");
            if (nota < 1 || nota > 10) {
                System.out.println("Nota inválida. Introduza um valor entre 1 e 10.");
            }
        }
        return nota;
    }
}

