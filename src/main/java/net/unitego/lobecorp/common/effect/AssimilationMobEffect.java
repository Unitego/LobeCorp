package net.unitego.lobecorp.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.manager.SanityManager;
import org.jetbrains.annotations.NotNull;

public class AssimilationMobEffect extends MobEffect {
    public AssimilationMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            SanityManager sanityManager = ((ManagerAccess) player).lobeCorp$getSanityManager();
            return sanityManager.getAssimilationAmount() > 0.0f || player.level().isClientSide;
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
            SanityManager sanityManager = ((ManagerAccess) player).lobeCorp$getSanityManager();
            sanityManager.setAssimilationAmount(Math.max(sanityManager.getAssimilationAmount(), (float) (4 * (1 + amplifier))));
        }
    }
}
