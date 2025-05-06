package net.unitego.lobecorp.common.network.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.network.payload.C2SDrinkWaterPayload;
import net.unitego.lobecorp.common.registry.ModMobEffects;
import net.unitego.lobecorp.common.util.LobeCorpUtils;

public class C2SDrinkWaterHandler {
    public static void handle(C2SDrinkWaterPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!((context.player()) instanceof ServerPlayer serverPlayer)) return;
            WaterData waterData = ((DataAccess) serverPlayer).lobeCorp$getWaterData();
            if (!waterData.hasDrunkWater && (waterData.needsWater() || serverPlayer.isCreative())) {
                switch (payload.waterSource()) {
                    case C2SDrinkWaterPayload.STREAM -> drinkWater(serverPlayer, waterData, 2, 0.05f, 40, 600, 1);
                    case C2SDrinkWaterPayload.RAIN -> drinkWater(serverPlayer, waterData, 1, 0.01f, 30, 200, 0);
                    case C2SDrinkWaterPayload.CAULDRON -> drinkWater(serverPlayer, waterData, 2, 0.01f, 20, 300, 0);
                }
            }
        });
    }

    private static void drinkWater(ServerPlayer serverPlayer, WaterData waterData, int water, float hydrationLevel,
                                   int cooldownTickTimer, int duration, int amplifier) {
        serverPlayer.swing(serverPlayer.getUsedItemHand());
        LobeCorpUtils.playSound(serverPlayer, SoundEvents.GENERIC_DRINK);
        waterData.drink(water, hydrationLevel);
        serverPlayer.addEffect(new MobEffectInstance(ModMobEffects.THIRST, duration, amplifier));
        waterData.cooldownTickTimer = cooldownTickTimer;
        waterData.hasDrunkWater = true;
    }
}
