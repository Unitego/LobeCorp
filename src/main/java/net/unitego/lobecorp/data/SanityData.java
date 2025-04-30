package net.unitego.lobecorp.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.init.AttributeRegistry;
import net.unitego.lobecorp.init.SEDRegistry;

public class SanityData {
    private final Player player;

    public SanityData(Player player) {
        this.player = player;
        setSanity(getMaxSanity());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains("Sanity", 99)) {
            setSanity(compoundTag.getFloat("Sanity"));
        }
        internalSetAssimilationAmount(compoundTag.getFloat("AssimilationAmount"));
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putFloat("Sanity", getSanity());
        compoundTag.putFloat("AssimilationAmount", getAssimilationAmount());
    }

    public void cure(float cureAmount) {
        if (cureAmount <= 0) return;
        float f = getSanity();
        if (f > 0.0F) {
            setSanity(f + cureAmount);
        }
    }

    public float getSanity() {
        return player.getEntityData().get(SEDRegistry.DATA_PLAYER_SANITY_ID);
    }

    public void setSanity(float sanity) {
        player.getEntityData().set(SEDRegistry.DATA_PLAYER_SANITY_ID, Mth.clamp(sanity, 0.0F, getMaxSanity()));
    }

    public boolean isBlow() {
        return getSanity() > 0.0F && getSanity() < getMaxSanity();
    }

    public boolean isPanicOrCrazing() {
        return getSanity() <= 0.0F;
    }

    public boolean isNormal() {
        return !player.isRemoved() && getSanity() > 0.0F;
    }

    public final float getMaxSanity() {
        return (float) player.getAttributeValue(AttributeRegistry.MAX_SANITY);
    }

    public final float getMaxAssimilation() {
        return (float) player.getAttributeValue(AttributeRegistry.MAX_ASSIMILATION);
    }

    public float getAssimilationAmount() {
        return player.getEntityData().get(SEDRegistry.DATA_PLAYER_ASSIMILATION_ID);
    }

    public final void setAssimilationAmount(float assimilationAmount) {
        internalSetAssimilationAmount(Mth.clamp(assimilationAmount, 0.0F, getMaxAssimilation()));
    }

    private void internalSetAssimilationAmount(float assimilationAmount) {
        player.getEntityData().set(SEDRegistry.DATA_PLAYER_ASSIMILATION_ID, assimilationAmount);
    }
}
