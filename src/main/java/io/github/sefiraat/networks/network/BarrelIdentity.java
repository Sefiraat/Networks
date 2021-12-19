package io.github.sefiraat.networks.network;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class BarrelIdentity {

    private final BlockMenu blockMenu;
    private final ItemStack itemStack;
    private final int amount;

    private final BarrelType type;

    public BarrelIdentity(BlockMenu blockMenu, ItemStack itemStack, int amount, BarrelType type) {
        this.blockMenu = blockMenu;
        this.itemStack = itemStack;
        this.amount = amount;
        this.type = type;
    }

    public BlockMenu getBlockMenu() {
        return blockMenu;
    }

    public Location getLocation() {
        return blockMenu.getLocation();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getAmount() {
        return amount;
    }

    public BarrelType getType() {
        return type;
    }

    public int getInputSlot() {
        if (this.type == BarrelType.INFINITY) {
            return 10;
        } else {
            return 0;
        }
    }

    public int getOutputSlot() {
        if (this.type == BarrelType.INFINITY) {
            return 16;
        } else {
            return 0;
        }
    }

    public enum BarrelType {
        INFINITY,
        FLUFFY
    }
}
