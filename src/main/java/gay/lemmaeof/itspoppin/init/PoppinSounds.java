package gay.lemmaeof.itspoppin.init;

import gay.lemmaeof.itspoppin.ItsPoppin;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class PoppinSounds implements Runnable {
	public static final SoundEvent POPCORN_POP = new SoundEvent(new Identifier(ItsPoppin.MODID, "popcorn_pop"));

	@Override
	public void run() {

	}
}
