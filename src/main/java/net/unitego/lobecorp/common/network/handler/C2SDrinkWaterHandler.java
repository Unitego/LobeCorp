package net.unitego.lobecorp.common.network.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.network.payload.C2SDrinkWaterPayload;
import net.unitego.lobecorp.common.network.sender.S2CSwingHandSender;
import net.unitego.lobecorp.common.registry.ModMobEffects;

public class C2SDrinkWaterHandler {
    public static void handle(C2SDrinkWaterPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!((context.player()) instanceof ServerPlayer serverPlayer)) return;
            WaterData waterData = ((DataAccess) serverPlayer).lobeCorp$getWaterData();
            if (waterData.noDrink() && (waterData.needsWater() || serverPlayer.isCreative())) {
                switch (payload.waterSource()) {
                    case C2SDrinkWaterPayload.STREAM -> {
                        S2CSwingHandSender.send(serverPlayer);
                        serverPlayer.swing(serverPlayer.getUsedItemHand());
                        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1, 1);
                        waterData.drink(2, 0.05f);
                        serverPlayer.addEffect(new MobEffectInstance(ModMobEffects.THIRST, 600, 1));
                        waterData.hasDrunkStream = true;
                    }
                    case C2SDrinkWaterPayload.RAIN -> {
                        S2CSwingHandSender.send(serverPlayer);
                        serverPlayer.swing(serverPlayer.getUsedItemHand());
                        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1, 1);
                        waterData.drink(1, 0.01f);
                        waterData.hasDrunkRain = true;
                    }
                    case C2SDrinkWaterPayload.CAULDRON -> {
                        S2CSwingHandSender.send(serverPlayer);
                        serverPlayer.swing(serverPlayer.getUsedItemHand());
                        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1, 1);
                        waterData.drink(2, 0.01f);
                        serverPlayer.addEffect(new MobEffectInstance(ModMobEffects.THIRST, 300));
                        waterData.hasDrunkCauldron = true;
                    }
                }
            }
        });
    }
}
