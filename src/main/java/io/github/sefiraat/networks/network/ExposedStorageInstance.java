package io.github.sefiraat.networks.network;

import io.github.thebusybiscuit.slimefun4.core.attributes.ExposedStorage;
import org.bukkit.Location;

public class ExposedStorageInstance {

    private final Location location;
    private final ExposedStorage exposedStorage;

    public ExposedStorageInstance(Location location, ExposedStorage exposedStorage) {
        this.location = location;
        this.exposedStorage = exposedStorage;
    }

    public Location getLocation() {
        return location;
    }

    public ExposedStorage getExposedStorage() {
        return exposedStorage;
    }
}
