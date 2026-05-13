package org.example.ui;

import org.example.model.Ator;
import org.example.model.DB;
import org.example.utils.Data;
import org.example.utils.Utils;

public class RegistarAtor {
    private DB imdb;

    public RegistarAtor(DB imdb) {
        this.imdb = imdb;
    }

    public void run() {

        System.out.println("Novo Ator:");

        Ator novoAtor = introduzDados();
        apresentaDados(novoAtor);
        if (Utils.confirma("Confirma os dados? (S/N)")) {
            imdb.adicionarAtor(novoAtor);
            System.out.println("Ator adicionado com sucesso");
            System.out.println(imdb.listarAtores());
        }
    }

    private void apresentaDados(Ator ator) {
        System.out.println(ator);
    }

    private static Ator introduzDados() {
        String nome = Utils.readLineFromConsole("Introduza o nome do ator: ");
        Data dataNascimento = Utils.readDateFromConsole("Introduza o data de nascimento: ");
        return new Ator(nome, dataNascimento);
    }
}
