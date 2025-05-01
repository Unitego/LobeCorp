package net.unitego.lobecorp.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.access.DataAccess;
import net.unitego.lobecorp.data.SanityData;
import org.jetbrains.annotations.NotNull;

public class RestorationMobEffect extends MobEffect {
    public RestorationMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
            if (sanityData.getSanity() < sanityData.getMaxSanity()) {
                sanityData.cure(1.0f);
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 50 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}
