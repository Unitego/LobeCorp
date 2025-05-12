package net.unitego.lobecorp.event.game;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.access.LobeCorpSlotAccess;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.effect.AbstractVulnerableEffect;
import net.unitego.lobecorp.common.item.badge.TeamBadge;
import net.unitego.lobecorp.common.item.ego.EGOWeaponItem;
import net.unitego.lobecorp.common.manager.WaterManager;
import net.unitego.lobecorp.common.util.*;
import net.unitego.lobecorp.data.loader.HydratingFoodLoader;
import net.unitego.lobecorp.network.sender.S2CSyncEquipmentSender;
import net.unitego.lobecorp.network.sender.S2CSyncStatsSender;
import net.unitego.lobecorp.registry.AttachmentTypesRegistry;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class CommonGameEvents {
    //生物受伤
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        DamageSource source = event.getSource();
        float amount = event.getAmount();
        //易伤效果处理
        for (MobEffectInstance activeEffect : living.getActiveEffects()) {
            if (activeEffect.getEffect().value() instanceof AbstractVulnerableEffect vulnerableEffect) {
                if (source.is(vulnerableEffect.getDamageType())) {
                    event.setAmount(amount * (activeEffect.getAmplifier() + 1) * 1.5f);
                    return;
                }
            }
        }
        if (!(living instanceof ServerPlayer serverPlayer)) return;
        //玩家进入战斗状态标记
        CombatStatusUtils.enteredCombat(serverPlayer);
    }

    //攻击实体
    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        Entity target = event.getTarget();
        //玩家进入战斗状态标记
        CombatStatusUtils.enteredCombat(serverPlayer);
        //玩家使用EGO武器进行攻击
        ItemStack mainHandItem = serverPlayer.getMainHandItem();
        if (mainHandItem.getItem() instanceof EGOWeaponItem egoWeaponItem) {
            DamageUtils.handlePlayerAttack(target, serverPlayer, egoWeaponItem);
            event.setCanceled(true);
        }
    }

    //玩家Tick前
    @SubscribeEvent
    public static void onPlayerTickPre(PlayerTickEvent.Pre event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        //部门同步
        TeamBadge.syncTeam(serverPlayer);
        //恐慌状态
        ((ManagerAccess) serverPlayer).lobeCorp$getSanityManager().panicState();
        //EGO反噬机制
        EGOBacklashUtils.applyEGOWeapon(serverPlayer, serverPlayer.getMainHandItem());
        EGOBacklashUtils.applyEGOWeapon(serverPlayer, serverPlayer.getOffhandItem());
        EGOBacklashUtils.applyEGOWeapon(serverPlayer, MiscUtils.getLobeCorpStack(serverPlayer, LobeCorpEquipmentSlot.LOBECORP_WEAPON));
        EGOBacklashUtils.applyEGOSuit(serverPlayer, MiscUtils.getLobeCorpStack(serverPlayer, LobeCorpEquipmentSlot.LOBECORP_SUIT));
        //正义品质修饰应用
        JusticeModifierUtils.apply(serverPlayer);
    }

    //玩家Tick后
    @SubscribeEvent
    public static void onPlayerTickPost(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        //弱抗减速机制移除
        LowResistSlowUtils.remove(serverPlayer);
        //战斗状态机制移除
        CombatStatusUtils.checkCombatTimeout(serverPlayer);
        //装备修饰机制应用
        EquipmentModifierUtils.tick(serverPlayer);
        //脑叶公司tick
        ItemStackHandler handler = serverPlayer.getData(AttachmentTypesRegistry.LOBECORP_STACK);
        for (int slotId = 0; slotId < handler.getSlots(); slotId++) {
            ItemStack stack = handler.getStackInSlot(slotId);
            if (!stack.isEmpty() && stack.getItem() instanceof LobeCorpSlotAccess access) {
                access.onLobeCorpTick(serverPlayer);
            }
        }
    }

    //玩家重生
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        S2CSyncEquipmentSender.remove(serverPlayer);
    }

    //服务端开始
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        Commands commands = server.getCommands();
        //脑叶公司部门创建
        CommandSourceStack commandSourceStack = server.createCommandSourceStack();
        TeamBadge.createTeam(commands, commandSourceStack);
        //数据驱动文件加载
        HydratingFoodLoader.load(server).thenRun(() -> {
        });
    }

    //生物掉落
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        if (!serverPlayer.isDeadOrDying()) return;
        if (serverPlayer.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) return;
        //脑叶公司插槽drop
        ItemStackHandler handler = serverPlayer.getData(AttachmentTypesRegistry.LOBECORP_STACK);
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                event.getDrops().add(new ItemEntity(
                        serverPlayer.level(),
                        serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                        stack.copy())
                );
                handler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
    }

    //玩家克隆
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!(event.getEntity() instanceof ServerPlayer newPlayer) || !(event.getOriginal() instanceof ServerPlayer oldPlayer))
            return;
        // 判断是否死亡且是否启用保留物品
        if (!event.isWasDeath() || !newPlayer.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) return;
        //脑叶公司插槽clone
        ItemStackHandler oldHandler = oldPlayer.getData(AttachmentTypesRegistry.LOBECORP_STACK);
        ItemStackHandler newHandler = newPlayer.getData(AttachmentTypesRegistry.LOBECORP_STACK);
        // 拷贝物品
        for (int i = 0; i < oldHandler.getSlots(); i++) {
            ItemStack stack = oldHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                // 克隆物品时，复制并考虑 NBT 数据的同步
                ItemStack newStack = stack.copy();
                newHandler.setStackInSlot(i, newStack);
            }
        }
    }

    //生物使用完物品
    @SubscribeEvent
    public static void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        //玩家通过物品恢复水分
        Item item = event.getItem().getItem();
        WaterManager waterManager = ((ManagerAccess) serverPlayer).lobeCorp$getWaterManager();
        HydratingFoodLoader.get(item).ifPresent(data -> waterManager.drink(data.water(), data.hydration()));
    }

    //玩家登录
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        S2CSyncStatsSender.remove(serverPlayer);
        S2CSyncEquipmentSender.remove(serverPlayer);
    }

    //生物死亡
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        //玩家死后移除所有修饰符
        CombatStatusUtils.clearOnDeath(serverPlayer);
    }
}
