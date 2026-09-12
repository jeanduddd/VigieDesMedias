package model;

import Exceptions.BuyPossessionException;
import Exceptions.SellPossessionException;
import util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe représentant une personne pouvant posséder des entités.
 */
public class Person extends Entity {
    private final Integer rangChallenges2024;
    private final Integer milliardaireForbes2024;
    private final Integer rangChallenges2023;
    private final Integer milliardaireForbes2023;
    private final Integer rangChallenges2022;
    private final Integer milliardaireForbes2022;
    private final Integer rangChallenges2021;
    private final Integer milliardaireForbes2021;
    private final Map<CanBePossessed, Pair> owns = new HashMap<>();


    /**
     * Constructeur de Person.
     *
     * @param name                   le nom de la personne.
     * @param rangChallenges2021     le rang Challenges 2021.
     * @param rangChallenges2022     le rang Challenges 2022.
     * @param rangChallenges2023     le rang Challenges 2023.
     * @param rangChallenges2024     le rang Challenges 2024.
     * @param milliardaireForbes2021 l'indicateur Forbes 2021.
     * @param milliardaireForbes2022 l'indicateur Forbes 2022.
     * @param milliardaireForbes2023 l'indicateur Forbes 2023.
     * @param milliardaireForbes2024 l'indicateur Forbes 2024.
     */
    public Person(String name,Integer rangChallenges2021,Integer rangChallenges2022,Integer rangChallenges2023, Integer rangChallenges2024,Integer milliardaireForbes2021,Integer milliardaireForbes2022,Integer milliardaireForbes2023,Integer milliardaireForbes2024) {
        super(name);
        this.rangChallenges2021 = rangChallenges2021;
        this.rangChallenges2022 = rangChallenges2022;
        this.rangChallenges2023 = rangChallenges2023;
        this.rangChallenges2024 = rangChallenges2024;
        this.milliardaireForbes2021 = milliardaireForbes2021;
        this.milliardaireForbes2022 = milliardaireForbes2022;
        this.milliardaireForbes2023 = milliardaireForbes2023;
        this.milliardaireForbes2024 = milliardaireForbes2024;
    }

    /**
     * Ajoute une possession à la personne.
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
     * Retourne une représentation sous forme de chaîne de la personne.
     *
     * @return une chaîne de caractères représentant la personne.
     */
    @Override
    public String toString() {
        return toStringHelper("");
    }

    /**
     * Retourne une représentation sous forme de chaîne avec indentation.
     *
     * @param indent le niveau d'indentation.
     * @return une chaîne de caractères représentant la personne.
     */
    public String toStringHelper(String indent) {
        StringBuilder sb = new StringBuilder();
        if (owns.isEmpty()) {
            sb.append(indent).append(super.getName()).append(" ne possède rien\n");
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
                if(possession instanceof Organization) {
                    sb.append(possession.toStringHelper(indent + "\t"));
                }

            }
        }
        return sb.toString();
    }

    /**
     * Retourne le nom de la personne.
     *
     * @return une chaîne de caractères représentant le nom.
     */
    public String getName() {
        return super.getName();
    }

    /**
     * Retourne les possessions de la personne.
     *
     * @return une map des possessions avec leurs qualifications et pourcentages.
     */
    public Map<CanBePossessed, Pair> getOwns() {
        return new HashMap<>(owns);
    }

    /**
     * Retourne le rang Challenges 2021.
     *
     * @return un entier représentant le rang.
     */
    public Integer getRangChallenges2021() {
        return rangChallenges2021;
    }

    /**
     * Retourne le rang Challenges 2022.
     *
     * @return un entier représentant le rang.
     */
    public Integer getRangChallenges2022() {
        return rangChallenges2022;
    }

    /**
     * Retourne le rang Challenges 2023.
     *
     * @return un entier représentant le rang.
     */
    public Integer getRangChallenges2023() {
        return rangChallenges2023;
    }

    /**
     * Retourne le rang Challenges 2024.
     *
     * @return un entier représentant le rang.
     */
    public Integer getRangChallenges2024() {
        return rangChallenges2024;
    }

    /**
     * Retourne l'indicateur Forbes 2021.
     *
     * @return un entier représentant l'indicateur.
     */
    public Integer getMilliardaireForbes2021() {
        return milliardaireForbes2021;
    }

    /**
     * Retourne l'indicateur Forbes 2022.
     *
     * @return un entier représentant l'indicateur.
     */
    public Integer getMilliardaireForbes2022() {
        return milliardaireForbes2022;
    }

    /**
     * Retourne l'indicateur Forbes 2023.
     *
     * @return un entier représentant l'indicateur.
     */
    public Integer getMilliardaireForbes2023() {
        return milliardaireForbes2023;
    }

    /**
     * Retourne l'indicateur Forbes 2024.
     *
     * @return un entier représentant l'indicateur.
     */
    public Integer getMilliardaireForbes2024() {
        return milliardaireForbes2024;
    }

}
