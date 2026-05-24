package org.example.ui;

import org.example.model.DB;
import org.example.model.Filme;
import org.example.model.Recurso;
import org.example.utils.Utils;
import org.example.model.Genero;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu para gerir filmes: listar, adicionar e remover.
 * Acessível apenas pelo Administrador.
 * Ao adicionar um filme, é obrigatório associar pelo menos um género.
 */
public class MenuGerirFilmes {
    private DB imdb;
    private String opcao;


    /**
     * Cria o menu de gestão de filmes.
     * @param imdb a base de dados da aplicação
     */
    public MenuGerirFilmes(DB imdb) {
        this.imdb = imdb;
    }

    /**
     * Executa o menu em loop até o utilizador escolher voltar.
     */
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


    /**
     * Lê os dados de um novo filme (título, descrição, ano, duração e géneros)
     * e adiciona-o à base de dados após confirmação.
     * Cancela a operação se não for escolhido nenhum género.
     */
    private void adicionarFilme() {
        System.out.println("\n--- Adicionar Filme ---");
        String titulo = Utils.readLineFromConsole("Título: ");
        String descricao = Utils.readLineFromConsole("Descrição: ");
        String ano = Utils.readLineFromConsole("Ano de lançamento: ");
        String duracao = Utils.readLineFromConsole("Duração (ex: 120 min): ");


        List<Genero> generosEscolhidos = new ArrayList<>();
        boolean adicionarMais = true;

        do {
            System.out.println("\nGéneros Disponíveis:");
            Genero[] valores = Genero.values(); // Vai buscar todas as opções do enum
            for (int i = 0; i < valores.length; i++) {
                System.out.println((i + 1) + ". " + valores[i]);
            }

            int escolha = Utils.readIntFromConsole("Selecione o número do género: ");

            if (escolha >= 1 && escolha <= valores.length) {
                Genero selecionado = valores[escolha - 1];
                if (!generosEscolhidos.contains(selecionado)) {
                    generosEscolhidos.add(selecionado);
                    System.out.println("Género '" + selecionado + "' adicionado.");
                } else {
                    System.out.println("Esse género já foi selecionado anteriormente.");
                }
            } else {
                System.out.println("Opção inválida!");
            }

            // O enunciado exige pelo menos um género, por isso só validamos a saída se já houver algum selecionado
            if (!generosEscolhidos.isEmpty()) {
                adicionarMais = Utils.confirma("Deseja adicionar mais algum género? (S/N): ");
            } else {
                System.out.println("É obrigatório associar pelo menos um género ao filme.");
            }
        } while (adicionarMais || generosEscolhidos.isEmpty());


        Filme novo = new Filme(titulo, descricao, ano, duracao, generosEscolhidos);
        System.out.println("\nFilme a adicionar: " + novo);

        if (Utils.confirma("Confirma? (S/N)")) {
            imdb.adicionarRecurso(novo);
            System.out.println("Filme adicionado com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }


    /**
     * Lista os filmes existentes, pede o título do filme a remover
     * e remove-o da base de dados após confirmação.
     */
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