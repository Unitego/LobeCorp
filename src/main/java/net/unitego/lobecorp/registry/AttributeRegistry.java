package net.unitego.lobecorp.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.unitego.lobecorp.LobeCorp;

public class AttributeRegistry {
    public static final Holder<Attribute> MAX_SANITY = register("player.max_sanity",
            new RangedAttribute("attribute.name.player.max_sanity",
                    20.0, 1.0, 1024.0).setSyncable(true));
    public static final Holder<Attribute> MAX_ASSIMILATION = register("player.max_assimilation",
            new RangedAttribute("attribute.name.player.max_assimilation",
                    0.0, 0.0, 2048.0).setSyncable(true)
    );

    private static Holder<Attribute> register(String name, Attribute attribute) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, LobeCorp.rl(name), attribute);
    }
}
