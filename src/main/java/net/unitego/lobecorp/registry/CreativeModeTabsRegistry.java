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
                        output.accept(ItemsRegistry.LOBECORP_LOGO);
                        output.accept(ItemsRegistry.BLUE_LEAF);
                        output.accept(ItemsRegistry.CODE_SUIT);
                        output.accept(ItemsRegistry.CODE_RIOT_STICK);
                        output.accept(ItemsRegistry.STANDARD_SUIT);
                        output.accept(ItemsRegistry.RED_RIOT_STICK);
                        output.accept(ItemsRegistry.WHITE_RIOT_STICK);
                        output.accept(ItemsRegistry.BLACK_RIOT_STICK);
                        output.accept(ItemsRegistry.PALE_RIOT_STICK);
                        //部门袖标
                        output.accept(ItemsRegistry.CONTROL_TEAM_BADGE);
                        output.accept(ItemsRegistry.INFORMATION_TEAM_BADGE);
                        output.accept(ItemsRegistry.SECURITY_TEAM_BADGE);
                        output.accept(ItemsRegistry.TRAINING_TEAM_BADGE);
                        output.accept(ItemsRegistry.CENTRAL_COMMAND_TEAM_BADGE);
                        output.accept(ItemsRegistry.WELFARE_TEAM_BADGE);
                        output.accept(ItemsRegistry.DISCIPLINARY_TEAM_BADGE);
                        output.accept(ItemsRegistry.RECORD_TEAM_BADGE);
                        output.accept(ItemsRegistry.EXTRACTION_TEAM_BADGE);
                        output.accept(ItemsRegistry.ARCHITECTURE_TEAM_BADGE);
                        //EGO武器
                        output.accept(ItemsRegistry.STANDARD_TRAINING_EGO_WEAPON);
                        output.accept(ItemsRegistry.PENITENCE_WEAPON);
                        output.accept(ItemsRegistry.WING_BEAT_WEAPON);
                        output.accept(ItemsRegistry.RED_EYES_WEAPON);
                        output.accept(ItemsRegistry.CHRISTMAS_WEAPON);
                        output.accept(ItemsRegistry.DIFFRACTION_WEAPON);
                        output.accept(ItemsRegistry.SANGUINE_DESIRE_WEAPON);
                        output.accept(ItemsRegistry.WRIST_CUTTER_WEAPON);
                        output.accept(ItemsRegistry.HORN_WEAPON);
                        output.accept(ItemsRegistry.FRAGMENTS_FROM_SOMEWHERE_WEAPON);
                        output.accept(ItemsRegistry.HARVEST_WEAPON);
                        output.accept(ItemsRegistry.FROST_SPLINTER_WEAPON);
                        output.accept(ItemsRegistry.GREEN_STEM_WEAPON);
                        output.accept(ItemsRegistry.HEAVEN_WEAPON);
                        output.accept(ItemsRegistry.SPORE_WEAPON);
                        output.accept(ItemsRegistry.REGRET_WEAPON);
                        output.accept(ItemsRegistry.LANTERN_WEAPON);
                        output.accept(ItemsRegistry.LOGGING_WEAPON);
                        output.accept(ItemsRegistry.LAMP_WEAPON);

                        //EGO护甲
                        output.accept(ItemsRegistry.STANDARD_TRAINING_EGO_SUIT);
                        output.accept(ItemsRegistry.PENITENCE_SUIT);
                        output.accept(ItemsRegistry.WING_BEAT_SUIT);
                        output.accept(ItemsRegistry.RED_EYES_SUIT);
                        output.accept(ItemsRegistry.CHRISTMAS_SUIT);
                        output.accept(ItemsRegistry.SANGUINE_DESIRE_SUIT);
                        output.accept(ItemsRegistry.WRIST_CUTTER_SUIT);
                        output.accept(ItemsRegistry.HORN_SUIT);
                        output.accept(ItemsRegistry.FRAGMENTS_FROM_SOMEWHERE_SUIT);
                        output.accept(ItemsRegistry.HARVEST_SUIT);
                        output.accept(ItemsRegistry.FROST_SPLINTER_SUIT);
                        output.accept(ItemsRegistry.GREEN_STEM_SUIT);
                        output.accept(ItemsRegistry.HEAVEN_SUIT);
                        output.accept(ItemsRegistry.SPORE_SUIT);
                        output.accept(ItemsRegistry.REGRET_SUIT);
                        output.accept(ItemsRegistry.LANTERN_SUIT);
                        output.accept(ItemsRegistry.LOGGING_SUIT);
                        output.accept(ItemsRegistry.LAMP_SUIT);

                        //EGO饰品
                        output.accept(ItemsRegistry.STANDARD_TRAINING_EGO_GIFT);
                        output.accept(ItemsRegistry.PENITENCE_GIFT);
                        output.accept(ItemsRegistry.WING_BEAT_GIFT);
                        output.accept(ItemsRegistry.RED_EYES_GIFT);
                        output.accept(ItemsRegistry.CHRISTMAS_GIFT);
                        output.accept(ItemsRegistry.DIFFRACTION_GIFT);
                        output.accept(ItemsRegistry.SANGUINE_DESIRE_GIFT);
                        output.accept(ItemsRegistry.WRIST_CUTTER_GIFT);
                        output.accept(ItemsRegistry.HORN_GIFT);
                        output.accept(ItemsRegistry.FRAGMENTS_FROM_SOMEWHERE_GIFT);
                        output.accept(ItemsRegistry.HARVEST_GIFT);
                        output.accept(ItemsRegistry.FROST_SPLINTER_GIFT);
                        output.accept(ItemsRegistry.GREEN_STEM_GIFT);
                        output.accept(ItemsRegistry.HEAVEN_GIFT);
                        output.accept(ItemsRegistry.SPORE_GIFT);
                        output.accept(ItemsRegistry.REGRET_GIFT);
                        output.accept(ItemsRegistry.LANTERN_GIFT);
                        output.accept(ItemsRegistry.LOGGING_GIFT);
                        output.accept(ItemsRegistry.LAMP_GIFT);

                        output.accept(ItemsRegistry.BLESS_GIFT);

                        //自定义
                        //血肉契约——苏玖
                        output.accept(ItemsRegistry.FLESH_BOUND_WEAPON);
                        output.accept(ItemsRegistry.FLESH_BOUND_SUIT);
                        output.accept(ItemsRegistry.FLESH_BOUND_GIFT);

                    }).build());

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
