package gay.lemmaeof.itspoppin.item;

import gay.lemmaeof.itspoppin.hooks.SpecialItemEntity;
import gay.lemmaeof.itspoppin.init.PoppinBlocks;
import gay.lemmaeof.itspoppin.init.PoppinItems;
import gay.lemmaeof.itspoppin.init.PoppinSounds;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;

public class CornCobItem extends Item implements SpecialItemEntity {
	public CornCobItem(Settings settings) {
		super(settings);
	}

	@Override
	public void tick(ItemEntity entity) {
		ItemStack stack = entity.getStack();
		BlockState blockState = entity.world.getBlockState(entity.getBlockPos());
		FluidState fluidState = entity.world.getFluidState(entity.getBlockPos());
		if (stack.getCount() > 1) return;
		if (!stack.hasNbt() && !blockState.isIn(PoppinBlocks.FIERY_BLOCKS) && !fluidState.isIn(PoppinBlocks.FIERY_FLUIDS)) return;
		NbtCompound stackData = stack.getOrCreateSubNbt("ItsPoppinStackData");
		NbtCompound entityData = stack.getOrCreateSubNbt("ItsPoppinEntityData");
		int heat = entityData.getInt("Heat");
		if (blockState.isIn(PoppinBlocks.FIERY_BLOCKS) || fluidState.isIn(PoppinBlocks.FIERY_FLUIDS)) {
			int kernelsPopped = stackData.getInt("KernelsPopped");
			if (heat > 60) {
				RandomGenerator rand = entity.world.getRandom();
				int goal = (((heat - 60) / 10) + (64 - kernelsPopped));
				double gauss = MathHelper.clamp(rand.nextGaussian() + 1.5, 0, 3) * 96;
				boolean popNew = gauss < goal;
				if (popNew && kernelsPopped < 64) {
					ItemEntity kernel = new ItemEntity(entity.world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(PoppinItems.POPCORN_KERNEL));
					kernel.setVelocity((rand.nextFloat() * 0.1) - 0.05, rand.nextFloat() * 0.5 + 0.1, (rand.nextFloat() * 0.1) - 0.05);
					entity.world.spawnEntity(kernel);
					((ServerWorld) kernel.world).spawnParticles(ParticleTypes.CLOUD, kernel.getX(), kernel.getY(), kernel.getZ(), 10, (rand.nextFloat() * 0.1) - 0.05, rand.nextFloat() * 0.25 + 0.1, (rand.nextFloat() * 0.1) - 0.05, 0.1);
					kernel.world.playSound(null, kernel.getX(), kernel.getY(), kernel.getZ(), PoppinSounds.POPCORN_POP, SoundCategory.BLOCKS, 1f, (rand.nextFloat() - rand.nextFloat()) * 0.1F + 1.0F);
					stackData.putInt("KernelsPopped", kernelsPopped + 1);
				}
			}
			if (heat < 700) entityData.putInt("Heat", heat + 1);
			if (kernelsPopped >= 64) {
				entity.world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
				entity.discard();
			}
		} else {
			if (heat > 0) entityData.putInt("Heat", heat - 1);
		}
	}

	@Override
	public ItemStack getPickupStack(ItemStack stack) {
		if (stack.hasNbt()) stack.removeSubNbt("ItsPoppinEntityData");
		return stack;
	}

	@Override
	public int getItemBarStep(ItemStack stack) {
		return Math.round(13.0F - (float)stack.getOrCreateSubNbt("ItsPoppinStackData").getInt("KernelsPopped") * 13.0F / 64);
	}

	@Override
	public int getItemBarColor(ItemStack stack) {
		return 0xFFF587;
	}

	@Override
	public boolean isItemBarVisible(ItemStack stack) {
		if (!stack.hasNbt()) return false;
		int kernels = stack.getOrCreateSubNbt("ItsPoppinStackData").getInt("KernelsPopped");
		return kernels > 0 && kernels < 64;
	}
}
