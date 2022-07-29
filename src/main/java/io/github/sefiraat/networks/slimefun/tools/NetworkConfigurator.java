package io.github.sefiraat.networks.slimefun.tools;

import de.jeff_media.morepersistentdatatypes.DataType;
import dev.sefiraat.sefilib.itemstacks.GeneralItemStackUtils;
import dev.sefiraat.sefilib.string.Theme;
import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.slimefun.network.NetworkDirectional;
import io.github.sefiraat.networks.slimefun.network.NetworkPusher;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.StackUtils;
import io.github.sefiraat.networks.utils.Themes;
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
                            player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
                                "configurator.wrong-block",
                                Theme.WARNING
                            ));
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
            player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
                "configurator.no-direction",
                Theme.WARNING
            ));
            return;
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (directional.getItemSlots().length > 0) {
            final ItemStack[] itemStacks = new ItemStack[directional.getItemSlots().length];

            int i = 0;
            for (int slot : directional.getItemSlots()) {
                final ItemStack possibleStack = blockMenu.getItemInSlot(slot);
                if (possibleStack != null) {
                    itemStacks[i] = GeneralItemStackUtils.getAsQuantity(blockMenu.getItemInSlot(slot), 1);
                }
                i++;
            }
            DataTypeMethods.setCustom(itemMeta, ITEM, DataType.ITEM_STACK_ARRAY, itemStacks);
        } else {
            PersistentDataAPI.remove(itemMeta, ITEM);
        }

        DataTypeMethods.setCustom(itemMeta, FACE, DataType.STRING, blockFace.name());
        itemStack.setItemMeta(itemMeta);
        player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
            "configurator.copied",
            Theme.SUCCESS
        ));
    }

    private void applyConfig(@Nonnull NetworkDirectional directional, @Nonnull ItemStack itemStack, @Nonnull BlockMenu blockMenu, @Nonnull Player player) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final ItemStack[] templateStacks = DataTypeMethods.getCustom(itemMeta, ITEM, DataType.ITEM_STACK_ARRAY);
        final String string = DataTypeMethods.getCustom(itemMeta, FACE, DataType.STRING);

        if (string == null) {
            player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
                "configurator.direction-not-supplied",
                Theme.WARNING
            ));
            return;
        }

        directional.setDirection(blockMenu, BlockFace.valueOf(string));
        player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
            "configurator.direction-applied",
            Theme.SUCCESS
        ));


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
                            final ItemStack stackClone = GeneralItemStackUtils.getAsQuantity(stack, 1);
                            stack.setAmount(stack.getAmount() - 1);
                            blockMenu.replaceExistingItem(directional.getItemSlots()[i], stackClone);
                            player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
                                "configurator.item-added",
                                Theme.SUCCESS
                            ));
                            worked = true;
                            break;
                        }
                    }
                    if (!worked) {
                        player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
                            "configurator.not-enough-items",
                            Theme.WARNING,
                            i,
                            Theme.PASSIVE
                        ));
                    }
                } else if (directional instanceof NetworkPusher) {
                    player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
                        "configurator.no-item",
                        Theme.WARNING,
                        i,
                        Theme.PASSIVE
                    ));
                }
                i++;
            }
        } else {
            player.sendMessage(Networks.getLanguageManager().getPlayerMessage(
                "configurator.no-item",
                Theme.WARNING,
                "A",
                Theme.PASSIVE
            ));
        }
    }
}