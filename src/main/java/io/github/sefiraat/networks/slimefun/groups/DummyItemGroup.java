package io.github.sefiraat.networks.slimefun.groups;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

public class DummyItemGroup extends ItemGroup {

    @ParametersAreNonnullByDefault
    public DummyItemGroup(NamespacedKey key, ItemStack item) {
        super(key, item);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isHidden(Player p) {
        return true;
    }

}
