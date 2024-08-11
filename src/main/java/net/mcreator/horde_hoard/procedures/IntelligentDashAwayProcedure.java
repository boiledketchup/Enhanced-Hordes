package net.mcreator.horde_hoard.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos;

import net.mcreator.horde_hoard.init.HordeHoardModMobEffects;

import javax.annotation.Nullable;

import java.util.Random;

@Mod.EventBusSubscriber
public class IntelligentDashAwayProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity.getType().is(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge:intelligent_teams")))
				&& entity.getType().is(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge:intelligent_teams")))) {
			if (event != null && event.isCancelable()) {
				event.setCanceled(true);
			}
		}
		if (sourceentity.getType().is(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge:intelligent_teams")))) {
			if (!(sourceentity instanceof WitherSkeleton)) {
				if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
					if (sourceentity instanceof LivingEntity _entity) {
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
					if (sourceentity instanceof LivingEntity _entity)
						_entity.swing(InteractionHand.MAIN_HAND, true);
					sourceentity.setDeltaMovement(new Vec3(((sourceentity.getDeltaMovement().x() + Mth.nextDouble(new Random(), -0.5, 0.5)) * (-1) * 2), (sourceentity.getDeltaMovement().y() + 0.4),
							((sourceentity.getDeltaMovement().z() + Mth.nextDouble(new Random(), -0.5, 0.5)) * (-1) * 2)));
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.CLOUD, (sourceentity.getX() + 0.5), (sourceentity.getY()), (sourceentity.getZ() + 0.5), 10, 0.5, 1, 0.5, 0.1);
					if (sourceentity instanceof LivingEntity _entity)
						_entity.addEffect(new MobEffectInstance(HordeHoardModMobEffects.THINKING.get(), 50, 1, (false), (false)));
				}
			}
		}
	}
}
