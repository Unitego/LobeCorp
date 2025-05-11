package net.unitego.lobecorp.common.util;

import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 用于处理玩家战斗状态和战斗能力判定的工具类。
 */
public class CombatStatusUtils {
    // 玩家 -> 最后战斗时间（tick）
    private static final Map<Player, Long> combatTimestamps = new WeakHashMap<>();
    // 玩家 -> key -> 判定结果（true = 已触发，false = 尚未触发）
    private static final Map<Player, Map<String, Boolean>> combatFlags = new WeakHashMap<>();
    // 玩家 -> key -> 注册的几率值
    private static final Map<Player, Map<String, Float>> chanceRegistry = new WeakHashMap<>();
    // 玩家 -> key -> 能力过期时间戳（tick）
    private static final Map<Player, Map<String, Long>> expireTimestamps = new WeakHashMap<>();

    // 战斗状态超时（tick，20 tick = 1 秒）
    private static final int COMBAT_TIMEOUT_TICKS = 600; // 30 秒

    /**
     * 标记玩家进入战斗状态（例如攻击或被攻击时调用）。
     * 同时根据注册的能力，尝试触发几率效果。
     */
    public static void enteredCombat(Player player) {
        long now = player.level().getGameTime();
        combatTimestamps.put(player, now);

        Map<String, Boolean> flags = combatFlags.get(player);
        Map<String, Float> chances = chanceRegistry.get(player);
        Map<String, Long> expires = expireTimestamps.get(player);

        if (flags != null && chances != null) {
            for (Map.Entry<String, Boolean> entry : flags.entrySet()) {
                String key = entry.getKey();
                boolean alreadySuccess = entry.getValue();

                long expireAt = expires != null ? expires.getOrDefault(key, -1L) : -1L;

                // 如果已经成功但已经过期，则允许重新 roll
                if (alreadySuccess && expireAt > 0 && now > expireAt) {
                    entry.setValue(false);
                    expires.remove(key);
                    alreadySuccess = false;
                }

                // 如果当前未成功，尝试 roll
                if (!alreadySuccess) {
                    float chance = chances.getOrDefault(key, 0f);
                    boolean result = MiscUtils.roll(player, chance);
                    if (result) {
                        entry.setValue(true);
                    }
                }
            }
        }

        MiscUtils.sendCenterClientMessage(MiscUtils.ENTERED_COMBAT);
    }

    /**
     * 定期检查战斗状态是否超时（例如每 tick 调用）。
     */
    public static void checkCombatTimeout(Player player) {
        Long lastCombatTime = combatTimestamps.get(player);
        if (lastCombatTime == null) return;
        if (player.level().getGameTime() - lastCombatTime > COMBAT_TIMEOUT_TICKS) {
            combatTimestamps.remove(player);
            chanceRegistry.remove(player);
            MiscUtils.sendCenterClientMessage(MiscUtils.LEFT_COMBAT);
        }
    }

    /**
     * 判断玩家当前是否处于战斗状态（30 秒内）。
     */
    public static boolean isInCombat(Player player) {
        Long lastCombatTime = combatTimestamps.get(player);
        if (lastCombatTime == null) return false;
        return player.level().getGameTime() - lastCombatTime <= COMBAT_TIMEOUT_TICKS;
    }

    /**
     * 玩家死亡时清除所有状态。
     */
    public static void clearOnDeath(Player player) {
        combatTimestamps.remove(player);
        combatFlags.remove(player);
        chanceRegistry.remove(player);
        expireTimestamps.remove(player);
    }

    /**
     * 注册一个带几率的战斗能力，该能力将只在触发后持续一段时间。
     *
     * @param player   玩家
     * @param key      能力唯一标识
     * @param chance   触发几率（0.0~1.0）
     * @param duration 能力持续时间（tick），设为 0 表示永久标记，不再重新判定
     */
    public static void registerCombatChance(Player player, String key, float chance, long duration) {
        combatFlags.computeIfAbsent(player, p -> new WeakHashMap<>()).putIfAbsent(key, false);
        chanceRegistry.computeIfAbsent(player, p -> new WeakHashMap<>()).putIfAbsent(key, chance);
        if (duration > 0) {
            expireTimestamps.computeIfAbsent(player, p -> new WeakHashMap<>())
                    .putIfAbsent(key, player.level().getGameTime() + duration);
        }
    }

    /**
     * 获取某能力当前是否已触发（即 roll 成功且未过期）。
     */
    public static boolean getCombatChance(Player player, String key) {
        Map<String, Boolean> flags = combatFlags.getOrDefault(player, Map.of());
        if (!flags.getOrDefault(key, false)) return false;
        Map<String, Long> expires = expireTimestamps.getOrDefault(player, Map.of());
        long now = player.level().getGameTime();
        long expireAt = expires.getOrDefault(key, -1L);
        return expireAt == -1L || now <= expireAt;
    }
}
