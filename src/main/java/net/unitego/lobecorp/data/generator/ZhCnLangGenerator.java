package net.unitego.lobecorp.data.generator;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.client.init.KeyInit;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.registry.*;

public class ZhCnLangGenerator extends LanguageProvider {
    public ZhCnLangGenerator(PackOutput output) {
        super(output, LobeCorp.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        //属性名
        add(AttributesRegistry.MAX_SANITY.get().getDescriptionId(), "最大精神值");
        add(AttributesRegistry.MAX_ASSIMILATION.get().getDescriptionId(), "最大认知同化值");
        add(AttributesRegistry.WORK_SUCCESS.get().getDescriptionId(), "工作成率");
        add(AttributesRegistry.WORK_VELOCITY.get().getDescriptionId(), "工作速率");
        add(AttributesRegistry.ATTACK_VELOCITY.get().getDescriptionId(), "攻击速率");
        add(AttributesRegistry.MOVE_VELOCITY.get().getDescriptionId(), "移动速率");
        //创造栏名
        add(CreativeModeTabsRegistry.LOBECORP_TAB_NAME, "脑叶公司");
        //死亡信息
        add(DamageTypesRegistry.DEATH_ATTACK_DRY, "%1$s渴死了");
        add(DamageTypesRegistry.DEATH_ATTACK_DRY_PLAYER, "%1$s在与%2$s战斗时渴死了");
        add(DamageTypesRegistry.DEATH_ATTACK_INSANE, "%1$s狂乱了");
        add(DamageTypesRegistry.DEATH_ATTACK_INSANE_PLAYER, "%1$s在与%2$s战斗时狂乱了");
        add(DamageTypesRegistry.DEATH_ATTACK_INDIRECT_MYSTIC, "%1$s被%2$s使用的神秘杀死了");
        add(DamageTypesRegistry.DEATH_ATTACK_INDIRECT_MYSTIC_ITEM, "%1$s被%2$s使用%3$s杀死了");
        add(DamageTypesRegistry.DEATH_ATTACK_MYSTIC, "%1$s被神秘杀死了");
        add(DamageTypesRegistry.DEATH_ATTACK_MYSTIC_PLAYER, "%1$s在试图逃离%2$s时被神秘杀死了");
        add(DamageTypesRegistry.DEATH_ATTACK_RED, "%2$s致使%1$s死于物理破碎");
        add(DamageTypesRegistry.DEATH_ATTACK_RED_ITEM, "%2$s使用%3$s致使%1$s死于物理破碎");
        add(DamageTypesRegistry.DEATH_ATTACK_WHITE, "%2$s致使%1$s死于精神崩溃");
        add(DamageTypesRegistry.DEATH_ATTACK_WHITE_ITEM, "%2$s使用%3$s致使%1$s死于精神崩溃");
        add(DamageTypesRegistry.DEATH_ATTACK_BLACK, "%2$s致使%1$s死于侵蚀贯穿");
        add(DamageTypesRegistry.DEATH_ATTACK_BLACK_ITEM, "%2$s使用%3$s致使%1$s死于侵蚀贯穿");
        add(DamageTypesRegistry.DEATH_ATTACK_PALE, "%2$s致使%1$s死于灵魂削除");
        add(DamageTypesRegistry.DEATH_ATTACK_PALE_ITEM, "%2$s使用%3$s致使%1$s死于灵魂削除");
        //物品名
        add(ItemsRegistry.LOBECORP_LOGO.get(), "脑叶公司Logo");
        add(ItemsRegistry.BLUE_LEAF.get(), "蓝叶");
        add(ItemsRegistry.CODE_SUIT.get(), "代码西装");
        add(ItemsRegistry.CODE_RIOT_STICK.get(), "代码镇暴棍");
        add(ItemsRegistry.STANDARD_SUIT.get(), "标准西装");
        add(ItemsRegistry.RED_RIOT_STICK.get(), "物理镇暴棍");
        add(ItemsRegistry.WHITE_RIOT_STICK.get(), "精神镇暴棍");
        add(ItemsRegistry.BLACK_RIOT_STICK.get(), "侵蚀镇暴棍");
        add(ItemsRegistry.PALE_RIOT_STICK.get(), "灵魂镇暴棍");
        //菜单名
        add(MenusRegistry.CONTAINER_LOBECORP_EQUIPMENT, "装备");
        //效果名
        add(MobEffectsRegistry.ABSENT.get(), "恍惚");
        add(MobEffectsRegistry.ASSIMILATION.get(), "认知同化");
        add(MobEffectsRegistry.HYDRATION.get(), "滋润");
        add(MobEffectsRegistry.INSANE.get(), "狂乱");
        add(MobEffectsRegistry.RESTORATION.get(), "精神恢复");
        add(MobEffectsRegistry.SANITY_BOOST.get(), "精神提升");
        add(MobEffectsRegistry.THIRST.get(), "干渴");
        add(MobEffectsRegistry.INSTANT_SANITY.get(), "瞬间镇定");
        add(MobEffectsRegistry.INSTANT_INJURY.get(), "瞬间损伤");
        //声音名
        add(SoundEventsRegistry.SWALLOW_WATER.get().getLocation().toLanguageKey(), "咽水");
        add(SoundEventsRegistry.CHANGE_EQUIPMENT.get().getLocation().toLanguageKey(), "变换装备");

        //按键名
        add(KeyInit.KEY_CATEGORIES_LOBECORP, "脑叶公司");
        add(KeyInit.KEY_TOGGLE_EQUIPMENT.get().getName(), "开启/关闭 装备");

        //武器模板
        add(EGOWeaponTemplate.MACE.getTranslationKey(), "棁");
        add(EGOWeaponTemplate.AXE.getTranslationKey(), "斧");
        add(EGOWeaponTemplate.KNIFE.getTranslationKey(), "刀");
        //插槽名
        add(LobeCorpEquipmentSlot.LOBECORP_WEAPON.getSlotModifiersName(), "在武器位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_SUIT.getSlotModifiersName(), "在护甲位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_BADGE.getSlotModifiersName(), "在袖标位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_TOOL.getSlotModifiersName(), "在工具位时：");

        //脑叶公司
        add(MiscUtils.MOTTO, "直面恐惧，创造未来");
        add(MiscUtils.CONFIRM, "确认");
        add(MiscUtils.INVALID_VALUE, "输入无效，请检查输入值。");
        add(MiscUtils.STAFF_RANK, "职员等级");
        add(MiscUtils.STAFF_FORTITUDE, "勇气");
        add(MiscUtils.STAFF_PRUDENCE, "谨慎");
        add(MiscUtils.STAFF_TEMPERANCE, "自律");
        add(MiscUtils.STAFF_JUSTICE, "正义");
        add(MiscUtils.HOLD_LEFT_SHIFT_SHOW_SKILL, "按住左Shift显示技能");
        add(MiscUtils.EGO_RANK, "EGO等级：");
        add(MiscUtils.EQUIP_REQUIRE, "装备要求：");
        add(MiscUtils.WEAPON_TEMPLATE, "武器模板：");
        add(MiscUtils.DAMAGE_TYPE, "伤害类型：");
        add(MiscUtils.DAMAGE_RESIST, "伤害抗性：");
        add(MiscUtils.RED, "物理");
        add(MiscUtils.WHITE, "精神");
        add(MiscUtils.BLACK, "侵蚀");
        add(MiscUtils.PALE, "灵魂");
    }
}
