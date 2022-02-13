package io.github.sefiraat.networks.managers;

import io.github.sefiraat.networks.Networks;
import org.bukkit.event.Listener;

public class ListenerManager {

    public ListenerManager() {

    }

    private void addListener(Listener listener) {
        Networks.getPluginManager().registerEvents(listener, Networks.getInstance());
    }
}
