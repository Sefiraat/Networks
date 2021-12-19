package io.github.sefiraat.networks.network;

import org.bukkit.inventory.ItemStack;

public class ItemRequest {

    private final ItemStack itemStack;
    private int amount;

    public ItemRequest(ItemStack itemStack, int amount) {
        this.itemStack = itemStack;
        this.amount = amount;
    }

    public ItemStack getItemStack() {
        return itemStack;
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
