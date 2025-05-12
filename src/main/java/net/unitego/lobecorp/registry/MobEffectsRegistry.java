package net.unitego.lobecorp.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.effect.*;

import java.util.UUID;

public class MobEffectsRegistry {
    public static final UUID MOB_EFFECT_ASSIMILATION_MODIFIER_ID = UUID.fromString("DA2F9E13-1D13-4D70-9B54-04041C06B857");
    public static final UUID MOB_EFFECT_SANITY_BOOST_MODIFIER_ID = UUID.fromString("C5FAE497-6E90-44EB-97A3-9D50C2E8E26A");

    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister
            .create(Registries.MOB_EFFECT, LobeCorp.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> ABSENT = REGISTER.register("absent",
            () -> new AbsentMobEffect(MobEffectCategory.HARMFUL, 2588492));//恍惚
    public static final DeferredHolder<MobEffect, MobEffect> ASSIMILATION = REGISTER.register("assimilation",
            () -> new AssimilationMobEffect(MobEffectCategory.BENEFICIAL, 16744448)
                    .addAttributeModifier(AttributesRegistry.MAX_ASSIMILATION, String.valueOf(MOB_EFFECT_ASSIMILATION_MODIFIER_ID),
                            4.0, AttributeModifier.Operation.ADD_VALUE));//认知同化
    public static final DeferredHolder<MobEffect, MobEffect> HYDRATION = REGISTER.register("hydration",
            () -> new HydrationMobEffect(MobEffectCategory.BENEFICIAL, 6476510));//滋润
    public static final DeferredHolder<MobEffect, MobEffect> INSANE = REGISTER.register("insane",
            () -> new InsaneMobEffect(MobEffectCategory.HARMFUL, 2557504));//狂乱
    public static final DeferredHolder<MobEffect, MobEffect> INSTANT_INJURY = REGISTER.register("instant_injury",
            () -> new CureOrHarmMobEffect(MobEffectCategory.HARMFUL, 211141, true));//瞬间损伤
    public static final DeferredHolder<MobEffect, MobEffect> INSTANT_SANITY = REGISTER.register("instant_sanity",
            () -> new CureOrHarmMobEffect(MobEffectCategory.BENEFICIAL, 10865167, false));//瞬间镇定
    public static final DeferredHolder<MobEffect, MobEffect> RESTORATION = REGISTER.register("restoration",
            () -> new RestorationMobEffect(MobEffectCategory.BENEFICIAL, 5885736));//精神恢复
    public static final DeferredHolder<MobEffect, MobEffect> SANITY_BOOST = REGISTER.register("sanity_boost",
            () -> new SanityBoostMobEffect(MobEffectCategory.BENEFICIAL, 16298947)
                    .addAttributeModifier(AttributesRegistry.MAX_SANITY, String.valueOf(MOB_EFFECT_SANITY_BOOST_MODIFIER_ID),
                            4.0, AttributeModifier.Operation.ADD_VALUE));//精神提升
    public static final DeferredHolder<MobEffect, MobEffect> THIRST = REGISTER.register("thirst",
            () -> new ThirstMobEffect(MobEffectCategory.HARMFUL, 13819490));//干渴
    public static final DeferredHolder<MobEffect, MobEffect> VULNERABLE_RED = REGISTER.register("vulnerable_red",
            () -> new AbstractVulnerableEffect(MobEffectCategory.HARMFUL, 11141120, DamageTypesRegistry.RED));//物理易伤
    public static final DeferredHolder<MobEffect, MobEffect> VULNERABLE_WHITE = REGISTER.register("vulnerable_white",
            () -> new AbstractVulnerableEffect(MobEffectCategory.HARMFUL, 16777215, DamageTypesRegistry.WHITE));//精神易伤
    public static final DeferredHolder<MobEffect, MobEffect> VULNERABLE_BLACK = REGISTER.register("vulnerable_black",
            () -> new AbstractVulnerableEffect(MobEffectCategory.HARMFUL, 11141290, DamageTypesRegistry.BLACK));//侵蚀易伤
    public static final DeferredHolder<MobEffect, MobEffect> VULNERABLE_PALE = REGISTER.register("vulnerable_pale",
            () -> new AbstractVulnerableEffect(MobEffectCategory.HARMFUL, 5636095, DamageTypesRegistry.PALE));//灵魂易伤

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
