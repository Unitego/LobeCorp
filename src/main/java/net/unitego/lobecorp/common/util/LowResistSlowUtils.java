package net.unitego.lobecorp.common.util;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

//弱抗减速机制工具
public class LowResistSlowUtils {
    private static final Map<Player, Integer> playerLowResistSlowTicks = new HashMap<>();

    public static void apply(Player player) {
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null) return;
        // 如果没有应用减速效果，添加它
        if (movementSpeed.getModifier(MiscUtils.LOW_RESIST_SLOW_MODIFIER_ID) == null) {
            AttributeModifier slow = new AttributeModifier(
                    MiscUtils.LOW_RESIST_SLOW_MODIFIER_ID,
                    "Low Resist Slow Modifier",
                    -0.9, // 减速 90%
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );
            movementSpeed.addTransientModifier(slow);
        }

        // 设置持续时间为 20 ticks (即 1 秒)
        playerLowResistSlowTicks.put(player, 20);
    }

    public static void remove(Player player) {
        if (playerLowResistSlowTicks.containsKey(player)) {
            int ticks = playerLowResistSlowTicks.get(player) - 1;
            if (ticks <= 0) {
                // 如果计时器到期，移除减速效果
                AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
                if (movementSpeed != null && movementSpeed.getModifier(MiscUtils.LOW_RESIST_SLOW_MODIFIER_ID) != null) {
                    movementSpeed.removeModifier(MiscUtils.LOW_RESIST_SLOW_MODIFIER_ID);
                }
                // 移除计时器
                playerLowResistSlowTicks.remove(player);
            } else {
                playerLowResistSlowTicks.put(player, ticks);
            }
        }
    }
}
