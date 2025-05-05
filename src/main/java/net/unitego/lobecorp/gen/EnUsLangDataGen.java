package net.unitego.lobecorp.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.registry.*;
import net.unitego.lobecorp.common.util.LobeCorpUtils;

public class EnUsLangDataGen extends LanguageProvider {
    public EnUsLangDataGen(PackOutput output) {
        super(output, LobeCorp.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //属性名
        add(ModAttributes.MAX_SANITY.get().getDescriptionId(), "Max Sanity");
        add(ModAttributes.MAX_ASSIMILATION.get().getDescriptionId(), "Max Assimilation");
        add(ModAttributes.WORK_SUCCESS.get().getDescriptionId(), "Work Success");
        add(ModAttributes.WORK_VELOCITY.get().getDescriptionId(), "Work Velocity");
        add(ModAttributes.ATTACK_VELOCITY.get().getDescriptionId(), "Attack Velocity");
        add(ModAttributes.MOVE_VELOCITY.get().getDescriptionId(), "Move Velocity");
        //效果名
        add(ModMobEffects.ABSENT.get(), "Absent");
        add(ModMobEffects.ASSIMILATION.get(), "Assimilation");
        add(ModMobEffects.HYDRATION.get(), "Hydration");
        add(ModMobEffects.INSANE.get(), "Insane");
        add(ModMobEffects.RESTORATION.get(), "Restoration");
        add(ModMobEffects.SANITY_BOOST.get(), "Sanity Boost");
        add(ModMobEffects.THIRST.get(), "Thirst");
        add(ModMobEffects.INSTANT_SANITY.get(), "Instant Sanity");
        add(ModMobEffects.INSTANT_INJURY.get(), "Instant Injury");
        //物品名
        add(ModItems.LOBE_CORP_LOGO.get(), "Lobe Corp Logo");
        add(ModItems.BLUE_LEAF.get(), "Blue Leaf");
        add(ModItems.CODE_RIOT_STICK.get(), "Code Riot Stick");
        add(ModItems.RED_RIOT_STICK.get(), "Red Riot Stick");
        add(ModItems.WHITE_RIOT_STICK.get(), "White Riot Stick");
        add(ModItems.BLACK_RIOT_STICK.get(), "Black Riot Stick");
        add(ModItems.PALE_RIOT_STICK.get(), "Pale Riot Stick");
        //创造栏名
        add(ModCreativeModeTabs.LOBE_CORP_TAB_NAME, "Lobe Corp");
        //声音名
        add(ModSoundEvents.SWALLOW_WATER_SOUND.get().getLocation().toLanguageKey(), "Swallow Water");
        //按键名
        add(ModKeyMappings.CATEGORIES_LOBE_CORP, "Lobe Corp");
        add(ModKeyMappings.TOGGLE_EQUIPMENT.get().getName(), "Open/Close Equipment");
        //界面名
        add(ModMenus.CONTAINER_LOBECORP_EQUIPMENT, "Equipment");
        //死亡信息
        add(ModDamageTypes.DEATH_ATTACK_DRY, "%1$s dried to death");
        add(ModDamageTypes.DEATH_ATTACK_DRY_PLAYER, "%1$s dried to death whilst fighting %2$s");
        add(ModDamageTypes.DEATH_ATTACK_INSANE, "%1$s died due to insanity");
        add(ModDamageTypes.DEATH_ATTACK_INSANE_PLAYER, "%1$s experienced insanity to death whilst fighting %2$s");
        add(ModDamageTypes.DEATH_ATTACK_INDIRECT_MYSTIC, "%1$s was killed by %2$s using mystic");
        add(ModDamageTypes.DEATH_ATTACK_INDIRECT_MYSTIC_ITEM, "%1$s was killed by %2$s using %3$s");
        add(ModDamageTypes.DEATH_ATTACK_MYSTIC, "%1$s was killed by mystic");
        add(ModDamageTypes.DEATH_ATTACK_MYSTIC_PLAYER, "%1$s was killed by mystic whilst trying to escape %2$s");
        add(ModDamageTypes.DEATH_ATTACK_RED, "%2$s caused %1$s died from Physical Shattering");
        add(ModDamageTypes.DEATH_ATTACK_RED_ITEM, "%2$s using %3$s caused %1$s died from Physical Shattering");
        add(ModDamageTypes.DEATH_ATTACK_WHITE, "%2$s caused %1$s died from Mental Collapse");
        add(ModDamageTypes.DEATH_ATTACK_WHITE_ITEM, "%2$s using %3$s caused %1$s died from Mental Collapse");
        add(ModDamageTypes.DEATH_ATTACK_BLACK, "%2$s caused %1$s died from Erosion Penetration");
        add(ModDamageTypes.DEATH_ATTACK_BLACK_ITEM, "%2$s using %3$s caused %1$s died from Erosion Penetration");
        add(ModDamageTypes.DEATH_ATTACK_PALE, "%2$s caused %1$s died from Soul Erasure");
        add(ModDamageTypes.DEATH_ATTACK_PALE_ITEM, "%2$s using %3$s caused %1$s died from Soul Erasure");
        //武器模板
        add(EGOWeaponTemplate.MACE.getTranslationKey(), "Mace");
        add(EGOWeaponTemplate.AXE.getTranslationKey(), "Axe");
        add(EGOWeaponTemplate.KNIFE.getTranslationKey(), "Knife");
        //脑叶公司
        add(LobeCorpUtils.MOTTO, "Face the Fear, Build the Future");
        add(LobeCorpUtils.CONFIRM, "Confirm");
        add(LobeCorpUtils.INVALID_VALUE, "Invalid Input，please check the values.");
        add(LobeCorpUtils.STAFF_RANK, "Staff Rank");
        add(LobeCorpUtils.STAFF_FORTITUDE, "Fortitude");
        add(LobeCorpUtils.STAFF_PRUDENCE, "Prudence");
        add(LobeCorpUtils.STAFF_TEMPERANCE, "Temperance");
        add(LobeCorpUtils.STAFF_JUSTICE, "Justice");
        add(LobeCorpUtils.HOLD_LEFT_SHIFT_SHOW_SKILL, "Hold Left Shift Show Skill");
        add(LobeCorpUtils.EGO_RANK, "EGO Rank: ");
        add(LobeCorpUtils.WEAPON_TEMPLATE, "Weapon Template: ");
        add(LobeCorpUtils.DAMAGE_TYPE, "Damage Type: ");
        add(LobeCorpUtils.EQUIP_REQUIRE, "Equip Require: ");
        add(LobeCorpUtils.RED, "Red");
        add(LobeCorpUtils.WHITE, "White");
        add(LobeCorpUtils.BLACK, "Black");
        add(LobeCorpUtils.PALE, "Pale");
    }
}
