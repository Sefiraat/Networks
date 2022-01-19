package io.github.sefiraat.networks.slimefun.tools;

import io.github.sefiraat.networks.network.stackcaches.BlueprintInstance;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.StringUtils;
import io.github.sefiraat.networks.utils.Theme;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.sefiraat.networks.utils.datatypes.PersistentCraftingBlueprintType;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public class CraftingBlueprint extends UnplaceableBlock {

    public CraftingBlueprint(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @ParametersAreNonnullByDefault
    public static void setBlueprint(ItemStack blueprint, ItemStack[] recipe, ItemStack output) {
        final ItemMeta itemMeta = blueprint.getItemMeta();
        final ItemMeta outputMeta = output.getItemMeta();
        DataTypeMethods.setCustom(itemMeta, Keys.BLUEPRINT_INSTANCE, PersistentCraftingBlueprintType.TYPE, new BlueprintInstance(recipe, output));
        List<String> lore = new ArrayList<>();

        lore.add(Theme.CLICK_INFO + "指定配方");

        for (ItemStack item : recipe) {
            if (item == null) {
                lore.add(Theme.PASSIVE + "無");
                continue;
            }
            ItemMeta recipeItemMeta = item.getItemMeta();
            if (recipeItemMeta.hasDisplayName()) {
                lore.add(Theme.PASSIVE + ChatColor.stripColor(recipeItemMeta.getDisplayName()));
            } else {
                lore.add(Theme.PASSIVE + StringUtils.toTitleCase(item.getType().name()));
            }
        }

        lore.add("");
        lore.add(Theme.CLICK_INFO + "輸出");

        if (outputMeta.hasDisplayName()) {
            lore.add(Theme.PASSIVE + ChatColor.stripColor(outputMeta.getDisplayName()));
        } else {
            lore.add(Theme.PASSIVE + StringUtils.toTitleCase(output.getType().name()));
        }
        itemMeta.setLore(lore);

        blueprint.setItemMeta(itemMeta);
    }

}
