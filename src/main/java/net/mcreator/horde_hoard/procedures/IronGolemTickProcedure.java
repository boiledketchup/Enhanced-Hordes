package net.mcreator.horde_hoard.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.horde_hoard.init.HordeHoardModMobEffects;
import net.mcreator.horde_hoard.init.HordeHoardModGameRules;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class IronGolemTickProcedure {
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
		if (entity instanceof IronGolem) {
			if (!entity.isInLava() && entity.isOnFire()) {
				entity.clearFire();
			}
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.WITHER);
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.POISON);
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.LEVITATION);
			if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null) {
				if (!(entity instanceof LivingEntity _livEnt9 && _livEnt9.hasEffect(MobEffects.REGENERATION)) && !(entity instanceof LivingEntity _livEnt10 && _livEnt10.hasEffect(HordeHoardModMobEffects.THINKING.get()))
						&& !((world.getLevelData().getGameRules().getInt(HordeHoardModGameRules.IRONGOLEMREGENPOWER)) < 1)) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20, (int) ((world.getLevelData().getGameRules().getInt(HordeHoardModGameRules.IRONGOLEMREGENPOWER)) - 1), false, false));
					if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) == (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1))) {
						if (world instanceof ServerLevel _level)
							_level.sendParticles(ParticleTypes.HAPPY_VILLAGER, (x + 0.8), (y + 1), (z + 0.8), 1, 1, 1, 1, 0.2);
					}
				}
			} else if (!(entity instanceof LivingEntity _livEnt17 && _livEnt17.hasEffect(MobEffects.MOVEMENT_SPEED))) {
				if (((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof Villager) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 1, false, false));
				}
			}
		}
	}
}
