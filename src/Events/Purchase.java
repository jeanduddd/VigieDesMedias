package Events;

import model.CanBePossessed;
import model.Entity;

/**
 * Représente un événement d'achat.
 */
public class Purchase extends Event {
    private final CanBePossessed targetedPossession;
    private final Integer percentage;
    private final Entity target;

    /**
     * Constructeur de Purchase.
     *
     * @param eventName          le nom de l'événement.
     * @param source             l'entité qui achète.
     * @param targetedPossession l'entité achetée.
     * @param percentage         le pourcentage d'achat.
     * @param target             l'entité qui vend.
     */
    public Purchase(String eventName, Entity source, CanBePossessed targetedPossession, Integer percentage, Entity target) {
        super(eventName, source);
        this.targetedPossession = targetedPossession;
        this.percentage = percentage;
        this.target = target;
    }

    /**
     * Retourne le pourcentage d'achat.
     *
     * @return un entier représentant le pourcentage d'achat.
     */
    public Integer getPercentage() {
        return percentage;
    }

    /**
     * Retourne le vendeur.
     *
     * @return une instance d'Entity représentant le vendeur.
     */
    public Entity getTarget() {
        return target;
    }

    /**
     * Retourne l'entité achetée.
     *
     * @return une instance de CanBePossessed représentant l'entité achetée.
     */
    public CanBePossessed getTargetedPossession() {
        return targetedPossession;
    }
}
