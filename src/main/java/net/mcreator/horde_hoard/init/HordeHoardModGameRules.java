
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.horde_hoard.init;

import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.GameRules;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HordeHoardModGameRules {
	public static final GameRules.Key<GameRules.BooleanValue> HORDESTACKING = GameRules.register("hordeStacking", GameRules.Category.MOBS, GameRules.BooleanValue.create(true));
	public static final GameRules.Key<GameRules.BooleanValue> HORDEMULTIPLYING = GameRules.register("hordeMultiplying", GameRules.Category.MOBS, GameRules.BooleanValue.create(true));
	public static final GameRules.Key<GameRules.IntegerValue> IRONGOLEMREGENPOWER = GameRules.register("ironGolemRegenPower", GameRules.Category.MOBS, GameRules.IntegerValue.create(4));
}
