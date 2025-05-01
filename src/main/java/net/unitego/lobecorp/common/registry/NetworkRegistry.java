package net.unitego.lobecorp.common.registry;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.network.handler.S2CSyncIconHandler;
import net.unitego.lobecorp.common.network.handler.S2CSyncStatsHandler;
import net.unitego.lobecorp.common.network.payload.S2CSetSyncStatsPayload;
import net.unitego.lobecorp.common.network.payload.S2CSyncIconPayload;
@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkRegistry {
    public static final String NETWORK_VERSION = "1.0.0";
    @SubscribeEvent
    public static void init(RegisterPayloadHandlersEvent event) {
        event.registrar(NETWORK_VERSION).playToClient(
                S2CSetSyncStatsPayload.TYPE,
                S2CSetSyncStatsPayload.STREAM_CODEC,
                (S2CSyncStatsHandler::handle)
        );
        event.registrar(NETWORK_VERSION).playToClient(
                S2CSyncIconPayload.TYPE,
                S2CSyncIconPayload.STREAM_CODEC,
                (S2CSyncIconHandler::handle)
        );
    }
}
