![](https://github.com/Sefiraat/Networks/blob/master/images/logo/logo_large.png?raw=true)

網路是黏液科技的附加, 它帶來了簡單又強大的物品儲存和移動網路, 也可以與物流一起運作.

## Download Networks

[![Build Status](https://thebusybiscuit.github.io/builds/Sefiraat/Networks/master/badge.svg)](https://thebusybiscuit.github.io/builds/Sefiraat/Networks/master)

## About Networks

You can find a fuller guide to Networks including all items and blocks in my [Documentation Pages](https://sefiraat.gitbook.io/docs/)

![](https://github.com/Sefiraat/Networks/blob/master/images/wiki/setup.png?raw=true)

> 此為**非官方**版本, 請勿在該作者問題追蹤內回報! <br>
> [原作連結](https://github.com/Sefiraat/Networks) | [非官方Discord](https://discord.gg/GF4CwjFXT9)

| 非官方繁體中文版 | 官方英文版 |
| -------- | -------- |
| 點下方圖片下載 | 點下方圖片下載 |
| [![Build Status](https://xMikux.github.io/builds/SlimeTraditionalTranslation/Networks/master/badge.svg)](https://xMikux.github.io/builds/SlimeTraditionalTranslation/Networks/master) | [![Build Status](https://thebusybiscuit.github.io/builds/Sefiraat/Networks/master/badge.svg)](https://thebusybiscuit.github.io/builds/Sefiraat/Networks/master) |

## 網路格 / 網路製作格

它可以訪問網路中的每個物品並將它顯示在單個GUI介面上, 你可以一個或一組的拿出. 物品可以直接通過這個網路格放入, 另一個特殊的網路製作格可以直接使用來自網路內的物品來製作原版和黏液科技的物品.

![](https://github.com/Sefiraat/Networks/blob/master/images/wiki/grid.png?raw=true)

![](https://github.com/Sefiraat/Networks/blob/master/images/wiki/grid_crafting.png?raw=true)

## 網路橋

網路橋就像是普通的方塊, 用於幫助你便宜的擴展你的網路.

## 網路單元
網路單元是一個獨立的方塊, 可以在裡面容納大箱子的空間物品. 這些物品暴露給網路使用. 它們是為了你那些沒有多少貨不可堆疊/獨特物品所設計.

## 網路記憶體外殼
網路記憶體外殼是一個可以在其之中儲存物品的獨立方塊. 記憶卡可以儲存單一類型的大量物品. 從 4k 的物品數量開始, 最高可以升級到兩億. 外殼被認為是用於大規模生產物品的深度儲存桶.
可以在網路記憶體擦除器中將記憶卡內的物品擦除乾淨, 它將會慢慢地將記憶卡內的物品流回網路之中. 在想 升級/遷移 的時候很有用.

## 網路監控器
網路監控器是用來 "暴露" 連接方塊的儲存庫. 這是網路來查看網路外殼內部與其他附加插件的方式, 像是 無限附加 (Infinity Expansion) 的儲存單元.

## 網路 輸入/輸出 器
網路輸入器有一個九格欄位的儲存空間可供物流訪問並將物品放入其中. 如果有辦法的話, 輸入器將定期嘗試將這些物品從輸入器欄位中轉移置網路之中.
網路輸出器將單一物品當作範本, 並嘗試將匹配的物品從網路中提取到自身. 它的內部儲存空間可通過物流訪問.
這兩個方塊在需要時充當 物流/網路 之間的橋樑

## 網路 推送/抓取 器
網路抓取器將嘗試從相鄰的 Slimefun 機器內移出物品, 如果可能, 將嘗試直接移動到網路之中. 這適用於任何接受物流輸出的 Slimefun 機器.
網路推送器將單個物品當作範本, 並嘗試將物品從網路中提出, 並盡可能地將其推送到鄰近的 Slimefun 機器的輸入欄中.

## 電力
網路電容將從能量網路之中接收能量並將其儲存. 任何連接網路的機器都可以在需要時使用此電力.
總網路電力使用量可以在網路電量顯示器查看

## 自動化製作!
網路編碼器將使用給予的配方 ( 原版或 Slimefun ) 並將其編碼到一個空白藍圖中.
網路自動製作器將使用藍圖並嘗試直接使用網路內的物品定期製作它, 前提是有找到足夠的物品與能量足夠. 物品將會被輸入回網路之中.
預留版的網路自動製作器將做同樣的事情, 但會把製作出的物品保存在自己的儲存空間, 最多是一組, 並將這組物品暴露給網路以提供提取. 這對於保持物品只存在一組而不需要製作太多, 或者對於其他自動製作器的使用材料很有用.

## 網路遠端遙控
網路遠端遙控器是一個昂貴的製作品, 可以讓你無線訪問綁定的網路. 不同級別的遠端遙控可以訪問不同的最大範圍, 從150格遠, 到500格遠, 直至無限遠與跨維度.

## 垃圾回收
網路清理器將單個物品當作為範本, 並在網路中尋找匹配的物品, 並將其提取並銷毀. 請小心使用.

## 其他/雜類
- 網路蠟筆可以讓你打開/關閉來自機器的粒子, 向你顯示它們何時以及它們在做甚麼, 而不必打開GUI.
- 網路配置器可以讓你複製一個範本節點的設定, 並將設定貼上在另一個節點. 製作成本高, 但可以加快你的設定過程.
- 網路探測器將顯示連接的控制器的每個方塊, 包括其內容的摘要.


## Thanks!

As usual, a big thanks Boomer, Cai and Lucky for their help testing and refining networks

A big thanks to the owners of **mct.tantrum.org** who have tested nearly everything I have made and really given me direction and drive to make these things including some monster test setups of Networks to really push it into the dirt ;)

Another big shoutout to **GentlemanCheesy** of **mc.talosmp.net** for being my first (and as of writing this, only!) sponsor. A few coffee's a month to make me feel better about making these addons <3
