package net.unitego.lobecorp.event.mod;

import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.registry.AttributeRegistry;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/01 14:28:29}
 */
@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AttributeEvents {
    @SubscribeEvent
    public static void modify(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AttributeRegistry.MAX_SANITY);
        event.add(EntityType.PLAYER, AttributeRegistry.MAX_ASSIMILATION);
    }
}
