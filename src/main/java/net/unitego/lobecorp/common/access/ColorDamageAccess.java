package net.unitego.lobecorp.common.access;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.List;

public interface ColorDamageAccess {
    List<ResourceKey<DamageType>> getDamageTypes();
}
