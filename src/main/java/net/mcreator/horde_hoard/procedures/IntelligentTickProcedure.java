package net.mcreator.horde_hoard.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos;

import net.mcreator.horde_hoard.init.HordeHoardModMobEffects;

import javax.annotation.Nullable;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

@Mod.EventBusSubscriber
public class IntelligentTickProcedure {
	@SubscribeEvent
	public static void onEntityTick(LivingEvent.LivingTickEvent event) {
		execute(event, event.getEntity().level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean doo = false;
		if (entity.getType().is(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge:intelligent_teams"))) || entity.getType().is(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge:intelligent_piglin")))) {
			if (!(entity instanceof LivingEntity _livEnt ? _livEnt.hasEffect(HordeHoardModMobEffects.THINKING.get()) : false)) {
				if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
					if (!(((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).isEnchanted())) {
						if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.BOW && !((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof IronGolem)) {
							{
								final Vec3 _center = new Vec3(x, y, z);
								List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4.5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
										.collect(Collectors.toList());
								for (Entity entityiterator : _entfound) {
									if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator
											|| entityiterator.getType().is(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge:intelligent_teams")))
													&& !(entity.getX() + entity.getZ() == entityiterator.getX() + entityiterator.getZ())) {
										doo = true;
										break;
									} else {
										doo = false;
									}
								}
							}
							if (doo) {
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(Blocks.AIR);
									_setstack.setCount(1);
									_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, new BlockPos(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_generic")), SoundSource.HOSTILE, (float) 0.5, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_generic")), SoundSource.HOSTILE, (float) 0.5, 1, false);
									}
								}
								if (entity instanceof LivingEntity _entity)
									_entity.swing(InteractionHand.MAIN_HAND, true);
								if (entity instanceof LivingEntity _entity)
									_entity.addEffect(new MobEffectInstance(HordeHoardModMobEffects.THINKING.get(), 40, 1, (false), (false)));
							}
						} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
							{
								final Vec3 _center = new Vec3(x, y, z);
								List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4.5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
										.collect(Collectors.toList());
								for (Entity entityiterator : _entfound) {
									if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator
											|| entityiterator.getType().is(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge:intelligent_teams")))
													&& !(entity.getX() + entity.getZ() == entityiterator.getX() + entityiterator.getZ())) {
										doo = false;
										break;
									} else {
										doo = true;
									}
								}
							}
							if (doo) {
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(Items.BOW);
									_setstack.setCount(1);
									_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, new BlockPos(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.crossbow.loading_end")), SoundSource.HOSTILE, (float) 0.5, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.crossbow.loading_end")), SoundSource.HOSTILE, (float) 0.5, 1, false);
									}
								}
								if (entity instanceof LivingEntity _entity)
									_entity.swing(InteractionHand.MAIN_HAND, true);
								if (entity instanceof LivingEntity _entity)
									_entity.addEffect(new MobEffectInstance(HordeHoardModMobEffects.THINKING.get(), 40, 1, (false), (false)));
							}
						} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.CROSSBOW) {
							{
								final Vec3 _center = new Vec3(x, y, z);
								List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4.5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
										.collect(Collectors.toList());
								for (Entity entityiterator : _entfound) {
									if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator
											|| entityiterator.getType().is(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge:intelligent_piglin")))
													&& !(entity.getX() + entity.getZ() == entityiterator.getX() + entityiterator.getZ())) {
										doo = true;
										break;
									} else {
										doo = false;
									}
								}
							}
							if (doo) {
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(Items.GOLDEN_SWORD);
									_setstack.setCount(1);
									_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, new BlockPos(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_generic")), SoundSource.HOSTILE, (float) 0.5, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_generic")), SoundSource.HOSTILE, (float) 0.5, 1, false);
									}
								}
								if (entity instanceof LivingEntity _entity)
									_entity.swing(InteractionHand.MAIN_HAND, true);
								if (entity instanceof LivingEntity _entity)
									_entity.addEffect(new MobEffectInstance(HordeHoardModMobEffects.THINKING.get(), 40, 1, (false), (false)));
							}
						} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.GOLDEN_SWORD) {
							{
								final Vec3 _center = new Vec3(x, y, z);
								List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4.5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
										.collect(Collectors.toList());
								for (Entity entityiterator : _entfound) {
									if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator
											|| entityiterator.getType().is(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge:intelligent_piglin")))
													&& !(entity.getX() + entity.getZ() == entityiterator.getX() + entityiterator.getZ())) {
										doo = false;
										break;
									} else {
										doo = true;
									}
								}
							}
							if (doo) {
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(Items.CROSSBOW);
									_setstack.setCount(1);
									_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, new BlockPos(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.crossbow.loading_end")), SoundSource.HOSTILE, (float) 0.5, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.crossbow.loading_end")), SoundSource.HOSTILE, (float) 0.5, 1, false);
									}
								}
								if (entity instanceof LivingEntity _entity)
									_entity.swing(InteractionHand.MAIN_HAND, true);
								if (entity instanceof LivingEntity _entity)
									_entity.addEffect(new MobEffectInstance(HordeHoardModMobEffects.THINKING.get(), 40, 1, (false), (false)));
								if (entity instanceof ZombifiedPiglin) {
									{
										Entity _shootFrom = entity;
										Level projectileLevel = _shootFrom.level;
										if (!projectileLevel.isClientSide()) {
											Projectile _entityToSpawn = new Object() {
												public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
													AbstractArrow entityToSpawn = new Arrow(EntityType.ARROW, level);
													entityToSpawn.setOwner(shooter);
													entityToSpawn.setBaseDamage(damage);
													entityToSpawn.setKnockback(knockback);
													entityToSpawn.setSecondsOnFire(100);
													return entityToSpawn;
												}
											}.getArrow(projectileLevel, entity, 5, 1);
											_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
											_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, (float) 0.2);
											projectileLevel.addFreshEntity(_entityToSpawn);
										}
									}
									if (world instanceof Level _level) {
										if (!_level.isClientSide()) {
											_level.playSound(null, new BlockPos(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.crossbow.shoot")), SoundSource.HOSTILE, 1, 1);
										} else {
											_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.crossbow.shoot")), SoundSource.HOSTILE, 1, 1, false);
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
