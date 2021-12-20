package io.github.sefiraat.networks.listeners;

import io.github.sefiraat.networks.utils.GeneralUtils;
import io.github.sefiraat.networks.utils.Theme;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class MiscListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void checkCooldown(@Nonnull PlayerInteractEvent event) {
        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
        if (itemStack.getType() != Material.AIR
            && (event.getAction() == Action.RIGHT_CLICK_AIR
            || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            && GeneralUtils.isOnCooldown(itemStack)
        ) {
            event.getPlayer().sendMessage(Theme.WARNING + "This is still on cooldown");
            event.setCancelled(true);
        }
    }
}