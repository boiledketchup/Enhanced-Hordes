package net.mcreator.horde_hoard.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.horde_hoard.init.HordeHoardModMobEffects;
import net.mcreator.horde_hoard.init.HordeHoardModGameRules;

import javax.annotation.Nullable;

import java.util.stream.Collectors;
import java.util.Random;
import java.util.List;
import java.util.Comparator;

@Mod.EventBusSubscriber
public class HordeTickProcedure {
	@SubscribeEvent
	public static void onEntityTick(LivingEvent.LivingUpdateEvent event) {
		execute(event, event.getEntityLiving().level, event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ(), event.getEntityLiving());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double rand = 0;
		double yit = 0;
		boolean skipthistick = false;
		if (entity.getType().is(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge:hordes")))) {
			if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
							.collect(Collectors.toList());
					for (Entity entityiterator : _entfound) {
						if (!entityiterator.getType().is(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge:hordes"))) && !(entityiterator instanceof Player)) {
							if (entity instanceof Mob _entity && entityiterator instanceof LivingEntity _ent)
								_entity.setTarget(_ent);
							break;
						}
					}
				}
			}
			skipthistick = false;
			if (entity.getType().is(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge:horde_grave_robbers"))) && !(entity instanceof LivingEntity _livEnt ? _livEnt.isBaby() : false)) {
				if ((world.getBlockState(new BlockPos(x, y - 1, z))).is(BlockTags.create(new ResourceLocation("forge:hidden_zombie_blocks"))) && !((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)
						&& world.getLevelData().getGameRules().getBoolean(HordeHoardModGameRules.HORDEMULTIPLYING)) {
					if (entity instanceof LivingEntity _livEnt ? _livEnt.hasEffect(HordeHoardModMobEffects.THINKING.get()) : false) {
						if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(HordeHoardModMobEffects.THINKING.get()) ? _livEnt.getEffect(HordeHoardModMobEffects.THINKING.get()).getDuration() : 0) % 20 == 0
								&& !((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(HordeHoardModMobEffects.THINKING.get()) ? _livEnt.getEffect(HordeHoardModMobEffects.THINKING.get()).getDuration() : 0) == 0)) {
							if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
								if (entity instanceof LivingEntity _entity)
									_entity.swing(InteractionHand.MAIN_HAND, true);
								world.levelEvent(2001, new BlockPos(x, y - 1, z), Block.getId((world.getBlockState(new BlockPos(x, y - 1, z)))));
							}
						}
						if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(HordeHoardModMobEffects.THINKING.get()) ? _livEnt.getEffect(HordeHoardModMobEffects.THINKING.get()).getDuration() : 0) == 1) {
							if (world instanceof ServerLevel _level)
								_level.getServer().getCommands().performCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, (y - 1), z), Vec2.ZERO, _level, 4, "", new TextComponent(""), _level.getServer(), null).withSuppressedOutput(),
										("summon " + (entity.getEncodeId() + " ~ ~ ~")));
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, new BlockPos(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie.destroy_egg")), SoundSource.HOSTILE, 1, 1);
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
						if (entity instanceof LivingEntity _entity)
							_entity.addEffect(new MobEffectInstance(HordeHoardModMobEffects.THINKING.get(), 200, 10, (false), (false)));
						skipthistick = true;
					}
				} else if (entity instanceof LivingEntity _livEnt ? _livEnt.hasEffect(HordeHoardModMobEffects.THINKING.get()) : false) {
					if (entity instanceof LivingEntity _entity)
						_entity.removeEffect(HordeHoardModMobEffects.THINKING.get());
				}
			}
			if (!skipthistick && world.getLevelData().getGameRules().getBoolean(HordeHoardModGameRules.HORDESTACKING)) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(0.4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
							.collect(Collectors.toList());
					for (Entity entityiterator : _entfound) {
						if (!(entity.getUUID().toString()).equals(entityiterator.getUUID().toString())) {
							if (entityiterator.getType().is(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge:hordes")))) {
								rand = Math.random();
								if (rand > 0.16) {
									rand = 0.16;
								}
								if (entity instanceof LivingEntity _livEnt ? _livEnt.isBaby() : false) {
									entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x() + (rand / 20) * Mth.nextInt(new Random(), -1, 1)), (rand * 2), (entity.getDeltaMovement().z() + (rand / 20) * Mth.nextInt(new Random(), -1, 1))));
									entity.fallDistance = 0;
								} else {
									entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x() + (rand / 20) * Mth.nextInt(new Random(), -1, 1)), rand, (entity.getDeltaMovement().z() + (rand / 20) * Mth.nextInt(new Random(), -1, 1))));
									entity.fallDistance = 0;
									if (entity instanceof LivingEntity _entity)
										_entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 5, 0, (false), (false)));
								}
								if ((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null) {
									if (entityiterator instanceof Mob _entity && (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof LivingEntity _ent)
										_entity.setTarget(_ent);
								}
								if (entity instanceof LivingEntity _entity)
									_entity.swing(InteractionHand.MAIN_HAND, true);
								if (entity.isOnFire()) {
									entityiterator.setSecondsOnFire(5);
									if (!(entity instanceof LivingEntity _livEnt ? _livEnt.hasEffect(MobEffects.MOVEMENT_SPEED) : false)) {
										if (entity instanceof LivingEntity _entity)
											_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, entity.getRemainingFireTicks(), 2, (false), (false)));
									}
									if (!(entityiterator instanceof LivingEntity _livEnt ? _livEnt.hasEffect(MobEffects.MOVEMENT_SPEED) : false)) {
										if (entityiterator instanceof LivingEntity _entity)
											_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2, (false), (false)));
									}
								}
								if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && !Blocks.AIR.defaultBlockState().is(BlockTags.create(new ResourceLocation("forge:horde_breakable")))) {
									yit = -1;
									for (int index0 = 0; index0 < (int) (4); index0++) {
										if ((world.getBlockState(new BlockPos(x, y + yit, z))).is(BlockTags.create(new ResourceLocation("forge:horde_breakable")))) {
											{
												BlockPos _pos = new BlockPos(x, y + yit, z);
												Block.dropResources(world.getBlockState(_pos), world, new BlockPos(x, y + yit, z), null);
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
