package io.github.sefiraat.networks.utils;

import io.github.sefiraat.networks.network.stackcaches.ItemStackCache;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@UtilityClass
public class StackUtils {

    @Nonnull
    public static ItemStack getAsQuantity(@Nonnull ItemStack itemStack, int amount) {
        ItemStack clone = itemStack.clone();
        clone.setAmount(amount);
        return clone;
    }

    public static boolean itemsMatch(@Nonnull ItemStackCache cache, @Nullable ItemStack itemStack) {
        if (cache.getItemStack() == null || itemStack == null) {
            return itemStack == null && cache.getItemStack() == null;
        }
        if (itemStack.getType() != cache.getItemType()) {
            return false;
        }
        if (itemStack.hasItemMeta() && cache.getItemStack().hasItemMeta()) {
            final ItemMeta itemMeta = itemStack.getItemMeta();
            final ItemMeta cachedMeta = cache.getItemMeta();
            final Optional<String> optionalStackId1 = Slimefun.getItemDataService().getItemData(itemMeta);
            final Optional<String> optionalStackId2 = Slimefun.getItemDataService().getItemData(cachedMeta);
            if (optionalStackId1.isPresent() && optionalStackId2.isPresent()) {
                return optionalStackId1.get().equals(optionalStackId2.get());
            }
            return itemMeta.equals(cachedMeta);
        } else {
            return itemStack.hasItemMeta() == cache.getItemStack().hasItemMeta();
        }
    }

    public static boolean itemsMatchCrafting(@Nullable ItemStack itemStack1, @Nullable ItemStack itemStack2) {
        if (itemStack1 == null || itemStack2 == null) {
            return itemStack2 == null && itemStack1 == null;
        }
        if (itemStack2.getType() != itemStack1.getType()) {
            return false;
        }
        if (itemStack1.hasItemMeta() && itemStack2.hasItemMeta()) {
            final ItemMeta itemMeta2 = itemStack2.getItemMeta();
            final ItemMeta itemMeta1 = itemStack1.getItemMeta();
            final Optional<String> optionalStackId1 = Slimefun.getItemDataService().getItemData(itemMeta1);
            final Optional<String> optionalStackId2 = Slimefun.getItemDataService().getItemData(itemMeta2);
            if (optionalStackId1.isPresent() && optionalStackId2.isPresent()) {
                return optionalStackId1.get().equals(optionalStackId2.get());
            }
            return itemMeta2.equals(itemMeta1);
        } else {
            return itemStack1.hasItemMeta() == itemStack2.hasItemMeta();
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
