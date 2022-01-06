package io.github.sefiraat.networks.slimefun.network;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class NetworkMemoryShellCache {

    @Nullable
    private ItemStack itemStack;
    private boolean needsLoreRefresh;
    private boolean isCached;

    @Nullable
    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(@Nullable ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public boolean isNeedsLoreRefresh() {
        return needsLoreRefresh;
    }

    public void setNeedsLoreRefresh(boolean needsLoreRefresh) {
        this.needsLoreRefresh = needsLoreRefresh;
    }

    public boolean isCached() {
        return isCached;
    }

    public void setCached(boolean cached) {
        isCached = cached;
    }

}
