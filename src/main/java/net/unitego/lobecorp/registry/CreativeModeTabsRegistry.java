package net.unitego.lobecorp.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;

public class CreativeModeTabsRegistry {
    public static final String LOBECORP_TAB_NAME = "itemGroup.lobecorp.lobecorp_tab";

    public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, LobeCorp.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> LOBECORP_TAB = REGISTER.register("lobecorp_tab",
            () -> CreativeModeTab.builder().title(Component.translatable(LOBECORP_TAB_NAME)).icon(() ->
                            ItemsRegistry.LOBECORP_LOGO.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ItemsRegistry.LOBECORP_LOGO.get());
                        output.accept(ItemsRegistry.BLUE_LEAF);
                        output.accept(ItemsRegistry.CODE_SUIT);
                        output.accept(ItemsRegistry.CODE_RIOT_STICK);
                        output.accept(ItemsRegistry.STANDARD_SUIT);
                        output.accept(ItemsRegistry.RED_RIOT_STICK);
                        output.accept(ItemsRegistry.WHITE_RIOT_STICK);
                        output.accept(ItemsRegistry.BLACK_RIOT_STICK);
                        output.accept(ItemsRegistry.PALE_RIOT_STICK);
                    }).build());

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
