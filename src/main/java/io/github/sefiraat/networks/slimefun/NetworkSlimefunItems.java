package io.github.sefiraat.networks.slimefun;

import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.slimefun.network.NetworkBridge;
import io.github.sefiraat.networks.slimefun.network.NetworkController;
import io.github.sefiraat.networks.slimefun.network.NetworkExport;
import io.github.sefiraat.networks.slimefun.network.NetworkGrid;
import io.github.sefiraat.networks.slimefun.network.NetworkImport;
import io.github.sefiraat.networks.slimefun.network.NetworkMonitor;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public enum NetworkSlimefunItems {

    NETWORK_CONTROLLER(
        new NetworkController(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_CONTROLLER,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{}
        )
    ),

    NETWORK_BRIDGE(
        new NetworkBridge(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_BRIDGE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{}
        )
    ),

    NETWORK_MONITOR(
        new NetworkMonitor(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_MONITOR,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{}
        )
    ),

    NETWORK_IMPORT(
        new NetworkImport(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_IMPORT,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{}
        )
    ),

    NETWORK_EXPORT(
        new NetworkExport(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_EXPORT,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{}
        )
    ),

    NETWORK_GRID(
        new NetworkGrid(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_GRID,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{}
        )
    );

    @Getter
    private final SlimefunItem item;

    NetworkSlimefunItems(SlimefunItem slimefunItem) {
        final Networks plugin = Networks.getInstance();

        this.item = slimefunItem;
        this.item.register(plugin);
    }

    public static void trigger() {
        // Empty to trigger static loading
    }

    @Nonnull
    public ItemStack getItemStack() {
        return item.getItem().clone();
    }

    @Nonnull
    public ItemStack getItemStack(int amount) {
        ItemStack itemStack = item.getItem().clone();
        itemStack.setAmount(amount);
        return itemStack;
    }

}
