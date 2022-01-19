package io.github.sefiraat.networks.slimefun.tools;

import de.jeff_media.morepersistentdatatypes.DataType;
import io.github.sefiraat.networks.slimefun.network.NetworkDirectional;
import io.github.sefiraat.networks.slimefun.network.grid.NetworkGrid;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.Theme;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Optional;

public class NetworkRemote extends SlimefunItem {

    private static final NamespacedKey KEY = Keys.newKey("location");
    private static final int[] RANGES = new int[]{
        150,
        500,
        0,
        -1
    };

    private final int range;

    public NetworkRemote(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int range) {
        super(itemGroup, item, recipeType, recipe);
        this.range = range;
        addItemHandler(
            new ItemUseHandler() {
                @Override
                public void onRightClick(PlayerRightClickEvent e) {
                    final Player player = e.getPlayer();
                    if (player.isSneaking()) {
                        final Optional<Block> optional = e.getClickedBlock();
                        if (optional.isPresent()) {
                            final Block block = optional.get();
                            final SlimefunItem slimefunItem = BlockStorage.check(block);
                            if (Slimefun.getProtectionManager().hasPermission(player, block, Interaction.INTERACT_BLOCK)
                                && slimefunItem instanceof NetworkGrid
                            ) {
                                setGrid(e.getItem(), block, player);
                            } else {
                                player.sendMessage(Theme.ERROR + "Must be set to a Network Grid (not crafting grid).");
                            }
                        }
                    } else {
                        tryOpenGrid(e.getItem(), player);
                    }
                    e.cancel();
                }
            }
        );
    }

    private void setGrid(@Nonnull ItemStack itemStack, @Nonnull Block block, @Nonnull Player player) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        DataTypeMethods.setCustom(itemMeta, KEY, DataType.LOCATION, block.getLocation());
        itemStack.setItemMeta(itemMeta);
        player.sendMessage(Theme.SUCCESS + "Grid has been bound to the remote.");
    }

    private void tryOpenGrid(@Nonnull ItemStack itemStack, @Nonnull Player player) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final Location location = DataTypeMethods.getCustom(itemMeta, KEY, DataType.LOCATION);

        if (location != null) {

            if (!location.getWorld().isChunkLoaded(location.getBlockX() / 16, location.getBlockZ() / 16)) {
                player.sendMessage(Theme.ERROR + "The bound grid is not loaded.");
                return;
            }

            final boolean sameDimension = location.getWorld().equals(player.getWorld());

            if (this.range == -1
                || this.range == 0 && sameDimension
                || sameDimension && player.getLocation().distance(location) <= this.range
            ) {
                openGrid(location, player);
            } else {
                player.sendMessage(Theme.ERROR + "The bound grid is not within reach.");
            }
        } else {
            player.sendMessage(Theme.ERROR + "Remote is not bound to a grid.");
        }
    }

    private void openGrid(@Nonnull Location location, @Nonnull Player player) {
        BlockMenu blockMenu = BlockStorage.getInventory(location);
        SlimefunItem slimefunItem = BlockStorage.check(location);
        if (Slimefun.getProtectionManager().hasPermission(player, blockMenu.getLocation(), Interaction.INTERACT_BLOCK)
            && slimefunItem instanceof NetworkGrid
        ) {
            blockMenu.open(player);
        } else {
            player.sendMessage(Theme.ERROR + "The bound grid can no longer be found.");
            Firework firework;
            FireworkEffectMeta effectMeta;
            effectMeta.setEffect(FireworkEffect.builder().withColor());
        }
    }

    public int getRange() {
        return this.range;
    }

    public static int[] getRanges() {
        return RANGES;
    }
}