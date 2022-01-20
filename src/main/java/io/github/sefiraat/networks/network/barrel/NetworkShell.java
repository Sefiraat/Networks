package io.github.sefiraat.networks.network.barrel;

import io.github.sefiraat.networks.network.stackcaches.BarrelIdentity;
import io.github.sefiraat.networks.network.stackcaches.CardInstance;
import io.github.sefiraat.networks.network.stackcaches.ItemRequest;
import io.github.sefiraat.networks.slimefun.network.NetworkMemoryShell;
import io.github.sefiraat.networks.slimefun.network.NetworkMemoryShellCache;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.sefiraat.networks.utils.datatypes.PersistentAmountInstanceType;
import io.github.sefiraat.networks.utils.datatypes.PersistentCardInstanceType;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NetworkShell extends BarrelIdentity {

    public NetworkShell(Location location, ItemStack itemStack, int amount) {
        super(location, itemStack, amount, BarrelType.NETWORKS);
    }

    @Override
    @Nullable
    public ItemStack requestItem(@Nonnull ItemRequest itemRequest) {

        final BlockMenu blockMenu = BlockStorage.getInventory(this.getLocation());

        if (blockMenu == null) {
            return null;
        }

        final NetworkMemoryShellCache cache = NetworkMemoryShell.getCaches().get(blockMenu.getLocation());

        if (cache == null) {
            return null;
        }

        return NetworkMemoryShell.getItemStack(cache, blockMenu, itemRequest.getAmount());
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

            NetworkMemoryShellCache cache = NetworkMemoryShell.getCaches().get(this.getLocation());
            if (cache != null) {
                NetworkMemoryShell.tryInputItem(itemsToDeposit, cache);
            }
        }
    }

    @Nullable
    private static CardInstance getCardInstance(@Nonnull ItemStack card, @Nonnull NetworkMemoryShellCache cache) {
        final ItemMeta cardMeta = card.getItemMeta();
        CardInstance instance;

        if (cache.getItemStack() == null) {
            instance = DataTypeMethods.getCustom(cardMeta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE);
        } else {
            instance = DataTypeMethods.getCustom(cardMeta, Keys.CARD_INSTANCE, PersistentAmountInstanceType.TYPE);
            if (instance != null) {
                instance.setItemStack(cache.getItemStack());
            }
        }

        return instance;
    }

    @Nullable
    private static CardInstance getAmountInstance(@Nonnull ItemStack card) {
        final ItemMeta cardMeta = card.getItemMeta();
        return DataTypeMethods.getCustom(cardMeta, Keys.CARD_INSTANCE, PersistentAmountInstanceType.TYPE);
    }

    private static void setCardInstance(@Nonnull ItemStack card, @Nonnull CardInstance cardInstance) {
        final ItemMeta cardMeta = card.getItemMeta();
        DataTypeMethods.setCustom(cardMeta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE, cardInstance);
        card.setItemMeta(cardMeta);
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
