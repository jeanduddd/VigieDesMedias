package model;

/**
 * Classe abstraite représentant une entité pouvant être possédée.
 */
public abstract class CanBePossessed extends Entity {

    /**
     * Constructeur de CanBePossessed.
     *
     * @param name le nom de l'entité pouvant être possédée.
     */
    public CanBePossessed(String name) {
        super(name);
    }

    /**
     * Retourne le nom de l'entité.
     *
     * @return une chaîne de caractères représentant le nom.
     */
    public String getName() {
        return super.getName();
    }

    /**
     * Retourne une représentation sous forme de chaîne de l'entité avec un niveau d'indentation.
     *
     * @param indent le niveau d'indentation.
     * @return une chaîne de caractères représentant l'entité.
     */
    @Override
    public String toString() {
        return toStringHelper("");
    }

    /**
     * Retourne une représentation sous forme de chaîne de l'entité.
     *
     * @return une chaîne de caractères représentant l'entité.
     */
    public String toStringHelper(String indent) {
        return null;
    }
}
