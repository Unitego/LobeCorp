package net.unitego.lobecorp.event.game;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponItem;
import net.unitego.lobecorp.common.network.sender.S2CSyncEquipmentSender;
import net.unitego.lobecorp.common.network.sender.S2CSyncStatsSender;
import net.unitego.lobecorp.common.util.DamageUtils;
import net.unitego.lobecorp.loader.HydratingFoodLoader;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class CommonGameEvents {
    //攻击者使用EGO武器攻击实体
    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        Entity target = event.getTarget();
        Player player = event.getEntity();
        ItemStack mainHandItem = player.getMainHandItem();
        if (!player.level().isClientSide && mainHandItem.getItem() instanceof EGOWeaponItem egoWeaponItem) {
            DamageUtils.handlePlayerAttack(target, player, egoWeaponItem);
            event.setCanceled(true);
        }
    }

    //伤害分流事件
    @SubscribeEvent
    public static void onLivingHurt(LivingDamageEvent event) {
        LivingEntity living = event.getEntity();//受击者
        DamageSource source = event.getSource();//伤害源
        float amount = event.getAmount();//伤害
        if (living instanceof Player player) DamageUtils.handlePlayerHurt(player, source, amount);
        else DamageUtils.handleNonPlayerHurt(living, source, amount);
        event.setCanceled(true);
    }

    //数据驱动
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        HydratingFoodLoader.load(event.getServer()).thenRun(() -> {
        });
    }

    //玩家使用物品回复干渴值
    @SubscribeEvent
    public static void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            Item item = event.getItem().getItem();
            WaterData waterData = ((DataAccess) player).lobeCorp$getWaterData();
            HydratingFoodLoader.get(item).ifPresent(data -> waterData.drink(data.water(), data.hydration()));
        }
    }

    //移除玩家数据
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            S2CSyncStatsSender.remove(serverPlayer);
            S2CSyncEquipmentSender.remove(serverPlayer);
        }
    }
}
