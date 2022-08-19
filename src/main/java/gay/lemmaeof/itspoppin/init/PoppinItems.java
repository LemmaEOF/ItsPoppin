package gay.lemmaeof.itspoppin.init;

import gay.lemmaeof.itspoppin.ItsPoppin;
import gay.lemmaeof.itspoppin.item.CornCobItem;
import gay.lemmaeof.itspoppin.item.MolotovPoptailItem;

import gay.lemmaeof.itspoppin.item.PopcornBowlItem;
import gay.lemmaeof.itspoppin.item.PopcornSaltItem;
import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class PoppinItems {
	public static final Item CORN = new CornCobItem(new Item.Settings().group(ItemGroup.FOOD).fireproof());
	public static final Item CORN_KERNEL = new AliasedBlockItem(
			PoppinBlocks.CORN,
			new Item.Settings().group(ItemGroup.FOOD).fireproof()
	);
	public static final Item POPCORN_KERNEL = new Item(
			new Item.Settings().group(ItemGroup.FOOD).fireproof().food(
					new FoodComponent.Builder().snack().hunger(1).saturationModifier(0.1F).alwaysEdible().build()
			)
	);
	public static final Item MOLOTOV_POPTAIL = new MolotovPoptailItem(new Item.Settings().group(ItemGroup.COMBAT));
	public static final Item POPCORN_SALT = new PopcornSaltItem(new Item.Settings().group(ItemGroup.FOOD));
	public static final Item POPCORN_BOWL = new PopcornBowlItem(new Item.Settings()
			.group(ItemGroup.FOOD)
			.maxCount(16)
			.food(
					new FoodComponent.Builder().hunger(8).saturationModifier(0.8F).alwaysEdible().build()
			)
	);

	public static void init() {
		ItsPoppin.AUTOREG.autoRegister(Registry.ITEM, PoppinItems.class, Item.class);
	}
}
