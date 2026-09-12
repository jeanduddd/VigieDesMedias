package Exceptions;

import java.io.IOException;

/**
 * Exception levée lorsqu'une erreur se produit lors de la vente d'une possession.
 */
public class SellPossessionException extends IOException {
    /**
     * Constructeur de SellPossessionException.
     *
     * @param message le message décrivant l'erreur.
     */
    public SellPossessionException(String message) {
        super(message);
    }
}
