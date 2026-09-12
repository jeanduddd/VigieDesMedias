package Exceptions;

import java.io.IOException;

/**
 * Exception levée lorsqu'une erreur se produit lors de l'achat d'une possession.
 */
public class BuyPossessionException extends IOException {
    /**
     * Constructeur de BuyPossessionException.
     *
     * @param message le message décrivant l'erreur.
     */
    public BuyPossessionException(String message) {
        super(message);
    }
}
