package net.unitego.lobecorp.event.mod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.client.hud.BaseElement;
import net.unitego.lobecorp.client.hud.HUDResource;
import net.unitego.lobecorp.common.registry.HUDRegistry;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HUDModEvents {
    //渲染自定义HUD图层
    @SubscribeEvent
    public static void renderCustomHUDLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(HUDResource.LC_HUD, ((guiGraphics, partialTick) -> {
            for (BaseElement element : HUDRegistry.getElements()) {
                //if (element.check()) {
                guiGraphics.pose().pushPose();
                element.draw(guiGraphics, partialTick, guiGraphics.guiWidth(), guiGraphics.guiHeight());
                guiGraphics.pose().popPose();
                //}
            }
        }));
    }
}
