package Events.ParserEvents;

/**
 * Représente un événement de parsing lié à un achat.
 */
public class PurchaseParserEvent extends ParserEvent {
    private final String typeOfPurchase;
    private final String nameOfTheBuyer;
    private final String nameOfTheBoughtEntity;
    private final Double percent;
    private final String nameOfTheSeller;

    /**
     * Constructeur de PurchaseParserEvent.
     *
     * @param type                le type de l'événement.
     * @param typeOfPurchase      le type de l'achat.
     * @param nameOfTheBuyer      le nom de l'acheteur.
     * @param nameOfTheBoughtEntity le nom de l'entité achetée.
     * @param percent             le pourcentage d'achat.
     * @param nameOfTheSeller     le nom du vendeur.
     */
    public PurchaseParserEvent(String type ,String typeOfPurchase, String nameOfTheBuyer,String nameOfTheBoughtEntity, Double percent, String nameOfTheSeller) {
        super(type);
        this.typeOfPurchase = typeOfPurchase;
        this.nameOfTheBuyer = nameOfTheBuyer;
        this.nameOfTheBoughtEntity = nameOfTheBoughtEntity;
        this.percent = percent;
        this.nameOfTheSeller = nameOfTheSeller;
    }

    /**
     * Retourne le nom de l'acheteur.
     *
     * @return une chaîne de caractères représentant l'acheteur.
     */
    public String getNameOfTheBuyer() {
        return nameOfTheBuyer;
    }

    /**
     * Retourne le pourcentage d'achat.
     *
     * @return un Double représentant le pourcentage acheté.
     */
    public Double getPercent() {
        return percent;
    }

    /**
     * Retourne le nom du vendeur.
     *
     * @return une chaîne de caractères représentant le vendeur.
     */
    public String getNameOfTheSeller() {
        return nameOfTheSeller;
    }

    /**
     * Retourne le type d'achat.
     *
     * @return une chaîne de caractères représentant le type de l'achat.
     */
    public String getTypeOfPurchase() {
        return typeOfPurchase;
    }

    /**
     * Retourne le nom de l'entité achetée.
     *
     * @return une chaîne de caractères représentant l'entité achetée.
     */
    public String getNameOfTheBoughtEntity() {
        return nameOfTheBoughtEntity;
    }
}
