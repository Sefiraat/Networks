package io.github.sefiraat.networks.slimefun.tools;

import io.github.sefiraat.networks.network.ItemStackCache;
import io.github.sefiraat.networks.utils.Theme;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
    public ItemStack withdrawStack(int amount) {
        if (this.getItemStack() == null) {
            return null;
        }
        final ItemStack clone = this.getItemStack().clone();
        clone.setAmount(Math.min(this.amount, amount));
        reduceAmount(clone.getAmount());
        return clone;
    }

    @Nullable
    public ItemStack withdrawStack() {
        if (this.getItemStack() == null) {
            return null;
        }
        return withdrawStack(this.getItemStack().getMaxStackSize());
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
        String name = itemMeta.hasDisplayName() ? ChatColor.stripColor(itemMeta.getDisplayName()) : this.getItemType().name();
        return Theme.CLICK_INFO + name + ": " + Theme.PASSIVE + this.amount;
    }
}
