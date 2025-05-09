package net.unitego.lobecorp.registry;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.component.LobeCorpAttributeModifiers;

public class DataComponentTypesRegistry {
    public static final DeferredRegister.DataComponents REGISTER = DeferredRegister.createDataComponents(LobeCorp.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<LobeCorpAttributeModifiers>> LOBECORP_ATTRIBUTE_MODIFIERS =
            REGISTER.register("lobecorp_attribute_modifiers", () -> DataComponentType.<LobeCorpAttributeModifiers>builder()
                    .persistent(LobeCorpAttributeModifiers.CODEC)
                    .networkSynchronized(LobeCorpAttributeModifiers.STREAM_CODEC).build());

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
