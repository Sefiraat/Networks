package io.github.sefiraat.networks.slimefun.network;

import dev.sefiraat.sefilib.misc.ParticleUtils;
import dev.sefiraat.sefilib.world.LocationUtils;
import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.features.blockstatesnapshot.BlockStateSnapshotResult;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class NetworkControlX extends NetworkDirectional {

    private static final int[] TEMPLATE_BACKGROUND = new int[]{16};
    private static final int REQUIRED_POWER = 100;
    private static final Particle.DustOptions DUST_OPTIONS = new Particle.DustOptions(Color.GRAY, 1);

    public NetworkControlX(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, NodeType.CUTTER);
    }

    @Override
    protected void onTick(@Nullable BlockMenu blockMenu, @Nonnull Block block) {
        super.onTick(blockMenu, block);
        if (blockMenu != null) {
            tryBreakBlock(blockMenu);
        }
    }

    private void tryBreakBlock(@Nonnull BlockMenu blockMenu) {
        final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());

        if (definition == null || definition.getNode() == null) {
            return;
        }

        if (definition.getNode().getRoot().getRootPower() < REQUIRED_POWER) {
            return;
        }

        final BlockFace direction = getCurrentDirection(blockMenu);

        if (direction == BlockFace.SELF) {
            return;
        }

        final Block targetBlock = blockMenu.getBlock().getRelative(direction);
        final Material material = targetBlock.getType();

        if (material.getHardness() < 0 || material.isAir()) {
            return;
        }

        final SlimefunItem slimefunItem = BlockStorage.check(targetBlock);

        if (slimefunItem != null) {
            return;
        }

        final BlockStateSnapshotResult blockState = PaperLib.getBlockState(targetBlock, true);

        if (blockState.getState() instanceof InventoryHolder) {
            return;
        }

        final UUID uuid = UUID.fromString(BlockStorage.getLocationInfo(blockMenu.getLocation(), OWNER_KEY));
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        if (!Slimefun.getProtectionManager().hasPermission(offlinePlayer, targetBlock, Interaction.BREAK_BLOCK)) {
            return;
        }

        final ItemStack resultStack = new ItemStack(material, 1);

        definition.getNode().getRoot().addItemStack(resultStack);

        if (resultStack.getAmount() == 0) {
            definition.getNode().getRoot().removeRootPower(REQUIRED_POWER);
            Bukkit.getScheduler().runTask(Networks.getInstance(), bukkitTask -> {
                targetBlock.setType(Material.AIR, true);
                ParticleUtils.displayParticleRandomly(
                    LocationUtils.centre(targetBlock.getLocation()),
                    1,
                    5,
                    DUST_OPTIONS
                );
            });
        }
    }

    @Nullable
    @Override
    protected int[] getOtherBackgroundSlots() {
        return TEMPLATE_BACKGROUND;
    }

    @Override
    protected Particle.DustOptions getDustOptions() {
        return DUST_OPTIONS;
    }
}
