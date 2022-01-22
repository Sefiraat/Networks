package io.github.sefiraat.networks.network.barrel;

import io.github.sefiraat.networks.network.stackcaches.ItemRequest;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface BarrelCore {

    @Nullable
    ItemStack requestItem(@Nonnull ItemRequest itemRequest);

    default void depositItemStack(ItemStack itemToDeposit) {
        depositItemStack(new ItemStack[]{itemToDeposit});
    }

    void depositItemStack(ItemStack[] itemsToDeposit);

    int getInputSlot();

    int getOutputSlot();
}
