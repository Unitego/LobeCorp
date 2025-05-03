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
            switch (payload.name()) {
                case S2CSyncStatsPayload.HYDRATION -> waterData.setHydration(payload.value());
                case S2CSyncStatsPayload.DESICCATION -> waterData.setDesiccation(payload.value());
                case S2CSyncStatsPayload.SATURATION -> foodData.setSaturation(payload.value());
                case S2CSyncStatsPayload.EXHAUSTION -> foodData.setExhaustion(payload.value());
            }
        });
    }
}
