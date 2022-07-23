package io.github.sefiraat.networks.network.barrel;

import io.github.sefiraat.networks.network.stackcaches.BarrelIdentity;
import io.github.sefiraat.networks.network.stackcaches.ItemRequest;
import io.github.sefiraat.networks.network.stackcaches.QuantumCache;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumStorage;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NetworkStorage extends BarrelIdentity {

    public NetworkStorage(Location location, ItemStack itemStack, int amount) {
        super(location, itemStack, amount, BarrelType.NETWORKS);
    }

    @Override
    @Nullable
    public ItemStack requestItem(@Nonnull ItemRequest itemRequest) {
        final BlockMenu blockMenu = BlockStorage.getInventory(this.getLocation());

        if (blockMenu == null) {
            return null;
        }

        final QuantumCache cache = NetworkQuantumStorage.getCaches().get(blockMenu.getLocation());

        if (cache == null) {
            return null;
        }

        return NetworkQuantumStorage.getItemStack(cache, blockMenu, itemRequest.getAmount());
    }

    @Override
    public void depositItemStack(ItemStack[] itemsToDeposit) {
        if (BlockStorage.check(this.getLocation()) instanceof NetworkQuantumStorage) {
            final BlockMenu blockMenu = BlockStorage.getInventory(this.getLocation());
            final QuantumCache cache = NetworkQuantumStorage.getCaches().get(this.getLocation());
            if (cache != null) {
                NetworkQuantumStorage.tryInputItem(blockMenu.getLocation(), itemsToDeposit, cache);
            }
        }
    }


    @Override
    public int getInputSlot() {
        return NetworkQuantumStorage.INPUT_SLOT;
    }

    @Override
    public int getOutputSlot() {
        return NetworkQuantumStorage.OUTPUT_SLOT;
    }
}
