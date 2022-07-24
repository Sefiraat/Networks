package io.github.sefiraat.networks.network;

import io.github.mooy1.infinityexpansion.items.storage.StorageUnit;
import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.network.barrel.InfinityBarrel;
import io.github.sefiraat.networks.network.barrel.NetworkStorage;
import io.github.sefiraat.networks.network.stackcaches.BarrelIdentity;
import io.github.sefiraat.networks.network.stackcaches.ItemRequest;
import io.github.sefiraat.networks.network.stackcaches.QuantumCache;
import io.github.sefiraat.networks.slimefun.network.NetworkDirectional;
import io.github.sefiraat.networks.slimefun.network.NetworkGreedyBlock;
import io.github.sefiraat.networks.slimefun.network.NetworkPowerNode;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumStorage;
import io.github.sefiraat.networks.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NetworkRoot extends NetworkNode {

    private final Set<Location> nodeLocations = new HashSet<>();
    private final int maxNodes;
    private boolean isOverburdened = false;

    private final Set<Location> bridges = new HashSet<>();
    private final Set<Location> monitors = new HashSet<>();
    private final Set<Location> importers = new HashSet<>();
    private final Set<Location> exporters = new HashSet<>();
    private final Set<Location> grids = new HashSet<>();
    private final Set<Location> cells = new HashSet<>();
    private final Set<Location> wipers = new HashSet<>();
    private final Set<Location> grabbers = new HashSet<>();
    private final Set<Location> pushers = new HashSet<>();
    private final Set<Location> purgers = new HashSet<>();
    private final Set<Location> crafters = new HashSet<>();
    private final Set<Location> powerNodes = new HashSet<>();
    private final Set<Location> powerDisplays = new HashSet<>();
    private final Set<Location> encoders = new HashSet<>();
    private final Set<Location> greedyBlocks = new HashSet<>();

    private Set<BarrelIdentity> barrels = null;

    private long rootPower = 0;

    private boolean displayParticles = false;

    public NetworkRoot(@Nonnull Location location, @Nonnull NodeType type, int maxNodes) {
        super(location, type);
        this.maxNodes = maxNodes;
        this.root = this;
    }

    public void registerNode(@Nonnull Location location, @Nonnull NodeType type) {
        nodeLocations.add(location);
        switch (type) {
            case CONTROLLER -> {
                // Nothing here guvnor
            }
            case BRIDGE -> bridges.add(location);
            case STORAGE_MONITOR -> monitors.add(location);
            case IMPORT -> importers.add(location);
            case EXPORT -> exporters.add(location);
            case GRID -> grids.add(location);
            case CELL -> cells.add(location);
            case WIPER -> wipers.add(location);
            case GRABBER -> grabbers.add(location);
            case PUSHER -> pushers.add(location);
            case PURGER -> purgers.add(location);
            case CRAFTER -> crafters.add(location);
            case POWER_NODE -> powerNodes.add(location);
            case POWER_DISPLAY -> powerDisplays.add(location);
            case ENCODER -> encoders.add(location);
            case GREEDY_BLOCK -> greedyBlocks.add(location);
        }
    }

    public Set<Location> getNodeLocations() {
        return this.nodeLocations;
    }

    public int getMaxNodes() {
        return maxNodes;
    }

    public int getNodeCount() {
        return this.nodeLocations.size();
    }

    public boolean isOverburdened() {
        return isOverburdened;
    }

    public void setOverburdened(boolean overburdened) {
        if (overburdened && !isOverburdened) {
            final Location loc = this.nodePosition.clone();
            for (int x = 0; x <= 1; x++) {
                for (int y = 0; y <= 1; y++) {
                    for (int z = 0; z <= 1; z++) {
                        loc.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc.clone().add(x, y, z), 0);
                    }
                }
            }
        }
        this.isOverburdened = overburdened;
    }

    public Set<Location> getBridges() {
        return this.bridges;
    }

    public Set<Location> getMonitors() {
        return this.monitors;
    }

    public Set<Location> getImporters() {
        return this.importers;
    }

    public Set<Location> getExporters() {
        return this.exporters;
    }

    public Set<Location> getGrids() {
        return this.grids;
    }

    public Set<Location> getCells() {
        return this.cells;
    }

    public Set<Location> getWipers() {
        return this.wipers;
    }

    public Set<Location> getGrabbers() {
        return this.grabbers;
    }

    public Set<Location> getPushers() {
        return this.pushers;
    }

    public Set<Location> getPurgers() {
        return this.purgers;
    }

    public Set<Location> getCrafters() {
        return this.crafters;
    }

    public Set<Location> getPowerNodes() {
        return this.powerNodes;
    }

    public Set<Location> getPowerDisplays() {
        return this.powerDisplays;
    }

    public Set<Location> getEncoders() {
        return this.encoders;
    }

    @Nonnull
    public Map<ItemStack, Integer> getAllNetworkItems() {
        final Map<ItemStack, Integer> itemStacks = new HashMap<>();

        // Barrels
        for (BarrelIdentity barrelIdentity : getBarrels()) {
            final Integer currentAmount = itemStacks.get(barrelIdentity.getItemStack());
            final int newAmount;
            if (currentAmount == null) {
                newAmount = barrelIdentity.getAmount();
            } else {
                long newLong = (long) currentAmount + (long) barrelIdentity.getAmount();
                if (newLong > Integer.MAX_VALUE) {
                    newAmount = Integer.MAX_VALUE;
                } else {
                    newAmount = currentAmount + barrelIdentity.getAmount();
                }
            }
            itemStacks.put(barrelIdentity.getItemStack(), newAmount);
        }

        for (BlockMenu blockMenu : getGreedyBlocks()) {
            final ItemStack itemStack = blockMenu.getItemInSlot(NetworkGreedyBlock.INPUT_SLOT);
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                continue;
            }
            final ItemStack clone = StackUtils.getAsQuantity(itemStack, 1);
            final Integer currentAmount = itemStacks.get(clone);
            final int newAmount;
            if (currentAmount == null) {
                newAmount = itemStack.getAmount();
            } else {
                long newLong = (long) currentAmount + (long) itemStack.getAmount();
                if (newLong > Integer.MAX_VALUE) {
                    newAmount = Integer.MAX_VALUE;
                } else {
                    newAmount = currentAmount + itemStack.getAmount();
                }
            }
            itemStacks.put(clone, newAmount);
        }

        for (BlockMenu blockMenu : getCrafterOutputs()) {
            int[] slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
            for (int slot : slots) {
                final ItemStack itemStack = blockMenu.getItemInSlot(slot);
                if (itemStack == null || itemStack.getType() == Material.AIR) {
                    continue;
                }
                final ItemStack clone = StackUtils.getAsQuantity(itemStack, 1);
                final Integer currentAmount = itemStacks.get(clone);
                final int newAmount;
                if (currentAmount == null) {
                    newAmount = itemStack.getAmount();
                } else {
                    long newLong = (long) currentAmount + (long) itemStack.getAmount();
                    if (newLong > Integer.MAX_VALUE) {
                        newAmount = Integer.MAX_VALUE;
                    } else {
                        newAmount = currentAmount + itemStack.getAmount();
                    }
                }
                itemStacks.put(clone, newAmount);
            }
        }

        for (BlockMenu blockMenu : getCellMenus()) {
            for (ItemStack itemStack : blockMenu.getContents()) {
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    final ItemStack clone = itemStack.clone();

                    clone.setAmount(1);

                    final Integer currentAmount = itemStacks.get(clone);
                    int newAmount;

                    if (currentAmount == null) {
                        newAmount = itemStack.getAmount();
                    } else {
                        long newLong = (long) currentAmount + (long) itemStack.getAmount();
                        if (newLong > Integer.MAX_VALUE) {
                            newAmount = Integer.MAX_VALUE;
                        } else {
                            newAmount = currentAmount + itemStack.getAmount();
                        }
                    }

                    itemStacks.put(clone, newAmount);
                }
            }
        }
        return itemStacks;
    }

    @Nonnull
    public Set<BarrelIdentity> getBarrels() {

        if (this.barrels != null) {
            return this.barrels;
        }

        final Set<Location> addedLocations = new HashSet<>();
        final Set<BarrelIdentity> barrelSet = new HashSet<>();

        for (Location cellLocation : this.monitors) {
            final BlockFace face = NetworkDirectional.getSelectedFace(cellLocation);

            if (face == null) {
                continue;
            }

            final Location testLocation = cellLocation.clone().add(face.getDirection());

            if (addedLocations.contains(testLocation)) {
                continue;
            } else {
                addedLocations.add(testLocation);
            }

            final SlimefunItem slimefunItem = BlockStorage.check(testLocation);

            if (Networks.getSupportedPluginManager()
                .isInfinityExpansion() && slimefunItem instanceof StorageUnit unit) {
                final BlockMenu menu = BlockStorage.getInventory(testLocation);
                final InfinityBarrel infinityBarrel = getInfinityBarrel(menu, unit);
                if (infinityBarrel != null) {
                    barrelSet.add(infinityBarrel);
                }
                continue;
            }

            if (slimefunItem instanceof NetworkQuantumStorage) {
                final BlockMenu menu = BlockStorage.getInventory(testLocation);
                final NetworkStorage storage = getNetworkStorage(menu);
                if (storage != null) {
                    barrelSet.add(storage);
                }
            }

        }

        this.barrels = barrelSet;
        return barrelSet;
    }

    @Nullable
    private InfinityBarrel getInfinityBarrel(@Nonnull BlockMenu blockMenu, @Nonnull StorageUnit storageUnit) {
        final ItemStack itemStack = blockMenu.getItemInSlot(16);
        final Config config = BlockStorage.getLocationInfo(blockMenu.getLocation());
        final String storedString = config.getString("stored");

        if (storedString == null) {
            return null;
        }

        final int storedInt = Integer.parseInt(storedString);

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return null;
        }

        final io.github.mooy1.infinityexpansion.items.storage.StorageCache cache = storageUnit.getCache(blockMenu.getLocation());

        if (cache == null) {
            return null;
        }

        final ItemStack clone = itemStack.clone();
        clone.setAmount(1);

        return new InfinityBarrel(
            blockMenu.getLocation(),
            clone,
            storedInt + itemStack.getAmount(),
            cache
        );
    }

    @Nullable
    private NetworkStorage getNetworkStorage(@Nonnull BlockMenu blockMenu) {

        final QuantumCache cache = NetworkQuantumStorage.getCaches().get(blockMenu.getLocation());

        if (cache == null || cache.getItemStack() == null) {
            return null;
        }

        final ItemStack output = blockMenu.getItemInSlot(NetworkQuantumStorage.OUTPUT_SLOT);
        final ItemStack itemStack = cache.getItemStack();
        int storedInt = cache.getAmount();

        if (output != null && output.getType() != Material.AIR && StackUtils.itemsMatch(cache, output, true)) {
            storedInt = storedInt + output.getAmount();
        }

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return null;
        }

        final ItemStack clone = itemStack.clone();
        clone.setAmount(1);

        return new NetworkStorage(
            blockMenu.getLocation(),
            clone,
            storedInt
        );
    }

    @Nonnull
    public Set<BlockMenu> getCellMenus() {
        final Set<BlockMenu> menus = new HashSet<>();
        for (Location cellLocation : this.cells) {
            BlockMenu menu = BlockStorage.getInventory(cellLocation);
            if (menu != null) {
                menus.add(menu);
            }
        }
        return menus;
    }

    @Nonnull
    public Set<BlockMenu> getCrafterOutputs() {
        final Set<BlockMenu> menus = new HashSet<>();
        for (Location location : this.crafters) {
            BlockMenu menu = BlockStorage.getInventory(location);
            if (menu != null) {
                menus.add(menu);
            }
        }
        return menus;
    }

    @Nonnull
    public Set<BlockMenu> getGreedyBlocks() {
        final Set<BlockMenu> menus = new HashSet<>();
        for (Location location : this.greedyBlocks) {
            BlockMenu menu = BlockStorage.getInventory(location);
            if (menu != null) {
                menus.add(menu);
            }
        }
        return menus;
    }

    /**
     * Checks the Network's exposed items and removes items matching the request up
     * to the amount requested. Items are withdrawn in this order:
     * <p>
     * Cells
     * Withholding AutoCrafters
     * Deep Storages (Barrels)
     *
     * @param request The {@link ItemRequest} being requested from the Network
     * @return The {@link ItemStack} matching the request with as many as could be found. Null if none.
     */
    @Nullable
    public ItemStack getItemStack(@Nonnull ItemRequest request) {
        ItemStack stackToReturn = null;

        // Cells first
        for (BlockMenu blockMenu : getCellMenus()) {
            for (ItemStack itemStack : blockMenu.getContents()) {
                if (itemStack == null
                    || itemStack.getType() == Material.AIR
                    || !StackUtils.itemsMatch(request, itemStack, true)
                ) {
                    continue;
                }

                // Mark the Cell as dirty otherwise the changes will not save on shutdown
                blockMenu.markDirty();

                // If the return stack is null, we need to set it up
                if (stackToReturn == null) {
                    stackToReturn = itemStack.clone();
                    stackToReturn.setAmount(1);
                    request.receiveAmount(1);
                    itemStack.setAmount(itemStack.getAmount() - 1);
                }

                // Escape if fulfilled request
                if (request.getAmount() <= 0) {
                    return stackToReturn;
                }

                if (request.getAmount() <= itemStack.getAmount()) {
                    // We can't take more than this stack. Level to request amount, remove items and then return
                    stackToReturn.setAmount(stackToReturn.getAmount() + request.getAmount());
                    itemStack.setAmount(itemStack.getAmount() - request.getAmount());
                    return stackToReturn;
                } else {
                    // We can take more than what is here, consume before trying to take more
                    stackToReturn.setAmount(stackToReturn.getAmount() + itemStack.getAmount());
                    request.receiveAmount(itemStack.getAmount());
                    itemStack.setAmount(0);
                }
            }
        }

        // Crafters
        for (BlockMenu blockMenu : getCrafterOutputs()) {
            int[] slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
            for (int slot : slots) {
                final ItemStack itemStack = blockMenu.getItemInSlot(slot);
                if (itemStack == null || itemStack.getType() == Material.AIR || !StackUtils.itemsMatch(
                    request,
                    itemStack,
                    true
                )) {
                    continue;
                }

                // Stack is null, so we can fill it here
                if (stackToReturn == null) {
                    stackToReturn = itemStack.clone();
                    stackToReturn.setAmount(1);
                    request.receiveAmount(1);
                    itemStack.setAmount(itemStack.getAmount() - 1);
                }

                // Escape if fulfilled request
                if (request.getAmount() <= 0) {
                    return stackToReturn;
                }

                if (request.getAmount() <= itemStack.getAmount()) {
                    stackToReturn.setAmount(stackToReturn.getAmount() + request.getAmount());
                    itemStack.setAmount(itemStack.getAmount() - request.getAmount());
                    return stackToReturn;
                } else {
                    stackToReturn.setAmount(stackToReturn.getAmount() + itemStack.getAmount());
                    request.receiveAmount(itemStack.getAmount());
                    itemStack.setAmount(0);
                }
            }
        }

        // Greedy Blocks
        for (BlockMenu blockMenu : getGreedyBlocks()) {
            final ItemStack itemStack = blockMenu.getItemInSlot(NetworkGreedyBlock.INPUT_SLOT);
            if (itemStack == null
                || itemStack.getType() == Material.AIR
                || !StackUtils.itemsMatch(request, itemStack, true)
            ) {
                continue;
            }

            // Mark the Cell as dirty otherwise the changes will not save on shutdown
            blockMenu.markDirty();

            // If the return stack is null, we need to set it up
            if (stackToReturn == null) {
                stackToReturn = itemStack.clone();
                stackToReturn.setAmount(1);
                request.receiveAmount(1);
                itemStack.setAmount(itemStack.getAmount() - 1);
            }

            // Escape if fulfilled request
            if (request.getAmount() <= 0) {
                return stackToReturn;
            }

            if (request.getAmount() <= itemStack.getAmount()) {
                // We can't take more than this stack. Level to request amount, remove items and then return
                stackToReturn.setAmount(stackToReturn.getAmount() + request.getAmount());
                itemStack.setAmount(itemStack.getAmount() - request.getAmount());
                return stackToReturn;
            } else {
                // We can take more than what is here, consume before trying to take more
                stackToReturn.setAmount(stackToReturn.getAmount() + itemStack.getAmount());
                request.receiveAmount(itemStack.getAmount());
                itemStack.setAmount(0);
            }
        }

        // Barrels
        for (BarrelIdentity barrelIdentity : getBarrels()) {

            final ItemStack itemStack = barrelIdentity.getItemStack();

            if (itemStack == null || !StackUtils.itemsMatch(request, itemStack, true)) {
                continue;
            }

            boolean infinity = barrelIdentity instanceof InfinityBarrel;
            final ItemStack fetched = barrelIdentity.requestItem(request);
            if (fetched == null || fetched.getType() == Material.AIR || (infinity && fetched.getAmount() == 1)) {
                continue;
            }

            // Stack is null, so we can fill it here
            if (stackToReturn == null) {
                stackToReturn = fetched.clone();
                stackToReturn.setAmount(1);
                request.receiveAmount(1);
                fetched.setAmount(fetched.getAmount() - 1);
            }

            // Escape if fulfilled request
            if (request.getAmount() <= 0) {
                return stackToReturn;
            }

            final int preserveAmount = infinity ? fetched.getAmount() - 1 : fetched.getAmount();

            if (request.getAmount() <= preserveAmount) {
                stackToReturn.setAmount(stackToReturn.getAmount() + request.getAmount());
                fetched.setAmount(fetched.getAmount() - request.getAmount());
                return stackToReturn;
            } else {
                stackToReturn.setAmount(stackToReturn.getAmount() + preserveAmount);
                request.receiveAmount(preserveAmount);
                fetched.setAmount(fetched.getAmount() - preserveAmount);
            }

        }

        return stackToReturn;
    }

    public boolean contains(@Nonnull ItemRequest[] requests) {
        for (ItemRequest request : requests) {
            if (!contains(request)) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(@Nonnull ItemRequest request) {
        int found = 0;

        // Cells first
        for (BlockMenu blockMenu : getCellMenus()) {
            for (ItemStack itemStack : blockMenu.getContents()) {
                if (itemStack == null
                    || itemStack.getType() == Material.AIR
                    || !StackUtils.itemsMatch(request, itemStack, true)
                ) {
                    continue;
                }

                found += itemStack.getAmount();

                // Escape if found all we need
                if (found >= request.getAmount()) {
                    return true;
                }
            }
        }

        // Crafters
        for (BlockMenu blockMenu : getCrafterOutputs()) {
            int[] slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
            for (int slot : slots) {
                final ItemStack itemStack = blockMenu.getItemInSlot(slot);
                if (itemStack == null
                    || itemStack.getType() == Material.AIR
                    || !StackUtils.itemsMatch(request, itemStack, true)
                ) {
                    continue;
                }

                found += itemStack.getAmount();

                // Escape if found all we need
                if (found >= request.getAmount()) {
                    return true;
                }
            }
        }

        // Barrels
        for (BarrelIdentity barrelIdentity : getBarrels()) {
            final ItemStack itemStack = barrelIdentity.getItemStack();

            if (itemStack == null || !StackUtils.itemsMatch(request, itemStack, true)) {
                continue;
            }

            if (barrelIdentity instanceof InfinityBarrel) {
                if (barrelIdentity.getItemStack().getMaxStackSize() > 1) {
                    found += barrelIdentity.getAmount() - 2;
                }
            } else {
                found += barrelIdentity.getAmount();
            }

            // Escape if found all we need
            if (found >= request.getAmount()) {
                return true;
            }
        }

        // Greedy Blocks
        for (BlockMenu blockMenu : getGreedyBlocks()) {
            final ItemStack itemStack = blockMenu.getItemInSlot(NetworkGreedyBlock.INPUT_SLOT);
            if (itemStack == null
                || itemStack.getType() == Material.AIR
                || !StackUtils.itemsMatch(request, itemStack, true)
            ) {
                continue;
            }

            found += itemStack.getAmount();

            // Escape if found all we need
            if (found >= request.getAmount()) {
                return true;
            }
        }

        return false;
    }

    public void addItemStack(@Nonnull ItemStack incoming) {
        // Run for matching greedy blocks
        for (BlockMenu blockMenu : getGreedyBlocks()) {
            final ItemStack template = blockMenu.getItemInSlot(NetworkGreedyBlock.TEMPLATE_SLOT);

            if (template == null || template.getType() == Material.AIR || !StackUtils.itemsMatch(incoming, template)) {
                continue;
            }

            final ItemStack itemStack = blockMenu.getItemInSlot(NetworkGreedyBlock.INPUT_SLOT);

            if (itemStack == null || itemStack.getType() == Material.AIR) {
                blockMenu.replaceExistingItem(NetworkGreedyBlock.INPUT_SLOT, incoming.clone());
                incoming.setAmount(0);
                return;
            }

            final int itemStackAmount = itemStack.getAmount();
            final int incomingStackAmount = incoming.getAmount();
            if (itemStackAmount < itemStack.getMaxStackSize() && StackUtils.itemsMatch(itemStack, incoming)) {
                final int maxCanAdd = itemStack.getMaxStackSize() - itemStackAmount;
                final int amountToAdd = Math.min(maxCanAdd, incomingStackAmount);

                itemStack.setAmount(itemStackAmount + amountToAdd);
                incoming.setAmount(incomingStackAmount - amountToAdd);
            }
            // Given we have found a match, it doesn't matter if the item moved or not, we will not bring it in
            return;
        }

        // Run for matching barrels
        for (BarrelIdentity barrelIdentity : getBarrels()) {
            if (StackUtils.itemsMatch(barrelIdentity, incoming, true)) {

                barrelIdentity.depositItemStack(incoming);

                // All distributed, can escape
                if (incoming.getAmount() == 0) {
                    return;
                }
            }
        }

        // Then run for matching items in cells
        // Prepare a fallback menu and slot. This way we don't have to scan more than once
        BlockMenu fallbackBlockMenu = null;
        int fallBackSlot = 0;

        for (BlockMenu blockMenu : getCellMenus()) {
            int i = 0;
            for (ItemStack itemStack : blockMenu.getContents()) {
                // If this is an empty slot - move on, if it's our first, store it for later.
                if (itemStack == null || itemStack.getType().isAir()) {
                    if (fallbackBlockMenu == null) {
                        fallbackBlockMenu = blockMenu;
                        fallBackSlot = i;
                    }
                    continue;
                }

                final int itemStackAmount = itemStack.getAmount();
                final int incomingStackAmount = incoming.getAmount();

                if (itemStackAmount < itemStack.getMaxStackSize() && StackUtils.itemsMatch(incoming, itemStack)) {
                    final int maxCanAdd = itemStack.getMaxStackSize() - itemStackAmount;
                    final int amountToAdd = Math.min(maxCanAdd, incomingStackAmount);

                    itemStack.setAmount(itemStackAmount + amountToAdd);
                    incoming.setAmount(incomingStackAmount - amountToAdd);

                    // Mark dirty otherwise changes will not save
                    blockMenu.markDirty();

                    // All distributed, can escape
                    if (incomingStackAmount == 0) {
                        return;
                    }
                }
                i++;
            }
        }

        // Add to fallback slot
        if (fallbackBlockMenu != null) {
            fallbackBlockMenu.replaceExistingItem(fallBackSlot, incoming.clone());
            incoming.setAmount(0);
        }
    }

    @Override
    public long retrieveBlockCharge() {
        return 0;
    }

    public long getRootPower() {
        return this.rootPower;
    }

    public void setRootPower(long power) {
        this.rootPower = power;
    }

    public void addRootPower(long power) {
        this.rootPower += power;
    }

    public void removeRootPower(long power) {
        int removed = 0;
        for (Location node : powerNodes) {
            final SlimefunItem item = BlockStorage.check(node);
            if (item instanceof NetworkPowerNode powerNode) {
                final int charge = powerNode.getCharge(node);
                if (charge <= 0) {
                    continue;
                }
                final int toRemove = (int) Math.min(power - removed, charge);
                powerNode.removeCharge(node, toRemove);
                this.rootPower -= power;
                removed = removed + toRemove;
            }
            if (removed >= power) {
                return;
            }
        }
    }

    public boolean isDisplayParticles() {
        return displayParticles;
    }

    public void setDisplayParticles(boolean displayParticles) {
        this.displayParticles = displayParticles;
    }
}
