package io.github.sefiraat.networks.runnables;

import io.github.sefiraat.networks.network.GridItemRequest;
import io.github.sefiraat.networks.network.ObjectDefinition;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GridProvideItem extends BukkitRunnable {

    private final Player player;
    private final ObjectDefinition definition;
    private final GridItemRequest request;

    public GridProvideItem(Player player, ObjectDefinition definition, GridItemRequest request) {
        this.player = player;
        this.definition = definition;
        this.request = request;
    }

    @Override
    public void run() {
        if (player.getItemOnCursor().getType() == Material.AIR) {
            ItemStack requestingStack = definition.getNode().getRoot().getItemStack(request);
            if (requestingStack != null) {
                request.getPlayer().setItemOnCursor(requestingStack);
            }
        }
    }
}
