package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.GridItemRequest;
import io.github.sefiraat.networks.network.NetworkRoot;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.slimefun.NetworkSlimefunItems;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
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

    private static final int PAGE_PREVIOUS = 44;
    private static final int PAGE_NEXT = 53;

    private static final ItemStack BLANK_SLOT_STACK = new CustomItemStack(
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        " "
    );

    private static final CustomItemStack PAGE_PREVIOUS_STACK = new CustomItemStack(
        Material.RED_STAINED_GLASS_PANE,
        Theme.CLICK_INFO.getColor() + "Previous Page"
    );

    private static final CustomItemStack PAGE_NEXT_STACK = new CustomItemStack(
        Material.RED_STAINED_GLASS_PANE,
        Theme.CLICK_INFO.getColor() + "Next Page"
    );

    private static final Map<Location, Integer> PAGE_MAP = new HashMap<>();
    private static final Map<Location, Integer> MAX_PAGE_MAP = new HashMap<>();

    private final ItemSetting<Integer> tickRate;

    public NetworkGrid(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, NodeType.GRID);

        this.tickRate = new IntRangeSetting(this, "tick_rate", 1, 1, 10);
        addItemSetting(this.tickRate);

        addItemHandler(
            new BlockTicker() {

                private int tick = 1;

                @Override
                public boolean isSynchronized() {
                    return true;
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
        final long startTime = System.nanoTime();
        final ItemStack itemStack = blockMenu.getItemInSlot(INPUT_SLOT);

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());
        if (definition.getNode() == null) {
            return;
        }

        definition.getNode().getRoot().addItemStack(itemStack);
        long finishTime = System.nanoTime();
        String message = MessageFormat.format("{0}Trying to add item: {1}", Theme.CLICK_INFO.getColor(), finishTime - startTime);
        // blockMenu.toInventory().getViewers().get(0).sendMessage(message);
    }

    void updateDisplay(@Nonnull BlockMenu blockMenu) {
        if (blockMenu.hasViewer()) {
            final long startTime = System.nanoTime();
            final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());

            if (definition.getNode() == null) {
                return;
            }

            // Update Screen
            NetworkRoot root = definition.getNode().getRoot();

            final List<Map.Entry<ItemStack, Integer>> entries = root.getAllNetworkItems().entrySet().stream()
                .sorted(
                    Comparator.comparing(itemStackIntegerEntry -> {
                        ItemMeta itemMeta = itemStackIntegerEntry.getKey().getItemMeta();
                        return itemMeta.hasDisplayName()
                            ? ChatColor.stripColor(itemMeta.getDisplayName())
                            : itemStackIntegerEntry.getKey().getType().name();
                    }))
                .toList();

            final int pages = (int) Math.ceil(entries.size() / (double) DISPLAY_SLOTS.length) - 1;
            final int page = PAGE_MAP.getOrDefault(blockMenu.getLocation(), 0);

            final int start = page * DISPLAY_SLOTS.length;
            final int end = Math.min(start + DISPLAY_SLOTS.length, entries.size());
            final List<Map.Entry<ItemStack, Integer>> validEntries = entries.subList(start, end);

            MAX_PAGE_MAP.put(blockMenu.getLocation(), pages);

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
            long finishTime = System.nanoTime();
            String message = MessageFormat.format("{0}Updating Display: {1}", Theme.CLICK_INFO.getColor(), finishTime - startTime);
            // blockMenu.toInventory().getViewers().get(0).sendMessage(message);
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
        final long startTime = System.nanoTime();
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

        final GridItemRequest request = new GridItemRequest(clone, player, amount);

        // Process item request
        if (player.getItemOnCursor().getType() == Material.AIR) {
            ItemStack requestingStack = definition.getNode().getRoot().getItemStack(request);
            if (requestingStack != null) {
                request.getPlayer().setItemOnCursor(requestingStack);
            }
        }
        long finishTime = System.nanoTime();
        String message = MessageFormat.format("{0}Retrieving Item: {1}", Theme.CLICK_INFO.getColor(), finishTime - startTime);
        // player.sendMessage(message);

        return false;
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
                PAGE_MAP.put(menu.getLocation(), 0);

                menu.replaceExistingItem(PAGE_PREVIOUS, PAGE_PREVIOUS_STACK);
                menu.addMenuClickHandler(PAGE_PREVIOUS, (p, slot, item, action) -> {
                    Integer page = PAGE_MAP.getOrDefault(menu.getLocation(), 0);
                    page = page <= 0 ? 0 : page - 1;
                    PAGE_MAP.put(menu.getLocation(), page);
                    return false;
                });

                menu.replaceExistingItem(PAGE_NEXT, PAGE_NEXT_STACK);
                menu.addMenuClickHandler(PAGE_NEXT, (p, slot, item, action) -> {
                    Integer page = PAGE_MAP.getOrDefault(menu.getLocation(), 0);
                    Integer maxPages = MAX_PAGE_MAP.getOrDefault(menu.getLocation(), 0);
                    page = page >= maxPages ? maxPages : page + 1;
                    PAGE_MAP.put(menu.getLocation(), page);
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
