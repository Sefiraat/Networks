package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.network.stackcaches.CardInstance;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.sefiraat.networks.utils.datatypes.PersistentCardInstanceType;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.ListSelectionModel;
import java.text.MessageFormat;
import java.util.List;

public class NetworkMemoryShellCache {

    @Nonnull
    private final ItemStack memoryCard;
    @Nonnull
    private final ItemMeta memoryCardMeta;
    @Nullable
    private final CardInstance cardInstance;
    @Nonnull
    private final Location cacheLocation;
    boolean hasErrored = false;

    public NetworkMemoryShellCache(@Nonnull ItemStack memoryCard, @Nonnull Location cacheLocation) {
        this.memoryCard = memoryCard;
        this.memoryCardMeta = memoryCard.getItemMeta();
        this.cardInstance = retrieveCardInstance(memoryCard);
        this.cacheLocation = cacheLocation;
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
            if (this.cardInstance.getItemStack() == null) {
                if (!hasErrored) {
                    String loreItem = "Unknown";
                    List<String> lore = this.memoryCardMeta.getLore();
                    if (lore != null && lore.size() >= 11) {
                        String possibleLore = lore.get(10);
                        if (possibleLore != null) {
                            loreItem = possibleLore;
                        }
                    }
                    Networks.getInstance().getLogger().warning(
                        MessageFormat.format("The card in the shell located at: x:{0} y:{1} z:{2} has a faulty itemstack data and will need to be deleted and remade.",
                                             this.cacheLocation.getBlockX(),
                                             this.cacheLocation.getBlockY(),
                                             this.cacheLocation.getBlockZ()
                        )
                    );
                    Networks.getInstance().getLogger().warning(MessageFormat.format("This card shows the items as being: {0}", loreItem));
                    this.hasErrored = true;
                }
                return;
            }
            this.cardInstance.updateLore(this.memoryCardMeta);
            DataTypeMethods.setCustom(this.memoryCardMeta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE, this.cardInstance);
            this.memoryCard.setItemMeta(this.memoryCardMeta);
        }
    }

    @Nullable
    private static CardInstance retrieveCardInstance(@Nonnull ItemStack card) {
        final ItemMeta cardMeta = card.getItemMeta();
        return DataTypeMethods.getCustom(cardMeta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE);
    }
}
