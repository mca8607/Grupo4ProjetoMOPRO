package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

public class DB implements Serializable{
    private String url;
    private List<UtilizadorRegistado> lstUtilizadores;
    private List<Ator> lstAtores;
    private List<Recurso> lstRecursos;

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


    private List<Filme> extrairFilmes() {
        List<Filme> filmes = new ArrayList<>();
        for (Recurso r : lstRecursos) {
            if (r instanceof Filme) {
                filmes.add((Filme) r);
            }
        }
        return filmes;
    }

    public String listarFilmesPorTitulo() {
        List<Filme> filmes = extrairFilmes();
        Collections.sort(filmes, new Comparator<Filme>() {
            @Override
            public int compare(Filme f1, Filme f2) {
                return f1.getTitulo().compareToIgnoreCase(f2.getTitulo());
            }
        });

        StringBuilder sb = new StringBuilder("\n--- Filmes Ordenados por Título ---");
        for (Filme f : filmes) sb.append("\n\t- ").append(f.getTitulo()).append(" (").append(f).append(")");
        return sb.toString();
    }

    public String listarFilmesPorClassificacaoMedia() {
        List<Filme> filmes = extrairFilmes();
        Collections.sort(filmes, new Comparator<Filme>() {
            @Override
            public int compare(Filme f1, Filme f2) {
                return Double.compare(f2.getClassificacaoMedia(), f1.getClassificacaoMedia());
            }
        });

        StringBuilder sb = new StringBuilder("\n--- Filmes Ordenados por Classificação Média ---");
        for (Filme f : filmes) {
            sb.append(String.format("\n\t- [%.1f/10] %s", f.getClassificacaoMedia(), f.getTitulo()));
        }
        return sb.toString();
    }

    public String listarAtoresPorNome() {
        List<Ator> atores = new ArrayList<>(lstAtores);
        Collections.sort(atores, new Comparator<Ator>() {
            @Override
            public int compare(Ator a1, Ator a2) {
                return a1.getNome().compareToIgnoreCase(a2.getNome());
            }
        });

        StringBuilder sb = new StringBuilder("\n--- Atores Ordenados por Nome ---");
        for (Ator a : atores) sb.append("\n\t- ").append(a);
        return sb.toString();
    }

    public String listarAtoresPorNumFilmes() {
        List<Ator> atores = new ArrayList<>(lstAtores);
        final DB self = this;
        Collections.sort(atores, new Comparator<Ator>() {
            @Override
            public int compare(Ator a1, Ator a2) {
                return Integer.compare(a2.getNumFilmes(self), a1.getNumFilmes(self));
            }
        });

        StringBuilder sb = new StringBuilder("\n--- Atores Ordenados por Número de Filmes ---");
        for (Ator a : atores) {
            sb.append("\n\t- ").append(a.getNome()).append(" | Filmes: ").append(a.getNumFilmes(self));
        }
        return sb.toString();
    }

    public String listarUtilizadoresPorFilmesVistos() {
        List<UtilizadorRegistado> utilizadores = new ArrayList<>(lstUtilizadores);
        Collections.sort(utilizadores, new Comparator<UtilizadorRegistado>() {
            @Override
            public int compare(UtilizadorRegistado u1, UtilizadorRegistado u2) {
                return Integer.compare(u2.getNumFilmesVistos(), u1.getNumFilmesVistos());
            }
        });

        StringBuilder sb = new StringBuilder("\n--- Utilizadores Ordenados por Mais Filmes Vistos ---");
        for (UtilizadorRegistado u : utilizadores) {
            sb.append("\n\t- ").append(u.getNome()).append(" | Vistos: ").append(u.getNumFilmesVistos()).append(" filmes");
        }
        return sb.toString();
    }


    public void guardar(String ficheiro) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro));
        oos.writeObject(this);
        oos.close();
    }

    public static DB carregar(String ficheiro) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro));
        DB db = (DB) ois.readObject();
        ois.close();
        return db;
    }


    public String getUrl() {
        return url;
    }

    public List<UtilizadorRegistado> getLstUtilizadores() {
        return lstUtilizadores;
    }

    public List<Ator> getLstAtores() {
        return lstAtores;
    }

    public List<Recurso> getLstRecursos() {
        return lstRecursos;
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