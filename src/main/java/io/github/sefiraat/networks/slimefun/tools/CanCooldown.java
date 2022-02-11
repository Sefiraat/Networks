package io.github.sefiraat.networks.slimefun.tools;

import io.github.sefiraat.networks.utils.StackUtils;
import io.github.sefiraat.networks.utils.Theme;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public interface CanCooldown {

    /**
     * The duration, in seconds, this item will go on cooldown for
     *
     * @return The cooldown duration in seconds
     */
    int cooldownDuration();

    @ParametersAreNonnullByDefault
    default boolean canBeUsed(ItemStack itemStack) {
        return canBeUsed(null, itemStack);
    }

    @ParametersAreNonnullByDefault
    default boolean canBeUsed(@Nullable Player player, ItemStack itemStack) {
        if (StackUtils.isOnCooldown(itemStack)) {
            if (player != null) {
                player.sendMessage(Theme.WARNING + "This is still on cooldown");
            }
            return false;
        } else {
            return true;
        }
    }

    @ParametersAreNonnullByDefault
    default void putOnCooldown(ItemStack itemStack) {
        StackUtils.putOnCooldown(itemStack, this.cooldownDuration());
    }
}
