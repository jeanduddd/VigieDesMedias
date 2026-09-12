package model;

import java.util.Objects;

/**
 * Classe représentant un média pouvant être possédé.
 */
public class Media extends CanBePossessed {
    private final String type;
    private final String periodicity;
    private final String scale;
    private final String price;
    private boolean disappeared;

    /**
     * Constructeur de Media.
     *
     * @param name         le nom du média.
     * @param type         le type du média.
     * @param periodicity  la périodicité du média.
     * @param scale        l'échelle du média.
     * @param price        le prix du média.
     * @param disappeared  indique si le média a disparu.
     */
    public Media(String name, String type, String periodicity, String scale, String price, boolean disappeared) {
        super(name);
        this.type = type;
        this.periodicity = periodicity;
        this.scale = scale;
        this.price = price;
        this.disappeared = disappeared;
    }

    /**
     * Retourne le type du média.
     *
     * @return une chaîne de caractères représentant le type.
     */
    public String getType() {
        return type;
    }

    /**
     * Retourne la périodicité du média.
     *
     * @return une chaîne de caractères représentant la périodicité.
     */
    public String getPeriodicity() {
        return periodicity;
    }

    /**
     * Retourne l'échelle du média.
     *
     * @return une chaîne de caractères représentant l'échelle.
     */
    public String getScale() {
        return scale;
    }

    /**
     * Retourne le prix du média.
     *
     * @return une chaîne de caractères représentant le prix.
     */
    public String getPrice() {
        return price;
    }

    /**
     * Indique si le média a disparu.
     *
     * @return un booléen indiquant si le média a disparu.
     */
    public boolean isDisappeared() {
        return disappeared;
    }

    /**
     * Met à jour l'état de disparition du média.
     *
     * @param disappeared le nouvel état de disparition.
     */
    public void setDisappeared(boolean disappeared) {
        this.disappeared = disappeared;
    }

    /**
     * Retourne une représentation sous forme de chaîne du média.
     *
     * @return une chaîne de caractères représentant le média.
     */
    @Override
    public String toString() {
        return toStringHelper("");
    }

    /**
     * Retourne une représentation sous forme de chaîne avec indentation.
     *
     * @param indent le niveau d'indentation.
     * @return une chaîne de caractères représentant le média.
     */
    public String toStringHelper(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent);
        sb.append(super.getName()).append(" est un média, ne possède rien\n");
        return sb.toString();
    }
}
