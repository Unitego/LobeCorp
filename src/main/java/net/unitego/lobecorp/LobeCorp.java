package net.unitego.lobecorp;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.unitego.lobecorp.registry.*;
import org.slf4j.Logger;

@Mod(LobeCorp.MOD_ID)
public class LobeCorp {
    public static final String MOD_ID = "lobecorp";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LobeCorp(IEventBus bus) {
        LOGGER.info("Unitego.");

        AttachmentTypesRegistry.init(bus);
        AttributesRegistry.init(bus);
        CreativeModeTabsRegistry.init(bus);
        DataComponentTypesRegistry.init(bus);
        ItemsRegistry.init(bus);
        MenusRegistry.init(bus);
        MobEffectsRegistry.init(bus);
        SoundEventsRegistry.init(bus);
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
