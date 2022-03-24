package io.github.sefiraat.networks.slimefun;

import io.github.sefiraat.networks.slimefun.network.NetworkMemoryWiper;
import io.github.sefiraat.networks.slimefun.tools.NetworkCard;
import io.github.sefiraat.networks.slimefun.tools.NetworkRemote;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.text.MessageFormat;

/**
 * Creating SlimefunItemstacks here due to some items being created in Enums so this will
 * act as a one-stop-shop for the stacks themselves.
 */
@UtilityClass
public class NetworksSlimefunItemStacks {

    // Materials
    public static final SlimefunItemStack SYNTHETIC_EMERALD_SHARD;
    public static final SlimefunItemStack OPTIC_GLASS;
    public static final SlimefunItemStack OPTIC_CABLE;
    public static final SlimefunItemStack OPTIC_STAR;
    public static final SlimefunItemStack RADIOACTIVE_OPTIC_STAR;
    public static final SlimefunItemStack SHRINKING_BASE;
    public static final SlimefunItemStack SIMPLE_NANOBOTS;
    public static final SlimefunItemStack ADVANCED_NANOBOTS;
    public static final SlimefunItemStack AI_CORE;
    public static final SlimefunItemStack EMPOWERED_AI_CORE;
    public static final SlimefunItemStack PRISTINE_AI_CORE;
    public static final SlimefunItemStack INTERDIMENSIONAL_PRESENCE;

    // Network Items
    public static final SlimefunItemStack NETWORK_CONTROLLER;
    public static final SlimefunItemStack NETWORK_BRIDGE;
    public static final SlimefunItemStack NETWORK_MONITOR;
    public static final SlimefunItemStack NETWORK_IMPORT;
    public static final SlimefunItemStack NETWORK_EXPORT;
    public static final SlimefunItemStack NETWORK_GRABBER;
    public static final SlimefunItemStack NETWORK_PUSHER;
    public static final SlimefunItemStack NETWORK_PURGER;
    public static final SlimefunItemStack NETWORK_GRID;
    public static final SlimefunItemStack NETWORK_CRAFTING_GRID;
    public static final SlimefunItemStack NETWORK_CELL;
    public static final SlimefunItemStack NETWORK_MEMORY_SHELL;
    public static final SlimefunItemStack NETWORK_QUANTUM_WORKBENCH;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_1;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_2;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_3;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_4;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_5;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_6;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_7;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_8;
    public static final SlimefunItemStack NETWORK_MEMORY_WIPER_1;
    public static final SlimefunItemStack NETWORK_MEMORY_WIPER_2;
    public static final SlimefunItemStack NETWORK_MEMORY_WIPER_3;
    public static final SlimefunItemStack NETWORK_MEMORY_WIPER_4;
    public static final SlimefunItemStack NETWORK_CAPACITOR_1;
    public static final SlimefunItemStack NETWORK_CAPACITOR_2;
    public static final SlimefunItemStack NETWORK_POWER_DISPLAY;
    public static final SlimefunItemStack NETWORK_RECIPE_ENCODER;
    public static final SlimefunItemStack NETWORK_AUTO_CRAFTER;
    public static final SlimefunItemStack NETWORK_AUTO_CRAFTER_WITHHOLDING;

    // Tools
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_1;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_2;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_3;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_4;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_5;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_6;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_7;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_8;
    public static final SlimefunItemStack CRAFTING_BLUEPRINT;
    public static final SlimefunItemStack NETWORK_PROBE;
    public static final SlimefunItemStack NETWORK_REMOTE;
    public static final SlimefunItemStack NETWORK_REMOTE_EMPOWERED;
    public static final SlimefunItemStack NETWORK_REMOTE_PRISTINE;
    public static final SlimefunItemStack NETWORK_REMOTE_ULTIMATE;
    public static final SlimefunItemStack NETWORK_CRAYON;
    public static final SlimefunItemStack NETWORK_CONFIGURATOR;
    public static final SlimefunItemStack NETWORK_RAKE_1;
    public static final SlimefunItemStack NETWORK_RAKE_2;
    public static final SlimefunItemStack NETWORK_RAKE_3;

