package io.github.sefiraat.networks.utils;

import org.apache.commons.lang.WordUtils;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public static String toTitleCase(String string) {
        final char[] delimiters = {' ', '_'};
        return WordUtils.capitalizeFully(string, delimiters).replace("_", " ");
    }

    /**
     * List of names to be given to ArmourStands, invisible but mods and Minimaps can see them :)
     */
    @Nonnull
    private static final List<String> EGG_NAMES = Arrays.asList(
        "TheBusyBiscuit",
        "Alessio",
        "Walshy",
        "Jeff",
        "Seggan",
        "BOOMER_1",
        "svr333",
        "variananora",
        "ProfElements",
        "Riley",
        "FluffyBear",
        "GallowsDove",
        "Apeiros",
        "Martin",
        "Bunnky",
        "ReasonFoundDecoy",
        "Oah",
        "Azak",
        "andrewandy",
        "EpicPlayer10",
        "GentlemanCheesy",
        "ybw0014",
        "Ashian",
        "R.I.P",
        "OOOOMAGAAA",
        "TerslenK",
        "FN_FAL",
        "supertechxter"
    );

    @Nonnull
    public static String getRandomEggName() {
        int rnd = ThreadLocalRandom.current().nextInt(0, EGG_NAMES.size());
        return EGG_NAMES.get(rnd);
    }

    @Nonnull
    public static List<String> getEggNames() {
        return EGG_NAMES;
    }

}
