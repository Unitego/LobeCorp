package net.unitego.lobecorp.event.game;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.registry.tag.DamageTypeTagRegistry;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/01 12:19:08}
 */
//@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class LivingEvents {
//    @SubscribeEvent
    public static void huntEvent(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        Entity entity = source.getEntity();
        if (!(entity instanceof Player player)) return;
        float amount = event.getAmount();
        SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
        if (source.is(DamageTypeTagRegistry.RED.get())) {
            float health = amount;
        }
    }
}
