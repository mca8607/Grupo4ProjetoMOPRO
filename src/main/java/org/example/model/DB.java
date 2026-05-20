package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class DB {
    private String url;
    private List<UtilizadorRegistado> lstUtilizadores;
    private List<Ator> lstAtores;
    private List<Recurso> lstRecursos; // Lista central para Filmes e Séries

    public DB(String url) {
        this.url = url;
        this.lstAtores = new ArrayList<>();
        this.lstUtilizadores = new ArrayList<>();
        this.lstRecursos = new ArrayList<>();
    }

    // --- Métodos de Adição ---
    public void adicionarAtor(Ator a) {
        this.lstAtores.add(a);
    }

    public void adicionarUtilizador(UtilizadorRegistado u) {
        this.lstUtilizadores.add(u);
    }

    public void adicionarRecurso(Recurso r) {
        this.lstRecursos.add(r);
    }

    // --- Métodos de Remoção ---
    public void removerAtor(Ator ator) {
        lstAtores.remove(ator);
    }

    // --- Métodos de Pesquisa e Login ---
    public UtilizadorRegistado pesquisaUtilizador(String username) {
        for (UtilizadorRegistado u : lstUtilizadores) {
            if (u.temNome(username)) {
                return u;
            }
        }
        return null;
    }

    public UtilizadorRegistado login(String username, String password) {
        UtilizadorRegistado ur = pesquisaUtilizador(username);
        // Verifica se o utilizador existe e se a password coincide
        if (ur != null && ur.temPassord(password)) {
            return ur;
        }
        return null;
    }

    public Ator pesquisaAtor(String nome) {
        for (Ator a : lstAtores) {
            if (a.temNome(nome)) {
                return a;
            }
        }
        return null;
    }

    // --- Métodos de Listagem ---
    public String listarUtilizadores() {
        StringBuilder sb = new StringBuilder("\nLista de Utilizadores:");
        if (lstUtilizadores.isEmpty()) {
            sb.append(" (VAZIA)\n");
        } else {
            for (UtilizadorRegistado u : lstUtilizadores) {
                // Identifica se é Admin ou Espectador na listagem
                sb.append("\n\t- ").append(u).append(u instanceof Admin ? " (admin)" : "");
            }
        }
        return sb.toString();
    }

    public String listarAtores() {
        StringBuilder sb = new StringBuilder("\nLista de Atores:");
        if (lstAtores.isEmpty()) {
            sb.append(" (VAZIA)\n");
        } else {
            for (Ator ator : lstAtores) {
                sb.append("\n\t- ").append(ator);
            }
        }
        return sb.toString();
    }

    public String listarFilmes() {
        StringBuilder sb = new StringBuilder("\nLista de Filmes:");
        boolean temFilmes = false;
        for (Recurso r : lstRecursos) {
            if (r instanceof Filme) {
                sb.append("\n\t- ").append(r);
                temFilmes = true;
            }
        }
        return temFilmes ? sb.toString() : "\nLista de Filmes: (VAZIA)";
    }

    public String listarSeries() {
        StringBuilder sb = new StringBuilder("\nLista de Séries:");
        boolean temSeries = false;
        for (Recurso r : lstRecursos) {
            if (r instanceof Serie) {
                sb.append("\n\t- ").append(r);
                temSeries = true;
            }
        }
        return temSeries ? sb.toString() : "\nLista de Séries: (VAZIA)";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("=== Estado atual da DB ===").append("\n");
        sb.append("URL: ").append(url).append("\n");
        sb.append(listarUtilizadores());
        sb.append(listarAtores());
        sb.append(listarFilmes());
        sb.append(listarSeries());
        return sb.toString();
    }
}