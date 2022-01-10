package io.github.sefiraat.networks.network.barrel;

import io.github.sefiraat.networks.network.stackcaches.BarrelIdentity;
import io.github.sefiraat.networks.slimefun.network.NetworkMemoryShell;
import io.github.sefiraat.networks.slimefun.network.NetworkMemoryShellCache;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NetworkShell extends BarrelIdentity {

    public NetworkShell(Location location, ItemStack itemStack, int amount) {
        super(location, itemStack, amount, BarrelType.NETWORKS);
    }

    @Override
    public ItemStack requestItem(ItemStack similarStack) {
        BlockMenu blockMenu = BlockStorage.getInventory(this.getLocation());
        return blockMenu == null ? null : blockMenu.getItemInSlot(this.getOutputSlot());
    }

    @Override
    public void depositItemStack(ItemStack[] itemsToDeposit) {
        final BlockMenu blockMenu = BlockStorage.getInventory(this.getLocation());
        final SlimefunItem slimefunItem = BlockStorage.check(this.getLocation());

        if (slimefunItem instanceof NetworkMemoryShell) {
            final ItemStack card = blockMenu.getItemInSlot(this.getInputSlot());

            // No card, quick exit
            if (card == null || card.getType() == Material.AIR) {
                return;
            }

            NetworkMemoryShellCache cache = NetworkMemoryShell.CACHES.get(this.getLocation());
            if (cache != null) {
                NetworkMemoryShell.tryInputItem(card, itemsToDeposit, cache);
            }
        }

    }

    @Override
    public int getInputSlot() {
        return NetworkMemoryShell.CARD_SLOT;
    }

    @Override
    public int getOutputSlot() {
        return NetworkMemoryShell.OUTPUT_SLOT;
    }
}
