package net.unitego.lobecorp.common.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.registry.AttachmentTypeRegistry;
import net.unitego.lobecorp.common.registry.AttributeRegistry;

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

        return player.getData(AttachmentTypeRegistry.PLAYER_SANITY);
    }

    public void setSanity(float sanity) {
        if (player.level().isClientSide) {
            System.out.println("Client sanity:" + sanity);
            System.out.println("Client getMaxSanity:" + getMaxSanity());
        } else {
            System.out.println("Server sanity:" + sanity);
            System.out.println("Server getMaxSanity:" + getMaxSanity());
        }
        System.out.println("——————————");
        player.setData(AttachmentTypeRegistry.PLAYER_SANITY, Mth.clamp(sanity, 0.0F, getMaxSanity()));
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
        return player.getData(AttachmentTypeRegistry.PLAYER_ASSIMILATION);
    }

    public final void setAssimilationAmount(float assimilationAmount) {
        internalSetAssimilationAmount(Mth.clamp(assimilationAmount, 0.0F, getMaxAssimilation()));
    }

    private void internalSetAssimilationAmount(float assimilationAmount) {
        player.setData(AttachmentTypeRegistry.PLAYER_ASSIMILATION, assimilationAmount);
    }
}
