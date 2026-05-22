package org.example.ui;

import org.example.model.DB;
import org.example.utils.Utils;

public class MenuListagens {
    private DB imdb;
    private String opcao;

    public MenuListagens(DB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        do {
            System.out.println("\n\n");
            System.out.println("#################################################");
            System.out.println("#             LISTAGENS ORDENADAS               #");
            System.out.println("#################################################");
            System.out.println("#                                               #");
            System.out.println("#  1. Filmes por Título                         #");
            System.out.println("#  2. Filmes por Classificação Média           #");
            System.out.println("#  3. Atores por Nome                           #");
            System.out.println("#  4. Atores por Número de Filmes               #");
            System.out.println("#  5. Utilizadores com Mais Filmes Vistos       #");
            System.out.println("#                                               #");
            System.out.println("#  0. Voltar                                    #");
            System.out.println("#################################################");
            System.out.println();

            opcao = Utils.readLineFromConsole("Escolha uma opção: ");

            switch (opcao) {
                case "1":
                    System.out.println(imdb.listarFilmesPorTitulo());
                    break;
                case "2":
                    System.out.println(imdb.listarFilmesPorClassificacaoMedia());
                    break;
                case "3":
                    System.out.println(imdb.listarAtoresPorNome());
                    break;
                case "4":
                    System.out.println(imdb.listarAtoresPorNumFilmes());
                    break;
                case "5":
                    System.out.println(imdb.listarUtilizadoresPorFilmesVistos());
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (!opcao.equals("0"));
    }
}