package net.unitego.lobecorp.init;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.unitego.lobecorp.network.handler.S2CSetSanityHandler;
import net.unitego.lobecorp.network.payload.S2CSetSanityPayload;

public class NetworkRegistry {
    public static final String NETWORK_VERSION = "1.0";

    @SubscribeEvent
    public static void init(RegisterPayloadHandlersEvent event) {
        event.registrar(NETWORK_VERSION).playToClient(
                S2CSetSanityPayload.TYPE,
                S2CSetSanityPayload.STREAM_CODEC,
                (S2CSetSanityHandler::handle)
        );
    }
}
