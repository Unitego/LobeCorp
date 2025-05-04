package net.unitego.lobecorp.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;

public class ModCreativeModeTabs {
    public static final String LOBE_CORP_NAME = "itemGroup.lobecorp.lobe_corp";
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LobeCorp.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> LOBE_CORP = CREATIVE_MODE_TAB.register("lobe_corp",
            () -> CreativeModeTab.builder().title(Component.translatable(LOBE_CORP_NAME)).icon(() -> ModItems.LOBE_CORP_LOGO.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.LOBE_CORP_LOGO.get());
                        output.accept(ModItems.BLUE_LEAF);
                    }).build());
}
