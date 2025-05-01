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

import net.unitego.lobecorp.common.registry.*;
import net.unitego.lobecorp.event.mod.HUDEvents;
import org.slf4j.Logger;

@Mod(LobeCorp.MOD_ID)
public class LobeCorp {
    public static final String MOD_ID = "lobecorp";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LobeCorp(IEventBus bus, ModContainer container) {
        LOGGER.info("Face the Fear, Build the Future.");

        HUDRegistry.init();
        AttributeRegistry.init(bus);
        EffectRegistry.init(bus);
        AttachmentTypeRegistry.init(bus);

//        bus.addListener(NetworkRegistry::init);
//        NeoForge.EVENT_BUS.register(S2CSyncIconSender.class);
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
            HUDEvents.registerCustomHUDLayers(event);
        }
    }
}
