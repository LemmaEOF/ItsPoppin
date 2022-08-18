package gay.lemmaeof.itspoppin.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import gay.lemmaeof.itspoppin.hooks.SpecialItemEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity extends Entity {
	@Shadow public abstract ItemStack getStack();

	public MixinItemEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/entity/ItemEntity.updateWaterState()Z"))
	private void hookTick(CallbackInfo info) {
		if (this.getStack().getItem() instanceof SpecialItemEntity special && !this.getWorld().isClient) {
			special.tick((ItemEntity)(Object)this);
		}
	}

	@ModifyExpressionValue(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "net/minecraft/entity/ItemEntity.getStack()Lnet/minecraft/item/ItemStack;"))
	private ItemStack modifyStackWithPickup(ItemStack orig) {
		if (orig.getItem() instanceof SpecialItemEntity special) {
			return special.getPickupStack(orig);
		}
		return orig;
	}
}
