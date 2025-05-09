package net.unitego.lobecorp.network.handler;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.manager.WaterManager;
import net.unitego.lobecorp.network.payload.S2CDrinkResetPayload;

public class S2CDrinkResetHandler {
    public static void handle(S2CDrinkResetPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            WaterManager waterManager = ((ManagerAccess) context.player()).lobeCorp$getWaterManager();
            waterManager.hasDrunkWater = false;
        });
    }
}
