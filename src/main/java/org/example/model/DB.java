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

/**
 * Classe principal que representa a base de dados da plataforma.
 * Gere utilizadores, atores e recursos (filmes e séries).
 * Suporta serialização para persistência de dados em ficheiro.
 */
public class DB implements Serializable{
    /** URL identificador da base de dados. */
    private String url;
    /** Lista de utilizadores registados (admins e espectadores). */
    private List<UtilizadorRegistado> lstUtilizadores;
    /** Lista de atores registados. */
    private List<Ator> lstAtores;
    /** Lista central de recursos (filmes e séries). */
    private List<Recurso> lstRecursos;


    /**
     * Cria uma nova base de dados.
     * @param url identificador ou URL da base de dados
     */
    public DB(String url) {
        this.url = url;
        this.lstAtores = new ArrayList<>();
        this.lstUtilizadores = new ArrayList<>();
        this.lstRecursos = new ArrayList<>();
    }

    // --- Métodos de Adição ---


    /**
     * Adiciona um ator à base de dados.
     * @param a ator a adicionar
     */
    public void adicionarAtor(Ator a) {
        this.lstAtores.add(a);
    }


    /**
     * Adiciona um utilizador à base de dados.
     * @param u utilizador a adicionar
     */
    public void adicionarUtilizador(UtilizadorRegistado u) {
        this.lstUtilizadores.add(u);
    }


    /**
     * Adiciona um recurso (filme ou série) à base de dados.
     * Não permite duplicados: dois recursos com o mesmo título e ano
     * são considerados duplicados e não são adicionados.
     * @param novoRecurso recurso a adicionar
     */

    public void adicionarRecurso(Recurso novoRecurso) {
        for (Recurso r : lstRecursos) {
            if (r.getTitulo().equalsIgnoreCase(novoRecurso.getTitulo()) &&
                    r.getDataLancamento().equals(novoRecurso.getDataLancamento())) {
                System.out.println("ERRO: Já existe um filme/série com o título \"" + r.getTitulo() + "\" para o ano " + r.getDataLancamento() + ".");
                return;
            }
        }
        this.lstRecursos.add(novoRecurso);
    }

    // --- Métodos de Remoção ---


    /**
     * Remove um ator da base de dados.
     * @param ator ator a remover
     */
    public void removerAtor(Ator ator) {
        lstAtores.remove(ator);
    }

    // --- Métodos de Pesquisa e Login ---

    /**
     * Pesquisa um utilizador pelo nome (username).
     * @param username nome do utilizador
     * @return o utilizador encontrado, ou null se não existir
     */
    public UtilizadorRegistado pesquisaUtilizador(String username) {
        for (UtilizadorRegistado u : lstUtilizadores) {
            if (u.temNome(username)) {
                return u;
            }
        }
        return null;
    }


    /**
     * Autentica um utilizador com username e password.
     * @param username nome de utilizador
     * @param password palavra-passe
     * @return o utilizador autenticado, ou null se as credenciais forem inválidas
     */
    public UtilizadorRegistado login(String username, String password) {
        UtilizadorRegistado ur = pesquisaUtilizador(username);
        if (ur != null && ur.temPassord(password)) {
            return ur;
        }
        return null;
    }


    /**
     * Pesquisa um ator pelo nome exato.
     * @param nome nome do ator
     * @return o ator encontrado, ou null se não existir
     */
    public Ator pesquisaAtor(String nome) {
        for (Ator a : lstAtores) {
            if (a.temNome(nome)) {
                return a;
            }
        }
        return null;
    }

    // --- Métodos de Listagem ---

    /**
     * Devolve uma representação textual de todos os utilizadores.
     * @return string com a lista de utilizadores
     */
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


    /**
     * Devolve uma representação textual de todos os atores.
     * @return string com a lista de atores
     */
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


    /**
     * Devolve uma representação textual de todos os filmes.
     * @return string com a lista de filmes
     */
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


    /**
     * Devolve uma representação textual de todas as séries.
     * @return string com a lista de séries
     */
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


    // --- Métodos de Listagem Ordenada ---

    /**
     * Extrai apenas os filmes da lista de recursos.
     * @return lista de filmes
     */
    private List<Filme> extrairFilmes() {
        List<Filme> filmes = new ArrayList<>();
        for (Recurso r : lstRecursos) {
            if (r instanceof Filme) {
                filmes.add((Filme) r);
            }
        }
        return filmes;
    }


    /**
     * Devolve os filmes ordenados alfabeticamente por título (A-Z).
     * @return string com os filmes ordenados por título
     */
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



    /**
     * Devolve os filmes ordenados por classificação média (do maior para o menor).
     * @return string com os filmes ordenados por classificação média
     */
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


    /**
     * Devolve os atores ordenados alfabeticamente por nome (A-Z).
     * @return string com os atores ordenados por nome
     */
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


    /**
     * Devolve os atores ordenados pelo número de filmes em que participaram (do maior para o menor).
     * @return string com os atores ordenados por número de filmes
     */
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


    /**
     * Devolve os utilizadores ordenados pelo número de filmes vistos (do maior para o menor).
     * @return string com os utilizadores ordenados por filmes vistos
     */
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

    // --- Serialização ---


    /**
     * Guarda o estado atual da base de dados num ficheiro binário.
     * @param ficheiro caminho do ficheiro onde guardar
     * @throws IOException se ocorrer um erro ao escrever o ficheiro
     */
    public void guardar(String ficheiro) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro));
        oos.writeObject(this);
        oos.close();
    }



    /**
     * Carrega uma base de dados a partir de um ficheiro binário previamente guardado.
     * @param ficheiro caminho do ficheiro a ler
     * @return a base de dados carregada
     * @throws IOException            se ocorrer um erro ao ler o ficheiro
     * @throws ClassNotFoundException se a classe não for encontrada durante a desserialização
     */
    public static DB carregar(String ficheiro) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro));
        DB db = (DB) ois.readObject();
        ois.close();
        return db;
    }

    // --- Getters ---


    /**
     * Devolve o URL da base de dados.
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Devolve a lista de todos os utilizadores.
     * @return lista de utilizadores
     */
    public List<UtilizadorRegistado> getLstUtilizadores() {
        return lstUtilizadores;
    }


    /**
     * Devolve a lista de todos os atores.
     * @return lista de atores
     */
    public List<Ator> getLstAtores() {
        return lstAtores;
    }


    /**
     * Devolve a lista de todos os recursos (filmes e séries).
     * @return lista de recursos
     */
    public List<Recurso> getLstRecursos() {
        return lstRecursos;
    }

    /**
     * Devolve uma representação textual do estado atual da base de dados.
     * @return string com utilizadores, atores, filmes e séries
     */
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