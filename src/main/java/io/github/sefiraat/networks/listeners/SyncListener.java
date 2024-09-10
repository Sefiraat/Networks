package io.github.sefiraat.networks.listeners;

import javax.annotation.Nonnull;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import io.github.sefiraat.networks.utils.NetworkUtils;

public class SyncListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(@Nonnull BlockBreakEvent event) {
        NetworkUtils.clearNetwork(event.getBlock().getLocation());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(@Nonnull BlockPlaceEvent event) {
        NetworkUtils.clearNetwork(event.getBlock().getLocation());
    }
}