package net.unitego.lobecorp.event.mod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.client.hud.BaseElement;
import net.unitego.lobecorp.client.hud.HUDResource;
import net.unitego.lobecorp.client.screen.EquipmentScreen;
import net.unitego.lobecorp.common.registry.HUDRegistry;
import net.unitego.lobecorp.common.registry.ModKeyMappings;
import net.unitego.lobecorp.common.registry.ModMenus;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    //注册屏幕
    @SubscribeEvent
    public static void onRegisterMenuScreensEvent(RegisterMenuScreensEvent event) {
        event.register(ModMenus.EQUIPMENT_MENU.get(), EquipmentScreen::new);
    }

    //注册按键绑定
    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ModKeyMappings.TOGGLE_EQUIPMENT.get());
    }

    //渲染自定义HUD图层
    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        HUDRegistry.init();
        event.registerAboveAll(HUDResource.LOBE_CORP_HUD, ((guiGraphics, partialTick) -> {
            for (BaseElement element : HUDRegistry.getElements()) {
                if (element.check()) {
                    guiGraphics.pose().pushPose();
                    element.draw(guiGraphics, partialTick, guiGraphics.guiWidth(), guiGraphics.guiHeight());
                    guiGraphics.pose().popPose();
                }
            }
        }));
    }
}
