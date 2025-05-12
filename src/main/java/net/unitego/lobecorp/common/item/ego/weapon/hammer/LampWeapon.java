package net.unitego.lobecorp.common.item.ego.weapon.hammer;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.item.ego.EGOWeaponItem;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.registry.DamageTypesRegistry;
import net.unitego.lobecorp.registry.MobEffectsRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LampWeapon extends EGOWeaponItem {
    public static final String WEAPON_LAMP_1 = egoSkillString("weapon.lamp_1");

    public LampWeapon() {
        super(new Properties(), List.of(WEAPON_LAMP_1),
                EGORank.WAW, EGOWeaponTemplate.HAMMER, List.of(DamageTypesRegistry.BLACK), 24.0f,
                StaffManager.EquipRequire.builder().fortitude(StaffManager.StaffRank.III).prudence(StaffManager.StaffRank.III).build());
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (!(attacker instanceof Player player)) return false;
        if (!(player.getMainHandItem().getItem() instanceof LampWeapon)) return false;
        if (MiscUtils.roll(player, 0.25f)) {
            target.addEffect(new MobEffectInstance(MobEffectsRegistry.VULNERABLE_BLACK, 60));
            return true;
        }
        return false;
    }
}
