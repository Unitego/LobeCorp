package net.unitego.lobecorp.common.network.handler;

import net.minecraft.world.food.FoodData;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.network.payload.S2CSyncStatsPayload;

public class S2CSyncStatsHandler {
    public static void handle(S2CSyncStatsPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            WaterData waterData = ((DataAccess) context.player()).lobeCorp$getWaterData();
            FoodData foodData = context.player().getFoodData();
            if (S2CSyncStatsPayload.HYDRATION.equals(payload.name())) {
                waterData.setHydration(payload.value());
            } else if (S2CSyncStatsPayload.DESICCATION.equals(payload.name())) {
                waterData.setDesiccation(payload.value());
            } else if (S2CSyncStatsPayload.SATURATION.equals(payload.name())) {
                foodData.setSaturation(payload.value());
            } else if (S2CSyncStatsPayload.EXHAUSTION.equals(payload.name())) {
                foodData.setExhaustion(payload.value());
            }
        });
    }
}
