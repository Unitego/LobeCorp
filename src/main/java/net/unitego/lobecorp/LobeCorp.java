package net.unitego.lobecorp;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.unitego.lobecorp.common.registry.*;
import org.slf4j.Logger;

@Mod(LobeCorp.MOD_ID)
public class LobeCorp {
    public static final String MOD_ID = "lobecorp";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LobeCorp(IEventBus bus, ModContainer container) {
        LOGGER.info("Face the Fear, Build the Future.");

        ModAttributes.ATTRIBUTES.register(bus);
        ModCreativeModeTabs.CREATIVE_MODE_TAB.register(bus);
        ModItems.ITEMS.register(bus);
        ModMobEffects.MOB_EFFECTS.register(bus);
        ModSoundEvents.SOUND_EVENTS.register(bus);
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
