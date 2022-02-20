package io.github.sefiraat.networks.commands;

import io.github.sefiraat.networks.network.stackcaches.CardInstance;
import io.github.sefiraat.networks.slimefun.tools.NetworkCard;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.Theme;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.sefiraat.networks.utils.datatypes.PersistentCardInstanceType;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public class NetworksMain implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player player) {
            if ((player.isOp() || player.hasPermission("networks.admin")) && args.length >= 2) {
                if (args[0].equalsIgnoreCase("fillcard")) {
                    try {
                        int number = Integer.parseInt(args[1]);
                        fillCard(player, number);
                        return true;
                    } catch (NumberFormatException exception) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void fillCard(Player player, int amount) {
        final ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            player.sendMessage(Theme.ERROR + "Item in hand must be a card.");
            return;
        }

        SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);

        if (!(slimefunItem instanceof NetworkCard card)) {
            player.sendMessage(Theme.ERROR + "Item in hand must be a card.");
            return;
        }

        ItemMeta meta = itemStack.getItemMeta();
        final CardInstance cardInstance = DataTypeMethods.getCustom(
            meta,
            Keys.CARD_INSTANCE,
            PersistentCardInstanceType.TYPE
        );

        if (cardInstance == null || cardInstance.getItemStack() == null) {
            player.sendMessage(Theme.ERROR + "This card has either not been set to an item yet or is a corrupted card.");
            return;
        }

        cardInstance.setAmount(amount);
        DataTypeMethods.setCustom(meta, Keys.CARD_INSTANCE, PersistentCardInstanceType.TYPE, cardInstance);
        cardInstance.updateLore(meta);
        itemStack.setItemMeta(meta);
        player.sendMessage(Theme.SUCCESS + "Item updated");
    }
}
