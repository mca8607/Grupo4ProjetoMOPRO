package org.example.ui;

import org.example.model.*;

public class Main {
    public static void main(String[] args) {
        DB imdb = null;
        MenuFonteInfo menuFonteInfo = new MenuFonteInfo(imdb);
        menuFonteInfo.run();
    }
}
