package Events;

import model.Entity;

import java.util.Date;

/**
 * Représente un événement de publication.
 */
public class Publication extends Event{
    private final Date eventDate = new Date();
    private final Entity[] targetEntities;

    /**
     * Constructeur de Publication.
     *
     * @param eventName      le nom de l'événement.
     * @param source         l'entité à la source de l'événement.
     * @param targetEntities les entités ciblées par la publication.
     */
    public Publication(String eventName, Entity source, Entity[] targetEntities) {
        super(eventName, source);
        this.targetEntities = targetEntities;
    }

    /**
     * Retourne la date de l'événement.
     *
     * @return une instance de Date représentant la date de l'événement.
     */
    public Date getEventDate() {
        return eventDate;
    }

    /**
     * Retourne les entités cibles de la publication.
     *
     * @return un tableau d'entités représentant les cibles.
     */
    public Entity[] getTargetEntities() {
        return targetEntities;
    }
}
