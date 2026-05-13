package org.example.ui;

import org.example.model.Ator;
import org.example.model.DB;
import org.example.utils.Utils;

public class RemoverAtor {
    private DB imdb;

    public RemoverAtor(DB imdb) {
        this.imdb = imdb;
    }

    public void run() {
        System.out.println("Remover ator");
        Ator ator = introduzDados();
        apresentaDados(ator);
        if (Utils.confirma("Confirma que pretende remover? (S/N)")) {
            imdb.removerAtor(ator);
            System.out.println("Ator eliminado com sucesso");
            System.out.println(imdb.listarAtores());
        }
    }

    private void apresentaDados(Ator ator) {
        System.out.println(ator);
    }

    private Ator introduzDados() {
        System.out.println(imdb.listarAtores());
        String nomeRemover = Utils.readLineFromConsole("Escolha o ator a remover (introduza o nome): ");
        return imdb.pesquisaAtor(nomeRemover);
    }
}
