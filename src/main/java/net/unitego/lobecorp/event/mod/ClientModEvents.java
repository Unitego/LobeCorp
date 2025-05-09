package net.unitego.lobecorp.event.mod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.client.init.HudInit;
import net.unitego.lobecorp.client.init.KeyInit;
import net.unitego.lobecorp.client.init.ScreenInit;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.network.handler.S2CUseLogoHandler;
import net.unitego.lobecorp.network.payload.S2CUseLogoPayload;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    //网络注册
    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        event.registrar(MiscUtils.NETWORK_VERSION).playToClient(
                S2CUseLogoPayload.TYPE, S2CUseLogoPayload.STREAM_CODEC, S2CUseLogoHandler::handle
        );
    }

    //界面注册
    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        ScreenInit.init(event);
    }

    //热键注册
    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        KeyInit.init(event);
    }

    //GUI注册
    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        HudInit.init(event);
    }
}
