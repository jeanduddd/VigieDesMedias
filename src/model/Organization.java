package model;

import Exceptions.BuyPossessionException;
import Exceptions.SellPossessionException;
import util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe représentant une organisation pouvant posséder d'autres entités.
 */
public class Organization extends CanBePossessed {
    private final String comment;
    private final Map<CanBePossessed, Pair> owns = new HashMap<>();

    /**
     * Constructeur de Organization.
     *
     * @param name    le nom de l'organisation.
     * @param comment un commentaire sur l'organisation.
     */
    public Organization(String name, String comment) {
        super(name);
        this.comment = comment;
    }

    /**
     * Ajoute une possession à l'organisation.
     *
     * @param possession    l'entité à posséder.
     * @param qualification la qualification de la possession.
     * @param percentage    le pourcentage de possession.
     */
    public void addPossession(CanBePossessed possession,String qualification,Double percentage) {
        if (!(owns.containsKey(possession) || percentage==null || percentage<0 || percentage>100)) {
            owns.put(possession, new Pair(qualification,percentage));
        }
    }

    /**
     * Ajoute un pourcentage de possession à une entité déjà possédée ou l'ajoute comme nouvelle possession lors d'un achat.
     *
     * @param possession l'entité à posséder.
     * @param percent    le pourcentage de possession.
     * @throws BuyPossessionException si le pourcentage est invalide ou dépasse 100%.
     */
    public void addPossession(CanBePossessed possession, double percent) throws BuyPossessionException {
        if (percent < 0 || percent > 100) {
            throw new BuyPossessionException("achat impossible ( > 100% ou < 0% )");
        }
        if (owns.containsKey(possession)) {
            if (owns.get(possession).getPercentage() + percent <= 100) {
                owns.get(possession).setPercentage(owns.get(possession).getPercentage() + percent);
            } else {
                throw new BuyPossessionException("achat impossible ( > 100% )");
            }
        }
        else{
            owns.put(possession, new Pair(null,percent));
        }
    }

    /**
     * Vend un pourcentage de possession d'une entité ou la retire complètement si le pourcentage atteint 0%.
     *
     * @param possession l'entité à vendre.
     * @param percent    le pourcentage à vendre.
     * @throws SellPossessionException si le pourcentage est invalide ou si la vente est impossible.
     */
    public void sellPossession(CanBePossessed possession, double percent) throws SellPossessionException {
        if (percent < 0 || percent > 100) {
            throw new SellPossessionException( "vente impossible ( > 100% ou < 0% )");
        }
        if (owns.containsKey(possession)) {
            if (owns.get(possession).getPercentage() - percent == 0) {
                owns.remove(possession);
            }
            else if (owns.get(possession).getPercentage() - percent >= 0) {
                owns.get(possession).setPercentage(owns.get(possession).getPercentage() - percent);
            } else {
                throw new SellPossessionException("vente impossible ( < 0% )");
            }
        }
        else{
            throw new SellPossessionException("vente impossible ( "+ super.getName() +" ne possede pas " + possession+ " )");
        }
    }

    /**
     * Retourne les possessions de l'organisation.
     *
     * @return une map des possessions avec leurs qualifications et pourcentages.
     */
    public Map<CanBePossessed, Pair> getOwns() {
        return new HashMap<>(owns);
    }

    /**
     * Retourne le commentaire de l'organisation.
     *
     * @return une chaîne de caractères représentant le commentaire.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Retourne une représentation sous forme de chaîne de l'organisation.
     *
     * @return une chaîne de caractères représentant l'organisation.
     */
    @Override
    public String toString() {
        return toStringHelper("");
    }

    /**
     * Retourne une représentation sous forme de chaîne avec indentation.
     *
     * @param indent le niveau d'indentation.
     * @return une chaîne de caractères représentant l'organisation.
     */
    public String toStringHelper(String indent) {
        StringBuilder sb = new StringBuilder();
        if (owns.isEmpty()) {
            sb.append(indent).append(super.getName()).append("ne possède rien\n");
        }
        else {
            sb.append(indent).append(super.getName()).append(" possède :\n");
            for (CanBePossessed possession : owns.keySet()) {
                sb.append(indent).append("\t");
                if (Objects.equals(owns.get(possession).getQualification(), "égal à")) {
                    sb.append("- détient");
                } else {
                    sb.append(owns.get(possession).getQualification() == null ? "" : "- " + owns.get(possession).getQualification());
                }
                sb.append(owns.get(possession).getPercentage() == null ? "" : " à " + owns.get(possession).getPercentage() + "%");
                sb.append(" ").append(possession.getName()).append(" ( ").append(possession.getClass().getSimpleName()).append(" )\n");
                if (possession instanceof Organization) {
                    sb.append(possession.toStringHelper(indent + "\t"));
                }
            }
        }
        return sb.toString();
    }

    /**
     * Retourne le nom de l'organisation.
     *
     * @return une chaîne de caractères représentant le nom.
     */
    public String getName() {
        return super.getName();
    }
}
