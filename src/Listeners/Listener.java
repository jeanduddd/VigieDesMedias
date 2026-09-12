package Listeners;

import Events.Event;

/**
 * Classe abstraite représentant un Listener générique.
 */
public abstract class Listener {

    /**
     * Méthode abstraite appelée lorsqu'un événement est reçu.
     *
     * @param event l'événement à traiter.
     */
    public void onEvent(Event event){}
}
