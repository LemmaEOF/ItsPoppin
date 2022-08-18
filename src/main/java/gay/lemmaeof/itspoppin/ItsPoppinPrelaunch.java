package gay.lemmaeof.itspoppin;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class ItsPoppinPrelaunch implements PreLaunchEntrypoint {
	@Override
	public void onPreLaunch(ModContainer mod) {
		MixinExtrasBootstrap.init();
	}
}
