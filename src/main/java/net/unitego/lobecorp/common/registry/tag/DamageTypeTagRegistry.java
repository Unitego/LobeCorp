package net.unitego.lobecorp.common.registry.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/01 12:23:10}
 */
public enum DamageTypeTagRegistry implements Supplier<TagKey<DamageType>> {
    RED,
    WHITE,
    BLACK,
    PALE,
    SPIRIT,
    CENSORED
    ;
    private final TagKey<DamageType> tag;
    DamageTypeTagRegistry() {
        tag = create(name().toLowerCase(Locale.ROOT));
    }

    private static TagKey<DamageType> create(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(name));
    }

    @Override
    public TagKey<DamageType> get() {
        return tag;
    }
}
