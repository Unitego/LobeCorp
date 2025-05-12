package net.unitego.lobecorp.network.handler;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.item.ego.EGOWeaponItem;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.network.payload.C2SSwitchWeaponPayload;
import net.unitego.lobecorp.registry.AttachmentTypesRegistry;
import net.unitego.lobecorp.registry.SoundEventsRegistry;

public class C2SSwitchWeaponHandler {
    public static void handle(C2SSwitchWeaponPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            int slot = 0;
            Player player = context.player();
            Inventory inventory = player.getInventory();
            ItemStackHandler handler = player.getData(AttachmentTypesRegistry.LOBECORP_STACK);
            ItemStack mainHandStack = player.getMainHandItem();
            ItemStack egoWeaponStack = handler.getStackInSlot(slot);
            if (mainHandStack.isEmpty() && !egoWeaponStack.isEmpty()) {
                inventory.setItem(inventory.selected, egoWeaponStack.copy());
                handler.setStackInSlot(slot, ItemStack.EMPTY);
                MiscUtils.playSound(player, SoundEventsRegistry.SWITCH_WEAPON.get());
            } else if (!mainHandStack.isEmpty() && egoWeaponStack.isEmpty()) {
                if (mainHandStack.getItem() instanceof EGOWeaponItem) {
                    handler.setStackInSlot(slot, mainHandStack.copy());
                    inventory.setItem(inventory.selected, ItemStack.EMPTY);
                    MiscUtils.playSound(player, SoundEventsRegistry.SWITCH_WEAPON.get());
                }
            } else if (!mainHandStack.isEmpty()) {
                if (mainHandStack.getItem() instanceof EGOWeaponItem) {
                    inventory.setItem(inventory.selected, egoWeaponStack.copy());
                    handler.setStackInSlot(slot, mainHandStack.copy());
                    MiscUtils.playSound(player, SoundEventsRegistry.SWITCH_WEAPON.get());
                }
            }
        });
    }
}
