package net.unitego.lobecorp.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;

public class AttributesRegistry {
    public static final DeferredRegister<Attribute> REGISTER = DeferredRegister
            .create(Registries.ATTRIBUTE, LobeCorp.MOD_ID);

    public static final DeferredHolder<Attribute, Attribute> MAX_SANITY = register("player.max_sanity", 20.0, 1.0, 1024.0);
    public static final DeferredHolder<Attribute, Attribute> MAX_ASSIMILATION = register("player.max_assimilation", 0.0, 0.0, 2048.0);
    public static final DeferredHolder<Attribute, Attribute> WORK_SUCCESS = register("player.work_success", 20.0, 1.0, 1024.0);
    public static final DeferredHolder<Attribute, Attribute> WORK_VELOCITY = register("player.work_velocity", 20.0, 1.0, 1024.0);
    public static final DeferredHolder<Attribute, Attribute> ATTACK_VELOCITY = register("player.attack_velocity", 20.0, 1.0, 1024.0);
    public static final DeferredHolder<Attribute, Attribute> MOVE_VELOCITY = register("player.move_velocity", 20.0, 1.0, 1024.0);

    private static DeferredHolder<Attribute, Attribute> register(String name, double defaultValue, double min, double max) {
        return REGISTER.register(name, location -> new RangedAttribute(
                "attribute.name." + location.getPath(),
                defaultValue, min, max).setSyncable(true));
    }

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
