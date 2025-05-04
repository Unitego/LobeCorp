package net.unitego.lobecorp.event.mod;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.network.handler.*;
import net.unitego.lobecorp.common.network.payload.*;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkEvents {
    public static final String NETWORK_VERSION = "1.0.0";

    //注册网络包
    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        event.registrar(NETWORK_VERSION).playToServer(
                C2SDrinkWaterPayload.TYPE, C2SDrinkWaterPayload.STREAM_CODEC, C2SDrinkWaterHandler::handle
        );
        event.registrar(NETWORK_VERSION).playToServer(
                C2SOpenEquipmentPayload.TYPE, C2SOpenEquipmentPayload.STREAM_CODEC, C2SOpenEquipmentHandler::handle
        );
        event.registrar(NETWORK_VERSION).playToClient(
                S2CDrinkResetPayload.TYPE, S2CDrinkResetPayload.STREAM_CODEC, S2CDrinkResetHandler::handle
        );
        event.registrar(NETWORK_VERSION).playToClient(
                S2CSyncStatsPayload.TYPE, S2CSyncStatsPayload.STREAM_CODEC, S2CSyncStatsHandler::handle
        );
        event.registrar(NETWORK_VERSION).playToClient(
                S2CSetSanityPayload.TYPE, S2CSetSanityPayload.STREAM_CODEC, S2CSetSanityHandler::handle
        );
    }
}
