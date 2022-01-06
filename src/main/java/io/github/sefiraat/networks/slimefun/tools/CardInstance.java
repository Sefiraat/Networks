package io.github.sefiraat.networks.slimefun.tools;

import io.github.sefiraat.networks.utils.Theme;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.List;

public class CardInstance {

    @Nullable
    private ItemStack itemStack;
    @Nullable
    private Material type;
    @Nullable
    private ItemMeta itemMeta;
    private int amount;
    private final int limit;

    public CardInstance(@Nullable ItemStack itemStack, int amount, int limit) {
        this.itemStack = itemStack;
        if (this.itemStack != null) {
            this.itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : null;
            this.type = itemStack.getType();
        }
        this.amount = amount;
        this.limit = limit;
    }

    @Nullable
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Nullable
    public Material getType() {
        return type;
    }

    @Nullable
    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setItemStack(@Nullable ItemStack itemStack) {
        this.itemStack = itemStack;
        if (this.itemStack != null) {
            this.itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : null;
            this.type = itemStack.getType();
        }
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Nullable
    public ItemStack withdrawStack(int amount) {
        if (this.itemStack == null) {
            return null;
        }
        final ItemStack clone = this.itemStack.clone();
        clone.setAmount(Math.min(this.amount, amount));
        reduceAmount(clone.getAmount());
        return clone;
    }

    @Nullable
    public ItemStack withdrawStack() {
        if (this.itemStack == null) {
            return null;
        }
        return withdrawStack(this.itemStack.getMaxStackSize());
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

    public void updateLore(ItemMeta itemMeta) {
        List<String> lore = itemMeta.getLore();
        lore.set(10, getLoreLine());
        itemMeta.setLore(lore);
    }

    public String getLoreLine() {
        if (this.itemStack == null) {
            return Theme.WARNING + "Empty";
        }
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        String name = itemMeta.hasDisplayName() ? ChatColor.stripColor(itemMeta.getDisplayName()) : this.itemStack.getType().name();
        return Theme.CLICK_INFO + name + ": " + Theme.PASSIVE + this.amount;
    }
}
