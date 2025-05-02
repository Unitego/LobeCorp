package net.unitego.lobecorp.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.effect.*;

public class ModMobEffects {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, LobeCorp.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> ABSENT = MOB_EFFECTS.register("absent",
            () -> new AbsentMobEffect(MobEffectCategory.HARMFUL, 2588492));//恍惚
    public static final DeferredHolder<MobEffect, MobEffect> ASSIMILATION = MOB_EFFECTS.register("assimilation",
            () -> new AssimilationMobEffect(MobEffectCategory.BENEFICIAL, 16744448)
                    .addAttributeModifier(ModAttributes.MAX_ASSIMILATION, "DA2F9E13-1D13-4D70-9B54-04041C06B857",
                            4.0, AttributeModifier.Operation.ADD_VALUE));//认知同化
    public static final DeferredHolder<MobEffect, MobEffect> HYDRATION = MOB_EFFECTS.register("hydration",
            () -> new HydrationMobEffect(MobEffectCategory.BENEFICIAL, 6476510));//滋润
    public static final DeferredHolder<MobEffect, MobEffect> INSANE = MOB_EFFECTS.register("insane",
            () -> new InsaneMobEffect(MobEffectCategory.HARMFUL, 2557504));//狂乱
    public static final DeferredHolder<MobEffect, MobEffect> INSTANT_INJURY = MOB_EFFECTS.register("instant_injury",
            () -> new CureOrHarmMobEffect(MobEffectCategory.HARMFUL, 211141, true));//瞬间损伤
    public static final DeferredHolder<MobEffect, MobEffect> INSTANT_SANITY = MOB_EFFECTS.register("instant_sanity",
            () -> new CureOrHarmMobEffect(MobEffectCategory.BENEFICIAL, 10865167, false));//瞬间镇定
    public static final DeferredHolder<MobEffect, MobEffect> RESTORATION = MOB_EFFECTS.register("restoration",
            () -> new RestorationMobEffect(MobEffectCategory.BENEFICIAL, 5885736));//精神恢复
    public static final DeferredHolder<MobEffect, MobEffect> SANITY_BOOST = MOB_EFFECTS.register("sanity_boost",
            () -> new SanityBoostMobEffect(MobEffectCategory.BENEFICIAL, 16298947)
                    .addAttributeModifier(ModAttributes.MAX_SANITY, "C5FAE497-6E90-44EB-97A3-9D50C2E8E26A",
                            4.0, AttributeModifier.Operation.ADD_VALUE));//精神提升
    public static final DeferredHolder<MobEffect, MobEffect> THIRST = MOB_EFFECTS.register("thirst",
            () -> new ThirstMobEffect(MobEffectCategory.HARMFUL, 13819490));//干渴

    public static void init(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }
}
