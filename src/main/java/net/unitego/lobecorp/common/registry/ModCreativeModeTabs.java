package net.unitego.lobecorp.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;

public class ModCreativeModeTabs {
    public static final String LOBECORP_TAB_NAME = "itemGroup.lobecorp.lobecorp_tab";
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LobeCorp.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> LOBECORP_TAB = CREATIVE_MODE_TABS.register("lobecorp_tab",
            () -> CreativeModeTab.builder().title(Component.translatable(LOBECORP_TAB_NAME)).icon(() ->
                            ModItems.LOBECORP_LOGO.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.LOBECORP_LOGO.get());
                        output.accept(ModItems.BLUE_LEAF);
                        output.accept(ModItems.CODE_RIOT_STICK);
                        output.accept(ModItems.RED_RIOT_STICK);
                        output.accept(ModItems.WHITE_RIOT_STICK);
                        output.accept(ModItems.BLACK_RIOT_STICK);
                        output.accept(ModItems.PALE_RIOT_STICK);
                    }).build());
}
