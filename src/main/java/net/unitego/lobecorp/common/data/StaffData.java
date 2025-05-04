package net.unitego.lobecorp.common.data;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.registry.ModAttributes;

import java.util.Objects;

//职员数据
public class StaffData {
    public static final String STAFF_RANK = "lobecorp.info.staff_rank";//职员等级
    public static final String STAFF_FORTITUDE = "lobecorp.info.staff_fortitude";//勇气
    public static final String STAFF_PRUDENCE = "lobecorp.info.staff_prudence";//谨慎
    public static final String STAFF_TEMPERANCE = "lobecorp.info.staff_temperance";//自律
    public static final String STAFF_JUSTICE = "lobecorp.info.staff_justice";//正义
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

    public int getMaxHealth() {
        return (int) Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).getValue();
    }

    public void setMaxHealth(int maxHealth) {
        Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(maxHealth);
    }

    public int getMaxSanity() {
        return (int) Objects.requireNonNull(player.getAttribute(ModAttributes.MAX_SANITY)).getValue();
    }

    public void setMaxSanity(int maxSanity) {
        Objects.requireNonNull(player.getAttribute(ModAttributes.MAX_SANITY)).setBaseValue(maxSanity);
    }

    public int getWorkSuccess() {
        return (int) Objects.requireNonNull(player.getAttribute(ModAttributes.WORK_SUCCESS)).getValue();
    }

    public void setWorkSuccess(int workSuccess) {
        Objects.requireNonNull(player.getAttribute(ModAttributes.WORK_SUCCESS)).setBaseValue(workSuccess);
    }

    public int getWorkVelocity() {
        return (int) Objects.requireNonNull(player.getAttribute(ModAttributes.WORK_VELOCITY)).getValue();
    }

    public void setWorkVelocity(int workVelocity) {
        Objects.requireNonNull(player.getAttribute(ModAttributes.WORK_VELOCITY)).setBaseValue(workVelocity);
    }

    public int getAttackVelocity() {
        return (int) Objects.requireNonNull(player.getAttribute(ModAttributes.ATTACK_VELOCITY)).getValue();
    }

    public void setAttackVelocity(int attackVelocity) {
        Objects.requireNonNull(player.getAttribute(ModAttributes.ATTACK_VELOCITY)).setBaseValue(attackVelocity);
    }

    public int getMoveVelocity() {
        return (int) Objects.requireNonNull(player.getAttribute(ModAttributes.MOVE_VELOCITY)).getValue();
    }

    public void setMoveVelocity(int moveVelocity) {
        Objects.requireNonNull(player.getAttribute(ModAttributes.MOVE_VELOCITY)).setBaseValue(moveVelocity);
    }

    public StaffRank getJusticeRank() {
        return calculateVirtueRank((getAttackVelocity() + getMoveVelocity()) / 2);
    }

    public StaffRank getTemperanceRank() {
        return calculateVirtueRank((getWorkSuccess() + getWorkVelocity()) / 2);
    }

    public StaffRank getPrudenceRank() {
        return calculateVirtueRank(getMaxSanity());
    }

    public StaffRank getFortitudeRank() {
        return calculateVirtueRank(getMaxHealth());
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
