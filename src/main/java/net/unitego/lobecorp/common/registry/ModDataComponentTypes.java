package net.unitego.lobecorp.common.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.component.LobeCorpAttributeModifiers;

public class ModDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, LobeCorp.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<LobeCorpAttributeModifiers>> LOBECORP_ATTRIBUTE_MODIFIERS =
            DATA_COMPONENT_TYPES.register("lobecorp_attribute_modifiers", () -> DataComponentType.<LobeCorpAttributeModifiers>builder()
                    .persistent(LobeCorpAttributeModifiers.CODEC)
                    .networkSynchronized(LobeCorpAttributeModifiers.STREAM_CODEC).build());
}
