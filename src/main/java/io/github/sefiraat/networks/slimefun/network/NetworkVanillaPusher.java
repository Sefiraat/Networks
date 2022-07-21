package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class NetworkVanillaPusher extends NetworkDirectional {

    private static final int[] BACKGROUND_SLOTS = new int[]{
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 15, 16, 17, 18, 20, 22, 23, 24, 26, 27, 28, 30, 31, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44
    };
    private static final int INPUT_SLOT = 25;
    private static final int NORTH_SLOT = 11;
    private static final int SOUTH_SLOT = 29;
    private static final int EAST_SLOT = 21;
    private static final int WEST_SLOT = 19;
    private static final int UP_SLOT = 14;
    private static final int DOWN_SLOT = 32;

    public NetworkVanillaPusher(ItemGroup itemGroup,
                                SlimefunItemStack item,
                                RecipeType recipeType,
                                ItemStack[] recipe
    ) {
        super(itemGroup, item, recipeType, recipe, NodeType.PUSHER);
        this.getSlotsToDrop().add(INPUT_SLOT);
    }

    @Override
    protected void onTick(@Nullable BlockMenu blockMenu, @Nonnull Block block) {
        super.onTick(blockMenu, block);
        if (blockMenu != null) {
            tryPushItem(blockMenu);
        }
    }

    private void tryPushItem(@Nonnull BlockMenu blockMenu) {
        final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());

        if (definition == null || definition.getNode() == null) {
            return;
        }

        final BlockFace direction = getCurrentDirection(blockMenu);
        final Block block = blockMenu.getBlock();
        final Block targetBlock = blockMenu.getBlock().getRelative(direction);
        final UUID uuid = UUID.fromString(BlockStorage.getLocationInfo(block.getLocation(), OWNER_KEY));
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        if (!Slimefun.getProtectionManager().hasPermission(offlinePlayer, targetBlock, Interaction.INTERACT_BLOCK)) {
            return;
        }

        final BlockState blockState = targetBlock.getState();

        if (!(blockState instanceof InventoryHolder holder)) {
            return;
        }

        final Inventory inventory = holder.getInventory();
        final ItemStack stack = blockMenu.getItemInSlot(INPUT_SLOT);

        if (stack == null || stack.getType() == Material.AIR) {
            return;
        }

        if (inventory instanceof FurnaceInventory furnace) {
            handleFurnace(stack, furnace);
        } else if (inventory instanceof BrewerInventory brewer) {
            handleBrewingStand(stack, brewer);
        } else if (InvUtils.fits(holder.getInventory(), stack)) {
            holder.getInventory().addItem(stack);
            stack.setAmount(0);
        }
    }

    private void handleFurnace(@Nonnull ItemStack stack, @Nonnull FurnaceInventory furnace) {
        if (stack.getType().isFuel()
            && (furnace.getFuel() == null || furnace.getFuel().getType() == Material.AIR)
        ) {
            furnace.setFuel(stack.clone());
            stack.setAmount(0);
        } else if (!stack.getType().isFuel()
            && (furnace.getSmelting() == null || furnace.getSmelting().getType() == Material.AIR)
        ) {
            furnace.setSmelting(stack.clone());
            stack.setAmount(0);
        }
    }

    private void handleBrewingStand(@Nonnull ItemStack stack, @Nonnull BrewerInventory brewer) {
        if (stack.getType() == Material.BLAZE_POWDER) {
            if (brewer.getFuel() == null || brewer.getFuel().getType() == Material.AIR) {
                brewer.setFuel(stack.clone());
                stack.setAmount(0);
            }
        } else if (stack.getType() == Material.POTION) {
            for (int i = 0; i < 3; i++) {
                final ItemStack stackInSlot = brewer.getContents()[i];
                if (stackInSlot == null || stackInSlot.getType() == Material.AIR) {
                    final ItemStack[] contents = brewer.getContents();
                    contents[i] = stack.clone();
                    brewer.setContents(contents);
                    stack.setAmount(0);
                    return;
                }
            }
        } else if (brewer.getIngredient() == null || brewer.getIngredient().getType() == Material.AIR) {
            brewer.setIngredient(stack.clone());
            stack.setAmount(0);
        }
    }

    @Nonnull
    @Override
    protected int[] getBackgroundSlots() {
        return BACKGROUND_SLOTS;
    }

    @Override
    public int getNorthSlot() {
        return NORTH_SLOT;
    }

    @Override
    public int getSouthSlot() {
        return SOUTH_SLOT;
    }

    @Override
    public int getEastSlot() {
        return EAST_SLOT;
    }

    @Override
    public int getWestSlot() {
        return WEST_SLOT;
    }

    @Override
    public int getUpSlot() {
        return UP_SLOT;
    }

    @Override
    public int getDownSlot() {
        return DOWN_SLOT;
    }

    @Override
    public boolean runSync() {
        return true;
    }

    @Override
    public int[] getInputSlots() {
        return new int[]{INPUT_SLOT};
    }

    @Override
    protected Particle.DustOptions getDustOptions() {
        return new Particle.DustOptions(Color.MAROON, 1);
    }
}
