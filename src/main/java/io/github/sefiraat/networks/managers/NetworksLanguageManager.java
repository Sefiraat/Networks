package io.github.sefiraat.networks.managers;

import dev.sefiraat.sefilib.localization.LanguageManager;
import dev.sefiraat.sefilib.string.Theme;
import io.github.sefiraat.networks.utils.Themes;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class NetworksLanguageManager extends LanguageManager {

    @ParametersAreNonnullByDefault
    public NetworksLanguageManager(JavaPlugin javaPlugin, String langDirectory, String chosenLang, String defaultLang) {
        super(javaPlugin, langDirectory, chosenLang + ".yml", defaultLang);
    }

    @Nonnull
    public String getLoggingMessage(@Nonnull String messageName) {
        return getString("messages.logging." + messageName);
    }
    @Nonnull
    public String getPlayerMessage(@Nonnull String messageName) {
        return getPlayerMessage(messageName, new Object[0]);
    }
    @Nonnull
    public String getPlayerMessage(@Nonnull String messageName, Object... args) {
        return getString("messages.player." + messageName, args);
    }

    @Nonnull
    public String getThemeLore(@Nonnull String messageName) {
        return getString("themes.lore." + messageName);
    }

    @Nonnull
    public String getCategoryName(@Nonnull String categoryName) {
        return Themes.MAIN + getString("categories.name." + categoryName);
    }

    @Nonnull
    public String getGuiIconName(@Nonnull String iconName) {
        return getGuiIconName(iconName, new Object[0]);
    }

    @Nonnull
    public String getGuiIconName(@Nonnull String iconName, Object... args) {
        return Themes.MAIN + getString("gui.items." + iconName + ".name", args);
    }

    @Nonnull
    public List<String> getGuiIconLore(@Nonnull String itemName) {
        return getGuiIconLore(itemName, new Object[0]);
    }
    @Nonnull
    public List<String> getGuiIconLore(@Nonnull String itemName, Object... args) {
        return getStringList("gui.items." + itemName + ".lore", args);
    }

    @Nonnull
    public String getRecipeTypeName(@Nonnull String typeName) {
        return Themes.MAIN + getString("recipe-types." + typeName + ".name");
    }

    @Nonnull
    public List<String> getRecipeTypeLore(@Nonnull String typeName) {
        return getRecipeTypeLore(typeName, new Object[0]);
    }

    @Nonnull
    public List<String> getRecipeTypeLore(@Nonnull String itemName, Object... args) {
        return getStringList("recipe-types." + itemName + ".lore", args);
    }

    @Nonnull
    public String getItemName(@Nonnull String itemName) {
        return getString("items." + itemName + ".name");
    }

    @Nonnull
    public List<String> getItemLore(@Nonnull String itemName) {
        return getItemLore(itemName, new Object[0]);
    }

    @Nonnull
    public List<String> getItemLore(@Nonnull String itemName, Object... args) {
        return getStringList("items." + itemName + ".lore", args);
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public SlimefunItemStack getThemedStack(String id, ItemStack itemStack, Theme theme, String itemName) {
        return getThemedStack(id, itemStack, theme, itemName, new Object[0]);
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public SlimefunItemStack getThemedStack(String id,
                                            ItemStack itemStack,
                                            Theme theme,
                                            String itemName,
                                            Object... args
    ) {
        final List<String> lore = getItemLore(itemName, args);
        return Theme.themedSlimefunItemStack(
            id,
            itemStack,
            theme,
            getItemName(itemName),
            lore
        );
    }
}
