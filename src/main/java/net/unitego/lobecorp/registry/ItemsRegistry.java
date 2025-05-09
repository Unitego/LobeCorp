package net.unitego.lobecorp.registry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.item.LobeCorpLogo;
import net.unitego.lobecorp.common.item.ego.suit.EGOSuitItem;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponItem;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
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
                    List.of(DamageTypesRegistry.RED, DamageTypesRegistry.WHITE, DamageTypesRegistry.BLACK, DamageTypesRegistry.PALE), 0.0f, StaffManager.EquipRequire.NONE));//代码镇暴棍
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

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
