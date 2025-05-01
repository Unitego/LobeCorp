package net.unitego.lobecorp.network.handler;

import net.minecraft.world.food.FoodData;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.access.DataAccess;
import net.unitego.lobecorp.data.WaterData;
import net.unitego.lobecorp.network.payload.S2CSyncIconPayload;

public class S2CSyncIconHandler {
    public static void handle(S2CSyncIconPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            WaterData waterData = ((DataAccess) context.player()).lobeCorp$getWaterData();
            FoodData foodData = context.player().getFoodData();
            if (S2CSyncIconPayload.HYDRATION.equals(payload.name())) {
                waterData.setHydration(payload.value());
            } else if (S2CSyncIconPayload.DESICCATION.equals(payload.name())) {
                waterData.setDesiccation(payload.value());
            } else if (S2CSyncIconPayload.SATURATION.equals(payload.name())) {
                foodData.setSaturation(payload.value());
            } else if (S2CSyncIconPayload.EXHAUSTION.equals(payload.name())) {
                foodData.setExhaustion(payload.value());
            }
        });
    }
}
