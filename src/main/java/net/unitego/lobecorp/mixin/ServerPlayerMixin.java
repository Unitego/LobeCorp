package net.unitego.lobecorp.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.util.ITeleporter;
import net.unitego.lobecorp.access.DataAccess;
import net.unitego.lobecorp.data.SanityData;
import net.unitego.lobecorp.data.WaterData;
import net.unitego.lobecorp.network.sender.S2CSyncIconSender;
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

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickMixin(CallbackInfo ci) {
        S2CSyncIconSender.send(lobeCorp$serverPlayer);
    }

    @Inject(method = "doTick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerPlayer;getHealth()F", ordinal = 0))
    private void doTickMixin(CallbackInfo ci) {
        if (lobeCorp$sanityData.getSanity() != lobeCorp$lastSentSanity
                || lobeCorp$lastSentWater != lobeCorp$waterData.getWaterLevel() ||
                lobeCorp$waterData.getHydrationLevel() == 0.0F != lobeCorp$lastWaterHydrationZero) {
            S2CSyncStatsSender.send(lobeCorp$serverPlayer);
            lobeCorp$lastSentSanity = lobeCorp$sanityData.getSanity();
            lobeCorp$lastSentWater = lobeCorp$waterData.getWaterLevel();
            lobeCorp$lastWaterHydrationZero = lobeCorp$waterData.getHydrationLevel() == 0.0F;
        }
    }

    @Inject(method = "changeDimension", at = @At(value = "FIELD",
            target = "Lnet/minecraft/server/level/ServerPlayer;lastSentHealth:F"))
    private void changeDimensionMixin(ServerLevel server, ITeleporter teleporter, CallbackInfoReturnable<Entity> cir) {
        lobeCorp$lastSentSanity = -1.0F;
        lobeCorp$lastSentWater = -1;
    }

    @Inject(method = "resetSentInfo", at = @At(value = "FIELD",
            target = "Lnet/minecraft/server/level/ServerPlayer;lastSentHealth:F"))
    private void resetSentInfoMixin(CallbackInfo ci) {
        lobeCorp$lastSentSanity = -1.0E8F;
    }

    @Inject(method = "restoreFrom", at = @At(value = "FIELD",
            target = "Lnet/minecraft/server/level/ServerPlayer;foodData:Lnet/minecraft/world/food/FoodData;", ordinal = 0))
    private void restoreFromMixin1(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
        lobeCorp$sanityData.setSanity(((DataAccess) that).lobeCorp$getSanityData().getSanity());
        lobeCorp$waterData.setWaterLevel(((DataAccess) that).lobeCorp$getWaterData().getWaterLevel());
        lobeCorp$waterData.setHydration(((DataAccess) that).lobeCorp$getWaterData().getHydrationLevel());
        lobeCorp$waterData.setDesiccation(((DataAccess) that).lobeCorp$getWaterData().getDesiccationLevel());
    }

    @Inject(method = "restoreFrom", at = @At(value = "FIELD",
            target = "Lnet/minecraft/server/level/ServerPlayer;lastSentHealth:F"))
    private void restoreFromMixin2(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
        lobeCorp$lastSentSanity = -1.0F;
        lobeCorp$lastSentWater = -1;
    }
}
