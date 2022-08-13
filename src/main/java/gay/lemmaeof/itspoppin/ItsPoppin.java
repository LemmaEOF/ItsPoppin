package gay.lemmaeof.itspoppin;

import gay.lemmaeof.itspoppin.transformer.ItemEntityTransformer;
import gay.lemmaeof.itspoppin.transformer.ItemRendererTransformer;
import nilloader.NilLoader;
import nilloader.api.ClassTransformer;
import nilloader.api.ModRemapper;
import nilloader.api.NilLogger;

public class ItsPoppin implements Runnable {

	public static final NilLogger log = NilLogger.get("It's Poppin'");
	
	@Override
	public void run() {
		log.info("It's POPPIN'!");

		if (tryRemap("net.fabricmc.loader.api.FabricLoader", "net.fabricmc.intermediary-1.19.2")) {
			//TODO: possible fabriquilt special support?
		} else if (tryRemap("cpw.mods.modlauncher.Launcher", "nilgradle.dynamic.frankenmapping-com.mojang.launcher.client-8e8c9be5dc27802caba47053d4fdea328f7f89bdxde.oceanlabs.mcp.mcp_config-1.19.2-v2-")) {
			//TODO: possible Forge special support?
		}

		NilLoader.registerTransformer(new ItemEntityTransformer());
		NilLoader.registerTransformer(new ItemRendererTransformer());
	}

	private boolean tryRemap(String smokeTest, String target) {
		try {
			Class.forName(smokeTest, false, ItsPoppin.class.getClassLoader());
			ModRemapper.setTargetMapping(target);
			return true;
		} catch (ClassNotFoundException t) {
			return false;
		}
	}

}