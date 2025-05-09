package net.unitego.lobecorp.common.component;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.unitego.lobecorp.common.access.LobeCorpSlotAccess;
import net.unitego.lobecorp.common.item.badge.TeamBadge;
import org.jetbrains.annotations.NotNull;

//脑叶公司插槽
public class LobeCorpSlot extends SlotItemHandler {
    private final Player player;
    private final LobeCorpEquipmentSlot slotType;
    private final ResourceLocation icon;
    private final int index;
    private boolean isActive;//启用状态

    public LobeCorpSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition,
                        Player player, LobeCorpEquipmentSlot slotType, ResourceLocation icon) {
        super(itemHandler, index, xPosition, yPosition);
        this.index = index;
        this.player = player;
        this.slotType = slotType;
        this.icon = icon;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (slotType == LobeCorpEquipmentSlot.LOBECORP_BADGE) {
            if (player instanceof ServerPlayer serverPlayer) {
                TeamBadge.syncTeam(getItem(), serverPlayer);
            }
        }
    }

    @Override
    public boolean isActive() {
        if (index >= 0 && index <= 3) return true;
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof LobeCorpSlotAccess access && access.getLobeCorpSlot() == slotType;
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
}
