package io.github.sefiraat.networks.slimefun.tools;

import de.jeff_media.morepersistentdatatypes.DataType;
import io.github.sefiraat.networks.slimefun.network.NetworkDirectional;
import io.github.sefiraat.networks.slimefun.network.NetworkPusher;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.StackUtils;
import io.github.sefiraat.networks.utils.Theme;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Optional;

public class NetworkConfigurator extends SlimefunItem {

    private static final NamespacedKey FACE = Keys.newKey("face");
    private static final NamespacedKey ITEM = Keys.newKey("item");

    public NetworkConfigurator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        addItemHandler(
            new ItemUseHandler() {
                @Override
                public void onRightClick(PlayerRightClickEvent e) {
                    final Player player = e.getPlayer();
                    final Optional<Block> optional = e.getClickedBlock();
                    if (optional.isPresent()) {
                        final Block block = optional.get();
                        final SlimefunItem slimefunItem = BlockStorage.check(block);
                        if (Slimefun.getProtectionManager().hasPermission(player, block, Interaction.INTERACT_BLOCK)
                            && slimefunItem instanceof NetworkDirectional directional
                        ) {
                            final BlockMenu blockMenu = BlockStorage.getInventory(block);
                            if (player.isSneaking()) {
                                setConfigurator(directional, e.getItem(), blockMenu, player);
                            } else {
                                applyConfig(directional, e.getItem(), blockMenu, player);
                            }
                        } else {
                            player.sendMessage(Theme.ERROR + "Must target a directional Networks interface.");
                        }
                    }
                    e.cancel();
                }
            }
        );
    }

    private void setConfigurator(@Nonnull NetworkDirectional directional, @Nonnull ItemStack itemStack, @Nonnull BlockMenu blockMenu, @Nonnull Player player) {
        final BlockFace blockFace = NetworkDirectional.getSelectedFace(blockMenu.getLocation());

        if (blockFace == null) {
            player.sendMessage(Theme.ERROR + "This directional does not yet have a direction set");
            return;
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();

        final ItemStack templateStack = directional.getItemSlot() > -1 ? blockMenu.getItemInSlot(directional.getItemSlot()) : null;
        DataTypeMethods.setCustom(itemMeta, FACE, DataType.STRING, blockFace.name());

        if (templateStack != null && templateStack.getType() != Material.AIR) {
            final ItemStack clone = StackUtils.getAsQuantity(templateStack, 1);
            DataTypeMethods.setCustom(itemMeta, ITEM, DataType.ITEM_STACK, clone);
        } else {
            PersistentDataAPI.remove(itemMeta, ITEM);
        }

        itemStack.setItemMeta(itemMeta);
        player.sendMessage(Theme.SUCCESS + "Configuration copied.");
    }

    private void applyConfig(@Nonnull NetworkDirectional directional, @Nonnull ItemStack itemStack, @Nonnull BlockMenu blockMenu, @Nonnull Player player) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final ItemStack templateStack = DataTypeMethods.getCustom(itemMeta, ITEM, DataType.ITEM_STACK);
        final String string = DataTypeMethods.getCustom(itemMeta, FACE, DataType.STRING);

        if (string == null) {
            player.sendMessage(Theme.ERROR + "No direction has been copied yet.");
            return;
        }

        directional.setDirection(blockMenu, BlockFace.valueOf(string));
        player.sendMessage(Theme.SUCCESS + "Direction has been successfully applied.");

        final ItemStack currentBlueprint = directional.getItemSlot() > -1 ? blockMenu.getItemInSlot(directional.getItemSlot()) : null;
        if (currentBlueprint != null && currentBlueprint.getType() != Material.AIR) {
            blockMenu.getLocation().getWorld().dropItem(blockMenu.getLocation(), currentBlueprint.clone());
            currentBlueprint.setAmount(0);
        }

        if (templateStack != null && templateStack.getType() != Material.AIR) {
            for (ItemStack stack : player.getInventory()) {
                if (StackUtils.itemsMatch(stack, templateStack, false)) {
                    final ItemStack stackClone = StackUtils.getAsQuantity(stack, 1);
                    stack.setAmount(stack.getAmount() - 1);
                    blockMenu.replaceExistingItem(directional.getItemSlot(), stackClone);
                player.sendMessage(Theme.SUCCESS + "Filter item removed from inventory and placed in filter.");
                    return;
                }
            }
            player.sendMessage(Theme.WARNING + "You don't have enough matching items to put in the filter.");
        } else if (directional instanceof NetworkPusher) {
            player.sendMessage(Theme.WARNING + "No item has been provided.");
        }

    }
}