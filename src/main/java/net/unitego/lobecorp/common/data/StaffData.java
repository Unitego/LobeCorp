package net.unitego.lobecorp.common.data;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.registry.ModAttributes;

import java.util.Objects;

//职员数据
public class StaffData {
    private final Player player;

    public StaffData(Player player) {
        this.player = player;
    }

    //计算品质等级
    public static StaffRank calculateVirtueRank(int value) {
        if (value >= 100) return StaffRank.EX;
        if (value >= 85) return StaffRank.V;
        if (value >= 65) return StaffRank.IV;
        if (value >= 45) return StaffRank.III;
        if (value >= 30) return StaffRank.II;
        if (value > 0) return StaffRank.I;
        return StaffRank.ER;
    }

    //计算职员等级
    public static StaffRank calculateStaffRank(StaffRank fortitudeRank, StaffRank prudenceRank, StaffRank temperanceRank, StaffRank justiceRank) {
        int total = fortitudeRank.value + prudenceRank.value + temperanceRank.value + justiceRank.value;
        if (total >= 24) return StaffRank.EX;
        if (total >= 16) return StaffRank.V;
        int value = Mth.clamp((total) / 3, -1, 4);
        return switch (value) {
            case 4 -> StaffRank.IV;
            case 3 -> StaffRank.III;
            case 2 -> StaffRank.II;
            case 1 -> StaffRank.I;
            default -> StaffRank.ER;
        };
    }

    public double getMaxHealth() {
        return Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).getValue();
    }

    public void setMaxHealth(double maxHealth) {
        Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(maxHealth);
    }

    public double getMaxSanity() {
        return Objects.requireNonNull(player.getAttribute(ModAttributes.MAX_SANITY)).getValue();
    }

    public void setMaxSanity(double maxSanity) {
        Objects.requireNonNull(player.getAttribute(ModAttributes.MAX_SANITY)).setBaseValue(maxSanity);
    }

    public double getWorkSuccess() {
        return Objects.requireNonNull(player.getAttribute(ModAttributes.WORK_SUCCESS)).getValue();
    }

    public void setWorkSuccess(double workSuccess) {
        Objects.requireNonNull(player.getAttribute(ModAttributes.WORK_SUCCESS)).setBaseValue(workSuccess);
    }

    public double getWorkVelocity() {
        return Objects.requireNonNull(player.getAttribute(ModAttributes.WORK_VELOCITY)).getValue();
    }

    public void setWorkVelocity(double workVelocity) {
        Objects.requireNonNull(player.getAttribute(ModAttributes.WORK_VELOCITY)).setBaseValue(workVelocity);
    }

    public double getAttackVelocity() {
        return Objects.requireNonNull(player.getAttribute(ModAttributes.ATTACK_VELOCITY)).getValue();
    }

    public void setAttackVelocity(double attackVelocity) {
        Objects.requireNonNull(player.getAttribute(ModAttributes.ATTACK_VELOCITY)).setBaseValue(attackVelocity);
    }

    public double getMoveVelocity() {
        return Objects.requireNonNull(player.getAttribute(ModAttributes.MOVE_VELOCITY)).getValue();
    }

    public void setMoveVelocity(double moveVelocity) {
        Objects.requireNonNull(player.getAttribute(ModAttributes.MOVE_VELOCITY)).setBaseValue(moveVelocity);
    }

    public StaffRank getJusticeRank() {
        return calculateVirtueRank((int) ((getAttackVelocity() + getMoveVelocity()) / 2));
    }

    public StaffRank getTemperanceRank() {
        return calculateVirtueRank((int) ((getWorkSuccess() + getWorkVelocity()) / 2));
    }

    public StaffRank getPrudenceRank() {
        return calculateVirtueRank((int) getMaxSanity());
    }

    public StaffRank getFortitudeRank() {
        return calculateVirtueRank((int) getMaxHealth());
    }

    public StaffRank getStaffRank() {
        return calculateStaffRank(getFortitudeRank(), getPrudenceRank(), getTemperanceRank(), getJusticeRank());
    }

    //职员等级
    public enum StaffRank {
        I("I", 1),
        II("II", 2),
        III("III", 3),
        IV("IV", 4),
        V("V", 5),
        EX("EX", 6),
        ER("ER", -1);
        private final String rank;
        private final int value;

        StaffRank(String rank, int value) {
            this.rank = rank;
            this.value = value;
        }

        public String getRank() {
            return rank;
        }

        public int getValue() {
            return value;
        }
    }
}
