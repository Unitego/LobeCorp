package net.unitego.lobecorp.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.util.ITeleporter;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.network.sender.S2CSetSanitySender;
import net.unitego.lobecorp.common.network.sender.S2CSyncStatsSender;
import net.unitego.lobecorp.common.registry.ModAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Unique
    ServerPlayer lobeCorp$serverPlayer = (ServerPlayer) (Object) this;
    @Unique
    private WaterData lobeCorp$waterData = ((DataAccess) this).lobeCorp$getWaterData();
    @Unique
    private SanityData lobeCorp$sanityData = ((DataAccess) this).lobeCorp$getSanityData();
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
    }

    //同步精神机制相关数值
    @Inject(method = "doTick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerPlayer;getHealth()F", ordinal = 0))
    private void doTickMixin(CallbackInfo ci) {
        if (lobeCorp$sanityData.getSanity() != lobeCorp$lastSentSanity
                || lobeCorp$lastSentWater != lobeCorp$waterData.getWaterLevel() ||
                lobeCorp$waterData.getHydrationLevel() == 0.0F != lobeCorp$lastWaterHydrationZero) {
            S2CSetSanitySender.send(lobeCorp$serverPlayer);
            lobeCorp$lastSentSanity = lobeCorp$sanityData.getSanity();
            lobeCorp$lastSentWater = lobeCorp$waterData.getWaterLevel();
            lobeCorp$lastWaterHydrationZero = lobeCorp$waterData.getHydrationLevel() == 0.0F;
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
        lobeCorp$sanityData.setSanity(((DataAccess) that).lobeCorp$getSanityData().getSanity());
        lobeCorp$waterData.setWaterLevel(((DataAccess) that).lobeCorp$getWaterData().getWaterLevel());
        lobeCorp$waterData.setHydration(((DataAccess) that).lobeCorp$getWaterData().getHydrationLevel());
        lobeCorp$waterData.setDesiccation(((DataAccess) that).lobeCorp$getWaterData().getDesiccationLevel());
        double maxSanityBaseValue = Objects.requireNonNull(that.getAttribute(ModAttributes.MAX_SANITY)).getBaseValue();
        Objects.requireNonNull(lobeCorp$serverPlayer.getAttribute(ModAttributes.MAX_SANITY)).setBaseValue(maxSanityBaseValue);
        double maxHealthBaseValue = Objects.requireNonNull(that.getAttribute(Attributes.MAX_HEALTH)).getBaseValue();
        Objects.requireNonNull(lobeCorp$serverPlayer.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(maxHealthBaseValue);
    }

    //实体复制同步
    @Inject(method = "restoreFrom", at = @At(value = "FIELD",
            target = "Lnet/minecraft/server/level/ServerPlayer;lastSentHealth:F"))
    private void restoreFromMixin2(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
        lobeCorp$lastSentSanity = -1.0F;
        lobeCorp$lastSentWater = -1;
    }
}
