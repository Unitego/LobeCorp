package net.unitego.lobecorp.common.item.ego.weapon.spear;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.item.ego.EGOWeaponItem;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponProfile;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.registry.DamageTypesRegistry;
import net.unitego.lobecorp.registry.MobEffectsRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SporeWeapon extends EGOWeaponItem {
    public static final String WEAPON_SPORE_1 = egoSkillString("weapon.spore_1");

    public SporeWeapon() {
        super(new Properties(), List.of(WEAPON_SPORE_1),
                EGORank.WAW, new EGOWeaponProfile(EGOWeaponTemplate.SPEAR), List.of(DamageTypesRegistry.WHITE), 11.0f,
                StaffManager.EquipRequire.builder().staff(StaffManager.StaffRank.II).temperance(StaffManager.StaffRank.II).build());
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (!(attacker instanceof Player player)) return false;
        if (!(player.getMainHandItem().getItem() instanceof SporeWeapon)) return false;
        if (MiscUtils.roll(player, 0.25f)) {
            target.addEffect(new MobEffectInstance(MobEffectsRegistry.VULNERABLE_WHITE, 60));
            return true;
        }
        return false;
    }
}
