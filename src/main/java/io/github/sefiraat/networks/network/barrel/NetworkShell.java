package io.github.sefiraat.networks.network.barrel;

import io.github.sefiraat.networks.slimefun.network.NetworkMemoryShell;
import io.github.sefiraat.networks.slimefun.tools.CardInstance;
import io.github.sefiraat.networks.slimefun.tools.NetworkCard;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.sefiraat.networks.utils.datatypes.PersistentCardInstanceType;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        final ItemStack card = blockMenu.getItemInSlot(this.getInputSlot());
        final SlimefunItem cardItem = SlimefunItem.getByItem(card);

        if (cardItem instanceof NetworkCard) {
            ItemMeta itemMeta = card.getItemMeta();
            CardInstance instance = DataTypeMethods.getCustom(itemMeta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE);

            if (instance == null || instance.getItemStack() == null) {
                return;
            }

            for (ItemStack itemStack : itemsToDeposit) {
                if (SlimefunUtils.isItemSimilar(itemStack, instance.getItemStack(), true, false)) {
                    instance.increaseAmount(itemStack.getAmount());
                    itemStack.setAmount(0);
                }
            }
            instance.updateLore(itemMeta);
            DataTypeMethods.setCustom(itemMeta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE, instance);
            card.setItemMeta(itemMeta);
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
