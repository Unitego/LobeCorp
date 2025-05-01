package net.unitego.lobecorp.event;

import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.unitego.lobecorp.registry.AttributeRegistry;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/01 14:28:29}
 */
public class AttributeEvents {
    public static void modify(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AttributeRegistry.MAX_SANITY);
        event.add(EntityType.PLAYER, AttributeRegistry.MAX_ASSIMILATION);
    }
}
