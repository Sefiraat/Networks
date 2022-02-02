package io.github.sefiraat.networks.network;

import io.github.sefiraat.networks.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public final class SupportedRecipes {

    private static final Map<ItemStack[], ItemStack> RECIPES = new HashMap<>();

    static {
        for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
            RecipeType recipeType = item.getRecipeType();
            if ((recipeType == RecipeType.ENHANCED_CRAFTING_TABLE) && allowedRecipe(item)) {
                ItemStack[] itemStacks = new ItemStack[9];
                int i = 0;
                for (ItemStack itemStack : item.getRecipe()) {
                    if (itemStack == null) {
                        itemStacks[i] = null;
                    } else {
                        itemStacks[i] = new ItemStack(itemStack.clone());
                    }
                    i++;
                }
                SupportedRecipes.addRecipe(itemStacks, item.getRecipeOutput());
            }
        }
    }

    public static Map<ItemStack[], ItemStack> getRecipes() {
        return RECIPES;
    }

    public static void addRecipe(@Nonnull ItemStack[] input, @Nonnull ItemStack output) {
        RECIPES.put(input, output);
    }

    public boolean testRecipe(@Nonnull ItemStack[] input, @Nonnull ItemStack[] recipe) {
        for (int test = 0; test < recipe.length; test++) {
            if (!StackUtils.itemsMatch(input[test], recipe[test])) {
                return false;
            }
        }
        return true;
    }

    public static boolean allowedRecipe(@Nonnull SlimefunItemStack i) {
        return allowedRecipe(i.getItemId());
    }

    public static boolean allowedRecipe(@Nonnull String s) {
        return !isBackpack(s);
    }

    public static boolean isBackpack(@Nonnull String s) {
        return s.matches("(.*)BACKPACK(.*)");
    }

    public static boolean allowedRecipe(@Nonnull SlimefunItem i) {
        return allowedRecipe(i.getId());
    }

}
