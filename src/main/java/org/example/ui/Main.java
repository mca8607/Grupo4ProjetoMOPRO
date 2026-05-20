package org.example.ui;

import org.example.model.*;

public class Main {
    public static void main(String[] args) {
        // CORREÇÃO: Em vez de null, criamos o objeto DB
        DB imdb = new DB("www.imdb.com");

        MenuFonteInfo menuFonteInfo = new MenuFonteInfo(imdb);
        menuFonteInfo.run();

        // Se quiseres testar a série aqui, certifica-te que os parâmetros batem certo
        Serie serie = new Serie("Stranger things", "muito fixe", "2008");
        serie.exibeFicha();
    }
}