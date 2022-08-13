package gay.lemmaeof.itspoppin.init;

import gay.lemmaeof.itspoppin.item.CornCobItem;

import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class PoppinItems implements Runnable {
	public static final Item CORN = new CornCobItem(new Item.Settings().group(ItemGroup.FOOD).fireproof());
	public static final Item CORN_KERNEL = new AliasedBlockItem(
			PoppinBlocks.CORN_CROP,
			new Item.Settings().group(ItemGroup.FOOD).fireproof()
	);
	public static final Item POPCORN_KERNEL = new Item(
			new Item.Settings().group(ItemGroup.FOOD).fireproof().food(
					new FoodComponent.Builder().snack().hunger(1).saturationModifier(0.1f).alwaysEdible().build()
			)
	);

	@Override
	public void run() {

	}
}
