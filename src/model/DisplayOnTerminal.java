package model;

import java.sql.Array;
import java.text.Collator;
import java.util.*;

/**
 * Classe utilitaire pour afficher des entités sur le terminal.
 */
public class DisplayOnTerminal {

    /**
     * Affiche une liste d'entités triées par nom sur le terminal.
     *
     * @param entities la liste des entités à afficher.
     */
    public static void DisplayOnTerminalByName(ArrayList<Entity> entities) {
        Collator collator = Collator.getInstance(Locale.FRENCH);

        entities.sort(Comparator.comparing(Entity::getName, collator));

        for (Entity entity : entities) {
            System.out.println(entity.toString());
        }
    }

    /**
     * Affiche une map d'entités triées par clé (nom) sur le terminal.
     *
     * @param entities la map des entités à afficher.
     */
    public static void DisplayOnTerminalByName2(Map<String,Entity> entities) {
        Collator collator = Collator.getInstance(Locale.FRENCH);

        entities.keySet().stream()
                .sorted(collator)
                .forEach(key -> System.out.println(entities.get(key).toString()));
    }
}

