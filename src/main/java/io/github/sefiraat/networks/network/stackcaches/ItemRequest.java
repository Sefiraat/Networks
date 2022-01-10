package io.github.sefiraat.networks.network.stackcaches;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class ItemRequest extends ItemStackCache {

    private int amount;

    public ItemRequest(@Nonnull ItemStack itemStack, int amount) {
        super(itemStack);
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void receiveAmount(int amount) {
        this.amount = this.amount - amount;
    }
}
