package net.unitego.lobecorp.common.item.ego.suit;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.item.ego.EGOSuitItem;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.registry.DamageTypesRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PenitenceSuit extends EGOSuitItem {
    public static final String SUIT_PENITENCE1 = egoSkillString("suit.penitence1");

    public PenitenceSuit() {
        super(new Properties(), List.of(SUIT_PENITENCE1),
                EGORank.ZAYIN, 0.9f, 0.8f, 0.9f, 2.0f, StaffManager.EquipRequire.NONE);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (MiscUtils.getLobeCorpStack(serverPlayer, LobeCorpEquipmentSlot.LOBECORP_SUIT).getItem() instanceof PenitenceSuit) {
                DamageSource lastDamageSource = serverPlayer.getLastDamageSource();
                if (lastDamageSource != null) {
                    if (lastDamageSource.is(DamageTypesRegistry.RED) || lastDamageSource.is(DamageTypesRegistry.BLACK)) {
                        if (serverPlayer.hurtTime == serverPlayer.invulnerableTime / 2) {
                            if (serverPlayer.level().random.nextFloat() <= 0.05f) {
                                ((ManagerAccess) serverPlayer).lobeCorp$getSanityManager().cure(10);
                            }
                        }
                    }
                }
            }
        }
    }
}
