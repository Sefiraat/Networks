package io.github.sefiraat.networks.slimefun;

import io.github.schntgaispock.slimehud.SlimeHUD;
import io.github.schntgaispock.slimehud.util.HudBuilder;
import io.github.schntgaispock.slimehud.waila.HudController;
import io.github.sefiraat.networks.network.stackcaches.QuantumCache;
import io.github.sefiraat.networks.slimefun.network.NetworkGreedyBlock;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumStorage;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HudCallbacks {
    public static void setup() {
        HudController controller = SlimeHUD.getHudController();

        controller.registerCustomHandler(NetworkQuantumStorage.class, request -> {
            Location location = request.getLocation();
            QuantumCache cache = NetworkQuantumStorage.getCaches().get(location);
            if (cache == null || cache.getItemStack() == null) {
                return "&7| Empty";
            }

            return format(cache.getItemStack(), cache.getAmount(), cache.getLimit());
        });

        controller.registerCustomHandler(NetworkGreedyBlock.class, request -> {
            Location location = request.getLocation();
            BlockMenu menu = BlockStorage.getInventory(location);
            if (menu == null) {
                return "&7| Empty";
            }

            ItemStack template = menu.getItemInSlot(NetworkGreedyBlock.TEMPLATE_SLOT);
            if (template == null || template.getType().isAir()) {
                return "&7| Empty";
            }

            ItemStack item = menu.getItemInSlot(NetworkGreedyBlock.INPUT_SLOT);
            int amount = item == null || item.getType().isAir() ? 0 : item.getAmount();
            return format(template, amount, template.getMaxStackSize());
        });
    }

    private static String format(ItemStack item, int amount, int limit) {
        ItemMeta meta = item.getItemMeta();
        String amountStr = HudBuilder.getAbbreviatedNumber(amount);
        String limitStr = HudBuilder.getAbbreviatedNumber(limit);

        if (meta != null && meta.hasDisplayName()) {
            return "&7| " + meta.getDisplayName() + " &7| " + amountStr + "/" + limitStr;
        } else {
            return "&7| &f" + ChatUtils.humanize(item.getType().name()) + " &7| " + amountStr + "/" + limitStr;
        }
    }
}
