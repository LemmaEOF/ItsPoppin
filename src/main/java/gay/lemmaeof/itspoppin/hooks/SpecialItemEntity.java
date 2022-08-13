package gay.lemmaeof.itspoppin.hooks;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;

public interface SpecialItemEntity {
	void tick(ItemEntity entity);

	ItemStack getPickupStack(ItemStack stack);
}
