package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public abstract class NetworkObject extends SlimefunItem {

    @Getter
    private final NodeType nodeType;
    @Getter
    private final List<Integer> slotsToDrop = new ArrayList<>();

    protected NetworkObject(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, NodeType type) {
        this(itemGroup, item, recipeType, recipe, null, type);
    }

    protected NetworkObject(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, ItemStack recipeOutput, NodeType type) {
        super(itemGroup, item, recipeType, recipe, recipeOutput);
        this.nodeType = type;
        addItemHandler(
            new BlockTicker() {

                @Override
                public boolean isSynchronized() {
                    return runSync();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    addToRegistry(b);
                }
            },
            new BlockBreakHandler(false, false) {
                @Override
                @ParametersAreNonnullByDefault
                public void onPlayerBreak(BlockBreakEvent event, ItemStack item, List<ItemStack> drops) {
                    preBreak(event);
                    onBreak(event);
                }
            }
        );
    }

    protected void addToRegistry(@Nonnull Block block) {
        if (!NetworkStorage.getAllNetworkObjects().containsKey(block.getLocation())) {
            NetworkStorage.getAllNetworkObjects().put(block.getLocation(), new NodeDefinition(nodeType));
        }
    }

    protected void preBreak(@Nonnull BlockBreakEvent event) {

    }

    protected void onBreak(@Nonnull BlockBreakEvent event) {
        final Location location = event.getBlock().getLocation();
        final BlockMenu blockMenu = BlockStorage.getInventory(event.getBlock());

        if (blockMenu != null) {
            for (int i : this.slotsToDrop) {
                blockMenu.dropItems(location, i);
            }
        }
        NetworkStorage.removeNode(location);

        if (this.nodeType == NodeType.CONTROLLER) {
            NetworkController.wipeNetwork(location);
        }

        BlockStorage.clearBlockInfo(location);
    }

    public boolean runSync() {
        return false;
    }
}
