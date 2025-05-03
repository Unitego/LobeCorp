package net.unitego.lobecorp.common.network.handler;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.network.payload.S2CSwingHandPayload;

public class S2CSwingHandHandler {
    public static void handle(S2CSwingHandPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> context.player().swing(context.player().getUsedItemHand()));
    }
}
