package net.unitego.lobecorp.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.util.ITeleporter;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.manager.SanityManager;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.manager.WaterManager;
import net.unitego.lobecorp.network.sender.S2CSetSanitySender;
import net.unitego.lobecorp.network.sender.S2CSyncEquipmentSender;
import net.unitego.lobecorp.network.sender.S2CSyncStatsSender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Unique
    final
    ServerPlayer lobeCorp$serverPlayer = (ServerPlayer) (Object) this;
    @Unique
    private final WaterManager lobeCorp$waterManager = ((ManagerAccess) this).lobeCorp$getWaterManager();
    @Unique
    private final SanityManager lobeCorp$sanityManager = ((ManagerAccess) this).lobeCorp$getSanityManager();
    @Unique
    private float lobeCorp$lastSentSanity = -1.0E8F;
    @Unique
    private int lobeCorp$lastSentWater = -99999999;
    @Unique
    private boolean lobeCorp$lastWaterHydrationZero = true;

    //同步状态
    @Inject(method = "tick", at = @At("TAIL"))
    private void tickMixin(CallbackInfo ci) {
        S2CSyncStatsSender.send(lobeCorp$serverPlayer);
        S2CSyncEquipmentSender.send(lobeCorp$serverPlayer);
    }

    //同步精神机制相关数值
    @Inject(method = "doTick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerPlayer;getHealth()F", ordinal = 0))
    private void doTickMixin(CallbackInfo ci) {
        if (lobeCorp$sanityManager.getSanity() != lobeCorp$lastSentSanity
                || lobeCorp$lastSentWater != lobeCorp$waterManager.getWaterLevel() ||
                lobeCorp$waterManager.getHydrationLevel() == 0.0F != lobeCorp$lastWaterHydrationZero) {
            S2CSetSanitySender.send(lobeCorp$serverPlayer);
            lobeCorp$lastSentSanity = lobeCorp$sanityManager.getSanity();
            lobeCorp$lastSentWater = lobeCorp$waterManager.getWaterLevel();
            lobeCorp$lastWaterHydrationZero = lobeCorp$waterManager.getHydrationLevel() == 0.0F;
        }
    }

    //维度改变同步
    @Inject(method = "changeDimension", at = @At(value = "FIELD",
            target = "Lnet/minecraft/server/level/ServerPlayer;lastSentHealth:F"))
    private void changeDimensionMixin(ServerLevel server, ITeleporter teleporter, CallbackInfoReturnable<Entity> cir) {
        lobeCorp$lastSentSanity = -1.0F;
        lobeCorp$lastSentWater = -1;
    }

    //重置发送信息
    @Inject(method = "resetSentInfo", at = @At(value = "FIELD",
            target = "Lnet/minecraft/server/level/ServerPlayer;lastSentHealth:F"))
    private void resetSentInfoMixin(CallbackInfo ci) {
        lobeCorp$lastSentSanity = -1.0E8F;
    }

    //通关后同步与重置某些属性
    @Inject(method = "restoreFrom", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerPlayer;setHealth(F)V"))
    private void restoreFromMixin1(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
        StaffManager staffManager = ((ManagerAccess) that).lobeCorp$getStaffManager();
        //最大生命值
        staffManager.setMaxHealthBaseValue(staffManager.getMaxHealthBaseValue());
        //最大精神值
        staffManager.setMaxSanityBaseValue(staffManager.getMaxSanityBaseValue());
        //工作成率和工作速率
        staffManager.setWorkSuccessBaseValue(staffManager.getWorkSuccessBaseValue());
        staffManager.setWorkVelocityBaseValue(staffManager.getWorkVelocityBaseValue());
        //攻击速率和移动速率
        staffManager.setAttackVelocityBaseValue(staffManager.getAttackVelocityBaseValue());
        staffManager.setMoveVelocityBaseValue(staffManager.getMoveVelocityBaseValue());
        //其余同步
        SanityManager sanityManager = ((ManagerAccess) that).lobeCorp$getSanityManager();
        WaterManager waterManager = ((ManagerAccess) that).lobeCorp$getWaterManager();
        lobeCorp$sanityManager.setSanity(sanityManager.getSanity());
        lobeCorp$waterManager.setWaterLevel(waterManager.getWaterLevel());
        lobeCorp$waterManager.setHydration(waterManager.getHydrationLevel());
        lobeCorp$waterManager.setDesiccation(waterManager.getDesiccationLevel());
    }

    //实体复制同步
    @Inject(method = "restoreFrom", at = @At(value = "FIELD",
            target = "Lnet/minecraft/server/level/ServerPlayer;lastSentHealth:F"))
    private void restoreFromMixin2(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
        lobeCorp$lastSentSanity = -1.0F;
        lobeCorp$lastSentWater = -1;
    }
}
