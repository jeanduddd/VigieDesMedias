package Events.ParserEvents;

/**
 * Classe abstraite représentant un événement de parsing.
 */
public abstract class ParserEvent {
    private final String typeOfCommand;

    /**
     * Constructeur de ParserEvent.
     *
     * @param type le type de commande associé à l'événement.
     */
    public ParserEvent(String type) {
        typeOfCommand = type;
    }

    /**
     * Retourne le type de commande associé à l'événement.
     *
     * @return une chaîne de caractères représentant le type de commande.
     */
    public String getType() {
        return typeOfCommand;
    }
}
