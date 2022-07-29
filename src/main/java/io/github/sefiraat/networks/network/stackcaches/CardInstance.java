package io.github.sefiraat.networks.network.stackcaches;

import dev.sefiraat.sefilib.string.Theme;
import io.github.sefiraat.networks.utils.Themes;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CardInstance extends ItemStackCache {

    private int amount;
    private final int limit;

    public CardInstance(@Nullable ItemStack itemStack, int amount, int limit) {
        super(itemStack);
        this.amount = amount;
        this.limit = limit;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Nullable
    public ItemStack withdrawItem(int amount) {
        if (this.getItemStack() == null) {
            return null;
        }
        final ItemStack clone = this.getItemStack().clone();
        clone.setAmount(Math.min(this.amount, amount));
        reduceAmount(clone.getAmount());
        return clone;
    }

    @Nullable
    public ItemStack withdrawItem() {
        if (this.getItemStack() == null) {
            return null;
        }
        return withdrawItem(this.getItemStack().getMaxStackSize());
    }

    public void increaseAmount(int amount) {
        long total = (long) this.amount + (long) amount;
        if (total > this.limit) {
            this.amount = this.limit;
        } else {
            this.amount = this.amount + amount;
        }
    }

    public void reduceAmount(int amount) {
        this.amount = this.amount - amount;
    }

    public void updateLore(@Nonnull ItemMeta itemMeta) {
        List<String> lore = itemMeta.getLore();
        lore.set(10, getLoreLine());
        itemMeta.setLore(lore);
    }

    public String getLoreLine() {
        if (this.getItemStack() == null) {
            return Theme.WARNING + "Empty";
        }
        ItemMeta itemMeta = this.getItemMeta();
        String name;
        if (itemMeta != null && itemMeta.hasDisplayName()) {
            name = ChatColor.stripColor(itemMeta.getDisplayName());
        } else if (this.getItemType() != null) {
            name = this.getItemType().name();
        } else {
            name = "Unknown/Error";
        }
        return Theme.CLICK_INFO + name + ": " + Theme.PASSIVE + this.amount;
    }
}
