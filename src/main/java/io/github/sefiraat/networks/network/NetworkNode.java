package io.github.sefiraat.networks.network;

import io.github.sefiraat.networks.NetworkStorage;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class NetworkNode {

    public static final Set<Material> VALID_CONTAINERS = Set.of(
        Material.CHEST,
        Material.BARREL,
        Material.SHULKER_BOX
    );

    public static final Set<BlockFace> VALID_FACES = EnumSet.of(
        BlockFace.UP,
        BlockFace.DOWN,
        BlockFace.NORTH,
        BlockFace.EAST,
        BlockFace.SOUTH,
        BlockFace.WEST
    );
    private static final Particle.DustOptions DUST_OPTIONS = new Particle.DustOptions(Color.RED, 1);

    protected final Set<NetworkNode> childrenNodes = new HashSet<>();
    protected NetworkNode parent = null;
    protected NetworkRoot root = null;
    protected Location nodePosition;
    protected ObjectType nodeType;

    public NetworkNode(Location location, ObjectType type) {
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

    public Set<NetworkNode> getChildrenNodes() {
        return childrenNodes;
    }

    public Location getNodePosition() {
        return nodePosition;
    }

    public ObjectType getNodeType() {
        return nodeType;
    }

    public NetworkRoot getRoot() {
        return this.root;
    }

    private void setRoot(NetworkRoot root) {
        this.root = root;
    }

    @Nonnull
    public Set<Location> getNetworkLocations() {
        return getRoot().networkLocations;
    }

    public boolean networkContains(@Nonnull NetworkNode networkNode) {
        return networkContains(networkNode.nodePosition);
    }

    public boolean networkContains(@Nonnull Location location) {
        return getNetworkLocations().contains(location);
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

    public void addAllChildren() {

        // Loop through all possible locations
        for (BlockFace face : VALID_FACES) {
            final Location testLocation = this.nodePosition.clone().add(face.getDirection());
            final ObjectDefinition testDefinition = NetworkStorage.getAllNetworkObjects().get(testLocation);

            if (testDefinition == null) {
                continue;
            }

            final ObjectType testType = testDefinition.getType();

            // Kill additional controllers if it isn't the root
            if (testType == ObjectType.CONTROLLER && !testLocation.equals(getRoot().nodePosition)) {
                killAdditionalController(testLocation);
            }

            // Check if it's in the network already and, if not, create a child node and propagate further.
            if (testType != ObjectType.CONTROLLER && !this.networkContains(testLocation)) {
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
            location.getWorld().dropItemNaturally(location, toDrop);
        }
        block.setType(Material.AIR);
    }

    @Nonnull
    protected Set<Location> getMatchingChildren(@Nonnull ObjectType objectType) {
        Set<Location> itemList = new HashSet<>();
        for (NetworkNode networkNode : getChildrenNodes()) {
            itemList.addAll(networkNode.getMatchingChildren(objectType));
            if (this.nodeType == objectType) {
                itemList.add(this.nodePosition);
            }
        }
        return itemList;
    }

}
