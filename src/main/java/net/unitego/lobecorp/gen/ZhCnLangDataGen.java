package net.unitego.lobecorp.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.registry.*;
import net.unitego.lobecorp.common.util.LobeCorpUtils;

public class ZhCnLangDataGen extends LanguageProvider {
    public ZhCnLangDataGen(PackOutput output) {
        super(output, LobeCorp.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        //属性名
        add(ModAttributes.MAX_SANITY.get().getDescriptionId(), "最大精神值");
        add(ModAttributes.MAX_ASSIMILATION.get().getDescriptionId(), "最大认知同化值");
        add(ModAttributes.WORK_SUCCESS.get().getDescriptionId(), "工作成率");
        add(ModAttributes.WORK_VELOCITY.get().getDescriptionId(), "工作速率");
        add(ModAttributes.ATTACK_VELOCITY.get().getDescriptionId(), "攻击速率");
        add(ModAttributes.MOVE_VELOCITY.get().getDescriptionId(), "移动速率");
        //效果名
        add(ModMobEffects.ABSENT.get(), "恍惚");
        add(ModMobEffects.ASSIMILATION.get(), "认知同化");
        add(ModMobEffects.HYDRATION.get(), "滋润");
        add(ModMobEffects.INSANE.get(), "狂乱");
        add(ModMobEffects.RESTORATION.get(), "精神恢复");
        add(ModMobEffects.SANITY_BOOST.get(), "精神提升");
        add(ModMobEffects.THIRST.get(), "干渴");
        add(ModMobEffects.INSTANT_SANITY.get(), "瞬间镇定");
        add(ModMobEffects.INSTANT_INJURY.get(), "瞬间损伤");
        //物品名
        add(ModItems.LOBECORP_LOGO.get(), "脑叶公司Logo");
        add(ModItems.BLUE_LEAF.get(), "蓝叶");
        add(ModItems.CODE_SUIT.get(), "代码西装");
        add(ModItems.CODE_RIOT_STICK.get(), "代码镇暴棍");
        add(ModItems.STANDARD_SUIT.get(), "标准西装");
        add(ModItems.RED_RIOT_STICK.get(), "物理镇暴棍");
        add(ModItems.WHITE_RIOT_STICK.get(), "精神镇暴棍");
        add(ModItems.BLACK_RIOT_STICK.get(), "侵蚀镇暴棍");
        add(ModItems.PALE_RIOT_STICK.get(), "灵魂镇暴棍");
        //创造栏名
        add(ModCreativeModeTabs.LOBECORP_TAB_NAME, "脑叶公司");
        //声音名
        add(ModSoundEvents.SWALLOW_WATER_SOUND.get().getLocation().toLanguageKey(), "咽水");
        //按键名
        add(ModKeyMappings.CATEGORIES_LOBECORP, "脑叶公司");
        add(ModKeyMappings.TOGGLE_EQUIPMENT.get().getName(), "开启/关闭 装备");
        //界面名
        add(ModMenus.CONTAINER_LOBECORP_EQUIPMENT, "装备");
        //插槽名
        add(LobeCorpUtils.getItemModifiersName(LobeCorpEquipmentSlot.LOBECORP_WEAPON), "在武器位时：");
        add(LobeCorpUtils.getItemModifiersName(LobeCorpEquipmentSlot.LOBECORP_SUIT), "在护甲位时：");
        add(LobeCorpUtils.getItemModifiersName(LobeCorpEquipmentSlot.LOBECORP_BADGE), "在袖标位时：");
        add(LobeCorpUtils.getItemModifiersName(LobeCorpEquipmentSlot.LOBECORP_TOOL), "在工具位时：");
        //死亡信息
        add(ModDamageTypes.DEATH_ATTACK_DRY, "%1$s渴死了");
        add(ModDamageTypes.DEATH_ATTACK_DRY_PLAYER, "%1$s在与%2$s战斗时渴死了");
        add(ModDamageTypes.DEATH_ATTACK_INSANE, "%1$s狂乱了");
        add(ModDamageTypes.DEATH_ATTACK_INSANE_PLAYER, "%1$s在与%2$s战斗时狂乱了");
        add(ModDamageTypes.DEATH_ATTACK_INDIRECT_MYSTIC, "%1$s被%2$s使用的神秘杀死了");
        add(ModDamageTypes.DEATH_ATTACK_INDIRECT_MYSTIC_ITEM, "%1$s被%2$s使用%3$s杀死了");
        add(ModDamageTypes.DEATH_ATTACK_MYSTIC, "%1$s被神秘杀死了");
        add(ModDamageTypes.DEATH_ATTACK_MYSTIC_PLAYER, "%1$s在试图逃离%2$s时被神秘杀死了");
        add(ModDamageTypes.DEATH_ATTACK_RED, "%2$s致使%1$s死于物理破碎");
        add(ModDamageTypes.DEATH_ATTACK_RED_ITEM, "%2$s使用%3$s致使%1$s死于物理破碎");
        add(ModDamageTypes.DEATH_ATTACK_WHITE, "%2$s致使%1$s死于精神崩溃");
        add(ModDamageTypes.DEATH_ATTACK_WHITE_ITEM, "%2$s使用%3$s致使%1$s死于精神崩溃");
        add(ModDamageTypes.DEATH_ATTACK_BLACK, "%2$s致使%1$s死于侵蚀贯穿");
        add(ModDamageTypes.DEATH_ATTACK_BLACK_ITEM, "%2$s使用%3$s致使%1$s死于侵蚀贯穿");
        add(ModDamageTypes.DEATH_ATTACK_PALE, "%2$s致使%1$s死于灵魂削除");
        add(ModDamageTypes.DEATH_ATTACK_PALE_ITEM, "%2$s使用%3$s致使%1$s死于灵魂削除");
        //武器模板
        add(EGOWeaponTemplate.MACE.getTranslationKey(), "棁");
        add(EGOWeaponTemplate.AXE.getTranslationKey(), "斧");
        add(EGOWeaponTemplate.KNIFE.getTranslationKey(), "刀");
        //脑叶公司
        add(LobeCorpUtils.MOTTO, "直面恐惧，创造未来");
        add(LobeCorpUtils.CONFIRM, "确认");
        add(LobeCorpUtils.INVALID_VALUE, "输入无效，请检查输入值。");
        add(LobeCorpUtils.STAFF_RANK, "职员等级");
        add(LobeCorpUtils.STAFF_FORTITUDE, "勇气");
        add(LobeCorpUtils.STAFF_PRUDENCE, "谨慎");
        add(LobeCorpUtils.STAFF_TEMPERANCE, "自律");
        add(LobeCorpUtils.STAFF_JUSTICE, "正义");
        add(LobeCorpUtils.HOLD_LEFT_SHIFT_SHOW_SKILL, "按住左Shift显示技能");
        add(LobeCorpUtils.EGO_RANK, "EGO等级：");
        add(LobeCorpUtils.EQUIP_REQUIRE, "装备要求：");
        add(LobeCorpUtils.WEAPON_TEMPLATE, "武器模板：");
        add(LobeCorpUtils.DAMAGE_TYPE, "伤害类型：");
        add(LobeCorpUtils.DAMAGE_RESIST, "伤害抗性：");
        add(LobeCorpUtils.RED, "物理");
        add(LobeCorpUtils.WHITE, "精神");
        add(LobeCorpUtils.BLACK, "侵蚀");
        add(LobeCorpUtils.PALE, "灵魂");
    }
}
