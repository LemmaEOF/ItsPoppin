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
				ALOAD(2),
				INVOKEVIRTUAL("net/minecraft/item/ItemStack", "getCount", "()I")
		).jumpAfter();
		ctx.add(
				ALOAD(2),
				INVOKESTATIC("gay/lemmaeof/itspoppin/transformer/ItemRendererTransformer$Hooks", "bypassOnlyWithOneItem", "(ILnet/minecraft/item/ItemStack;)I")
		);
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
		public static int bypassOnlyWithOneItem(int orig, ItemStack stack) {
			if (stack.getItem() == PoppinItems.POPCORN_KERNEL && orig == 1) return 4;
			return orig;
		}

		public static String transformStackCountString(ItemStack stack, String orig) {
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
		private static boolean isInt(String str) {
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
}
