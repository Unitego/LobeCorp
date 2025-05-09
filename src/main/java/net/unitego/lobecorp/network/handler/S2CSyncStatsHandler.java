package net.unitego.lobecorp.network.handler;

import net.minecraft.world.food.FoodData;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.manager.WaterManager;
import net.unitego.lobecorp.network.payload.S2CSyncStatsPayload;

public class S2CSyncStatsHandler {
    public static void handle(S2CSyncStatsPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            WaterManager waterManager = ((ManagerAccess) context.player()).lobeCorp$getWaterManager();
            FoodData foodData = context.player().getFoodData();
            switch (payload.name()) {
                case S2CSyncStatsPayload.HYDRATION -> waterManager.setHydration(payload.value());
                case S2CSyncStatsPayload.DESICCATION -> waterManager.setDesiccation(payload.value());
                case S2CSyncStatsPayload.SATURATION -> foodData.setSaturation(payload.value());
                case S2CSyncStatsPayload.EXHAUSTION -> foodData.setExhaustion(payload.value());
            }
        });
    }
}
