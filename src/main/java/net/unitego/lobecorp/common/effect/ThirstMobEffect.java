package net.unitego.lobecorp.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.WaterData;
import org.jetbrains.annotations.NotNull;

public class ThirstMobEffect extends MobEffect {
    public ThirstMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            if (!player.getAbilities().invulnerable) {
                WaterData waterData = ((DataAccess) player).lobeCorp$getWaterData();
                waterData.addDesiccation(0.005F * (float) (amplifier + 1));
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
