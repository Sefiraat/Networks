package io.github.sefiraat.networks.network.barrel;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class NetworkShell extends BarrelIdentity {

    public NetworkShell(Location location, ItemStack itemStack, int amount) {
        super(location, itemStack, amount, BarrelType.NETWORKS);
    }

    @Override
    public ItemStack requestItem(ItemStack similarStack) {
        return null;
    }

    @Override
    public void depositItemStack(ItemStack[] itemsToDeposit) {

    }

    @Override
    public int getInputSlot() {
        return 10;
    }

    @Override
    public int getOutputSlot() {
        return 16;
    }
}
