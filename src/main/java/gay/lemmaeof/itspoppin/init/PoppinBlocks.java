package gay.lemmaeof.itspoppin.init;

import gay.lemmaeof.itspoppin.block.CornCropBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PoppinBlocks implements Runnable {
	public static final TagKey<Block> FIERY_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier("itspoppin:is_fiery"));
	public static final TagKey<Fluid> FIERY_FLUIDS =  TagKey.of(Registry.FLUID_KEY, new Identifier("itspoppin:is_fiery"));

	public static final Block CORN_CROP = new CornCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));

	@Override
	public void run() {

	}
}
