package io.github.sefiraat.networks.slimefun;

import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.slimefun.groups.DummyItemGroup;
import io.github.sefiraat.networks.slimefun.groups.MainFlexGroup;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@UtilityClass
public final class NetworksItemGroups {

    public static final MainFlexGroup MAIN = new MainFlexGroup(
        Keys.newKey("main"),
        new CustomItemStack(
            new ItemStack(Material.BLACK_STAINED_GLASS),
            Theme.MAIN.getColor() + "網路"
        )
    );

    public static final DummyItemGroup MATERIALS = new DummyItemGroup(
        Keys.newKey("materials"),
        new CustomItemStack(
            new ItemStack(Material.WHITE_STAINED_GLASS),
            Theme.MAIN.getColor() + "製作材料"
        )
    );

    public static final DummyItemGroup TOOLS = new DummyItemGroup(
        Keys.newKey("tools"),
        new CustomItemStack(
            new ItemStack(Material.PAINTING),
            Theme.MAIN.getColor() + "網路管理工具"
        )
    );

    public static final DummyItemGroup NETWORK_ITEMS = new DummyItemGroup(
        Keys.newKey("network_items"),
        new CustomItemStack(
            new ItemStack(Material.BLACK_STAINED_GLASS),
            Theme.MAIN.getColor() + "網路物品"
        )
    );

    public static final DummyItemGroup NETWORK_QUANTUMS = new DummyItemGroup(
        Keys.newKey("network_quantums"),
        new CustomItemStack(
            new ItemStack(Material.WHITE_TERRACOTTA),
            Theme.MAIN.getColor() + "網路量子儲物設備"
        )
    );

    public static final ItemGroup DISABLED_ITEMS = new HiddenItemGroup(
        Keys.newKey("disabled_items"),
        new CustomItemStack(
            new ItemStack(Material.BARRIER),
            Theme.MAIN.getColor() + "停用/已移除的物品"
        )
    );

    static {
        final Networks plugin = Networks.getInstance();

        // Slimefun Registry
        NetworksItemGroups.MAIN.register(plugin);
        NetworksItemGroups.MATERIALS.register(plugin);
        NetworksItemGroups.TOOLS.register(plugin);
        NetworksItemGroups.NETWORK_ITEMS.register(plugin);
        NetworksItemGroups.NETWORK_QUANTUMS.register(plugin);
        NetworksItemGroups.DISABLED_ITEMS.register(plugin);
    }

    public static class HiddenItemGroup extends ItemGroup {

        public HiddenItemGroup(NamespacedKey key, ItemStack item) {
            super(key, item);
        }

        @Override
        public boolean isHidden(@Nonnull Player p) {
            return true;
        }
    }
}
