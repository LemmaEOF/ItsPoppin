package gay.lemmaeof.itspoppin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.ProjectileDamageSource;

public class PoppedDamageSource extends ProjectileDamageSource {
	public PoppedDamageSource(Entity projectile, Entity owner) {
		super("itspoppin.popped", projectile, owner);
	}
}
