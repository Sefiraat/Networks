package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.network.stackcaches.ItemRequest;
import io.github.sefiraat.networks.slimefun.NetworkSlimefunItems;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class NetworkWirelessTransmitter extends NetworkObject {

    public static final int TEMPLATE_SLOT = 13;

    private static final int[] BACKGROUND_SLOTS = new int[]{
        0, 1, 2, 6, 7, 8, 9, 10, 11, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26
    };

    private static final int[] BACKGROUND_SLOTS_TEMPLATE = new int[]{
        3, 4, 5, 12, 14, 21, 22, 23
    };

    private static final CustomItemStack TEMPLATE_BACKGROUND_STACK = new CustomItemStack(
        Material.GREEN_STAINED_GLASS_PANE,
        Theme.SUCCESS + "Transmit items matching"
    );

    private static final String LINKED_LOCATION_KEY_X = "linked-location-x";
    private static final String LINKED_LOCATION_KEY_Y = "linked-location-y";
    private static final String LINKED_LOCATION_KEY_Z = "linked-location-z";

    private static final int REQUIRED_POWER = 7500;
    private static final int TICKS_PER = 5;

    private final Map<Location, Location> linkedLocations = new HashMap<>();

    public NetworkWirelessTransmitter(ItemGroup itemGroup,
                                      SlimefunItemStack item,
                                      RecipeType recipeType,
                                      ItemStack[] recipe
    ) {
        super(itemGroup, item, recipeType, recipe, NodeType.WIRELESS_TRANSMITTER);
        this.getSlotsToDrop().add(TEMPLATE_SLOT);

        addItemHandler(
            new BlockTicker() {
                private final Map<Location, Integer> tickMap = new HashMap<>();
                private final Map<Location, Boolean> firstTick = new HashMap<>();

                @Override
                public boolean isSynchronized() {
                    return false;
                }

                @Override
                public void tick(Block block, SlimefunItem slimefunItem, Config config) {
                    BlockMenu blockMenu = BlockStorage.getInventory(block);
                    if (blockMenu != null) {
                        addToRegistry(block);

                        boolean isFirstTick = firstTick.getOrDefault(block.getLocation(), true);
                        if (isFirstTick) {
                            final String xString = BlockStorage.getLocationInfo(
                                block.getLocation(),
                                LINKED_LOCATION_KEY_X
                            );
                            final String yString = BlockStorage.getLocationInfo(
                                block.getLocation(),
                                LINKED_LOCATION_KEY_Y
                            );
                            final String zString = BlockStorage.getLocationInfo(
                                block.getLocation(),
                                LINKED_LOCATION_KEY_Z
                            );
                            if (xString != null && yString != null && zString != null) {
                                final Location linkedLocation = new Location(
                                    block.getWorld(),
                                    Integer.parseInt(xString),
                                    Integer.parseInt(yString),
                                    Integer.parseInt(zString)
                                );
                                linkedLocations.put(block.getLocation(), linkedLocation);
                            }
                            firstTick.put(block.getLocation(), false);
                        }

                        int tick = tickMap.getOrDefault(block.getLocation(), 0);
                        if (tick >= TICKS_PER) {
                            onTick(blockMenu);
                            tickMap.remove(block.getLocation());
                            tick = 0;
                        } else {
                            tick++;
                        }
                        tickMap.put(block.getLocation(), tick + 1);
                    }
                }
            }
        );
    }

    private void onTick(@Nonnull BlockMenu blockMenu) {
        final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());

        if (definition == null || definition.getNode() == null) {
            return;
        }

        final Location location = blockMenu.getLocation();
        final Location linkedLocation = linkedLocations.get(location);

        if (linkedLocation == null) {
            return;
        }

        final SlimefunItem slimefunItem = BlockStorage.check(linkedLocation);

        if (!(slimefunItem instanceof NetworkWirelessReceiver)) {
            linkedLocations.remove(location);
            return;
        }

        final BlockMenu linkedBlockMenu = BlockStorage.getInventory(linkedLocation);
        final ItemStack itemStack = linkedBlockMenu.getItemInSlot(NetworkWirelessReceiver.RECEIVED_SLOT);

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            final ItemStack templateStack = blockMenu.getItemInSlot(TEMPLATE_SLOT);

            if (templateStack == null || templateStack.getType() == Material.AIR) {
                return;
            }

            if (definition.getNode().getRoot().getRootPower() < REQUIRED_POWER) {
                return;
            }

            final ItemStack stackToPush = definition.getNode().getRoot().getItemStack(
                new ItemRequest(templateStack.clone(), templateStack.getMaxStackSize())
            );

            if (stackToPush != null) {
                definition.getNode().getRoot().removeRootPower(REQUIRED_POWER);
                linkedBlockMenu.pushItem(stackToPush, NetworkWirelessReceiver.RECEIVED_SLOT);
                if (definition.getNode().getRoot().isDisplayParticles()) {
                    final Location particleLocation = blockMenu.getLocation().clone().add(0.5, 1.1, 0.5);
                    final Location particleLocation2 = linkedBlockMenu.getLocation().clone().add(0.5, 2.1, 0.5);
                    particleLocation.getWorld().spawnParticle(
                        Particle.WAX_ON,
                        particleLocation,
                        0,
                        0,
                        4,
                        0
                    );
                    particleLocation2.getWorld().spawnParticle(
                        Particle.WAX_OFF,
                        particleLocation2,
                        0,
                        0,
                        -4,
                        0
                    );
                }
            }
        }
    }

    @Override
    public void postRegister() {
        new BlockMenuPreset(this.getId(), this.getItemName()) {

            @Override
            public void init() {
                drawBackground(BACKGROUND_SLOTS);
                drawBackground(TEMPLATE_BACKGROUND_STACK, BACKGROUND_SLOTS_TEMPLATE);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return NetworkSlimefunItems.NETWORK_CELL.canUse(player, false)
                    && Slimefun.getProtectionManager()
                    .hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[]{0};
            }

        };
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent event) {
        super.onBreak(event);
        linkedLocations.remove(event.getBlock().getLocation());
    }

    public void addLinkedLocation(@Nonnull Block block, @Nonnull Location linkedLocation) {
        linkedLocations.put(block.getLocation(), linkedLocation);
        BlockStorage.addBlockInfo(block, LINKED_LOCATION_KEY_X, String.valueOf(linkedLocation.getBlockX()));
        BlockStorage.addBlockInfo(block, LINKED_LOCATION_KEY_Y, String.valueOf(linkedLocation.getBlockY()));
        BlockStorage.addBlockInfo(block, LINKED_LOCATION_KEY_Z, String.valueOf(linkedLocation.getBlockZ()));
    }

}
