package net.unitego.lobecorp.event.game;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.util.DamageUtils;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class LivingEvents {
    //LivingDamageEvent用这个会导致每次都先扣伤害吸收值，所以用LivingHurtEvent
    //伤害分流事件
    @SubscribeEvent
    public static void DamageDistributor(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        DamageSource source = event.getSource();
        float amount = event.getAmount();

        if (living instanceof Player player) {
            DamageUtils.handlePlayerDamage(player, source, amount);
        } else {
            DamageUtils.handleNonPlayerDamage(living, source, amount);
        }
        event.setCanceled(true);
    }
}