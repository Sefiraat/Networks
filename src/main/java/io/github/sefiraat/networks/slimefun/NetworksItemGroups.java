package io.github.sefiraat.networks.slimefun;

import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public final class NetworksItemGroups {

    public static final NestedItemGroup MAIN = new NestedItemGroup(
        Keys.newKey("main"),
        new CustomItemStack(
            new ItemStack(Material.BLACK_STAINED_GLASS),
            Theme.MAIN.getColor() + "Networks"
        )
    );

    public static final SubItemGroup MATERIALS = new SubItemGroup(
        Keys.newKey("materials"),
        MAIN,
        new CustomItemStack(
            new ItemStack(Material.WHITE_STAINED_GLASS),
            Theme.MAIN.getColor() + "Crafting Materials"
        )
    );

    public static final SubItemGroup TOOLS = new SubItemGroup(
        Keys.newKey("tools"),
        MAIN,
        new CustomItemStack(
            new ItemStack(Material.PAINTING),
            Theme.MAIN.getColor() + "Memory Cards and Tools"
        )
    );

    public static final SubItemGroup NETWORK_ITEMS = new SubItemGroup(
        Keys.newKey("network_items"),
        MAIN,
        new CustomItemStack(
            new ItemStack(Material.BLACK_STAINED_GLASS),
            Theme.MAIN.getColor() + "Network Items"
        )
    );

    static {
        final Networks plugin = Networks.getInstance();

        // Slimefun Registry
        NetworksItemGroups.MAIN.register(plugin);
        NetworksItemGroups.MATERIALS.register(plugin);
        NetworksItemGroups.TOOLS.register(plugin);
        NetworksItemGroups.NETWORK_ITEMS.register(plugin);
    }
}
