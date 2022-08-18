package gay.lemmaeof.itspoppin.init;

import gay.lemmaeof.itspoppin.ItsPoppin;

import net.minecraft.block.Block;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PoppinSounds {
	public static final SoundEvent POPCORN_POP = new SoundEvent(new Identifier(ItsPoppin.MODID, "popcorn_pop"));
	public static final SoundEvent POPSPLOSION = new SoundEvent(new Identifier(ItsPoppin.MODID, "popsplosion"));

	public static void init() {
		ItsPoppin.AUTOREG.autoRegister(Registry.SOUND_EVENT, PoppinSounds.class, SoundEvent.class);
	}
}
