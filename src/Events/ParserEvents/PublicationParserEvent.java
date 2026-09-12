package Events.ParserEvents;

/**
 * Représente un événement de parsing lié à une publication.
 */
public class PublicationParserEvent extends ParserEvent {
    private final String typeOfPublication;
    private final String nameOfTheSourceMedia;
    private final String[] nameOfTheTargets;

    /**
     * Constructeur de PublicationParserEvent.
     *
     * @param type                le type de l'événement.
     * @param typeOfPublication   le type de publication.
     * @param nameOfTheSourceMedia le nom du média source.
     * @param nameOfTheTargets    les noms des cibles de la publication.
     */
    public PublicationParserEvent(String type,String typeOfPublication, String nameOfTheSourceMedia, String[] nameOfTheTargets) {
        super(type);
        this.typeOfPublication = typeOfPublication;
        this.nameOfTheSourceMedia = nameOfTheSourceMedia;
        this.nameOfTheTargets = nameOfTheTargets;
    }

    /**
     * Retourne le nom du média source.
     *
     * @return une chaîne de caractères représentant le média source.
     */
    public String getNameOfTheSourceMedia() {
        return nameOfTheSourceMedia;
    }

    /**
     * Retourne les noms des cibles de la publication.
     *
     * @return un tableau de chaînes de caractères représentant les cibles.
     */
    public String[] getNameOfTheTargets() {
        return nameOfTheTargets;
    }

    /**
     * Retourne le type de publication.
     *
     * @return une chaîne de caractères représentant le type de publication.
     */
    public String getTypeOfPublication() {
        return typeOfPublication;
    }

}