    static {

        SYNTHETIC_EMERALD_SHARD = Theme.themedSlimefunItemStack(
            "NTW_SYNTHETIC_EMERALD_SHARD",
            new ItemStack(Material.LIME_DYE),
            Theme.CRAFTING,
            "Synthetic Emerald Shard",
            "A shard of synthetic emerald that",
            "is the backbone for information",
            "transference."
        );

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

        RADIOACTIVE_OPTIC_STAR = Theme.themedSlimefunItemStack(
            "NTW_RADIOACTIVE_OPTIC_STAR",
            getPreEnchantedItemStack(Material.NETHER_STAR, true, new Pair<>(Enchantment.ARROW_DAMAGE, 1)),
            Theme.CRAFTING,
            "Radioactive Optic Star",
            "A crystalline star structure that",
            "can store insane amounts of information."
        );

        SHRINKING_BASE = Theme.themedSlimefunItemStack(
            "NTW_SHRINKING_BASE",
            getPreEnchantedItemStack(Material.PISTON, true, new Pair<>(Enchantment.ARROW_DAMAGE, 1)),
            Theme.CRAFTING,
            "Shrinking Base",
            "An advanced construct able to make",
            "big things go small."
        );

        SIMPLE_NANOBOTS = Theme.themedSlimefunItemStack(
            "NTW_SIMPLE_NANOBOTS",
            new ItemStack(Material.MELON_SEEDS),
            Theme.CRAFTING,
            "Simple Nanobots",
            "Teeny Tiny little bots that can",
            "help you with precise tasks."
        );

        ADVANCED_NANOBOTS = Theme.themedSlimefunItemStack(
            "NTW_ADVANCED_NANOBOTS",
            getPreEnchantedItemStack(Material.MELON_SEEDS, true, new Pair<>(Enchantment.ARROW_DAMAGE, 1)),
            Theme.CRAFTING,
            "Advanced Nanobots",
            "Teeny Tiny little bots that can",
            "help you with precise tasks.",
            "This version is smarter and faster."
        );

        AI_CORE = Theme.themedSlimefunItemStack(
            "NTW_AI_CORE",
            new ItemStack(Material.BRAIN_CORAL_BLOCK),
            Theme.CRAFTING,
            "A.I. Core",
            "A burgeoning artificial intelligence",
            "resides within this weak shell."
        );

        EMPOWERED_AI_CORE = Theme.themedSlimefunItemStack(
            "NTW_EMPOWERED_AI_CORE",
            new ItemStack(Material.TUBE_CORAL_BLOCK),
            Theme.CRAFTING,
            "Empowered A.I. Core",
            "A flourishing artificial intelligence",
            "resides within this shell."
        );

        PRISTINE_AI_CORE = Theme.themedSlimefunItemStack(
            "NTW_PRISTINE_AI_CORE",
            getPreEnchantedItemStack(Material.TUBE_CORAL_BLOCK, true, new Pair<>(Enchantment.ARROW_DAMAGE, 1)),
            Theme.CRAFTING,
            "Pristine A.I. Core",
            "A perfected artificial intelligence",
            "resides within this defined shell."
        );

        INTERDIMENSIONAL_PRESENCE = Theme.themedSlimefunItemStack(
            "NTW_INTERDIMENSIONAL_PRESENCE",
            getPreEnchantedItemStack(Material.ARMOR_STAND, true, new Pair<>(Enchantment.ARROW_DAMAGE, 1)),
            Theme.CRAFTING,
            "Interdimensional Presence",
            "An artificial intelligence that has",
            "grown too powerful for just a",
            "single dimension."
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
            "Infinity Barrels",
            "Network Shells"
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

        NETWORK_GRABBER = Theme.themedSlimefunItemStack(
            "NTW_GRABBER",
            new ItemStack(Material.MAGENTA_STAINED_GLASS),
            Theme.MACHINE,
            "Network Grabber",
            "The Network Grabber will try",
            "to grab the first item it finds",
            "from within the selected machine."
        );

        NETWORK_PUSHER = Theme.themedSlimefunItemStack(
            "NTW_PUSHER",
            new ItemStack(Material.BROWN_STAINED_GLASS),
            Theme.MACHINE,
            "Network Pusher",
            "The Network Pusher will try",
            "to push a matching item from a",
            "given item into the chosen machine."
        );

        NETWORK_PURGER = Theme.themedSlimefunItemStack(
            "NTW_TRASH",
            new ItemStack(Material.OBSERVER),
            Theme.MACHINE,
            "Network Purger",
            "The Network Purger will pull",
            "matching items from the network",
            "and instantly void them.",
            "Use with great care!"
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

        NETWORK_MEMORY_SHELL = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_SHELL",
            new ItemStack(Material.DEEPSLATE_TILES),
            Theme.MACHINE,
            "Network Memory Shell",
            "The Network Shell is a block",
            "that provides the network access",
            "to the memory cards inside of it."
        );

        NETWORK_QUANTUM_WORKBENCH = Theme.themedSlimefunItemStack(
            "NTW_QUANTUM_WORKBENCH",
            new ItemStack(Material.DRIED_KELP_BLOCK),
            Theme.MACHINE,
            "Network Quantum Workbench",
            "Allows the crafting of Quantum Storages."
        );


        NETWORK_QUANTUM_STORAGE_1 = Theme.themedSlimefunItemStack(
            "NTW_QUANTUM_STORAGE_1",
            new ItemStack(Material.WHITE_TERRACOTTA),
            Theme.MACHINE,
            "Network Quantum Storage (4K)",
            "Stores " + NetworkCard.getSizes()[0] + " items",
            "",
            "Stores items in mass quantities within",
            "a quantum singularity."
        );

        NETWORK_QUANTUM_STORAGE_2 = Theme.themedSlimefunItemStack(
            "NTW_QUANTUM_STORAGE_2",
            new ItemStack(Material.LIGHT_GRAY_TERRACOTTA),
            Theme.MACHINE,
            "Network Quantum Storage (32K)",
            "Stores " + NetworkCard.getSizes()[1] + " items",
            "",
            "Stores items in mass quantities within",
            "a quantum singularity."
        );

        NETWORK_QUANTUM_STORAGE_3 = Theme.themedSlimefunItemStack(
            "NTW_QUANTUM_STORAGE_3",
            new ItemStack(Material.GRAY_TERRACOTTA),
            Theme.MACHINE,
            "Network Quantum Storage (262K)",
            "Stores " + NetworkCard.getSizes()[2] + " items",
            "",
            "Stores items in mass quantities within",
            "a quantum singularity."
        );

        NETWORK_QUANTUM_STORAGE_4 = Theme.themedSlimefunItemStack(
            "NTW_QUANTUM_STORAGE_4",
            new ItemStack(Material.BROWN_TERRACOTTA),
            Theme.MACHINE,
            "Network Quantum Storage (2M)",
            "Stores " + NetworkCard.getSizes()[3] + " items",
            "",
            "Stores items in mass quantities within",
            "a quantum singularity."
        );

        NETWORK_QUANTUM_STORAGE_5 = Theme.themedSlimefunItemStack(
            "NTW_QUANTUM_STORAGE_5",
            new ItemStack(Material.BLACK_TERRACOTTA),
            Theme.MACHINE,
            "Network Quantum Storage (16M)",
            "Stores " + NetworkCard.getSizes()[4] + " items",
            "",
            "Stores items in mass quantities within",
            "a quantum singularity."
        );

        NETWORK_QUANTUM_STORAGE_6 = Theme.themedSlimefunItemStack(
            "NTW_QUANTUM_STORAGE_6",
            new ItemStack(Material.PURPLE_TERRACOTTA),
            Theme.MACHINE,
            "Network Quantum Storage (134M)",
            "Stores " + NetworkCard.getSizes()[5] + " items",
            "",
            "Stores items in mass quantities within",
            "a quantum singularity."
        );

        NETWORK_QUANTUM_STORAGE_7 = Theme.themedSlimefunItemStack(
            "NTW_QUANTUM_STORAGE_7",
            new ItemStack(Material.MAGENTA_TERRACOTTA),
            Theme.MACHINE,
            "Network Quantum Storage (1B)",
            "Stores " + NetworkCard.getSizes()[6] + " items",
            "",
            "Stores items in mass quantities within",
            "a quantum singularity."
        );

        NETWORK_QUANTUM_STORAGE_8 = Theme.themedSlimefunItemStack(
            "NTW_QUANTUM_STORAGE_8",
            new ItemStack(Material.RED_TERRACOTTA),
            Theme.MACHINE,
            "Network Quantum Storage (∞)",
            "Stores ∞ items... almost",
            "",
            "Stores items in mass quantities within",
            "a quantum singularity."
        );

        NETWORK_MEMORY_WIPER_1 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_WIPER_1",
            new ItemStack(Material.BASALT),
            Theme.MACHINE,
            "Network Memory Wiper α",
            "The Network Wiper is a block",
            "that will slowly try to empty a",
            "memory card's content back into",
            "the network.",
            "",
            MessageFormat.format("{0}Speed: {1}{2} Stack(s)/t", Theme.CLICK_INFO, Theme.PASSIVE, NetworkMemoryWiper.getStacksToPush()[0])
        );

        NETWORK_MEMORY_WIPER_2 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_WIPER_2",
            new ItemStack(Material.POLISHED_BASALT),
            Theme.MACHINE,
            "Network Memory Wiper β",
            "The Network Wiper is a block",
            "that will slowly try to empty a",
            "memory card's content back into",
            "the network.",
            "",
            MessageFormat.format("{0}Speed: {1}{2} Stack(s)/t", Theme.CLICK_INFO, Theme.PASSIVE, NetworkMemoryWiper.getStacksToPush()[1])
        );

