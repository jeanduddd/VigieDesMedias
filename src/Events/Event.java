package Events;

import model.Entity;

/**
 * Classe abstraite représentant un événement.
 */
public abstract class Event {
    private final String eventName;
    private final Entity sourceName;

    /**
     * Constructeur de la classe Event.
     *
     * @param eventName  le nom de l'événement.
     * @param sourceName l'entité à la source de l'événement.
     */
    Event(String eventName, Entity sourceName) {
        this.eventName = eventName;
        this.sourceName = sourceName;
    }

    /**
     * Retourne le nom de l'événement.
     *
     * @return une chaîne de caractères représentant le nom de l'événement.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Retourne l'entité à l'origine de l'événement.
     *
     * @return une instance de la classe Entity représentant l'entité à la source de l'événement.
     */
    public Entity getSourceName() {
        return sourceName;
    }
}
