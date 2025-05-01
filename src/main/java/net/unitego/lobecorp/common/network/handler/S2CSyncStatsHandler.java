package net.unitego.lobecorp.common.network.handler;

import net.minecraft.world.food.FoodData;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.network.payload.S2CSetSyncStatsPayload;

public class S2CSyncStatsHandler {
    public static void handle(S2CSetSyncStatsPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            SanityData sanityData = ((DataAccess) context.player()).lobeCorp$getSanityData();
            WaterData waterData = ((DataAccess) context.player()).lobeCorp$getWaterData();
            FoodData foodData = context.player().getFoodData();

            sanityData.setSanity(payload.sanity());
            waterData.setWaterLevel(payload.waterLevel());
            waterData.setHydration(payload.hydrationLevel());
            waterData.setDesiccation(payload.desiccationLevel());
            foodData.setExhaustion(payload.exhaustionLevel());
        });
    }
}
