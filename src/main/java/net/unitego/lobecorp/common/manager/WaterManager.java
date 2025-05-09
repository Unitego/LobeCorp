package net.unitego.lobecorp.common.manager;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.network.sender.S2CDrinkResetSender;
import net.unitego.lobecorp.registry.DamageTypesRegistry;
import net.unitego.lobecorp.registry.SoundEventsRegistry;

public class WaterManager {
    private final Player player;
    public boolean hasDrunkWater;
    public int cooldownTickTimer;
    private int waterLevel = 20;
    private float hydrationLevel;
    private float desiccationLevel;
    private int waterTickTimer;

    public WaterManager(Player player) {
        this.player = player;
        hydrationLevel = 5.0F;
    }

    private void add(int waterLevel, float hydrationLevel) {
        this.waterLevel = Math.min(waterLevel + this.waterLevel, 20);
        this.hydrationLevel = Math.min(hydrationLevel + this.hydrationLevel, (float) this.waterLevel);
    }

    public void drink(int waterLevelModifier, float hydrationLevelModifier) {
        add(waterLevelModifier, waterLevelModifier * hydrationLevelModifier * 2.0f);
    }

    public void tick() {
        Difficulty difficulty = player.level().getDifficulty();
        if (desiccationLevel > 4.0F) {
            desiccationLevel -= 4.0F;
            if (hydrationLevel > 0.0F) {
                hydrationLevel = Math.max(hydrationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                waterLevel = Math.max(waterLevel - 1, 0);
            }
        }
        boolean flag = player.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
        SanityManager sanityManager = ((ManagerAccess) player).lobeCorp$getSanityManager();
        if (flag && hydrationLevel > 0.0F && sanityManager.isBlow() && waterLevel >= 20) {
            waterTickTimer++;
            if (waterTickTimer >= 10) {
                float f = Math.min(hydrationLevel, 6.0F);
                sanityManager.cure(f / 6.0F);
                addDesiccation(f);
                waterTickTimer = 0;
            }
        } else if (flag && waterLevel >= 18 && sanityManager.isBlow()) {
            waterTickTimer++;
            if (waterTickTimer >= 80) {
                sanityManager.cure(1.0F);
                addDesiccation(6.0F);
                waterTickTimer = 0;
            }
        } else if (waterLevel <= 0) {
            waterTickTimer++;
            if (waterTickTimer >= 80) {
                if (sanityManager.getSanity() > 10.0F ||
                        difficulty == Difficulty.HARD ||
                        sanityManager.getSanity() > 1.0F &&
                                difficulty == Difficulty.NORMAL) {
                    player.hurt(player.damageSources().source(DamageTypesRegistry.DRY), 1.0F);
                }
                waterTickTimer = 0;
            }
        } else {
            waterTickTimer = 0;
        }
        //喝水状态冷却时间及声音播放
        if (player instanceof ServerPlayer serverPlayer && hasDrunkWater) {
            --cooldownTickTimer;
            if (cooldownTickTimer == 0) {
                MiscUtils.playSound(serverPlayer, SoundEventsRegistry.SWALLOW_WATER.get());
                hasDrunkWater = false;
                S2CDrinkResetSender.send(serverPlayer);
            }
        }
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains("waterLevel", 99)) {
            waterLevel = compoundTag.getInt("waterLevel");
            waterTickTimer = compoundTag.getInt("waterTickTimer");
            hydrationLevel = compoundTag.getFloat("waterHydrationLevel");
            desiccationLevel = compoundTag.getFloat("waterDesiccationLevel");
        }
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("waterLevel", waterLevel);
        compoundTag.putInt("waterTickTimer", waterTickTimer);
        compoundTag.putFloat("waterHydrationLevel", hydrationLevel);
        compoundTag.putFloat("waterDesiccationLevel", desiccationLevel);
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public boolean needsWater() {
        return waterLevel < 20;
    }

    public void addDesiccation(float desiccation) {
        desiccationLevel = Math.min(desiccationLevel + desiccation, 40.0F);
    }

    public float getDesiccationLevel() {
        return desiccationLevel;
    }

    public float getHydrationLevel() {
        return hydrationLevel;
    }

    public void setHydration(float hydrationLevel) {
        this.hydrationLevel = hydrationLevel;
    }

    public void setDesiccation(float desiccationLevel) {
        this.desiccationLevel = desiccationLevel;
    }
}
