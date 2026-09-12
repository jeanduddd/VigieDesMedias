package model;

/**
 * Classe représentant un lien entre un possesseur et une entité possédée.
 */
public class LinkPossessorPossessed {
    private final String origin;
    private final String qualification;
    private final Double value;
    private final String target;

    /**
     * Constructeur de LinkPossessorPossessed.
     *
     * @param origin       l'entité possédante.
     * @param qualification la qualification du lien.
     * @param value        la valeur associée au lien.
     * @param target       l'entité possédée.
     */
    public LinkPossessorPossessed(String origin, String qualification, Double value, String target) {
        this.origin = origin;
        this.qualification = qualification;
        this.value = value;
        this.target = target;
    }

    /**
     * Retourne l'entité possédante.
     *
     * @return une instance de `Entity` représentant le possesseur.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Retourne la qualification de l'entité.
     *
     * @return une chaîne de caractères représentant la qualification.
     */
    public String getQualification() {
        return qualification;
    }


    public Double getValue() {
        return value;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return origin + " " + qualification + " " + value + " " + target+"\n";
    }

}
