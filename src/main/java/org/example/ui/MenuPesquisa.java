package org.example.ui;

import org.example.model.Ator;
import org.example.model.Pesquisavel;
import org.example.model.Recurso;
import org.example.model.DB;
import org.example.utils.Utils;
import java.util.ArrayList;

public class MenuPesquisa {
    private DB imdb;

    public MenuPesquisa(DB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        System.out.println("\n--- Pesquisa de Conteúdos ---");
        String termo = Utils.readLineFromConsole("Introduza o título (filme/série) ou nome (ator): ");

        if (termo.trim().isEmpty()) {
            System.out.println("Pesquisa cancelada: termo vazio.");
            return;
        }

        efetuarPesquisa(termo);
    }

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