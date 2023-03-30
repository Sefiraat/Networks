package io.github.sefiraat.networks.managers;

import com.google.common.base.Preconditions;
import io.github.sefiraat.networks.Networks;
import org.bukkit.Bukkit;

public class SupportedPluginManager {

    private static SupportedPluginManager instance;

    private final boolean infinityExpansion;
    private final boolean netheopoiesis;

    // region First Tick Only Registrations
    private boolean mcMMO;
    private boolean wildChests;

    // endregion

    public SupportedPluginManager() {
        Preconditions.checkArgument(instance == null, "Cannot instantiate class");
        instance = this;
        this.infinityExpansion = Bukkit.getPluginManager().isPluginEnabled("InfinityExpansion");
        this.netheopoiesis = Bukkit.getPluginManager().isPluginEnabled("Netheopoiesis");
        Networks.getInstance()
            .getServer()
            .getScheduler()
            .runTaskLater(Networks.getInstance(), this::firstTickRegistrations, 1);
    }

    private void firstTickRegistrations() {
        this.wildChests = Bukkit.getPluginManager().isPluginEnabled("WildChests");
        this.mcMMO = Bukkit.getPluginManager().isPluginEnabled("mcMMO");
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

    public static SupportedPluginManager getInstance() {
        return instance;
    }
}
