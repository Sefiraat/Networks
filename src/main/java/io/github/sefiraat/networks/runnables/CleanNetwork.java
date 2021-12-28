package io.github.sefiraat.networks.runnables;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.NodeDefinition;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CleanNetwork extends BukkitRunnable {

    @Override
    public void run() {
        Set<Location> locationsToRemove = new HashSet<>();
        for (Map.Entry<Location, NodeDefinition> entry : NetworkStorage.getAllNetworkObjects().entrySet()) {
            if (entry.getValue().isExpired()) {
                locationsToRemove.add(entry.getKey());
            }
        }
        for (Location location : locationsToRemove) {
            NetworkStorage.getAllNetworkObjects().remove(location);
        }
    }
}
