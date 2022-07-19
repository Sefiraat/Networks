package io.github.sefiraat.networks.managers;

import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class SupportedPluginManager {

    private final boolean infinityExpansion;
    private final boolean netheopoiesis;

    public SupportedPluginManager() {
        this.infinityExpansion = Bukkit.getPluginManager().isPluginEnabled("InfinityExpansion");
        this.netheopoiesis = Bukkit.getPluginManager().isPluginEnabled("Netheopoiesis");
    }

}
