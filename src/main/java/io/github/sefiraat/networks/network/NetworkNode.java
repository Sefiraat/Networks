package io.github.sefiraat.networks.network;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.Networks;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class NetworkNode {

    protected static final Set<BlockFace> VALID_FACES = EnumSet.of(
        BlockFace.UP,
        BlockFace.DOWN,
        BlockFace.NORTH,
        BlockFace.EAST,
        BlockFace.SOUTH,
        BlockFace.WEST
    );

    protected final Set<NetworkNode> childrenNodes = new HashSet<>();
    protected NetworkNode parent = null;
    protected NetworkRoot root = null;
    protected Location nodePosition;
    protected NodeType nodeType;

    public NetworkNode(Location location, NodeType type) {
        this.nodePosition = location;
        this.nodeType = type;
    }

    public NetworkNode addChild(@Nonnull NetworkNode child) {
        child.setParent(this);
        child.setRoot(this.getRoot());
        this.root.addNode(child.nodePosition, child.nodeType);
        this.childrenNodes.add(child);
        return child;
    }

    public Location getNodePosition() {
        return nodePosition;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public boolean networkContains(@Nonnull NetworkNode networkNode) {
        return networkContains(networkNode.nodePosition);
    }

    public boolean networkContains(@Nonnull Location location) {
        return getNetworkLocations().contains(location);
    }

    @Nonnull
    public Set<Location> getNetworkLocations() {
        return getRoot().networkLocations;
    }

    public NetworkRoot getRoot() {
        return this.root;
    }

    private void setRoot(NetworkRoot root) {
        this.root = root;
    }

    public NetworkNode getParent() {
        return parent;
    }

    private void setParent(NetworkNode parent) {
        this.parent = parent;
    }

    public int numberOfChildren() {
        return this.childrenNodes.size();
    }

    public boolean isLeaf() {
        return this.childrenNodes.isEmpty();
    }

    public boolean isBranch() {
        return !this.childrenNodes.isEmpty();
    }

    public void delete() {
        if (parent != null) {
            this.parent.getChildrenNodes().remove(this);
        }
        this.getChildrenNodes().clear();
    }

    public Set<NetworkNode> getChildrenNodes() {
        return childrenNodes;
    }

    public void addAllChildren() {

        // Loop through all possible locations
        for (BlockFace face : VALID_FACES) {
            final Location testLocation = this.nodePosition.clone().add(face.getDirection());
            final NodeDefinition testDefinition = NetworkStorage.getAllNetworkObjects().get(testLocation);

            if (testDefinition == null) {
                continue;
            }

            final NodeType testType = testDefinition.getType();

            // Kill additional controllers if it isn't the root
            if (testType == NodeType.CONTROLLER && !testLocation.equals(getRoot().nodePosition)) {
                killAdditionalController(testLocation);
            }

            // Check if it's in the network already and, if not, create a child node and propagate further.
            if (testType != NodeType.CONTROLLER && !this.networkContains(testLocation)) {
                final NetworkNode networkNode = new NetworkNode(testLocation, testType);
                addChild(networkNode);
                networkNode.addAllChildren();
                testDefinition.setNode(networkNode);
                NetworkStorage.getAllNetworkObjects().put(testLocation, testDefinition);
            }

        }
    }

    private void killAdditionalController(@Nonnull Location location) {
        final Block block = location.getBlock();
        final ItemStack toDrop = BlockStorage.retrieve(block);
        if (toDrop != null) {
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    location.getWorld().dropItemNaturally(location, toDrop);
                    block.setType(Material.AIR);
                }
            };
            runnable.runTask(Networks.getInstance());
            NetworkStorage.getAllNetworkObjects().remove(location);
        }
    }

    @Nonnull
    protected Set<Location> getMatchingChildren(@Nonnull NodeType nodeType) {
        Set<Location> itemList = new HashSet<>();
        for (NetworkNode networkNode : getChildrenNodes()) {
            itemList.addAll(networkNode.getMatchingChildren(nodeType));
            if (this.nodeType == nodeType) {
                itemList.add(this.nodePosition);
            }
        }
        return itemList;
    }

}
