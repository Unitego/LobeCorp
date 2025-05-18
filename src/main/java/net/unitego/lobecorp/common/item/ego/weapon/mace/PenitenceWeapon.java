package net.unitego.lobecorp.common.item.ego.weapon.mace;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.item.ego.EGOWeaponItem;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponProfile;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.registry.DamageTypesRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PenitenceWeapon extends EGOWeaponItem {
    public static final String WEAPON_PENITENCE_1 = egoSkillString("weapon.penitence_1");

    public PenitenceWeapon() {
        super(new Properties(), List.of(WEAPON_PENITENCE_1),
                EGORank.ZAYIN, new EGOWeaponProfile(EGOWeaponTemplate.MACE), List.of(DamageTypesRegistry.WHITE), 5.0f,
                StaffManager.EquipRequire.NONE);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (!(attacker instanceof Player player)) return false;
        if (!(player.getMainHandItem().getItem() instanceof PenitenceWeapon)) return false;
        if (!(((ManagerAccess) player).lobeCorp$getStaffManager().getJusticeRank().getValue() > 2)) return false;
        if (MiscUtils.roll(player, 0.05f)) {
            ((ManagerAccess) player).lobeCorp$getSanityManager().cure(10);
            return true;
        }
        return false;
    }
}
