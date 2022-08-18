package gay.lemmaeof.itspoppin.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import gay.lemmaeof.itspoppin.init.PoppinItems;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
	private final ThreadLocal<ItemStack> stackLocal = new ThreadLocal<>();

	@Inject(method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At("HEAD"))
	private void grabItemStack(TextRenderer renderer, ItemStack stack, int x, int y, String countLabel, CallbackInfo info) {
		this.stackLocal.set(stack);
	}

	@ModifyExpressionValue(method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "net/minecraft/item/ItemStack.getCount()I", ordinal = 0))
	private int alwaysRenderNumberForPopcorn(int original) {
		if (stackLocal.get().getItem() == PoppinItems.POPCORN_KERNEL && original == 1) return 4;
		return original;
	}

	@ModifyArg(method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "net/minecraft/client/font/TextRenderer.draw(Ljava/lang/String;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I"))
	private String correctNumberForDraw(String orig) {
		return correctNumber(orig);
	}

	@ModifyArg(method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "net/minecraft/client/font/TextRenderer.getWidth(Ljava/lang/String;)I"))
	private String correctNumberForWidth(String orig) {
		return correctNumber(orig);
	}

	private String correctNumber(String orig) {
		ItemStack stack = stackLocal.get();
		if (stack.getItem() == PoppinItems.POPCORN_KERNEL) {
			if (orig.startsWith("\u00a7")) { //§
				//should catch the edge case in vanilla with yellow text, may not work with other mods
				String formatting = orig.substring(0, 2);
				String contents = orig.substring(2);
				if (isInt(contents)) {
					int i = Integer.parseInt(contents);
					return formatting + (i * 4);
				}
			} else if (isInt(orig)) {
				int i = Integer.parseInt(orig);
				return String.valueOf(i * 4);
			}
		}
		return orig;
	}

	//I could do radix checking but I *really* don't need to because Minecraft by default only uses decimal
	private boolean isInt(String str) {
		if (str.isEmpty()) return false;
		boolean firstChar = true;
		for (char c : str.toCharArray()) {
			if (firstChar) {
				firstChar = false;
				if (c == '-') continue;
			}
			if (c < '0' || c > '9') return false;
		}
		return true;
	}
}
