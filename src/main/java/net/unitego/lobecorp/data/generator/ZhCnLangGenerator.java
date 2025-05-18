package net.unitego.lobecorp.data.generator;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.client.init.KeyInit;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.item.badge.TeamBadge;
import net.unitego.lobecorp.common.item.ego.gift.chest.GreenStemGift;
import net.unitego.lobecorp.common.item.ego.gift.eye.GrinderMk4Gift;
import net.unitego.lobecorp.common.item.ego.gift.head.BearPawsGift;
import net.unitego.lobecorp.common.item.ego.gift.head.PenitenceGift;
import net.unitego.lobecorp.common.item.ego.gift.mouth.SanguineDesireGift;
import net.unitego.lobecorp.common.item.ego.suit.FleshBoundSuit;
import net.unitego.lobecorp.common.item.ego.suit.PenitenceSuit;
import net.unitego.lobecorp.common.item.ego.suit.RedEyesSuit;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.item.ego.weapon.axe.SanguineDesireWeapon;
import net.unitego.lobecorp.common.item.ego.weapon.hammer.LampWeapon;
import net.unitego.lobecorp.common.item.ego.weapon.mace.PenitenceWeapon;
import net.unitego.lobecorp.common.item.ego.weapon.mace.RedEyesWeapon;
import net.unitego.lobecorp.common.item.ego.weapon.spear.FragmentsFromSomewhereWeapon;
import net.unitego.lobecorp.common.item.ego.weapon.spear.FrostSplinterWeapon;
import net.unitego.lobecorp.common.item.ego.weapon.spear.SporeWeapon;
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
        //脑叶公司
        add(ItemsRegistry.LOBECORP_LOGO.get(), "脑叶公司Logo");
        add(ItemsRegistry.BLUE_LEAF.get(), "蓝叶");
        add(ItemsRegistry.CODE_SUIT.get(), "代码西装");
        add(ItemsRegistry.CODE_RIOT_STICK.get(), "代码镇暴棍");
        add(ItemsRegistry.STANDARD_SUIT.get(), "标准西装");
        add(ItemsRegistry.RED_RIOT_STICK.get(), "物理镇暴棍");
        add(ItemsRegistry.WHITE_RIOT_STICK.get(), "精神镇暴棍");
        add(ItemsRegistry.BLACK_RIOT_STICK.get(), "侵蚀镇暴棍");
        add(ItemsRegistry.PALE_RIOT_STICK.get(), "灵魂镇暴棍");
        //部门袖标
        add(ItemsRegistry.CONTROL_TEAM_BADGE.get(), "控制部袖标");
        add(ItemsRegistry.INFORMATION_TEAM_BADGE.get(), "情报部袖标");
        add(ItemsRegistry.SECURITY_TEAM_BADGE.get(), "安保部袖标");
        add(ItemsRegistry.TRAINING_TEAM_BADGE.get(), "培训部袖标");
        add(ItemsRegistry.CENTRAL_COMMAND_TEAM_BADGE.get(), "中央本部袖标");
        add(ItemsRegistry.WELFARE_TEAM_BADGE.get(), "福利部袖标");
        add(ItemsRegistry.DISCIPLINARY_TEAM_BADGE.get(), "惩戒部袖标");
        add(ItemsRegistry.RECORD_TEAM_BADGE.get(), "记录部袖标");
        add(ItemsRegistry.EXTRACTION_TEAM_BADGE.get(), "研发部袖标");
        add(ItemsRegistry.ARCHITECTURE_TEAM_BADGE.get(), "构筑部袖标");
        //EGO武器
        add(ItemsRegistry.STANDARD_TRAINING_EGO_WEAPON.get(), "教学用E.G.O");
        add(ItemsRegistry.PENITENCE_WEAPON.get(), "忏悔");
        add(ItemsRegistry.WING_BEAT_WEAPON.get(), "振翅");
        add(ItemsRegistry.RED_EYES_WEAPON.get(), "赤瞳");
        add(ItemsRegistry.CHRISTMAS_WEAPON.get(), "悲惨圣诞");
        add(ItemsRegistry.DIFFRACTION_WEAPON.get(), "虚无衍射体");
        add(ItemsRegistry.SANGUINE_DESIRE_WEAPON.get(), "血之渴望");
        add(ItemsRegistry.WRIST_CUTTER_WEAPON.get(), "割腕者");
        add(ItemsRegistry.HORN_WEAPON.get(), "犄角");
        add(ItemsRegistry.FRAGMENTS_FROM_SOMEWHERE_WEAPON.get(), "彼方的裂片");
        add(ItemsRegistry.HARVEST_WEAPON.get(), "猎头长耙");
        add(ItemsRegistry.FROST_SPLINTER_WEAPON.get(), "霜之碎片");
        add(ItemsRegistry.GREEN_STEM_WEAPON.get(), "绿色枝干");
        add(ItemsRegistry.HEAVEN_WEAPON.get(), "穿刺极乐");
        add(ItemsRegistry.SPORE_WEAPON.get(), "荧光菌孢");
        add(ItemsRegistry.REGRET_WEAPON.get(), "悔恨");
        add(ItemsRegistry.LANTERN_WEAPON.get(), "诱捕幻灯");
        add(ItemsRegistry.LOGGING_WEAPON.get(), "伐木者");
        add(ItemsRegistry.LAMP_WEAPON.get(), "目灯");
        add(ItemsRegistry.SO_CUTE_WEAPON.get(), "超特么可爱！！！");
        add(ItemsRegistry.BEAR_PAWS_WEAPON.get(), "熊熊抱");

        add(ItemsRegistry.GRINDER_MK4_WEAPON.get(), "粉碎机Mk4");
        add(ItemsRegistry.LIFE_FOR_A_DAREDEVIL_WEAPON.get(), "决死之心");
        add(ItemsRegistry.AMITA_WEAPON.get(), "无量");

        //EGO护甲
        add(ItemsRegistry.STANDARD_TRAINING_EGO_SUIT.get(), "教学用E.G.O");
        add(ItemsRegistry.PENITENCE_SUIT.get(), "忏悔");
        add(ItemsRegistry.WING_BEAT_SUIT.get(), "振翅");
        add(ItemsRegistry.RED_EYES_SUIT.get(), "赤瞳");
        add(ItemsRegistry.CHRISTMAS_SUIT.get(), "悲惨圣诞");
        add(ItemsRegistry.SANGUINE_DESIRE_SUIT.get(), "血之渴望");
        add(ItemsRegistry.WRIST_CUTTER_SUIT.get(), "割腕者");
        add(ItemsRegistry.HORN_SUIT.get(), "犄角");
        add(ItemsRegistry.FRAGMENTS_FROM_SOMEWHERE_SUIT.get(), "彼方的裂片");
        add(ItemsRegistry.HARVEST_SUIT.get(), "猎头长耙");
        add(ItemsRegistry.FROST_SPLINTER_SUIT.get(), "霜之碎片");
        add(ItemsRegistry.GREEN_STEM_SUIT.get(), "绿色枝干");
        add(ItemsRegistry.HEAVEN_SUIT.get(), "穿刺极乐");
        add(ItemsRegistry.SPORE_SUIT.get(), "荧光菌孢");
        add(ItemsRegistry.REGRET_SUIT.get(), "悔恨");
        add(ItemsRegistry.LANTERN_SUIT.get(), "诱捕幻灯");
        add(ItemsRegistry.LOGGING_SUIT.get(), "伐木者");
        add(ItemsRegistry.LAMP_SUIT.get(), "目灯");
        add(ItemsRegistry.SO_CUTE_SUIT.get(), "超特么可爱！！！");
        add(ItemsRegistry.BEAR_PAWS_SUIT.get(), "熊熊抱");

        add(ItemsRegistry.GRINDER_MK4_SUIT.get(), "粉碎机Mk4");
        add(ItemsRegistry.LIFE_FOR_A_DAREDEVIL_SUIT.get(), "决死之心");
        add(ItemsRegistry.AMITA_SUIT.get(), "无量");

        //EGO饰品
        add(ItemsRegistry.STANDARD_TRAINING_EGO_GIFT.get(), "教学用E.G.O");
        add(ItemsRegistry.PENITENCE_GIFT.get(), "忏悔");
        add(ItemsRegistry.WING_BEAT_GIFT.get(), "振翅");
        add(ItemsRegistry.RED_EYES_GIFT.get(), "赤瞳");
        add(ItemsRegistry.CHRISTMAS_GIFT.get(), "悲惨圣诞");
        add(ItemsRegistry.DIFFRACTION_GIFT.get(), "虚无衍射体");
        add(ItemsRegistry.SANGUINE_DESIRE_GIFT.get(), "血之渴望");
        add(ItemsRegistry.WRIST_CUTTER_GIFT.get(), "割腕者");
        add(ItemsRegistry.HORN_GIFT.get(), "犄角");
        add(ItemsRegistry.FRAGMENTS_FROM_SOMEWHERE_GIFT.get(), "彼方的裂片");
        add(ItemsRegistry.HARVEST_GIFT.get(), "猎头长耙");
        add(ItemsRegistry.FROST_SPLINTER_GIFT.get(), "霜之碎片");
        add(ItemsRegistry.GREEN_STEM_GIFT.get(), "绿色枝干");
        add(ItemsRegistry.HEAVEN_GIFT.get(), "穿刺极乐");
        add(ItemsRegistry.SPORE_GIFT.get(), "荧光菌孢");
        add(ItemsRegistry.REGRET_GIFT.get(), "悔恨");
        add(ItemsRegistry.LANTERN_GIFT.get(), "诱捕幻灯");
        add(ItemsRegistry.LOGGING_GIFT.get(), "伐木者");
        add(ItemsRegistry.LAMP_GIFT.get(), "目灯");
        add(ItemsRegistry.SO_CUTE_GIFT.get(), "超特么可爱！！！");
        add(ItemsRegistry.BEAR_PAWS_GIFT.get(), "熊熊抱");

        add(ItemsRegistry.GRINDER_MK4_GIFT.get(), "粉碎机Mk4");
        add(ItemsRegistry.AMITA_GIFT.get(), "无量");

        add(ItemsRegistry.BLESS_GIFT.get(), "祝福");

        //自定义
        //血肉契约——苏玖
        add(ItemsRegistry.FLESH_BOUND_WEAPON.get(), "血肉契约");
        add(ItemsRegistry.FLESH_BOUND_SUIT.get(), "血肉契约");
        add(ItemsRegistry.FLESH_BOUND_GIFT.get(), "血肉契约");

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
        add(MobEffectsRegistry.VULNERABLE_RED.get(), "物理易伤");
        add(MobEffectsRegistry.VULNERABLE_WHITE.get(), "精神易伤");
        add(MobEffectsRegistry.VULNERABLE_BLACK.get(), "侵蚀易伤");
        add(MobEffectsRegistry.VULNERABLE_PALE.get(), "灵魂易伤");
        //声音名
        add(SoundEventsRegistry.SWALLOW_WATER.get().getLocation().toLanguageKey(), "咽水");
        add(SoundEventsRegistry.CHANGE_EQUIPMENT.get().getLocation().toLanguageKey(), "变换装备");
        add(SoundEventsRegistry.SWITCH_WEAPON.get().getLocation().toLanguageKey(), "切换武器");

        //按键名
        add(KeyInit.KEY_CATEGORIES_LOBECORP, "脑叶公司");
        add(KeyInit.KEY_TOGGLE_EQUIPMENT.get().getName(), "开启/关闭 装备");
        add(KeyInit.KEY_SWITCH_WEAPON.get().getName(), "切换武器");

        //武器模板
        add(EGOWeaponTemplate.MACE.getTranslationKey(), "棁");
        add(EGOWeaponTemplate.AXE.getTranslationKey(), "斧");
        add(EGOWeaponTemplate.KNIFE.getTranslationKey(), "刀");
        add(EGOWeaponTemplate.SPEAR.getTranslationKey(), "矛");
        add(EGOWeaponTemplate.HAMMER.getTranslationKey(), "锤");
        add(EGOWeaponTemplate.FIST.getTranslationKey(), "拳套");
        add(EGOWeaponTemplate.SPECIAL.getTranslationKey(), "特殊");
        //插槽名
        add(LobeCorpEquipmentSlot.LOBECORP_WEAPON.getSlotModifiersName(), "在武器位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_SUIT.getSlotModifiersName(), "在护甲位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_BADGE.getSlotModifiersName(), "在袖标位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_TOOL.getSlotModifiersName(), "在工具位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_HAT.getSlotModifiersName(), "在头饰位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_HEAD.getSlotModifiersName(), "在头部位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_OCCIPUT.getSlotModifiersName(), "在后脑位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_EYE.getSlotModifiersName(), "在眼部位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_FACE.getSlotModifiersName(), "在面部位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_CHEEK.getSlotModifiersName(), "在脸颊位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_MASK.getSlotModifiersName(), "在面具位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_MOUTH.getSlotModifiersName(), "在口部位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_NECK.getSlotModifiersName(), "在颈部位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_CHEST.getSlotModifiersName(), "在胸部位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_HAND.getSlotModifiersName(), "在手部位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_GLOVE.getSlotModifiersName(), "在手套位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_RIGHTBACK.getSlotModifiersName(), "在右背位时：");
        add(LobeCorpEquipmentSlot.LOBECORP_LEFTBACK.getSlotModifiersName(), "在左背位时：");

        //脑叶公司
        add(MiscUtils.MOTTO, "直面恐惧，创造未来");
        add(MiscUtils.CONFIRM, "确认");
        add(MiscUtils.INVALID_VALUE, "输入无效，请检查输入值。");
        add(MiscUtils.ENTERED_COMBAT, "已进入战斗状态。");
        add(MiscUtils.LEFT_COMBAT, "已脱离战斗状态。");
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
        //部门名
        add(TeamBadge.teamBadgeString(TeamBadge.LobeCorpTeam.CONTROL_TEAM.getTeamName()), "[控制部]");
        add(TeamBadge.teamBadgeString(TeamBadge.LobeCorpTeam.INFORMATION_TEAM.getTeamName()), "[情报部]");
        add(TeamBadge.teamBadgeString(TeamBadge.LobeCorpTeam.SECURITY_TEAM.getTeamName()), "[安保部]");
        add(TeamBadge.teamBadgeString(TeamBadge.LobeCorpTeam.TRAINING_TEAM.getTeamName()), "[培训部]");
        add(TeamBadge.teamBadgeString(TeamBadge.LobeCorpTeam.CENTRAL_COMMAND_TEAM.getTeamName()), "[中央本部]");
        add(TeamBadge.teamBadgeString(TeamBadge.LobeCorpTeam.WELFARE_TEAM.getTeamName()), "[福利部]");
        add(TeamBadge.teamBadgeString(TeamBadge.LobeCorpTeam.DISCIPLINARY_TEAM.getTeamName()), "[惩戒部]");
        add(TeamBadge.teamBadgeString(TeamBadge.LobeCorpTeam.RECORD_TEAM.getTeamName()), "[记录部]");
        add(TeamBadge.teamBadgeString(TeamBadge.LobeCorpTeam.EXTRACTION_TEAM.getTeamName()), "[研发部]");
        add(TeamBadge.teamBadgeString(TeamBadge.LobeCorpTeam.ARCHITECTURE_TEAM.getTeamName()), "[构筑部]");

        //技能
        //忏悔
        add(PenitenceWeapon.WEAPON_PENITENCE_1, "如果持有者的正义等级高于2级，每次攻击都有5%的概率恢复10点精神值");
        add(PenitenceSuit.SUIT_PENITENCE_1, "当穿戴者受到物理或侵蚀伤害时，有5%的概率恢复10点精神值");
        add(PenitenceGift.GIFT_PENITENCE_1, "对异想体“一罪与百善”的工作成率提高10%");
        //赤瞳
        add(RedEyesWeapon.WEAPON_RED_EYES_1, "如果持有者的勇气等级高于2级，进入战斗后会提高2.5点移动速率");
        add(RedEyesSuit.SUIT_RED_EYES_1, "如果穿戴者的勇气等级高于2级，进入战斗后会提高2.5点移动速率");
        //彼方的裂片
        add(FragmentsFromSomewhereWeapon.WEAPON_FRAGMENTS_FROM_SOMEWHERE_1, "如果持有者的谨慎等级低于5级，进入战斗后有10%的概率提升40%的最大精神值。效果持续30秒");
        //血之渴望
        add(SanguineDesireWeapon.WEAPON_SANGUINE_DESIRE_1, "如果持有者的自律等级低于3级则会提高攻击力。但代价是每次攻击都会丧失等同于最大精神值4%的精神值");
        add(SanguineDesireGift.GIFT_SANGUINE_DESIRE_1, "持有“红舞鞋”E.G.O武器时，降低10点工作成率和工作速率，提高10点攻击速率");
        //霜之碎片
        add(FrostSplinterWeapon.WEAPON_FROST_SPLINTER_1, "被这支武器刺中的目标会减少30%的移动速度，持续3秒");
        //绿色枝干
        add(GreenStemGift.GIFT_GREEN_STEM_1, "持有“绿色枝干”时，提高5点攻击力");
        //荧光菌孢
        add(SporeWeapon.WEAPON_SPORE_1, "持有者攻击时有25%的概率为目标添加一个易伤效果，使其受到的精神伤害加深");
        //目灯
        add(LampWeapon.WEAPON_LAMP_1, "攻击时有25%的概率给目标添加一个易伤效果，使其受到的侵蚀伤害加深");
        //熊熊抱
        add(BearPawsGift.GIFT_BEAR_PAWS_1, "沟通工作的成功率提高3%");
        //粉碎机Mk4
        add(GrinderMk4Gift.GIFT_GRINDER_MK4_1, "洞察工作的成功率提高3%");

        //自定义
        //血肉契约——苏玖
        add(FleshBoundSuit.SUIT_FLESH_BOUND_1, "穿戴者持有“血肉契约”武器，最大生命值+10");
    }
}
