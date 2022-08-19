package gay.lemmaeof.itspoppin.mixin;

import com.google.common.collect.ImmutableSet;
import gay.lemmaeof.itspoppin.init.PoppinItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//TODO: Mixin can't adjust flow control so these would never get replanted - readd to mixin json when I figure out a solution
@Mixin(VillagerEntity.class)
public abstract class MixinVillagerEntity extends MerchantEntity {
	public MixinVillagerEntity(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "canGather", at = @At("HEAD"), cancellable = true)
	private void allowCornGather(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
		if (stack.getItem() == PoppinItems.CORN || stack.getItem() == PoppinItems.CORN_KERNEL) info.setReturnValue(true);
	}

	@Inject(method = "hasSeedToPlant", at = @At("HEAD"), cancellable = true)
	private void allowCornPlant(CallbackInfoReturnable<Boolean> info) {
		if (this.getInventory().containsAny(ImmutableSet.of(PoppinItems.CORN, PoppinItems.CORN_KERNEL))) info.setReturnValue(true);
	}
}
