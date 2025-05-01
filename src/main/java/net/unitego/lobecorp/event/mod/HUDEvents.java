package net.unitego.lobecorp.event.mod;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.client.hud.BaseElement;
import net.unitego.lobecorp.common.registry.HUDRegistry;

import static net.unitego.lobecorp.LobeCorp.MOD_ID;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public final class HUDEvents {
    public static final ResourceLocation LC_HUD = LobeCorp.rl("lc_hud");

    //注册自定义HUD图层
    @SubscribeEvent
    public static void registerCustomHUDLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(LC_HUD, ((guiGraphics, partialTick) -> {
            for (BaseElement element : HUDRegistry.getElements()) {
                //if (element.check()) {
                guiGraphics.pose().pushPose();
                element.draw(guiGraphics, partialTick, guiGraphics.guiWidth(), guiGraphics.guiHeight());
                guiGraphics.pose().popPose();
                //}
            }
        }));
    }

    //取消原版HUD图层
    public static void suppressVanillaHUDLayers(RenderGuiLayerEvent.Pre event) {
        ResourceLocation name = event.getName();
        if (VanillaGuiLayers.AIR_LEVEL.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.ARMOR_LEVEL.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.EXPERIENCE_BAR.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.EXPERIENCE_LEVEL.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.FOOD_LEVEL.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.PLAYER_HEALTH.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.HOTBAR.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.VEHICLE_HEALTH.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.JUMP_METER.equals(name)) {
            event.setCanceled(true);
        }
    }
}
