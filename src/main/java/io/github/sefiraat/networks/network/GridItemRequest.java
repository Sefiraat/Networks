package io.github.sefiraat.networks.network;

import io.github.sefiraat.networks.network.stackcaches.ItemRequest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GridItemRequest extends ItemRequest {

    private final Player player;

    public GridItemRequest(ItemStack itemStack, int amount, Player player) {
        super(itemStack, amount);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
