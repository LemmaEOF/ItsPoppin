package gay.lemmaeof.itspoppin.compat.kahur;

import com.unascribed.kahur.api.KahurFiringBehavior;
import gay.lemmaeof.itspoppin.entity.MolotovPoptailEntity;
import gay.lemmaeof.itspoppin.init.PoppinItems;

public class KahurBehavior {
	public static void init() {
		KahurFiringBehavior.register((shooter, kahur, suggestedPos, suggestedVelocity, stack) -> {
			MolotovPoptailEntity poptail = new MolotovPoptailEntity(shooter, shooter.getWorld());
			poptail.setItem(stack);
			poptail.setPosition(suggestedPos);
			poptail.setVelocity(suggestedVelocity);
			return poptail;
		}, PoppinItems.MOLOTOV_POPTAIL);
	}
}
