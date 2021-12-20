package io.github.sefiraat.networks.slimefun.tools;

import io.github.sefiraat.networks.network.NetworkRoot;
import io.github.sefiraat.networks.slimefun.network.NetworkController;
import io.github.sefiraat.networks.utils.GeneralUtils;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.Optional;

public class NetworkProbe extends SlimefunItem {

    public NetworkProbe(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        addItemHandler(
            new ItemUseHandler() {
                @Override
                public void onRightClick(PlayerRightClickEvent e) {
                    Optional<Block> optional = e.getClickedBlock();
                    if (optional.isPresent()) {
                        Block block = optional.get();
                        Player player = e.getPlayer();
                        SlimefunItem slimefunItem = SlimefunItem.getByItem(player.getInventory().getItemInMainHand());
                        if (slimefunItem instanceof NetworkProbe) {
                            displayToPlayer(block, player);
                            GeneralUtils.putOnCooldown(e.getItem(), 5);
                            e.cancel();
                        }
                    }
                }
            }
        );
    }

    private void displayToPlayer(@Nonnull Block block, @Nonnull Player player) {
        NetworkRoot root = NetworkController.NETWORKS.get(block.getLocation());
        if (root != null) {

            long time = System.nanoTime();

            int bridges = root.getBridges().size();
            long timeBridge = System.nanoTime();

            int importers = root.getImports().size();
            long timeImporter = System.nanoTime();

            int exporters = root.getExports().size();
            long timeExporter = System.nanoTime();

            int monitors = root.getMonitors().size();
            long timeMonitors = System.nanoTime();

            int cells = root.getCells().size();
            long timeCells = System.nanoTime();

            int barrels = root.getNetworkBarrels().size();
            long timeBarrels = System.nanoTime();

            int items = root.getAllNetworkItems().size();
            long timeItems = System.nanoTime();

            player.sendMessage("");
            player.sendMessage(MessageFormat.format("Bridges: {0} (Time taken: {1} ns)", bridges, timeBridge - time));
            player.sendMessage(MessageFormat.format("Importers: {0} (Time taken: {1} ns)", importers, timeImporter - timeBridge));
            player.sendMessage(MessageFormat.format("Exporters: {0} (Time taken: {1} ns)", exporters, timeExporter - timeImporter));
            player.sendMessage(MessageFormat.format("Monitors: {0} (Time taken: {1} ns)", monitors, timeMonitors - timeExporter));
            player.sendMessage(MessageFormat.format("Cells: {0} (Time taken: {1} ns)", cells, timeCells - timeMonitors));
            player.sendMessage(MessageFormat.format("Barrels: {0} (Time taken: {1} ns)", barrels, timeBarrels - timeCells));
            player.sendMessage(MessageFormat.format("Items: {0} (Time taken: {1} ns)", items, timeItems - timeBarrels));
        }
    }

}
