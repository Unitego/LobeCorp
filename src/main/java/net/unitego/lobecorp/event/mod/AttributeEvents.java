package net.unitego.lobecorp.event.mod;

import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.registry.ModAttributes;
import net.unitego.lobecorp.common.registry.SEDRegistry;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AttributeEvents {
    //给玩家增加属性
    @SubscribeEvent
    public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        SEDRegistry.init();
        event.add(EntityType.PLAYER, ModAttributes.MAX_SANITY);
        event.add(EntityType.PLAYER, ModAttributes.MAX_ASSIMILATION);
    }
}
