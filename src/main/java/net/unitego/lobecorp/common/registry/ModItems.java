package net.unitego.lobecorp.common.registry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.data.StaffData;
import net.unitego.lobecorp.common.item.LobeCorpLogo;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponItem;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.util.EGORank;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(LobeCorp.MOD_ID);

    public static final DeferredItem<LobeCorpLogo> LOBECORP_LOGO = ITEMS.register("lobecorp_logo", LobeCorpLogo::new);
    public static final DeferredItem<Item> BLUE_LEAF = ITEMS.register("blue_leaf",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).fast().build())));

    public static final DeferredItem<EGOWeaponItem> CODE_RIOT_STICK = ModItems.ITEMS.register("code_riot_stick", () ->
            new EGOWeaponItem(new Item.Properties().rarity(Rarity.RARE), List.of(), EGORank.ZAYIN, EGOWeaponTemplate.MACE,
                    List.of(ModDamageTypes.RED, ModDamageTypes.WHITE, ModDamageTypes.BLACK, ModDamageTypes.PALE), 0.0f, StaffData.EquipRequire.NONE));//代码镇暴棍
    public static final DeferredItem<EGOWeaponItem> RED_RIOT_STICK = ModItems.ITEMS.register("red_riot_stick", () ->
            new EGOWeaponItem(new Item.Properties().rarity(Rarity.UNCOMMON), List.of(), EGORank.ZAYIN, EGOWeaponTemplate.MACE,
                    List.of(ModDamageTypes.RED), 4.0f, StaffData.EquipRequire.NONE));//物理镇暴棍
    public static final DeferredItem<EGOWeaponItem> WHITE_RIOT_STICK = ModItems.ITEMS.register("white_riot_stick", () ->
            new EGOWeaponItem(new Item.Properties().rarity(Rarity.UNCOMMON), List.of(), EGORank.ZAYIN, EGOWeaponTemplate.MACE,
                    List.of(ModDamageTypes.WHITE), 4.0f, StaffData.EquipRequire.NONE));//精神镇暴棍
    public static final DeferredItem<EGOWeaponItem> BLACK_RIOT_STICK = ModItems.ITEMS.register("black_riot_stick", () ->
            new EGOWeaponItem(new Item.Properties().rarity(Rarity.UNCOMMON), List.of(), EGORank.ZAYIN, EGOWeaponTemplate.MACE,
                    List.of(ModDamageTypes.BLACK), 4.0f, StaffData.EquipRequire.NONE));//侵蚀镇暴棍
    public static final DeferredItem<EGOWeaponItem> PALE_RIOT_STICK = ModItems.ITEMS.register("pale_riot_stick", () ->
            new EGOWeaponItem(new Item.Properties().rarity(Rarity.UNCOMMON), List.of(), EGORank.ZAYIN, EGOWeaponTemplate.MACE,
                    List.of(ModDamageTypes.PALE), 4.0f, StaffData.EquipRequire.NONE));//灵魂镇暴棍
}
