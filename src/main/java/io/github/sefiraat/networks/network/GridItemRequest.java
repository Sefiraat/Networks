package io.github.sefiraat.networks.network;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GridItemRequest {

    private final ItemStack itemStack;
    private final Player player;
    private int amount;

    public GridItemRequest(ItemStack itemStack, Player player, int amount) {
        this.itemStack = itemStack;
        this.player = player;
        this.amount = amount;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Player getPlayer() {
        return player;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void receiveAmount(int amount) {
        this.amount = this.amount - amount;
    }
}
