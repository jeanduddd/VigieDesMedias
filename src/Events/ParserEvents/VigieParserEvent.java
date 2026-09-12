package Events.ParserEvents;

/**
 * Représente un événement de parsing lié à la Vigie.
 */
public class VigieParserEvent extends ParserEvent {
    private final String addOrRemove;
    private final String nameOfTheListened;

    /**
     * Constructeur de VigieParserEvent.
     *
     * @param type              le type de l'événement.
     * @param addOrRemove       indique si le suivi d'un listener est ajoutée ou supprimée.
     * @param nameOfTheListened le nom de l'entité suivie.
     */
    public VigieParserEvent(String type, String addOrRemove, String nameOfTheListened) {
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
     * @return une chaîne de caractères représentant le nom de l'entité.
     */
    public String getNameOfTheListened() {
        return nameOfTheListened;
    }
}
