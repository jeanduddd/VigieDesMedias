package Exceptions;

import java.io.IOException;

/**
 * Exception levée lorsqu'une erreur se produit lors du parsing.
 */
public class ParserException extends IOException {
    /**
     * Constructeur de ParserException.
     *
     * @param message le message décrivant l'erreur.
     */
    public ParserException(String message) {
        super(message);
    }
}
