
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.horde_hoard.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.effect.MobEffect;

import net.mcreator.horde_hoard.potion.ThinkingMobEffect;
import net.mcreator.horde_hoard.HordeHoardMod;

public class HordeHoardModMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, HordeHoardMod.MODID);
	public static final RegistryObject<MobEffect> THINKING = REGISTRY.register("thinking", () -> new ThinkingMobEffect());
}
