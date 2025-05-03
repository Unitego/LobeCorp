package net.unitego.lobecorp.event.mod;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.loader.HydratingFoodLoader;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class DataEvents {
    //数据驱动
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        HydratingFoodLoader.load(event.getServer()).thenRun(() -> {
        });
    }
}
