package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.network.ObjectType;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;

public class NetworkMonitor extends NetworkObject {

    public NetworkMonitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, ObjectType.STORAGE_MONITOR);
    }
}
