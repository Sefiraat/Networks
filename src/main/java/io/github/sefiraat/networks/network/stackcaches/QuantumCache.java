package io.github.sefiraat.networks.network.stackcaches;

import dev.sefiraat.sefilib.string.Theme;
import io.github.sefiraat.networks.utils.Themes;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class QuantumCache extends ItemStackCache {

    @Nullable
    private final ItemMeta storedItemMeta;
    private final int limit;
    private int amount;
    private boolean voidExcess;

    public QuantumCache(@Nullable ItemStack storedItem, int amount, int limit, boolean voidExcess) {
        super(storedItem);
        this.storedItemMeta = storedItem == null ? null : storedItem.getItemMeta();
        this.amount = amount;
        this.limit = limit;
        this.voidExcess = voidExcess;
    }

    @Nullable
    public ItemMeta getStoredItemMeta() {
        return this.storedItemMeta;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int increaseAmount(int amount) {
        long total = (long) this.amount + (long) amount;
        if (total > this.limit) {
            this.amount = this.limit;
            if (!this.voidExcess) {
                return (int) (total - this.limit);
            }
        } else {
            this.amount = this.amount + amount;
        }
        return 0;
    }

    public void reduceAmount(int amount) {
        this.amount = this.amount - amount;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isVoidExcess() {
        return voidExcess;
    }

    public void setVoidExcess(boolean voidExcess) {
        this.voidExcess = voidExcess;
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

    public void addMetaLore(ItemMeta itemMeta) {
        final List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.add("");
        lore.add(Theme.CLICK_INFO + "Holding: " +
                     (this.getItemMeta() != null && this.getItemMeta().hasDisplayName() ? this.getItemMeta().getDisplayName() : this.getItemStack().getType().name())
        );
        lore.add(Theme.CLICK_INFO + "Amount: " + this.getAmount());
        itemMeta.setLore(lore);
    }

    public void updateMetaLore(ItemMeta itemMeta) {
        final List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.set(lore.size() - 2, Theme.CLICK_INFO + "Holding: " +
                     (this.getItemMeta() != null && this.getItemMeta().hasDisplayName() ? this.getItemMeta().getDisplayName() : this.getItemStack().getType().name())
        );
        lore.set(lore.size() - 1, Theme.CLICK_INFO + "Amount: " + this.getAmount());
        itemMeta.setLore(lore);
    }
}
