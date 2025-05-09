package net.unitego.lobecorp.data.generator;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.tag.DamageTypeTags;
import net.unitego.lobecorp.registry.DamageTypesRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DamageTypeTagsGenerator extends TagsProvider<DamageType> {
    public DamageTypeTagsGenerator(PackOutput output,
                                   CompletableFuture<HolderLookup.Provider> lookupProvider,
                                   @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider, LobeCorp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(DamageTypes.FELL_OUT_OF_WORLD, DamageTypeTags.CENSORED_DAMAGE_TYPE.get());
        tag(DamageTypes.OUTSIDE_BORDER, DamageTypeTags.CENSORED_DAMAGE_TYPE.get());
        tag(DamageTypes.GENERIC_KILL, DamageTypeTags.CENSORED_DAMAGE_TYPE.get());
        tag(DamageTypes.STARVE, DamageTypeTags.DEPLETED_DAMAGE_TYPE.get());
        tag(DamageTypes.FREEZE, DamageTypeTags.DEPLETED_DAMAGE_TYPE.get());
        tag(DamageTypesRegistry.BLACK, DamageTypeTags.BLACK_DAMAGE_TYPE.get(), net.minecraft.tags.DamageTypeTags.BYPASSES_ARMOR, net.minecraft.tags.DamageTypeTags.BYPASSES_WOLF_ARMOR, net.minecraft.tags.DamageTypeTags.CAN_BREAK_ARMOR_STAND);
        tag(DamageTypesRegistry.DRY, DamageTypeTags.DEPLETED_DAMAGE_TYPE.get(), net.minecraft.tags.DamageTypeTags.BYPASSES_ARMOR, net.minecraft.tags.DamageTypeTags.BYPASSES_EFFECTS, net.minecraft.tags.DamageTypeTags.BYPASSES_SHIELD, net.minecraft.tags.DamageTypeTags.BYPASSES_WOLF_ARMOR, net.minecraft.tags.DamageTypeTags.NO_KNOCKBACK);
        tag(DamageTypesRegistry.INDIRECT_MYSTIC, DamageTypeTags.SPIRIT_DAMAGE_TYPE.get(), net.minecraft.tags.DamageTypeTags.BYPASSES_ARMOR, net.minecraft.tags.DamageTypeTags.BYPASSES_SHIELD, net.minecraft.tags.DamageTypeTags.BYPASSES_WOLF_ARMOR);
        tag(DamageTypesRegistry.INSANE, DamageTypeTags.SPIRIT_DAMAGE_TYPE.get(), net.minecraft.tags.DamageTypeTags.BYPASSES_ARMOR, net.minecraft.tags.DamageTypeTags.BYPASSES_SHIELD, net.minecraft.tags.DamageTypeTags.BYPASSES_WOLF_ARMOR, net.minecraft.tags.DamageTypeTags.NO_KNOCKBACK);
        tag(DamageTypesRegistry.MYSTIC, DamageTypeTags.SPIRIT_DAMAGE_TYPE.get(), net.minecraft.tags.DamageTypeTags.AVOIDS_GUARDIAN_THORNS, net.minecraft.tags.DamageTypeTags.BYPASSES_ARMOR, net.minecraft.tags.DamageTypeTags.BYPASSES_SHIELD, net.minecraft.tags.DamageTypeTags.BYPASSES_WOLF_ARMOR, net.minecraft.tags.DamageTypeTags.NO_KNOCKBACK);
        tag(DamageTypesRegistry.PALE, DamageTypeTags.PALE_DAMAGE_TYPE.get(), net.minecraft.tags.DamageTypeTags.BYPASSES_ARMOR, net.minecraft.tags.DamageTypeTags.BYPASSES_WOLF_ARMOR, net.minecraft.tags.DamageTypeTags.CAN_BREAK_ARMOR_STAND);
        tag(DamageTypesRegistry.RED, DamageTypeTags.RED_DAMAGE_TYPE.get(), net.minecraft.tags.DamageTypeTags.BYPASSES_ARMOR, net.minecraft.tags.DamageTypeTags.BYPASSES_WOLF_ARMOR, net.minecraft.tags.DamageTypeTags.CAN_BREAK_ARMOR_STAND);
        tag(DamageTypesRegistry.WHITE, DamageTypeTags.WHITE_DAMAGE_TYPE.get(), net.minecraft.tags.DamageTypeTags.BYPASSES_ARMOR, net.minecraft.tags.DamageTypeTags.BYPASSES_WOLF_ARMOR, net.minecraft.tags.DamageTypeTags.CAN_BREAK_ARMOR_STAND);
    }

    @SafeVarargs
    private void tag(ResourceKey<DamageType> type, TagKey<DamageType>... tags) {
        for (TagKey<DamageType> tag : tags) {
            tag(tag).add(type);
        }
    }
}
