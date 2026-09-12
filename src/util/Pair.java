package util;

/**
 * Classe représentant une paire de deux objets avec une chaine de caractères qualification et un pourcentage.
 */
public class Pair {
    private String qualification;
    private Double percentage;

    /**
     * Constructeur de Pair.
     *
     * @param qualification la qualification associée.
     * @param percentage le pourcentage associé.
     */
    public Pair(String qualification,Double percentage) {
        this.qualification = qualification;
        this.percentage = percentage;
    }

    /**
     * Retourne la qualification.
     *
     * @return une chaîne de caractères représentant la qualification.
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * Retourne le pourcentage.
     *
     * @return un Double représentant le pourcentage.
     */
    public Double getPercentage() {
        return percentage;
    }

    /**
     * Met à jour le pourcentage.
     *
     * @param percentage le nouveau pourcentage.
     */
    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}
