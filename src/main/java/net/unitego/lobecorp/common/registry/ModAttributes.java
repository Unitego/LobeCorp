package net.unitego.lobecorp.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;

public class ModAttributes {
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, LobeCorp.MOD_ID);

    public static final DeferredHolder<Attribute, Attribute> MAX_SANITY = register("max_sanity", 20.0, 1.0, 1024.0);
    public static final DeferredHolder<Attribute, Attribute> MAX_ASSIMILATION = register("max_assimilation", 0.0, 0.0, 2048.0);

    private static DeferredHolder<Attribute, Attribute> register(String name, double defaultValue, double min, double max) {
        return ATTRIBUTES.register(name, location -> new RangedAttribute(
                "attribute.name." + location.getNamespace() + "." + location.getPath(),
                defaultValue, min, max).setSyncable(true));
    }

    public static void init(IEventBus bus) {
        ATTRIBUTES.register(bus);
    }
}
