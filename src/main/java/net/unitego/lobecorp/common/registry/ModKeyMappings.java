package net.unitego.lobecorp.common.registry;


import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class ModKeyMappings {
    public static final String CATEGORIES_LOBE_CORP = "key.categories.lobe_corp";

    public static final Lazy<KeyMapping> TOGGLE_EQUIPMENT = Lazy.of(() -> new KeyMapping(
            "key.lobecorp.toggle_equipment", GLFW.GLFW_KEY_Z, CATEGORIES_LOBE_CORP
    ));
}
