package net.unitego.lobecorp.common.effect;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class AbstractVulnerableEffect extends MobEffect {
    private final ResourceKey<DamageType> damageType;

    public AbstractVulnerableEffect(MobEffectCategory category, int color, ResourceKey<DamageType> damageType) {
        super(category, color);
        this.damageType = damageType;
    }

    public ResourceKey<DamageType> getDamageType() {
        return damageType;
    }
}
