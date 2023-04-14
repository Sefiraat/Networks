package io.github.sefiraat.networks.managers;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;

public class SupportedPluginManager {

    private static SupportedPluginManager instance;

    private final boolean infinityExpansion;
    private final boolean netheopoiesis;
    private final boolean mcMMO;
    private final boolean wildChests;
    private final boolean aureliumSkills;

    public SupportedPluginManager() {
        Preconditions.checkArgument(instance == null, "Cannot instantiate class");
        instance = this;
        this.infinityExpansion = Bukkit.getPluginManager().isPluginEnabled("InfinityExpansion");
        this.netheopoiesis = Bukkit.getPluginManager().isPluginEnabled("Netheopoiesis");
        this.mcMMO = Bukkit.getPluginManager().isPluginEnabled("mcMMO");
        this.wildChests = Bukkit.getPluginManager().isPluginEnabled("WildChests");
        this.aureliumSkills = Bukkit.getPluginManager().isPluginEnabled("aureliumSkills");
    }

    public boolean isInfinityExpansion() {
        return infinityExpansion;
    }

    public boolean isNetheopoiesis() {
        return netheopoiesis;
    }

    public boolean isMcMMO() {
        return mcMMO;
    }

    public boolean isWildChests() {
        return wildChests;
    }

    public boolean isAureliumSkills() {
        return aureliumSkills;
    }

    public static SupportedPluginManager getInstance() {
        return instance;
    }
}
