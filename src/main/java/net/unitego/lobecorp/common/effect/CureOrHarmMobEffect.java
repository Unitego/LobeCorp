package net.unitego.lobecorp.common.effect;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.registry.ModDamageTypes;
import org.jetbrains.annotations.Nullable;

public class CureOrHarmMobEffect extends InstantenousMobEffect {
    private final boolean isHarm;

    public CureOrHarmMobEffect(MobEffectCategory category, int color, boolean isHarm) {
        super(category, color);
        this.isHarm = isHarm;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (isHarm != livingEntity.isInvertedHealAndHarm()) {
            livingEntity.hurt(livingEntity.damageSources().source(ModDamageTypes.MYSTIC), (float) (6 << amplifier));
        } else {
            if (livingEntity instanceof Player player) {
                SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
                sanityData.cure((float) Math.max(4 << amplifier, 0));
            }
        }
        return true;
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity livingEntity, int amplifier, double health) {
        if (isHarm != livingEntity.isInvertedHealAndHarm()) {
            int j = (int) (health * (double) (6 << amplifier) + 0.5);
            if (source == null) {
                livingEntity.hurt(livingEntity.damageSources().source(ModDamageTypes.MYSTIC), (float) j);
            } else {
                livingEntity.hurt(livingEntity.damageSources().source(ModDamageTypes.INDIRECT_MYSTIC, source, indirectSource), (float) j);
            }
        } else {
            if (livingEntity instanceof Player player) {
                SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
                int i = (int) (health * (double) (4 << amplifier) + 0.5);
                sanityData.cure((float) i);
            }
        }
    }
}
