package net.unitego.lobecorp.common.entity.abnormality;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.unitego.lobecorp.common.access.ColorDamageAccess;
import net.unitego.lobecorp.common.access.ColorResistAccess;
import net.unitego.lobecorp.common.access.EGORankAccess;
import net.unitego.lobecorp.common.entity.LobeCorpLiving;
import net.unitego.lobecorp.common.util.EGORank;

import java.util.List;

public abstract class Abnormality extends LobeCorpLiving implements EGORankAccess, ColorDamageAccess, ColorResistAccess {
    protected Abnormality(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public List<ResourceKey<DamageType>> getDamageTypes() {
        return List.of();
    }

    @Override
    public float getRedResist() {
        return 0;
    }

    @Override
    public float getWhiteResist() {
        return 0;
    }

    @Override
    public float getBlackResist() {
        return 0;
    }

    @Override
    public float getPaleResist() {
        return 0;
    }

    @Override
    public EGORank getEGORank() {
        return null;
    }
}
