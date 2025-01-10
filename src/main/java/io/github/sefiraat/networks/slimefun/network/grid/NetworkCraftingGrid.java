package io.github.sefiraat.networks.slimefun.network.grid;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.GridItemRequest;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.SupportedRecipes;
import io.github.sefiraat.networks.slimefun.NetworkSlimefunItems;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class NetworkCraftingGrid extends AbstractGrid {

    private static final int[] BACKGROUND_SLOTS = {
        0, 1, 3, 4, 5, 14, 23, 32, 33, 35, 41, 42, 44, 45, 47, 49, 50, 51, 52, 53
    };

    private static final int[] DISPLAY_SLOTS = {
        9, 10, 11, 12, 13, 18, 19, 20, 21, 22, 27, 28, 29, 30, 31, 36, 37, 38, 39, 40
    };

    private static final int[] CRAFT_ITEMS = {
        6, 7, 8, 15, 16, 17, 24, 25, 26
    };

    private static final int INPUT_SLOT = 2;
    private static final int FILTER = 45;
    private static final int PAGE_PREVIOUS = 46;
    private static final int CHANGE_SORT = 47;
    private static final int PAGE_NEXT = 48;

    private static final int CRAFT_BUTTON_SLOT = 34;
    private static final int CRAFT_OUTPUT_SLOT = 43;

    private static final ItemStack CRAFT_BUTTON_STACK = CustomItemStack.create(
        Material.CRAFTING_TABLE,
        Theme.CLICK_INFO.getColor() + "Craft",
        Theme.CLICK_INFO + "Left Click: " + Theme.PASSIVE + "Try to Craft",
        Theme.CLICK_INFO + "Shift Left Click: " + Theme.PASSIVE + "Try to return items"
    );

    private static final Map<Location, GridCache> CACHE_MAP = new HashMap<>();


    public NetworkCraftingGrid(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        for (int craftItem : CRAFT_ITEMS) {
            this.getSlotsToDrop().add(craftItem);
        }
        this.getSlotsToDrop().add(CRAFT_OUTPUT_SLOT);
    }

    @Override
    public void postRegister() {
        getPreset();
    }

    @Nonnull
    @Override
    public BlockMenuPreset getPreset() {
        return new BlockMenuPreset(this.getId(), this.getItemName()) {

            @Override
            public void init() {
                drawBackground(BACKGROUND_SLOTS);
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

                menu.replaceExistingItem(getPagePrevious(), getPagePreviousStack());
                menu.addMenuClickHandler(getPagePrevious(), (p, slot, item, action) -> {
                    GridCache gridCache = getCacheMap().get(menu.getLocation());
                    gridCache.setPage(gridCache.getPage() <= 0 ? 0 : gridCache.getPage() - 1);
                    CACHE_MAP.put(menu.getLocation(), gridCache);
                    return false;
                });

                menu.replaceExistingItem(getPageNext(), getPageNextStack());
                menu.addMenuClickHandler(getPageNext(), (p, slot, item, action) -> {
                    GridCache gridCache = getCacheMap().get(menu.getLocation());
                    gridCache.setPage(gridCache.getPage() >= gridCache.getMaxPages() ? gridCache.getMaxPages() : gridCache.getPage() + 1);
                    getCacheMap().put(menu.getLocation(), gridCache);
                    return false;
                });

                menu.replaceExistingItem(getChangeSort(), getChangeSortStack());
                menu.addMenuClickHandler(getChangeSort(), (p, slot, item, action) -> {
                    GridCache gridCache = getCacheMap().get(menu.getLocation());
                    if (gridCache.getSortOrder() == GridCache.SortOrder.ALPHABETICAL) {
                        gridCache.setSortOrder(GridCache.SortOrder.NUMBER);
                    } else {
                        gridCache.setSortOrder(GridCache.SortOrder.ALPHABETICAL);
                    }
                    getCacheMap().put(menu.getLocation(), gridCache);
                    return false;
                });

                menu.replaceExistingItem(getFilterSlot(), getFilterStack());
                menu.addMenuClickHandler(getFilterSlot(), (p, slot, item, action) -> {
                    GridCache gridCache = getCacheMap().get(menu.getLocation());
                    return setFilter(p, menu, gridCache, action);
                });

                for (int displaySlot : getDisplaySlots()) {
                    menu.replaceExistingItem(displaySlot, null);
                    menu.addMenuClickHandler(displaySlot, (p, slot, item, action) -> false);
                }

                menu.replaceExistingItem(CRAFT_BUTTON_SLOT, CRAFT_BUTTON_STACK);
                menu.addMenuClickHandler(CRAFT_BUTTON_SLOT, (player, slot, item, action) -> {
                    if (action.isShiftClicked()) {
                        tryReturnItems(menu);
                    } else {
                        tryCraft(menu, player);
                    }
                    return false;
                });
            }
        };
    }

    @Nonnull
    @Override
    protected Map<Location, GridCache> getCacheMap() {
        return CACHE_MAP;
    }

    @Override
    public int[] getBackgroundSlots() {
        return BACKGROUND_SLOTS;
    }

    @Override
    public int[] getDisplaySlots() {
        return DISPLAY_SLOTS;
    }

    @Override
    public int getInputSlot() {
        return INPUT_SLOT;
    }

    @Override
    public int getChangeSort() {
        return CHANGE_SORT;
    }

    @Override
    public int getPagePrevious() {
        return PAGE_PREVIOUS;
    }

    @Override
    public int getPageNext() {
        return PAGE_NEXT;
    }

    @Override
    protected int getFilterSlot() {
        return FILTER;
    }

    private void tryCraft(@Nonnull BlockMenu menu, @Nonnull Player player) {
        // Get node and, if it doesn't exist - escape
        final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(menu.getLocation());
        if (definition.getNode() == null) {
            return;
        }

        // Get the recipe input
        final ItemStack[] inputs = new ItemStack[CRAFT_ITEMS.length];
        int i = 0;
        for (int recipeSlot : CRAFT_ITEMS) {
            ItemStack stack = menu.getItemInSlot(recipeSlot);
            inputs[i] = stack;
            i++;
        }

        ItemStack crafted = null;

        // Go through each slimefun recipe, test and set the ItemStack if found
        for (Map.Entry<ItemStack[], ItemStack> entry : SupportedRecipes.getRecipes().entrySet()) {
            if (SupportedRecipes.testRecipe(inputs, entry.getKey())) {
                crafted = entry.getValue().clone();
                break;
            }
        }

        // If no slimefun recipe found, try a vanilla one
        if (crafted == null) {
            crafted = Bukkit.craftItem(inputs, player.getWorld(), player);
        }

        // If no item crafted OR result doesn't fit, escape
        if (crafted.getType() == Material.AIR || !menu.fits(crafted, CRAFT_OUTPUT_SLOT)) {
            return;
        }

        // Push item
        menu.pushItem(crafted, CRAFT_OUTPUT_SLOT);

        // Let's clear down all the items
        for (int recipeSlot : CRAFT_ITEMS) {
            final ItemStack itemInSlot = menu.getItemInSlot(recipeSlot);
            if (itemInSlot != null) {
                // Grab a clone for potential retrieval
                final ItemStack itemInSlotClone = itemInSlot.clone();
                itemInSlotClone.setAmount(1);
                ItemUtils.consumeItem(menu.getItemInSlot(recipeSlot), 1, true);
                // We have consumed a slot item and now the slot it empty - try to refill
                if (menu.getItemInSlot(recipeSlot) == null) {
                    // Process item request
                    final GridItemRequest request = new GridItemRequest(itemInSlotClone, 1, player);
                    final ItemStack requestingStack = definition.getNode().getRoot().getItemStack(request);
                    if (requestingStack != null) {
                        menu.replaceExistingItem(recipeSlot, requestingStack);
                    }
                }
            }
        }
    }

    private void tryReturnItems(@Nonnull BlockMenu menu) {
        // Get node and, if it doesn't exist - escape
        final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(menu.getLocation());

        if (definition.getNode() == null) {
            return;
        }

        for (int recipeSlot : CRAFT_ITEMS) {
            final ItemStack stack = menu.getItemInSlot(recipeSlot);

            if (stack == null || stack.getType() == Material.AIR) {
                continue;
            }
            definition.getNode().getRoot().addItemStack(stack);
        }
    }
}
