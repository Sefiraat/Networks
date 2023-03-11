package io.github.sefiraat.networks.utils;

import io.github.sefiraat.networks.Networks;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

@Data
@UtilityClass
public class Keys {

    public static final NamespacedKey ON_COOLDOWN = newKey("cooldown");
    public static final NamespacedKey CARD_INSTANCE = newKey("ntw_card");
    public static final NamespacedKey QUANTUM_STORAGE_INSTANCE = newKey("quantum_storage");
    public static final NamespacedKey BLUEPRINT_INSTANCE = newKey("ntw_blueprint");
    public static final NamespacedKey FACE = newKey("face");
    public static final NamespacedKey ITEM = newKey("item");

    @Nonnull
    public static NamespacedKey newKey(@Nonnull String value) {
        return new NamespacedKey(Networks.getInstance(), value);
    }
}
