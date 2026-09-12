package Listeners;

import Events.Event;
import Events.Publication;
import Events.Purchase;
import Events.VigieEvent;
import model.Entity;
import model.Media;
import model.Organization;
import model.Person;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant un Listener pour un média.
 */
public class MediaListener extends Listener {
    private final Vigie vigie;
    private final Media media;
    private final HashMap<Entity,Integer> numberOfPublicationsForAnEntity = new HashMap<>();
    private final ArrayList<Purchase> purchaseHistory = new ArrayList<>();
    private final HashMap<String,Entity> ownerHistory = new HashMap<>();

    /**
     * Constructeur de MediaListener.
     *
     * @param vigie l'instance de Vigie associée.
     * @param media le média suivi.
     */
    public MediaListener(Vigie vigie, Media media) {
        this.vigie = vigie;
        this.media = media;
    }

    /**
     * Retourne le nom du média suivi.
     *
     * @return une chaîne de caractères représentant le nom du média.
     */
    public String getFollowedMediaName(){
        return media.getName();
    }

    /**
     * Retourne l'historique des achats du média.
     *
     * @return une liste des achats concernant le média.
     */
    public ArrayList<Purchase> getPurchaseHistory(){
        return new ArrayList<>(purchaseHistory);
    }

    /**
     * Retourne le nombre de publications par entité.
     *
     * @return une HashMap associant les entités au nombre de publications.
     */
    public HashMap<Entity,Integer> getNumberOfPublicationsForAnEntity(){
        return new HashMap<>(numberOfPublicationsForAnEntity);
    }

    /**
     * Retourne l'historique des propriétaires du média.
     *
     * @return une HashMap associant les noms des propriétaires aux entités correspondantes.
     */
    public HashMap<String,Entity> getOwnerHistory(){
        return new HashMap<>(ownerHistory);
    }

    /**
     * Met à jour le nombre de publications pour une entité donnée.
     *
     * @param entity l'entité concernée.
     */
    private void updateNumberOfPublicationsForAnEntity(Entity entity) {
        if (numberOfPublicationsForAnEntity.containsKey(entity)) {
            numberOfPublicationsForAnEntity.put(entity, numberOfPublicationsForAnEntity.get(entity) + 1);
        }
        else {
            numberOfPublicationsForAnEntity.put(entity, 1);
        }
    }

    /**
     * Gère un événement reçu.
     *
     * @param event l'événement à traiter.
     */
    @Override
    public void onEvent(Event event){
        if (event instanceof Publication) {
            if (media.getName().equals(event.getSourceName().getName())) {
                for (Entity entity : ((Publication) event).getTargetEntities()) {
                    updateNumberOfPublicationsForAnEntity(entity);
                }
                int numberOfPublication = numberOfPublicationsForAnEntity.values().stream().mapToInt(Integer::intValue).sum();
                if (numberOfPublication > 5) {
                    for (Entity entity : numberOfPublicationsForAnEntity.keySet()) {
                        double percent = (double) numberOfPublicationsForAnEntity.get(entity) / numberOfPublication;
                        if (percent > 0.5) {
                            vigie.onEvent(new VigieEvent("est mentionné plus de 50% du temps à " + media.getName(), entity));
                        }
                    }
                }
            }
        }
        else if (event instanceof Purchase) {
            if (media.getName().equals(((Purchase) event).getTargetedPossession().getName())) {
                purchaseHistory.add((Purchase) event);
                if (!ownerHistory.containsKey(event.getSourceName().getName())) {
                    if (event.getSourceName() instanceof Person personne) {
                        ownerHistory.put(event.getSourceName().getName(), personne);
                        vigie.onEvent(new VigieEvent(personne.getName() + " est devenu un nouveau propriétaire de " + media.getName(), media));
                    } else if (event.getSourceName() instanceof Organization orga) {
                        ownerHistory.put(event.getSourceName().getName(), orga);
                        vigie.onEvent(new VigieEvent(orga.getName() + " est devenu un nouveau propriétaire de " + media.getName(), media));
                    }
                }
            }
        }
    }
}
