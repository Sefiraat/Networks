package io.github.sefiraat.networks.slimefun.tools;

import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.slimefun.network.NetworkBridge;
import io.github.sefiraat.networks.slimefun.network.NetworkExport;
import io.github.sefiraat.networks.slimefun.network.NetworkGrabber;
import io.github.sefiraat.networks.slimefun.network.NetworkImport;
import io.github.sefiraat.networks.slimefun.network.NetworkMonitor;
import io.github.sefiraat.networks.slimefun.network.NetworkVanillaGrabber;
import io.github.sefiraat.networks.slimefun.network.NetworkVanillaPusher;
import io.github.sefiraat.networks.slimefun.network.NetworkWirelessReceiver;
import io.github.sefiraat.networks.slimefun.network.NetworkWirelessTransmitter;
import io.github.sefiraat.networks.slimefun.network.NetworkObject;
import io.github.sefiraat.networks.slimefun.network.NetworkPusher;
import io.github.sefiraat.networks.slimefun.network.NetworkVanillaGrabber;
import io.github.sefiraat.networks.slimefun.network.NetworkVanillaPusher;
import io.github.sefiraat.networks.slimefun.network.NetworkWirelessReceiver;
import io.github.sefiraat.networks.slimefun.network.NetworkWirelessTransmitter;
import io.github.sefiraat.networks.utils.Keys;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.LimitedUseItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class NetworkRake extends LimitedUseItem {

    private static final NamespacedKey key = Keys.newKey("uses");

    private final Set<Class<? extends NetworkObject>> viableObjects = new HashSet<>();

    public NetworkRake(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int amount) {
        super(itemGroup, item, recipeType, recipe);
        setMaxUseCount(amount);
        viableObjects.add(NetworkBridge.class);
        viableObjects.add(NetworkMonitor.class);
        viableObjects.add(NetworkPusher.class);
        viableObjects.add(NetworkGrabber.class);
        viableObjects.add(NetworkImport.class);
        viableObjects.add(NetworkExport.class);
        viableObjects.add(NetworkVanillaGrabber.class);
        viableObjects.add(NetworkVanillaPusher.class);
        viableObjects.add(NetworkWirelessTransmitter.class);
        viableObjects.add(NetworkWirelessReceiver.class);
    }

    @Override
    public void preRegister() {
        addItemHandler((ItemUseHandler) this::onUse);
    }

    /**
     * This returns the {@link ItemHandler} that will be added to this {@link SlimefunItem}.
     *
     * @return The {@link ItemHandler} that should be added to this {@link SlimefunItem}
     */
    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return this::onUse;
    }

    protected void onUse(PlayerRightClickEvent e) {
        e.cancel();
        final Optional<Block> optional = e.getClickedBlock();
        if (optional.isPresent()) {
            final Block block = optional.get();
            final Player player = e.getPlayer();
            final SlimefunItem slimefunItem = BlockStorage.check(block);
            if (slimefunItem != null
                && viableObjects.contains(slimefunItem.getClass())
                && Slimefun.getProtectionManager().hasPermission(player, block, Interaction.BREAK_BLOCK)
            ) {
                final BlockBreakEvent event = new BlockBreakEvent(block, player);
                Networks.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return;
                }

                block.setType(Material.AIR);
                BlockStorage.clearBlockInfo(block);
                damageItem(e.getPlayer(), e.getItem());
            }
        }
    }

    @Override
    protected @Nonnull
    NamespacedKey getStorageKey() {
        return key;
    }
}
