package net.unitego.lobecorp.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.unitego.lobecorp.common.registry.ModAttachmentTypes;
import net.unitego.lobecorp.common.registry.ModMenus;
import org.jetbrains.annotations.NotNull;

public class EquipmentMenu extends AbstractContainerMenu {
    public EquipmentMenu(int containerId, Inventory playerInventory) {
        super(ModMenus.EQUIPMENT_MENU.get(), containerId);
        //脑叶公司插槽
        ItemStackHandler handler = playerInventory.player.getData(ModAttachmentTypes.LOBECORP_SLOTS);
        for (int i = 0; i < handler.getSlots(); i++) {
            int x = 62;
            int y = 62 - i * 18;
            addSlot(new SlotItemHandler(handler, i, x, y));
        }
        //一排快捷栏和三排背包
        for (int i = 0; i < 9; ++i) {
            addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(playerInventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
}
