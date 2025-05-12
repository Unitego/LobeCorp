package net.unitego.lobecorp.common.manager;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.registry.AttributesRegistry;
import net.unitego.lobecorp.registry.DamageTypesRegistry;

public class SanityManager {
    private final Player player;
    private final EntityDataAccessor<Float> dataPlayerSanityId;
    private final EntityDataAccessor<Float> dataPlayerAssimilationId;
    private boolean isCrazing;
    private int panicTickTimer;
    private boolean shouldKill;

    public SanityManager(Player player, EntityDataAccessor<Float> dataPlayerSanityId, EntityDataAccessor<Float> dataPlayerAssimilationId) {
        this.player = player;
        this.dataPlayerSanityId = dataPlayerSanityId;
        this.dataPlayerAssimilationId = dataPlayerAssimilationId;
        setSanity(getMaxSanity());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains("Sanity", 99)) {
            setSanity(compoundTag.getFloat("Sanity"));
            isCrazing = compoundTag.getBoolean("isCrazing");
            panicTickTimer = compoundTag.getInt("panicTickTimer");
        }
        internalSetAssimilationAmount(compoundTag.getFloat("AssimilationAmount"));
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putFloat("Sanity", getSanity());
        compoundTag.putFloat("AssimilationAmount", getAssimilationAmount());
        compoundTag.putBoolean("isCrazing", isCrazing);
        compoundTag.putInt("panicTickTimer", panicTickTimer);
    }

    public void cure(float cureAmount) {
        if (player.level().isClientSide) return;
        if (cureAmount <= 0) return;
        float f = getSanity();
        if (f > 0.0F) {
            setSanity(f + cureAmount);
        }
    }

    //恐慌状态
    public void panicState() {
        if (!(player.isCreative() || player.isSpectator()) && player.isAlive()) {
            if (isPanicOrCrazing()) {
                ++panicTickTimer;
                if (panicTickTimer >= 900) {
                    setShouldKill(true);
                    sanityKill();
                    panicTickTimer = 0;
                } else {
                    MiscUtils.hiddenEffect(player, MobEffects.CONFUSION, 900, 254);
                    MiscUtils.hiddenEffect(player, MobEffects.MOVEMENT_SLOWDOWN, 900, 254);
                }
            } else {
                panicTickTimer = 0;
            }
        }
    }

    private void sanityKill() {
        player.hurt(player.damageSources().source(DamageTypesRegistry.WHITE, player), Float.MAX_VALUE);
    }

    public float getSanity() {
        return player.getEntityData().get(dataPlayerSanityId);
    }

    public void setSanity(float sanity) {
        player.getEntityData().set(dataPlayerSanityId, Mth.clamp(sanity, 0.0F, getMaxSanity()));
    }

    public boolean isBlow() {
        return getSanity() > 0.0F && getSanity() < getMaxSanity() && !isPanicOrCrazing();
    }

    public boolean isPanicOrCrazing() {
        isCrazing = getSanity() <= 0.0f || (isCrazing && getSanity() < getMaxSanity());
        return isCrazing;
    }

    public boolean isNormal() {
        return !player.isRemoved() && getSanity() > 0.0F && !isPanicOrCrazing();
    }

    public final float getMaxSanity() {
        return (float) player.getAttributeValue(AttributesRegistry.MAX_SANITY);
    }

    public final float getMaxAssimilation() {
        return (float) player.getAttributeValue(AttributesRegistry.MAX_ASSIMILATION);
    }

    public float getAssimilationAmount() {
        return player.getEntityData().get(dataPlayerAssimilationId);
    }

    public final void setAssimilationAmount(float assimilationAmount) {
        internalSetAssimilationAmount(Mth.clamp(assimilationAmount, 0.0F, getMaxAssimilation()));
    }

    private void internalSetAssimilationAmount(float assimilationAmount) {
        player.getEntityData().set(dataPlayerAssimilationId, assimilationAmount);
    }

    public boolean isShouldKill() {
        return shouldKill;
    }

    public void setShouldKill(boolean shouldKill) {
        this.shouldKill = shouldKill;
    }
}
