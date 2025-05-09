package net.unitego.lobecorp.common.component;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.unitego.lobecorp.common.access.LobeCorpSlotAccess;
import org.jetbrains.annotations.NotNull;

//脑叶公司插槽
public class LobeCorpSlot extends SlotItemHandler {
    private boolean isActive;//启用状态
    private final LobeCorpEquipmentSlot slot;
    private final ResourceLocation icon;
    private final int index;

    public LobeCorpSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition,
                        LobeCorpEquipmentSlot slot, ResourceLocation icon) {
        super(itemHandler, index, xPosition, yPosition);
        this.index = index;
        this.slot = slot;
        this.icon = icon;
    }

    @Override
    public boolean isActive() {
        if (index >= 0 && index <= 3) return true;
        return isActive;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof LobeCorpSlotAccess access && access.getLobeCorpSlot() == slot;
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

    public void setActive(boolean active) {
        isActive = active;
    }
}
