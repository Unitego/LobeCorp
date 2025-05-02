package net.unitego.lobecorp.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.registry.ModDamageTypes;
import org.jetbrains.annotations.NotNull;

public class AbsentMobEffect extends MobEffect {
    public AbsentMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
            if (sanityData.getSanity() > 1.0f) {
                player.hurt(player.damageSources().source(ModDamageTypes.MYSTIC), 1.0f);
            }
        } else {
            if (livingEntity.getHealth() > 1.0f) {
                livingEntity.hurt(livingEntity.damageSources().source(ModDamageTypes.MYSTIC), 1.0f);
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 25 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}
