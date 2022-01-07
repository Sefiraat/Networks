package io.github.sefiraat.networks.managers;

import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.runnables.CleanNetwork;

public class RunnableManager {

    public RunnableManager() {

        final Networks plugin = Networks.getInstance();

        final CleanNetwork cleanNetwork = new CleanNetwork();

        cleanNetwork.runTaskTimerAsynchronously(plugin, 0, 1000);
    }
}
