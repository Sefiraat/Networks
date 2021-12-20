package io.github.sefiraat.networks.slimefun;

import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

/**
 * Creating SlimefunItemstacks here due to some items being created in Enums so this will
 * act as a one-stop-shop for the stacks themselves.
 */
@UtilityClass
public class NetworksSlimefunItemStacks {

    // Materials
    public static final SlimefunItemStack OPTIC_GLASS;
    public static final SlimefunItemStack OPTIC_CABLE;
    public static final SlimefunItemStack OPTIC_STAR;

    // Network Items
    public static final SlimefunItemStack NETWORK_CONTROLLER;
    public static final SlimefunItemStack NETWORK_BRIDGE;
    public static final SlimefunItemStack NETWORK_MONITOR;
    public static final SlimefunItemStack NETWORK_IMPORT;
    public static final SlimefunItemStack NETWORK_EXPORT;
    public static final SlimefunItemStack NETWORK_GRID;
    public static final SlimefunItemStack NETWORK_CELL;
    public static final SlimefunItemStack NETWORK_CRAFTING_GRID;

    // Tools
    public static final SlimefunItemStack NETWORK_PROBE;

    static {

        OPTIC_GLASS = Theme.themedSlimefunItemStack(
            "NTW_OPTIC_GLASS",
            new ItemStack(Material.GLASS),
            Theme.CRAFTING,
            "Optic Glass",
            "A simple glass that is able to",
            "transfer small bits of information."
        );

        OPTIC_CABLE = Theme.themedSlimefunItemStack(
            "NTW_OPTIC_CABLE",
            new ItemStack(Material.STRING),
            Theme.CRAFTING,
            "Optic Cable",
            "A simple wire that is able to",
            "transfer large bits of information."
        );

        OPTIC_STAR = Theme.themedSlimefunItemStack(
            "NTW_OPTIC_STAR",
            new ItemStack(Material.NETHER_STAR),
            Theme.CRAFTING,
            "Optic Star",
            "A crystalline star structure that",
            "can transfer large bits of information."
        );

        NETWORK_CONTROLLER = Theme.themedSlimefunItemStack(
            "NTW_CONTROLLER",
            new ItemStack(Material.BLACK_STAINED_GLASS),
            Theme.MACHINE,
            "Network Controller"
            , "The Network controller is the brain",
            "for the whole network. Max 1 per network."
        );

        NETWORK_BRIDGE = Theme.themedSlimefunItemStack(
            "NTW_BRIDGE",
            new ItemStack(Material.WHITE_STAINED_GLASS),
            Theme.MACHINE,
            "Network Bridge"
            , "The bridge allows you to cheaply",
            "connect network objects together."
        );

        NETWORK_MONITOR = Theme.themedSlimefunItemStack(
            "NTW_MONITOR",
            new ItemStack(Material.GREEN_STAINED_GLASS),
            Theme.MACHINE,
            "Network Monitor",
            "The Network Monitor allows simple",
            "import/export interaction with adjacent",
            "objects.",
            "",
            "Currently Supports:",
            "Infinity Barrels"
        );

        NETWORK_IMPORT = Theme.themedSlimefunItemStack(
            "NTW_IMPORT",
            new ItemStack(Material.RED_STAINED_GLASS),
            Theme.MACHINE,
            "Network Importer",
            "The Network Importer brings any",
            "item inside it into the network, up",
            "to 9 stacks per SF tick.",
            "Accepts items in from cargo."
        );

        NETWORK_EXPORT = Theme.themedSlimefunItemStack(
            "NTW_EXPORT",
            new ItemStack(Material.BLUE_STAINED_GLASS),
            Theme.MACHINE,
            "Network Exporter",
            "The Network Exporter can be set to",
            "constantly export 1 stack of any",
            "given item.",
            "Accepts item withdrawal from cargo."
        );

        NETWORK_GRID = Theme.themedSlimefunItemStack(
            "NTW_GRID",
            new ItemStack(Material.NOTE_BLOCK),
            Theme.MACHINE,
            "Network Grid",
            "The Network Grid shows you all",
            "the items you have in the network",
            "and lets you insert or withdraw",
            "directly."
        );

        NETWORK_CRAFTING_GRID = Theme.themedSlimefunItemStack(
            "NTW_CRAFTING_GRID",
            new ItemStack(Material.REDSTONE_LAMP),
            Theme.MACHINE,
            "Network Crafting Grid",
            "The Network Crafting Grid acts",
            "like a normal grid but displays less",
            "items but allows crafting using items",
            "directly from the network."
        );

        NETWORK_CELL = Theme.themedSlimefunItemStack(
            "NTW_CELL",
            new ItemStack(Material.HONEYCOMB_BLOCK),
            Theme.MACHINE,
            "Network Cell",
            "The Network Cell is a large",
            "(double chest) inventory that can",
            "be accessed both from the network",
            "and in the world."
        );

        NETWORK_PROBE = Theme.themedSlimefunItemStack(
            "NTW_PROBE",
            new ItemStack(Material.CLOCK),
            Theme.TOOL,
            "Network Probe",
            "When used on a controller, this will",
            "show the nodes on the network."
        );
    }

    @Nonnull
    @SafeVarargs
    public static ItemStack getPreEnchantedItemStack(Material material, @Nonnull Pair<Enchantment, Integer>... enchantments) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (Pair<Enchantment, Integer> pair : enchantments) {
            itemMeta.addEnchant(pair.getFirstValue(), pair.getSecondValue(), true);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
