package net.unitego.lobecorp.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import org.jetbrains.annotations.NotNull;

public class AssimilationMobEffect extends MobEffect {
    public AssimilationMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
            return sanityData.getAssimilationAmount() > 0.0f || player.level().isClientSide;
        }
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onEffectStarted(@NotNull LivingEntity livingEntity, int amplifier) {
        super.onEffectStarted(livingEntity, amplifier);
        if (livingEntity instanceof Player player) {
            SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
            sanityData.setAssimilationAmount(Math.max(sanityData.getAssimilationAmount(), (float) (4 * (1 + amplifier))));
        }
    }
}
