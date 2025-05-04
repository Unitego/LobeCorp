package net.unitego.lobecorp.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.unitego.lobecorp.LobeCorp;

public class ModDamageTypes {
    public static final String DEATH_ATTACK_DRY = "death.attack.dry";
    public static final String DEATH_ATTACK_DRY_PLAYER = "death.attack.dry.player";
    public static final String DEATH_ATTACK_INSANE = "death.attack.insane";
    public static final String DEATH_ATTACK_INSANE_PLAYER = "death.attack.insane.player";
    public static final String DEATH_ATTACK_INDIRECT_MYSTIC = "death.attack.indirectMystic";
    public static final String DEATH_ATTACK_INDIRECT_MYSTIC_ITEM = "death.attack.indirectMystic.item";
    public static final String DEATH_ATTACK_MYSTIC = "death.attack.mystic";
    public static final String DEATH_ATTACK_MYSTIC_PLAYER = "death.attack.mystic.player";
    public static final String DEATH_ATTACK_RED = "death.attack.red";
    public static final String DEATH_ATTACK_RED_ITEM = "death.attack.red.item";
    public static final String DEATH_ATTACK_WHITE = "death.attack.white";
    public static final String DEATH_ATTACK_WHITE_ITEM = "death.attack.white.item";
    public static final String DEATH_ATTACK_BLACK = "death.attack.black";
    public static final String DEATH_ATTACK_BLACK_ITEM = "death.attack.black.item";
    public static final String DEATH_ATTACK_PALE = "death.attack.pale";
    public static final String DEATH_ATTACK_PALE_ITEM = "death.attack.pale.item";

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
