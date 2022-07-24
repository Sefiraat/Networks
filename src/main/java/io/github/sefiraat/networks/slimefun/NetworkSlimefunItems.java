package io.github.sefiraat.networks.slimefun;

import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.slimefun.network.NetworkAutoCrafter;
import io.github.sefiraat.networks.slimefun.network.NetworkBridge;
import io.github.sefiraat.networks.slimefun.network.NetworkCell;
import io.github.sefiraat.networks.slimefun.network.NetworkController;
import io.github.sefiraat.networks.slimefun.network.NetworkEncoder;
import io.github.sefiraat.networks.slimefun.network.NetworkExport;
import io.github.sefiraat.networks.slimefun.network.NetworkGrabber;
import io.github.sefiraat.networks.slimefun.network.NetworkGreedyBlock;
import io.github.sefiraat.networks.slimefun.network.NetworkImport;
import io.github.sefiraat.networks.slimefun.network.NetworkMonitor;
import io.github.sefiraat.networks.slimefun.network.NetworkPowerDisplay;
import io.github.sefiraat.networks.slimefun.network.NetworkPowerNode;
import io.github.sefiraat.networks.slimefun.network.NetworkPowerOutlet;
import io.github.sefiraat.networks.slimefun.network.NetworkPurger;
import io.github.sefiraat.networks.slimefun.network.NetworkPusher;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumStorage;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumWorkbench;
import io.github.sefiraat.networks.slimefun.network.NetworkVanillaGrabber;
import io.github.sefiraat.networks.slimefun.network.NetworkVanillaPusher;
import io.github.sefiraat.networks.slimefun.network.NetworkWirelessReceiver;
import io.github.sefiraat.networks.slimefun.network.NetworkWirelessTransmitter;
import io.github.sefiraat.networks.slimefun.network.grid.NetworkCraftingGrid;
import io.github.sefiraat.networks.slimefun.network.grid.NetworkGrid;
import io.github.sefiraat.networks.slimefun.tools.CraftingBlueprint;
import io.github.sefiraat.networks.slimefun.tools.NetworkConfigurator;
import io.github.sefiraat.networks.slimefun.tools.NetworkCrayon;
import io.github.sefiraat.networks.slimefun.tools.NetworkProbe;
import io.github.sefiraat.networks.slimefun.tools.NetworkRake;
import io.github.sefiraat.networks.slimefun.tools.NetworkRemote;
import io.github.sefiraat.networks.slimefun.tools.NetworkWirelessConfigurator;
import io.github.sefiraat.networks.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class NetworkSlimefunItems {

    public static final UnplaceableBlock SYNTHETIC_EMERALD_SHARD;
    public static final UnplaceableBlock OPTIC_GLASS;
    public static final UnplaceableBlock OPTIC_CABLE;
    public static final UnplaceableBlock OPTIC_STAR;
    public static final UnplaceableBlock RADIOACTIVE_OPTIC_STAR;
    public static final UnplaceableBlock SHRINKING_BASE;
    public static final UnplaceableBlock SIMPLE_NANOBOTS;
    public static final UnplaceableBlock ADVANCED_NANOBOTS;
    public static final UnplaceableBlock AI_CORE;
    public static final UnplaceableBlock EMPOWERED_AI_CORE;
    public static final UnplaceableBlock PRISTINE_AI_CORE;
    public static final UnplaceableBlock INTERDIMENSIONAL_PRESENCE;

    public static final NetworkController NETWORK_CONTROLLER;
    public static final NetworkBridge NETWORK_BRIDGE;
    public static final NetworkMonitor NETWORK_MONITOR;
    public static final NetworkImport NETWORK_IMPORT;
    public static final NetworkExport NETWORK_EXPORT;
    public static final NetworkGrabber NETWORK_GRABBER;
    public static final NetworkPusher NETWORK_PUSHER;
    public static final NetworkVanillaGrabber NETWORK_VANILLA_GRABBER;
    public static final NetworkVanillaPusher NETWORK_VANILLA_PUSHER;
    public static final NetworkWirelessTransmitter NETWORK_WIRELESS_TRANSMITTER;
    public static final NetworkWirelessReceiver NETWORK_WIRELESS_RECEIVER;
    public static final NetworkPurger NETWORK_TRASH;
    public static final NetworkGrid NETWORK_GRID;
    public static final NetworkCraftingGrid NETWORK_CRAFTING_GRID;
    public static final NetworkCell NETWORK_CELL;
    public static final NetworkGreedyBlock NETWORK_GREEDY_BLOCK;
    public static final NetworkQuantumWorkbench NETWORK_QUANTUM_WORKBENCH;
    public static final NetworkQuantumStorage NETWORK_QUANTUM_STORAGE_1;
    public static final NetworkQuantumStorage NETWORK_QUANTUM_STORAGE_2;
    public static final NetworkQuantumStorage NETWORK_QUANTUM_STORAGE_3;
    public static final NetworkQuantumStorage NETWORK_QUANTUM_STORAGE_4;
    public static final NetworkQuantumStorage NETWORK_QUANTUM_STORAGE_5;
    public static final NetworkQuantumStorage NETWORK_QUANTUM_STORAGE_6;
    public static final NetworkQuantumStorage NETWORK_QUANTUM_STORAGE_7;
    public static final NetworkQuantumStorage NETWORK_QUANTUM_STORAGE_8;
    public static final NetworkPowerNode NETWORK_CAPACITOR_1;
    public static final NetworkPowerNode NETWORK_CAPACITOR_2;
    public static final NetworkPowerNode NETWORK_CAPACITOR_3;
    public static final NetworkPowerOutlet NETWORK_POWER_OUTLET_1;
    public static final NetworkPowerOutlet NETWORK_POWER_OUTLET_2;
    public static final NetworkPowerDisplay NETWORK_POWER_DISPLAY;
    public static final NetworkEncoder NETWORK_RECIPE_ENCODER;
    public static final NetworkAutoCrafter NETWORK_AUTO_CRAFTER;
    public static final NetworkAutoCrafter NETWORK_AUTO_CRAFTER_WITHHOLDING;

    public static final CraftingBlueprint CRAFTING_BLUEPRINT;
    public static final NetworkProbe NETWORK_PROBE;
    public static final NetworkRemote NETWORK_REMOTE;
    public static final NetworkRemote NETWORK_REMOTE_EMPOWERED;
    public static final NetworkRemote NETWORK_REMOTE_PRISTINE;
    public static final NetworkRemote NETWORK_REMOTE_ULTIMATE;
    public static final NetworkCrayon NETWORK_CRAYON;
    public static final NetworkConfigurator NETWORK_CONFIGURATOR;
    public static final NetworkWirelessConfigurator NETWORK_WIRELESS_CONFIGURATOR;
    public static final NetworkRake NETWORK_RAKE_1;
    public static final NetworkRake NETWORK_RAKE_2;
    public static final NetworkRake NETWORK_RAKE_3;

    static {

        final ItemStack glass = new ItemStack(Material.GLASS);

        SYNTHETIC_EMERALD_SHARD = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.SYNTHETIC_EMERALD_SHARD,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                SlimefunItems.STONE_CHUNK, SlimefunItems.SYNTHETIC_EMERALD, null,
                SlimefunItems.SYNTHETIC_EMERALD, null, null,
                null, null, null
            },
            StackUtils.getAsQuantity(NetworksSlimefunItemStacks.SYNTHETIC_EMERALD_SHARD, 3)
        );

        OPTIC_GLASS = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.OPTIC_GLASS,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                glass, glass, glass,
                glass, SYNTHETIC_EMERALD_SHARD.getItem(), glass,
                glass, glass, glass
            },
            StackUtils.getAsQuantity(NetworksSlimefunItemStacks.OPTIC_GLASS, 8)
        );

        OPTIC_CABLE = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.OPTIC_CABLE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem(),
                SlimefunItems.COPPER_WIRE, SYNTHETIC_EMERALD_SHARD.getItem(), SlimefunItems.COPPER_WIRE,
                OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem()
            },
            StackUtils.getAsQuantity(NetworksSlimefunItemStacks.OPTIC_CABLE, 16)
        );

        OPTIC_STAR = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.OPTIC_STAR,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), new ItemStack(Material.NETHER_STAR), OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem()
            }
        );

        RADIOACTIVE_OPTIC_STAR = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.RADIOACTIVE_OPTIC_STAR,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.BLISTERING_INGOT_3,
                OPTIC_CABLE.getItem(), OPTIC_STAR.getItem(), OPTIC_CABLE.getItem(),
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.BLISTERING_INGOT_3
            }
        );

        SHRINKING_BASE = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.SHRINKING_BASE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                SlimefunItems.CORINTHIAN_BRONZE_INGOT, SlimefunItems.ANDROID_INTERFACE_ITEMS, SlimefunItems.CORINTHIAN_BRONZE_INGOT,
                OPTIC_CABLE.getItem(), RADIOACTIVE_OPTIC_STAR.getItem(), OPTIC_CABLE.getItem(),
                SlimefunItems.CORINTHIAN_BRONZE_INGOT, SlimefunItems.ANDROID_MEMORY_CORE, SlimefunItems.CORINTHIAN_BRONZE_INGOT
            }
        );

        SIMPLE_NANOBOTS = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.SIMPLE_NANOBOTS,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                SHRINKING_BASE.getItem(), SlimefunItems.PROGRAMMABLE_ANDROID
            },
            StackUtils.getAsQuantity(NetworksSlimefunItemStacks.SIMPLE_NANOBOTS, 4)
        );

        ADVANCED_NANOBOTS = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.ADVANCED_NANOBOTS,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                SHRINKING_BASE.getItem(), SlimefunItems.PROGRAMMABLE_ANDROID_3
            },
            StackUtils.getAsQuantity(NetworksSlimefunItemStacks.ADVANCED_NANOBOTS, 4)
        );

        AI_CORE = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.AI_CORE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                SlimefunItems.BASIC_CIRCUIT_BOARD, SlimefunItems.ANDROID_MEMORY_CORE, SlimefunItems.BASIC_CIRCUIT_BOARD,
                SlimefunItems.BASIC_CIRCUIT_BOARD, ADVANCED_NANOBOTS.getItem(), SlimefunItems.BASIC_CIRCUIT_BOARD,
                SlimefunItems.BASIC_CIRCUIT_BOARD, SlimefunItems.ANDROID_MEMORY_CORE, SlimefunItems.BASIC_CIRCUIT_BOARD
            }
        );

        EMPOWERED_AI_CORE = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.EMPOWERED_AI_CORE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, RADIOACTIVE_OPTIC_STAR.getItem(), SlimefunItems.ADVANCED_CIRCUIT_BOARD,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, AI_CORE.getItem(), SlimefunItems.ADVANCED_CIRCUIT_BOARD,
                SlimefunItems.ADVANCED_CIRCUIT_BOARD, RADIOACTIVE_OPTIC_STAR.getItem(), SlimefunItems.ADVANCED_CIRCUIT_BOARD
            }
        );

        PRISTINE_AI_CORE = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.PRISTINE_AI_CORE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                SlimefunItems.NEPTUNIUM, SlimefunItems.DAMASCUS_STEEL_MULTI_TOOL, SlimefunItems.NEPTUNIUM,
                SlimefunItems.NEPTUNIUM, EMPOWERED_AI_CORE.getItem(), SlimefunItems.NEPTUNIUM,
                SlimefunItems.NEPTUNIUM, SlimefunItems.ELECTRIFIED_CRUCIBLE_3, SlimefunItems.NEPTUNIUM
            }
        );

        INTERDIMENSIONAL_PRESENCE = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.INTERDIMENSIONAL_PRESENCE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                SlimefunItems.PLUTONIUM, SlimefunItems.REINFORCED_ALLOY_MULTI_TOOL, SlimefunItems.PLUTONIUM,
                SlimefunItems.PLUTONIUM, PRISTINE_AI_CORE.getItem(), SlimefunItems.PLUTONIUM,
                SlimefunItems.PLUTONIUM, SlimefunItems.NETHER_STAR_REACTOR, SlimefunItems.PLUTONIUM
            }
        );

        NETWORK_CONTROLLER = new NetworkController(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_CONTROLLER,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), SlimefunItems.CARGO_MANAGER, OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
            }
        );

        NETWORK_BRIDGE = new NetworkBridge(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_BRIDGE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), SlimefunItems.CARGO_CONNECTOR_NODE, OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
            },
            StackUtils.getAsQuantity(NetworksSlimefunItemStacks.NETWORK_BRIDGE, 8)
        );

        NETWORK_MONITOR = new NetworkMonitor(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_MONITOR,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(),
                OPTIC_CABLE.getItem(), SlimefunItems.CARGO_MOTOR, OPTIC_CABLE.getItem(),
                NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(),
            }
        );

        NETWORK_IMPORT = new NetworkImport(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_IMPORT,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(),
                OPTIC_CABLE.getItem(), SlimefunItems.CARGO_INPUT_NODE, OPTIC_CABLE.getItem(),
                NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(),
            }
        );

        NETWORK_EXPORT = new NetworkExport(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_EXPORT,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(),
                OPTIC_CABLE.getItem(), SlimefunItems.CARGO_OUTPUT_NODE_2, OPTIC_CABLE.getItem(),
                NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(),
            }
        );

        NETWORK_GRABBER = new NetworkGrabber(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_GRABBER,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), NETWORK_IMPORT.getItem(), OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
            }
        );

        NETWORK_PUSHER = new NetworkPusher(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_PUSHER,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), NETWORK_EXPORT.getItem(), OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
            }
        );

        NETWORK_VANILLA_GRABBER = new NetworkVanillaGrabber(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_VANILLA_GRABBER,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
                new ItemStack(Material.HOPPER), NETWORK_GRABBER.getItem(), new ItemStack(Material.HOPPER),
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
            }
        );

        NETWORK_VANILLA_PUSHER = new NetworkVanillaPusher(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_VANILLA_PUSHER,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), new ItemStack(Material.HOPPER), OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), NETWORK_PUSHER.getItem(), OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), new ItemStack(Material.HOPPER), OPTIC_GLASS.getItem(),
            }
        );

        NETWORK_WIRELESS_TRANSMITTER = new NetworkWirelessTransmitter(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_WIRELESS_TRANSMITTER,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), ADVANCED_NANOBOTS.getItem(), OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), NETWORK_PUSHER.getItem(), OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), PRISTINE_AI_CORE.getItem(), OPTIC_GLASS.getItem(),
            }
        );

        NETWORK_WIRELESS_RECEIVER = new NetworkWirelessReceiver(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_WIRELESS_RECEIVER,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SIMPLE_NANOBOTS.getItem(), OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), NETWORK_GRABBER.getItem(), OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), AI_CORE.getItem(), OPTIC_GLASS.getItem(),
            }
        );

        NETWORK_TRASH = new NetworkPurger(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_PURGER,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), SlimefunItems.TRASH_CAN, OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
            }
        );

        NETWORK_GRID = new NetworkGrid(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_GRID,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(),
                OPTIC_CABLE.getItem(), new ItemStack(Material.NETHER_STAR), OPTIC_CABLE.getItem(),
                NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(),
            }
        );

        NETWORK_CRAFTING_GRID = new NetworkCraftingGrid(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_CRAFTING_GRID,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_STAR.getItem(), OPTIC_STAR.getItem(), OPTIC_STAR.getItem(),
                OPTIC_STAR.getItem(), NETWORK_GRID.getItem(), OPTIC_STAR.getItem(),
                OPTIC_STAR.getItem(), OPTIC_STAR.getItem(), OPTIC_STAR.getItem(),
            }
        );

        NETWORK_CELL = new NetworkCell(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_CELL,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(),
                OPTIC_CABLE.getItem(), new ItemStack(Material.CHEST), OPTIC_CABLE.getItem(),
                NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(),
            }
        );

        NETWORK_GREEDY_BLOCK = new NetworkGreedyBlock(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_GREEDY_BLOCK,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(),
                OPTIC_CABLE.getItem(), NETWORK_CELL.getItem(), OPTIC_CABLE.getItem(),
                NETWORK_BRIDGE.getItem(), SIMPLE_NANOBOTS.getItem(), NETWORK_BRIDGE.getItem(),
            }
        );

        NETWORK_QUANTUM_WORKBENCH = new NetworkQuantumWorkbench(
            NetworksItemGroups.NETWORK_QUANTUMS,
            NetworksSlimefunItemStacks.NETWORK_QUANTUM_WORKBENCH,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.ADVANCED_CIRCUIT_BOARD, OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), SlimefunItems.ADVANCED_CIRCUIT_BOARD, OPTIC_GLASS.getItem()
            }
        );

        NETWORK_QUANTUM_STORAGE_1 = new NetworkQuantumStorage(
            NetworksItemGroups.NETWORK_QUANTUMS,
            NetworksSlimefunItemStacks.NETWORK_QUANTUM_STORAGE_1,
            NetworkQuantumWorkbench.TYPE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), SlimefunItems.CARGO_MOTOR, OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem()
            },
            NetworkQuantumStorage.getSizes()[0]
        );

        NETWORK_QUANTUM_STORAGE_2 = new NetworkQuantumStorage(
            NetworksItemGroups.NETWORK_QUANTUMS,
            NetworksSlimefunItemStacks.NETWORK_QUANTUM_STORAGE_2,
            NetworkQuantumWorkbench.TYPE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.ALUMINUM_BRASS_INGOT, OPTIC_GLASS.getItem(),
                SlimefunItems.SYNTHETIC_SAPPHIRE, NETWORK_QUANTUM_STORAGE_1.getItem(), SlimefunItems.SYNTHETIC_SAPPHIRE,
                OPTIC_GLASS.getItem(), SlimefunItems.ALUMINUM_BRASS_INGOT, OPTIC_GLASS.getItem()
            },
            NetworkQuantumStorage.getSizes()[1]
        );

        NETWORK_QUANTUM_STORAGE_3 = new NetworkQuantumStorage(
            NetworksItemGroups.NETWORK_QUANTUMS,
            NetworksSlimefunItemStacks.NETWORK_QUANTUM_STORAGE_3,
            NetworkQuantumWorkbench.TYPE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.CORINTHIAN_BRONZE_INGOT, OPTIC_GLASS.getItem(),
                SlimefunItems.SYNTHETIC_DIAMOND, NETWORK_QUANTUM_STORAGE_2.getItem(), SlimefunItems.SYNTHETIC_DIAMOND,
                OPTIC_GLASS.getItem(), SlimefunItems.CORINTHIAN_BRONZE_INGOT, OPTIC_GLASS.getItem()
            },
            NetworkQuantumStorage.getSizes()[2]
        );

        NETWORK_QUANTUM_STORAGE_4 = new NetworkQuantumStorage(
            NetworksItemGroups.NETWORK_QUANTUMS,
            NetworksSlimefunItemStacks.NETWORK_QUANTUM_STORAGE_4,
            NetworkQuantumWorkbench.TYPE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.HARDENED_METAL_INGOT, OPTIC_GLASS.getItem(),
                SlimefunItems.SYNTHETIC_EMERALD, NETWORK_QUANTUM_STORAGE_3.getItem(), SlimefunItems.SYNTHETIC_EMERALD,
                OPTIC_GLASS.getItem(), SlimefunItems.HARDENED_METAL_INGOT, OPTIC_GLASS.getItem()
            },
            NetworkQuantumStorage.getSizes()[3]
        );

        NETWORK_QUANTUM_STORAGE_5 = new NetworkQuantumStorage(
            NetworksItemGroups.NETWORK_QUANTUMS,
            NetworksSlimefunItemStacks.NETWORK_QUANTUM_STORAGE_5,
            NetworkQuantumWorkbench.TYPE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.REINFORCED_ALLOY_INGOT, OPTIC_GLASS.getItem(),
                SlimefunItems.POWER_CRYSTAL, NETWORK_QUANTUM_STORAGE_4.getItem(), SlimefunItems.POWER_CRYSTAL,
                OPTIC_GLASS.getItem(), SlimefunItems.REINFORCED_ALLOY_INGOT, OPTIC_GLASS.getItem()
            },
            NetworkQuantumStorage.getSizes()[4]
        );

        NETWORK_QUANTUM_STORAGE_6 = new NetworkQuantumStorage(
            NetworksItemGroups.NETWORK_QUANTUMS,
            NetworksSlimefunItemStacks.NETWORK_QUANTUM_STORAGE_6,
            NetworkQuantumWorkbench.TYPE,
            new ItemStack[]{
                SlimefunItems.STEEL_PLATE, SlimefunItems.BLISTERING_INGOT, SlimefunItems.STEEL_PLATE,
                SlimefunItems.CARGO_MOTOR, NETWORK_QUANTUM_STORAGE_5.getItem(), SlimefunItems.CARGO_MOTOR,
                SlimefunItems.STEEL_PLATE, SlimefunItems.BLISTERING_INGOT, SlimefunItems.STEEL_PLATE
            },
            NetworkQuantumStorage.getSizes()[5]
        );

        NETWORK_QUANTUM_STORAGE_7 = new NetworkQuantumStorage(
            NetworksItemGroups.NETWORK_QUANTUMS,
            NetworksSlimefunItemStacks.NETWORK_QUANTUM_STORAGE_7,
            NetworkQuantumWorkbench.TYPE,
            new ItemStack[]{
                SlimefunItems.REINFORCED_PLATE, SlimefunItems.BLISTERING_INGOT_2, SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.CARGO_CONNECTOR_NODE, NETWORK_QUANTUM_STORAGE_6.getItem(), SlimefunItems.CARGO_CONNECTOR_NODE,
                SlimefunItems.REINFORCED_PLATE, SlimefunItems.BLISTERING_INGOT_2, SlimefunItems.REINFORCED_PLATE
            },
            NetworkQuantumStorage.getSizes()[6]
        );

        NETWORK_QUANTUM_STORAGE_8 = new NetworkQuantumStorage(
            NetworksItemGroups.NETWORK_QUANTUMS,
            NetworksSlimefunItemStacks.NETWORK_QUANTUM_STORAGE_8,
            NetworkQuantumWorkbench.TYPE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.BLISTERING_INGOT_3, OPTIC_GLASS.getItem(),
                SlimefunItems.CARGO_MANAGER, NETWORK_QUANTUM_STORAGE_7.getItem(), SlimefunItems.CARGO_MANAGER,
                OPTIC_GLASS.getItem(), SlimefunItems.BLISTERING_INGOT_3, OPTIC_GLASS.getItem()
            },
            NetworkQuantumStorage.getSizes()[7]
        );

        NETWORK_CAPACITOR_1 = new NetworkPowerNode(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_CAPACITOR_1,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), SlimefunItems.CARBONADO_EDGED_CAPACITOR, OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
            },
            1000
        );

        NETWORK_CAPACITOR_2 = new NetworkPowerNode(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_CAPACITOR_2,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_CAPACITOR_1.getItem(), NETWORK_CAPACITOR_1.getItem(), NETWORK_CAPACITOR_1.getItem(),
                NETWORK_CAPACITOR_1.getItem(), SlimefunItems.ENERGIZED_CAPACITOR, NETWORK_CAPACITOR_1.getItem(),
                NETWORK_CAPACITOR_1.getItem(), NETWORK_CAPACITOR_1.getItem(), NETWORK_CAPACITOR_1.getItem(),
            },
            10000
        );

        NETWORK_CAPACITOR_3 = new NetworkPowerNode(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_CAPACITOR_3,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_CAPACITOR_2.getItem(), NETWORK_CAPACITOR_2.getItem(), NETWORK_CAPACITOR_2.getItem(),
                NETWORK_CAPACITOR_2.getItem(), SlimefunItems.ENERGIZED_CAPACITOR, NETWORK_CAPACITOR_2.getItem(),
                NETWORK_CAPACITOR_2.getItem(), NETWORK_CAPACITOR_2.getItem(), NETWORK_CAPACITOR_2.getItem(),
            },
            1000000
        );

        NETWORK_POWER_OUTLET_1 = new NetworkPowerOutlet(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_POWER_OUTLET_1,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem(),
                OPTIC_GLASS.getItem(), SlimefunItems.ENERGY_CONNECTOR, OPTIC_GLASS.getItem(),
                OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem(),
            },
            500
        );

        NETWORK_POWER_OUTLET_2 = new NetworkPowerOutlet(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_POWER_OUTLET_2,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_POWER_OUTLET_1.getItem(), OPTIC_GLASS.getItem(), NETWORK_POWER_OUTLET_1.getItem(),
                OPTIC_GLASS.getItem(), SlimefunItems.ENERGY_CONNECTOR, OPTIC_GLASS.getItem(),
                NETWORK_POWER_OUTLET_1.getItem(), OPTIC_GLASS.getItem(), NETWORK_POWER_OUTLET_1.getItem(),
            },
            2000
        );

        NETWORK_POWER_DISPLAY = new NetworkPowerDisplay(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_POWER_DISPLAY,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.ENERGY_REGULATOR, OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), NETWORK_CAPACITOR_1.getItem(), OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), SlimefunItems.ENERGY_CONNECTOR, OPTIC_GLASS.getItem(),
            }
        );

        NETWORK_RECIPE_ENCODER = new NetworkEncoder(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_RECIPE_ENCODER,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                SlimefunItems.BASIC_CIRCUIT_BOARD, SlimefunItems.ANDROID_MEMORY_CORE, SlimefunItems.BASIC_CIRCUIT_BOARD,
                SlimefunItems.BASIC_CIRCUIT_BOARD, SlimefunItems.ENHANCED_AUTO_CRAFTER, SlimefunItems.BASIC_CIRCUIT_BOARD,
                SlimefunItems.BASIC_CIRCUIT_BOARD, SlimefunItems.CARGO_MOTOR, SlimefunItems.BASIC_CIRCUIT_BOARD
            }
        );

        NETWORK_AUTO_CRAFTER = new NetworkAutoCrafter(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_AUTO_CRAFTER,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), SIMPLE_NANOBOTS.getItem(), OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), SlimefunItems.ENHANCED_AUTO_CRAFTER, OPTIC_GLASS.getItem(),
            },
            64,
            false
        );

        NETWORK_AUTO_CRAFTER_WITHHOLDING = new NetworkAutoCrafter(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_AUTO_CRAFTER_WITHHOLDING,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_CABLE.getItem(), OPTIC_GLASS.getItem(),
                OPTIC_CABLE.getItem(), ADVANCED_NANOBOTS.getItem(), OPTIC_CABLE.getItem(),
                OPTIC_GLASS.getItem(), NETWORK_AUTO_CRAFTER.getItem(), OPTIC_GLASS.getItem(),
            },
            128,
            true
        );

        CRAFTING_BLUEPRINT = new CraftingBlueprint(
            NetworksItemGroups.TOOLS,
            NetworksSlimefunItemStacks.CRAFTING_BLUEPRINT,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_CABLE.getItem(), OPTIC_CABLE.getItem(), OPTIC_CABLE.getItem(),
                OPTIC_CABLE.getItem(), new ItemStack(Material.PAPER), OPTIC_CABLE.getItem(),
                OPTIC_CABLE.getItem(), OPTIC_CABLE.getItem(), OPTIC_CABLE.getItem()
            }
        );

        NETWORK_PROBE = new NetworkProbe(
            NetworksItemGroups.TOOLS,
            NetworksSlimefunItemStacks.NETWORK_PROBE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                null, SlimefunItems.DURALUMIN_INGOT, null,
                null, OPTIC_CABLE.getItem(), null,
                null, NETWORK_BRIDGE.getItem(), null
            }
        );

        NETWORK_REMOTE = new NetworkRemote(
            NetworksItemGroups.TOOLS,
            NetworksSlimefunItemStacks.NETWORK_REMOTE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                null, NETWORK_GRID.getItem(), null,
                null, AI_CORE.getItem(), null,
                null, OPTIC_STAR.getItem(), null
            },
            NetworkRemote.getRanges()[0]
        );

        NETWORK_REMOTE_EMPOWERED = new NetworkRemote(
            NetworksItemGroups.TOOLS,
            NetworksSlimefunItemStacks.NETWORK_REMOTE_EMPOWERED,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                null, NETWORK_REMOTE.getItem(), null,
                null, EMPOWERED_AI_CORE.getItem(), null,
                null, NETWORK_REMOTE.getItem(), null
            },
            NetworkRemote.getRanges()[1]
        );

        NETWORK_REMOTE_PRISTINE = new NetworkRemote(
            NetworksItemGroups.TOOLS,
            NetworksSlimefunItemStacks.NETWORK_REMOTE_PRISTINE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                null, NETWORK_REMOTE_EMPOWERED.getItem(), null,
                null, PRISTINE_AI_CORE.getItem(), null,
                null, NETWORK_REMOTE_EMPOWERED.getItem(), null
            },
            NetworkRemote.getRanges()[2]
        );

        NETWORK_REMOTE_ULTIMATE = new NetworkRemote(
            NetworksItemGroups.TOOLS,
            NetworksSlimefunItemStacks.NETWORK_REMOTE_ULTIMATE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                null, NETWORK_REMOTE_PRISTINE.getItem(), null,
                null, INTERDIMENSIONAL_PRESENCE.getItem(), null,
                null, NETWORK_REMOTE_PRISTINE.getItem(), null
            },
            NetworkRemote.getRanges()[3]
        );

        NETWORK_CRAYON = new NetworkCrayon(
            NetworksItemGroups.TOOLS,
            NetworksSlimefunItemStacks.NETWORK_CRAYON,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                null, OPTIC_CABLE.getItem(), null,
                null, new ItemStack(Material.HONEYCOMB), null,
                null, new ItemStack(Material.HONEYCOMB), null
            }
        );

        NETWORK_CONFIGURATOR = new NetworkConfigurator(
            NetworksItemGroups.TOOLS,
            NetworksSlimefunItemStacks.NETWORK_CONFIGURATOR,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                null, RADIOACTIVE_OPTIC_STAR.getItem(), null,
                null, NETWORK_CRAYON.getItem(), null,
                null, AI_CORE.getItem(), null
            }
        );

        NETWORK_WIRELESS_CONFIGURATOR = new NetworkWirelessConfigurator(
            NetworksItemGroups.TOOLS,
            NetworksSlimefunItemStacks.NETWORK_WIRELESS_CONFIGURATOR,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                null, RADIOACTIVE_OPTIC_STAR.getItem(), null,
                null, NETWORK_CONFIGURATOR.getItem(), null,
                null, INTERDIMENSIONAL_PRESENCE.getItem(), null
            }
        );

        NETWORK_RAKE_1 = new NetworkRake(
            NetworksItemGroups.TOOLS,
            NetworksSlimefunItemStacks.NETWORK_RAKE_1,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                null, RADIOACTIVE_OPTIC_STAR.getItem(), null,
                null, new ItemStack(Material.DIAMOND_SWORD), null,
                null, SYNTHETIC_EMERALD_SHARD.getItem(), null
            },
            250
        );

        NETWORK_RAKE_2 = new NetworkRake(
            NetworksItemGroups.TOOLS,
            NetworksSlimefunItemStacks.NETWORK_RAKE_2,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                null, RADIOACTIVE_OPTIC_STAR.getItem(), null,
                null, NETWORK_RAKE_1.getItem(), null,
                null, AI_CORE.getItem(), null
            },
            1000
        );

        NETWORK_RAKE_3 = new NetworkRake(
            NetworksItemGroups.TOOLS,
            NetworksSlimefunItemStacks.NETWORK_RAKE_3,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                null, RADIOACTIVE_OPTIC_STAR.getItem(), null,
                null, NETWORK_RAKE_2.getItem(), null,
                null, EMPOWERED_AI_CORE.getItem(), null
            },
            9999
        );
    }

    public static void setup() {
        Networks plugin = Networks.getInstance();

        SYNTHETIC_EMERALD_SHARD.register(plugin);
        OPTIC_GLASS.register(plugin);
        OPTIC_CABLE.register(plugin);
        OPTIC_STAR.register(plugin);
        RADIOACTIVE_OPTIC_STAR.register(plugin);
        SHRINKING_BASE.register(plugin);
        SIMPLE_NANOBOTS.register(plugin);
        ADVANCED_NANOBOTS.register(plugin);
        AI_CORE.register(plugin);
        EMPOWERED_AI_CORE.register(plugin);
        PRISTINE_AI_CORE.register(plugin);
        INTERDIMENSIONAL_PRESENCE.register(plugin);

        NETWORK_CONTROLLER.register(plugin);
        NETWORK_BRIDGE.register(plugin);
        NETWORK_MONITOR.register(plugin);
        NETWORK_IMPORT.register(plugin);
        NETWORK_EXPORT.register(plugin);
        NETWORK_GRABBER.register(plugin);
        NETWORK_PUSHER.register(plugin);
        NETWORK_VANILLA_GRABBER.register(plugin);
        NETWORK_VANILLA_PUSHER.register(plugin);
        NETWORK_WIRELESS_TRANSMITTER.register(plugin);
        NETWORK_WIRELESS_RECEIVER.register(plugin);
        NETWORK_TRASH.register(plugin);
        NETWORK_GRID.register(plugin);
        NETWORK_CRAFTING_GRID.register(plugin);
        NETWORK_CELL.register(plugin);
        NETWORK_GREEDY_BLOCK.register(plugin);
        NETWORK_QUANTUM_WORKBENCH.register(plugin);
        NETWORK_QUANTUM_STORAGE_1.register(plugin);
        NETWORK_QUANTUM_STORAGE_2.register(plugin);
        NETWORK_QUANTUM_STORAGE_3.register(plugin);
        NETWORK_QUANTUM_STORAGE_4.register(plugin);
        NETWORK_QUANTUM_STORAGE_5.register(plugin);
        NETWORK_QUANTUM_STORAGE_6.register(plugin);
        NETWORK_QUANTUM_STORAGE_7.register(plugin);
        NETWORK_QUANTUM_STORAGE_8.register(plugin);
        NETWORK_CAPACITOR_1.register(plugin);
        NETWORK_CAPACITOR_2.register(plugin);
        NETWORK_CAPACITOR_3.register(plugin);
        NETWORK_POWER_OUTLET_1.register(plugin);
        NETWORK_POWER_OUTLET_2.register(plugin);
        NETWORK_POWER_DISPLAY.register(plugin);
        NETWORK_RECIPE_ENCODER.register(plugin);
        NETWORK_AUTO_CRAFTER.register(plugin);
        NETWORK_AUTO_CRAFTER_WITHHOLDING.register(plugin);

        CRAFTING_BLUEPRINT.register(plugin);
        NETWORK_PROBE.register(plugin);
        NETWORK_REMOTE.register(plugin);
        NETWORK_REMOTE_EMPOWERED.register(plugin);
        NETWORK_REMOTE_PRISTINE.register(plugin);
        NETWORK_REMOTE_ULTIMATE.register(plugin);
        NETWORK_CRAYON.register(plugin);
        NETWORK_CONFIGURATOR.register(plugin);
        NETWORK_WIRELESS_CONFIGURATOR.register(plugin);
        NETWORK_RAKE_1.register(plugin);
        NETWORK_RAKE_2.register(plugin);
        NETWORK_RAKE_3.register(plugin);
    }
}
