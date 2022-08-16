package gay.lemmaeof.itspoppin.entity;

import gay.lemmaeof.itspoppin.init.PoppinEntities;
import gay.lemmaeof.itspoppin.init.PoppinItems;
import gay.lemmaeof.itspoppin.init.PoppinSounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;

public class MolotovPoptailEntity extends ThrownItemEntity {
	public MolotovPoptailEntity(EntityType<MolotovPoptailEntity> type, World world) {
		super(type, world);
	}

	public MolotovPoptailEntity(double x, double y, double z, World world) {
		super(PoppinEntities.MOLOTOV_POPTAIL, x, y, z, world);
	}

	public MolotovPoptailEntity(LivingEntity owner, World world) {
		super(PoppinEntities.MOLOTOV_POPTAIL, owner, world);
	}

	@Override
	public Item getDefaultItem() {
		return PoppinItems.MOLOTOV_POPTAIL;
	}

	public void handleStatus(byte status) {
		if (status == 3) {
			ParticleEffect effect = new ItemStackParticleEffect(ParticleTypes.ITEM, this.getItem());

			for(int i = 0; i < 8; ++i) {
				this.world.addParticle(effect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
			}
		}

	}

	@Override
	public void onEntityHit(EntityHitResult hit) {
		super.onEntityHit(hit);
		Entity entity = hit.getEntity();
		entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 2.0F);
		if (!this.world.isClient) {
			createPopsplosion();
		}
	}

	@Override
	public void onCollision(HitResult hit) {
		super.onCollision(hit);
		if (!this.world.isClient) {
			this.world.sendEntityStatus(this, (byte)3);
			createPopsplosion();
			this.discard();
		}
	}

	private void createPopsplosion() {
		RandomGenerator rand = this.world.getRandom();
		world.playSound(null, this.getX(), this.getY(), this.getZ(), PoppinSounds.POPSPLOSION, SoundCategory.BLOCKS, 1.0F, 1.0F);
		((ServerWorld) this.world).spawnParticles(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), 20, (rand.nextFloat() * 0.25) - 0.125, rand.nextFloat() * 0.5 + 0.1, (rand.nextFloat() * 0.25) - 0.125, 0.5);
		ItemStack item = this.getItem();
		if (!item.getOrCreateNbt().getBoolean("NoPops")) {
			for (int i = 0; i < 8; i++) {
				ItemEntity kernel = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), new ItemStack(PoppinItems.POPCORN_KERNEL));
				kernel.setVelocity((rand.nextFloat() * 0.25) - 0.125, rand.nextFloat() * 0.5 + 0.1, (rand.nextFloat() * 0.25) - 0.125);
				this.world.spawnEntity(kernel);
			}
		}
		DamageSource source = new PoppedDamageSource(this, this.getOwner());
		source.setProjectile().setFire().setExplosive();
		Box box = this.entityBounds.expand(4);
		for (Entity e : world.getOtherEntities(this, box, EntityPredicates.VALID_LIVING_ENTITY)) {
			e.damage(source, 8);
			if (!e.isFireImmune()) {
				e.setOnFireFor(6);
			}
		}
	}
}
