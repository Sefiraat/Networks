package io.github.sefiraat.networks.network.barrel;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class BarrelIdentity implements BarrelCore {

    private final Location location;
    private final ItemStack referenceStack;
    private final int amount;
    private final BarrelType type;

    @ParametersAreNonnullByDefault
    protected BarrelIdentity(Location location, ItemStack referenceStack, int amount, BarrelType type) {
        this.location = location;
        this.referenceStack = referenceStack;
        this.amount = amount;
        this.type = type;
    }

    public Location getLocation() {
        return this.location;
    }

    public ItemStack getReferenceStack() {
        return this.referenceStack;
    }

    public int getAmount() {
        return this.amount;
    }

    public BarrelType getType() {
        return this.type;
    }

    public boolean holdsMatchingItem(@Nonnull ItemStack itemStack) {
        return SlimefunUtils.isItemSimilar(itemStack, this.referenceStack, true, false);
    }

}
