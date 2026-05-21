package org.example.model;
import org.example.exceptions.JaVistoException;
public interface MarcavelComoVisto {

    boolean isVisto(Espectador espectador);
    void marcarComoVisto(Espectador espectador) throws JaVistoException;
}