package net.unitego.lobecorp.common.item.ego.weapon;

public enum EGOWeaponTemplate {
    /**
     * 在脑叶wiki里，他写的攻击速度是按秒来的
     * 所以拿到秒数后，先拿1s去1s/?s就能得到一个大概的数值
     * 比如棁类是2s，1s/2s=0.5，而这个0.5就会是游戏里物品拿在主手时发挥的攻击速度
     * 然后我们这里填数值的话就直接拿4（空手时）去0.5-4=-3.5
     * 这样的话到时候就好直接让他被调用时默认4-3.5=0.5
     * 公式：attackSpeed=1s/?s-4
     */
    MACE("mace", -3.50f, 3.00f, false, 1),//棁
    AXE("axe", -3.00f, 2.00f, false, 1),//斧
    KNIFE("knife", -2.50f, 2.00f, false, 1),//刀
    SPEAR("spear", -3.30f, 4.00f, true, 1),//矛
    HAMMER("hammer", -3.70f, 5.00f, true, 1),//锤
    FIST("fist", -3.50f, 2.00f, true, 2),//拳套
    SPECIAL("special", 0.00f, 0.00f, false, 0);//特殊

    private final String name;
    private final double attackSpeed;
    private final double interactionRange;
    private final boolean isBothHands;
    private final int attackCount;

    EGOWeaponTemplate(String name, double attackSpeed, double interactionRange, boolean isBothHands, int attackCount) {
        this.name = name;
        this.attackSpeed = attackSpeed;
        this.interactionRange = interactionRange;
        this.isBothHands = isBothHands;
        this.attackCount = attackCount;
    }

    public String getTranslationKey() {
        return "ego.weapon.template." + name;
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

    public int getAttackCount() {
        return attackCount;
    }
}
