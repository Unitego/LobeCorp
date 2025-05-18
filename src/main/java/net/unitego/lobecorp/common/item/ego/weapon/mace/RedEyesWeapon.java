package net.unitego.lobecorp.common.item.ego.weapon.mace;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.item.ego.EGOWeaponItem;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponProfile;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.CombatStatusUtils;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.common.util.EquipmentModifierUtils;
import net.unitego.lobecorp.registry.AttributesRegistry;
import net.unitego.lobecorp.registry.DamageTypesRegistry;

import java.util.List;

public class RedEyesWeapon extends EGOWeaponItem {
    public static final String WEAPON_RED_EYES_1 = egoSkillString("weapon.red_eyes_1");
    public static final String MODIFIER_ID_1 = "Red Eyes Weapon Modifier 1";

    public RedEyesWeapon() {
        super(new Properties(), List.of(WEAPON_RED_EYES_1),
                EGORank.TETH, new EGOWeaponProfile(EGOWeaponTemplate.MACE), List.of(DamageTypesRegistry.RED), 7.0f,
                StaffManager.EquipRequire.NONE);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getModifiers(Player player, ItemStack itemStack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(AttributesRegistry.MOVE_VELOCITY, new AttributeModifier(
                EquipmentModifierUtils.getStableModifierId(MODIFIER_ID_1), MODIFIER_ID_1,
                2.5f, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }

    @Override
    public boolean shouldApply(Player player) {
        return ((ManagerAccess) player).lobeCorp$getStaffManager().getFortitudeRank().getValue() > 2 &&
                CombatStatusUtils.isInCombat(player);
    }
}
