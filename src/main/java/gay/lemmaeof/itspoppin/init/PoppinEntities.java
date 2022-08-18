package gay.lemmaeof.itspoppin.init;

import gay.lemmaeof.itspoppin.ItsPoppin;
import gay.lemmaeof.itspoppin.entity.MolotovPoptailEntity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class PoppinEntities {

	public static final EntityType<MolotovPoptailEntity> MOLOTOV_POPTAIL = EntityType.Builder
			.<MolotovPoptailEntity>create(MolotovPoptailEntity::new, SpawnGroup.MISC)
			.setDimensions(0.25F, 0.25F)
			.maxTrackingRange(4)
			.trackingTickInterval(10)
			.build("itspoppin:molotov_poptail");

	public static void init() {
		ItsPoppin.AUTOREG.autoRegister(Registry.ENTITY_TYPE, PoppinEntities.class, EntityType.class);
	}
}
