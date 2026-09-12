package model;

/**
 * Classe abstraite représentant une entité avec un nom.
 */
public abstract class Entity {
    private final String name;

    /**
     * Constructeur de Entity.
     *
     * @param name le nom de l'entité.
     */
    public Entity(String name) {
        this.name = name;
    }

    /**
     * Retourne le nom de l'entité.
     *
     * @return une chaîne de caractères représentant le nom.
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne une représentation sous forme de chaîne de l'entité.
     *
     * @return une chaîne de caractères représentant l'entité.
     */
    @Override
    public String toString() {
        return null;
    }
}
