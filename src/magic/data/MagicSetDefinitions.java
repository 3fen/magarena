package magic.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import magic.MagicMain;
import magic.model.MagicCardDefinition;
import magic.model.MagicSetDefinition;

public class MagicSetDefinitions {

    public static enum MagicSets {

       _2ED ("Unlimited Edition"),
       _3ED ("Revised Edition"),
       _10E ("10th Edition"),
        ARN ("Arabian Nights"),
        LEG ("Legends"),
        ZEN ("");

        private final String setName;

        private MagicSets(final String name) {
            this.setName = name;
        }

        public String getSetName() {
            return setName;
        }

    }

    private static final HashMap<MagicSets, MagicSetDefinition> loadedSets = new HashMap<MagicSets, MagicSetDefinition>();

    private static MagicSetDefinition loadMagicSet(final MagicSets magicSet) {
        String content = null;
        final String filename = "/magic/data/sets/" + magicSet.toString().replace("_", "") + ".txt";
        try {
            content = FileIO.toStr(MagicMain.rootFrame.getClass().getResourceAsStream(filename));
        } catch (final IOException ex) {
            System.err.println("ERROR! Unable to load " + filename);
            return null;
        }

        final MagicSetDefinition magicSetDef = new MagicSetDefinition(magicSet.toString());

        try (final Scanner sc = new Scanner(content)) {
            while (sc.hasNextLine()) {
                final String line = sc.nextLine();
                magicSetDef.add(line.trim());
            }
        }

        return magicSetDef;

    }

    public static String[] getFilterValues() {
        final List<String> values = new ArrayList<String>();
        for (MagicSets magicSet : MagicSets.values()) {
            values.add(magicSet.toString().replace("_", "") + " " + magicSet.getSetName());
        }
        return values.toArray(new String[values.size()]);
    }

    public static boolean isCardInSet(MagicCardDefinition card, MagicSets magicSet) {
        if (!loadedSets.containsKey(magicSet)) {
            loadedSets.put(magicSet, loadMagicSet(magicSet));
        }
        return loadedSets.get(magicSet).contains(card.getName());
    }

    public static void clearLoadedSets() {
        loadedSets.clear();
    }

}
