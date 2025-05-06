package net.unitego.lobecorp.common.menu;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.unitego.lobecorp.client.hud.HUDResource;
import net.unitego.lobecorp.common.access.LobeCorpSlotAccess;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.registry.ModAttachmentTypes;
import net.unitego.lobecorp.common.registry.ModMenus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EquipmentMenu extends AbstractContainerMenu {
    private static final List<SlotFactory> SLOT_FACTORIES = List.of(
            new SlotFactory(LobeCorpEquipmentSlot.LOBECORP_WEAPON, HUDResource.EMPTY_LOBECORP_WEAPON_SPRITE, 0, 62, 62),
            new SlotFactory(LobeCorpEquipmentSlot.LOBECORP_SUIT, HUDResource.EMPTY_LOBECORP_SUIT_SPRITE, 1, 62, 44),
            new SlotFactory(LobeCorpEquipmentSlot.LOBECORP_BADGE, HUDResource.EMPTY_LOBECORP_BADGE_SPRITE, 2, 62, 26),
            new SlotFactory(LobeCorpEquipmentSlot.LOBECORP_TOOL, HUDResource.EMPTY_LOBECORP_TOOL_SPRITE, 3, 62, 8)
    );

    public EquipmentMenu(int containerId, Inventory playerInventory) {
        super(ModMenus.EQUIPMENT_MENU.get(), containerId);
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
        ItemStackHandler handler = playerInventory.player.getData(ModAttachmentTypes.LOBECORP_SLOTS);
        for (SlotFactory factory : SLOT_FACTORIES) {
            addSlot(factory.createSlot(handler));
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

    //脑叶公司插槽工厂
    private record SlotFactory(LobeCorpEquipmentSlot slot, ResourceLocation icon, int index, int x, int y) {
        public Slot createSlot(ItemStackHandler handler) {
            return new SlotItemHandler(handler, index, x, y) {
                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.getItem() instanceof LobeCorpSlotAccess access &&
                            access.getLobeCorpSlot() == slot;
                }

                @Override
                public boolean mayPickup(@NotNull Player playerIn) {
                    return getItem().isEmpty() || playerIn.isCreative() || !EnchantmentHelper.hasBindingCurse(getItem());
                }

                @OnlyIn(Dist.CLIENT)
                @Override
                public @NotNull Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, icon);
                }
            };
        }
    }
}
