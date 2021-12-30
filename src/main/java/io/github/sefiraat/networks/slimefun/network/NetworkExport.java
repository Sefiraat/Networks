package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.ItemRequest;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.slimefun.NetworkSlimefunItems;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class NetworkExport extends NetworkObject {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 17, 18, 22, 26, 27, 31, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    private static final int TEST_ITEM_SLOT = 20;
    private static final int[] TEST_ITEM_BACKDROP = {10, 11, 12, 19, 21, 28, 29, 30};
    private static final int OUTPUT_ITEM_SLOT = 24;
    private static final int[] OUTPUT_ITEM_BACKDROP = {14, 15, 16, 23, 25, 32, 33, 34};

    private static final CustomItemStack TEST_BACKDROP_STACK = new CustomItemStack(
        Material.GREEN_STAINED_GLASS_PANE,
        Theme.SUCCESS + "輸出符合的物品"
    );

    private static final CustomItemStack OUTPUT_BACKDROP_STACK = new CustomItemStack(
        Material.ORANGE_STAINED_GLASS_PANE,
        Theme.SUCCESS + "輸出欄"
    );

    private final ItemSetting<Integer> tickRate;

    public NetworkExport(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, NodeType.EXPORT);
        this.tickRate = new IntRangeSetting(this, "tick_rate", 1, 1, 10);
        addItemSetting(this.tickRate);

        addItemHandler(
            new BlockTicker() {

                private int tick = 1;

                @Override
                public boolean isSynchronized() {
                    return true;
                }

                @Override
                public void tick(Block block, SlimefunItem item, Config data) {
                    if (tick <= 1) {
                        final BlockMenu blockMenu = BlockStorage.getInventory(block);
                        addToRegistry(block);
                        tryGrabItemBitch(blockMenu);
                    }
                }

                @Override
                public void uniqueTick() {
                    tick = tick <= 1 ? tickRate.getValue() : tick - 1;
                }
            },
            new BlockBreakHandler(true, true) {
                @Override
                public void onPlayerBreak(BlockBreakEvent e, ItemStack item, List<ItemStack> drops) {
                    BlockMenu blockMenu = BlockStorage.getInventory(e.getBlock());
                    blockMenu.dropItems(blockMenu.getLocation(), TEST_ITEM_SLOT);
                    blockMenu.dropItems(blockMenu.getLocation(), OUTPUT_ITEM_SLOT);
                }
            }
        );
    }

    private void tryGrabItemBitch(BlockMenu blockMenu) {
        final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());

        if (definition.getNode() == null) {
            return;
        }

        ItemStack testItem = blockMenu.getItemInSlot(TEST_ITEM_SLOT);

        if (testItem == null || blockMenu.getItemInSlot(OUTPUT_ITEM_SLOT) != null) {
            return;
        }

        ItemStack clone = testItem.clone();
        clone.setAmount(1);

        ItemRequest itemRequest = new ItemRequest(clone, clone.getMaxStackSize());
        ItemStack retrieved = definition.getNode().getRoot().getItemStack(itemRequest);
        if (retrieved != null) {
            blockMenu.pushItem(retrieved, OUTPUT_ITEM_SLOT);
        }
    }

    @Override
    public void postRegister() {
        new BlockMenuPreset(this.getId(), this.getItemName()) {

            @Override
            public void init() {
                drawBackground(BACKGROUND_SLOTS);
                drawBackground(TEST_BACKDROP_STACK, TEST_ITEM_BACKDROP);
                drawBackground(OUTPUT_BACKDROP_STACK, OUTPUT_ITEM_BACKDROP);
                setSize(45);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return NetworkSlimefunItems.NETWORK_GRID.canUse(player, false)
                    && Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                if (flow == ItemTransportFlow.WITHDRAW) {
                    return new int[]{OUTPUT_ITEM_SLOT};
                }
                return new int[0];
            }
        };
    }

}
