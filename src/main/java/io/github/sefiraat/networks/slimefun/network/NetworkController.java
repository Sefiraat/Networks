package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.NetworkNode;
import io.github.sefiraat.networks.network.NetworkRoot;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class NetworkController extends NetworkObject {

    private static final String CRAYON = "crayon";
    private static final Map<Location, NetworkRoot> NETWORKS = new HashMap<>();
    private static final Set<Location> CRAYONS = new HashSet<>();

    private final ItemSetting<Integer> maxNodes;
    protected final Map<Location, Boolean> firstTickMap = new HashMap<>();

    public NetworkController(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, NodeType.CONTROLLER);

        maxNodes = new IntRangeSetting(this, "max_nodes", 10, 2000, 5000);
        addItemSetting(maxNodes);

        addItemHandler(
            new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return false;
                }

                @Override
                public void tick(Block block, SlimefunItem item, Config data) {

                    if (!firstTickMap.containsKey(block.getLocation())) {
                        onFirstTick(block, data);
                        firstTickMap.put(block.getLocation(), true);
                    }

                    addToRegistry(block);
                    NetworkRoot networkRoot = new NetworkRoot(block.getLocation(), NodeType.CONTROLLER, maxNodes.getValue());
                    networkRoot.addAllChildren();

                    NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(block.getLocation());
                    if (definition != null) {
                        definition.setNode(networkRoot);
                    }

                    boolean crayon = CRAYONS.contains(block.getLocation());
                    if (crayon) {
                        networkRoot.setDisplayParticles(true);
                    }

                    NETWORKS.put(block.getLocation(), networkRoot);
                }
            }
        );
    }

    @Override
    protected void prePlace(@Nonnull PlayerRightClickEvent event) {
        Optional<Block> blockOptional = event.getClickedBlock();

        if (blockOptional.isPresent()) {
            Block block = blockOptional.get();
            Block target = block.getRelative(event.getClickedFace());

            for (BlockFace checkFace : CHECK_FACES) {
                Block checkBlock = target.getRelative(checkFace);
                SlimefunItem slimefunItem = BlockStorage.check(checkBlock);

                // For directly adjacent controllers
                if (slimefunItem instanceof NetworkController) {
                    cancelPlace(event);
                    return;
                }

                // Check for node definitions. If there isn't one, we don't care
                NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(checkBlock.getLocation());
                if (definition == null) {
                    continue;
                }

                // There is a definition, if it has a node, then it's part of an active network.
                if (definition.getNode() != null) {
                    cancelPlace(event);
                    return;
                }
            }
        }
    }

    @Override
    protected void cancelPlace(PlayerRightClickEvent event) {
        event.getPlayer().sendMessage(Theme.ERROR.getColor() + "This network already has a controller!");
        event.cancel();
    }

    private void onFirstTick(@Nonnull Block block, @Nonnull Config data) {
        final String crayon = data.getString(CRAYON);
        if (Boolean.parseBoolean(crayon)) {
            CRAYONS.add(block.getLocation());
        }
    }

    public static Map<Location, NetworkRoot> getNetworks() {
        return NETWORKS;
    }

    public static Set<Location> getCrayons() {
        return CRAYONS;
    }

    public static void addCrayon(@Nonnull Location location) {
        BlockStorage.addBlockInfo(location, CRAYON, String.valueOf(true));
        CRAYONS.add(location);
    }

    public static void removeCrayon(@Nonnull Location location) {
        BlockStorage.addBlockInfo(location, CRAYON, null);
        CRAYONS.remove(location);
    }

    public static boolean hasCrayon(@Nonnull Location location) {
        return CRAYONS.contains(location);
    }

    public static void wipeNetwork(@Nonnull Location location) {
        NetworkRoot networkRoot = NETWORKS.remove(location);
        if (networkRoot != null) {
            for (NetworkNode node : networkRoot.getChildrenNodes()) {
                NetworkStorage.removeNode(node.getNodePosition());
            }
        }
    }
}
