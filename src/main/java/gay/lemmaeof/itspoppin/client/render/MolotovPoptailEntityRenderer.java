package gay.lemmaeof.itspoppin.client.render;

import gay.lemmaeof.itspoppin.entity.MolotovPoptailEntity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class MolotovPoptailEntityRenderer extends FlyingItemEntityRenderer<MolotovPoptailEntity> {
	public MolotovPoptailEntityRenderer(EntityRendererFactory.Context ctx, float scale, boolean lit) {
		super(ctx, scale, lit);
	}

	public MolotovPoptailEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
	}
}
