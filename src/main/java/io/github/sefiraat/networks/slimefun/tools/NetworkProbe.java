package io.github.sefiraat.networks.slimefun.tools;

import io.github.sefiraat.networks.network.NetworkRoot;
import io.github.sefiraat.networks.slimefun.network.NetworkController;
import io.github.sefiraat.networks.utils.StackUtils;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class NetworkProbe extends SlimefunItem {

    private static final MessageFormat MESSAGE_FORMAT = new MessageFormat("{0}{1}: {2}{3}", Locale.ROOT);

    public NetworkProbe(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        addItemHandler(
            new ItemUseHandler() {
                @Override
                public void onRightClick(PlayerRightClickEvent e) {
                    final Optional<Block> optional = e.getClickedBlock();
                    if (optional.isPresent()) {
                        final Block block = optional.get();
                        final Player player = e.getPlayer();
                        final SlimefunItem slimefunItem = BlockStorage.check(block);
                        if (slimefunItem instanceof NetworkController) {
                            displayToPlayer(block, player);
                            StackUtils.putOnCooldown(e.getItem(), 10);
                            e.cancel();
                        }
                    }
                }
            }
        );
    }

    private void displayToPlayer(@Nonnull Block block, @Nonnull Player player) {
        final NetworkRoot root = NetworkController.getNetworks().get(block.getLocation());
        if (root != null) {
            final int bridges = root.getBridges().size();
            final int monitors = root.getMonitors().size();
            final int importers = root.getImporters().size();
            final int exporters = root.getExporters().size();
            final int grids = root.getGrids().size();
            final int cells = root.getCells().size();
            final int shells = root.getShells().size();
            final int wipers = root.getWipers().size();
            final int grabbers = root.getGrabbers().size();
            final int pushers = root.getPushers().size();
            final int purgers = root.getPushers().size();
            final int crafters = root.getCrafters().size();
            final int powerNodes = root.getPowerNodes().size();
            final int powerDisplays = root.getPowerDisplays().size();
            final int encoders = root.getEncoders().size();

            final Map<ItemStack, Integer> allNetworkItems = root.getAllNetworkItems();
            final int distinctItems = allNetworkItems.size();
            long totalItems = allNetworkItems.values().stream().mapToLong(integer -> integer).sum();

            final ChatColor c = Theme.CLICK_INFO.getColor();
            final ChatColor p = Theme.PASSIVE.getColor();

            player.sendMessage("------------------------------");
            player.sendMessage("       Network Summary        ");
            player.sendMessage("------------------------------");
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Bridges", p, bridges}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Monitors", p, monitors}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Importers", p, importers}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Exporters", p, exporters}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Grids", p, grids}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Cells", p, cells}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Shells", p, shells}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Wipers", p, wipers}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Grabbers", p, grabbers}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Pushers", p, pushers}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Purgers", p, purgers}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Crafters", p, crafters}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Power Nodes", p, powerNodes}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Power Displays", p, powerDisplays}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Encoders", p, encoders}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Distinct Items", p, distinctItems}, new StringBuffer(), null).toString());
            player.sendMessage(MESSAGE_FORMAT.format(new Object[]{c, "Total Items", p, totalItems}, new StringBuffer(), null).toString());
        }
    }

}
