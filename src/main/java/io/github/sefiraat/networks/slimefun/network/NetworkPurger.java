package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.network.stackcaches.ItemRequest;
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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class NetworkPurger extends NetworkObject {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 2, 6, 7, 8, 9, 10, 11, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private static final int TEST_ITEM_SLOT = 13;
    private static final int[] TEST_ITEM_BACKDROP = {3, 4, 5, 12, 14, 21, 22, 23};

    private static final CustomItemStack TEST_BACKDROP_STACK = new CustomItemStack(
        Material.GREEN_STAINED_GLASS_PANE,
        Theme.SUCCESS + "Purge Item Matching"
    );

    private final ItemSetting<Integer> tickRate;

    public NetworkPurger(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, NodeType.PURGER);
        this.tickRate = new IntRangeSetting(this, "tick_rate", 1, 1, 10);
        addItemSetting(this.tickRate);

        this.getSlotsToDrop().add(TEST_ITEM_SLOT);

        addItemHandler(
            new BlockTicker() {

                private int tick = 1;

                @Override
                public boolean isSynchronized() {
                    return false;
                }

                @Override
                public void tick(Block block, SlimefunItem item, Config data) {
                    if (tick <= 1) {
                        final BlockMenu blockMenu = BlockStorage.getInventory(block);
                        addToRegistry(block);
                        tryKillItem(blockMenu);
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
                }
            }
        );
    }

    private void tryKillItem(@Nonnull BlockMenu blockMenu) {
        final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());

        if (definition == null || definition.getNode() == null) {
            return;
        }

        ItemStack testItem = blockMenu.getItemInSlot(TEST_ITEM_SLOT);

        if (testItem == null) {
            return;
        }

        ItemStack clone = testItem.clone();
        clone.setAmount(1);

        ItemRequest itemRequest = new ItemRequest(clone, clone.getMaxStackSize());
        ItemStack retrieved = definition.getNode().getRoot().getItemStack(itemRequest);
        if (retrieved != null) {
            retrieved.setAmount(0);
            Location location = blockMenu.getLocation().clone().add(0.5, 1.2, 0.5);
            if (definition.getNode().getRoot().isDisplayParticles()) {
                location.getWorld().spawnParticle(Particle.SMOKE_NORMAL, location, 0, 0, 0.05, 0);
            }
        }
    }

    @Override
    public void postRegister() {
        new BlockMenuPreset(this.getId(), this.getItemName()) {

            @Override
            public void init() {
                drawBackground(BACKGROUND_SLOTS);
                drawBackground(TEST_BACKDROP_STACK, TEST_ITEM_BACKDROP);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return NetworkSlimefunItems.NETWORK_PURGER.canUse(player, false)
                    && Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[0];
            }
        };
    }
}
