package org.example.ui;

import org.example.model.Ator;
import org.example.model.Pesquisavel;
import org.example.model.Recurso;
import org.example.model.DB;
import org.example.utils.Utils;
import java.util.ArrayList;

/**
 * Menu de pesquisa de conteúdos.
 * Permite pesquisar filmes e séries pelo título,
 * e atores pelo nome, utilizando a interface {@link Pesquisavel}.
 */
public class MenuPesquisa {
    private DB imdb;


    /**
     * Cria o menu de pesquisa.
     * @param imdb a base de dados da aplicação
     */
    public MenuPesquisa(DB imdb) {
        this.imdb = imdb;
    }


    /**
     * Lê o termo de pesquisa e apresenta os resultados.
     * Se o termo estiver vazio, cancela a pesquisa.
     */

    public void run() {
        System.out.println("\n--- Pesquisa de Conteúdos ---");
        String termo = Utils.readLineFromConsole("Introduza o título (filme/série) ou nome (ator): ");

        if (termo.trim().isEmpty()) {
            System.out.println("Pesquisa cancelada: termo vazio.");
            return;
        }

        efetuarPesquisa(termo);
    }


    /**
     * Efetua a pesquisa nos recursos e nos atores da base de dados.
     * Utiliza o método {@link Pesquisavel#correspondePesquisa(String)} de cada objeto.
     * @param termo texto a pesquisar
     */
    private void efetuarPesquisa(String termo) {
        ArrayList<Pesquisavel> resultados = new ArrayList<>();

        // Pesquisa nos Recursos (Filmes e Séries) recorrendo ao getter da DB
        for (Recurso r : imdb.getLstRecursos()) {
            if (r.correspondePesquisa(termo)) {
                resultados.add(r);
            }
        }

        // Pesquisa nos Atores recorrendo ao getter da DB
        for (Ator a : imdb.getLstAtores()) {
            if (a.correspondePesquisa(termo)) {
                resultados.add(a);
            }
        }

        apresentarResultados(resultados, termo);
    }


    /**
     * Apresenta os resultados da pesquisa no ecrã.
     * Indica o tipo de cada resultado (Filme, Serie ou Ator).
     * @param resultados lista de objetos que correspondem à pesquisa
     * @param termo      termo que foi pesquisado
     */
    private void apresentarResultados(ArrayList<Pesquisavel> resultados, String termo) {
        System.out.println("\nResultados para: '" + termo + "'");
        System.out.println("------------------------------------");

        if (resultados.isEmpty()) {
            System.out.println("Nenhum resultado encontrado.");
        } else {
            for (Pesquisavel p : resultados) {
                // p.getClass().getSimpleName() mostrará "Filme", "Serie" ou "Ator"
                System.out.println("[" + p.getClass().getSimpleName() + "] " + p.toString());
            }
            System.out.println("Total de resultados: " + resultados.size());
        }
        System.out.println("------------------------------------");
    }
}