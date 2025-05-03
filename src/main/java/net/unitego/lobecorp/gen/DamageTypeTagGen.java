package net.unitego.lobecorp.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.registry.ModDamageTypes;
import net.unitego.lobecorp.common.registry.tag.DamageTypeTagRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DamageTypeTagGen extends TagsProvider<DamageType> {
    public DamageTypeTagGen(PackOutput output,
                            CompletableFuture<HolderLookup.Provider> lookupProvider,
                            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider, LobeCorp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(DamageTypes.FELL_OUT_OF_WORLD, DamageTypeTagRegistry.CENSORED.get());
        tag(DamageTypes.OUTSIDE_BORDER, DamageTypeTagRegistry.CENSORED.get());
        tag(DamageTypes.GENERIC_KILL, DamageTypeTagRegistry.CENSORED.get());
        tag(DamageTypes.STARVE, DamageTypeTagRegistry.CENSORED.get());
        tag(ModDamageTypes.BLACK, DamageTypeTagRegistry.BLACK.get(), DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.BYPASSES_WOLF_ARMOR, DamageTypeTags.CAN_BREAK_ARMOR_STAND);
        tag(ModDamageTypes.DRY, DamageTypeTagRegistry.CENSORED.get(), DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.BYPASSES_EFFECTS, DamageTypeTags.BYPASSES_SHIELD, DamageTypeTags.BYPASSES_WOLF_ARMOR, DamageTypeTags.NO_KNOCKBACK);
        tag(ModDamageTypes.INDIRECT_MYSTIC, DamageTypeTagRegistry.SPIRIT.get(), DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.BYPASSES_SHIELD, DamageTypeTags.BYPASSES_WOLF_ARMOR);
        tag(ModDamageTypes.INSANE, DamageTypeTagRegistry.SPIRIT.get(), DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.BYPASSES_SHIELD, DamageTypeTags.BYPASSES_WOLF_ARMOR, DamageTypeTags.NO_KNOCKBACK);
        tag(ModDamageTypes.MYSTIC, DamageTypeTagRegistry.SPIRIT.get(), DamageTypeTags.AVOIDS_GUARDIAN_THORNS, DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.BYPASSES_SHIELD, DamageTypeTags.BYPASSES_WOLF_ARMOR, DamageTypeTags.NO_KNOCKBACK);
        tag(ModDamageTypes.PALE, DamageTypeTagRegistry.PALE.get(), DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.BYPASSES_WOLF_ARMOR, DamageTypeTags.CAN_BREAK_ARMOR_STAND);
        tag(ModDamageTypes.RED, DamageTypeTagRegistry.RED.get(), DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.BYPASSES_WOLF_ARMOR, DamageTypeTags.CAN_BREAK_ARMOR_STAND);
        tag(ModDamageTypes.WHITE, DamageTypeTagRegistry.WHITE.get(), DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.BYPASSES_WOLF_ARMOR, DamageTypeTags.CAN_BREAK_ARMOR_STAND);
    }

    @SafeVarargs
    private void tag(ResourceKey<DamageType> type, TagKey<DamageType>... tags) {
        for (TagKey<DamageType> tag : tags) {
            tag(tag).add(type);
        }
    }
}
