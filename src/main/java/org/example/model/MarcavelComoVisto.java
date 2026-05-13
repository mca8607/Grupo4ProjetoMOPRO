package org.example.model;

public interface MarcavelComoVisto {
    boolean isVisto(Espectador espectador);

    void marcarComoVisto(Espectador espectador) throws Exception;
}