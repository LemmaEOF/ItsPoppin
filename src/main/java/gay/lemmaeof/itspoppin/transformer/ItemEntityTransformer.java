package gay.lemmaeof.itspoppin.transformer;

import gay.lemmaeof.itspoppin.hooks.SpecialItemEntity;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;

@Patch.Class("net.minecraft.entity.ItemEntity")
public class ItemEntityTransformer extends MiniTransformer {
	@Patch.Method("tick()V")
	public void hookTick(PatchContext ctx) {
		ctx.search(
				ALOAD(0),
				GETFIELD("net/minecraft/entity/ItemEntity", "world", "Lnet/minecraft/world/World;"),
				GETFIELD("net/minecraft/world/World", "isClient", "Z"),
				IFNE(new LabelNode()),
				ALOAD(0),
				GETFIELD("net/minecraft/entity/ItemEntity", "itemAge", "I")
		).jumpBefore();
		ctx.add(
				ALOAD(0),
				INVOKESTATIC("gay/lemmaeof/itspoppin/transformer/ItemEntityTransformer$Hooks", "tickIfAble", "(Lnet/minecraft/entity/ItemEntity;)V")
		);
	}

	@Patch.Method("onPlayerCollision(Lnet/minecraft/entity/player/PlayerEntity;)V")
	public void hookPickup(PatchContext ctx) {
		ctx.search(
				INVOKEVIRTUAL("net/minecraft/entity/ItemEntity", "getStack", "()Lnet/minecraft/item/ItemStack;")
		).jumpAfter();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/itspoppin/transformer/ItemEntityTransformer$Hooks", "replaceItemStack", "(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;")
		);
	}

	public static class Hooks {
		public static void tickIfAble(ItemEntity entity) {
			if (entity.getStack().getItem() instanceof SpecialItemEntity item && !entity.world.isClient) {
				item.tick(entity);
			}
		}

		public static ItemStack replaceItemStack(ItemStack stack) {
			if (stack.getItem() instanceof SpecialItemEntity item) {
				return item.getPickupStack(stack);
			}
			return stack;
		}
	}
}
