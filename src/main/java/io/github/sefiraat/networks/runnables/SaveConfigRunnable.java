package io.github.sefiraat.networks.runnables;

import io.github.sefiraat.networks.Networks;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveConfigRunnable extends BukkitRunnable {

    @Override
    public void run() {
        Networks.getConfigManager().saveAll();
    }
}
