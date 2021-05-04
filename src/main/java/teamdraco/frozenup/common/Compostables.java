package teamdraco.frozenup.common;

import teamdraco.frozenup.init.FrozenUpItems;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemConvertible;

public class Compostables {
	public static void registerCompostables() {
		registerCompostable(0.3F, FrozenUpItems.TRUFFLE);
		registerCompostable(0.3F, FrozenUpItems.TRUFFLE_MUFFIN);
		registerCompostable(0.5F, FrozenUpItems.TRUFFLE_CAKE);
	}
	
	public static void registerCompostable(float chance, ItemConvertible item) {
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(item.asItem(), chance);
	}
}