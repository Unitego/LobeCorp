package net.unitego.lobecorp.event.game;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponItem;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.manager.WaterManager;
import net.unitego.lobecorp.common.util.DamageUtils;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.data.loader.HydratingFoodLoader;
import net.unitego.lobecorp.network.sender.S2CSyncEquipmentSender;
import net.unitego.lobecorp.network.sender.S2CSyncStatsSender;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class CommonGameEvents {
    //攻击实体
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

    //玩家Tick前
    @SubscribeEvent
    public static void onPlayerTickPre(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        //恐慌状态
        ((ManagerAccess) player).lobeCorp$getSanityManager().panicState();
        //脑叶公司攻击速率移动速率修饰原版攻击速度移动速度
        StaffManager staffManager = ((ManagerAccess) player).lobeCorp$getStaffManager();
        double attackVelocity = staffManager.getAttackVelocity();
        double moveVelocity = staffManager.getMoveVelocity();
        // 修改原版攻击速度（仅当数值变化时更新）
        AttributeInstance attackSpeed = player.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeed != null) {
            MiscUtils.updateAttributeModifier(attackSpeed, MiscUtils.ATTRIBUTE_ATTACK_VELOCITY_MODIFIER_ID,
                    "LobeCorp Attack Velocity Modifier", (attackVelocity * 0.2f) / 100,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        }
        // 修改原版移动速度（仅当数值变化时更新）
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed != null) {
            MiscUtils.updateAttributeModifier(movementSpeed, MiscUtils.ATTRIBUTE_MOVE_VELOCITY_MODIFIER_ID,
                    "LobeCorp Move Velocity Modifier", (moveVelocity * 0.2f) / 100,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        }
    }

    //服务端开始
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        HydratingFoodLoader.load(event.getServer()).thenRun(() -> {
        });
    }

    //生物使用完物品
    @SubscribeEvent
    public static void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            Item item = event.getItem().getItem();
            WaterManager waterManager = ((ManagerAccess) player).lobeCorp$getWaterManager();
            HydratingFoodLoader.get(item).ifPresent(data -> waterManager.drink(data.water(), data.hydration()));
        }
    }

    //玩家登录
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            S2CSyncStatsSender.remove(serverPlayer);
            S2CSyncEquipmentSender.remove(serverPlayer);
        }
    }
}
