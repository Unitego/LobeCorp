package net.unitego.lobecorp;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.unitego.lobecorp.event.HUDRenderEvent;
import org.slf4j.Logger;

@Mod(LobeCorp.MODID)
public class LobeCorp {
    public static final String MODID = "lobecorp";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LobeCorp() {
        LOGGER.info("Face the Fear, Build the Future.");
    }

    public static ResourceLocation locate(String path) {
        return new ResourceLocation(MODID, path);
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void RegisterGuiLayers(RegisterGuiLayersEvent event) {
            HUDRenderEvent.render(event);
        }
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class ClientGameEvents {
        @SubscribeEvent
        public static void RenderGuiLayer(RenderGuiLayerEvent.Pre event) {
            HUDRenderEvent.cancel(event);
        }
    }
}
