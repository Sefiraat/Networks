package io.github.sefiraat.networks.listeners;

import io.github.sefiraat.networks.slimefun.network.NetworkQuantumStorage;
import io.github.thebusybiscuit.slimefun4.api.events.ExplosiveToolBreakBlocksEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ExplosiveToolListener implements Listener {

    @EventHandler
    public void onExplosiveBlockBreak(@Nonnull ExplosiveToolBreakBlocksEvent event) {
        final List<Block> blocksToRemove = new ArrayList<>();
        for (Block block : event.getAdditionalBlocks()) {
            final Location location = block.getLocation();
            if (NetworkQuantumStorage.getCaches().containsKey(location)) {
                blocksToRemove.add(block);
            }
        }
        event.getAdditionalBlocks().removeAll(blocksToRemove);
    }
}
