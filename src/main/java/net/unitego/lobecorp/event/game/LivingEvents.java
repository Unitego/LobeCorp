package net.unitego.lobecorp.event.game;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.util.DamageUtils;
import net.unitego.lobecorp.loader.HydratingFoodLoader;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class LivingEvents {
    //玩家使用物品回复干渴值
    @SubscribeEvent
    public static void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            Item item = event.getItem().getItem();
            WaterData waterData = ((DataAccess) player).lobeCorp$getWaterData();
            HydratingFoodLoader.get(item).ifPresent(data -> waterData.drink(data.water(), data.hydration()));
        }
    }

    //LivingDamageEvent用这个会导致每次都先扣伤害吸收值，所以用LivingHurtEvent
    //伤害分流事件
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
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
