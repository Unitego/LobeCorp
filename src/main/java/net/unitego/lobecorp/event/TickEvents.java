package net.unitego.lobecorp.event;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.unitego.lobecorp.access.DataAccess;
import net.unitego.lobecorp.data.SanityData;
import net.unitego.lobecorp.registry.AttributeRegistry;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/01 14:20:08}
 */
public class TickEvents {
    public static void playerTickEvent(PlayerTickEvent.Pre event){
        var player = event.getEntity();
        for (AttributeInstance attributeinstance : player.getAttributes().getDirtyAttributes()) {
            var attribute = attributeinstance.getAttribute();
            SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
            if (attribute.equals(AttributeRegistry.MAX_SANITY)) {
                float f = sanityData.getMaxSanity();
                if (sanityData.getSanity() > f) {
                    sanityData.setSanity(f);
                }
            } else if (attribute.equals(AttributeRegistry.MAX_ASSIMILATION)) {
                float f1 = sanityData.getMaxAssimilation();
                if (sanityData.getAssimilationAmount() > f1) {
                    sanityData.setAssimilationAmount(f1);
                }
            }
        }
    }
}
