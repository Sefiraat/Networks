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

    @Nonnull
    public static NamespacedKey newKey(@Nonnull String value) {
        return new NamespacedKey(Networks.getInstance(), value);
    }
}
