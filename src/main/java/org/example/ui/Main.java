package org.example.ui;

import org.example.model.*;

public class Main {
    public static void main(String[] args) {
        DB imdb = new DB("www.imdb.com");
        MenuFonteInfo menuFonteInfo = new MenuFonteInfo(imdb);
        menuFonteInfo.run();
    }
}