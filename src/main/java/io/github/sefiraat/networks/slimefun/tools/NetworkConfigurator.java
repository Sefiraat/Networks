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
                            player.sendMessage(Theme.ERROR + "必須對一個網路接口方向為目標.");
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
            player.sendMessage(Theme.ERROR + "這個方向上未設置方向");
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
        player.sendMessage(Theme.SUCCESS + "設定已複製.");
    }

    private void applyConfig(@Nonnull NetworkDirectional directional, @Nonnull ItemStack itemStack, @Nonnull BlockMenu blockMenu, @Nonnull Player player) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final ItemStack templateStack = DataTypeMethods.getCustom(itemMeta, ITEM, DataType.ITEM_STACK);
        final String string = DataTypeMethods.getCustom(itemMeta, FACE, DataType.STRING);

        if (string == null) {
            player.sendMessage(Theme.ERROR + "尚未複製任何方向設定.");
            return;
        }

        directional.setDirection(blockMenu, BlockFace.valueOf(string));
        player.sendMessage(Theme.SUCCESS + "方向已成功添加.");

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
                player.sendMessage(Theme.SUCCESS + "過濾物品從背包中移除, 並放入在過濾欄位中.");
                    return;
                }
            }
            player.sendMessage(Theme.WARNING + "你沒有足夠的匹配物品可以放入過濾欄.");
        } else if (directional instanceof NetworkPusher) {
            player.sendMessage(Theme.WARNING + "沒有提供任何物品.");
        }

    }
}