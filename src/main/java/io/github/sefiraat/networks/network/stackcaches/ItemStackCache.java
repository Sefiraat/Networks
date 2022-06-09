package io.github.sefiraat.networks.network.stackcaches;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;

public class ItemStackCache {

    private ItemStack itemStack;
    @Nullable
    private ItemMeta itemMeta = null;
    private boolean metaCached = false;

    public ItemStackCache(@Nullable ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Nullable
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;

        // refresh meta here
        this.metaCached = false;
        this.itemMeta = null;
    }

    @Nullable
    public ItemMeta getItemMeta() {
        if (this.itemMeta == null && !this.metaCached) {
            this.itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : null;
            this.metaCached = !this.metaCached;
        }
        return this.itemMeta;
    }

    @Nullable
    public Material getItemType() {
        return this.itemStack.getType();
    }
}
