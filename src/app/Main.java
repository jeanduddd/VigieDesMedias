package app;

import Events.Event;
import Events.ParserEvents.*;
import Events.Publication;
import Events.Purchase;
import Exceptions.BuyPossessionException;
import Exceptions.ParserException;
import Exceptions.SellPossessionException;
import Listeners.Listener;
import Listeners.MediaListener;
import Listeners.PersonListener;
import Listeners.Vigie;
import model.*;
import util.Parser;

import java.util.*;

/**
 * Classe principale de l'application.
 * Gère l'initialisation des entités, le traitement des commandes utilisateur et les événements.
 */
public class Main {

    public static void main(String[] args) {
        // Liste contenant toutes les entités (personnes, organisations, médias)
        ArrayList<Entity> EVERYONE = new ArrayList<>();

        // Parsing des entités à partir des fichiers
        ArrayList<Person> persons = Parser.personParse();
        ArrayList<Organization> organizations = Parser.organizationParse();
        ArrayList<Media> medias = Parser.mediaParse();

        // Ajout des entités à la liste globale
        EVERYONE.addAll(persons);
        EVERYONE.addAll(organizations);
        EVERYONE.addAll(medias);

        // Map pour accéder aux entités par leur nom
        Map<String, Entity> EVERYONEBYNAME = new HashMap<>();
        for (Entity entity : EVERYONE) {
            EVERYONEBYNAME.put(entity.getName(), entity);
        }

        // Fichiers contenant les liens entre possesseurs et entités possédées
        String[] links = {
                "Medias_francais/personne-organisation.tsv",
                "Medias_francais/personne-media.tsv",
                "Medias_francais/organisation-organisation.tsv",
                "Medias_francais/organisation-media.tsv"
        };

        // Parsing des liens et mise à jour des possessions
        for (String link : links) {
            ArrayList<LinkPossessorPossessed> personOrganization = Parser.linkPossessorPossessedParse(link);
            for (LinkPossessorPossessed possessor : personOrganization) {
                Entity source = EVERYONEBYNAME.get(possessor.getOrigin());
                Entity target = EVERYONEBYNAME.get(possessor.getTarget());
                if (source instanceof Organization organization && target instanceof CanBePossessed) {
                    organization.addPossession((CanBePossessed) target, possessor.getQualification(), possessor.getValue());
                }
                if (source instanceof Person person && target instanceof CanBePossessed) {
                    person.addPossession((CanBePossessed) target, possessor.getQualification(), possessor.getValue());
                }
            }
        }

        // Affichage initial des entités
        DisplayOnTerminal.DisplayOnTerminalByName(EVERYONE);

        // Gestion des listeners et de la Vigie
        HashMap<String, Listener> listenedWithListener = new HashMap<>();
        Vigie vigie = new Vigie();

        // Scanner pour lire les commandes utilisateur
        Scanner sc = new Scanner(System.in);
        String input = "";

        // Boucle principale pour traiter les commandes utilisateur
        start:
        while (true) {
            System.out.println("Entrez votre commande (\"commandes\" pour afficher les commandes disponibles\") :");
            input = sc.nextLine();
            if (Objects.equals(input, "exit")) {
                break;
            } else if (input.equals("afficher")) {
                DisplayOnTerminal.DisplayOnTerminalByName(EVERYONE);
                continue;
            } else if (input.equals("commandes")) {
                System.out.println("exit pour sortir");
                System.out.println("afficher");
                System.out.println("Format rachat : \"event - achat - nom personne qui achète - cible de l'achat - % - nom personne qui vend\"");
                System.out.println("Format publication/diffusion : \"event - type de publication/diffusion - média à l'origine - liste des personnes / organisations / médias mentionnées séparés par des /\"");
                System.out.println("Format ajout d'un module de suivi : \"listener - ajout - nom du média/personne\"");
                System.out.println("Format retrait d'un module de suivi : \"listener - retrait - nom du média/personne\"");
                System.out.println("Format ajout d'un suivi de la Vigie : \"vigie - ajout - nom du média/personne\"");
                System.out.println("Format retrait d'un suivi de la Vigie : \"vigie - retrait - nom du média/personne\"\n");
            } else {
                try {
                    // Parsing de la commande utilisateur
                    ParserEvent parserOutput = Parser.parseInput(input);

                    // Gestion des événements de la Vigie
                    if (parserOutput instanceof VigieParserEvent vigieOutput) {
                        // Traitement des commandes d'ajout ou de retrait de suivi par la Vigie
                        if (EVERYONEBYNAME.containsKey(vigieOutput.getNameOfTheListened())) {
                            if (vigieOutput.getAddOrRemove().equals("ajout")) {
                                if (!vigie.getFollowedListener().containsKey(vigieOutput.getNameOfTheListened())) {
                                    if (listenedWithListener.containsKey(vigieOutput.getNameOfTheListened())) {
                                        vigie.addFollowedListener(listenedWithListener.get(vigieOutput.getNameOfTheListened()));
                                        System.out.println("La vigie suit maintenant " + vigieOutput.getNameOfTheListened());
                                    } else {
                                        System.out.println("Le listener en question n'existe pas");
                                    }
                                } else {
                                    System.out.println("La vigie suit déjà le module de cette entité");
                                }
                            } else if (vigieOutput.getAddOrRemove().equals("retrait")) {
                                if (vigie.getFollowedListener().containsKey(vigieOutput.getNameOfTheListened())) {
                                    if (listenedWithListener.containsKey(vigieOutput.getNameOfTheListened())) {
                                        vigie.removeFollowedListener(listenedWithListener.get(vigieOutput.getNameOfTheListened()));
                                        System.out.println("Il a bien été enlevé");
                                    } else {
                                        System.out.println("Le listener en question n'existe pas");
                                    }
                                } else {
                                    System.out.println("La vigie ne suit pas le module de cette entité");
                                }
                            } else {
                                System.out.println("Ajout ou retrait mal écrit");
                            }
                        } else {
                            System.out.println("La personne cherchée n'existe pas");
                        }
                    }

                    // Gestion des événements de Listener
                    else if (parserOutput instanceof ListenerParserEvent listenerOutput) {
                        // Traitement des commandes d'ajout ou de retrait de Listener
                        if (EVERYONEBYNAME.containsKey(listenerOutput.getNameOfTheListened())) {
                            if (listenerOutput.getAddOrRemove().equals("ajout")) {
                                if (!listenedWithListener.containsKey(listenerOutput.getNameOfTheListened())) {
                                    if (EVERYONEBYNAME.get(listenerOutput.getNameOfTheListened()) instanceof Person) {
                                        listenedWithListener.put(listenerOutput.getNameOfTheListened(), new PersonListener(vigie, (Person) EVERYONEBYNAME.get(listenerOutput.getNameOfTheListened())));
                                        System.out.println("Le Listener a bien été créé");
                                    } else if (EVERYONEBYNAME.get(listenerOutput.getNameOfTheListened()) instanceof Media) {
                                        listenedWithListener.put(listenerOutput.getNameOfTheListened(), new MediaListener(vigie, (Media) EVERYONEBYNAME.get(listenerOutput.getNameOfTheListened())));
                                        System.out.println("Le Listener a bien été créé");
                                    } else {
                                        System.out.println("Le listener ne peut pas suivre autre chose qu'un média/personne");
                                    }
                                } else {
                                    System.out.println("Listener pour cette entité existe déjà");
                                }
                            } else if (listenerOutput.getAddOrRemove().equals("retrait")) {
                                if (listenedWithListener.containsKey(listenerOutput.getNameOfTheListened())) {
                                    if (EVERYONEBYNAME.get(listenerOutput.getNameOfTheListened()) instanceof Person) {
                                        if (vigie.getSubscribeToPersonListeners().containsKey(listenerOutput.getNameOfTheListened())) {
                                            vigie.removeFollowedListener(listenedWithListener.get(listenerOutput.getNameOfTheListened()));
                                        }
                                    }
                                    if (EVERYONEBYNAME.get(listenerOutput.getNameOfTheListened()) instanceof Media) {
                                        if (vigie.getSubscribeToMediaListeners().containsKey(listenerOutput.getNameOfTheListened())) {
                                            vigie.removeFollowedListener(listenedWithListener.get(listenerOutput.getNameOfTheListened()));
                                        }
                                    }
                                    listenedWithListener.remove(listenerOutput.getNameOfTheListened());
                                    System.out.println("Listener a bien été enlevé");
                                } else {
                                    System.out.println("Le listener de cette entité n'existe pas");
                                }
                            } else {
                                System.out.println("Ajout ou retrait mal écrit");
                            }
                        } else {
                            System.out.println("La personne cherchée n'existe pas");
                        }
                    }

                    // Gestion des événements d'achat
                    else if (parserOutput instanceof PurchaseParserEvent purchaseOutput) {
                        // Validation des entités impliquées dans l'achat
                        if (EVERYONEBYNAME.containsKey(purchaseOutput.getNameOfTheSeller()) &&
                                EVERYONEBYNAME.containsKey(purchaseOutput.getNameOfTheBuyer()) &&
                                EVERYONEBYNAME.containsKey(purchaseOutput.getNameOfTheBoughtEntity())) {
                            if (!(EVERYONEBYNAME.get(purchaseOutput.getNameOfTheSeller()) instanceof Media) &&
                                    !(EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBuyer()) instanceof Media) &&
                                    !(EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBoughtEntity()) instanceof Person)) {
                                if (EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBuyer()) != EVERYONEBYNAME.get(purchaseOutput.getNameOfTheSeller())) {
                                    try {
                                        // Réalisation de l'achat
                                        if (EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBuyer()) instanceof Person buyer &&
                                                EVERYONEBYNAME.get(purchaseOutput.getNameOfTheSeller()) instanceof Person seller) {
                                            seller.sellPossession((CanBePossessed) EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBoughtEntity()), purchaseOutput.getPercent());
                                            buyer.addPossession((CanBePossessed) EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBoughtEntity()), purchaseOutput.getPercent());
                                        } else if (EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBuyer()) instanceof Organization buyer &&
                                                EVERYONEBYNAME.get(purchaseOutput.getNameOfTheSeller()) instanceof Organization seller) {
                                            seller.sellPossession((CanBePossessed) EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBoughtEntity()), purchaseOutput.getPercent());
                                            buyer.addPossession((CanBePossessed) EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBoughtEntity()), purchaseOutput.getPercent());
                                        } else if (EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBuyer()) instanceof Person buyer &&
                                                EVERYONEBYNAME.get(purchaseOutput.getNameOfTheSeller()) instanceof Organization seller) {
                                            seller.sellPossession((CanBePossessed) EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBoughtEntity()), purchaseOutput.getPercent());
                                            buyer.addPossession((CanBePossessed) EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBoughtEntity()), purchaseOutput.getPercent());
                                        } else if (EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBuyer()) instanceof Organization buyer &&
                                                EVERYONEBYNAME.get(purchaseOutput.getNameOfTheSeller()) instanceof Person seller) {
                                            seller.sellPossession((CanBePossessed) EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBoughtEntity()), purchaseOutput.getPercent());
                                            buyer.addPossession((CanBePossessed) EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBoughtEntity()), purchaseOutput.getPercent());
                                        }
                                        // Notification des listeners
                                        Event event = new Purchase(purchaseOutput.getTypeOfPurchase(),
                                                EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBuyer()),
                                                (CanBePossessed) EVERYONEBYNAME.get(purchaseOutput.getNameOfTheBoughtEntity()),
                                                purchaseOutput.getPercent().intValue(),
                                                EVERYONEBYNAME.get(purchaseOutput.getNameOfTheSeller()));
                                        for (Listener i : listenedWithListener.values()) {
                                            i.onEvent(event);
                                        }
                                        System.out.println("L'achat a bien été réalisé");
                                    } catch (BuyPossessionException | SellPossessionException e) {
                                        System.out.println(e.getMessage());
                                    }
                                } else {
                                    System.out.println("On ne peut pas acheter et vendre en même temps");
                                }
                            } else {
                                System.out.println("Un média ne peut pas acheter / une personne ne peut pas être vendue");
                            }
                        } else {
                            System.out.println("L'acheteur, le vendeur ou l'achat n'existe pas");
                        }
                    }

                    // Gestion des événements de publication
                    else if (parserOutput instanceof PublicationParserEvent publicationOutput) {
                        Entity[] targets = new Entity[publicationOutput.getNameOfTheTargets().length];
                        for (int i = 0; i < targets.length; i++) {
                            if (!EVERYONEBYNAME.containsKey(publicationOutput.getNameOfTheTargets()[i])) {
                                System.out.println("Une des cibles n'existe pas");
                                continue start;
                            }
                            targets[i] = EVERYONEBYNAME.get(publicationOutput.getNameOfTheTargets()[i]);
                        }
                        if (EVERYONEBYNAME.containsKey(publicationOutput.getNameOfTheSourceMedia())) {
                            if (EVERYONEBYNAME.get(publicationOutput.getNameOfTheSourceMedia()) instanceof Media media) {
                                switch (publicationOutput.getTypeOfPublication()) {
                                    case "article" -> {
                                        if (media.getType().equals("magazine") || media.getType().equals("Site") || media.getType().equals("Presse (généraliste  politique  économique)")) {
                                            Event event = new Publication(publicationOutput.getTypeOfPublication(), EVERYONEBYNAME.get(publicationOutput.getNameOfTheSourceMedia()), targets);
                                            for (Listener i : listenedWithListener.values()) {
                                                i.onEvent(event);
                                            }
                                            System.out.println("La publication a bien été émise");
                                        } else {
                                            System.out.println("Ce média ne peut pas émettre ce type de publication");
                                        }
                                    }
                                    case "reportage" -> {
                                        if (media.getType().equals("Télévision") || media.getType().equals("Radio")) {
                                            Event event = new Publication(publicationOutput.getTypeOfPublication(), EVERYONEBYNAME.get(publicationOutput.getNameOfTheSourceMedia()), targets);
                                            for (Listener i : listenedWithListener.values()) {
                                                i.onEvent(event);
                                            }
                                            System.out.println("La publication a bien été émise");
                                        } else {
                                            System.out.println("Ce média ne peut pas émettre ce type de publication");
                                        }
                                    }
                                    case "interview" -> {
                                        if (media.getType().equals("Télévision") || media.getType().equals("Radio") || media.getType().equals("magazine") || media.getType().equals("Site") || media.getType().equals("Presse (généraliste  politique  économique)")) {
                                            Event event = new Publication(publicationOutput.getTypeOfPublication(), EVERYONEBYNAME.get(publicationOutput.getNameOfTheSourceMedia()), targets);
                                            for (Listener i : listenedWithListener.values()) {
                                                i.onEvent(event);
                                            }
                                            System.out.println("La publication a bien été émise");
                                        } else {
                                            System.out.println("Ce média ne peut pas émettre ce type de publication");
                                        }
                                    }
                                    default -> System.out.println("Ce type de diffusion n'existe pas");
                                }
                            } else {
                                System.out.println("La source n'est pas un média");
                            }
                        } else {
                            System.out.println("La source n'existe pas");
                        }
                    } else {
                        System.out.println("Commande invalide");
                    }
                } catch (ParserException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        // Fermeture du scanner
        sc.close();
    }
}