package io.github.sefiraat.networks.utils;

import io.github.sefiraat.networks.network.stackcaches.ItemStackCache;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

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

    /**
     * Heal the entity by the provided amount
     *
     * @param itemStack         The {@link LivingEntity} to heal
     * @param durationInSeconds The amount to heal by
     */
    @ParametersAreNonnullByDefault
    public static void putOnCooldown(ItemStack itemStack, int durationInSeconds) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            PersistentDataAPI.setLong(itemMeta, Keys.ON_COOLDOWN, System.currentTimeMillis() + (durationInSeconds * 1000L));
            itemStack.setItemMeta(itemMeta);
        }
    }

    /**
     * Heal the entity by the provided amount
     *
     * @param itemStack The {@link LivingEntity} to heal
     */
    @ParametersAreNonnullByDefault
    public static boolean isOnCooldown(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            long cooldownUntil = PersistentDataAPI.getLong(itemMeta, Keys.ON_COOLDOWN, 0);
            return System.currentTimeMillis() < cooldownUntil;
        }
        return false;
    }
}
