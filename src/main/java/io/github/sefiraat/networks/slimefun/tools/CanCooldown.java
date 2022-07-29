package io.github.sefiraat.networks.slimefun.tools;

import dev.sefiraat.sefilib.itemstacks.Cooldowns;
import dev.sefiraat.sefilib.string.Theme;
import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.StackUtils;
import io.github.sefiraat.networks.utils.Themes;
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
        if (Cooldowns.isOnCooldown(Keys.ON_COOLDOWN, itemStack)) {
            if (player != null) {
                player.sendMessage(Networks.getLanguageManager().getPlayerMessage("on-cooldown", Theme.ERROR));
            }
            return false;
        } else {
            return true;
        }
    }

    @ParametersAreNonnullByDefault
    default void putOnCooldown(ItemStack itemStack) {
        Cooldowns.addCooldown(Keys.ON_COOLDOWN, itemStack, this.cooldownDuration());
    }
}
