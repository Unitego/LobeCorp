package net.unitego.lobecorp.common.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.registry.AttachmentTypesRegistry;

import java.util.UUID;

/**
 * 不知道放哪的东西就都丢这里吧！
 */
public class MiscUtils {
    public static final UUID INTERACTION_RANGE_MODIFIER_ID = UUID.fromString("F17D5E0B-44D0-4E2B-B81B-9C471B547B29");
    public static final UUID LOBECORP_SUIT_MODIFIER_ID = UUID.fromString("EE3CBD25-29F0-4496-8D0F-CE1094AE095C");

    public static final String NETWORK_VERSION = "1.0.0";//网络版本
    public static final String MOTTO = info("motto");
    public static final String CONFIRM = info("confirm");
    public static final String INVALID_VALUE = info("invalid_value");
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

    private static String info(String string) {
        return LobeCorp.MOD_ID + ".info." + string;
    }

    public static ItemStack getLobeCorpStack(Player player, LobeCorpEquipmentSlot slot) {
        return player.getData(AttachmentTypesRegistry.LOBECORP_STACK).getStackInSlot(slot.ordinal() - 1);
    }

    public static void setLobeCorpStack(Player player, LobeCorpEquipmentSlot slot, ItemStack stack) {
        player.getData(AttachmentTypesRegistry.LOBECORP_STACK).setStackInSlot(slot.ordinal() - 1, stack.copy());
    }

    public static void playSound(ServerPlayer serverPlayer, SoundEvent soundEvent) {
        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), soundEvent, SoundSource.PLAYERS, 1, 1);
    }

    /**
     * 安全更新属性修饰符（仅当数值变化时重新应用）
     *
     * @param instance   属性实例
     * @param modifierId 修饰符UUID
     * @param name       修饰符名称
     * @param newAmount  新数值
     * @param operation  运算类型
     */
    public static void updateAttributeModifier(AttributeInstance instance, UUID modifierId, String name,
                                               double newAmount, AttributeModifier.Operation operation) {
        AttributeModifier existingModifier = instance.getModifier(modifierId);
        // 如果修饰符已存在且数值相同，则跳过更新
        if (existingModifier != null &&
                existingModifier.amount() == newAmount &&
                existingModifier.operation() == operation) {
            return;
        }
        // 移除旧修饰符（如果存在）
        if (existingModifier != null) {
            instance.removeModifier(modifierId);
        }
        // 添加新修饰符
        instance.addPermanentModifier(new AttributeModifier(modifierId, name, newAmount, operation));
    }
}
