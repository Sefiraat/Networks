package io.github.sefiraat.networks.slimefun.tools;

import io.github.sefiraat.networks.slimefun.network.NetworkController;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Optional;

public class NetworkCrayon extends SlimefunItem {

    public NetworkCrayon(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        addItemHandler(
            new ItemUseHandler() {
                @Override
                public void onRightClick(PlayerRightClickEvent e) {
                    final Optional<Block> optional = e.getClickedBlock();
                    if (optional.isPresent()) {
                        final Block block = optional.get();
                        final Player player = e.getPlayer();
                        final SlimefunItem slimefunItem = BlockStorage.check(block);
                        if (slimefunItem instanceof NetworkController) {
                            toggleCrayon(block, player);
                            e.cancel();
                        }
                    }
                }
            }
        );
    }

    public void toggleCrayon(@Nonnull Block block, @Nonnull Player player) {
        if (NetworkController.hasCrayon(block.getLocation())) {
            NetworkController.removeCrayon(block.getLocation());
            player.sendMessage(Theme.WARNING + "Crayon removed from network.");
        } else {
            NetworkController.addCrayon(block.getLocation());
            player.sendMessage(Theme.SUCCESS + "Crayon added to network.");
        }
    }
}
