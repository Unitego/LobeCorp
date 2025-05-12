package net.unitego.lobecorp.client.init;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeyInit {
    public static final String KEY_CATEGORIES_LOBECORP = "key.categories.lobecorp";

    public static final Lazy<KeyMapping> KEY_TOGGLE_EQUIPMENT = Lazy.of(() -> new KeyMapping(
            "key.lobecorp.toggle_equipment", GLFW.GLFW_KEY_Z, KEY_CATEGORIES_LOBECORP
    ));

    public static final Lazy<KeyMapping> KEY_SWITCH_WEAPON = Lazy.of(() -> new KeyMapping(
            "key.lobecorp.switch_weapon", GLFW.GLFW_KEY_X, KEY_CATEGORIES_LOBECORP
    ));

    public static void init(RegisterKeyMappingsEvent event) {
        event.register(KEY_TOGGLE_EQUIPMENT.get());
        event.register(KEY_SWITCH_WEAPON.get());
    }
}
