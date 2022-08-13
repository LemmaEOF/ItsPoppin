package gay.lemmaeof.itspoppin.block;

import gay.lemmaeof.itspoppin.init.PoppinItems;

import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class CornCropBlock extends CropBlock {
	public CornCropBlock(Settings settings) {
		super(settings);
	}

	@Override
	public ItemConvertible getSeedsItem() {
		return PoppinItems.CORN_KERNEL;
	}

	@Override
	public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
		if (this.getSeedsItem().asItem().isInGroup(group)) {
			stacks.add(new ItemStack(this.getSeedsItem()));
		}
	}
}
