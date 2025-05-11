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
import net.unitego.lobecorp.common.item.ego.gift.head.PenitenceGift;
import net.unitego.lobecorp.common.item.ego.gift.occiput.BlessGift;
import net.unitego.lobecorp.common.item.ego.suit.PenitenceSuit;
import net.unitego.lobecorp.common.item.ego.suit.RedEyesSuit;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.item.ego.weapon.mace.PenitenceWeapon;
import net.unitego.lobecorp.common.item.ego.weapon.mace.RedEyesWeapon;
import net.unitego.lobecorp.common.item.ego.weapon.spear.FragmentsFromSomewhereWeapon;
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
    //矛
    public static final DeferredItem<EGOWeaponItem> HORN_WEAPON = REGISTER.register("horn_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.TETH, EGOWeaponTemplate.SPEAR, List.of(DamageTypesRegistry.RED), 6.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOWeaponItem> FRAGMENTS_FROM_SOMEWHERE_WEAPON = REGISTER.register("fragments_from_somewhere_weapon", FragmentsFromSomewhereWeapon::new);
    //锤
    public static final DeferredItem<EGOWeaponItem> REGRET_WEAPON = REGISTER.register("regret_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.TETH, EGOWeaponTemplate.HAMMER, List.of(DamageTypesRegistry.RED), 14.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOWeaponItem> LOGGING_WEAPON = REGISTER.register("logging_weapon", () ->
            new EGOWeaponItem(new Item.Properties(), List.of(), EGORank.HE, EGOWeaponTemplate.HAMMER, List.of(DamageTypesRegistry.RED), 17.0f,
                    StaffManager.EquipRequire.builder().temperance(StaffManager.StaffRank.II).build()));
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
    public static final DeferredItem<EGOSuitItem> HORN_SUIT = REGISTER.register("horn_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.TETH, 0.8f, 0.8f, 1.5f, 2.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOSuitItem> FRAGMENTS_FROM_SOMEWHERE_SUIT = REGISTER.register("fragments_from_somewhere_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.TETH, 1.0f, 1.2f, 0.6f, 2.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOSuitItem> REGRET_SUIT = REGISTER.register("regret_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.TETH, 0.7f, 1.2f, 0.8f, 2.0f, StaffManager.EquipRequire.NONE));
    public static final DeferredItem<EGOSuitItem> LOGGING_SUIT = REGISTER.register("logging_suit", () ->
            new EGOSuitItem(new Item.Properties(), List.of(), EGORank.HE, 0.8f, 1.2f, 0.8f, 1.5f, StaffManager.EquipRequire.NONE));
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
    public static final DeferredItem<EGOGiftItem> HORN_GIFT = REGISTER.register("horn_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxHealth(2).maxSanity(2).build(), LobeCorpEquipmentSlot.LOBECORP_HEAD));
    public static final DeferredItem<EGOGiftItem> FRAGMENTS_FROM_SOMEWHERE_GIFT = REGISTER.register("fragments_from_somewhere_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().workSuccess(2).workVelocity(2).build(), LobeCorpEquipmentSlot.LOBECORP_CHEST));
    public static final DeferredItem<EGOGiftItem> REGRET_GIFT = REGISTER.register("regret_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxHealth(2).maxSanity(2).build(), LobeCorpEquipmentSlot.LOBECORP_MASK));
    public static final DeferredItem<EGOGiftItem> LOGGING_GIFT = REGISTER.register("logging_gift", () ->
            new EGOGiftItem(new Item.Properties(), List.of(), EGOGiftItem.EGOGiftBonus.builder().maxHealth(2).workSuccess(2).workVelocity(2).build(), LobeCorpEquipmentSlot.LOBECORP_CHEST));

    public static final DeferredItem<BlessGift> BLESS_GIFT = REGISTER.register("bless_gift", BlessGift::new);

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
