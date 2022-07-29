package io.github.sefiraat.networks.slimefun.tools;

import de.jeff_media.morepersistentdatatypes.DataType;
import dev.sefiraat.sefilib.string.Theme;
import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.slimefun.network.grid.NetworkGrid;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.Themes;
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
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
                                player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
                                    "remote.grid-not-set",
                                    Theme.ERROR
                                ));
                            }
                        }
                    } else {
                        tryOpenGrid(e.getItem(), player, NetworkRemote.this.range);
                    }
                    e.cancel();
                }
            }
        );
    }

    public static void setGrid(@Nonnull ItemStack itemStack, @Nonnull Block block, @Nonnull Player player) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        DataTypeMethods.setCustom(itemMeta, KEY, DataType.LOCATION, block.getLocation());
        itemStack.setItemMeta(itemMeta);
        player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
            "remote.grid-set",
            Theme.SUCCESS
        ));
    }

    public static void tryOpenGrid(@Nonnull ItemStack itemStack, @Nonnull Player player, int range) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final Location location = DataTypeMethods.getCustom(itemMeta, KEY, DataType.LOCATION);

        if (location != null) {

            if (!location.getWorld().isChunkLoaded(location.getBlockX() / 16, location.getBlockZ() / 16)) {
                player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
                    "remote.grid-not-loaded",
                    Theme.ERROR
                ));
                return;
            }

            final boolean sameDimension = location.getWorld().equals(player.getWorld());

            if (range == -1
                || range == 0 && sameDimension
                || sameDimension && player.getLocation().distance(location) <= range
            ) {
                openGrid(location, player);
            } else {
                player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
                    "remote.grid-out-of-range",
                    Theme.ERROR
                ));
            }
        } else {
            player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
                "remote.grid-not-bound",
                Theme.ERROR
            ));
        }
    }

    public static void openGrid(@Nonnull Location location, @Nonnull Player player) {
        BlockMenu blockMenu = BlockStorage.getInventory(location);
        SlimefunItem slimefunItem = BlockStorage.check(location);
        if (slimefunItem instanceof NetworkGrid
            && Slimefun.getProtectionManager().hasPermission(player, location, Interaction.INTERACT_BLOCK)
        ) {
            blockMenu.open(player);
        } else {
            player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
                "remote.grid-bound-grid-not-found",
                Theme.ERROR
            ));
        }
    }

    public int getRange() {
        return this.range;
    }

    public static int[] getRanges() {
        return RANGES;
    }
}