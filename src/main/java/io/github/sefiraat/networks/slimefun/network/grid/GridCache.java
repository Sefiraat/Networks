package io.github.sefiraat.networks.slimefun.network.grid;

public class GridCache {

    private int page;
    private int maxPages;
    private SortOrder sortOrder;

    public GridCache(int page, int maxPages, SortOrder sortOrder) {
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

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    enum SortOrder {
        ALPHABETICAL,
        NUMBER
    }
}
