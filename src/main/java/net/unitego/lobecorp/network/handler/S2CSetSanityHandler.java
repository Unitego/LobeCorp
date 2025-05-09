package net.unitego.lobecorp.network.handler;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.manager.SanityManager;
import net.unitego.lobecorp.common.manager.WaterManager;
import net.unitego.lobecorp.network.payload.S2CSetSanityPayload;

public class S2CSetSanityHandler {
    public static void handle(S2CSetSanityPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            SanityManager sanityManager = ((ManagerAccess) context.player()).lobeCorp$getSanityManager();
            WaterManager waterManager = ((ManagerAccess) context.player()).lobeCorp$getWaterManager();

            sanityManager.setSanity(payload.sanity());
            waterManager.setWaterLevel(payload.waterLevel());
            waterManager.setHydration(payload.hydrationLevel());
        });
    }
}
