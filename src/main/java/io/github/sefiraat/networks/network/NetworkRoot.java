package io.github.sefiraat.networks.network;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NetworkRoot extends NetworkNode {

    protected static final List<String> BARREL_INFINITY = List.of(
        "BASIC_STORAGE",
        "ADVANCED_STORAGE",
        "REINFORCED_STORAGE",
        "VOID_STORAGE",
        "INFINITY_STORAGE"
    );

    protected static final List<String> BARREL_FLUFFY = List.of(
        "SMALL_FLUFFY_BARREL",
        "MEDIUM_FLUFFY_BARREL",
        "BIG_FLUFFY_BARREL",
        "LARGE_FLUFFY_BARREL",
        "MASSIVE_FLUFFY_BARREL",
        "BOTTOMLESS_FLUFFY_BARREL"
    );

    protected final Set<Location> networkLocations = new HashSet<>();
    protected final Set<Location> networkBridges = new HashSet<>();
    protected final Set<Location> networkMonitors = new HashSet<>();
    protected final Set<Location> networkCells = new HashSet<>();
    protected final Set<Location> networkExporters = new HashSet<>();
    protected final Set<Location> networkImporters = new HashSet<>();

    public NetworkRoot(Location location, NodeType type) {
        super(location, type);
        this.root = this;
    }

    public void addNode(Location location, NodeType type) {
        networkLocations.add(location);
        switch (type) {
            case BRIDGE -> networkBridges.add(location);
            case STORAGE_MONITOR -> networkMonitors.add(location);
            case IMPORT -> networkImporters.add(location);
            case EXPORT -> networkExporters.add(location);
            case CELL -> networkCells.add(location);
            default -> {
                // Not required
            }
        }
    }

    public Set<Location> getBridges() {
        return networkBridges;
    }

    public Set<Location> getMonitors() {
        return networkMonitors;
    }

    public Set<Location> getCells() {
        return networkCells;
    }

    public Set<Location> getExports() {
        return networkExporters;
    }

    public Set<Location> getImports() {
        return networkImporters;
    }

    public Map<ItemStack, Integer> getAllNetworkItems() {
        final Map<ItemStack, Integer> itemStacks = new LinkedHashMap<>();

        for (BarrelIdentity barrelIdentity : getBarrelItems().values()) {
            itemStacks.put(barrelIdentity.getItemStack(), barrelIdentity.getAmount());
        }

        for (BlockMenu blockMenu : getCellMenus()) {
            for (ItemStack itemStack : blockMenu.getContents()) {
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    final ItemStack clone = itemStack.clone();

                    clone.setAmount(1);

                    final Integer currentAmount = itemStacks.get(clone);
                    final int newAmount = currentAmount == null ? itemStack.getAmount() : currentAmount + itemStack.getAmount();

                    itemStacks.put(clone, newAmount);
                }
            }
        }
        return itemStacks;
    }

    public Map<ItemStack, BarrelIdentity> getBarrelItems() {
        final Map<ItemStack, BarrelIdentity> barrelItemMap = new LinkedHashMap<>();
        for (Location cellLocation : networkMonitors) {
            for (BlockFace face : VALID_FACES) {
                final Location testLocation = cellLocation.clone().add(face.getDirection());
                final BlockMenu menu = BlockStorage.getInventory(testLocation);
                if (menu != null) {
                    final Config config = BlockStorage.getLocationInfo(testLocation);
                    final String id = config.getString("id");

                    // final boolean fluffy = BARREL_FLUFFY.contains(id);  TODO add fluffy when workaround 2 slots
                    final boolean infinity = BARREL_INFINITY.contains(id);

                    if (infinity) {
                        final ItemStack itemStack = menu.getItemInSlot(16);

                        if (itemStack == null || itemStack.getType() == Material.AIR) {
                            continue;
                        }

                        final ItemStack clone = itemStack.clone();
                        final String storedString = config.getString("stored");
                        final int storedInt = Integer.parseInt(storedString);

                        clone.setAmount(1);
                        BarrelIdentity identity = new BarrelIdentity(menu.getLocation(), clone, storedInt + itemStack.getAmount(), 10, 16);
                        barrelItemMap.put(clone, identity);
                    }
                }
            }
        }
        return barrelItemMap;
    }

    public Set<BlockMenu> getCellMenus() {
        final Set<BlockMenu> menus = new HashSet<>();
        for (Location cellLocation : networkCells) {
            BlockMenu menu = BlockStorage.getInventory(cellLocation);
            if (menu != null) {
                menus.add(menu);
            }
        }
        return menus;
    }

    @Nullable
    public ItemStack getItemStack(GridItemRequest request) {
        ItemStack requestedStack = null;

        for (BlockMenu blockMenu : getCellMenus()) {
            for (ItemStack itemStack : blockMenu.getContents()) {
                if (itemStack != null
                    && itemStack.getType() != Material.AIR
                    && SlimefunUtils.isItemSimilar(request.getItemStack(), itemStack, true, false)
                ) {
                    // Stack is null, so we can fill it here
                    if (requestedStack == null) {
                        requestedStack = itemStack.clone();
                        requestedStack.setAmount(1);
                        request.receiveAmount(1);
                        itemStack.setAmount(itemStack.getAmount() - 1);
                    }

                    // Escape if fulfilled request
                    if (request.getAmount() <= 0) {
                        return requestedStack;
                    }

                    if (request.getAmount() <= itemStack.getAmount()) {
                        requestedStack.setAmount(requestedStack.getAmount() + request.getAmount());
                        itemStack.setAmount(itemStack.getAmount() - request.getAmount());
                        return requestedStack;
                    } else {
                        requestedStack.setAmount(requestedStack.getAmount() + itemStack.getAmount());
                        request.receiveAmount(itemStack.getAmount());
                        itemStack.setAmount(0);
                    }
                }
            }
        }

        for (BarrelIdentity barrelIdentity : getBarrelItems().values()) {
            if (SlimefunUtils.isItemSimilar(request.getItemStack(), barrelIdentity.getItemStack(), true, false)) {
                final BlockMenu menu = BlockStorage.getInventory(barrelIdentity.getLocation());
                final ItemStack itemStack = menu.getItemInSlot(barrelIdentity.getOutputSlot());

                if (SlimefunUtils.isItemSimilar(request.getItemStack(), itemStack, true, false)) {

                    if (itemStack.getAmount() == 1) {
                        continue;
                    }

                    // Stack is null, so we can fill it here
                    if (requestedStack == null) {
                        requestedStack = itemStack.clone();
                        requestedStack.setAmount(1);
                        request.receiveAmount(1);
                        itemStack.setAmount(itemStack.getAmount() - 1);
                    }

                    // Escape if fulfilled request
                    if (request.getAmount() <= 0) {
                        return requestedStack;
                    }

                    final int preserveAmount = itemStack.getAmount() - 1;

                    if (request.getAmount() <= preserveAmount) {
                        requestedStack.setAmount(requestedStack.getAmount() + request.getAmount());
                        itemStack.setAmount(itemStack.getAmount() - request.getAmount());
                        return requestedStack;
                    } else {
                        requestedStack.setAmount(requestedStack.getAmount() + preserveAmount);
                        request.receiveAmount(preserveAmount);
                        itemStack.setAmount(1);
                    }
                }
            }
        }

        return requestedStack;
    }

    public void addItemStack(ItemStack incomingStack) {
        // Run for matching barrels
        for (BarrelIdentity barrelIdentity : getBarrelItems().values()) {
            if (SlimefunUtils.isItemSimilar(incomingStack, barrelIdentity.getItemStack(), true, false)) {
                final BlockMenu menu = BlockStorage.getInventory(barrelIdentity.getLocation());
                final ItemStack itemStack = menu.getItemInSlot(barrelIdentity.getInputSlot());

                if (SlimefunUtils.isItemSimilar(incomingStack, itemStack, true, false)
                    && itemStack.getAmount() < itemStack.getMaxStackSize()
                ) {
                    final int maxCanAdd = itemStack.getMaxStackSize() - itemStack.getAmount();
                    final int amountToAdd = Math.min(maxCanAdd, incomingStack.getAmount());
                    itemStack.setAmount(itemStack.getAmount() + amountToAdd);
                    incomingStack.setAmount(incomingStack.getAmount() - amountToAdd);
                } else {
                    menu.replaceExistingItem(barrelIdentity.getInputSlot(), incomingStack.clone());
                    incomingStack.setAmount(0);
                }

                // All distributed, can escape
                if (incomingStack.getAmount() == 0) {
                    return;
                }
            }
        }

        // Then run for matching items in cells
        for (BlockMenu blockMenu : getCellMenus()) {
            for (ItemStack itemStack : blockMenu.getContents()) {
                if (itemStack != null
                    && itemStack.getType() != Material.AIR
                    && SlimefunUtils.isItemSimilar(incomingStack, itemStack, true, false)
                    && itemStack.getAmount() < itemStack.getMaxStackSize()
                ) {
                    final int maxCanAdd = itemStack.getMaxStackSize() - itemStack.getAmount();
                    final int amountToAdd = Math.min(maxCanAdd, incomingStack.getAmount());
                    itemStack.setAmount(itemStack.getAmount() + amountToAdd);
                    incomingStack.setAmount(incomingStack.getAmount() - amountToAdd);

                    // All distributed, can escape
                    if (incomingStack.getAmount() == 0) {
                        return;
                    }
                }
            }
        }

        // Now run for empty slots
        for (BlockMenu blockMenu : getCellMenus()) {
            int i = 0;
            for (ItemStack itemStack : blockMenu.getContents()) {
                if (itemStack == null || itemStack.getType() == Material.AIR) {
                    blockMenu.replaceExistingItem(i, incomingStack.clone());
                    incomingStack.setAmount(0);
                    return;
                }
                i++;
            }
        }
    }

