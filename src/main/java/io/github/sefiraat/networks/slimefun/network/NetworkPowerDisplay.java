package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.network.stackcaches.ItemRequest;
import io.github.sefiraat.networks.utils.StackUtils;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public abstract class NetworkPowerDisplay extends NetworkObject {

    private static final int[] BACKGROUND_SLOTS = new int[]{
        0,1,2,3,5,6,7,8
    };
    private static final int DISPLAY_SLOT = 4;

    public NetworkPowerDisplay(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, NodeType.CRAFTER);
        addItemHandler(
            new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return false;
                }

                @Override
                public void tick(Block block, SlimefunItem slimefunItem, Config config) {

                }
            }
        );
    }
}
