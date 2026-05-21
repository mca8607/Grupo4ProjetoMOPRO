package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class UtilizadorRegistado implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String nome;
    private String password;

    private List<Object> listaPersonal;
    private List<Object> listaVistos;

    public UtilizadorRegistado(String email, String nome, String password) {
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.listaPersonal = new ArrayList<>();
        this.listaVistos = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public boolean temPassord(String pass) {
        return password.equals(pass);
    }

    public boolean temNome(String username) {
        return username.equals(nome);
    }

    public void adicionarFilmeListaPessoal(Filme filme) {
        if (!listaPersonal.contains(filme)) {
            listaPersonal.add(filme);
        }
    }

    public void adicionarEpisodioListaPessoal(Episodios episodio) {
        if (!listaPersonal.contains(episodio)) {
            listaPersonal.add(episodio);
        }
    }

    public void removerFilmeListaPessoal(Filme filme) {
        listaPersonal.remove(filme);
    }

    public void removerEpisodioListaPessoal(Episodios episodio) {
        listaPersonal.remove(episodio);
    }

    public String consultarListaPessoal() {
        if (listaPersonal.isEmpty()) {
            return nome + " não tem itens na lista pessoal.";
        }
        String texto = "Lista pessoal de " + nome + ":\n";
        for (Object item : listaPersonal) {
            texto = texto + "\t- " + item + "\n";
        }
        return texto;
    }

    public void adicionarVisto(Object item) {
        if (!listaVistos.contains(item)) {
            listaVistos.add(item);
        }
    }

    public boolean jaViu(Object item) {
        return listaVistos.contains(item);
    }

    public int getNumFilmesVistos() {
        int count = 0;
        for (Object item : listaVistos) {
            if (item instanceof Filme) count++;
        }
        return count;
    }

    @Override
    public String toString() {
        return nome + " <" + email + ">";
    }
}