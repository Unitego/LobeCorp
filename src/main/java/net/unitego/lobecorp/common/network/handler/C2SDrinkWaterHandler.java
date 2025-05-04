package net.unitego.lobecorp.common.network.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.network.payload.C2SDrinkWaterPayload;
import net.unitego.lobecorp.common.registry.ModMobEffects;

public class C2SDrinkWaterHandler {
    public static void handle(C2SDrinkWaterPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!((context.player()) instanceof ServerPlayer serverPlayer)) return;
            WaterData waterData = ((DataAccess) serverPlayer).lobeCorp$getWaterData();
            if (!waterData.hasDrunkWater && (waterData.needsWater() || serverPlayer.isCreative())) {
                switch (payload.waterSource()) {
                    case C2SDrinkWaterPayload.STREAM -> {
                        serverPlayer.swing(serverPlayer.getUsedItemHand());
                        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1, 1);
                        waterData.drink(2, 0.05f);
                        serverPlayer.addEffect(new MobEffectInstance(ModMobEffects.THIRST, 600, 1));
                        waterData.cooldownTickTimer = 40;
                        waterData.hasDrunkWater = true;
                    }
                    case C2SDrinkWaterPayload.RAIN -> {
                        serverPlayer.swing(serverPlayer.getUsedItemHand());
                        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1, 1);
                        waterData.drink(1, 0.01f);
                        waterData.cooldownTickTimer = 30;
                        waterData.hasDrunkWater = true;
                    }
                    case C2SDrinkWaterPayload.CAULDRON -> {
                        serverPlayer.swing(serverPlayer.getUsedItemHand());
                        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1, 1);
                        waterData.drink(2, 0.01f);
                        serverPlayer.addEffect(new MobEffectInstance(ModMobEffects.THIRST, 300));
                        waterData.cooldownTickTimer = 20;
                        waterData.hasDrunkWater = true;
                    }
                }
            }
        });
    }
}
