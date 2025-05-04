package net.unitego.lobecorp.common.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.network.sender.S2CDrinkResetSender;
import net.unitego.lobecorp.common.registry.ModDamageTypes;
import net.unitego.lobecorp.common.registry.ModSoundEvents;

public class WaterData {
    public boolean hasDrunkWater;
    public int cooldownTickTimer;
    private int waterLevel = 20;
    private float hydrationLevel;
    private float desiccationLevel;
    private int tickTimer;
    private int lastWaterLevel = 20;

    public WaterData() {
        hydrationLevel = 5.0F;
    }

    private void add(int waterLevel, float hydrationLevel) {
        this.waterLevel = Math.min(waterLevel + this.waterLevel, 20);
        this.hydrationLevel = Math.min(hydrationLevel + this.hydrationLevel, (float) this.waterLevel);
    }

    public void drink(int waterLevelModifier, float hydrationLevelModifier) {
        add(waterLevelModifier, waterLevelModifier * hydrationLevelModifier * 2.0f);
    }

    public void tick(Player player) {
        Difficulty difficulty = player.level().getDifficulty();
        lastWaterLevel = waterLevel;
        if (desiccationLevel > 4.0F) {
            desiccationLevel -= 4.0F;
            if (hydrationLevel > 0.0F) {
                hydrationLevel = Math.max(hydrationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                waterLevel = Math.max(waterLevel - 1, 0);
            }
        }

        boolean flag = player.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
        SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
        if (flag && hydrationLevel > 0.0F && sanityData.isBlow() && waterLevel >= 20) {
            tickTimer++;
            if (tickTimer >= 10) {
                float f = Math.min(hydrationLevel, 6.0F);
                sanityData.cure(f / 6.0F);
                addDesiccation(f);
                tickTimer = 0;
            }
        } else if (flag && waterLevel >= 18 && sanityData.isBlow()) {
            tickTimer++;
            if (tickTimer >= 80) {
                sanityData.cure(1.0F);
                addDesiccation(6.0F);
                tickTimer = 0;
            }
        } else if (waterLevel <= 0) {
            tickTimer++;
            if (tickTimer >= 80) {
                if (sanityData.getSanity() > 10.0F ||
                        difficulty == Difficulty.HARD ||
                        sanityData.getSanity() > 1.0F &&
                                difficulty == Difficulty.NORMAL) {
                    player.hurt(player.damageSources().source(ModDamageTypes.DRY), 1.0F);
                }

                tickTimer = 0;
            }
        } else {
            tickTimer = 0;
        }
        //喝水状态冷却时间及声音播放
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (hasDrunkWater) {
            --cooldownTickTimer;
            if (cooldownTickTimer == 0) {
                serverPlayer.level().playSound(null, serverPlayer.blockPosition(), ModSoundEvents.SWALLOW_WATER_SOUND.get(), SoundSource.PLAYERS, 1, 1);
                hasDrunkWater = false;
                S2CDrinkResetSender.send(serverPlayer);
            }
        }
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains("waterLevel", 99)) {
            waterLevel = compoundTag.getInt("waterLevel");
            tickTimer = compoundTag.getInt("waterTickTimer");
            hydrationLevel = compoundTag.getFloat("waterHydrationLevel");
            desiccationLevel = compoundTag.getFloat("waterDesiccationLevel");
        }
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("waterLevel", waterLevel);
        compoundTag.putInt("waterTickTimer", tickTimer);
        compoundTag.putFloat("waterHydrationLevel", hydrationLevel);
        compoundTag.putFloat("waterDesiccationLevel", desiccationLevel);
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public int getLastWaterLevel() {
        return lastWaterLevel;
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
