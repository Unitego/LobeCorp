package net.unitego.lobecorp.common.effect;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.access.DataAccess;

public class HydrationMobEffect extends InstantenousMobEffect {
    public HydrationMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide && livingEntity instanceof Player player) {
            ((DataAccess) player).lobeCorp$getWaterData().drink(amplifier + 1, 1.0f);
        }
        return true;
    }
}
