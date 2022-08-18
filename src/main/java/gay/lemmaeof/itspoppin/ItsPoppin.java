package gay.lemmaeof.itspoppin;

import com.unascribed.lib39.core.api.AutoRegistry;
import gay.lemmaeof.itspoppin.init.PoppinBlocks;
import gay.lemmaeof.itspoppin.init.PoppinEntities;
import gay.lemmaeof.itspoppin.init.PoppinItems;
import gay.lemmaeof.itspoppin.init.PoppinSounds;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItsPoppin implements ModInitializer {
	public static final String MODID = "itspoppin";

	public static final Logger log = LoggerFactory.getLogger("It's Poppin'");

	public static final AutoRegistry AUTOREG = AutoRegistry.of(MODID);

	@Override
	public void onInitialize(ModContainer mod) {
		PoppinBlocks.init();
		PoppinEntities.init();
		PoppinItems.init();
		PoppinSounds.init();
	}

}
