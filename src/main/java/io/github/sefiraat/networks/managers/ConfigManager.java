package io.github.sefiraat.networks.managers;

import io.github.sefiraat.networks.Networks;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ConfigManager {

    @Getter
    private static ConfigManager instance;

    @Getter
    private final FileConfiguration blockColors;

    public ConfigManager() {
        instance = this;
        Networks.getInstance().saveDefaultConfig();
        this.blockColors = getConfig("block_colors.yml", true);
    }

    /**
     * @noinspection unchecked
     */
    @Nullable
    public static Particle.DustOptions getDustOptions(@Nonnull Block block, float size) {
        final List<?> list = getInstance().getBlockColors().getList(block.getType().name());

        if (list == null) {
            return null;
        }
        final List<Integer> integers = (List<Integer>) list;
        final Color color = Color.fromRGB(integers.get(0), integers.get(1), integers.get(2));
        return new Particle.DustOptions(color, size);
    }

    /**
     * @noinspection ResultOfMethodCallIgnored
     */
    private FileConfiguration getConfig(String fileName, boolean updateWithDefaults) {
        final Networks plugin = Networks.getInstance();
        final File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(fileName, true);
        }
        final FileConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
            if (updateWithDefaults) {
                updateConfig(config, file, fileName);
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return config;
    }

    private void updateConfig(FileConfiguration config, File file, String fileName) throws IOException {
        final InputStream inputStream = Networks.getInstance().getResource(fileName);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final YamlConfiguration defaults = YamlConfiguration.loadConfiguration(reader);
        config.addDefaults(defaults);
        config.options().copyDefaults(true);
        config.save(file);
    }

    public void saveAll() {
        Networks.getInstance().getLogger().info("Networks saving data.");
        saveConfig(blockColors, "block_colors.yml");
    }

    private void saveConfig(FileConfiguration configuration, String filename) {
        File file = new File(Networks.getInstance().getDataFolder(), filename);
        try {
            configuration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
