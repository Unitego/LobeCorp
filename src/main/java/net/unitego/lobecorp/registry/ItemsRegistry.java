package net.unitego.lobecorp.registry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.item.LobeCorpLogo;
import net.unitego.lobecorp.common.item.badge.team.*;
import net.unitego.lobecorp.common.item.ego.EGOGiftItem;
import net.unitego.lobecorp.common.item.ego.EGOSuitItem;
import net.unitego.lobecorp.common.item.ego.EGOWeaponItem;
import net.unitego.lobecorp.common.item.ego.gift.chest.GreenStemGift;
import net.unitego.lobecorp.common.item.ego.gift.head.PenitenceGift;
import net.unitego.lobecorp.common.item.ego.gift.mouth.SanguineDesireGift;
import net.unitego.lobecorp.common.item.ego.gift.occiput.BlessGift;
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
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.EGORank;

import java.util.List;

public class ItemsRegistry {
    public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(LobeCorp.MOD_ID);

    public static final DeferredItem<LobeCorpLogo> LOBECORP_LOGO = REGISTER.register("lobecorp_logo", LobeCorpLogo::new);
    public static final DeferredItem<Item> BLUE_LEAF = REGISTER.register("blue_leaf", () -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build())));

    public static final DeferredItem<EGOSuitItem> CODE_SUIT = REGISTER.register("code_suit", () ->
            new EGOSuitItem(new Item.Properties().rarity(Rarity.RARE), List.of(), EGORank.ZAYIN,
                    0.0f, 0.0f, 0.0f, 0.0f, StaffManager.EquipRequire.NONE));//代码西装
    public static final DeferredItem<EGOWeaponItem> CODE_RIOT_STICK = REGISTER.register("code_riot_stick", () ->
            new EGOWeaponItem(new Item.Properties().rarity(Rarity.RARE), List.of(), EGORank.ZAYIN, EGOWeaponTemplate.MACE,
                    List.of(DamageTypesRegistry.RED, DamageTypesRegistry.WHITE, DamageTypesRegistry.BLACK, DamageTypesRegistry.PALE), 65535.0f, StaffManager.EquipRequire.NONE));//代码镇暴棍
    public static final DeferredItem<EGOSuitItem> STANDARD_SUIT = REGISTER.register("standard_suit", () ->
            new EGOSuitItem(new Item.Properties().rarity(Rarity.UNCOMMON), List.of(), EGORank.ZAYIN,
                    1.0f, 1.0f, 1.5f, 2.0f, StaffManager.EquipRequire.NONE));//标准西装
    public static final DeferredItem<EGOWeaponItem> RED_RIOT_STICK = REGISTER.register("red_riot_stick", () ->
            new EGOWeaponItem(new Item.Properties().rarity(Rarity.UNCOMMON), List.of(), EGORank.ZAYIN, EGOWeaponTemplate.MACE,
                    List.of(DamageTypesRegistry.RED), 4.0f, StaffManager.EquipRequire.NONE));//物理镇暴棍
    public static final DeferredItem<EGOWeaponItem> WHITE_RIOT_STICK = REGISTER.register("white_riot_stick", () ->
            new EGOWeaponItem(new Item.Properties().rarity(Rarity.UNCOMMON), List.of(), EGORank.ZAYIN, EGOWeaponTemplate.MACE,
                    List.of(DamageTypesRegistry.WHITE), 4.0f, StaffManager.EquipRequire.NONE));//精神镇暴棍
    public static final DeferredItem<EGOWeaponItem> BLACK_RIOT_STICK = REGISTER.register("black_riot_stick", () ->
            new EGOWeaponItem(new Item.Properties().rarity(Rarity.UNCOMMON), List.of(), EGORank.ZAYIN, EGOWeaponTemplate.MACE,
                    List.of(DamageTypesRegistry.BLACK), 4.0f, StaffManager.EquipRequire.NONE));//侵蚀镇暴棍
    public static final DeferredItem<EGOWeaponItem> PALE_RIOT_STICK = REGISTER.register("pale_riot_stick", () ->
            new EGOWeaponItem(new Item.Properties().rarity(Rarity.UNCOMMON), List.of(), EGORank.ZAYIN, EGOWeaponTemplate.MACE,
                    List.of(DamageTypesRegistry.PALE), 4.0f, StaffManager.EquipRequire.NONE));//灵魂镇暴棍

    //EGO武器
    //棁
    public static final DeferredItem<EGOWeaponItem> STANDARD_TRAINING_EGO_WEAPON = REGISTER.register("standard_training_ego_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.TETH, EGOWeaponTemplate.MACE, List.of(DamageTypesRegistry.WHITE), 5.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<PenitenceWeapon> PENITENCE_WEAPON = REGISTER.register("penitence_weapon", PenitenceWeapon::new);
    public static final DeferredItem<EGOWeaponItem> WING_BEAT_WEAPON = REGISTER.register("wing_beat_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.ZAYIN, EGOWeaponTemplate.MACE, List.of(DamageTypesRegistry.RED), 5.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<RedEyesWeapon> RED_EYES_WEAPON = REGISTER.register("red_eyes_weapon", RedEyesWeapon::new);
    public static final DeferredItem<EGOWeaponItem> CHRISTMAS_WEAPON = REGISTER.register("christmas_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.HE, EGOWeaponTemplate.MACE, List.of(DamageTypesRegistry.WHITE), 11.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOWeaponItem> DIFFRACTION_WEAPON = REGISTER.register("diffraction_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.WAW, EGOWeaponTemplate.MACE, List.of(DamageTypesRegistry.WHITE), 15.0f,
                    StaffManager.EquipRequire.builder().prudence(StaffManager.StaffRank.III).build()));
    //斧
    public static final DeferredItem<SanguineDesireWeapon> SANGUINE_DESIRE_WEAPON = REGISTER.register("sanguine_desire_weapon", SanguineDesireWeapon::new);
    //刀
    public static final DeferredItem<EGOWeaponItem> WRIST_CUTTER_WEAPON = REGISTER.register("wrist_cutter_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.TETH, EGOWeaponTemplate.KNIFE, List.of(DamageTypesRegistry.WHITE), 1.5f, StaffManager.EquipRequire.NONE));
    //矛
    public static final DeferredItem<EGOWeaponItem> HORN_WEAPON = REGISTER.register("horn_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.TETH, EGOWeaponTemplate.SPEAR, List.of(DamageTypesRegistry.RED), 6.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOWeaponItem> FRAGMENTS_FROM_SOMEWHERE_WEAPON = REGISTER.register("fragments_from_somewhere_weapon", FragmentsFromSomewhereWeapon::new);
    public static final DeferredItem<EGOWeaponItem> HARVEST_WEAPON = REGISTER.register("harvest_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.HE, EGOWeaponTemplate.SPEAR, List.of(DamageTypesRegistry.BLACK), 8.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<FrostSplinterWeapon> FROST_SPLINTER_WEAPON = REGISTER.register("frost_splinter_weapon", FrostSplinterWeapon::new);
    public static final DeferredItem<EGOWeaponItem> GREEN_STEM_WEAPON = REGISTER.register("green_stem_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.WAW, EGOWeaponTemplate.SPEAR, List.of(DamageTypesRegistry.BLACK), 11.0f,
                    StaffManager.EquipRequire.builder().temperance(StaffManager.StaffRank.III).build()));
    public static final DeferredItem<EGOWeaponItem> HEAVEN_WEAPON = REGISTER.register("heaven_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.WAW, EGOWeaponTemplate.SPEAR, List.of(DamageTypesRegistry.RED), 11.0f,
                    StaffManager.EquipRequire.builder().staff(StaffManager.StaffRank.III).build()));
    public static final DeferredItem<SporeWeapon> SPORE_WEAPON = REGISTER.register("spore_weapon", SporeWeapon::new);
    //锤
    public static final DeferredItem<EGOWeaponItem> REGRET_WEAPON = REGISTER.register("regret_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.TETH, EGOWeaponTemplate.HAMMER, List.of(DamageTypesRegistry.RED), 14.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOWeaponItem> LANTERN_WEAPON = REGISTER.register("lantern_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.TETH, EGOWeaponTemplate.HAMMER, List.of(DamageTypesRegistry.BLACK), 14.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOWeaponItem> LOGGING_WEAPON = REGISTER.register("logging_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.HE, EGOWeaponTemplate.HAMMER, List.of(DamageTypesRegistry.RED), 17.0f,
                    StaffManager.EquipRequire.builder().temperance(StaffManager.StaffRank.II).build()));
    public static final DeferredItem<LampWeapon> LAMP_WEAPON = REGISTER.register("lamp_weapon", LampWeapon::new);
    //EGO护甲
    public static final DeferredItem<EGOSuitItem> STANDARD_TRAINING_EGO_SUIT = REGISTER.register("standard_training_ego_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.TETH, 0.5f, 1.0f, 1.5f, 2.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOSuitItem> PENITENCE_SUIT = REGISTER.register("penitence_suit", PenitenceSuit::new);
    public static final DeferredItem<EGOSuitItem> WING_BEAT_SUIT = REGISTER.register("wing_beat_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.ZAYIN, 0.8f, 0.8f, 1.0f, 2.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<RedEyesSuit> RED_EYES_SUIT = REGISTER.register("red_eyes_suit", RedEyesSuit::new);
    public static final DeferredItem<EGOSuitItem> CHRISTMAS_SUIT = REGISTER.register("christmas_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.HE, 0.8f, 0.6f, 1.3f, 1.5f,
                    StaffManager.EquipRequire.builder().fortitude(StaffManager.StaffRank.II).build()));
    public static final DeferredItem<EGOSuitItem> SANGUINE_DESIRE_SUIT = REGISTER.register("sanguine_desire_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.HE, 0.5f, 1.2f, 0.8f, 1.5f,
                    StaffManager.EquipRequire.builder().temperance(StaffManager.StaffRank.III).build()));
    public static final DeferredItem<EGOSuitItem> WRIST_CUTTER_SUIT = REGISTER.register("wrist_cutter_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.TETH, 1.0f, 0.6f, 1.2f, 2.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOSuitItem> HORN_SUIT = REGISTER.register("horn_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.TETH, 0.8f, 0.8f, 1.5f, 2.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOSuitItem> FRAGMENTS_FROM_SOMEWHERE_SUIT = REGISTER.register("fragments_from_somewhere_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.TETH, 1.0f, 1.2f, 0.6f, 2.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOSuitItem> HARVEST_SUIT = REGISTER.register("harvest_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.HE, 0.6f, 0.8f, 1.3f, 1.5f,
                    StaffManager.EquipRequire.builder().prudence(StaffManager.StaffRank.II).build()));
    public static final DeferredItem<EGOSuitItem> FROST_SPLINTER_SUIT = REGISTER.register("frost_splinter_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.HE, 1.3f, 0.6f, 0.8f, 1.5f,
                    StaffManager.EquipRequire.builder().fortitude(StaffManager.StaffRank.II).build()));
    public static final DeferredItem<EGOSuitItem> GREEN_STEM_SUIT = REGISTER.register("green_stem_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.WAW, 0.8f, 1.2f, 0.6f, 1.5f,
                    StaffManager.EquipRequire.builder().temperance(StaffManager.StaffRank.III).build()));
    public static final DeferredItem<EGOSuitItem> HEAVEN_SUIT = REGISTER.register("heaven_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.WAW, 1.2f, 0.8f, 0.6f, 1.5f,
                    StaffManager.EquipRequire.builder().fortitude(StaffManager.StaffRank.III).build()));
    public static final DeferredItem<EGOSuitItem> SPORE_SUIT = REGISTER.register("spore_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.WAW, 0.8f, 0.6f, 1.2f, 1.5f,
                    StaffManager.EquipRequire.builder().prudence(StaffManager.StaffRank.II).temperance(StaffManager.StaffRank.II).build()));
    public static final DeferredItem<EGOSuitItem> REGRET_SUIT = REGISTER.register("regret_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.TETH, 0.7f, 1.2f, 0.8f, 2.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOSuitItem> LANTERN_SUIT = REGISTER.register("lantern_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.TETH, 0.8f, 0.7f, 1.2f, 2.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOSuitItem> LOGGING_SUIT = REGISTER.register("logging_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.HE, 0.8f, 1.2f, 0.8f, 1.5f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOSuitItem> LAMP_SUIT = REGISTER.register("lamp_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.WAW, 0.8f, 0.7f, 0.4f, 1.5f,
                    StaffManager.EquipRequire.builder().staff(StaffManager.StaffRank.IV).temperance(StaffManager.StaffRank.III).justice(StaffManager.StaffRank.III).build()));
    //EGO饰品
    public static final DeferredItem<EGOGiftItem> STANDARD_TRAINING_EGO_GIFT = REGISTER.register("standard_training_ego_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxHealth(2).maxSanity(2).build(), LobeCorpEquipmentSlot.LOBECORP_HEAD));
    public static final DeferredItem<EGOGiftItem> PENITENCE_GIFT = REGISTER.register("penitence_gift", PenitenceGift::new);
    public static final DeferredItem<EGOGiftItem> WING_BEAT_GIFT = REGISTER.register("wing_beat_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().workSuccess(2).workVelocity(2).build(), LobeCorpEquipmentSlot.LOBECORP_GLOVE));
    public static final DeferredItem<EGOGiftItem> RED_EYES_GIFT = REGISTER.register("red_eyes_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().workSuccess(3).workVelocity(3).build(), LobeCorpEquipmentSlot.LOBECORP_EYE));
    public static final DeferredItem<EGOGiftItem> CHRISTMAS_GIFT = REGISTER.register("christmas_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxHealth(-4).maxSanity(8).build(), LobeCorpEquipmentSlot.LOBECORP_HEAD));
    public static final DeferredItem<EGOGiftItem> DIFFRACTION_GIFT = REGISTER.register("diffraction_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxSanity(6).build(), LobeCorpEquipmentSlot.LOBECORP_HAT));
    public static final DeferredItem<SanguineDesireGift> SANGUINE_DESIRE_GIFT = REGISTER.register("sanguine_desire_gift", SanguineDesireGift::new);
    public static final DeferredItem<EGOGiftItem> WRIST_CUTTER_GIFT = REGISTER.register("wrist_cutter_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().workSuccess(2).workVelocity(2).build(), LobeCorpEquipmentSlot.LOBECORP_GLOVE));
    public static final DeferredItem<EGOGiftItem> HORN_GIFT = REGISTER.register("horn_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxHealth(2).maxSanity(2).build(), LobeCorpEquipmentSlot.LOBECORP_HEAD));
    public static final DeferredItem<EGOGiftItem> FRAGMENTS_FROM_SOMEWHERE_GIFT = REGISTER.register("fragments_from_somewhere_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().workSuccess(2).workVelocity(2).build(), LobeCorpEquipmentSlot.LOBECORP_CHEST));
    public static final DeferredItem<EGOGiftItem> HARVEST_GIFT = REGISTER.register("harvest_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxSanity(4).build(), LobeCorpEquipmentSlot.LOBECORP_NECK));
    public static final DeferredItem<EGOGiftItem> FROST_SPLINTER_GIFT = REGISTER.register("frost_splinter_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxHealth(6).maxSanity(6).build(), LobeCorpEquipmentSlot.LOBECORP_CHEEK));
    public static final DeferredItem<GreenStemGift> GREEN_STEM_GIFT = REGISTER.register("green_stem_gift", GreenStemGift::new);
    public static final DeferredItem<EGOGiftItem> HEAVEN_GIFT = REGISTER.register("heaven_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxHealth(4).workSuccess(2).workVelocity(2).build(), LobeCorpEquipmentSlot.LOBECORP_HAT));
    public static final DeferredItem<EGOGiftItem> SPORE_GIFT = REGISTER.register("spore_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxSanity(5).workSuccess(2).workVelocity(2).build(), LobeCorpEquipmentSlot.LOBECORP_GLOVE));
    public static final DeferredItem<EGOGiftItem> REGRET_GIFT = REGISTER.register("regret_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxHealth(2).maxSanity(2).build(), LobeCorpEquipmentSlot.LOBECORP_MASK));
    public static final DeferredItem<EGOGiftItem> LANTERN_GIFT = REGISTER.register("lantern_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxHealth(5).build(), LobeCorpEquipmentSlot.LOBECORP_MOUTH));
    public static final DeferredItem<EGOGiftItem> LOGGING_GIFT = REGISTER.register("logging_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxHealth(2).workSuccess(2).workVelocity(2).build(), LobeCorpEquipmentSlot.LOBECORP_CHEST));
    public static final DeferredItem<EGOGiftItem> LAMP_GIFT = REGISTER.register("lamp_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxHealth(3).workSuccess(3).workVelocity(3).build(), LobeCorpEquipmentSlot.LOBECORP_HAT));

    public static final DeferredItem<BlessGift> BLESS_GIFT = REGISTER.register("bless_gift", BlessGift::new);

    //自定义
    //血肉契约——苏玖
    public static final DeferredItem<EGOWeaponItem> FLESH_BOUND_WEAPON = REGISTER.register("flesh_bound_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.HE, EGOWeaponTemplate.SPEAR, List.of(DamageTypesRegistry.RED), 8.0f,
                    StaffManager.EquipRequire.builder().staff(StaffManager.StaffRank.II).build()));
    public static final DeferredItem<FleshBoundSuit> FLESH_BOUND_SUIT = REGISTER.register("flesh_bound_suit", FleshBoundSuit::new);
    public static final DeferredItem<EGOGiftItem> FLESH_BOUND_GIFT = REGISTER.register("flesh_bound_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxHealth(-10).workVelocity(-20).attackVelocity(5).moveVelocity(20).build(), LobeCorpEquipmentSlot.LOBECORP_NECK));

    //部门袖标
    public static final DeferredItem<ControlTeamBadge> CONTROL_TEAM_BADGE = REGISTER.register("control_team_badge", ControlTeamBadge::new);
    public static final DeferredItem<InformationTeamBadge> INFORMATION_TEAM_BADGE = REGISTER.register("information_team_badge", InformationTeamBadge::new);
    public static final DeferredItem<SecurityTeamBadge> SECURITY_TEAM_BADGE = REGISTER.register("security_team_badge", SecurityTeamBadge::new);
    public static final DeferredItem<TrainingTeamBadge> TRAINING_TEAM_BADGE = REGISTER.register("training_team_badge", TrainingTeamBadge::new);
    public static final DeferredItem<CentralCommandTeamBadge> CENTRAL_COMMAND_TEAM_BADGE = REGISTER.register("central_command_team_badge", CentralCommandTeamBadge::new);
    public static final DeferredItem<WelfareTeamBadge> WELFARE_TEAM_BADGE = REGISTER.register("welfare_team_badge", WelfareTeamBadge::new);
    public static final DeferredItem<DisciplinaryTeamBadge> DISCIPLINARY_TEAM_BADGE = REGISTER.register("disciplinary_team_badge", DisciplinaryTeamBadge::new);
    public static final DeferredItem<RecordTeamBadge> RECORD_TEAM_BADGE = REGISTER.register("record_team_badge", RecordTeamBadge::new);
    public static final DeferredItem<ExtractionTeamBadge> EXTRACTION_TEAM_BADGE = REGISTER.register("extraction_team_badge", ExtractionTeamBadge::new);
    public static final DeferredItem<ArchitectureTeamBadge> ARCHITECTURE_TEAM_BADGE = REGISTER.register("architecture_team_badge", ArchitectureTeamBadge::new);

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
