package io.github.sefiraat.networks.slimefun;

import io.github.sefiraat.networks.slimefun.network.NetworkMemoryWiper;
import io.github.sefiraat.networks.slimefun.tools.NetworkCard;
import io.github.sefiraat.networks.utils.Theme;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.text.MessageFormat;

/**
 * Creating SlimefunItemstacks here due to some items being created in Enums so this will
 * act as a one-stop-shop for the stacks themselves.
 */
@UtilityClass
public class NetworksSlimefunItemStacks {

    // Materials
    public static final SlimefunItemStack OPTIC_GLASS;
    public static final SlimefunItemStack OPTIC_CABLE;
    public static final SlimefunItemStack OPTIC_STAR;
    public static final SlimefunItemStack RADIOACTIVE_OPTIC_STAR;

    // Network Items
    public static final SlimefunItemStack NETWORK_CONTROLLER;
    public static final SlimefunItemStack NETWORK_BRIDGE;
    public static final SlimefunItemStack NETWORK_MONITOR;
    public static final SlimefunItemStack NETWORK_IMPORT;
    public static final SlimefunItemStack NETWORK_EXPORT;
    public static final SlimefunItemStack NETWORK_GRABBER;
    public static final SlimefunItemStack NETWORK_PUSHER;
    public static final SlimefunItemStack NETWORK_PURGER;
    public static final SlimefunItemStack NETWORK_GRID;
    public static final SlimefunItemStack NETWORK_CRAFTING_GRID;
    public static final SlimefunItemStack NETWORK_CELL;
    public static final SlimefunItemStack NETWORK_MEMORY_SHELL;
    public static final SlimefunItemStack NETWORK_MEMORY_WIPER_1;
    public static final SlimefunItemStack NETWORK_MEMORY_WIPER_2;
    public static final SlimefunItemStack NETWORK_MEMORY_WIPER_3;
    public static final SlimefunItemStack NETWORK_MEMORY_WIPER_4;

    // Tools
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_1;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_2;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_3;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_4;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_5;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_6;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_7;
    public static final SlimefunItemStack NETWORK_MEMORY_CARD_8;
    public static final SlimefunItemStack NETWORK_PROBE;

