package io.github.sefiraat.networks.utils;

import de.jeff_media.morepersistentdatatypes.DataType;
import io.github.sefiraat.networks.slimefun.network.NetworkDirectional;
import io.github.sefiraat.networks.slimefun.network.NetworkPusher;
import io.github.sefiraat.networks.slimefun.tools.NetworkConfigurator;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public class NetworkUtils {

    public static void applyConfig(@Nonnull NetworkDirectional directional, @Nonnull BlockMenu blockMenu, @Nonnull Player player) {
        ItemStack itemStack = player.getInventory().getItemInOffHand();

        if (SlimefunItem.getByItem(itemStack) instanceof NetworkConfigurator) {
            applyConfig(directional, itemStack, blockMenu, player);
        }
    }

    public static void applyConfig(@Nonnull NetworkDirectional directional, @Nonnull ItemStack itemStack, @Nonnull BlockMenu blockMenu, @Nonnull Player player) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final ItemStack[] templateStacks = DataTypeMethods.getCustom(itemMeta, Keys.ITEM, DataType.ITEM_STACK_ARRAY);
        final String string = DataTypeMethods.getCustom(itemMeta, Keys.FACE, DataType.STRING);

        if (string == null) {
            player.sendMessage(Theme.ERROR + "Direction: " + Theme.PASSIVE + "Not supplied");
            return;
        }

        directional.setDirection(blockMenu, BlockFace.valueOf(string));
        player.sendMessage(Theme.ERROR + "Direction: " + Theme.PASSIVE + "Successfully applied");


        if (directional.getItemSlots().length > 0) {
            for (int slot : directional.getItemSlots()) {
                final ItemStack stackToDrop = blockMenu.getItemInSlot(slot);
                if (stackToDrop != null && stackToDrop.getType() != Material.AIR) {
                    blockMenu.getLocation().getWorld().dropItem(blockMenu.getLocation(), stackToDrop.clone());
                    stackToDrop.setAmount(0);
                }
            }
        }

        if (templateStacks != null) {
            int i = 0;
            for (ItemStack templateStack : templateStacks) {
                if (templateStack != null && templateStack.getType() != Material.AIR) {
                    boolean worked = false;
                    for (ItemStack stack : player.getInventory()) {
                        if (StackUtils.itemsMatch(stack, templateStack)) {
                            final ItemStack stackClone = StackUtils.getAsQuantity(stack, 1);
                            stack.setAmount(stack.getAmount() - 1);
                            blockMenu.replaceExistingItem(directional.getItemSlots()[i], stackClone);
                            player.sendMessage(Theme.SUCCESS + "Item [" + i + "]: " + Theme.PASSIVE + "Item added into filter");
                            worked = true;
                            break;
                        }
                    }
                    if (!worked) {
                        player.sendMessage(Theme.WARNING + "Item [" + i + "]: " + Theme.PASSIVE + "Not enough items to fill filter");
                    }
                } else if (directional instanceof NetworkPusher) {
                    player.sendMessage(Theme.WARNING + "Item [" + i + "]: " + Theme.PASSIVE + "No item in stored config");
                }
                i++;
            }
        } else {
            player.sendMessage(Theme.WARNING + "Items: " + Theme.PASSIVE + "No items in stored config");
        }
    }
}
