package net.unitego.lobecorp.client.init;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.client.gui.hud.BaseElement;
import net.unitego.lobecorp.client.gui.hud.element.*;

import java.util.LinkedHashSet;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class HudInit {
    private static final ResourceLocation LOBECORP_HUD = LobeCorp.rl("lobecorp_hud");
    private static final Set<BaseElement> ELEMENTS = new LinkedHashSet<>();

    public static void init(RegisterGuiLayersEvent event) {
        ELEMENTS.add(new AirElement());
        ELEMENTS.add(new ArmorElement());
        ELEMENTS.add(new AvatarElement());
        ELEMENTS.add(new EntityInspectElement());
        ELEMENTS.add(new ExperienceElement());
        ELEMENTS.add(new FoodElement());
        ELEMENTS.add(new HealthElement());
        ELEMENTS.add(new HotBarElement());
        ELEMENTS.add(new MountHealthElement());
        ELEMENTS.add(new MountJumpElement());
        ELEMENTS.add(new SanityElement());
        ELEMENTS.add(new WaterElement());
        event.registerAboveAll(LOBECORP_HUD, ((guiGraphics, partialTick) -> {
            for (BaseElement element : ELEMENTS) {
                if (element.check()) {
                    guiGraphics.pose().pushPose();
                    element.draw(guiGraphics, partialTick, guiGraphics.guiWidth(), guiGraphics.guiHeight());
                    guiGraphics.pose().popPose();
                }
            }
        }));
    }
}
