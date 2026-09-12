package Events;

import model.Entity;

/**
 * Représente un événement lié à Vigie.
 */
public class VigieEvent extends Event {

    /**
     * Constructeur de VigieEvent.
     *
     * @param eventName le nom de l'événement.
     * @param entity    l'entité associée à l'événement.
     */
    public VigieEvent(String eventName, Entity entity) {
        super(eventName, entity);
    }


}
