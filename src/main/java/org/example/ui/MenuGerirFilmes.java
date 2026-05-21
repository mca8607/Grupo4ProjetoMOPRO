package org.example.ui;

import org.example.model.DB;
import org.example.model.Filme;
import org.example.model.Recurso;
import org.example.utils.Utils;


public class MenuGerirFilmes {
    private DB imdb;
    private String opcao;


    public MenuGerirFilmes(DB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#               GERIR FILMES                    #");
            System.out.println("#################################################");
            System.out.println("#                                               #");
            System.out.println("#  1. Listar filmes                             #");
            System.out.println("#  2. Adicionar filme                           #");
            System.out.println("#  3. Remover filme                             #");
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
                    adicionarFilme();
                    break;
                case "3":
                    removerFilme();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (!opcao.equals("0"));
    }


    private void adicionarFilme() {
        System.out.println("\n--- Adicionar Filme ---");
        String titulo = Utils.readLineFromConsole("Título: ");
        String descricao = Utils.readLineFromConsole("Descrição: ");
        String ano = Utils.readLineFromConsole("Ano de lançamento: ");
        String duracao = Utils.readLineFromConsole("Duração (ex: 120 min): ");

        Filme novo = new Filme(titulo, descricao, ano, duracao);
        System.out.println("\nFilme a adicionar: " + novo);

        if (Utils.confirma("Confirma? (S/N)")) {
            imdb.adicionarRecurso(novo);
            System.out.println("Filme adicionado com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }


    private void removerFilme() {
        System.out.println(imdb.listarFilmes());
        String titulo = Utils.readLineFromConsole("Título do filme a remover: ");

        Recurso aRemover = null;
        for (Recurso r : imdb.getLstRecursos()) {
            if (r instanceof Filme && r.getTitulo().equalsIgnoreCase(titulo)) {
                aRemover = r;
                break;
            }
        }

        if (aRemover == null) {
            System.out.println("Filme não encontrado.");
            return;
        }

        System.out.println("Filme encontrado: " + aRemover);
        if (Utils.confirma("Confirma a remoção? (S/N)")) {
            imdb.getLstRecursos().remove(aRemover);
            System.out.println("Filme removido com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}