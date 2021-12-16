package io.github.sefiraat.networks;

import io.github.sefiraat.networks.network.NodeDefinition;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class NetworkStorage {

    protected static final Map<Location, NodeDefinition> ALL_NETWORK_OBJECTS = new HashMap<>();

    public static Map<Location, NodeDefinition> getAllNetworkObjects() {
        return ALL_NETWORK_OBJECTS;
    }

}
