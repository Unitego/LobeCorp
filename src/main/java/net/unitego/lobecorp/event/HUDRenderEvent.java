package net.unitego.lobecorp.event;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.render.hud.BaseElement;
import net.unitego.lobecorp.render.hud.element.*;

import java.util.ArrayList;
import java.util.List;

//HUD渲染事件
@OnlyIn(Dist.CLIENT)
public final class HUDRenderEvent {
    public static final ResourceLocation LC_HUD = LobeCorp.locate("lc_hud");
    private static final List<BaseElement> ELEMENTS = new ArrayList<>();

    static {
        registerElement(new AirElement());
        registerElement(new ArmorElement());
        registerElement(new AvatarElement());
        registerElement(new ExperienceElement());
        registerElement(new FoodElement());
        registerElement(new HealthElement());
        registerElement(new HotBarElement());
        registerElement(new MountHealthElement());
        registerElement(new MountJumpElement());
        registerElement(new SanityElement());
        registerElement(new WaterElement());
    }

    private static void registerElement(BaseElement element) {
        ELEMENTS.add(element);
    }

    //通过对应元素的检查方法判断是否应该渲染
    public static void render(RegisterGuiLayersEvent event) {
        event.registerAboveAll(LC_HUD, (guiGraphics, partialTick) -> {
            for (BaseElement element : ELEMENTS) {
                if (element.check()) {
                    guiGraphics.pose().pushPose();
                    element.draw(guiGraphics, partialTick, guiGraphics.guiWidth(), guiGraphics.guiHeight());
                    guiGraphics.pose().popPose();
                }
            }
        });
    }

    //取消原版元素的渲染
    public static void cancel(RenderGuiLayerEvent.Pre event) {
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
