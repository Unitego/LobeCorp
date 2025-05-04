package net.unitego.lobecorp.common.network.handler;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.network.payload.S2CDrinkResetPayload;

public class S2CDrinkResetHandler {
    public static void handle(S2CDrinkResetPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            WaterData waterData = ((DataAccess) context.player()).lobeCorp$getWaterData();
            waterData.hasDrunkWater = false;
        });
    }
}
