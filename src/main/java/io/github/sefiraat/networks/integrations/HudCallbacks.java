package io.github.sefiraat.networks.integrations;

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
    private static final String EMPTY = "&7| Empty";
    public static void setup() {
        HudController controller = SlimeHUD.getHudController();

        controller.registerCustomHandler(NetworkQuantumStorage.class, request -> {
            Location location = request.getLocation();
            QuantumCache cache = NetworkQuantumStorage.getCaches().get(location);
            if (cache == null || cache.getItemStack() == null) {
                return EMPTY;
            }

            return format(cache.getItemStack(), cache.getAmount(), cache.getLimit());
        });

        controller.registerCustomHandler(NetworkGreedyBlock.class, request -> {
            Location location = request.getLocation();
            BlockMenu menu = BlockStorage.getInventory(location);
            if (menu == null) {
                return EMPTY;
            }

            ItemStack templateStack = menu.getItemInSlot(NetworkGreedyBlock.TEMPLATE_SLOT);
            if (templateStack == null || templateStack.getType().isAir()) {
                return EMPTY;
            }

            ItemStack itemStack = menu.getItemInSlot(NetworkGreedyBlock.INPUT_SLOT);
            int amount = itemStack == null || itemStack.getType() != templateStack.getType() ? 0 : itemStack.getAmount();
            return format(templateStack, amount, templateStack.getMaxStackSize());
        });
    }

    private static String format(ItemStack itemStack, int amount, int limit) {
        ItemMeta meta = itemStack.getItemMeta();
        String amountStr = HudBuilder.getAbbreviatedNumber(amount);
        String limitStr = HudBuilder.getAbbreviatedNumber(limit);
        String itemName = meta != null && meta.hasDisplayName()
                ? meta.getDisplayName()
                : ChatUtils.humanize(itemStack.getType().name());

        return "&7| &f" + itemName + " &7| " + amountStr + "/" + limitStr;
    }
}
