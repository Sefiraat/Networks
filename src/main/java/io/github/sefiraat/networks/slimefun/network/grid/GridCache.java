package io.github.sefiraat.networks.slimefun.network.grid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GridCache {

    private int page;
    private int maxPages;
    @Nonnull
    private SortOrder sortOrder;
    @Nullable
    private String filter;

    public GridCache(int page, int maxPages, @Nonnull SortOrder sortOrder) {
        this.page = page;
        this.maxPages = maxPages;
        this.sortOrder = sortOrder;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    @Nonnull
    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(@Nonnull SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Nullable
    public String getFilter() {
        return filter;
    }

    public void setFilter(@Nullable String filter) {
        this.filter = filter;
    }

    enum SortOrder {
        ALPHABETICAL,
        NUMBER
    }
}
