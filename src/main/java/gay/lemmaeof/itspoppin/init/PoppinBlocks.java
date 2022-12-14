package gay.lemmaeof.itspoppin.init;

import java.util.List;

import gay.lemmaeof.itspoppin.ItsPoppin;
import gay.lemmaeof.itspoppin.block.CornCropBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PoppinBlocks {
	public static final TagKey<Block> FIERY_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier("itspoppin:is_fiery"));
	public static final TagKey<Fluid> FIERY_FLUIDS =  TagKey.of(Registry.FLUID_KEY, new Identifier("itspoppin:is_fiery"));
	//TODO: turn into a tag later, maybe? gonna have to restructure some stuff for that since I'm practically doing (non-dangerous) reg-rep
	public static final List<String> ADD_CORN_TO = List.of("zombie_savanna", "farm_savanna");

	public static final Block CORN = new CornCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));

	public static void init() {
		ItsPoppin.AUTOREG.autoRegister(Registry.BLOCK, PoppinBlocks.class, Block.class);
	}
}
