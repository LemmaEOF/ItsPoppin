package gay.lemmaeof.itspoppin;

import gay.lemmaeof.itspoppin.init.PoppinBlocks;
import gay.lemmaeof.itspoppin.init.PoppinEntities;
import gay.lemmaeof.itspoppin.init.PoppinItems;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

public class ItsPoppinClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		BlockRenderLayerMap.put(RenderLayer.getCutoutMipped(), PoppinBlocks.CORN);
		EntityRendererRegistry.register(PoppinEntities.MOLOTOV_POPTAIL, FlyingItemEntityRenderer::new);
		ModelPredicateProviderRegistry.register(
				PoppinItems.POPCORN_BOWL,
				new Identifier(ItsPoppin.MODID, "salted"),
				(stack, world, entity, seed) -> PotionUtil.getPotion(stack) == Potions.EMPTY? 0 : 1);
		ColorProviderRegistry.ITEM.register((stack, layer) -> layer == 1? PotionUtil.getColor(stack) : 0xFFFFFF, PoppinItems.POPCORN_BOWL);
		ColorProviderRegistry.ITEM.register((stack, layer) -> PotionUtil.getPotion(stack) != Potions.EMPTY? PotionUtil.getColor(stack) : 0xFFFFFF, PoppinItems.POPCORN_SALT);
	}
}
