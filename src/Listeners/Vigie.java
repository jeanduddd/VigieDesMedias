package Listeners;

import Events.Event;
import Events.VigieEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Vigie extends Listener {
    private final HashMap<String, MediaListener> SubscribeToMediaListeners = new HashMap<>();
    private final HashMap<String, PersonListener> SubscribeToPersonListeners = new HashMap<>();
    private final ArrayList<VigieEvent> alertHistory = new ArrayList<>();

    public Vigie() {}

    /**
     * Retourne une copie des abonnements aux MediaListeners.
     *
     * @return une copie de la HashMap contenant les MediaListeners suivis.
     */
    public HashMap<String, MediaListener> getSubscribeToMediaListeners() { return new HashMap<>(SubscribeToMediaListeners); }

    /**
     * Retourne une copie des abonnements aux PersonListeners.
     *
     * @return une copie de la HashMap contenant les PersonListeners suivis.
     */
    public HashMap<String, PersonListener> getSubscribeToPersonListeners() { return new HashMap<>(SubscribeToPersonListeners); }

    /**
     * Ajoute un Listener à la liste des abonnements suivis.
     *
     * @param l le Listener à ajouter (MediaListener ou PersonListener).
     */
    public void addFollowedListener(Listener l){
        if (l instanceof MediaListener) {
            if (!SubscribeToMediaListeners.containsKey(((MediaListener) l).getFollowedMediaName())) {
                SubscribeToMediaListeners.put(((MediaListener) l).getFollowedMediaName(), (MediaListener) l);
                System.out.println(SubscribeToMediaListeners.size() + " media listeners have been added");
            }
            else {
                System.out.println("Already Followed!");
            }
        }
        else if (l instanceof PersonListener) {
            if (!SubscribeToMediaListeners.containsKey(((PersonListener) l).GetFollowedPersonName())) {
                SubscribeToPersonListeners.put(((PersonListener) l).GetFollowedPersonName(), (PersonListener) l);
                System.out.println(SubscribeToPersonListeners.size() + " person listeners have been added");
            }
            else {
                System.out.println("Already Followed!");
            }
        }
    }

    /**
     * Supprime un Listener de la liste des abonnements suivis.
     *
     * @param l le Listener à supprimer (MediaListener ou PersonListener).
     */
    public void removeFollowedListener(Listener l){
        if (l instanceof MediaListener) {
            if ( !SubscribeToMediaListeners.containsKey(((MediaListener) l).getFollowedMediaName())){
                System.out.println("doesnt Follow!");
            }
            SubscribeToMediaListeners.remove(((MediaListener) l).getFollowedMediaName());
            System.out.println("media listeners have been removed");
        }
        else if (l instanceof PersonListener) {
            if (!SubscribeToPersonListeners.containsKey(((PersonListener) l).GetFollowedPersonName())){
                System.out.println("doesnt Follow!");
            }
            SubscribeToPersonListeners.remove(((PersonListener) l).GetFollowedPersonName());
            System.out.println("person listeners have been removed");
        }
    }

    /**
     * Retourne une HashMap contenant tous les Listeners suivis (MediaListeners et PersonListeners).
     *
     * @return une HashMap des Listeners suivis.
     */
    public HashMap<String, Listener> getFollowedListener(){
        HashMap<String, Listener> followedListener = new HashMap<>();
        followedListener.putAll(SubscribeToMediaListeners);
        followedListener.putAll(SubscribeToPersonListeners);
        return followedListener;
    }

    /**
     * Retourne l'historique des alertes sous forme de liste.
     *
     * @return une copie de la liste des VigieEvents enregistrés.
     */
    public ArrayList<VigieEvent> getAlertHistory(){
        return new ArrayList<>(alertHistory);
    }

    /**
     * Gère un événement reçu. Si l'événement est un VigieEvent et qu'il provient d'une source suivie,
     * il est ajouté à l'historique des alertes.
     *
     * @param event l'événement à traiter.
     */
    @Override
    public void onEvent(Event event){
        if (event instanceof VigieEvent){
            if (SubscribeToMediaListeners.containsKey(event.getSourceName().getName()) || SubscribeToPersonListeners.containsKey(event.getSourceName().getName())) {
                alertHistory.add((VigieEvent) event);
                System.out.println("nouvelle alerte : "+ event.getEventName());
            }
        }
    }
}
