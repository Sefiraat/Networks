package io.github.sefiraat.networks.slimefun;

import io.github.bakedlibs.dough.items.ItemUtils;
import io.github.schntgaispock.slimehud.SlimeHUD;
import io.github.schntgaispock.slimehud.util.HudBuilder;
import io.github.schntgaispock.slimehud.waila.HudController;
import io.github.sefiraat.networks.network.stackcaches.QuantumCache;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumStorage;
import org.bukkit.Location;

public class HudCallbacks {
    public static void setup() {
        HudController controller = SlimeHUD.getHudController();
        controller.registerCustomHandler(NetworkQuantumStorage.class, request -> {
            Location location = request.getLocation();
            QuantumCache cache = NetworkQuantumStorage.getCaches().get(location);
            if (cache == null || cache.getItemStack() == null) {
                return "Empty";
            }

            String amount = HudBuilder.getAbbreviatedNumber(cache.getAmount());
            String limit = HudBuilder.getAbbreviatedNumber(cache.getLimit());
            String item = ItemUtils.getItemName(cache.getItemStack())
                    .replace("[", "").replace("]", "");
            return "&7| " + item + "&7|" + amount + "/" + limit;
        });
    }
}
