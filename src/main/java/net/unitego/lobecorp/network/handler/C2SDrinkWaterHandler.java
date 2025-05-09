package net.unitego.lobecorp.network.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.manager.WaterManager;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.network.payload.C2SDrinkWaterPayload;
import net.unitego.lobecorp.registry.MobEffectsRegistry;

public class C2SDrinkWaterHandler {
    public static void handle(C2SDrinkWaterPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!((context.player()) instanceof ServerPlayer serverPlayer)) return;
            WaterManager waterManager = ((ManagerAccess) serverPlayer).lobeCorp$getWaterManager();
            if (!waterManager.hasDrunkWater && (waterManager.needsWater() || serverPlayer.isCreative())) {
                switch (payload.waterSource()) {
                    case C2SDrinkWaterPayload.STREAM -> drinkWater(serverPlayer, waterManager, 2, 0.05f, 40, 600, 1);
                    case C2SDrinkWaterPayload.RAIN -> drinkWater(serverPlayer, waterManager, 1, 0.01f, 30, 200, 0);
                    case C2SDrinkWaterPayload.CAULDRON -> drinkWater(serverPlayer, waterManager, 2, 0.01f, 20, 300, 0);
                }
            }
        });
    }

    private static void drinkWater(ServerPlayer serverPlayer, WaterManager waterManager, int water, float hydrationLevel,
                                   int cooldownTickTimer, int duration, int amplifier) {
        serverPlayer.swing(serverPlayer.getUsedItemHand());
        MiscUtils.playSound(serverPlayer, SoundEvents.GENERIC_DRINK);
        waterManager.drink(water, hydrationLevel);
        serverPlayer.addEffect(new MobEffectInstance(MobEffectsRegistry.THIRST, duration, amplifier));
        waterManager.cooldownTickTimer = cooldownTickTimer;
        waterManager.hasDrunkWater = true;
    }
}
