package io.github.sefiraat.networks.listeners;

import io.github.sefiraat.networks.Networks;
import org.bukkit.event.Listener;

public class ListenerManager {

    public ListenerManager() {
        addListener(new MiscListener());
    }

    private void addListener(Listener listener) {
        Networks.getPluginManager().registerEvents(listener, Networks.getInstance());
    }

}
