package io.github.sefiraat.networks.network.barrel;

import io.github.sefiraat.networks.network.ItemStackCache;
import io.github.sefiraat.networks.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class BarrelIdentity extends ItemStackCache implements BarrelCore {

    private final Location location;
    private final int amount;
    private final BarrelType type;

    @ParametersAreNonnullByDefault
    protected BarrelIdentity(Location location, ItemStack itemStack, int amount, BarrelType type) {
        super(itemStack);
        this.location = location;
        this.amount = amount;
        this.type = type;
    }

    public Location getLocation() {
        return this.location;
    }

    public int getAmount() {
        return this.amount;
    }

    public BarrelType getType() {
        return this.type;
    }

    public boolean holdsMatchingItem(@Nonnull ItemStack itemStack) {
        return StackUtils.itemsMatch(this, itemStack);
    }

}
