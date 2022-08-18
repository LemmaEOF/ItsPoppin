package gay.lemmaeof.itspoppin;

import gay.lemmaeof.itspoppin.client.render.MolotovPoptailEntityRenderer;
import gay.lemmaeof.itspoppin.init.PoppinBlocks;
import gay.lemmaeof.itspoppin.init.PoppinEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

public class ItsPoppinClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		BlockRenderLayerMap.put(RenderLayer.getCutoutMipped(), PoppinBlocks.CORN);
		EntityRendererRegistry.register(PoppinEntities.MOLOTOV_POPTAIL, MolotovPoptailEntityRenderer::new);
	}
}
