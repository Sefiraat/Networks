package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.slimefun.NetworkSlimefunItems;
import io.github.sefiraat.networks.utils.StackUtils;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

public class NetworkMemoryShell extends NetworkObject {

    public static final int INPUT_SLOT = 1;
    public static final int CARD_SLOT = 4;
    public static final int OUTPUT_SLOT = 7;

    private static final ItemStack BACK_INPUT = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, Theme.PASSIVE + "Input");
    private static final ItemStack BACK_CARD = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, Theme.PASSIVE + "Memory Card");
    private static final ItemStack BACK_OUTPUT = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, Theme.PASSIVE + "Output");

    private static final int[] INPUT_SLOTS = new int[]{0, 2};
    private static final int[] CARD_SLOTS = new int[]{3, 5};
    private static final int[] OUTPUT_SLOTS = new int[]{6, 8};

    private static final Map<Location, NetworkMemoryShellCache> CACHES = new HashMap<>();

    public NetworkMemoryShell(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, NodeType.SHELL);
        this.getSlotsToDrop().add(INPUT_SLOT);
        this.getSlotsToDrop().add(CARD_SLOT);
        this.getSlotsToDrop().add(OUTPUT_SLOT);
        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return false;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                addToRegistry(b);

                final BlockMenu blockMenu = BlockStorage.getInventory(b);

                if (blockMenu == null) {
                    return;
                }

                final ItemStack card = blockMenu.getItemInSlot(CARD_SLOT);

                // No card, quick exit
                if (card == null || card.getType() == Material.AIR) {
                    return;
                }

                NetworkMemoryShellCache cache = CACHES.get(blockMenu.getLocation());

                if (cache == null) {
                    cache = new NetworkMemoryShellCache(card);
                }

                // There is a viewer, update the stack
                if (blockMenu.hasViewer()) {
                    cache.refreshMemoryCard();
                }

                // Move items from the input slot into the card
                final ItemStack input = blockMenu.getItemInSlot(INPUT_SLOT);
                if (input != null && input.getType() != Material.AIR) {
                    tryInputItem(new ItemStack[]{input}, cache);
                }

                // Output items
                final ItemStack output = blockMenu.getItemInSlot(OUTPUT_SLOT);
                if (output == null || output.getType() == Material.AIR) {
                    // No item in output, try output
                    final ItemStack fetched = getItemStack(cache);
                    blockMenu.pushItem(fetched, OUTPUT_SLOT);
                } else if (output.getAmount() < output.getMaxStackSize()) {
                    // There is an item but its not filled so lets top it up if we can
                    final int requestAmount = output.getMaxStackSize() - output.getAmount();
                    final ItemStack fetched = getItemStack(cache, requestAmount);
                    blockMenu.pushItem(fetched, OUTPUT_SLOT);
                }

                CACHES.put(blockMenu.getLocation(), cache);

            }
        });
    }

    @ParametersAreNonnullByDefault
    public static void tryInputItem(ItemStack[] input, NetworkMemoryShellCache cache) {
        if (cache.getCardInstance() == null) {
            return;
        }
        for (ItemStack itemStack : input) {
            if (StackUtils.itemsMatch(cache.getCardInstance(), itemStack)) {
                cache.getCardInstance().increaseAmount(itemStack.getAmount());
                itemStack.setAmount(0);
            }
        }

    }

    @ParametersAreNonnullByDefault
    @Nullable
    public static ItemStack getItemStack(NetworkMemoryShellCache cache) {
        if (cache.getCardInstance() == null || cache.getCardInstance().getAmount() <= 0) {
            return null;
        }
        return cache.getCardInstance().withdrawItem();
    }

    @ParametersAreNonnullByDefault
    @Nullable
    public static ItemStack getItemStack(@Nonnull NetworkMemoryShellCache cache, int amount) {
        if (cache.getCardInstance() == null || cache.getCardInstance().getAmount() <= 0) {
            return null;
        }
        return cache.getCardInstance().withdrawItem(amount);
    }

    @Override
    public void postRegister() {
        new BlockMenuPreset(this.getId(), this.getItemName()) {

            @Override
            public void init() {
                for (int i : INPUT_SLOTS) {
                    addItem(i, BACK_INPUT, (p, slot, item, action) -> false);
                }
                for (int i : CARD_SLOTS) {
                    addItem(i, BACK_CARD, (p, slot, item, action) -> false);
                }
                for (int i : OUTPUT_SLOTS) {
                    addItem(i, BACK_OUTPUT, (p, slot, item, action) -> false);
                }
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return NetworkSlimefunItems.NETWORK_CELL.canUse(player, false)
                    && Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                if (flow == ItemTransportFlow.INSERT) {
                    return new int[]{INPUT_SLOT};
                } else if (flow == ItemTransportFlow.WITHDRAW) {
                    return new int[]{OUTPUT_SLOT};
                }
                return new int[0];
            }
        };
    }

    public static Map<Location, NetworkMemoryShellCache> getCaches() {
        return CACHES;
    }

}
