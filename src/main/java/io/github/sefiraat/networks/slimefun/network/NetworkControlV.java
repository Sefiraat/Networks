package io.github.sefiraat.networks.slimefun.network;

import com.gmail.nossr50.mcMMO;
import dev.sefiraat.sefilib.misc.ParticleUtils;
import dev.sefiraat.sefilib.world.LocationUtils;
import io.github.bakedlibs.dough.blocks.BlockPosition;
import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.managers.SupportedPluginManager;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.network.stackcaches.ItemRequest;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NetworkControlV extends NetworkDirectional {

    private static final int[] BACKGROUND_SLOTS = new int[]{
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 15, 17, 18, 20, 22, 23, 24, 26, 27, 28, 30, 31, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44
    };
    private static final int[] TEMPLATE_BACKGROUND = new int[]{16};
    private static final int TEMPLATE_SLOT = 25;
    private static final int NORTH_SLOT = 11;
    private static final int SOUTH_SLOT = 29;
    private static final int EAST_SLOT = 21;
    private static final int WEST_SLOT = 19;
    private static final int UP_SLOT = 14;
    private static final int DOWN_SLOT = 32;
    private static final int REQUIRED_POWER = 100;

    private final Set<BlockPosition> blockCache = new HashSet<>();

    public static final ItemStack TEMPLATE_BACKGROUND_STACK = CustomItemStack.create(
        Material.BLUE_STAINED_GLASS_PANE, Theme.PASSIVE + "Paste items matching template"
    );

    public NetworkControlV(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, NodeType.PASTER);
        this.getSlotsToDrop().add(TEMPLATE_SLOT);
    }

    @Override
    protected void onTick(@Nullable BlockMenu blockMenu, @Nonnull Block block) {
        super.onTick(blockMenu, block);
        if (blockMenu != null) {
            tryPasteBlock(blockMenu);
        }
    }

    @Override
    protected void onUniqueTick() {
        blockCache.clear();
    }

    private void tryPasteBlock(@Nonnull BlockMenu blockMenu) {
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
        final BlockPosition targetPosition = new BlockPosition(targetBlock);

        if (this.blockCache.contains(targetPosition)) {
            return;
        }

        final Material material = targetBlock.getType();

        if (!material.isAir()) {
            return;
        }

        final ItemStack templateStack = blockMenu.getItemInSlot(TEMPLATE_SLOT);

        if (templateStack == null) {
            return;
        }

        final Material templateMaterial = templateStack.getType();

        if (!templateMaterial.isBlock() || SlimefunTag.SENSITIVE_MATERIALS.isTagged(templateMaterial)) {
            return;
        }

        final SlimefunItem slimefunItem = SlimefunItem.getByItem(templateStack);

        if (slimefunItem != null) {
            return;
        }

        final UUID uuid = UUID.fromString(BlockStorage.getLocationInfo(blockMenu.getLocation(), OWNER_KEY));
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        if (!Slimefun.getProtectionManager().hasPermission(offlinePlayer, targetBlock, Interaction.PLACE_BLOCK)) {
            return;
        }

        final ItemRequest request = new ItemRequest(templateStack.clone(), 1);
        final ItemStack fetchedStack = definition.getNode().getRoot().getItemStack(request);

        if (fetchedStack == null || fetchedStack.getAmount() < 1) {
            return;
        }

        this.blockCache.add(targetPosition);
        Bukkit.getScheduler().runTask(Networks.getInstance(), bukkitTask -> {
            targetBlock.setType(fetchedStack.getType(), true);
            if (SupportedPluginManager.getInstance().isMcMMO()) {
                mcMMO.getPlaceStore().setTrue(targetBlock);
            }
            ParticleUtils.displayParticleRandomly(
                LocationUtils.centre(targetBlock.getLocation()),
                Particle.ELECTRIC_SPARK,
                1,
                5
            );
        });
    }

    @Nonnull
    @Override
    protected int[] getBackgroundSlots() {
        return BACKGROUND_SLOTS;
    }

    @Nullable
    @Override
    protected int[] getOtherBackgroundSlots() {
        return TEMPLATE_BACKGROUND;
    }

    @Nullable
    @Override
    protected ItemStack getOtherBackgroundStack() {
        return TEMPLATE_BACKGROUND_STACK;
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
    public int[] getItemSlots() {
        return new int[]{TEMPLATE_SLOT};
    }

    @Override
    protected Particle.DustOptions getDustOptions() {
        return new Particle.DustOptions(Color.MAROON, 1);
    }
}
