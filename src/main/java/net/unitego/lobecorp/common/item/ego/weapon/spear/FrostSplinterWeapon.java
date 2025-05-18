package net.unitego.lobecorp.common.item.ego.weapon.spear;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.item.ego.EGOWeaponItem;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponProfile;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.registry.DamageTypesRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FrostSplinterWeapon extends EGOWeaponItem {
    public static final String WEAPON_FROST_SPLINTER_1 = egoSkillString("weapon.frost_splinter_1");

    public FrostSplinterWeapon() {
        super(new Properties(), List.of(WEAPON_FROST_SPLINTER_1),
                EGORank.HE,new EGOWeaponProfile(EGOWeaponTemplate.SPEAR), List.of(DamageTypesRegistry.WHITE), 8.0f, StaffManager.EquipRequire.NONE);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (!(attacker instanceof Player player)) return false;
        if (player.getMainHandItem().getItem() instanceof FrostSplinterWeapon) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
            return true;
        }
        return false;
    }
}
