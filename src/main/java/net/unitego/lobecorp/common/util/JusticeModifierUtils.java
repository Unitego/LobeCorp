package net.unitego.lobecorp.common.util;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.manager.StaffManager;

import java.util.UUID;

//正义品质修饰工具
public class JusticeModifierUtils {
    public static void apply(Player player) {
        //脑叶公司攻击速率移动速率修饰原版攻击速度移动速度
        StaffManager staffManager = ((ManagerAccess) player).lobeCorp$getStaffManager();
        double attackVelocity = staffManager.getAttackVelocity();
        double moveVelocity = staffManager.getMoveVelocity();
        // 修改原版攻击速度（仅当数值变化时更新）
        AttributeInstance attackSpeed = player.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeed != null) {
            updateAttributeModifier(attackSpeed, MiscUtils.ATTRIBUTE_ATTACK_VELOCITY_MODIFIER_ID,
                    "LobeCorp Attack Velocity Modifier", (attackVelocity * 0.2f) / 100
            );
        }
        // 修改原版移动速度（仅当数值变化时更新）
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed != null) {
            updateAttributeModifier(movementSpeed, MiscUtils.ATTRIBUTE_MOVE_VELOCITY_MODIFIER_ID,
                    "LobeCorp Move Velocity Modifier", (moveVelocity * 0.2f) / 100
            );
        }
    }

    /**
     * 安全更新属性修饰符（仅当数值变化时重新应用）
     *
     * @param instance   属性实例
     * @param modifierId 修饰符UUID
     * @param name       修饰符名称
     * @param newAmount  新数值
     */
    private static void updateAttributeModifier(AttributeInstance instance, UUID modifierId, String name, double newAmount) {
        AttributeModifier existingModifier = instance.getModifier(modifierId);
        // 如果修饰符已存在且数值相同，则跳过更新
        if (existingModifier != null &&
                existingModifier.amount() == newAmount &&
                existingModifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE) {
            return;
        }
        // 移除旧修饰符（如果存在）
        if (existingModifier != null) {
            instance.removeModifier(modifierId);
        }
        // 添加新修饰符
        instance.addPermanentModifier(new AttributeModifier(modifierId, name, newAmount, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }
}
