package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.network.stackcaches.ItemRequest;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NetworkPusher extends NetworkDirectional {

    public static final int TEMPLATE_SLOT = 25;

    public NetworkPusher(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, NodeType.PUSHER);
        this.getSlotsToDrop().add(TEMPLATE_SLOT);
    }

    @Override
    protected void onTick(@Nullable BlockMenu blockMenu, @Nonnull Block block) {
        super.onTick(blockMenu, block);
        if (blockMenu != null) {
            tryPushItem(blockMenu);
        }
    }

    private void tryPushItem(@Nonnull BlockMenu blockMenu) {
        final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());

        if (definition.getNode() == null) {
            return;
        }

        ItemStack testItem = blockMenu.getItemInSlot(TEMPLATE_SLOT);

        if (testItem == null || testItem.getType() == Material.AIR) {
            return;
        }

        final BlockMenu targetMenu = BlockStorage.getInventory(blockMenu.getBlock().getRelative(getCurrentDirection(blockMenu)));

        if (targetMenu == null) {
            return;
        }

        final ItemStack clone = testItem.clone();
        clone.setAmount(1);
        final ItemRequest itemRequest = new ItemRequest(clone, clone.getMaxStackSize());

        int[] slots = targetMenu.getPreset().getSlotsAccessedByItemTransport(targetMenu, ItemTransportFlow.INSERT, clone);

        for (int slot : slots) {
            final ItemStack itemStack = targetMenu.getItemInSlot(slot);

            if (itemStack == null || itemStack.getType() != Material.AIR) {
                ItemStack retrieved = definition.getNode().getRoot().getItemStack(itemRequest);
                if (retrieved != null) {
                    targetMenu.pushItem(retrieved, slots);
                }
                break;
            }
        }
    }

    @Nonnull
    @Override
    protected int[] getBackgroundSlots() {
        return new int[]{
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 15, 16, 17, 18, 20, 22, 23, 24, 26, 27, 28, 30, 31, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44
        };
    }

    @Override
    protected int getNorthSlot() {
        return 11;
    }

    @Override
    protected int getSouthSlot() {
        return 29;
    }

    @Override
    protected int getEastSlot() {
        return 21;
    }

    @Override
    protected int getWestSlot() {
        return 19;
    }

    @Override
    protected int getUpSlot() {
        return 14;
    }

    @Override
    protected int getDownSlot() {
        return 32;
    }
}