    static {

        OPTIC_GLASS = Theme.themedSlimefunItemStack(
            "NTW_OPTIC_GLASS",
            new ItemStack(Material.GLASS),
            Theme.CRAFTING,
            "光學玻璃",
            "一個簡單的玻璃,",
            "能夠傳輸少量位元的資訊."
        );

        OPTIC_CABLE = Theme.themedSlimefunItemStack(
            "NTW_OPTIC_CABLE",
            new ItemStack(Material.STRING),
            Theme.CRAFTING,
            "光纜",
            "一個簡單的線纜,",
            "能夠傳輸大量位元的資訊."
        );

        OPTIC_STAR = Theme.themedSlimefunItemStack(
            "NTW_OPTIC_STAR",
            new ItemStack(Material.NETHER_STAR),
            Theme.CRAFTING,
            "光學之星",
            "一種結晶星狀結構,",
            "能夠傳輸大量位元的資訊."
        );

        RADIOACTIVE_OPTIC_STAR = Theme.themedSlimefunItemStack(
            "NTW_RADIOACTIVE_OPTIC_STAR",
            getPreEnchantedItemStack(Material.NETHER_STAR, true, new Pair<>(Enchantment.ARROW_DAMAGE, 1)),
            Theme.CRAFTING,
            "輻射光學之星",
            "一種結晶星狀結構,",
            "能夠儲存瘋狂數量的位元的資訊."
        );

        NETWORK_CONTROLLER = Theme.themedSlimefunItemStack(
            "NTW_CONTROLLER",
            new ItemStack(Material.BLACK_STAINED_GLASS),
            Theme.MACHINE,
            "網路控制器",
            "網路控制器是整個",
            "網路的大腦. 每個網路只能有一個."
        );

        NETWORK_BRIDGE = Theme.themedSlimefunItemStack(
            "NTW_BRIDGE",
            new ItemStack(Material.WHITE_STAINED_GLASS),
            Theme.MACHINE,
            "網路橋",
            "網路橋可以讓便宜的將",
            "你要接的網路對象接在一起."
        );

        NETWORK_MONITOR = Theme.themedSlimefunItemStack(
            "NTW_MONITOR",
            new ItemStack(Material.GREEN_STAINED_GLASS),
            Theme.MACHINE,
            "網路監控器",
            "網路監控器可以讓相鄰",
            "對象進行簡單的 輸入/輸出",
            "互動.",
            "",
            "目前支持:",
            "無限附加 - 儲存單元"
        );

        NETWORK_IMPORT = Theme.themedSlimefunItemStack(
            "NTW_IMPORT",
            new ItemStack(Material.RED_STAINED_GLASS),
            Theme.MACHINE,
            "網路輸入器",
            "網路輸入器將在",
            "其內部的任何物品, 輸入進網路,",
            "每黏液科技的tick 最多可以儲存九組.",
            "接受來自物流的物品."
        );

        NETWORK_EXPORT = Theme.themedSlimefunItemStack(
            "NTW_EXPORT",
            new ItemStack(Material.BLUE_STAINED_GLASS),
            Theme.MACHINE,
            "網路輸出器",
            "網路輸出器可以",
            "設置為不斷導出任何",
            "所指定的物品一組.",
            "接受從物流中提取的物品."
        );

        NETWORK_GRABBER = Theme.themedSlimefunItemStack(
            "NTW_GRABBER",
            new ItemStack(Material.MAGENTA_STAINED_GLASS),
            Theme.MACHINE,
            "網路抓取器",
            "網路抓取器將嘗試從",
            "選定的機器中抓取",
            "它第一個找到的物品."
        );

        NETWORK_PUSHER = Theme.themedSlimefunItemStack(
            "NTW_PUSHER",
            new ItemStack(Material.BROWN_STAINED_GLASS),
            Theme.MACHINE,
            "網路推送器",
            "網路推送器將嘗試",
            "推送匹配的物品",
            "到所選的機器中."
        );

        NETWORK_PURGER = Theme.themedSlimefunItemStack(
            "NTW_TRASH",
            new ItemStack(Material.OBSERVER),
            Theme.MACHINE,
            "網路清理器",
            "網路清理器將從",
            "網路中拉出匹配的物品,",
            "並立即丟入虛空銷毀.",
            "請小心使用!"
        );

        NETWORK_GRID = Theme.themedSlimefunItemStack(
            "NTW_GRID",
            new ItemStack(Material.NOTE_BLOCK),
            Theme.MACHINE,
            "網路格",
            "網路格可以顯示",
            "你所有在網路內的物品,",
            "並讓你直接輸入或輸出",
            "物品."
        );

        NETWORK_CRAFTING_GRID = Theme.themedSlimefunItemStack(
            "NTW_CRAFTING_GRID",
            new ItemStack(Material.REDSTONE_LAMP),
            Theme.MACHINE,
            "網路製作格",
            "網路製作格類似普通的網路格,",
            "但顯示的物品更少,",
            "可以允許你直接使用來自",
            "網路內的物品進行合成."
        );

        NETWORK_CELL = Theme.themedSlimefunItemStack(
            "NTW_CELL",
            new ItemStack(Material.HONEYCOMB_BLOCK),
            Theme.MACHINE,
            "網路單元",
            "網路單元是一個大型的",
            "儲存空間 (大型儲物箱),",
            "可以從網路與世界上",
            "進行訪問."
        );

        NETWORK_MEMORY_SHELL = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_SHELL",
            new ItemStack(Material.DEEPSLATE_TILES),
            Theme.MACHINE,
            "網路記憶體外殼",
            "網路外殼是一個方塊,",
            "提供網路訪問",
            "記憶卡內的東西."
        );

