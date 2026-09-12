package Listeners;

import Events.Event;
import Events.Publication;
import Events.VigieEvent;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant un Listener pour une personne.
 */
public class PersonListener extends Listener {
    private final Vigie vigie;
    private final Person person;
    private final ArrayList<Publication> publicationsAboutThePerson = new ArrayList<>();
    private final HashMap<Media,Integer> publicationPerMedia = new HashMap<>();
    private final HashMap<Media,Double> publicationRate  = new HashMap<>();

    /**
     * Constructeur de PersonListener.
     *
     * @param vigie  l'instance de Vigie associée.
     * @param person la personne suivie.
     */
    public PersonListener(Vigie vigie,Person person) {
        this.vigie = vigie;
        this.person = person;
    }

    /**
     * Retourne le nom de la personne suivie.
     *
     * @return une chaîne de caractères représentant le nom de la personne.
     */
    public String GetFollowedPersonName() {
        return person.getName();
    }

    /**
     * Retourne les publications concernant la personne suivie.
     *
     * @return une liste de publications.
     */
    public ArrayList<Publication> GetPublicationsAboutThePerson() {
        return new ArrayList<>(publicationsAboutThePerson);
    }

    /**
     * Retourne les publications par média.
     *
     * @return une HashMap associant les médias au nombre de publications de cette personne.
     */
    public HashMap<Media,Integer> GetPublicationsPerMedia() {
        return new HashMap<>(publicationPerMedia);
    }

    /**
     * Retourne le taux de publication par média.
     *
     * @return une HashMap associant les médias au taux de publication sur cette personne.
     */
    public HashMap<Media,Double> GetPublicationRate() {
        return new HashMap<>(publicationRate);
    }

    /**
     * Met à jour le taux de publication sur la personne par média.
     */
    private void updatePublicationRate(){
        int numberOfPublication = publicationsAboutThePerson.size();
        for (Media media : publicationPerMedia.keySet()) {
            publicationRate.put(media, (double) (publicationPerMedia.get(media)/numberOfPublication));
        }
    }

    /**
     * Vérifie si une personne possède un média donné.
     *
     * @param source la personne source.
     * @param target le média cible.
     * @return true si la personne possède le média, false sinon.
     */
    private boolean possessMedia(Person source, Media target) {
        for (CanBePossessed possessed : source.getOwns().keySet()) {
            if (possessed.getName().equals(target.getName())) {
                return true;
            } else if (possessed instanceof Organization) {
                if (possessMediaRecursive((Organization) possessed, target)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Vérifie récursivement si une organisation possède un média donné.
     *
     * @param org    l'organisation source.
     * @param target le média cible.
     * @return true si l'organisation possède le média, false sinon.
     */
    private boolean possessMediaRecursive(Organization org, Media target) {
        for (CanBePossessed possessed : org.getOwns().keySet()) {
            if (possessed.getName().equals(target.getName())) {
                return true;
            } else if (possessed instanceof Organization) {
                if (possessMediaRecursive((Organization) possessed, target)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gère un événement reçu.
     *
     * @param event l'événement à traiter.
     */
    @Override
    public void onEvent(Event event){
        if (event instanceof Publication) {
            boolean targeted=false;
            for (Entity entity : ((Publication) event).getTargetEntities()){
                if (entity.getName().equals(person.getName())) { targeted = true;}
            }
            if (targeted) {
                publicationsAboutThePerson.add((Publication) event);
                if (publicationPerMedia.containsKey((Media) event.getSourceName())) {
                    publicationPerMedia.put((Media) event.getSourceName(), publicationPerMedia.get((Media) event.getSourceName()) + 1);
                } else {
                    publicationPerMedia.put((Media) event.getSourceName(), 1);
                }
                updatePublicationRate();
                if (possessMedia(person, (Media) event.getSourceName())) {
                    vigie.onEvent(new VigieEvent(person.getName()+ " a été mentionné dans un média qu'il possède", person));
                }
            }
        }
    }
}