//    public Set<Container> getConnectedVanillaInventories() {
//        final Set<Container> containers = new HashSet<>();
//        for (Location monitorLocation : networkMonitors) {
//            for (BlockFace face : VALID_FACES) {
//                final Block block = monitorLocation.clone().add(face.getDirection()).getBlock();
//                if (VALID_CONTAINERS.contains(block.getType())) {
//                    containers.add((Container) block.getState());
//                }
//            }
//        }
//        return containers;
//    }
//
//    public Set<Block> getConnectedContainersAsBlocks() {
//        final Set<Block> blocks = new HashSet<>();
//        for (Location monitorLocation : networkMonitors) {
//            for (BlockFace face : VALID_FACES) {
//                final Block block = monitorLocation.clone().add(face.getDirection()).getBlock();
//                if (VALID_CONTAINERS.contains(block.getType())) {
//                    blocks.add(block);
//                }
//            }
//        }
//        return blocks;
//    }
//
//    public Map<ItemStack, Integer> getConnectedVanillaInventoryItemStacks() {
//        final Map<ItemStack, Integer> itemStacks = new HashMap<>();
//
//        for (Container container : getConnectedVanillaInventories()) {
//            for (ItemStack itemStack : container.getInventory().getContents()) {
//                if (itemStack != null && itemStack.getType() != Material.AIR) {
//                    final ItemStack clone = itemStack.clone();
//                    final int maxAmountCaredFor = (clone.getMaxStackSize() * 2) + 1;
//
//                    clone.setAmount(1);
//
//                    final Integer currentAmount = itemStacks.get(clone);
//                    final int newAmount;
//
//                    if (currentAmount == null) {
//                        newAmount = itemStack.getAmount();
//                    } else {
//                        // TODO - Try to remove this, its not user friendly but is more performant.
//                        if (currentAmount >= maxAmountCaredFor) {
//                            continue;
//                        }
//                        newAmount = currentAmount + itemStack.getAmount();
//                    }
//                    itemStacks.put(clone, newAmount);
//                }
//            }
//        }
//        return itemStacks;
//    }
//
//    @Nullable
//    public ItemStack getItemStack(GridItemRequest request) {
//        ItemStack requestedStack = null;
//
//        for (Container container : getConnectedVanillaInventories()) {
//            for (ItemStack itemStack : container.getInventory().getContents()) {
//                if (itemStack != null
//                    && itemStack.getType() != Material.AIR
//                    && SlimefunUtils.isItemSimilar(request.getItemStack(), itemStack, true, false)
//                ) {
//                    // Stack is null, so we can fill it here
//                    if (requestedStack == null) {
//                        requestedStack = itemStack.clone();
//                        requestedStack.setAmount(1);
//                        request.receiveAmount(1);
//                        itemStack.setAmount(itemStack.getAmount() - 1);
//                    }
//
//                    // Escape if fulfilled request
//                    if (request.getAmount() <= 0) {
//                        return requestedStack;
//                    }
//
//                    if (request.getAmount() <= itemStack.getAmount()) {
//                        requestedStack.setAmount(requestedStack.getAmount() + request.getAmount());
//                        itemStack.setAmount(itemStack.getAmount() - request.getAmount());
//                        return requestedStack;
//                    } else {
//                        requestedStack.setAmount(requestedStack.getAmount() + itemStack.getAmount());
//                        request.receiveAmount(itemStack.getAmount());
//                        itemStack.setAmount(0);
//                    }
//                }
//            }
//        }
//        return requestedStack;
//    }
//
//    public void addItemStack(ItemStack incomingStack) {
//        // Run one for matching items first
//        for (Container container : getConnectedVanillaInventories()) {
//            for (ItemStack itemStack : container.getInventory().getContents()) {
//                if (itemStack != null
//                    && itemStack.getType() != Material.AIR
//                    && SlimefunUtils.isItemSimilar(incomingStack, itemStack, true, false)
//                    && itemStack.getAmount() < itemStack.getMaxStackSize()
//                ) {
//                    final int maxCanAdd = itemStack.getMaxStackSize() - itemStack.getAmount();
//                    final int amountToAdd = Math.min(maxCanAdd, incomingStack.getAmount());
//                    itemStack.setAmount(itemStack.getAmount() + amountToAdd);
//                    incomingStack.setAmount(incomingStack.getAmount() - amountToAdd);
//
//                    // All distributed, can escape
//                    if (incomingStack.getAmount() == 0) {
//                        return;
//                    }
//                }
//            }
//        }
//
//        // Now run for empty slots
//        for (Container container : getConnectedVanillaInventories()) {
//            int i = 0;
//            for (ItemStack itemStack : container.getInventory().getContents()) {
//                if (itemStack == null || itemStack.getType() == Material.AIR) {
//                    container.getInventory().setItem(i, incomingStack);
//                    incomingStack.setAmount(0);
//                    return;
//                }
//                i++;
//            }
//        }
//    }

}