        NETWORK_MEMORY_WIPER_3 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_WIPER_3",
            new ItemStack(Material.SMOOTH_BASALT),
            Theme.MACHINE,
            "Network Memory Wiper γ",
            "The Network Wiper is a block",
            "that will slowly try to empty a",
            "memory card's content back into",
            "the network.",
            "",
            MessageFormat.format("{0}Speed: {1}{2} Stack(s)/t", Theme.CLICK_INFO, Theme.PASSIVE, NetworkMemoryWiper.getStacksToPush()[2])
        );

        NETWORK_MEMORY_WIPER_4 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_WIPER_4",
            new ItemStack(Material.POLISHED_BLACKSTONE),
            Theme.MACHINE,
            "Network Memory Wiper δ",
            "The Network Wiper is a block",
            "that will slowly try to empty a",
            "memory card's content back into",
            "the network.",
            "",
            MessageFormat.format("{0}Speed: {1}{2} Stack(s)/t", Theme.CLICK_INFO, Theme.PASSIVE, NetworkMemoryWiper.getStacksToPush()[3])
        );

        NETWORK_CAPACITOR_1 = Theme.themedSlimefunItemStack(
            "NTW_CAPACITOR_1",
            new ItemStack(Material.BROWN_GLAZED_TERRACOTTA),
            Theme.MACHINE,
            "Network Capacitor (1)",
            "The Network Capacitor can take",
            "power in and store it for use",
            "within the network.",
            "",
            MessageFormat.format("{0}Capacity: {1}{2}", Theme.CLICK_INFO, Theme.PASSIVE, 1000)
        );

        NETWORK_CAPACITOR_2 = Theme.themedSlimefunItemStack(
            "NTW_CAPACITOR_2",
            new ItemStack(Material.GREEN_GLAZED_TERRACOTTA),
            Theme.MACHINE,
            "Network Capacitor (2)",
            "The Network Capacitor can take",
            "power in and store it for use",
            "within the network.",
            "",
            MessageFormat.format("{0}Capacity: {1}{2}", Theme.CLICK_INFO, Theme.PASSIVE, 10000)
        );

        NETWORK_POWER_DISPLAY = Theme.themedSlimefunItemStack(
            "NTW_POWER_DISPLAY",
            new ItemStack(Material.TINTED_GLASS),
            Theme.MACHINE,
            "Network Power Display",
            "The Network Power Display will",
            "display the power in the network.",
            "Simple, right?"
        );

        NETWORK_RECIPE_ENCODER = Theme.themedSlimefunItemStack(
            "NTW_RECIPE_ENCODER",
            new ItemStack(Material.TARGET),
            Theme.MACHINE,
            "Network Recipe Encoder",
            "Used to form a Crafting Blueprint",
            "from input items.",
            "",
            MessageFormat.format("{0}Network Drain: {1}{2}/encode", Theme.CLICK_INFO, Theme.PASSIVE, 20000)
        );

        NETWORK_AUTO_CRAFTER = Theme.themedSlimefunItemStack(
            "NTW_AUTO_CRAFTER",
            new ItemStack(Material.BLACK_GLAZED_TERRACOTTA),
            Theme.MACHINE,
            "Network Auto Crafter",
            "The Network Auto Crafter accepts",
            "a crafting blueprint. When the",
            "blueprint output item is requested",
            "while there is none in the network",
            "it will be crafted if you have",
            "materials.",
            "",
            MessageFormat.format("{0}Network Drain: {1}{2}/craft", Theme.CLICK_INFO, Theme.PASSIVE, 64)
        );

        NETWORK_AUTO_CRAFTER_WITHHOLDING = Theme.themedSlimefunItemStack(
            "NTW_AUTO_CRAFTER_WITHHOLDING",
            new ItemStack(Material.WHITE_GLAZED_TERRACOTTA),
            Theme.MACHINE,
            "Network Auto Crafter (Withholding)",
            "The Network Auto Crafter accepts",
            "a crafting blueprint. When the",
            "blueprint output item is requested",
            "while there is none in the network",
            "it will be crafted if you have",
            "materials.",
            "",
            "A Withholding Crafter will keep",
            "a stack in the output and stop",
            "crafting. The stack can been seen",
            "in the Network and also allows for",
            "cargo.",
            "",
            MessageFormat.format("{0}Network Drain: {1}{2}/craft", Theme.CLICK_INFO, Theme.PASSIVE, 128)
        );

        NETWORK_MEMORY_CARD_1 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_1",
            new ItemStack(Material.LIGHT_GRAY_DYE),
            Theme.TOOL,
            "Network Memory Card (4K)",
            "Stores " + NetworkCard.getSizes()[0] + " items",
            "",
            "Right click with an item in your",
            "offhand to set the card's item.",
            "Card must be empty to set item.",
            "",
            Theme.WARNING + "Voids extra incoming items.",
            Theme.WARNING + "Upgrading will wipe memory.",
            "",
            Theme.WARNING + "Empty"
        );

        NETWORK_MEMORY_CARD_2 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_2",
            new ItemStack(Material.GRAY_DYE),
            Theme.TOOL,
            "Network Memory Card (32K)",
            "Stores " + NetworkCard.getSizes()[1] + " items",
            "",
            "Right click with an item in your",
            "offhand to set the card's item.",
            "Card must be empty to set item.",
            "",
            Theme.WARNING + "Voids extra incoming items.",
            Theme.WARNING + "Upgrading will wipe memory.",
            "",
            Theme.WARNING + "Empty"
        );

        NETWORK_MEMORY_CARD_3 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_3",
            new ItemStack(Material.LIME_DYE),
            Theme.TOOL,
            "Network Memory Card (262K)",
            "Stores " + NetworkCard.getSizes()[2] + " items",
            "",
            "Right click with an item in your",
            "offhand to set the card's item.",
            "Card must be empty to set item.",
            "",
            Theme.WARNING + "Voids extra incoming items.",
            Theme.WARNING + "Upgrading will wipe memory.",
            "",
            Theme.WARNING + "Empty"
        );

        NETWORK_MEMORY_CARD_4 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_4",
            new ItemStack(Material.GREEN_DYE),
            Theme.TOOL,
            "Network Memory Card (2M)",
            "Stores " + NetworkCard.getSizes()[3] + " items",
            "",
            "Right click with an item in your",
            "offhand to set the card's item.",
            "Card must be empty to set item.",
            "",
            Theme.WARNING + "Voids extra incoming items.",
            Theme.WARNING + "Upgrading will wipe memory.",
            "",
            Theme.WARNING + "Empty"
        );

        NETWORK_MEMORY_CARD_5 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_5",
            new ItemStack(Material.LIGHT_BLUE_DYE),
            Theme.TOOL,
            "Network Memory Card (16M)",
            "Stores " + NetworkCard.getSizes()[4] + " items",
            "",
            "Right click with an item in your",
            "offhand to set the card's item.",
            "Card must be empty to set item.",
            "",
            Theme.WARNING + "Voids extra incoming items.",
            Theme.WARNING + "Upgrading will wipe memory.",
            "",
            Theme.WARNING + "Empty"
        );

        NETWORK_MEMORY_CARD_6 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_6",
            new ItemStack(Material.BLUE_DYE),
            Theme.TOOL,
            "Network Memory Card (134M)",
            "Stores " + NetworkCard.getSizes()[5] + " items",
            "",
            "Right click with an item in your",
            "offhand to set the card's item.",
            "Card must be empty to set item.",
            "",
            Theme.WARNING + "Voids extra incoming items.",
            Theme.WARNING + "Upgrading will wipe memory.",
            "",
            Theme.WARNING + "Empty"
        );

        NETWORK_MEMORY_CARD_7 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_7",
            new ItemStack(Material.PINK_DYE),
            Theme.TOOL,
            "Network Memory Card (1B)",
            "Stores " + NetworkCard.getSizes()[6] + " items",
            "",
            "Right click with an item in your",
            "offhand to set the card's item.",
            "Card must be empty to set item.",
            "",
            Theme.WARNING + "Voids extra incoming items.",
            Theme.WARNING + "Upgrading will wipe memory.",
            "",
            Theme.WARNING + "Empty"
        );

        NETWORK_MEMORY_CARD_8 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_8",
            new ItemStack(Material.RED_DYE),
            Theme.TOOL,
            "Network Memory Card (∞)",
            "Stores ∞ items... almost",
            "",
            "Right click with an item in your",
            "offhand to set the card's item.",
            "Card must be empty to set item.",
            "",
            Theme.WARNING + "Voids extra incoming items.",
            Theme.WARNING + "Upgrading will wipe memory.",
            "",
            Theme.WARNING + "Empty"
        );

        CRAFTING_BLUEPRINT = Theme.themedSlimefunItemStack(
            "NTW_CRAFTING_BLUEPRINT",
            new ItemStack(Material.BLUE_DYE),
            Theme.TOOL,
            "Crafting Blueprint",
            "A blank blueprint that can",
            "be used to store a crafting",
            "recipe."
        );

        NETWORK_PROBE = Theme.themedSlimefunItemStack(
            "NTW_PROBE",
            new ItemStack(Material.CLOCK),
            Theme.TOOL,
            "Network Probe",
            "When used on a controller, this will",
            "show the nodes on the network."
        );

        NETWORK_REMOTE = Theme.themedSlimefunItemStack(
            "NTW_REMOTE",
            new ItemStack(Material.PAINTING),
            Theme.TOOL,
            "Network Remote",
            "Opens a bound grid wirelessly.",
            "The grid must be chunk loaded.",
            "",
            MessageFormat.format("{0}Range: {1}{2}", Theme.CLICK_INFO, Theme.PASSIVE, NetworkRemote.getRanges()[0])
        );

        NETWORK_REMOTE_EMPOWERED = Theme.themedSlimefunItemStack(
            "NTW_REMOTE_EMPOWERED",
            new ItemStack(Material.ITEM_FRAME),
            Theme.TOOL,
            "Network Remote Empowered",
            "Opens a bound grid wirelessly.",
            "The grid must be chunk loaded.",
            "",
            MessageFormat.format("{0}Range: {1}{2}", Theme.CLICK_INFO, Theme.PASSIVE, NetworkRemote.getRanges()[1])
        );

        NETWORK_REMOTE_PRISTINE = Theme.themedSlimefunItemStack(
            "NTW_REMOTE_PRISTINE",
            new ItemStack(Material.GLOW_ITEM_FRAME),
            Theme.TOOL,
            "Network Remote Pristine",
            "Opens a bound grid wirelessly.",
            "The grid must be chunk loaded.",
            "",
            MessageFormat.format("{0}Range: {1}{2}", Theme.CLICK_INFO, Theme.PASSIVE, "Unlimited")
        );

        NETWORK_REMOTE_ULTIMATE = Theme.themedSlimefunItemStack(
            "NTW_REMOTE_ULTIMATE",
            getPreEnchantedItemStack(Material.GLOW_ITEM_FRAME, true, new Pair<>(Enchantment.ARROW_DAMAGE, 1)),
            Theme.TOOL,
            "Network Remote Ultimate",
            "Opens a bound grid wirelessly.",
            "The grid must be chunk loaded.",
            "",
            MessageFormat.format("{0}Range: {1}{2}", Theme.CLICK_INFO, Theme.PASSIVE, "Cross Dimensional")
        );

        NETWORK_CRAYON = Theme.themedSlimefunItemStack(
            "NTW_CRAYON",
            new ItemStack(Material.RED_CANDLE),
            Theme.TOOL,
            "Network Crayon",
            "When used on a controller, this will",
            "enable particle display from specific",
            "blocks when working."
        );

        NETWORK_CONFIGURATOR = Theme.themedSlimefunItemStack(
            "NTW_CONFIGURATOR",
            new ItemStack(Material.BLAZE_ROD),
            Theme.TOOL,
            "Network Configurator",
            "Used to copy and paste the",
            "configurations of directional",
            "interfaces.",
            "",
            MessageFormat.format("{0}Right Click: {1}{2}", Theme.CLICK_INFO, Theme.PASSIVE, "Apply Config"),
            MessageFormat.format("{0}Shift Right Click: {1}{2}", Theme.CLICK_INFO, Theme.PASSIVE, "Store Config")
        );

        NETWORK_RAKE_1 = Theme.themedSlimefunItemStack(
            "NTW_RAKE_1",
            new ItemStack(Material.TWISTING_VINES),
            Theme.TOOL,
            "Network Rake (1)",
            "Right click a Network Object to",
            "break it instantly.",
            "",
            ChatColor.YELLOW + "250 Uses " + ChatColor.GRAY + "left"
        );

        NETWORK_RAKE_2 = Theme.themedSlimefunItemStack(
            "NTW_RAKE_2",
            new ItemStack(Material.WEEPING_VINES),
            Theme.TOOL,
            "Network Rake (2)",
            "Right click a Network Object to",
            "break it instantly.",
            "",
            ChatColor.YELLOW + "1000 Uses " + ChatColor.GRAY + "left"
        );

        NETWORK_RAKE_3 = Theme.themedSlimefunItemStack(
            "NTW_RAKE_3",
            getPreEnchantedItemStack(Material.WEEPING_VINES, true, new Pair<>(Enchantment.LUCK, 1)),
            Theme.TOOL,
            "Network Rake (3)",
            "Right click a Network Object to",
            "break it instantly.",
            "",
            ChatColor.YELLOW + "9999 Uses " + ChatColor.GRAY + "left"
        );
    }

    @Nonnull
    @SafeVarargs
    public static ItemStack getPreEnchantedItemStack(Material material, boolean hide, @Nonnull Pair<Enchantment, Integer>... enchantments) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (Pair<Enchantment, Integer> pair : enchantments) {
            itemMeta.addEnchant(pair.getFirstValue(), pair.getSecondValue(), true);
        }
        if (hide) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
