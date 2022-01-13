package io.github.sefiraat.networks.network.stackcaches;

import org.bukkit.inventory.ItemStack;

public class BlueprintInstance extends ItemStackCache {

    private final ItemStack[] recipe;

    public BlueprintInstance(ItemStack[] recipe, ItemStack expectedOutput) {
        super(expectedOutput);
        this.recipe = recipe;
    }

    public ItemStack[] getRecipe() {
        return recipe;
    }

}
