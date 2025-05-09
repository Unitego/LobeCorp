package net.unitego.lobecorp.common.item.ego.weapon.mace;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.item.ego.EGOWeaponItem;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.registry.DamageTypesRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PenitenceWeapon extends EGOWeaponItem {
    public static final String WEAPON_PENITENCE1 = egoSkillString("weapon.penitence1");

    public PenitenceWeapon() {
        super(new Properties(), List.of(WEAPON_PENITENCE1),
                EGORank.ZAYIN, EGOWeaponTemplate.MACE, List.of(DamageTypesRegistry.WHITE), 5.0f, StaffManager.EquipRequire.NONE);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (attacker instanceof ServerPlayer serverPlayer) {
            if (((ManagerAccess) serverPlayer).lobeCorp$getStaffManager().getJusticeRank().getValue() > 2) {
                if (serverPlayer.level().random.nextFloat() <= 0.05f) {
                    ((ManagerAccess) serverPlayer).lobeCorp$getSanityManager().cure(10);
                    return true;
                }
            }
        }
        return false;
    }
}
