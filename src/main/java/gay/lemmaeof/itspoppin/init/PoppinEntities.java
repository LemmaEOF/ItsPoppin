package gay.lemmaeof.itspoppin.init;

import gay.lemmaeof.aleph.one.annotate.Renderer;
import gay.lemmaeof.itspoppin.entity.MolotovPoptailEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

public class PoppinEntities implements Runnable {

	@Renderer("gay.lemmaeof.itspoppin.client.render.MolotovPoptailEntityRenderer")
	public static final EntityType<MolotovPoptailEntity> MOLOTOV_POPTAIL = EntityType.Builder
			.<MolotovPoptailEntity>create(MolotovPoptailEntity::new, SpawnGroup.MISC)
			.setDimensions(0.25F, 0.25F)
			.maxTrackingRange(4)
			.trackingTickInterval(10)
			.build("itspoppin:molotov_poptail");

	@Override
	public void run() {

	}
}
