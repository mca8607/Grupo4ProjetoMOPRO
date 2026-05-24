package org.example.ui;

import org.example.model.DB;

/**
 * Classe principal da aplicação.
 * Inicializa a base de dados e lança o {@link MenuFonteInfo},
 * onde o utilizador pode carregar dados demo, carregar de ficheiro
 * ou avançar para o menu principal.
 */
public class Main {

    /**
     * Método de entrada da aplicação.
     * Cria a base de dados e abre o menu de fonte de informação.
     *
     * @param args argumentos da linha de comandos (não utilizados)
     */
    public static void main(String[] args) {
        DB imdb = new DB("www.imdb.com");
        MenuFonteInfo menuFonteInfo = new MenuFonteInfo(imdb);
        menuFonteInfo.run();
    }
}