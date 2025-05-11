package net.unitego.lobecorp.common.item.ego.suit;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.item.ego.EGOSuitItem;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.registry.DamageTypesRegistry;

import java.util.List;

public class PenitenceSuit extends EGOSuitItem {
    public static final String SUIT_PENITENCE_1 = egoSkillString("suit.penitence_1");

    public PenitenceSuit() {
        super(new Properties(), List.of(SUIT_PENITENCE_1),
                EGORank.ZAYIN, 0.9f, 0.8f, 0.9f, 2.0f, StaffManager.EquipRequire.NONE);
    }

    @Override
    public void onLobeCorpTick(Player player) {
        DamageSource lastDamageSource = player.getLastDamageSource();
        if (lastDamageSource == null) return;
        if (lastDamageSource.is(DamageTypesRegistry.RED) || lastDamageSource.is(DamageTypesRegistry.BLACK)) {
            if (player.hurtTime == player.hurtDuration / 2) {
                if (MiscUtils.roll(player, 0.05f)) {
                    ((ManagerAccess) player).lobeCorp$getSanityManager().cure(10);
                }
            }
        }
    }
}
