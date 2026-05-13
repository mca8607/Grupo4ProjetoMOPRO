package org.example.model;

public abstract class UtilizadorRegistado{
    private String email;
    private String nome;
    private String password;

    public UtilizadorRegistado(String email, String nome, String password) {
        this.email = email;
        this.nome = nome;
        this.password = password;
    }

    public boolean temPassord(String pass){
        return password.equals(pass);
    }

    @Override
    public String toString() {
        return nome + " <" + email + ">";
    }

    public boolean temNome(String username) { return username.equals(nome); }
}
