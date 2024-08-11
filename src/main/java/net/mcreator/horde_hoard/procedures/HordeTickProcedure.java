package net.mcreator.horde_hoard.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.mcreator.horde_hoard.init.HordeHoardModMobEffects;
import net.mcreator.horde_hoard.init.HordeHoardModGameRules;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Comparator;

@Mod.EventBusSubscriber
public class HordeTickProcedure {
	@SubscribeEvent
	public static void onEntityTick(LivingEvent.LivingTickEvent event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean skipthistick = false;
		double rand = 0;
		double yit = 0;
		double myx = 0;
		myx = x;
		if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:hordes")))) {
			skipthistick = false;
			if (!(entity instanceof LivingEntity _livEnt1 && _livEnt1.isBaby()) && entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:horde_grave_robbers")))) {
				if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).is(BlockTags.create(new ResourceLocation("forge:hidden_zombie_blocks"))) && !((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)
						&& world.getLevelData().getGameRules().getBoolean(HordeHoardModGameRules.HORDEMULTIPLYING)) {
					if (entity instanceof LivingEntity _livEnt8 && _livEnt8.hasEffect(HordeHoardModMobEffects.THINKING.get())) {
						if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(HordeHoardModMobEffects.THINKING.get()) ? _livEnt.getEffect(HordeHoardModMobEffects.THINKING.get()).getDuration() : 0) % 20 == 0
								&& !((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(HordeHoardModMobEffects.THINKING.get()) ? _livEnt.getEffect(HordeHoardModMobEffects.THINKING.get()).getDuration() : 0) == 0)) {
							if (entity instanceof LivingEntity _entity)
								_entity.swing(InteractionHand.MAIN_HAND, true);
							world.levelEvent(2001, BlockPos.containing(x, y - 1, z), Block.getId((world.getBlockState(BlockPos.containing(x, y - 1, z)))));
						}
						if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(HordeHoardModMobEffects.THINKING.get()) ? _livEnt.getEffect(HordeHoardModMobEffects.THINKING.get()).getDuration() : 0) == 1) {
							if (world instanceof ServerLevel _level) {
								Entity entityToSpawn = EntityType.ZOMBIE.spawn(_level, BlockPos.containing(x, y - 1, z), MobSpawnType.MOB_SUMMONED);
								if (entityToSpawn != null) {
									entityToSpawn.setDeltaMovement(0, 0, 0);
								}
							}
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie.destroy_egg")), SoundSource.HOSTILE, 1, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie.destroy_egg")), SoundSource.HOSTILE, 1, 1, false);
								}
							}
						}
						skipthistick = true;
						entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
					} else if (Math.random() < 0.0008) {
						if (entity instanceof LivingEntity _entity)
							_entity.removeEffect(MobEffects.MOVEMENT_SPEED);
						if (entity instanceof LivingEntity _entity)
							_entity.removeEffect(HordeHoardModMobEffects.THINKING.get());
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(HordeHoardModMobEffects.THINKING.get(), 200, 10, false, false));
						skipthistick = true;
					}
				} else if (entity instanceof LivingEntity _livEnt21 && _livEnt21.hasEffect(HordeHoardModMobEffects.THINKING.get())) {
					if (entity instanceof LivingEntity _entity)
						_entity.removeEffect(HordeHoardModMobEffects.THINKING.get());
				}
			}
			if (!skipthistick && world.getLevelData().getGameRules().getBoolean(HordeHoardModGameRules.HORDESTACKING)) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(0.45 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!(myx == entityiterator.getX())) {
							if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:hordes")))) {
								rand = Math.random();
								if (rand > 0.16) {
									rand = 0.16;
								}
								if (entity instanceof LivingEntity _livEnt26 && _livEnt26.isBaby()) {
									entity.setDeltaMovement(
											new Vec3((entity.getDeltaMovement().x() + (rand / 20) * Mth.nextInt(RandomSource.create(), -1, 1)), (rand * 2), (entity.getDeltaMovement().z() + (rand / 20) * Mth.nextInt(RandomSource.create(), -1, 1))));
									entity.fallDistance = 0;
								} else {
									entity.setDeltaMovement(
											new Vec3((entity.getDeltaMovement().x() + (rand / 20) * Mth.nextInt(RandomSource.create(), -1, 1)), rand, (entity.getDeltaMovement().z() + (rand / 20) * Mth.nextInt(RandomSource.create(), -1, 1))));
									entity.fallDistance = 0;
									if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
										_entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 5, 0, false, false));
								}
								if (entity instanceof LivingEntity _entity)
									_entity.swing(InteractionHand.MAIN_HAND, true);
								if (entity.isOnFire()) {
									entityiterator.setSecondsOnFire(5);
									if (!(entity instanceof LivingEntity _livEnt43 && _livEnt43.hasEffect(MobEffects.MOVEMENT_SPEED))) {
										if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
											_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, entity.getRemainingFireTicks(), 2, false, false));
									}
									if (!(entityiterator instanceof LivingEntity _livEnt46 && _livEnt46.hasEffect(MobEffects.MOVEMENT_SPEED))) {
										if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
											_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2, false, false));
									}
								}
								if (!Blocks.AIR.defaultBlockState().is(BlockTags.create(new ResourceLocation("forge:horde_breakable")))) {
									if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
										yit = -1;
										for (int index0 = 0; index0 < 4; index0++) {
											if ((world.getBlockState(BlockPos.containing(x, y + yit, z))).is(BlockTags.create(new ResourceLocation("forge:horde_breakable")))) {
												{
													BlockPos _pos = BlockPos.containing(x, y + yit, z);
													Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y + yit, z), null);
													world.destroyBlock(_pos, false);
												}
											}
											yit = yit + 1;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
