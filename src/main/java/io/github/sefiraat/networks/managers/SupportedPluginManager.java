package io.github.sefiraat.networks.managers;

import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class SupportedPluginManager {

    private final boolean isInfinityExpansion;

    public SupportedPluginManager() {
        isInfinityExpansion = Bukkit.getPluginManager().isPluginEnabled("InfinityExpansion");
    }

}
