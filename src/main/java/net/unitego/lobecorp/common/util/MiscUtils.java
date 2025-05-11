package net.unitego.lobecorp.common.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.registry.AttachmentTypesRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

/**
 * 不知道放哪的东西就都丢这里吧！
 */
public class MiscUtils {
    public static final UUID ATTRIBUTE_ATTACK_VELOCITY_MODIFIER_ID = UUID.fromString("9F0BF5D-8C67-40D0-9A7C-C9E29011F02B");//攻击速率修饰ID
    public static final UUID ATTRIBUTE_MOVE_VELOCITY_MODIFIER_ID = UUID.fromString("A12BFDCB-F7D2-458A-B2B5-9C4F7AFAC2E8");//移动速率修饰ID
    public static final UUID INTERACTION_RANGE_MODIFIER_ID = UUID.fromString("F17D5E0B-44D0-4E2B-B81B-9C471B547B29");//交互范围修饰ID
    public static final UUID LOBECORP_WEAPON_MODIFIER_ID = UUID.fromString("91D1DBFF-954B-4399-A2A3-B5CC7D838E98");//EGO武器修饰ID
    public static final UUID LOBECORP_SUIT_MODIFIER_ID = UUID.fromString("EE3CBD25-29F0-4496-8D0F-CE1094AE095C");//EGO护甲修饰ID
    public static final UUID LOBECORP_BADGE_MODIFIER_ID = UUID.fromString("A4D67FF2-34CB-4704-A01D-62DC1BD306AF");//EGO袖标修饰ID
    public static final UUID LOBECORP_TOOL_MODIFIER_ID = UUID.fromString("48C60708-5D00-4BA1-85D4-62137DB8ADCE");//EGO工具修饰ID
    public static final UUID LOW_RESIST_SLOW_MODIFIER_ID = UUID.fromString("8F1ACDBD-EB65-461C-9E69-35A675023A0B");//弱抗减速修饰ID

    public static final String NETWORK_VERSION = "1.0.0";//网络版本
    public static final String MOTTO = info("motto");
    public static final String CONFIRM = info("confirm");
    public static final String INVALID_VALUE = info("invalid_value");
    public static final String ENTERED_COMBAT = info("entered_combat");
    public static final String LEFT_COMBAT = info("left_combat");
    public static final String STAFF_RANK = info("staff_rank");//职员等级
    public static final String STAFF_FORTITUDE = info("staff_fortitude");//勇气
    public static final String STAFF_PRUDENCE = info("staff_prudence");//谨慎
    public static final String STAFF_TEMPERANCE = info("staff_temperance");//自律
    public static final String STAFF_JUSTICE = info("staff_justice");//正义
    public static final String HOLD_LEFT_SHIFT_SHOW_SKILL = info("press_left_shift_show_skill");
    //E.G.O
    public static final String EGO_RANK = info("ego_rank");
    public static final String EQUIP_REQUIRE = info("equip_require");
    public static final String WEAPON_TEMPLATE = info("weapon_template");
    public static final String DAMAGE_TYPE = info("damage_type");
    public static final String DAMAGE_RESIST = info("damage_resist");
    //四色
    public static final String RED = info("red");
    public static final String WHITE = info("white");
    public static final String BLACK = info("black");
    public static final String PALE = info("pale");

    public static String info(String string) {
        return LobeCorp.MOD_ID + ".info." + string;
    }

    public static ItemStack getLobeCorpStack(Player player, LobeCorpEquipmentSlot slot) {
        return player.getData(AttachmentTypesRegistry.LOBECORP_STACK).getStackInSlot(slot.ordinal() - 1);
    }

    public static void setLobeCorpStack(Player player, LobeCorpEquipmentSlot slot, ItemStack stack) {
        player.getData(AttachmentTypesRegistry.LOBECORP_STACK).setStackInSlot(slot.ordinal() - 1, stack.copy());
    }

    public static void playSound(Player player, SoundEvent soundEvent) {
        player.level().playSound(null, player.blockPosition(), soundEvent, SoundSource.PLAYERS, 1, 1);
    }

    public static void sendCenterClientMessage(String message) {
        Objects.requireNonNull(Minecraft.getInstance().player).displayClientMessage(Component.translatable(message), true);
    }

    public static boolean roll(Player player, float chance) {
        return player.level().random.nextFloat() <= chance;
    }

    //无击退伤害源
    public static DamageSource noKnockBackDamageSource(ResourceKey<DamageType> key, Entity entity) {
        Holder<DamageType> damageType = entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(key);
        return new DamageSource(damageType, entity) {
            @Override
            public boolean is(@NotNull TagKey<DamageType> tag) {
                return tag == DamageTypeTags.NO_KNOCKBACK || super.is(tag);
            }
        };
    }

    //隐藏效果
    public static void hiddenEffect(Player player, Holder<MobEffect> effect, int duration, int amplifier) {
        player.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false, false));
    }
}
