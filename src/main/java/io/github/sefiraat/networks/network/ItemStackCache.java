package io.github.sefiraat.networks.network;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;

public class ItemStackCache {

    private ItemStack itemStack;
    @Nullable
    private ItemMeta itemMeta = null;
    @Nullable
    private Material itemType = null;

    public ItemStackCache(@Nullable ItemStack itemStack) {
        this.itemStack = itemStack;
        if (itemStack != null) {
            this.itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : null;
            this.itemType = itemStack.getType();
        }
    }

    public ItemStackCache(@Nullable ItemStack itemStack, @Nullable ItemMeta itemMeta, @Nullable Material itemType) {
        this.itemStack = itemStack;
        this.itemMeta = itemMeta;
        this.itemType = itemType;
    }

    @Nullable
    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Nullable
    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    @Nullable
    public Material getItemType() {
        return itemType;
    }
}
