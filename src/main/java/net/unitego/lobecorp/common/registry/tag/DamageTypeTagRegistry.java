package net.unitego.lobecorp.common.registry.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.unitego.lobecorp.LobeCorp;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/01 12:23:10}
 */
public enum DamageTypeTagRegistry implements Supplier<TagKey<DamageType>> {
    /**
     * 红、白、黑、蓝四色伤害都有碎盾机制，并且都无视原版护甲
     * 受到伤害大于伤害吸收值或认知同化值的时候，会直接归于0
     * 然后玩家依旧会吃满这次伤害，不会被抵消掉一部分
     */
    RED_DAMAGE_TYPE,//红伤只扣除玩家生命值，然后会受到四色抗性与等级压制的影响
    WHITE_DAMAGE_TYPE,//白伤只扣除玩家精神值，玩家精神值归零会进入恐慌状态
    BLACK_DAMAGE_TYPE,//黑伤生命和精神都扣
    PALE_DAMAGE_TYPE,//蓝伤对于玩家走百分比伤害扣除生命，
    SPIRIT_DAMAGE_TYPE,//有insane和mystic，insane你可以看作原版的wither，mystic相当于原版的magic
    CENSORED_DAMAGE_TYPE;//掉虚空、边界外、指令杀、饥饿、干渴
    private final TagKey<DamageType> tag;

    DamageTypeTagRegistry() {
        tag = create(name().toLowerCase(Locale.ROOT));
    }

    private static TagKey<DamageType> create(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, LobeCorp.rl(name));
    }

    @Override
    public TagKey<DamageType> get() {
        return tag;
    }
}
