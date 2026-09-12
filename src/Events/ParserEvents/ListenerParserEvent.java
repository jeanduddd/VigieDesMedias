package Events.ParserEvents;

/**
 * Représente un événement de parsing lié à un Listener.
 */
public class ListenerParserEvent extends ParserEvent {
    private final String addOrRemove;
    private final String nameOfTheListened;

    /**
     * Constructeur de ListenerParserEvent.
     *
     * @param type              le type de l'événement.
     * @param addOrRemove       indique si un Listener est ajouté ou supprimé.
     * @param nameOfTheListened le nom de l'entité suivie.
     */
    public ListenerParserEvent(String type, String addOrRemove, String nameOfTheListened) {
        super(type);
        this.addOrRemove = addOrRemove;
        this.nameOfTheListened = nameOfTheListened;
    }

    /**
     * Retourne l'action effectuée (ajout ou suppression).
     *
     * @return une chaîne de caractères représentant l'action.
     */
    public String getAddOrRemove() {
        return addOrRemove;
    }

    /**
     * Retourne le nom de l'entité suivie.
     *
     * @return une chaîne de caractères représentant le nom de l'entité suivie.
     */
    public String getNameOfTheListened() {
        return nameOfTheListened;
    }

}
