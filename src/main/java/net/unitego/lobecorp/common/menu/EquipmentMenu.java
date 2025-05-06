package net.unitego.lobecorp.common.menu;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.registry.ModAttachmentTypes;
import net.unitego.lobecorp.common.registry.ModMenus;
import org.jetbrains.annotations.NotNull;

public class EquipmentMenu extends AbstractContainerMenu {
    public EquipmentMenu(int containerId, Inventory playerInventory) {
        super(ModMenus.EQUIPMENT_MENU.get(), containerId);
        ItemStackHandler handler = playerInventory.player.getData(ModAttachmentTypes.LOBECORP_SLOTS);
        //脑叶公司插槽
        for (int i = 0; i < handler.getSlots(); i++) {
            int x = 62;
            int y = 62 - i * 18;
            addSlot(new SlotItemHandler(handler, i, x, y) {
                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public boolean mayPlace(ItemStack stack) {
                    return super.mayPlace(stack);
                }

                @Override
                public boolean mayPickup(Player playerIn) {
                    return super.mayPickup(playerIn);
                }

                @Override
                public @NotNull Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, LobeCorp.rl("gui/sprites/empty_lobecorp_weapon"));
                }
            });
        }
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
