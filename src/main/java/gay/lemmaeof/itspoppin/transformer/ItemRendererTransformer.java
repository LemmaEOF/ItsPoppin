package gay.lemmaeof.itspoppin.transformer;

import gay.lemmaeof.itspoppin.init.PoppinItems;
import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

import net.minecraft.item.ItemStack;

@Patch.Class("net/minecraft/client/render/item/ItemRenderer")
public class ItemRendererTransformer extends MiniTransformer {
	@Patch.Method("renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V")
	public void hookGuiNumber(PatchContext ctx) {
		ctx.search(
				ALOAD(6),
				DCONST_0(),
				DCONST_0()
		).jumpBefore();
		ctx.add(
				ALOAD(2),
				ALOAD(7),
				INVOKESTATIC("gay/lemmaeof/itspoppin/transformer/ItemRendererTransformer$Hooks", "transformStackCountString", "(Lnet/minecraft/item/ItemStack;Ljava/lang/String;)Ljava/lang/String;"),
				ASTORE(7)
		);
	}

	public static class Hooks {
		public static String transformStackCountString(ItemStack stack, String orig) {
			if (stack.getItem() == PoppinItems.POPCORN_KERNEL) {
				try {
					int i = Integer.parseInt(orig);
					return String.valueOf(i * 4);
				} catch (NumberFormatException e) {
					return orig;
				}
			} else {
				return orig;
			}
		}
	}
}
