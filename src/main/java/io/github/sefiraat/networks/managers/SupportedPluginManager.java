package io.github.sefiraat.networks.managers;

import com.bgsoftware.wildstacker.api.WildStackerAPI;
import com.google.common.base.Preconditions;
import io.github.sefiraat.networks.Networks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;

import dev.rosewood.rosestacker.api.RoseStackerAPI;
import dev.rosewood.rosestacker.stack.StackedItem;

public class SupportedPluginManager {

    private static SupportedPluginManager instance;

    private final boolean infinityExpansion;
    private final boolean netheopoiesis;
    private final boolean slimeHud;
    private final boolean roseStacker;
    private final boolean wildStacker;

    private RoseStackerAPI roseStackerAPI;

    // region First Tick Only Registrations
    private boolean mcMMO;
    private boolean wildChests;

    // endregion

    public SupportedPluginManager() {
        Preconditions.checkArgument(instance == null, "Cannot instantiate class");
        instance = this;
        this.infinityExpansion = Bukkit.getPluginManager().isPluginEnabled("InfinityExpansion");
        this.netheopoiesis = Bukkit.getPluginManager().isPluginEnabled("Netheopoiesis");
        this.slimeHud = Bukkit.getPluginManager().isPluginEnabled("SlimeHUD");

        this.roseStacker = Bukkit.getPluginManager().isPluginEnabled("RoseStacker");
        if (roseStacker) {
            this.roseStackerAPI = RoseStackerAPI.getInstance();
        }

        this.wildStacker = Bukkit.getPluginManager().isPluginEnabled("WildStacker");
        Networks.getInstance()
            .getServer()
            .getScheduler()
            .runTaskLater(Networks.getInstance(), this::firstTickRegistrations, 1);
    }

    private void firstTickRegistrations() {
        this.wildChests = Bukkit.getPluginManager().isPluginEnabled("WildChests");
        this.mcMMO = Bukkit.getPluginManager().isPluginEnabled("mcMMO");
    }

    public boolean isInfinityExpansion() {
        return infinityExpansion;
    }

    public boolean isNetheopoiesis() {
        return netheopoiesis;
    }

    public boolean isSlimeHud() {
        return slimeHud;
    }

    public boolean isMcMMO() {
        return mcMMO;
    }

    public boolean isWildChests() {
        return wildChests;
    }

    public boolean isRoseStacker() {
        return roseStacker;
    }

    public RoseStackerAPI getRoseStackerAPI() {
        return roseStackerAPI;
    }

    public boolean isWildStacker() {
        return wildStacker;
    }

    public static SupportedPluginManager getInstance() {
        return instance;
    }

    public static int getStackAmount(Item item) {
        if (getInstance().isWildStacker()) {
            return WildStackerAPI.getItemAmount(item);
        } else if (getInstance().isRoseStacker()) {
            StackedItem stackedItem = getInstance().getRoseStackerAPI().getStackedItem(item);
            return stackedItem == null ? item.getItemStack().getAmount() : stackedItem.getStackSize();
        } else {
            return item.getItemStack().getAmount();
        }
    }

    public static void setStackAmount(Item item, int amount) {
        if (getInstance().isWildStacker()) {
            WildStackerAPI.getStackedItem(item).setStackAmount(amount, true);
        } else if (getInstance().isRoseStacker()) {
            StackedItem stackedItem = getInstance().getRoseStackerAPI().getStackedItem(item);
            if (stackedItem != null) {
                stackedItem.setStackSize(amount);
            }
        } else {
            item.getItemStack().setAmount(amount);
        }
    }
}
