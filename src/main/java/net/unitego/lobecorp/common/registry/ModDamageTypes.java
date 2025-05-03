package net.unitego.lobecorp.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.unitego.lobecorp.LobeCorp;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> BLACK = create("black");
    public static final ResourceKey<DamageType> DRY = create("dry");
    public static final ResourceKey<DamageType> INDIRECT_MYSTIC = create("indirect_mystic");
    public static final ResourceKey<DamageType> INSANE = create("insane");
    public static final ResourceKey<DamageType> MYSTIC = create("mystic");
    public static final ResourceKey<DamageType> PALE = create("pale");
    public static final ResourceKey<DamageType> RED = create("red");
    public static final ResourceKey<DamageType> WHITE = create("white");

    private static ResourceKey<DamageType> create(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, LobeCorp.rl(name));
    }

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(BLACK, new DamageType(BLACK.location().getPath(), 0.1f));
        context.register(DRY, new DamageType(DRY.location().getPath(), 0.0f));
        context.register(INDIRECT_MYSTIC, new DamageType(INDIRECT_MYSTIC.location().getPath(), 0.0f));
        context.register(INSANE, new DamageType(INSANE.location().getPath(), 0.0f));
        context.register(MYSTIC, new DamageType(MYSTIC.location().getPath(), 0.0f));
        context.register(PALE, new DamageType(PALE.location().getPath(), 0.1f));
        context.register(RED, new DamageType(RED.location().getPath(), 0.1f));
        context.register(WHITE, new DamageType(WHITE.location().getPath(), 0.1f));
    }
}
