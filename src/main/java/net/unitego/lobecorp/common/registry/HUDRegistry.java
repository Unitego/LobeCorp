package net.unitego.lobecorp.common.registry;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.client.hud.BaseElement;
import net.unitego.lobecorp.client.hud.element.*;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 管理所有HUD元素的注册
 */
@OnlyIn(Dist.CLIENT)
public final class HUDRegistry {
    private static final Set<BaseElement> ELEMENTS = new LinkedHashSet<>();

    private static void register(BaseElement element) {
        ELEMENTS.add(element);
    }

    public static void init() {
        register(new AirElement());
        register(new ArmorElement());
        register(new AvatarElement());
        register(new ExperienceElement());
        register(new FoodElement());
        register(new HealthElement());
        register(new HotBarElement());
        register(new MountHealthElement());
        register(new MountJumpElement());
        register(new SanityElement());
        register(new WaterElement());
    }

    public static Set<BaseElement> getElements() {
        return Collections.unmodifiableSet(ELEMENTS);
    }
}
