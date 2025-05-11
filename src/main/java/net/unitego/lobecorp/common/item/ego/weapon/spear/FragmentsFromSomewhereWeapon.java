package net.unitego.lobecorp.common.item.ego.weapon.spear;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.item.ego.EGOWeaponItem;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.CombatStatusUtils;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.common.util.EquipmentModifierUtils;
import net.unitego.lobecorp.registry.AttributesRegistry;
import net.unitego.lobecorp.registry.DamageTypesRegistry;

import java.util.List;

public class FragmentsFromSomewhereWeapon extends EGOWeaponItem {
    public static final String WEAPON_FRAGMENTS_FROM_SOMEWHERE_1 = egoSkillString("weapon.fragments_from_somewhere_1");
    private static final String MODIFIER_ID_1 = "Fragments From Somewhere Weapon Modifier 1";

    public FragmentsFromSomewhereWeapon() {
        super(new Properties(), List.of(WEAPON_FRAGMENTS_FROM_SOMEWHERE_1),
                EGORank.TETH, EGOWeaponTemplate.SPEAR, List.of(DamageTypesRegistry.BLACK), 6.0f, StaffManager.EquipRequire.NONE);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getModifiers(ItemStack itemStack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = ArrayListMultimap.create();
        modifiers.put(AttributesRegistry.MAX_SANITY, new AttributeModifier(
                EquipmentModifierUtils.getStableModifierId(MODIFIER_ID_1), MODIFIER_ID_1,
                0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }

    @Override
    public boolean shouldApply(Player player) {
        if (((ManagerAccess) player).lobeCorp$getStaffManager().getPrudenceRank().getValue() >= 5) return false;
        CombatStatusUtils.registerCombatChance(player, MODIFIER_ID_1, 0.10f, 600);
        return CombatStatusUtils.getCombatChance(player, MODIFIER_ID_1);
    }
}
