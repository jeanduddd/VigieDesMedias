package Listeners;

import Events.Event;

/**
 * Interface représentant un écouteur d'événements.
 */
public interface EventListener {
    /**
     * Méthode appelée lorsqu'un événement est reçu.
     *
     * @param event l'événement à traiter.
     */
    void onEvent(Event event);

}
