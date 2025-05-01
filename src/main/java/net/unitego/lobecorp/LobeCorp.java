package net.unitego.lobecorp;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.unitego.lobecorp.hud.HUDEvent;
import net.unitego.lobecorp.network.sender.S2CSyncIconSender;
import net.unitego.lobecorp.registry.EffectRegistry;
import net.unitego.lobecorp.registry.HUDRegistry;
import net.unitego.lobecorp.registry.NetworkRegistry;
import net.unitego.lobecorp.registry.SEDRegistry;
import org.slf4j.Logger;

@Mod(LobeCorp.MOD_ID)
public class LobeCorp {
    public static final String MOD_ID = "lobecorp";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LobeCorp(IEventBus bus, ModContainer container) {
        LOGGER.info("Face the Fear, Build the Future.");

        HUDRegistry.init();
        SEDRegistry.init();

        EffectRegistry.init(bus);

        bus.addListener(NetworkRegistry::init);

        NeoForge.EVENT_BUS.register(S2CSyncIconSender.class);
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
            HUDEvent.registerCustomHUDLayers(event);
        }
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class ClientGameEvents {
        @SubscribeEvent
        public static void onRenderGuiLayerPre(RenderGuiLayerEvent.Pre event) {
            HUDEvent.suppressVanillaHUDLayers(event);
        }
    }
}
