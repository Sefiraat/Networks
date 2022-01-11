package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.network.stackcaches.CardInstance;
import io.github.sefiraat.networks.slimefun.NetworkSlimefunItems;
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
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class NetworkMemoryWiper extends NetworkObject {

    private static final int CARD_SLOT = 4;

    private static final ItemStack BACK_CARD = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, Theme.PASSIVE + "Memory Card");

    private static final int[] BACKGROUND_SLOTS = new int[]{0, 1, 2, 6, 7, 8};
    private static final int[] CARD_SLOTS = new int[]{3, 5};

    public static final int[] STACKS_TO_PUSH = new int[]{
        1,
        3,
        9,
        27
    };

    private final int tier;

    public NetworkMemoryWiper(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int tier) {
        super(itemGroup, item, recipeType, recipe, NodeType.WIPER);
        this.tier = tier;
        this.getSlotsToDrop().add(CARD_SLOT);
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

                tryPushStack(blockMenu, card, STACKS_TO_PUSH[tier]);
            }
        });
    }

    @ParametersAreNonnullByDefault
    private void tryPushStack(BlockMenu blockMenu, ItemStack card, int stacks) {
        final SlimefunItem cardItem = SlimefunItem.getByItem(card);
        if (cardItem instanceof NetworkCard) {
            final CardInstance amountInstance = getAmountInstance(card);

            if (amountInstance == null || amountInstance.getAmount() <= 0) {
                return;
            }

            final CardInstance cardInstance = getCardInstance(card);

            for (int i = 0; i < stacks; i++) {
                final ItemStack itemStack = cardInstance.withdrawStack();

                if (itemStack == null) {
                    return;
                }

                final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());

                if (definition.getNode() == null) {
                    return;
                }

                definition.getNode().getRoot().addItemStack(itemStack);

                if (itemStack.getType() != Material.AIR && itemStack.getAmount() > 0) {
                    cardInstance.increaseAmount(itemStack.getAmount());
                    return;
                }
            }

            setCardInstance(card, cardInstance);
        }
    }

    @Nullable
    private CardInstance getCardInstance(@Nonnull ItemStack card) {
        final ItemMeta cardMeta = card.getItemMeta();
        return DataTypeMethods.getCustom(cardMeta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE);
    }

    @Nullable
    private CardInstance getAmountInstance(@Nonnull ItemStack card) {
        final ItemMeta cardMeta = card.getItemMeta();
        return DataTypeMethods.getCustom(cardMeta, Keys.CARD_INSTANCE, PersistentAmountInstanceType.TYPE);
    }

    private void setCardInstance(@Nonnull ItemStack card, CardInstance cardInstance) {
        final ItemMeta cardMeta = card.getItemMeta();
        cardInstance.updateLore(cardMeta);
        DataTypeMethods.setCustom(cardMeta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE, cardInstance);
        card.setItemMeta(cardMeta);
    }

    @Override
    public void postRegister() {
        new BlockMenuPreset(this.getId(), this.getItemName()) {

            @Override
            public void init() {
                drawBackground(BACKGROUND_SLOTS);
                for (int i : CARD_SLOTS) {
                    addItem(i, BACK_CARD, (p, slot, item, action) -> false);
                }

            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return NetworkSlimefunItems.NETWORK_CELL.canUse(player, false)
                    && Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[0];
            }

        };
    }

    public int getTier() {
        return tier;
    }
}
