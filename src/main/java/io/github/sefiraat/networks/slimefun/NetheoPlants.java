package io.github.sefiraat.networks.slimefun;

import dev.sefiraat.netheopoiesis.api.items.DoNothingSeed;
import dev.sefiraat.netheopoiesis.api.items.HarvestableSeed;
import dev.sefiraat.netheopoiesis.api.plant.Growth;
import dev.sefiraat.netheopoiesis.api.plant.GrowthStages;
import dev.sefiraat.netheopoiesis.api.plant.Placements;
import dev.sefiraat.netheopoiesis.implementation.Stacks;
import dev.sefiraat.netheopoiesis.utils.Skulls;
import dev.sefiraat.netheopoiesis.utils.Theme;
import dev.sefiraat.sefilib.itemstacks.GeneralItemStackUtils;
import io.github.sefiraat.networks.Networks;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NetheoPlants {

    // Stacks
    public static final SlimefunItemStack STONE_CHUNK_SEED = Theme.themedSeed(
        "NTW_STONE_CHUNK_SEED",
        Skulls.SEED_PURPLE.getPlayerHead(),
        Theme.SEED,
        Networks.getLanguageManager().getItemName("stone-chunk-seed"),
        Networks.getLanguageManager().getStringArray("items.stone-chunk-seed.lore"),
        Stacks.getCanBePlacedOnLore(Networks.getLanguageManager().getString("items.stone-chunk-seed.placed-on"))
    );

    public static final SlimefunItemStack SYNTHETIC_SEED = Theme.themedSeed(
        "NTW_SYNTHETIC_SEED",
        Skulls.SEED_ORANGE.getPlayerHead(),
        Theme.SEED,
        Networks.getLanguageManager().getItemName("synthetic-seed"),
        Networks.getLanguageManager().getStringArray("items.synthetic-seed.lore"),
        Stacks.getCanBePlacedOnLore(Networks.getLanguageManager().getString("items.synthetic-seed.placed-on"))
    );

    public static final SlimefunItemStack SYNTHETIC_EMERALD_SEED = Theme.themedSeed(
        "NTW_SYNTHETIC_EMERALD_SEED",
        Skulls.SEED_GREEN.getPlayerHead(),
        Theme.SEED,
        Networks.getLanguageManager().getItemName("synthetic-emerald-seed"),
        Networks.getLanguageManager().getStringArray("items.synthetic-emerald-seed.lore"),
        Stacks.getCanBePlacedOnLore(Networks.getLanguageManager().getString("items.synthetic-emerald-seed.placed-on"))
    );

    public static final SlimefunItemStack SYNTHETIC_DIAMOND_SEED = Theme.themedSeed(
        "NTW_SYNTHETIC_DIAMOND_SEED",
        Skulls.SEED_GREEN.getPlayerHead(),
        Theme.SEED,
        Networks.getLanguageManager().getItemName("synthetic-diamond-seed"),
        Networks.getLanguageManager().getStringArray("items.synthetic-diamond-seed.lore"),
        Stacks.getCanBePlacedOnLore(Networks.getLanguageManager().getString("items.synthetic-diamond-seed.placed-on"))
    );

    public static final SlimefunItemStack FRAGMENTED_SEED = Theme.themedSeed(
        "NTW_FRAGMENTED_SEED",
        Skulls.SEED_GREEN.getPlayerHead(),
        Theme.SEED,
        Networks.getLanguageManager().getItemName("fragmented-seed"),
        Networks.getLanguageManager().getStringArray("items.fragmented-seed.lore"),
        Stacks.getCanBePlacedOnLore(Networks.getLanguageManager().getString("items.fragmented-seed.placed-on"))
    );

    public static void setup() {

        final Networks plugin = Networks.getInstance();

        new HarvestableSeed(STONE_CHUNK_SEED)
            .setHarvestingResult(SlimefunItems.STONE_CHUNK)
            .setGrowth(new Growth(GrowthStages.FUNGAL_PURPLE, Placements.NETHER_GRASS_AND_UP, 5, 0.05))
            .addBreedingPair(Stacks.STONEY_SEED.getItemId(), Stacks.METALLIC_SEED.getItemId(), 0.2, 0.1)
            .addFlavourProfile(0, 0, 0, 0, 0)
            .tryRegister(plugin);

        new DoNothingSeed(SYNTHETIC_SEED)
            .setGrowth(new Growth(GrowthStages.SPIKEY_ORANGE, Placements.NETHER_GRASS_AND_UP, 0, 0.001))
            .addBreedingPair(Stacks.COBBLED_SEED.getItemId(), Stacks.LEARNED_SEED.getItemId(), 0.01, 0)
            .addFlavourProfile(0, 0, 5, 0, 0)
            .tryRegister(plugin);

        new HarvestableSeed(SYNTHETIC_EMERALD_SEED)
            .setHarvestingResult(SlimefunItems.SYNTHETIC_EMERALD)
            .setGrowth(new Growth(GrowthStages.VINEY_GREEN, Placements.VORACIOUS_AND_UP, 20, 0.05))
            .addBreedingPair(SYNTHETIC_SEED.getItemId(), Stacks.VALUABLE_SEED.getItemId(), 0.01, 0.001)
            .addFlavourProfile(0, 15, 0, 0, 0)
            .tryRegister(plugin);

        new HarvestableSeed(SYNTHETIC_DIAMOND_SEED)
            .setHarvestingResult(SlimefunItems.SYNTHETIC_DIAMOND)
            .setGrowth(new Growth(GrowthStages.VINEY_BLUE, Placements.VORACIOUS_AND_UP, 20, 0.05))
            .addBreedingPair(SYNTHETIC_SEED.getItemId(), Stacks.PERFECTION_SEED.getItemId(), 0.01, 0.001)
            .addFlavourProfile(15, 0, 0, 0, 0)
            .tryRegister(plugin);

        new HarvestableSeed(FRAGMENTED_SEED)
            .setHarvestingResult(GeneralItemStackUtils.getAsQuantity(NetworksSlimefunItemStacks.SYNTHETIC_EMERALD_SHARD, 9))
            .setGrowth(new Growth(GrowthStages.VINEY_GREEN, Placements.VORACIOUS_AND_UP, 10, 0.005))
            .addBreedingPair(SYNTHETIC_SEED.getItemId(), Stacks.PERFECTION_SEED.getItemId(), 0.01, 0.001)
            .addFlavourProfile(10, 0, 0, 0, 0)
            .tryRegister(plugin);
    }
}
