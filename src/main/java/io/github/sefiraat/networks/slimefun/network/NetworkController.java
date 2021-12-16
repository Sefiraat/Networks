package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.network.NetworkRoot;
import io.github.sefiraat.networks.network.NodeType;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NetworkController extends NetworkObject {

    protected static final Map<Location, NetworkRoot> NETWORKS = new HashMap<>();

    public NetworkController(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, NodeType.CONTROLLER);

        addItemHandler(
            new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return true;
                }

                @Override
                public void tick(Block block, SlimefunItem item, Config data) {
                    addToRegistry(block);
                    NetworkRoot networkRoot = new NetworkRoot(block.getLocation(), NodeType.CONTROLLER);
                    networkRoot.addAllChildren();
                    NETWORKS.put(block.getLocation(), networkRoot);
                }
            },
            new BlockUseHandler() {
                @Override
                public void onRightClick(PlayerRightClickEvent event) {
                    Optional<Block> optional = event.getClickedBlock();
                    if (optional.isPresent()) {
                        Block block = optional.get();
                        Player player = event.getPlayer();
                        NetworkRoot root = NETWORKS.get(block.getLocation());
                        if (root != null) {

                            long time = System.nanoTime();
                            int bridges = root.getBridges().size();
                            long timeBridge = System.nanoTime();
                            int monitors = root.getMonitors().size();
                            long timeMonitors = System.nanoTime();
                            int cells = root.getCells().size();
                            long timeCells = System.nanoTime();
                            int importers = root.getImports().size();
                            long timeImporter = System.nanoTime();
                            int exporters = root.getExports().size();
                            long timeExporter = System.nanoTime();
                            int inventories = root.getCellMenus().size();
                            long timeInventories = System.nanoTime();
                            int items = root.getAllNetworkItems().size();
                            long timeItems = System.nanoTime();

                            player.sendMessage("");
                            player.sendMessage(MessageFormat.format("Bridges: {0} ({1})", bridges, timeBridge - time));
                            player.sendMessage(MessageFormat.format("Monitors: {0} ({1})", monitors, timeMonitors - timeBridge));
                            player.sendMessage(MessageFormat.format("Cells: {0} ({1})", cells, timeCells - timeMonitors));
                            player.sendMessage(MessageFormat.format("Importers: {0} ({1})", importers, timeImporter - timeCells));
                            player.sendMessage(MessageFormat.format("Exporters: {0} ({1})", exporters, timeExporter - timeImporter));
                            player.sendMessage(MessageFormat.format("Inventories: {0} ({1})", inventories, timeInventories - timeExporter));
                            player.sendMessage(MessageFormat.format("Items: {0} ({1})", items, timeItems - timeInventories));
                        }
                    }
                }
            }
        );
    }
}
