package net.unitego.lobecorp.common.item.ego.weapon;

public class EGOWeaponProfile {
    private final EGOWeaponTemplate egoWeaponTemplate;
    private final double attackSpeed;
    private final double interactionRange;
    private final boolean isBothHands;
    private final int attackCount;

    public EGOWeaponProfile(EGOWeaponTemplate egoWeaponTemplate) {
        this.egoWeaponTemplate = egoWeaponTemplate;
        this.attackSpeed = egoWeaponTemplate.getAttackSpeed();
        this.interactionRange = egoWeaponTemplate.getInteractionRange();
        this.isBothHands = egoWeaponTemplate.isBothHands();
        this.attackCount = egoWeaponTemplate.getAttackCount();
    }

    public EGOWeaponProfile(double attackSpeed, double interactionRange, boolean isBothHands, int attackCount) {
        this.attackCount = attackCount;
        this.egoWeaponTemplate = EGOWeaponTemplate.SPECIAL;
        this.attackSpeed = attackSpeed;
        this.interactionRange = interactionRange;
        this.isBothHands = isBothHands;
    }

    public EGOWeaponTemplate getEgoWeaponTemplate() {
        return egoWeaponTemplate;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public double getInteractionRange() {
        return interactionRange;
    }

    public boolean isBothHands() {
        return isBothHands;
    }

    public String getTranslationKey() {
        return egoWeaponTemplate.getTranslationKey();
    }

    public int getAttackCount() {
        return attackCount;
    }
}
