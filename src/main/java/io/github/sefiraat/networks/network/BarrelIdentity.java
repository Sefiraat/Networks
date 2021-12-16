package io.github.sefiraat.networks.network;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class BarrelIdentity {

    private final Location location;
    private final ItemStack itemStack;
    private final int amount;
    private final int inputSlot;
    private final int outputSlot;

    public BarrelIdentity(Location location, ItemStack itemStack, int amount, int inputSlot, int outputSlot) {
        this.location = location;
        this.itemStack = itemStack;
        this.amount = amount;
        this.inputSlot = inputSlot;
        this.outputSlot = outputSlot;
    }

    public Location getLocation() {
        return location;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getAmount() {
        return amount;
    }

    public int getInputSlot() {
        return inputSlot;
    }

    public int getOutputSlot() {
        return outputSlot;
    }
}
