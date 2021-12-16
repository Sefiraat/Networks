package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.GridItemRequest;
import io.github.sefiraat.networks.network.ObjectDefinition;
import io.github.sefiraat.networks.network.ObjectType;
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
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class NetworkGrid extends NetworkObject {

    private static final int[] BACKGROUND_SLOTS = {
        0, 1, 2, 3, 5, 6, 7, 8, 45, 46, 47, 48, 49, 50, 51, 52, 53
    };
    private static final int INPUT_SLOT = 4;
    private static final ItemStack BLANK_SLOT_STACK = new CustomItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "");

    private final ItemSetting<Integer> tickRate;

    public NetworkGrid(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, ObjectType.GRID);

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
                    if (tick <= 0) {
                        final BlockMenu blockMenu = BlockStorage.getInventory(block);
                        addToRegistry(block);
                        tryAddItem(blockMenu);
                        updateDisplay(blockMenu);
                    }
                }

                @Override
                public void uniqueTick() {
                    tick = tick <= 0 ? tickRate.getValue() : tick - 1;
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

        final ObjectDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());
        if (definition.getNode() == null) {
            return;
        }

        definition.getNode().getRoot().addItemStack(itemStack);
        long finishTime = System.nanoTime();
        String message = MessageFormat.format("{0}Updating Display: {1}", Theme.CLICK_INFO.getColor(), finishTime - startTime);
        blockMenu.toInventory().getViewers().get(0).sendMessage(message);
    }

    private void updateDisplay(@Nonnull BlockMenu blockMenu) {
        if (blockMenu.hasViewer()) {
            final long startTime = System.nanoTime();
            final ObjectDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());

            if (definition.getNode() == null) {
                return;
            }

            // Update Screen
            final Map<ItemStack, Integer> itemStacks = definition.getNode().getRoot().getConnectedVanillaInventoryItemStacks();
            for (int i = 0; i < 36; i++) {
                if (itemStacks.entrySet().size() > i) {
                    final Map.Entry<ItemStack, Integer> entry = itemStacks.entrySet().stream().toList().get(i);
                    final ItemStack display = entry.getKey().clone();
                    final ItemMeta itemMeta = display.getItemMeta();
                    List<String> lore = itemMeta.getLore();

                    if (lore == null) {
                        lore = getLoreAddition(entry.getValue());
                    } else {
                        lore.addAll(getLoreAddition(entry.getValue()));
                    }

                    itemMeta.setLore(lore);
                    display.setItemMeta(itemMeta);
                    blockMenu.replaceExistingItem(9 + i, display);
                    blockMenu.addMenuClickHandler(9 + i, (player, slot, item, action) ->
                        retrieveItem(player, definition, item, action)
                    );
                } else {
                    blockMenu.replaceExistingItem(9 + i, BLANK_SLOT_STACK);
                    blockMenu.addMenuClickHandler(9 + i, (p, slot, item, action) -> false);
                }
            }
            long finishTime = System.nanoTime();
            String message = MessageFormat.format("{0}Updating Display: {1}", Theme.CLICK_INFO.getColor(), finishTime - startTime);
            blockMenu.toInventory().getViewers().get(0).sendMessage(message);
        }
    }

    @ParametersAreNonnullByDefault
    private boolean retrieveItem(Player player, ObjectDefinition definition, ItemStack itemStack, ClickAction action) {
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
        player.sendMessage(message);

        return false;
    }

    @Nonnull
    private static List<String> getLoreAddition(int amount) {
        return List.of(
            "",
            MessageFormat.format("{0}Amount: {1}{2}", Theme.CLICK_INFO.getColor(), Theme.PASSIVE.getColor(), amount)
        );
    }

    @Override
    public void postRegister() {
        new BlockMenuPreset(this.getId(), this.getItemName()) {

            private final int page = 1;

            @Override
            public void init() {
                drawBackground(BACKGROUND_SLOTS);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return NetworkSlimefunItems.NETWORK_MONITOR.getItem().canUse(player, false)
                    && Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[0];
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                for (int i = 0; i < 36; i++) {
                    menu.replaceExistingItem(9 + i, null);
                    menu.addMenuClickHandler(9 + i, (p, slot, item, action) -> false);
                }
            }
        };
    }

}