        NETWORK_MEMORY_WIPER_1 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_WIPER_1",
            new ItemStack(Material.BASALT),
            Theme.MACHINE,
            "網路記憶體擦除器 α",
            "網路擦除器是一個方塊,",
            "它將會緩慢的嘗試將",
            "記憶卡內的內容清空",
            "至網路中.",
            "",
            MessageFormat.format("{0}速度: {1}{2} 組/t", Theme.CLICK_INFO, Theme.PASSIVE, NetworkMemoryWiper.STACKS_TO_PUSH[0])
        );

        NETWORK_MEMORY_WIPER_2 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_WIPER_2",
            new ItemStack(Material.POLISHED_BASALT),
            Theme.MACHINE,
            "網路記憶體擦除器 β",
            "網路擦除器是一個方塊,",
            "它將會緩慢的嘗試將",
            "記憶卡內的內容清空",
            "至網路中.",
            "",
            MessageFormat.format("{0}速度: {1}{2} 組/t", Theme.CLICK_INFO, Theme.PASSIVE, NetworkMemoryWiper.STACKS_TO_PUSH[1])
        );

        NETWORK_MEMORY_WIPER_3 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_WIPER_3",
            new ItemStack(Material.SMOOTH_BASALT),
            Theme.MACHINE,
            "網路記憶體擦除器 γ",
            "網路擦除器是一個方塊,",
            "它將會緩慢的嘗試將",
            "記憶卡內的內容清空",
            "至網路中.",
            "",
            MessageFormat.format("{0}速度: {1}{2} 組/t", Theme.CLICK_INFO, Theme.PASSIVE, NetworkMemoryWiper.STACKS_TO_PUSH[2])
        );

        NETWORK_MEMORY_WIPER_4 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_WIPER_4",
            new ItemStack(Material.POLISHED_BLACKSTONE),
            Theme.MACHINE,
            "網路記憶體擦除器 δ",
            "網路擦除器是一個方塊,",
            "它將會緩慢的嘗試將",
            "記憶卡內的內容清空",
            "至網路中.",
            "",
            MessageFormat.format("{0}速度: {1}{2} 組/t", Theme.CLICK_INFO, Theme.PASSIVE, NetworkMemoryWiper.STACKS_TO_PUSH[3])
        );

        NETWORK_MEMORY_CARD_1 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_1",
            new ItemStack(Material.LIGHT_GRAY_DYE),
            Theme.TOOL,
            "網路記憶卡 (4K)",
            "儲存 " + NetworkCard.SIZES[0] + " 個物品",
            "",
            "右鍵點擊來設定",
            "你副手所放的物品.",
            "卡片必須是空的才能設定物品.",
            "",
            Theme.WARNING + "超過儲存限制的物品將會被傳入虛空.",
            Theme.WARNING + "升級將會抹除記憶體.",
            "",
            Theme.WARNING + "空"
        );

        NETWORK_MEMORY_CARD_2 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_2",
            new ItemStack(Material.GRAY_DYE),
            Theme.TOOL,
            "網路記憶卡 (32K)",
            "儲存 " + NetworkCard.SIZES[1] + " 個物品",
            "",
            "右鍵點擊來設定",
            "你副手所放的物品.",
            "卡片必須是空的才能設定物品.",
            "",
            Theme.WARNING + "超過儲存限制的物品將會被傳入虛空.",
            Theme.WARNING + "升級將會抹除記憶體.",
            "",
            Theme.WARNING + "空"
        );

        NETWORK_MEMORY_CARD_3 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_3",
            new ItemStack(Material.LIME_DYE),
            Theme.TOOL,
            "網路記憶卡 (262K)",
            "儲存 " + NetworkCard.SIZES[2] + " 個物品",
            "",
            "右鍵點擊來設定",
            "你副手所放的物品.",
            "卡片必須是空的才能設定物品.",
            "",
            Theme.WARNING + "超過儲存限制的物品將會被傳入虛空.",
            Theme.WARNING + "升級將會抹除記憶體.",
            "",
            Theme.WARNING + "空"
        );

        NETWORK_MEMORY_CARD_4 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_4",
            new ItemStack(Material.GREEN_DYE),
            Theme.TOOL,
            "網路記憶卡 (2M)",
            "儲存 " + NetworkCard.SIZES[3] + " 個物品",
            "",
            "右鍵點擊來設定",
            "你副手所放的物品.",
            "卡片必須是空的才能設定物品.",
            "",
            Theme.WARNING + "超過儲存限制的物品將會被傳入虛空.",
            Theme.WARNING + "升級將會抹除記憶體.",
            "",
            Theme.WARNING + "空"
        );

        NETWORK_MEMORY_CARD_5 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_5",
            new ItemStack(Material.LIGHT_BLUE_DYE),
            Theme.TOOL,
            "網路記憶卡 (16M)",
            "儲存 " + NetworkCard.SIZES[4] + " 個物品",
            "",
            "右鍵點擊來設定",
            "你副手所放的物品.",
            "卡片必須是空的才能設定物品.",
            "",
            Theme.WARNING + "超過儲存限制的物品將會被傳入虛空.",
            Theme.WARNING + "升級將會抹除記憶體.",
            "",
            Theme.WARNING + "空"
        );

        NETWORK_MEMORY_CARD_6 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_6",
            new ItemStack(Material.BLUE_DYE),
            Theme.TOOL,
            "網路記憶卡 (134M)",
            "儲存 " + NetworkCard.SIZES[5] + " 個物品",
            "",
            "右鍵點擊來設定",
            "你副手所放的物品.",
            "卡片必須是空的才能設定物品.",
            "",
            Theme.WARNING + "超過儲存限制的物品將會被傳入虛空.",
            Theme.WARNING + "升級將會抹除記憶體.",
            "",
            Theme.WARNING + "空"
        );

        NETWORK_MEMORY_CARD_7 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_7",
            new ItemStack(Material.PINK_DYE),
            Theme.TOOL,
            "網路記憶卡 (1B)",
            "儲存 " + NetworkCard.SIZES[6] + " 個物品",
            "",
            "右鍵點擊來設定",
            "你副手所放的物品.",
            "卡片必須是空的才能設定物品.",
            "",
            Theme.WARNING + "超過儲存限制的物品將會被傳入虛空.",
            Theme.WARNING + "升級將會抹除記憶體.",
            "",
            Theme.WARNING + "空"
        );

        NETWORK_MEMORY_CARD_8 = Theme.themedSlimefunItemStack(
            "NTW_MEMORY_CARD_8",
            new ItemStack(Material.RED_DYE),
            Theme.TOOL,
            "網路記憶卡 (∞)",
            "儲存 ∞ 個物品... 幾乎是",
            "",
            "右鍵點擊來設定",
            "你副手所放的物品.",
            "卡片必須是空的才能設定物品.",
            "",
            Theme.WARNING + "超過儲存限制的物品將會被傳入虛空.",
            Theme.WARNING + "升級將會抹除記憶體.",
            "",
            Theme.WARNING + "空"
        );

        NETWORK_PROBE = Theme.themedSlimefunItemStack(
            "NTW_PROBE",
            new ItemStack(Material.CLOCK),
            Theme.TOOL,
            "網路探測器",
            "當使用在控制器上時,",
            "將會顯示這個網路上所有的節點資訊."
        );
    }

    @Nonnull
    @SafeVarargs
    public static ItemStack getPreEnchantedItemStack(Material material, boolean hide, @Nonnull Pair<Enchantment, Integer>... enchantments) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (Pair<Enchantment, Integer> pair : enchantments) {
            itemMeta.addEnchant(pair.getFirstValue(), pair.getSecondValue(), true);
        }
        if (hide) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
