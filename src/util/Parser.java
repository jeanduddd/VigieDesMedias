package util;

import Events.ParserEvents.*;

import Exceptions.ParserException;
import model.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe utilitaire pour le parsing de données.
 */
public class Parser {

    /**
     * Parse un fichier pour créer une liste de médias.
     *
     * @return une liste d'objets Media.
     */
    public static ArrayList<Media> mediaParse(){
        ArrayList<Media> mediaList = new ArrayList<Media>();

        File doc = new File("Medias_francais/medias.tsv");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(doc));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null){
                String[] fields = line.split("\t", -1);

                String name = fields.length > 0 ? fields[0].trim() : null;
                String type = fields.length > 1 ? fields[1].trim() : null;
                String periodicity = fields.length > 2 ? fields[2].trim() : null;
                String scale = fields.length > 3 ? fields[3].trim() : null;
                String price = fields.length > 4 ? fields[4].trim() : null;
                boolean disappeared = (fields.length > 5) && (!fields[5].trim().isEmpty());

                Media media = new Media(
                        name,
                        type.isEmpty() ? null : type,
                        periodicity.isEmpty() ? null : periodicity,
                        scale.isEmpty() ? null : scale,
                        price.isEmpty() ? null : price,
                        disappeared
                );

                mediaList.add(media);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return mediaList;
    }

    /**
     * Parse un fichier pour créer une liste d'organisations.
     *
     * @return une liste d'objets Organisation.
     */
    public static ArrayList<Organization> organizationParse(){
        ArrayList<Organization> organizationList = new ArrayList<Organization>();

        File doc = new File("Medias_francais/organisations.tsv");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(doc));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null){
                String[] fields = line.split("\t", -1);

                String name = fields.length > 0 ? fields[0].trim() : null;
                String comment = fields.length > 1 ? fields[1].trim() : null;

                Organization organization = new Organization(
                        name,
                        comment

                );

                organizationList.add(organization);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return organizationList;
    }

    /**
     * Parse un fichier pour créer une liste de personnes.
     *
     * @return une liste d'objets Personne.
     */
    public static ArrayList<Person> personParse(){
        ArrayList<Person> personList = new ArrayList<Person>();

        File doc = new File("Medias_francais/personnes.tsv");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(doc));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null){
                String[] fields = line.split("\t", -1);

                String name = fields.length > 0 ? fields[0].trim() : null;
                Integer rangChallenges2024 = !fields[1].trim().isEmpty() ? Integer.parseInt(fields[1]) : null;
                Integer milliardaireForbes2024 = !fields[2].trim().isEmpty() ? Integer.parseInt(fields[2]) : null;
                Integer rangChallenges2023 = !fields[3].trim().isEmpty() ? Integer.parseInt(fields[3]) : null;
                Integer milliardaireForbes2023 = !fields[4].trim().isEmpty() ? Integer.parseInt(fields[4]) : null;
                Integer rangChallenges2022 = !fields[5].trim().isEmpty() ? Integer.parseInt(fields[5]) : null;
                Integer milliardaireForbes2022 = !fields[6].trim().isEmpty() ? Integer.parseInt(fields[6]) : null;
                Integer rangChallenges2021 = !fields[7].trim().isEmpty() ? Integer.parseInt(fields[7]) : null;
                Integer milliardaireForbes2021 = !fields[8].trim().isEmpty() ? Integer.parseInt(fields[8]) : null;

                Person person = new Person(
                        name,
                        rangChallenges2021,
                        rangChallenges2022,
                        rangChallenges2023,
                        rangChallenges2024,
                        milliardaireForbes2021,
                        milliardaireForbes2022,
                        milliardaireForbes2023,
                        milliardaireForbes2024
                );

                personList.add(person);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return personList;
    }

    /**
     * Parse un fichier pour créer une liste de lien entre entité qui peut posséder et entité possédable.
     *
     * @return une liste d'objets linkPossessorPossessed.
     */
    public static ArrayList<LinkPossessorPossessed> linkPossessorPossessedParse(String doc){
        ArrayList<LinkPossessorPossessed> possessorPossessedList = new ArrayList<LinkPossessorPossessed>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(doc));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null){
                String[] fields = line.split("\t", -1);

                String origin = fields.length > 1 ? fields[1].trim() : null;
                String qualification = fields.length > 2 ? fields[2].trim() : null;
                Double value = !fields[3].trim().isEmpty() ? Double.parseDouble(fields[3].replace("%","")) : null;
                String target = fields.length > 4 ? fields[4].trim() : null;

                LinkPossessorPossessed LPP = new LinkPossessorPossessed(
                        origin,
                        qualification,
                        value,
                        target

                );

                possessorPossessedList.add(LPP);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return possessorPossessedList;
    }

    /**
     * Parse une chaîne de caractères d'entrée pour créer un événement.
     *
     * @param input la chaîne de caractères d'entrée.
     * @return un objet ParserEvent représentant l'événement.
     * @throws ParserException si la chaîne d'entrée est mal formée ou si des arguments sont manquants.
     */
    public static ParserEvent parseInput(String input) throws ParserException{
        String[] eventArray = input.split("-");
        for (int i = 0; i < eventArray.length; i++) {
            eventArray[i] = eventArray[i].trim();
        }
        if (eventArray.length == 0){
            throw new ParserException("Commande avec arguments manquant");
        }
        switch (eventArray[0]) {
            case "vigie" -> {
                if (eventArray.length <3){
                    throw new ParserException("Commande avec arguments manquant");
                }
                System.out.println("Vigie");
                try {
                    return new VigieParserEvent(eventArray[0], eventArray[1], eventArray[2]);
                } catch (Exception e) {
                    throw new ParserException("Commande mal écrite");
                }
            }
            case "listener" -> {
                if (eventArray.length <3){
                    throw new ParserException("Commande avec arguments manquant");
                }
                try {
                    return new ListenerParserEvent(eventArray[0], eventArray[1], eventArray[2]);
                }
                catch (Exception e){
                    throw new ParserException("Commande mal écrite");
                }
            }
            case "event" -> {
                if (eventArray[1].equals("achat")) {
                    if (eventArray.length <5){
                        throw new ParserException("Commande avec arguments manquant");
                    }
                    try {
                        return new PurchaseParserEvent(eventArray[0], eventArray[1], eventArray[2], eventArray[3], Double.valueOf(eventArray[4]), eventArray[5]);
                    }
                    catch (Exception e) {
                        throw new ParserException("Commande mal écrite");
                    }
                }
                else if (eventArray[1].equals("article") || eventArray[1].equals("reportage") || eventArray[1].equals("interview")) {
                    if (eventArray.length <4){
                        throw new ParserException("Commande avec arguments manquant");
                    }
                    String[] targets = eventArray[3].split("/");
                    for (int i = 0; i < targets.length; i++) {
                        targets[i] = targets[i].trim();
                    }
                    try {
                        return new PublicationParserEvent(eventArray[0], eventArray[1], eventArray[2], targets);
                    }
                    catch (Exception e) {
                        throw new ParserException("commande mal écrite");
                    }


                }
            }
        }
        throw new ParserException("Commande non existante");
    }
}
