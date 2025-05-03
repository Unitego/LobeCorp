package net.unitego.lobecorp.common.network.handler;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.network.payload.S2CSetSanityPayload;

public class S2CSetSanityHandler {
    public static void handle(S2CSetSanityPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            SanityData sanityData = ((DataAccess) context.player()).lobeCorp$getSanityData();
            WaterData waterData = ((DataAccess) context.player()).lobeCorp$getWaterData();

            sanityData.setSanity(payload.sanity());
            waterData.setWaterLevel(payload.waterLevel());
            waterData.setHydration(payload.hydrationLevel());
        });
    }
}
