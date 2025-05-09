package net.unitego.lobecorp.common.menu;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.unitego.lobecorp.client.gui.GuiResource;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.component.LobeCorpSlot;
import net.unitego.lobecorp.registry.AttachmentTypesRegistry;
import net.unitego.lobecorp.registry.MenusRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EquipmentMenu extends AbstractContainerMenu {
    private static final List<LobeCorpSlotFactory> LOBECORP_SLOT_FACTORIES = List.of(
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_WEAPON, GuiResource.EMPTY_LOBECORP_WEAPON_SPRITE, 0, 62, 62),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_SUIT, GuiResource.EMPTY_LOBECORP_SUIT_SPRITE, 1, 62, 44),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_BADGE, GuiResource.EMPTY_LOBECORP_BADGE_SPRITE, 2, 62, 26),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_TOOL, GuiResource.EMPTY_LOBECORP_TOOL_SPRITE, 3, 62, 8),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_HAT, GuiResource.EMPTY_LOBECORP_HAT_TEXTURE, 4, -24, 11),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_HEAD, GuiResource.EMPTY_LOBECORP_HEAD_TEXTURE, 5, -24, 29),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_OCCIPUT, GuiResource.EMPTY_LOBECORP_OCCIPUT_TEXTURE, 6, -24, 47),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_EYE, GuiResource.EMPTY_LOBECORP_EYE_TEXTURE, 7, -24, 65),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_FACE, GuiResource.EMPTY_LOBECORP_FACE_TEXTURE, 8, -24, 83),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_CHEEK, GuiResource.EMPTY_LOBECORP_CHEEK_TEXTURE, 9, -24, 101),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_MASK, GuiResource.EMPTY_LOBECORP_MASK_TEXTURE, 10, -24, 119),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_MOUTH, GuiResource.EMPTY_LOBECORP_MOUTH_TEXTURE, 11, -24, 137),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_NECK, GuiResource.EMPTY_LOBECORP_NECK_TEXTURE, 12, -24, 155),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_CHEST, GuiResource.EMPTY_LOBECORP_CHEST_TEXTURE, 13, -24, 173),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_HAND, GuiResource.EMPTY_LOBECORP_HAND_TEXTURE, 14, -24, 191),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_GLOVE, GuiResource.EMPTY_LOBECORP_GLOVE_TEXTURE, 15, -24, 209),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_RIGHTBACK, GuiResource.EMPTY_LOBECORP_RIGHTBACK_TEXTURE, 16, -24, 227),
            new LobeCorpSlotFactory(LobeCorpEquipmentSlot.LOBECORP_LEFTBACK, GuiResource.EMPTY_LOBECORP_LEFTBACK_TEXTURE, 17, -24, 245)
    );

    public EquipmentMenu(int containerId, Inventory playerInventory) {
        super(MenusRegistry.EQUIPMENT_MENU.get(), containerId);
        //一排快捷栏和三排背包
        for (int i = 0; i < 9; ++i) {
            addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(playerInventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }
        //脑叶公司插槽
        ItemStackHandler handler = playerInventory.player.getData(AttachmentTypesRegistry.LOBECORP_STACK);
        LOBECORP_SLOT_FACTORIES.forEach(factory -> addSlot(factory.createSlot(handler, playerInventory.player)));
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack original = slot.getItem();
        ItemStack copy = original.copy();
        int hotbarEnd = 9;
        int inventoryEnd = 36;
        if (index >= inventoryEnd) {
            if (!moveItemStackTo(original, 0, inventoryEnd, false)) return ItemStack.EMPTY;
        } else {
            boolean moved = moveItemStackTo(original, inventoryEnd, slots.size(), false);
            if (!moved) {
                if (index < hotbarEnd) {
                    if (!moveItemStackTo(original, hotbarEnd, inventoryEnd, false)) return ItemStack.EMPTY;
                } else {
                    if (!moveItemStackTo(original, 0, hotbarEnd, false)) return ItemStack.EMPTY;
                }
            }
        }
        if (original.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
        else slot.setChanged();
        return copy;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    //脑叶公司插槽工厂
    private record LobeCorpSlotFactory(LobeCorpEquipmentSlot slot, ResourceLocation icon, int index, int x, int y) {
        public LobeCorpSlot createSlot(ItemStackHandler handler, Player player) {
            return new LobeCorpSlot(handler, index, x, y, player, slot, icon);
        }
    }
}
