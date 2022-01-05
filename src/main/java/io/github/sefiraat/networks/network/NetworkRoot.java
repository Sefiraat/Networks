package io.github.sefiraat.networks.network;

import io.github.mooy1.infinityexpansion.items.storage.StorageUnit;
import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.network.barrel.BarrelIdentity;
import io.github.sefiraat.networks.network.barrel.BarrelType;
import io.github.sefiraat.networks.network.barrel.InfinityBarrel;
import io.github.sefiraat.networks.network.barrel.NetworkShell;
import io.github.sefiraat.networks.slimefun.network.NetworkMemoryShell;
import io.github.sefiraat.networks.slimefun.tools.CardInstance;
import io.github.sefiraat.networks.slimefun.tools.NetworkCard;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.sefiraat.networks.utils.datatypes.PersistentCardInstanceType;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NetworkRoot extends NetworkNode {

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

    @Nonnull
    public Map<BlockMenu, BarrelType> getNetworkBarrels() {
        final Map<BlockMenu, BarrelType> menus = new HashMap<>();
        for (Location cellLocation : networkMonitors) {
            for (BlockFace face : VALID_FACES) {
                final Location testLocation = cellLocation.clone().add(face.getDirection());
                final SlimefunItem slimefunItem = BlockStorage.check(testLocation);

                if (Networks.getSupportedPluginManager().isInfinityExpansion()
                    && slimefunItem instanceof StorageUnit
                ) {
                    BlockMenu menu = BlockStorage.getInventory(testLocation);
                    menus.put(menu, BarrelType.INFINITY);
                    continue;
                }

                if (slimefunItem instanceof NetworkMemoryShell) {
                    BlockMenu menu = BlockStorage.getInventory(testLocation);
                    menus.put(menu, BarrelType.NETWORKS);
                }
            }
        }
        return menus;
    }

    @Nonnull
    public Map<ItemStack, Integer> getAllNetworkItems() {
        final Map<ItemStack, Integer> itemStacks = new HashMap<>();

        for (BarrelIdentity barrelIdentity : getBarrels()) {
            final Integer currentAmount = itemStacks.get(barrelIdentity.getReferenceStack());
            final int newAmount = currentAmount == null ? barrelIdentity.getAmount() : currentAmount + barrelIdentity.getAmount();
            itemStacks.put(barrelIdentity.getReferenceStack(), newAmount);
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

    @Nonnull
    public Set<BarrelIdentity> getBarrels() {
        final Set<BarrelIdentity> barrelItemMap = new HashSet<>();

        for (Location cellLocation : networkMonitors) {
            for (BlockFace face : VALID_FACES) {
                final Location testLocation = cellLocation.clone().add(face.getDirection());
                final SlimefunItem slimefunItem = BlockStorage.check(testLocation);

                if (Networks.getSupportedPluginManager().isInfinityExpansion()
                    && slimefunItem instanceof StorageUnit unit
                ) {
                    BlockMenu menu = BlockStorage.getInventory(testLocation);
                    InfinityBarrel infinityBarrel = getInfinityBarrel(menu, unit);
                    if (infinityBarrel != null) {
                        barrelItemMap.add(infinityBarrel);
                    }
                    continue;
                }

                if (slimefunItem instanceof NetworkMemoryShell) {
                    BlockMenu menu = BlockStorage.getInventory(testLocation);
                    NetworkShell shell = getShell(menu);
                    if (shell != null) {
                        barrelItemMap.add(shell);
                    }
                }
            }
        }

        return barrelItemMap;
    }

    @Nullable
    private InfinityBarrel getInfinityBarrel(@Nonnull BlockMenu blockMenu, @Nonnull StorageUnit storageUnit) {
        final ItemStack itemStack = blockMenu.getItemInSlot(16);
        final Config config = BlockStorage.getLocationInfo(blockMenu.getLocation());
        final String storedString = config.getString("stored");
        final int storedInt = Integer.parseInt(storedString);

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return null;
        }

        final ItemStack clone = itemStack.clone();
        clone.setAmount(1);

        return new InfinityBarrel(
            blockMenu.getLocation(),
            clone,
            storedInt + itemStack.getAmount(),
            storageUnit.getCache(blockMenu.getLocation())
        );
    }

    @Nullable
    private NetworkShell getShell(@Nonnull BlockMenu blockMenu) {

        final ItemStack card = blockMenu.getItemInSlot(NetworkMemoryShell.CARD_SLOT);
        final ItemStack output = blockMenu.getItemInSlot(NetworkMemoryShell.OUTPUT_SLOT);
        final SlimefunItem cardItem = SlimefunItem.getByItem(card);

        if (cardItem instanceof NetworkCard) {
            ItemMeta itemMeta = card.getItemMeta();
            CardInstance instance = DataTypeMethods.getCustom(itemMeta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE);

            if (instance == null || instance.getItemStack() == null) {
                return null;
            }

            final ItemStack itemStack = instance.getItemStack();
            int storedInt = instance.getAmount();

            if (SlimefunUtils.isItemSimilar(output, instance.getItemStack(), true)) {
                storedInt = storedInt + output.getAmount();
            }

            if (itemStack == null || itemStack.getType() == Material.AIR) {
                return null;
            }

            final ItemStack clone = itemStack.clone();
            clone.setAmount(1);

            return new NetworkShell(
                blockMenu.getLocation(),
                clone,
                storedInt
            );
        }

        return null;
    }

    @Nonnull
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
    public ItemStack getItemStack(@Nonnull ItemRequest request) {
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

        for (BarrelIdentity barrelIdentity : getBarrels()) {
            if (barrelIdentity.holdsMatchingItem(request.getItemStack())) {
                final ItemStack itemStack = barrelIdentity.requestItem(request.getItemStack());
                boolean infinity = barrelIdentity instanceof InfinityBarrel;

                if (itemStack == null
                    || (infinity && itemStack.getAmount() == 1)
                    || !SlimefunUtils.isItemSimilar(request.getItemStack(), itemStack, true, false)
                ) {
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

                final int preserveAmount = infinity ? itemStack.getAmount() - 1 : itemStack.getAmount();

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

        return requestedStack;
    }

    public void addItemStack(@Nonnull ItemStack incomingStack) {
        // Run for matching barrels
        for (BarrelIdentity barrelIdentity : getBarrels()) {
            if (SlimefunUtils.isItemSimilar(incomingStack, barrelIdentity.getReferenceStack(), true, false)) {

                barrelIdentity.depositItemStack(incomingStack);

                // All distributed, can escape
                if (incomingStack.getAmount() == 0) {
                    return;
                }
            }
        }

        // Then run for matching items in cells
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
                final int incomingStackAmount = incomingStack.getAmount();

                if (itemStackAmount < itemStack.getMaxStackSize()
                    && SlimefunUtils.isItemSimilar(incomingStack, itemStack, true, false)
                ) {
                    final int maxCanAdd = itemStack.getMaxStackSize() - itemStackAmount;
                    final int amountToAdd = Math.min(maxCanAdd, incomingStackAmount);

                    itemStack.setAmount(itemStackAmount + amountToAdd);
                    incomingStack.setAmount(incomingStackAmount - amountToAdd);

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
            fallbackBlockMenu.replaceExistingItem(fallBackSlot, incomingStack.clone());
            incomingStack.setAmount(0);
        }
    }
}
