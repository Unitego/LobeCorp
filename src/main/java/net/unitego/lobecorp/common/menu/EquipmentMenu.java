package net.unitego.lobecorp.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.registry.ModMenus;
import org.jetbrains.annotations.NotNull;

public class EquipmentMenu extends AbstractContainerMenu {
    public EquipmentMenu(int containerId, Inventory playerInventory) {
        super(ModMenus.EQUIPMENT_MENU.get(), containerId);
        //三排背包和一排快捷栏
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(playerInventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack original = slot.getItem();
        ItemStack copy = original.copy();
        if (index < 27) {
            if (!moveItemStackTo(original, 27, 36, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < 36) {
            if (!moveItemStackTo(original, 0, 27, false)) {
                return ItemStack.EMPTY;
            }
        }
        if (original.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        return copy;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
}
