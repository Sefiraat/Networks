package io.github.sefiraat.networks.slimefun;

import dev.sefiraat.sefilib.itemstacks.ItemStackGenerators;
import dev.sefiraat.sefilib.string.Theme;
import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumStorage;
import io.github.sefiraat.networks.slimefun.tools.NetworkRemote;
import io.github.sefiraat.networks.utils.Themes;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.items.LimitedUseItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
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
    public static final SlimefunItemStack NETWORK_VANILLA_GRABBER;
    public static final SlimefunItemStack NETWORK_VANILLA_PUSHER;
    public static final SlimefunItemStack NETWORK_WIRELESS_TRANSMITTER;
    public static final SlimefunItemStack NETWORK_WIRELESS_RECEIVER;
    public static final SlimefunItemStack NETWORK_PURGER;
    public static final SlimefunItemStack NETWORK_GRID;
    public static final SlimefunItemStack NETWORK_CRAFTING_GRID;
    public static final SlimefunItemStack NETWORK_CELL;
    public static final SlimefunItemStack NETWORK_GREEDY_BLOCK;
    public static final SlimefunItemStack NETWORK_QUANTUM_WORKBENCH;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_1;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_2;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_3;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_4;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_5;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_6;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_7;
    public static final SlimefunItemStack NETWORK_QUANTUM_STORAGE_8;
    public static final SlimefunItemStack NETWORK_CAPACITOR_1;
    public static final SlimefunItemStack NETWORK_CAPACITOR_2;
    public static final SlimefunItemStack NETWORK_CAPACITOR_3;
    public static final SlimefunItemStack NETWORK_POWER_OUTLET_1;
    public static final SlimefunItemStack NETWORK_POWER_OUTLET_2;
    public static final SlimefunItemStack NETWORK_POWER_DISPLAY;
    public static final SlimefunItemStack NETWORK_RECIPE_ENCODER;
    public static final SlimefunItemStack NETWORK_AUTO_CRAFTER;
    public static final SlimefunItemStack NETWORK_AUTO_CRAFTER_WITHHOLDING;

    // Tools
    public static final SlimefunItemStack CRAFTING_BLUEPRINT;
    public static final SlimefunItemStack NETWORK_PROBE;
    public static final SlimefunItemStack NETWORK_REMOTE;
    public static final SlimefunItemStack NETWORK_REMOTE_EMPOWERED;
    public static final SlimefunItemStack NETWORK_REMOTE_PRISTINE;
    public static final SlimefunItemStack NETWORK_REMOTE_ULTIMATE;
    public static final SlimefunItemStack NETWORK_CRAYON;
    public static final SlimefunItemStack NETWORK_CONFIGURATOR;
    public static final SlimefunItemStack NETWORK_WIRELESS_CONFIGURATOR;
    public static final SlimefunItemStack NETWORK_RAKE_1;
    public static final SlimefunItemStack NETWORK_RAKE_2;
    public static final SlimefunItemStack NETWORK_RAKE_3;

    static {

        SYNTHETIC_EMERALD_SHARD = Networks.getLanguageManager().getThemedStack(
            "NTW_SYNTHETIC_EMERALD_SHARD",
            new ItemStack(Material.LIME_DYE),
            Themes.CRAFTING,
            "synthetic-emerald-shard"
        );

        OPTIC_GLASS = Networks.getLanguageManager().getThemedStack(
            "NTW_OPTIC_GLASS",
            new ItemStack(Material.GLASS),
            Themes.CRAFTING,
            "optic-glass"
        );

        OPTIC_CABLE = Networks.getLanguageManager().getThemedStack(
            "NTW_OPTIC_CABLE",
            new ItemStack(Material.STRING),
            Themes.CRAFTING,
            "optic-cable"
        );

        OPTIC_STAR = Networks.getLanguageManager().getThemedStack(
            "NTW_OPTIC_STAR",
            new ItemStack(Material.NETHER_STAR),
            Themes.CRAFTING,
            "optic-star"
        );

        RADIOACTIVE_OPTIC_STAR = Networks.getLanguageManager().getThemedStack(
            "NTW_RADIOACTIVE_OPTIC_STAR",
            ItemStackGenerators.createEnchantedItemStack(
                Material.NETHER_STAR,
                true,
                new Pair<>(Enchantment.ARROW_DAMAGE, 1)
            ),
            Themes.CRAFTING,
            "radioactive-optic-star"
        );

        SHRINKING_BASE = Networks.getLanguageManager().getThemedStack(
            "NTW_SHRINKING_BASE",
            ItemStackGenerators.createEnchantedItemStack(
                Material.PISTON,
                true,
                new Pair<>(Enchantment.ARROW_DAMAGE, 1)
            ),
            Themes.CRAFTING,
            "shrinking-base"
        );

        SIMPLE_NANOBOTS = Networks.getLanguageManager().getThemedStack(
            "NTW_SIMPLE_NANOBOTS",
            new ItemStack(Material.MELON_SEEDS),
            Themes.CRAFTING,
            "simple-nanobots"
        );

        ADVANCED_NANOBOTS = Networks.getLanguageManager().getThemedStack(
            "NTW_ADVANCED_NANOBOTS",
            ItemStackGenerators.createEnchantedItemStack(
                Material.MELON_SEEDS,
                true,
                new Pair<>(Enchantment.ARROW_DAMAGE, 1)
            ),
            Themes.CRAFTING,
            "advanced-nanobots"
        );

        AI_CORE = Networks.getLanguageManager().getThemedStack(
            "NTW_AI_CORE",
            new ItemStack(Material.BRAIN_CORAL_BLOCK),
            Themes.CRAFTING,
            "ai-core"
        );

        EMPOWERED_AI_CORE = Networks.getLanguageManager().getThemedStack(
            "NTW_EMPOWERED_AI_CORE",
            new ItemStack(Material.TUBE_CORAL_BLOCK),
            Themes.CRAFTING,
            "empowered-ai-core"
        );

        PRISTINE_AI_CORE = Networks.getLanguageManager().getThemedStack(
            "NTW_PRISTINE_AI_CORE",
            ItemStackGenerators.createEnchantedItemStack(
                Material.TUBE_CORAL_BLOCK,
                true,
                new Pair<>(Enchantment.ARROW_DAMAGE, 1)
            ),
            Themes.CRAFTING,
            "pristine-ai-core"
        );

        INTERDIMENSIONAL_PRESENCE = Networks.getLanguageManager().getThemedStack(
            "NTW_INTERDIMENSIONAL_PRESENCE",
            ItemStackGenerators.createEnchantedItemStack(
                Material.ARMOR_STAND,
                true,
                new Pair<>(Enchantment.ARROW_DAMAGE, 1)
            ),
            Themes.CRAFTING,
            "interdimensional-presence"
        );

        NETWORK_CONTROLLER = Networks.getLanguageManager().getThemedStack(
            "NTW_CONTROLLER",
            new ItemStack(Material.BLACK_STAINED_GLASS),
            Themes.MACHINE,
            "network-controller"
        );

        NETWORK_BRIDGE = Networks.getLanguageManager().getThemedStack(
            "NTW_BRIDGE",
            new ItemStack(Material.WHITE_STAINED_GLASS),
            Themes.MACHINE,
            "network-bridge"
        );

        NETWORK_MONITOR = Networks.getLanguageManager().getThemedStack(
            "NTW_MONITOR",
            new ItemStack(Material.GREEN_STAINED_GLASS),
            Themes.MACHINE,
            "network-monitor"
        );

        NETWORK_IMPORT = Networks.getLanguageManager().getThemedStack(
            "NTW_IMPORT",
            new ItemStack(Material.RED_STAINED_GLASS),
            Themes.MACHINE,
            "network-importer"
        );

        NETWORK_EXPORT = Networks.getLanguageManager().getThemedStack(
            "NTW_EXPORT",
            new ItemStack(Material.BLUE_STAINED_GLASS),
            Themes.MACHINE,
            "network-exporter"
        );

        NETWORK_GRABBER = Networks.getLanguageManager().getThemedStack(
            "NTW_GRABBER",
            new ItemStack(Material.MAGENTA_STAINED_GLASS),
            Themes.MACHINE,
            "network-grabber"
        );

        NETWORK_PUSHER = Networks.getLanguageManager().getThemedStack(
            "NTW_PUSHER",
            new ItemStack(Material.BROWN_STAINED_GLASS),
            Themes.MACHINE,
            "network-pusher"
        );

        NETWORK_VANILLA_GRABBER = Networks.getLanguageManager().getThemedStack(
            "NTW_VANILLA_GRABBER",
            new ItemStack(Material.ORANGE_STAINED_GLASS),
            Themes.MACHINE,
            "network-vanilla-grabber"
        );

        NETWORK_VANILLA_PUSHER = Networks.getLanguageManager().getThemedStack(
            "NTW_VANILLA_PUSHER",
            new ItemStack(Material.LIME_STAINED_GLASS),
            Themes.MACHINE,
            "network-vanilla-pusher"
        );

        NETWORK_WIRELESS_TRANSMITTER = Networks.getLanguageManager().getThemedStack(
            "NTW_NETWORK_WIRELESS_TRANSMITTER",
            new ItemStack(Material.CYAN_STAINED_GLASS),
            Themes.MACHINE,
            "network-wireless-transmitter"
        );

        NETWORK_WIRELESS_RECEIVER = Networks.getLanguageManager().getThemedStack(
            "NTW_NETWORK_WIRELESS_RECEIVER",
            new ItemStack(Material.PURPLE_STAINED_GLASS),
            Themes.MACHINE,
            "network-wireless-receiver"
        );

        NETWORK_PURGER = Networks.getLanguageManager().getThemedStack(
            "NTW_TRASH",
            new ItemStack(Material.OBSERVER),
            Themes.MACHINE,
            "network-purger"
        );

        NETWORK_GRID = Networks.getLanguageManager().getThemedStack(
            "NTW_GRID",
            new ItemStack(Material.NOTE_BLOCK),
            Themes.MACHINE,
            "network-grid"
        );

        NETWORK_CRAFTING_GRID = Networks.getLanguageManager().getThemedStack(
            "NTW_CRAFTING_GRID",
            new ItemStack(Material.REDSTONE_LAMP),
            Themes.MACHINE,
            "network-crafting-grid"
        );

        NETWORK_CELL = Networks.getLanguageManager().getThemedStack(
            "NTW_CELL",
            new ItemStack(Material.HONEYCOMB_BLOCK),
            Themes.MACHINE,
            "network-cell"
        );

        NETWORK_GREEDY_BLOCK = Networks.getLanguageManager().getThemedStack(
            "NTW_GREEDY_BLOCK",
            new ItemStack(Material.SHROOMLIGHT),
            Themes.MACHINE,
            "network-greedy-block"
        );

        NETWORK_QUANTUM_WORKBENCH = Networks.getLanguageManager().getThemedStack(
            "NTW_QUANTUM_WORKBENCH",
            new ItemStack(Material.DRIED_KELP_BLOCK),
            Themes.MACHINE,
            "network-quantum-workbench"
        );


        NETWORK_QUANTUM_STORAGE_1 = Networks.getLanguageManager().getThemedStack(
            "NTW_QUANTUM_STORAGE_1",
            new ItemStack(Material.WHITE_TERRACOTTA),
            Themes.MACHINE,
            "network-quantum-storage-1",
            String.valueOf(NetworkQuantumStorage.getSizes()[0])
        );

        NETWORK_QUANTUM_STORAGE_2 = Networks.getLanguageManager().getThemedStack(
            "NTW_QUANTUM_STORAGE_2",
            new ItemStack(Material.LIGHT_GRAY_TERRACOTTA),
            Themes.MACHINE,
            "network-quantum-storage-2",
            String.valueOf(NetworkQuantumStorage.getSizes()[1])
        );

        NETWORK_QUANTUM_STORAGE_3 = Networks.getLanguageManager().getThemedStack(
            "NTW_QUANTUM_STORAGE_3",
            new ItemStack(Material.GRAY_TERRACOTTA),
            Themes.MACHINE,
            "network-quantum-storage-3",
            String.valueOf(NetworkQuantumStorage.getSizes()[2])
        );

        NETWORK_QUANTUM_STORAGE_4 = Networks.getLanguageManager().getThemedStack(
            "NTW_QUANTUM_STORAGE_4",
            new ItemStack(Material.BROWN_TERRACOTTA),
            Themes.MACHINE,
            "network-quantum-storage-4",
            String.valueOf(NetworkQuantumStorage.getSizes()[3])
        );

        NETWORK_QUANTUM_STORAGE_5 = Networks.getLanguageManager().getThemedStack(
            "NTW_QUANTUM_STORAGE_5",
            new ItemStack(Material.BLACK_TERRACOTTA),
            Themes.MACHINE,
            "network-quantum-storage-5",
            String.valueOf(NetworkQuantumStorage.getSizes()[4])
        );

        NETWORK_QUANTUM_STORAGE_6 = Networks.getLanguageManager().getThemedStack(
            "NTW_QUANTUM_STORAGE_6",
            new ItemStack(Material.PURPLE_TERRACOTTA),
            Themes.MACHINE,
            "network-quantum-storage-6",
            String.valueOf(NetworkQuantumStorage.getSizes()[5])
        );

        NETWORK_QUANTUM_STORAGE_7 = Networks.getLanguageManager().getThemedStack(
            "NTW_QUANTUM_STORAGE_7",
            new ItemStack(Material.MAGENTA_TERRACOTTA),
            Themes.MACHINE,
            "network-quantum-storage-7",
            String.valueOf(NetworkQuantumStorage.getSizes()[6])
        );

        NETWORK_QUANTUM_STORAGE_8 = Networks.getLanguageManager().getThemedStack(
            "NTW_QUANTUM_STORAGE_8",
            new ItemStack(Material.RED_TERRACOTTA),
            Themes.MACHINE,
            "network-quantum-storage-8",
            String.valueOf(NetworkQuantumStorage.getSizes()[7])
        );

        NETWORK_CAPACITOR_1 = Networks.getLanguageManager().getThemedStack(
            "NTW_CAPACITOR_1",
            new ItemStack(Material.BROWN_GLAZED_TERRACOTTA),
            Themes.MACHINE,
            "network-capacitor-1",
            Theme.CLICK_INFO,
            Theme.PASSIVE,
            1000
        );

        NETWORK_CAPACITOR_2 = Networks.getLanguageManager().getThemedStack(
            "NTW_CAPACITOR_2",
            new ItemStack(Material.GREEN_GLAZED_TERRACOTTA),
            Themes.MACHINE,
            "network-capacitor-2",
            Theme.CLICK_INFO,
            Theme.PASSIVE,
            10000
        );

        NETWORK_CAPACITOR_3 = Networks.getLanguageManager().getThemedStack(
            "NTW_CAPACITOR_3",
            new ItemStack(Material.BLACK_GLAZED_TERRACOTTA),
            Themes.MACHINE,
            "network-capacitor-3",
            Theme.CLICK_INFO,
            Theme.PASSIVE,
            1000000
        );

        NETWORK_POWER_OUTLET_1 = Networks.getLanguageManager().getThemedStack(
            "NTW_POWER_OUTLET_1",
            new ItemStack(Material.YELLOW_GLAZED_TERRACOTTA),
            Themes.MACHINE,
            "network-power-outlet-1",
            Theme.CLICK_INFO,
            Theme.PASSIVE,
            500
        );

        NETWORK_POWER_OUTLET_2 = Networks.getLanguageManager().getThemedStack(
            "NTW_POWER_OUTLET_2",
            new ItemStack(Material.RED_GLAZED_TERRACOTTA),
            Themes.MACHINE,
            "network-power-outlet-2",
            Theme.CLICK_INFO,
            Theme.PASSIVE,
            2000
        );

        NETWORK_POWER_DISPLAY = Networks.getLanguageManager().getThemedStack(
            "NTW_POWER_DISPLAY",
            new ItemStack(Material.TINTED_GLASS),
            Themes.MACHINE,
            "network-power-display"
        );

        NETWORK_RECIPE_ENCODER = Networks.getLanguageManager().getThemedStack(
            "NTW_RECIPE_ENCODER",
            new ItemStack(Material.TARGET),
            Themes.MACHINE,
            "network-recipe-encoder",
            Theme.CLICK_INFO,
            Theme.PASSIVE,
            20000
        );

        NETWORK_AUTO_CRAFTER = Networks.getLanguageManager().getThemedStack(
            "NTW_AUTO_CRAFTER",
            new ItemStack(Material.BLACK_GLAZED_TERRACOTTA),
            Themes.MACHINE,
            "network-auto-crafter",
            Theme.CLICK_INFO,
            Theme.PASSIVE,
            64
        );

        NETWORK_AUTO_CRAFTER_WITHHOLDING = Networks.getLanguageManager().getThemedStack(
            "NTW_AUTO_CRAFTER_WITHHOLDING",
            new ItemStack(Material.WHITE_GLAZED_TERRACOTTA),
            Themes.MACHINE,
            "network-auto-crafter-withholding",
            Theme.CLICK_INFO,
            Theme.PASSIVE,
            128
        );

        CRAFTING_BLUEPRINT = Networks.getLanguageManager().getThemedStack(
            "NTW_CRAFTING_BLUEPRINT",
            new ItemStack(Material.BLUE_DYE),
            Themes.TOOL,
            "crafting-blueprint"
        );

        NETWORK_PROBE = Networks.getLanguageManager().getThemedStack(
            "NTW_PROBE",
            new ItemStack(Material.CLOCK),
            Themes.TOOL,
            "network-probe"
        );

        NETWORK_REMOTE = Networks.getLanguageManager().getThemedStack(
            "NTW_REMOTE",
            new ItemStack(Material.PAINTING),
            Themes.TOOL,
            "network-remote",
            Theme.CLICK_INFO,
            Theme.PASSIVE,
            NetworkRemote.getRanges()[0]
        );

        NETWORK_REMOTE_EMPOWERED = Networks.getLanguageManager().getThemedStack(
            "NTW_REMOTE_EMPOWERED",
            new ItemStack(Material.ITEM_FRAME),
            Themes.TOOL,
            "network-remote-empowered",
            Theme.CLICK_INFO,
            Theme.PASSIVE,
            NetworkRemote.getRanges()[1]
        );

        NETWORK_REMOTE_PRISTINE = Networks.getLanguageManager().getThemedStack(
            "NTW_REMOTE_PRISTINE",
            new ItemStack(Material.GLOW_ITEM_FRAME),
            Themes.TOOL,
            "network-remote-pristine",
            Theme.CLICK_INFO,
            Theme.PASSIVE
        );

        NETWORK_REMOTE_ULTIMATE = Networks.getLanguageManager().getThemedStack(
            "NTW_REMOTE_ULTIMATE",
            ItemStackGenerators.createEnchantedItemStack(
                Material.GLOW_ITEM_FRAME,
                true,
                new Pair<>(Enchantment.ARROW_DAMAGE, 1)
            ),
            Themes.TOOL,
            "network-remote-ultimate",
            Theme.CLICK_INFO,
            Theme.PASSIVE
        );

        NETWORK_CRAYON = Networks.getLanguageManager().getThemedStack(
            "NTW_CRAYON",
            new ItemStack(Material.RED_CANDLE),
            Themes.TOOL,
            "network-crayon"
        );

        NETWORK_CONFIGURATOR = Networks.getLanguageManager().getThemedStack(
            "NTW_CONFIGURATOR",
            new ItemStack(Material.BLAZE_ROD),
            Themes.TOOL,
            "network-configurator",
            Theme.CLICK_INFO,
            Theme.PASSIVE
        );

        NETWORK_WIRELESS_CONFIGURATOR = Networks.getLanguageManager().getThemedStack(
            "NTW_WIRELESS_CONFIGURATOR",
            new ItemStack(Material.BLAZE_ROD),
            Themes.TOOL,
            "network-wireless-configurator",
            Theme.CLICK_INFO,
            Theme.PASSIVE
        );

        NETWORK_RAKE_1 = Networks.getLanguageManager().getThemedStack(
            "NTW_RAKE_1",
            new ItemStack(Material.TWISTING_VINES),
            Themes.TOOL,
            "network-rake-1",
            LoreBuilder.usesLeft(250)
        );

        NETWORK_RAKE_2 = Networks.getLanguageManager().getThemedStack(
            "NTW_RAKE_2",
            new ItemStack(Material.WEEPING_VINES),
            Themes.TOOL,
            "network-rake-2",
            LoreBuilder.usesLeft(1000)
        );

        NETWORK_RAKE_3 = Networks.getLanguageManager().getThemedStack(
            "NTW_RAKE_3",
            ItemStackGenerators.createEnchantedItemStack(
                Material.WEEPING_VINES,
                true,
                new Pair<>(Enchantment.LUCK, 1)
            ),
            Themes.TOOL,
            "network-rake-3",
            LoreBuilder.usesLeft(9999)
        );
    }
}
