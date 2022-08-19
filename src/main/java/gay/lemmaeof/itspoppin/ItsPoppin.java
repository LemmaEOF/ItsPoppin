package gay.lemmaeof.itspoppin;

import com.unascribed.lib39.core.api.AutoRegistry;
import gay.lemmaeof.itspoppin.compat.kahur.KahurBehavior;
import gay.lemmaeof.itspoppin.init.*;
import io.github.tropheusj.milk.Milk;
import io.github.tropheusj.milk.MilkCauldron;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItsPoppin implements ModInitializer {
	public static final String MODID = "itspoppin";

	public static final Logger log = LoggerFactory.getLogger("It's Poppin'!");

	public static final AutoRegistry AUTOREG = AutoRegistry.of(MODID);

	@Override
	public void onInitialize(ModContainer mod) {
		PoppinBlocks.init();
		PoppinEntities.init();
		PoppinItems.init();
		PoppinRecipes.init();
		PoppinSounds.init();
		Milk.enableCauldron();

		CauldronBehavior behavior = ((state, world, pos, player, hand, stack) -> {
			ItemStack saltStack = new ItemStack(PoppinItems.POPCORN_SALT);
			Potion pot = PotionUtil.getPotion(stack);
			if (pot != Potions.WATER) {
				PotionUtil.setPotion(saltStack, pot);
			}
			player.getInventory().offerOrDrop(saltStack);
			if (!player.isCreative()) player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE), false));
			world.playSound(null, pos, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1f, 1f);
			world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
			return ActionResult.CONSUME;
		});
		MilkCauldron.addBehavior(behavior, Items.POTION);

		if (QuiltLoader.isModLoaded("kahur")) {
			KahurBehavior.init();
		}
	}

}
