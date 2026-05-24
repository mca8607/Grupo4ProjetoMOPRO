package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que representa um utilizador registado na plataforma.
 * Contém a lista pessoal de filmes e episódios e a lista de conteúdos vistos.
 * É a classe base para {@link Espectador} e {@link Admin}.
 */
public abstract class UtilizadorRegistado implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Endereço de email do utilizador. */
    private String email;

    /** Nome de utilizador (username). */
    private String nome;

    /** Palavra-passe do utilizador. */
    private String password;

    /** Lista pessoal de filmes e episódios guardados pelo utilizador. */
    private List<Object> listaPersonal;

    /** Lista de filmes e episódios marcados como vistos. */
    private List<Object> listaVistos;


    /**
     * Cria um novo utilizador registado.
     * @param email    endereço de email
     * @param nome     nome de utilizador (username)
     * @param password palavra-passe
     */
    public UtilizadorRegistado(String email, String nome, String password) {
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.listaPersonal = new ArrayList<>();
        this.listaVistos = new ArrayList<>();
    }

    /**
     * Devolve o endereço de email do utilizador.
     * @return email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Devolve o nome de utilizador (username).
     * @return nome
     */
    public String getNome() {
        return nome;
    }



    /**
     * Verifica se a password fornecida corresponde à do utilizador.
     * @param pass password a verificar
     * @return true se coincidir, false caso contrário
     */
    public boolean temPassord(String pass) {
        return password.equals(pass);
    }


    /**
     * Verifica se o username fornecido corresponde ao nome deste utilizador.
     * @param username nome a comparar
     * @return true se igual, false caso contrário
     */
    public boolean temNome(String username) {
        return username.equals(nome);
    }


    /**
     * Adiciona um filme à lista pessoal do utilizador.
     * Não adiciona o filme se já estiver na lista.
     * @param filme o filme a adicionar
     */
    public void adicionarFilmeListaPessoal(Filme filme) {
        if (!listaPersonal.contains(filme)) {
            listaPersonal.add(filme);
        }
    }


    /**
     * Adiciona um episódio à lista pessoal do utilizador.
     * Não adiciona o episódio se já estiver na lista.
     * @param episodio o episódio a adicionar
     */
    public void adicionarEpisodioListaPessoal(Episodios episodio) {
        if (!listaPersonal.contains(episodio)) {
            listaPersonal.add(episodio);
        }
    }


    /**
     * Remove um filme da lista pessoal do utilizador.
     * @param filme o filme a remover
     */
    public void removerFilmeListaPessoal(Filme filme) {
        listaPersonal.remove(filme);
    }


    /**
     * Remove um episódio da lista pessoal do utilizador.
     * @param episodio o episódio a remover
     */
    public void removerEpisodioListaPessoal(Episodios episodio) {
        listaPersonal.remove(episodio);
    }



    /**
     * Devolve uma representação textual da lista pessoal do utilizador.
     * @return string com os itens da lista pessoal, ou mensagem se estiver vazia
     */
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



    /**
     * Regista um item (Filme ou Episodios) como visto por este utilizador.
     * Chamado internamente após {@link MarcavelComoVisto#marcarComoVisto}.
     * @param item o item a marcar como visto
     */
    public void adicionarVisto(Object item) {
        if (!listaVistos.contains(item)) {
            listaVistos.add(item);
        }
    }



    /**
     * Verifica se um item já foi marcado como visto por este utilizador.
     * @param item o item a verificar
     * @return true se já foi visto, false caso contrário
     */
    public boolean jaViu(Object item) {
        return listaVistos.contains(item);
    }


    /**
     * Devolve o número de filmes marcados como vistos por este utilizador.
     * @return número de filmes vistos
     */
    public int getNumFilmesVistos() {
        int count = 0;
        for (Object item : listaVistos) {
            if (item instanceof Filme) count++;
        }
        return count;
    }


    /**
     * Devolve uma representação textual do utilizador com o nome e o email.
     * @return string com os dados do utilizador
     */
    @Override
    public String toString() {
        return nome + " <" + email + ">";
    }
}