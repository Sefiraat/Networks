package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.network.stackcaches.CardInstance;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.sefiraat.networks.utils.datatypes.PersistentAmountInstanceType;
import io.github.sefiraat.networks.utils.datatypes.PersistentCardInstanceType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NetworkMemoryShellCache {

    @Nonnull
    private final ItemStack memoryCard;
    @Nonnull
    private final ItemMeta memoryCardMeta;
    @Nullable
    private final CardInstance cardInstance;

    public NetworkMemoryShellCache(@Nonnull ItemStack memoryCard) {
        this.memoryCard = memoryCard;
        this.memoryCardMeta = memoryCard.getItemMeta();
        this.cardInstance = retrieveCardInstance(memoryCard);
    }

    @Nonnull
    public ItemStack getMemoryCard() {
        return this.memoryCard;
    }

    @Nonnull
    public ItemMeta getMemoryCardMeta() {
        return this.memoryCardMeta;
    }

    @Nullable
    public ItemStack getItemStack() {
        return this.cardInstance == null ? null : this.cardInstance.getItemStack();
    }

    @Nullable
    public CardInstance getCardInstance() {
        return cardInstance;
    }

    public void refreshMemoryCard() {
        if (this.cardInstance != null) {
            this.cardInstance.updateLore(this.memoryCardMeta);
            DataTypeMethods.setCustom(this.memoryCardMeta, Keys.CARD_INSTANCE, PersistentAmountInstanceType.TYPE, this.cardInstance);
            this.memoryCard.setItemMeta(this.memoryCardMeta);
        }
    }

    @Nullable
    private static CardInstance retrieveCardInstance(@Nonnull ItemStack card) {
        final ItemMeta cardMeta = card.getItemMeta();
        return DataTypeMethods.getCustom(cardMeta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE);
    }
}
