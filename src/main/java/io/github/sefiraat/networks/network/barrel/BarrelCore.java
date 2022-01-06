package io.github.sefiraat.networks.network.barrel;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public interface BarrelCore {

    @Nullable
    ItemStack requestItem(ItemStack similarStack);

    default void depositItemStack(ItemStack itemToDeposit) {
        depositItemStack(new ItemStack[]{itemToDeposit});
    }

    void depositItemStack(ItemStack[] itemsToDeposit);

    int getInputSlot();

    int getOutputSlot();
}
