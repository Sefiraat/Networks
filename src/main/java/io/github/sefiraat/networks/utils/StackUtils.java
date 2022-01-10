package io.github.sefiraat.networks.utils;

import io.github.sefiraat.networks.network.ItemStackCache;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

@UtilityClass
public class StackUtils {

    @Nonnull
    public static ItemStack getAsQuantity(@Nonnull ItemStack itemStack, int amount) {
        ItemStack clone = itemStack.clone();
        clone.setAmount(amount);
        return clone;
    }

    public static boolean itemsMatch(@Nonnull ItemStackCache cache, @Nonnull ItemStack itemStack) {
        if (itemStack.getType() != cache.getItemType()) {
            return false;
        }
        if (itemStack.hasItemMeta()) {
            final ItemMeta itemMeta = itemStack.getItemMeta();
            final ItemMeta cachedMeta = cache.getItemMeta();
            return itemMeta.equals(cachedMeta);
        } else {
            return cache.getItemMeta() == null;
        }
    }

}
