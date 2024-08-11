
package net.mcreator.horde_hoard.potion;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class ThinkingMobEffect extends MobEffect {
	public ThinkingMobEffect() {
		super(MobEffectCategory.NEUTRAL, -13434727);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
