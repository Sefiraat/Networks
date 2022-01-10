package io.github.sefiraat.networks.slimefun.network.grid;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.GridItemRequest;
import io.github.sefiraat.networks.network.NetworkRoot;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.slimefun.NetworkSlimefunItems;
import io.github.sefiraat.networks.slimefun.network.NetworkObject;
import io.github.sefiraat.networks.utils.StackUtils;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.ActionType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkGrid extends NetworkObject {

    private static final int[] BACKGROUND_SLOTS = {
        17, 26, 35
    };

    private static final int[] DISPLAY_SLOTS = {
        0, 1, 2, 3, 4, 5, 6, 7,
        9, 10, 11, 12, 13, 14, 15, 16,
        18, 19, 20, 21, 22, 23, 24, 25,
        27, 28, 29, 30, 31, 32, 33, 34,
        36, 37, 38, 39, 40, 41, 42, 43,
        45, 46, 47, 48, 49, 50, 51, 52
    };

    private static final int INPUT_SLOT = 8;

    private static final int CHANGE_SORT = 26;
    private static final int PAGE_PREVIOUS = 44;
    private static final int PAGE_NEXT = 53;

    private static final ItemStack BLANK_SLOT_STACK = new CustomItemStack(
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        " "
    );

    private static final CustomItemStack CHANGE_SORT_STACK = new CustomItemStack(
        Material.BLUE_STAINED_GLASS_PANE,
        Theme.CLICK_INFO.getColor() + "Change Sort Order"
    );

    private static final CustomItemStack PAGE_PREVIOUS_STACK = new CustomItemStack(
        Material.RED_STAINED_GLASS_PANE,
        Theme.CLICK_INFO.getColor() + "Previous Page"
    );

    private static final CustomItemStack PAGE_NEXT_STACK = new CustomItemStack(
        Material.RED_STAINED_GLASS_PANE,
        Theme.CLICK_INFO.getColor() + "Next Page"
    );

    private static final Map<Location, GridCache> CACHE_MAP = new HashMap<>();

    private static final Comparator<Map.Entry<ItemStack, Integer>> ALPHABETICAL_SORT = Comparator.comparing(
        itemStackIntegerEntry -> {
            ItemStack itemStack = itemStackIntegerEntry.getKey();
            SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
            if (slimefunItem != null) {
                return ChatColor.stripColor(slimefunItem.getItemName());
            } else {
                ItemMeta itemMeta = itemStackIntegerEntry.getKey().getItemMeta();
                return itemMeta.hasDisplayName()
                    ? ChatColor.stripColor(itemMeta.getDisplayName())
                    : itemStackIntegerEntry.getKey().getType().name();
            }
        }
    );

    private static final Comparator<Map.Entry<ItemStack, Integer>> NUMERICAL_SORT = Map.Entry.comparingByValue();

    private final ItemSetting<Integer> tickRate;

    public NetworkGrid(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, NodeType.GRID);

        this.getSlotsToDrop().add(INPUT_SLOT);

        this.tickRate = new IntRangeSetting(this, "tick_rate", 1, 1, 10);
        addItemSetting(this.tickRate);

        addItemHandler(
            new BlockTicker() {

                private int tick = 1;

                @Override
                public boolean isSynchronized() {
                    return false;
                }

                @Override
                public void tick(Block block, SlimefunItem item, Config data) {
                    if (tick <= 1) {
                        final BlockMenu blockMenu = BlockStorage.getInventory(block);
                        addToRegistry(block);
                        tryAddItem(blockMenu);
                        updateDisplay(blockMenu);
                    }
                }

                @Override
                public void uniqueTick() {
                    tick = tick <= 1 ? tickRate.getValue() : tick - 1;
                }
            }
        );
    }

    private void tryAddItem(@Nonnull BlockMenu blockMenu) {
        final ItemStack itemStack = blockMenu.getItemInSlot(INPUT_SLOT);

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());
        if (definition.getNode() == null) {
            return;
        }

        definition.getNode().getRoot().addItemStack(itemStack);
    }

    void updateDisplay(@Nonnull BlockMenu blockMenu) {
        if (blockMenu.hasViewer()) {
            final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());

            if (definition.getNode() == null) {
                return;
            }

            // Update Screen
            NetworkRoot root = definition.getNode().getRoot();

            GridCache gridCache = CACHE_MAP.get(blockMenu.getLocation());

            final List<Map.Entry<ItemStack, Integer>> entries = root.getAllNetworkItems().entrySet().stream()
                .sorted(gridCache.getSortOrder() == GridCache.SortOrder.ALPHABETICAL ? ALPHABETICAL_SORT : NUMERICAL_SORT.reversed())
                .toList();

            final int pages = (int) Math.ceil(entries.size() / (double) DISPLAY_SLOTS.length) - 1;

            if (pages < 0) {
                for (int displaySlot : DISPLAY_SLOTS) {
                    blockMenu.replaceExistingItem(displaySlot, BLANK_SLOT_STACK);
                    blockMenu.addMenuClickHandler(displaySlot, (p, slot, item, action) -> false);
                }
                return;
            }

            final int page = gridCache.getPage();

            final int start = page * DISPLAY_SLOTS.length;
            final int end = Math.min(start + DISPLAY_SLOTS.length, entries.size());
            final List<Map.Entry<ItemStack, Integer>> validEntries = entries.subList(start, end);

            gridCache.setMaxPages(pages);
            CACHE_MAP.put(blockMenu.getLocation(), gridCache);

            for (int i = 0; i < DISPLAY_SLOTS.length; i++) {
                if (validEntries.size() > i) {
                    final Map.Entry<ItemStack, Integer> entry = validEntries.get(i);
                    final ItemStack displayStack = entry.getKey().clone();
                    final ItemMeta itemMeta = displayStack.getItemMeta();
                    List<String> lore = itemMeta.getLore();

                    if (lore == null) {
                        lore = getLoreAddition(entry.getValue());
                    } else {
                        lore.addAll(getLoreAddition(entry.getValue()));
                    }

                    itemMeta.setLore(lore);
                    displayStack.setItemMeta(itemMeta);
                    blockMenu.replaceExistingItem(DISPLAY_SLOTS[i], displayStack);
                    blockMenu.addMenuClickHandler(DISPLAY_SLOTS[i], (player, slot, item, action) ->
                        retrieveItem(player, definition, item, action)
                    );
                } else {
                    blockMenu.replaceExistingItem(DISPLAY_SLOTS[i], BLANK_SLOT_STACK);
                    blockMenu.addMenuClickHandler(DISPLAY_SLOTS[i], (p, slot, item, action) -> false);
                }
            }
        }
    }

    @Nonnull
    static List<String> getLoreAddition(int amount) {
        return List.of(
            "",
            MessageFormat.format("{0}Amount: {1}{2}", Theme.CLICK_INFO.getColor(), Theme.PASSIVE.getColor(), amount)
        );
    }

    @ParametersAreNonnullByDefault
    boolean retrieveItem(Player player, NodeDefinition definition, ItemStack itemStack, ClickAction action) {
        final ItemStack clone = itemStack.clone();
        final ItemMeta cloneMeta = clone.getItemMeta();
        final List<String> cloneLore = cloneMeta.getLore();

        cloneLore.remove(cloneLore.size() - 1);
        cloneLore.remove(cloneLore.size() - 1);
        cloneMeta.setLore(cloneLore);
        clone.setItemMeta(cloneMeta);
        int amount = 1;

        if (action.isRightClicked()) {
            amount = clone.getMaxStackSize();
        }

        final GridItemRequest request = new GridItemRequest(clone, amount, player);
        final ItemStack cursor = player.getItemOnCursor();

        // Quickly check if the cursor has an item and if we can add more to it
        if (cursor.getType() != Material.AIR && !canAddMore(action, cursor, request)) {
            return false;
        }

        final ItemStack requestingStack = definition.getNode().getRoot().getItemStack(request);
        if (requestingStack != null) {
            if (cursor.getType() != Material.AIR) {
                requestingStack.setAmount(cursor.getAmount() + 1);
            }
            request.getPlayer().setItemOnCursor(requestingStack);
        }

        return false;
    }

    private boolean canAddMore(@Nonnull ClickAction action, @Nonnull ItemStack cursor, @Nonnull GridItemRequest request) {
        return !action.isRightClicked()
            && request.getAmount() == 1
            && cursor.getAmount() < cursor.getMaxStackSize()
            && StackUtils.itemsMatch(request, cursor);
    }

    @Override
    public void postRegister() {
        new BlockMenuPreset(this.getId(), this.getItemName()) {

            @Override
            public void init() {
                drawBackground(BACKGROUND_SLOTS);
                setSize(54);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return NetworkSlimefunItems.NETWORK_GRID.canUse(player, false)
                    && Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[0];
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                CACHE_MAP.put(menu.getLocation(), new GridCache(0, 0, GridCache.SortOrder.ALPHABETICAL));

                menu.replaceExistingItem(PAGE_PREVIOUS, PAGE_PREVIOUS_STACK);
                menu.addMenuClickHandler(PAGE_PREVIOUS, (p, slot, item, action) -> {
                    GridCache gridCache = CACHE_MAP.get(menu.getLocation());
                    gridCache.setPage(gridCache.getPage() <= 0 ? 0 : gridCache.getPage() - 1);
                    CACHE_MAP.put(menu.getLocation(), gridCache);
                    return false;
                });

                menu.replaceExistingItem(PAGE_NEXT, PAGE_NEXT_STACK);
                menu.addMenuClickHandler(PAGE_NEXT, (p, slot, item, action) -> {
                    GridCache gridCache = CACHE_MAP.get(menu.getLocation());
                    gridCache.setPage(gridCache.getPage() >= gridCache.getMaxPages() ? gridCache.getMaxPages() : gridCache.getPage() + 1);
                    CACHE_MAP.put(menu.getLocation(), gridCache);
                    return false;
                });

                menu.replaceExistingItem(CHANGE_SORT, CHANGE_SORT_STACK);
                menu.addMenuClickHandler(CHANGE_SORT, (p, slot, item, action) -> {
                    GridCache gridCache = CACHE_MAP.get(menu.getLocation());
                    if (gridCache.getSortOrder() == GridCache.SortOrder.ALPHABETICAL) {
                        gridCache.setSortOrder(GridCache.SortOrder.NUMBER);
                    } else {
                        gridCache.setSortOrder(GridCache.SortOrder.ALPHABETICAL);
                    }
                    CACHE_MAP.put(menu.getLocation(), gridCache);
                    return false;
                });

                for (int displaySlot : DISPLAY_SLOTS) {
                    menu.replaceExistingItem(displaySlot, null);
                    menu.addMenuClickHandler(displaySlot, (p, slot, item, action) -> false);
                }
            }
        };
    }
}
