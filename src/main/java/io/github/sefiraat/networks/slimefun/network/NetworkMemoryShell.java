package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.slimefun.NetworkSlimefunItems;
import io.github.sefiraat.networks.slimefun.tools.CardInstance;
import io.github.sefiraat.networks.slimefun.tools.NetworkCard;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.Theme;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.sefiraat.networks.utils.datatypes.PersistentAmountInstanceType;
import io.github.sefiraat.networks.utils.datatypes.PersistentCardInstanceType;
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
import org.bukkit.inventory.meta.ItemMeta;

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

    public static final Map<Location, NetworkMemoryShellCache> CACHES = new HashMap<>();

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

                final NetworkMemoryShellCache cache = CACHES.getOrDefault(blockMenu.getLocation(), new NetworkMemoryShellCache());
                final ItemStack card = blockMenu.getItemInSlot(CARD_SLOT);

                // No card, quick exit
                if (card == null || card.getType() == Material.AIR) {
                    cache.setItemStack(null);
                    cache.setCached(false);
                    CACHES.put(blockMenu.getLocation(), cache);
                    return;
                }

                // Refresh stack here for actions taken externally
                tryRefresh(blockMenu, card, cache);

                final ItemStack input = blockMenu.getItemInSlot(INPUT_SLOT);
                if (input != null && input.getType() != Material.AIR) {
                    tryInputItem(card, new ItemStack[]{input}, cache);
                }

                final ItemStack output = blockMenu.getItemInSlot(OUTPUT_SLOT);

                // No item in output, try to fill
                if (output == null || output.getType() == Material.AIR) {
                    fillEmptySlot(blockMenu, card, cache);
                    tryRefresh(blockMenu, card, cache);
                    return;
                }

                // Item in output exists but has no room left - escape
                if (output.getAmount() >= output.getMaxStackSize()) {
                    return;
                }

                fillFilledSlot(blockMenu, card, output, cache);
                tryRefresh(blockMenu, card, cache);
            }
        });
    }

    @ParametersAreNonnullByDefault
    private void tryRefresh(BlockMenu blockMenu, ItemStack card, NetworkMemoryShellCache cache) {
        if (blockMenu.hasViewer()) {
            // Has a viewer, we force lore refreshes now
            if (cache.isNeedsLoreRefresh()) {
                CardInstance cardInstance = getCardInstance(card, cache);
                if (cardInstance != null) {
                    refreshCardLore(card, cardInstance);
                }
            }
            cache.setNeedsLoreRefresh(false);
            // And remove the cached stack to stop trickery
            cache.setItemStack(null);
            cache.setCached(false);
        } else {
            // No viewer, try to add ItemStack to the cache and mark for lore refresh
            cache.setNeedsLoreRefresh(true);
            if (!cache.isCached()) {
                CardInstance cardInstance = getCardInstance(card, cache);
                if (cardInstance != null) {
                    cache.setItemStack(cardInstance.getItemStack());
                    cache.setCached(true);
                }
            }
        }
        CACHES.put(blockMenu.getLocation(), cache);
    }

    @ParametersAreNonnullByDefault
    public static void tryInputItem(ItemStack card, ItemStack[] input, NetworkMemoryShellCache cache) {
        final SlimefunItem cardItem = SlimefunItem.getByItem(card);
        if (cardItem instanceof NetworkCard) {
            final CardInstance cardInstance = getCardInstance(card, cache);
            if (cardInstance == null) {
                return;
            }
            for (ItemStack itemStack : input) {
                if (itemMatch(itemStack, cardInstance)) {
                    cardInstance.increaseAmount(itemStack.getAmount());
                    itemStack.setAmount(0);
                    setCardInstance(card, cardInstance);
                    cache.setNeedsLoreRefresh(true);
                }
            }
        }
    }

    @ParametersAreNonnullByDefault
    private static void fillEmptySlot(BlockMenu blockMenu, ItemStack card, NetworkMemoryShellCache cache) {
        final SlimefunItem cardItem = SlimefunItem.getByItem(card);
        if (cardItem instanceof NetworkCard) {
            final CardInstance amountInstance = getAmountInstance(card);

            if (amountInstance == null || amountInstance.getAmount() <= 0) {
                return;
            }

            final CardInstance cardInstance = getCardInstance(card, cache);
            final ItemStack itemStack = cardInstance.withdrawStack();

            if (itemStack == null) {
                return;
            }

            blockMenu.pushItem(itemStack, OUTPUT_SLOT);
            setCardInstance(card, cardInstance);
            cache.setNeedsLoreRefresh(true);
        }
    }

    @ParametersAreNonnullByDefault
    private static void fillFilledSlot(BlockMenu blockMenu, ItemStack card, ItemStack output, NetworkMemoryShellCache cache) {
        final SlimefunItem cardItem = SlimefunItem.getByItem(card);
        if (cardItem instanceof NetworkCard) {
            final CardInstance cardInstance = getCardInstance(card, cache);
            if (cardInstance == null || cardInstance.getAmount() <= 0) {
                return;
            }
            final int requestAmount = output.getMaxStackSize() - output.getAmount();
            final ItemStack itemStack = cardInstance.withdrawStack(requestAmount);

            if (itemStack == null) {
                return;
            }

            blockMenu.pushItem(itemStack, OUTPUT_SLOT);
            setCardInstance(card, cardInstance);
            cache.setNeedsLoreRefresh(true);
        }
    }

    private static void refreshCardLore(@Nonnull ItemStack card, @Nonnull CardInstance cardInstance) {
        final ItemMeta cardMeta = card.getItemMeta();
        cardInstance.updateLore(cardMeta);
        DataTypeMethods.setCustom(cardMeta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE, cardInstance);
        card.setItemMeta(cardMeta);
    }

    @Nullable
    private static CardInstance getCardInstance(@Nonnull ItemStack card, @Nonnull NetworkMemoryShellCache cache) {
        final ItemMeta cardMeta = card.getItemMeta();
        CardInstance instance;

        if (cache.getItemStack() == null) {
            instance = DataTypeMethods.getCustom(cardMeta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE);
        } else {
            instance = DataTypeMethods.getCustom(cardMeta, Keys.CARD_INSTANCE, PersistentAmountInstanceType.TYPE);
            if (instance != null) {
                instance.setItemStack(cache.getItemStack());
            }
        }

        return instance;
    }

    @Nullable
    private static CardInstance getAmountInstance(@Nonnull ItemStack card) {
        final ItemMeta cardMeta = card.getItemMeta();
        return DataTypeMethods.getCustom(cardMeta, Keys.CARD_INSTANCE, PersistentAmountInstanceType.TYPE);
    }

    private static boolean itemMatch(@Nonnull ItemStack itemStack, @Nonnull CardInstance instance) {
        if (itemStack.getType() != instance.getItemType()) {
            return false;
        }
        if (itemStack.hasItemMeta()) {
            final ItemMeta itemMeta = itemStack.getItemMeta();
            final ItemMeta cachedMeta = instance.getItemMeta();
            return itemMeta.equals(cachedMeta);
        } else {
            return instance.getItemMeta() == null;
        }
    }

    private static void setCardInstance(@Nonnull ItemStack card, @Nonnull CardInstance cardInstance) {
        final ItemMeta cardMeta = card.getItemMeta();
        DataTypeMethods.setCustom(cardMeta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE, cardInstance);
        card.setItemMeta(cardMeta);
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
                    return new int[] {INPUT_SLOT};
                } else if (flow == ItemTransportFlow.WITHDRAW) {
                    return new int[] {OUTPUT_SLOT};
                }
                return new int[0];
            }

        };
    }

}
