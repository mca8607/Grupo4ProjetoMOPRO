package org.example.ui;

import org.example.model.*;
import org.example.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Menu para gerir séries: listar, adicionar séries, temporadas e episódios.
 * Acessível apenas pelo Administrador.
 * Ao adicionar uma série, é obrigatório associar pelo menos um género.
 */
public class MenuGerirSeries {
    private DB imdb;
    private String opcao;

    /**
     * Cria o menu de gestão de séries.
     * @param imdb a base de dados da aplicação
     */
    public MenuGerirSeries(DB imdb) {
        this.imdb = imdb;
    }

    /**
     * Executa o menu em loop até o utilizador escolher voltar.
     */
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
            System.out.println("#  5. Associar ator a episódio                  #");
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
                case "5":
                    associarAtorAEpisodio();
                case "0":
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (!opcao.equals("0"));
    }


    /**
     * Lê os dados de uma nova série (título, descrição, ano e géneros)
     * e adiciona-a à base de dados após confirmação.
     * Cancela a operação se não for escolhido nenhum género.
     */
    private void adicionarSerie() {
        System.out.println("\n--- Adicionar Série ---");
        String titulo = Utils.readLineFromConsole("Título: ");
        String descricao = Utils.readLineFromConsole("Descrição: ");
        String ano = Utils.readLineFromConsole("Ano de lançamento: ");
        List<Genero> generos = escolherGeneros();

        if (generos.isEmpty()) {
            System.out.println("Uma série deve ter pelo menos um género. Operação cancelada.");
            return;
        }

        Serie nova = new Serie(titulo, descricao, ano, generos);
        System.out.println("\nSérie a adicionar: " + nova);

        if (Utils.confirma("Confirma? (S/N)")) {
            imdb.adicionarRecurso(nova);
            System.out.println("Série adicionada com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }


    /**
     * Pede ao utilizador que escolha uma série existente e adiciona-lhe
     * uma nova temporada após confirmação.
     */
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

    /**
     * Pede ao utilizador que escolha uma série e uma temporada,
     * e adiciona um novo episódio a essa temporada após confirmação.
     */
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


    /**
     * Permite associar um ator existente a um episódio de uma série.
     * Guia o utilizador pela escolha da série, temporada, episódio e ator.
     */
    private void associarAtorAEpisodio() {
        Serie serie = escolherSerie();
        if (serie == null) return;

        if (serie.getTemporadas().isEmpty()) {
            System.out.println("Esta série não tem temporadas.");
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

        if (temporada.getEpisodios().isEmpty()) {
            System.out.println("Esta temporada não tem episódios.");
            return;
        }

        System.out.println("Episódios disponíveis:");
        for (int i = 0; i < temporada.getEpisodios().size(); i++) {
            System.out.println("  " + (i + 1) + ". " + temporada.getEpisodios().get(i));
        }

        int numEp = Utils.readIntFromConsole("Escolha o número do episódio: ");
        if (numEp < 1 || numEp > temporada.getEpisodios().size()) {
            System.out.println("Episódio inválido.");
            return;
        }

        Episodios episodio = temporada.getEpisodios().get(numEp - 1);

        if (imdb.getLstAtores().isEmpty()) {
            System.out.println("Não existem atores registados. Adicione atores primeiro.");
            return;
        }

        System.out.println(imdb.listarAtores());
        String nomeAtor = Utils.readLineFromConsole("Nome do ator a associar: ");

        Ator ator = imdb.pesquisaAtor(nomeAtor);
        if (ator == null) {
            System.out.println("Ator não encontrado.");
            return;
        }

        if (episodio.temAtor(ator)) {
            System.out.println("Este ator já está associado ao episódio \"" + episodio.getTitulo() + "\".");
            return;
        }

        episodio.adicionarAtor(ator);
        System.out.println("Ator \"" + ator.getNome() + "\" associado ao episódio \"" + episodio.getTitulo() + "\" com sucesso!");
    }



    /**
     * Apresenta a lista de séries e devolve a série cujo título é introduzido
     * pelo utilizador.
     * @return a série encontrada, ou null se não existir
     */
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


    /**
     * Apresenta os géneros disponíveis e permite ao utilizador escolher
     * um ou mais, um de cada vez.
     * É obrigatório escolher pelo menos um género.
     * @return lista de géneros escolhidos
     */
    private List<Genero> escolherGeneros() {
        Genero[] todos = Genero.values();
        List<Genero> escolhidos = new ArrayList<>();
        boolean adicionarMais = true;

        do {
            System.out.println("\nGéneros disponíveis:");
            for (int i = 0; i < todos.length; i++) {
                System.out.println("  " + (i + 1) + ". " + todos[i]);
            }

            int escolha = Utils.readIntFromConsole("Selecione o número do género: ");
            if (escolha >= 1 && escolha <= todos.length) {
                Genero selecionado = todos[escolha - 1];
                if (!escolhidos.contains(selecionado)) {
                    escolhidos.add(selecionado);
                    System.out.println("Género '" + selecionado + "' adicionado.");
                } else {
                    System.out.println("Esse género já foi selecionado.");
                }
            } else {
                System.out.println("Opção inválida.");
            }

            if (!escolhidos.isEmpty()) {
                adicionarMais = Utils.confirma("Deseja adicionar mais algum género? (S/N): ");
            } else {
                System.out.println("É obrigatório associar pelo menos um género.");
            }
        } while (adicionarMais || escolhidos.isEmpty());

        return escolhidos;
    }
}
