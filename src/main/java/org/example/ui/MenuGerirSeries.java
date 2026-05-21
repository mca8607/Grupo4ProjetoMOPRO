package org.example.ui;

import org.example.model.DB;
import org.example.model.Episodios;
import org.example.model.Recurso;
import org.example.model.Serie;
import org.example.model.Temporada;
import org.example.utils.Utils;

public class MenuGerirSeries {
    private DB imdb;
    private String opcao;

    public MenuGerirSeries(DB imdb) {
        this.imdb = imdb;
    }


    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#               GERIR SÉRIES                    #");
            System.out.println("#################################################");
            System.out.println("#                                               #");
            System.out.println("#  1. Listar séries                             #");
            System.out.println("#  2. Adicionar série                           #");
            System.out.println("#  3. Adicionar temporada a série               #");
            System.out.println("#  4. Adicionar episódio a temporada            #");
            System.out.println("#                                               #");
            System.out.println("#  0. Voltar                                    #");
            System.out.println("#                                               #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");

            switch (opcao) {
                case "1":
                    System.out.println(imdb.listarSeries());
                    break;
                case "2":
                    adicionarSerie();
                    break;
                case "3":
                    adicionarTemporada();
                    break;
                case "4":
                    adicionarEpisodio();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (!opcao.equals("0"));
    }


    private void adicionarSerie() {
        System.out.println("\n--- Adicionar Série ---");
        String titulo = Utils.readLineFromConsole("Título: ");
        String descricao = Utils.readLineFromConsole("Descrição: ");
        String ano = Utils.readLineFromConsole("Ano de lançamento: ");

        Serie nova = new Serie(titulo, descricao, ano);
        System.out.println("\nSérie a adicionar: " + nova);

        if (Utils.confirma("Confirma? (S/N)")) {
            imdb.adicionarRecurso(nova);
            System.out.println("Série adicionada com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }


    private void adicionarTemporada() {
        Serie serie = escolherSerie();
        if (serie == null) return;

        System.out.println("\n--- Adicionar Temporada a \"" + serie.getTitulo() + "\" ---");
        String titulo = Utils.readLineFromConsole("Título da temporada (pode deixar vazio): ");

        Temporada t = new Temporada(titulo, "", "");

        if (Utils.confirma("Confirma adição da temporada? (S/N)")) {
            serie.adicionarTemporadas(t);
            System.out.println("Temporada adicionada com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private void adicionarEpisodio() {
        Serie serie = escolherSerie();
        if (serie == null) return;

        if (serie.getTemporadas().isEmpty()) {
            System.out.println("Esta série não tem temporadas. Adicione uma temporada primeiro.");
            return;
        }

        System.out.println("Temporadas disponíveis:");
        for (int i = 0; i < serie.getTemporadas().size(); i++) {
            System.out.println("  " + (i + 1) + ". " + serie.getTemporadas().get(i));
        }

        int numTemp = Utils.readIntFromConsole("Escolha o número da temporada: ");
        if (numTemp < 1 || numTemp > serie.getTemporadas().size()) {
            System.out.println("Temporada inválida.");
            return;
        }

        Temporada temporada = serie.getTemporadas().get(numTemp - 1);
        String tituloEp = Utils.readLineFromConsole("Título do episódio: ");
        Episodios ep = new Episodios(tituloEp);

        if (Utils.confirma("Confirma adição do episódio? (S/N)")) {
            temporada.adicionarEpisodio(ep);
            System.out.println("Episódio adicionado com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }


    private Serie escolherSerie() {
        System.out.println(imdb.listarSeries());
        String titulo = Utils.readLineFromConsole("Título da série: ");

        for (Recurso r : imdb.getLstRecursos()) {
            if (r instanceof Serie && r.getTitulo().equalsIgnoreCase(titulo)) {
                return (Serie) r;
            }
        }
        System.out.println("Série não encontrada.");
        return null;
    }
}