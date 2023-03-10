package io.github.sefiraat.networks.slimefun.network;

import dev.sefiraat.sefilib.misc.ParticleUtils;
import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.slimefun.NetworkSlimefunItems;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

public class NetworkVacuum extends NetworkObject {

    private static final int[] INPUT_SLOTS = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};

    private final ItemSetting<Integer> tickRate;
    private final ItemSetting<Integer> vacuumRange;

    public NetworkVacuum(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, NodeType.VACUUM);

        this.tickRate = new IntRangeSetting(this, "tick_rate", 1, 1, 10);
        this.vacuumRange = new IntRangeSetting(this, "vacuum_range", 1, 2, 5);
        addItemSetting(this.tickRate, this.vacuumRange);

        for (int inputSlot : INPUT_SLOTS) {
            this.getSlotsToDrop().add(inputSlot);
        }

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
                        tryAddItem(blockMenu);
                        Bukkit.getScheduler().runTask(Networks.getInstance(), bukkitTask -> findItem(blockMenu));
                    }
                }

                @Override
                public void uniqueTick() {
                    tick = tick <= 1 ? tickRate.getValue() : tick - 1;
                }
            }
        );
    }

    private void findItem(@Nonnull BlockMenu blockMenu) {
        for (int inputSlot : INPUT_SLOTS) {
            final ItemStack inSlot = blockMenu.getItemInSlot(inputSlot);
            if (inSlot == null || inSlot.getType().isAir()) {
                final Location location = blockMenu.getLocation().clone().add(0.5, 0.5, 0.5);
                final int range = this.vacuumRange.getValue();
                Collection<Entity> items = location.getWorld()
                    .getNearbyEntities(location, range, range, range, Item.class::isInstance);
                Optional<Entity> optionalEntity = items.stream().findFirst();
                if (optionalEntity.isEmpty() || !(optionalEntity.get() instanceof Item item)) {
                    return;
                }
                if (item.getPickupDelay() <= 0 && !SlimefunUtils.hasNoPickupFlag(item)) {
                    final ItemStack itemStack = item.getItemStack();
                    blockMenu.replaceExistingItem(inputSlot, itemStack);
                    ParticleUtils.displayParticleRandomly(item, 1, 5, new Particle.DustOptions(Color.BLUE, 1));
                    item.remove();
                }
                return;
            }
        }
    }

    private void tryAddItem(@Nonnull BlockMenu blockMenu) {
        final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());

        if (definition.getNode() == null) {
            return;
        }

        for (int inputSlot : INPUT_SLOTS) {
            final ItemStack itemStack = blockMenu.getItemInSlot(inputSlot);

            if (itemStack == null || itemStack.getType() == Material.AIR) {
                continue;
            }
            definition.getNode().getRoot().addItemStack(itemStack);
        }
    }

    @Override
    public void postRegister() {
        new BlockMenuPreset(this.getId(), this.getItemName()) {

            @Override
            public void init() {
                setSize(9);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return NetworkSlimefunItems.NETWORK_GRID.canUse(player, false)
                    && Slimefun.getProtectionManager()
                    .hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                if (flow == ItemTransportFlow.INSERT) {
                    return INPUT_SLOTS;
                }
                return new int[0];
            }
        };
    }
}
