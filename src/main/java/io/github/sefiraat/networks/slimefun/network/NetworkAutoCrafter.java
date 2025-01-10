package io.github.sefiraat.networks.slimefun.network;

import io.github.sefiraat.networks.NetworkStorage;
import io.github.sefiraat.networks.network.NetworkRoot;
import io.github.sefiraat.networks.network.NodeDefinition;
import io.github.sefiraat.networks.network.NodeType;
import io.github.sefiraat.networks.network.SupportedRecipes;
import io.github.sefiraat.networks.network.stackcaches.BlueprintInstance;
import io.github.sefiraat.networks.network.stackcaches.ItemRequest;
import io.github.sefiraat.networks.slimefun.NetworkSlimefunItems;
import io.github.sefiraat.networks.slimefun.tools.CraftingBlueprint;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.StackUtils;
import io.github.sefiraat.networks.utils.Theme;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.sefiraat.networks.utils.datatypes.PersistentCraftingBlueprintType;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NetworkAutoCrafter extends NetworkObject {

    private static final int[] BACKGROUND_SLOTS = new int[]{
        3, 4, 5, 12, 13, 14, 21, 22, 23
    };
    private static final int[] BLUEPRINT_BACKGROUND = new int[]{0, 1, 2, 9, 11, 18, 19, 20};
    private static final int[] OUTPUT_BACKGROUND = new int[]{6, 7, 8, 15, 17, 24, 25, 26};

    private static final int BLUEPRINT_SLOT = 10;
    private static final int OUTPUT_SLOT = 16;

    public static final ItemStack BLUEPRINT_BACKGROUND_STACK = CustomItemStack.create(
        Material.BLUE_STAINED_GLASS_PANE, Theme.PASSIVE + "Crafting Blueprint"
    );

    public static final ItemStack OUTPUT_BACKGROUND_STACK = CustomItemStack.create(
        Material.GREEN_STAINED_GLASS_PANE, Theme.PASSIVE + "Output"
    );

    private final int chargePerCraft;
    private final boolean withholding;

    private static final Map<Location, BlueprintInstance> INSTANCE_MAP = new HashMap<>();

    public NetworkAutoCrafter(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int chargePerCraft, boolean withholding) {
        super(itemGroup, item, recipeType, recipe, NodeType.CRAFTER);

        this.chargePerCraft = chargePerCraft;
        this.withholding = withholding;

        this.getSlotsToDrop().add(BLUEPRINT_SLOT);
        this.getSlotsToDrop().add(OUTPUT_SLOT);

        addItemHandler(
            new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return false;
                }

                @Override
                public void tick(Block block, SlimefunItem slimefunItem, Config config) {
                    BlockMenu blockMenu = BlockStorage.getInventory(block);
                    if (blockMenu != null) {
                        addToRegistry(block);
                        craftPreFlight(blockMenu);
                    }
                }
            }
        );
    }

    protected void craftPreFlight(@Nonnull BlockMenu blockMenu) {

        releaseCache(blockMenu);

        final NodeDefinition definition = NetworkStorage.getAllNetworkObjects().get(blockMenu.getLocation());

        if (definition == null || definition.getNode() == null) {
            return;
        }

        final NetworkRoot root = definition.getNode().getRoot();

        if (!this.withholding) {
            final ItemStack stored = blockMenu.getItemInSlot(OUTPUT_SLOT);
            if (stored != null && stored.getType() != Material.AIR) {
                root.addItemStack(stored);
            }
        }

        final ItemStack blueprint = blockMenu.getItemInSlot(BLUEPRINT_SLOT);

        if (blueprint == null || blueprint.getType() == Material.AIR) {
            return;
        }

        final long networkCharge = root.getRootPower();

        if (networkCharge > this.chargePerCraft) {
            final SlimefunItem item = SlimefunItem.getByItem(blueprint);

            if (!(item instanceof CraftingBlueprint)) {
                return;
            }

            BlueprintInstance instance = INSTANCE_MAP.get(blockMenu.getLocation());

            if (instance == null) {
                final ItemMeta blueprintMeta = blueprint.getItemMeta();
                final Optional<BlueprintInstance> optional = DataTypeMethods.getOptionalCustom(blueprintMeta, Keys.BLUEPRINT_INSTANCE, PersistentCraftingBlueprintType.TYPE);

                if (optional.isEmpty()) {
                    return;
                }

                instance = optional.get();
                setCache(blockMenu, instance);
            }

            final ItemStack output = blockMenu.getItemInSlot(OUTPUT_SLOT);

            if (output != null
                && output.getType() != Material.AIR
                && (output.getAmount() + instance.getItemStack().getAmount() >= output.getMaxStackSize() || !StackUtils.itemsMatch(instance, output, true))) {
                return;
            }

            if (tryCraft(blockMenu, instance, root)) {
                root.removeRootPower(this.chargePerCraft);
            }
        }
    }

    private boolean tryCraft(@Nonnull BlockMenu blockMenu, @Nonnull BlueprintInstance instance, @Nonnull NetworkRoot root) {
        // Get the recipe input
        final ItemStack[] inputs = new ItemStack[9];

        /* Make sure the network has the required items
         * Needs to be revisited as matching is happening stacks 2x when I should
         * only need the one
         */
        HashMap<ItemStack, Integer> requiredItems = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            final ItemStack requested = instance.getRecipeItems()[i];
            if (requested != null) {
                requiredItems.merge(requested, 1, Integer::sum);
            }
        }

        for (Map.Entry<ItemStack, Integer> entry : requiredItems.entrySet()) {
            if (!root.contains(new ItemRequest(entry.getKey(), entry.getValue()))) {
                return false;
            }
        }

        // Then fetch the actual items
        for (int i = 0; i < 9; i++) {
            final ItemStack requested = instance.getRecipeItems()[i];
            if (requested != null) {
                final ItemStack fetched = root.getItemStack(new ItemRequest(instance.getRecipeItems()[i], 1));
                inputs[i] = fetched;
            } else {
                inputs[i] = null;
            }
        }

        ItemStack crafted = null;

        // Go through each slimefun recipe, test and set the ItemStack if found
        for (Map.Entry<ItemStack[], ItemStack> entry : SupportedRecipes.getRecipes().entrySet()) {
            if (SupportedRecipes.testRecipe(inputs, entry.getKey())) {
                crafted = entry.getValue().clone();
                break;
            }
        }

        // If no slimefun recipe found, try a vanilla one
        if (crafted == null) {
            instance.generateVanillaRecipe(blockMenu.getLocation().getWorld());
            if (instance.getRecipe() == null) {
                returnItems(root, inputs);
                return false;
            } else if (Arrays.equals(instance.getRecipeItems(), inputs)) {
                setCache(blockMenu, instance);
                crafted = instance.getRecipe().getResult();
            }
        }

        // If no item crafted OR result doesn't fit, escape
        if (crafted == null || crafted.getType() == Material.AIR) {
            returnItems(root, inputs);
            return false;
        }

        // Push item
        final Location location = blockMenu.getLocation().clone().add(0.5, 1.1, 0.5);
        if (root.isDisplayParticles()) {
            location.getWorld().spawnParticle(Particle.WAX_OFF, location, 0, 0, 4, 0);
        }
        blockMenu.pushItem(crafted, OUTPUT_SLOT);
        return true;
    }

    private void returnItems(@Nonnull NetworkRoot root, @Nonnull ItemStack[] inputs) {
        for (ItemStack input : inputs) {
            if (input != null) {
                root.addItemStack(input);
            }
        }
    }

    public void releaseCache(@Nonnull BlockMenu blockMenu) {
        if (blockMenu.hasViewer()) {
            INSTANCE_MAP.remove(blockMenu.getLocation());
        }
    }

    public void setCache(@Nonnull BlockMenu blockMenu, @Nonnull BlueprintInstance blueprintInstance) {
        if (!blockMenu.hasViewer()) {
            INSTANCE_MAP.putIfAbsent(blockMenu.getLocation().clone(), blueprintInstance);
        }
    }


    @Override
    public void postRegister() {
        new BlockMenuPreset(this.getId(), this.getItemName()) {

            @Override
            public void init() {
                drawBackground(BACKGROUND_SLOTS);
                drawBackground(BLUEPRINT_BACKGROUND_STACK, BLUEPRINT_BACKGROUND);
                drawBackground(OUTPUT_BACKGROUND_STACK, OUTPUT_BACKGROUND);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return NetworkSlimefunItems.NETWORK_AUTO_CRAFTER.canUse(player, false)
                    && Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                if (NetworkAutoCrafter.this.withholding && flow == ItemTransportFlow.WITHDRAW) {
                    return new int[]{OUTPUT_SLOT};
                }
                return new int[0];
            }
        };
    }
}
