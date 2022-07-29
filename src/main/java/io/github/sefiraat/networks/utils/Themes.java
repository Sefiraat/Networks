package io.github.sefiraat.networks.utils;

import dev.sefiraat.sefilib.string.Theme;
import io.github.sefiraat.networks.Networks;
import net.md_5.bungee.api.ChatColor;

public final class Themes {

    private Themes() {
        throw new IllegalStateException("Utility class");
    }

    public static final Theme MAIN = new Theme(
        ChatColor.of("#21588f"),
        "Networks"
    );

    public static final Theme RESEARCH = new Theme(
        ChatColor.of("#a60e03"),
        Networks.getLanguageManager().getThemeLore("research")
    );

    public static final Theme CRAFTING = new Theme(
        ChatColor.of("#dbcea9"),
        Networks.getLanguageManager().getThemeLore("crafting")
    );

    public static final Theme MACHINE = new Theme(
        ChatColor.of("#3295a8"),
        Networks.getLanguageManager().getThemeLore("machine")
    );

    public static final Theme TOOL = new Theme(
        ChatColor.of("#6b32a8"),
        Networks.getLanguageManager().getThemeLore("tool")
    );

    public static final Theme GUIDE = new Theme(
        ChatColor.of("#444444"),
        Networks.getLanguageManager().getThemeLore("guide")
    );
}
