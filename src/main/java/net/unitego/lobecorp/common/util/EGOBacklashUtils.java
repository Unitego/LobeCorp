package net.unitego.lobecorp.common.util;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.item.ego.EGOSuitItem;
import net.unitego.lobecorp.common.item.ego.EGOWeaponItem;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.registry.DamageTypesRegistry;

//EGO反噬机制
public class EGOBacklashUtils {
    public static void applyEGOWeapon(Player player, ItemStack stack) {
        if (!(player.isCreative() || player.isSpectator())) {
            if (stack.getItem() instanceof EGOWeaponItem egoWeaponItem) {
                StaffManager.EquipRequire equipRequire = egoWeaponItem.getEquipRequire();
                StaffManager staffManager = ((ManagerAccess) player).lobeCorp$getStaffManager();
                if (equipRequire.isNotSatisfiedBy(staffManager)) {
                    MiscUtils.hiddenEffect(player, MobEffects.CONFUSION, 200, 254);
                    DelayedTaskUtils.runLater(200, () -> {
                        if (!player.isInvulnerable()) {
                            player.hurt(MiscUtils.noKnockBackDamageSource(DamageTypesRegistry.BLACK, player), egoWeaponItem.getEGORank().getValue() * 2);
                        }
                    });
                }
            }
        }
    }

    public static void applyEGOSuit(Player player, ItemStack stack) {
        if (!(player.isCreative() || player.isSpectator())) {
            if (stack.getItem() instanceof EGOSuitItem egoSuitItem) {
                StaffManager.EquipRequire equipRequire = egoSuitItem.getEquipRequire();
                StaffManager staffManager = ((ManagerAccess) player).lobeCorp$getStaffManager();
                if (equipRequire.isNotSatisfiedBy(staffManager)) {
                    MiscUtils.hiddenEffect(player, MobEffects.CONFUSION, 200, 254);
                    DelayedTaskUtils.runLater(200, () -> {
                        if (!player.isInvulnerable()) {
                            player.hurt(MiscUtils.noKnockBackDamageSource(DamageTypesRegistry.BLACK, player), egoSuitItem.getEGORank().getValue() * 2);
                        }
                    });
                }
            }
        }
    }
}
