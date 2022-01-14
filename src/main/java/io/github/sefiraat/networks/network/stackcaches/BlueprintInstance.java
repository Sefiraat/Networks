package io.github.sefiraat.networks.network.stackcaches;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class BlueprintInstance extends ItemStackCache {

    private final ItemStack[] recipe;

    public BlueprintInstance(@Nonnull ItemStack[] recipe, @Nonnull ItemStack expectedOutput) {
        super(expectedOutput);
        this.recipe = recipe;
    }

    public ItemStack[] getRecipe() {
        return recipe;
    }

}
