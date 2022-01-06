package io.github.sefiraat.networks.slimefun;

import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.slimefun.network.NetworkBridge;
import io.github.sefiraat.networks.slimefun.network.NetworkCell;
import io.github.sefiraat.networks.slimefun.network.NetworkController;
import io.github.sefiraat.networks.slimefun.network.NetworkExport;
import io.github.sefiraat.networks.slimefun.network.NetworkImport;
import io.github.sefiraat.networks.slimefun.network.NetworkMemoryShell;
import io.github.sefiraat.networks.slimefun.network.NetworkMemoryWiper;
import io.github.sefiraat.networks.slimefun.network.NetworkMonitor;
import io.github.sefiraat.networks.slimefun.network.grid.NetworkCraftingGrid;
import io.github.sefiraat.networks.slimefun.network.grid.NetworkGrid;
import io.github.sefiraat.networks.slimefun.tools.NetworkCard;
import io.github.sefiraat.networks.slimefun.tools.NetworkProbe;
import io.github.sefiraat.networks.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class NetworkSlimefunItems {

    public static final UnplaceableBlock OPTIC_GLASS;
    public static final UnplaceableBlock OPTIC_CABLE;
    public static final UnplaceableBlock OPTIC_STAR;
    public static final UnplaceableBlock RADIOACTIVE_OPTIC_STAR;

    public static final NetworkController NETWORK_CONTROLLER;
    public static final NetworkBridge NETWORK_BRIDGE;
    public static final NetworkMonitor NETWORK_MONITOR;
    public static final NetworkImport NETWORK_IMPORT;
    public static final NetworkExport NETWORK_EXPORT;
    public static final NetworkGrid NETWORK_GRID;
    public static final NetworkCraftingGrid NETWORK_CRAFTING_GRID;
    public static final NetworkCell NETWORK_CELL;
    public static final NetworkMemoryShell NETWORK_MEMORY_SHELL;
    public static final NetworkMemoryWiper NETWORK_MEMORY_WIPER_1;
    public static final NetworkMemoryWiper NETWORK_MEMORY_WIPER_2;
    public static final NetworkMemoryWiper NETWORK_MEMORY_WIPER_3;
    public static final NetworkMemoryWiper NETWORK_MEMORY_WIPER_4;

    public static final NetworkCard NETWORK_MEMORY_CARD_1;
    public static final NetworkCard NETWORK_MEMORY_CARD_2;
    public static final NetworkCard NETWORK_MEMORY_CARD_3;
    public static final NetworkCard NETWORK_MEMORY_CARD_4;
    public static final NetworkCard NETWORK_MEMORY_CARD_5;
    public static final NetworkCard NETWORK_MEMORY_CARD_6;
    public static final NetworkCard NETWORK_MEMORY_CARD_7;
    public static final NetworkCard NETWORK_MEMORY_CARD_8;
    public static final NetworkProbe NETWORK_PROBE;


    static {

        final ItemStack glass = new ItemStack(Material.GLASS);

        OPTIC_GLASS = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.OPTIC_GLASS,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                glass, SlimefunItems.SYNTHETIC_EMERALD, glass,
                SlimefunItems.SYNTHETIC_EMERALD, glass, SlimefunItems.SYNTHETIC_EMERALD,
                glass, SlimefunItems.SYNTHETIC_EMERALD, glass
            },
            StackUtils.getAsQuantity(NetworksSlimefunItemStacks.OPTIC_GLASS, 5)
        );

        OPTIC_CABLE = new UnplaceableBlock(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.OPTIC_CABLE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem(),
                SlimefunItems.COPPER_WIRE, SlimefunItems.COPPER_WIRE, SlimefunItems.COPPER_WIRE,
                OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem(), OPTIC_GLASS.getItem()
            },
            StackUtils.getAsQuantity(NetworksSlimefunItemStacks.OPTIC_CABLE, 6)
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
            }
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

        NETWORK_MEMORY_SHELL = new NetworkMemoryShell(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_MEMORY_SHELL,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(),
                OPTIC_CABLE.getItem(), NETWORK_CELL.getItem(), OPTIC_CABLE.getItem(),
                NETWORK_BRIDGE.getItem(), OPTIC_CABLE.getItem(), NETWORK_BRIDGE.getItem(),
            }
        );

        NETWORK_MEMORY_WIPER_1 = new NetworkMemoryWiper(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_MEMORY_WIPER_1,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_BRIDGE.getItem(), OPTIC_STAR.getItem(), NETWORK_BRIDGE.getItem(),
                OPTIC_CABLE.getItem(), NETWORK_MEMORY_SHELL.getItem(), OPTIC_CABLE.getItem(),
                NETWORK_BRIDGE.getItem(), OPTIC_STAR.getItem(), NETWORK_BRIDGE.getItem(),
            },
            0
        );

        NETWORK_MEMORY_WIPER_2 = new NetworkMemoryWiper(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_MEMORY_WIPER_2,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_MEMORY_WIPER_1.getItem(), NETWORK_MEMORY_WIPER_1.getItem(), NETWORK_MEMORY_WIPER_1.getItem(),
                NETWORK_MEMORY_WIPER_1.getItem(), NETWORK_MEMORY_WIPER_1.getItem(), NETWORK_MEMORY_WIPER_1.getItem(),
                NETWORK_MEMORY_WIPER_1.getItem(), NETWORK_MEMORY_WIPER_1.getItem(), NETWORK_MEMORY_WIPER_1.getItem(),
            },
            1
        );

        NETWORK_MEMORY_WIPER_3 = new NetworkMemoryWiper(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_MEMORY_WIPER_3,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_MEMORY_WIPER_2.getItem(), NETWORK_MEMORY_WIPER_2.getItem(), NETWORK_MEMORY_WIPER_2.getItem(),
                NETWORK_MEMORY_WIPER_2.getItem(), NETWORK_MEMORY_WIPER_2.getItem(), NETWORK_MEMORY_WIPER_2.getItem(),
                NETWORK_MEMORY_WIPER_2.getItem(), NETWORK_MEMORY_WIPER_2.getItem(), NETWORK_MEMORY_WIPER_2.getItem(),
            },
            2
        );

        NETWORK_MEMORY_WIPER_4 = new NetworkMemoryWiper(
            NetworksItemGroups.NETWORK_ITEMS,
            NetworksSlimefunItemStacks.NETWORK_MEMORY_WIPER_4,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                NETWORK_MEMORY_WIPER_3.getItem(), NETWORK_MEMORY_WIPER_3.getItem(), NETWORK_MEMORY_WIPER_3.getItem(),
                NETWORK_MEMORY_WIPER_3.getItem(), NETWORK_MEMORY_WIPER_3.getItem(), NETWORK_MEMORY_WIPER_3.getItem(),
                NETWORK_MEMORY_WIPER_3.getItem(), NETWORK_MEMORY_WIPER_3.getItem(), NETWORK_MEMORY_WIPER_3.getItem(),
            },
            3
        );

        NETWORK_MEMORY_CARD_1 = new NetworkCard(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.NETWORK_MEMORY_CARD_1,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                SlimefunItems.BRASS_INGOT, SlimefunItems.BRASS_INGOT, SlimefunItems.BRASS_INGOT,
                OPTIC_CABLE.getItem(), SlimefunItems.CARGO_MOTOR, OPTIC_CABLE.getItem(),
                SlimefunItems.BRASS_INGOT, SlimefunItems.BRASS_INGOT, SlimefunItems.BRASS_INGOT
            },
            NetworkCard.SIZES[0]
        );

        NETWORK_MEMORY_CARD_2 = new NetworkCard(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.NETWORK_MEMORY_CARD_2,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.ALUMINUM_BRASS_INGOT, OPTIC_GLASS.getItem(),
                NETWORK_MEMORY_CARD_1.getItem(), NETWORK_MEMORY_CARD_1.getItem(), NETWORK_MEMORY_CARD_1.getItem(),
                OPTIC_GLASS.getItem(), SlimefunItems.ALUMINUM_BRASS_INGOT, OPTIC_GLASS.getItem()
            },
            NetworkCard.SIZES[1]
        );

        NETWORK_MEMORY_CARD_3 = new NetworkCard(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.NETWORK_MEMORY_CARD_3,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.CORINTHIAN_BRONZE_INGOT, OPTIC_GLASS.getItem(),
                NETWORK_MEMORY_CARD_2.getItem(), NETWORK_MEMORY_CARD_2.getItem(), NETWORK_MEMORY_CARD_2.getItem(),
                OPTIC_GLASS.getItem(), SlimefunItems.CORINTHIAN_BRONZE_INGOT, OPTIC_GLASS.getItem()
            },
            NetworkCard.SIZES[2]
        );

        NETWORK_MEMORY_CARD_4 = new NetworkCard(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.NETWORK_MEMORY_CARD_4,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.HARDENED_METAL_INGOT, OPTIC_GLASS.getItem(),
                NETWORK_MEMORY_CARD_3.getItem(), NETWORK_MEMORY_CARD_3.getItem(), NETWORK_MEMORY_CARD_3.getItem(),
                OPTIC_GLASS.getItem(), SlimefunItems.HARDENED_METAL_INGOT, OPTIC_GLASS.getItem()
            },
            NetworkCard.SIZES[3]
        );

        NETWORK_MEMORY_CARD_5 = new NetworkCard(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.NETWORK_MEMORY_CARD_5,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.REINFORCED_ALLOY_INGOT, OPTIC_GLASS.getItem(),
                NETWORK_MEMORY_CARD_4.getItem(), NETWORK_MEMORY_CARD_4.getItem(), NETWORK_MEMORY_CARD_4.getItem(),
                OPTIC_GLASS.getItem(), SlimefunItems.REINFORCED_ALLOY_INGOT, OPTIC_GLASS.getItem()
            },
            NetworkCard.SIZES[4]
        );

        NETWORK_MEMORY_CARD_6 = new NetworkCard(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.NETWORK_MEMORY_CARD_6,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.BLISTERING_INGOT, OPTIC_GLASS.getItem(),
                NETWORK_MEMORY_CARD_5.getItem(), NETWORK_MEMORY_CARD_5.getItem(), NETWORK_MEMORY_CARD_5.getItem(),
                OPTIC_GLASS.getItem(), SlimefunItems.BLISTERING_INGOT, OPTIC_GLASS.getItem()
            },
            NetworkCard.SIZES[5]
        );

        NETWORK_MEMORY_CARD_7 = new NetworkCard(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.NETWORK_MEMORY_CARD_7,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.BLISTERING_INGOT_2, OPTIC_GLASS.getItem(),
                NETWORK_MEMORY_CARD_6.getItem(), NETWORK_MEMORY_CARD_6.getItem(), NETWORK_MEMORY_CARD_6.getItem(),
                OPTIC_GLASS.getItem(), SlimefunItems.BLISTERING_INGOT_2, OPTIC_GLASS.getItem()
            },
            NetworkCard.SIZES[6]
        );

        NETWORK_MEMORY_CARD_8 = new NetworkCard(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.NETWORK_MEMORY_CARD_8,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                OPTIC_GLASS.getItem(), SlimefunItems.BLISTERING_INGOT_3, OPTIC_GLASS.getItem(),
                NETWORK_MEMORY_CARD_7.getItem(), NETWORK_MEMORY_CARD_7.getItem(), NETWORK_MEMORY_CARD_7.getItem(),
                OPTIC_GLASS.getItem(), SlimefunItems.BLISTERING_INGOT_3, OPTIC_GLASS.getItem()
            },
            NetworkCard.SIZES[7]
        );

        NETWORK_PROBE = new NetworkProbe(
            NetworksItemGroups.MATERIALS,
            NetworksSlimefunItemStacks.NETWORK_PROBE,
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                null, SlimefunItems.DURALUMIN_INGOT, null,
                null, OPTIC_CABLE.getItem(), null,
                null, NETWORK_BRIDGE.getItem(), null
            }
        );
    }

    public static void setup() {
        Networks plugin = Networks.getInstance();

        OPTIC_GLASS.register(plugin);
        OPTIC_CABLE.register(plugin);
        OPTIC_STAR.register(plugin);
        RADIOACTIVE_OPTIC_STAR.register(plugin);

        NETWORK_CONTROLLER.register(plugin);
        NETWORK_BRIDGE.register(plugin);
        NETWORK_MONITOR.register(plugin);
        NETWORK_IMPORT.register(plugin);
        NETWORK_EXPORT.register(plugin);
        NETWORK_GRID.register(plugin);
        NETWORK_CRAFTING_GRID.register(plugin);
        NETWORK_CELL.register(plugin);
        NETWORK_MEMORY_SHELL.register(plugin);
        NETWORK_MEMORY_WIPER_1.register(plugin);
        NETWORK_MEMORY_WIPER_2.register(plugin);
        NETWORK_MEMORY_WIPER_3.register(plugin);
        NETWORK_MEMORY_WIPER_4.register(plugin);

        NETWORK_MEMORY_CARD_1.register(plugin);
        NETWORK_MEMORY_CARD_2.register(plugin);
        NETWORK_MEMORY_CARD_3.register(plugin);
        NETWORK_MEMORY_CARD_4.register(plugin);
        NETWORK_MEMORY_CARD_5.register(plugin);
        NETWORK_MEMORY_CARD_6.register(plugin);
        NETWORK_MEMORY_CARD_7.register(plugin);
        NETWORK_MEMORY_CARD_8.register(plugin);

        NETWORK_PROBE.register(plugin);
    }
}
