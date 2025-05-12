package net.unitego.lobecorp.common.item.ego.weapon.axe;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.item.ego.EGOWeaponItem;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.manager.SanityManager;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.common.util.EquipmentModifierUtils;
import net.unitego.lobecorp.registry.DamageTypesRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SanguineDesireWeapon extends EGOWeaponItem {
    public static final String WEAPON_SANGUINE_DESIRE_1 = egoSkillString("weapon.sanguine_desire_1");
    private static final String MODIFIER_ID_1 = "Sanguine Desire Weapon Modifier 1";

    public SanguineDesireWeapon() {
        super(new Properties(), List.of(WEAPON_SANGUINE_DESIRE_1),
                EGORank.HE, EGOWeaponTemplate.AXE, List.of(DamageTypesRegistry.RED), 5.0f,
                StaffManager.EquipRequire.NONE);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (!(attacker instanceof Player player)) return false;
        if (player.isCreative() || player.isSpectator()) return false;
        if (!(player.getMainHandItem().getItem() instanceof SanguineDesireWeapon)) return false;
        if (((ManagerAccess) player).lobeCorp$getStaffManager().getTemperanceRank().getValue() < 3) {
            SanityManager sanityManager = ((ManagerAccess) player).lobeCorp$getSanityManager();
            float amount = sanityManager.getMaxSanity() * 0.04f;
            sanityManager.setSanity(sanityManager.getSanity() - amount);
            return true;
        }
        return false;
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getModifiers(Player player, ItemStack itemStack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        int value = ((ManagerAccess) player).lobeCorp$getStaffManager().getTemperanceRank().getValue();
        float amount = 0.0f;
        if (value == 1) amount = 5.0f;
        else if (value == 2) amount = 3.0f;
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                EquipmentModifierUtils.getStableModifierId(MODIFIER_ID_1), MODIFIER_ID_1,
                amount, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }

    @Override
    public boolean shouldApply(Player player) {
        int value = ((ManagerAccess) player).lobeCorp$getStaffManager().getTemperanceRank().getValue();
        return value == 1 || value == 2;
    }
}
