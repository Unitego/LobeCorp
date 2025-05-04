package net.unitego.lobecorp.client.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.StaffData;
import net.unitego.lobecorp.common.menu.EquipmentMenu;
import net.unitego.lobecorp.common.registry.ModAttributes;
import net.unitego.lobecorp.common.registry.ModKeyMappings;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class EquipmentScreen extends EffectRenderingInventoryScreen<EquipmentMenu> {
    public static final ResourceLocation EQUIPMENT_BACKGROUND = LobeCorp.rl("textures/gui/container/equipment_background.png");

    public EquipmentScreen(EquipmentMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int x = titleLabelX + 72;
        int y = titleLabelY + 60;
        StaffData staffData = ((DataAccess) Objects.requireNonNull(Objects.requireNonNull(minecraft).player)).lobeCorp$getStaffData();
        //品质
        guiGraphics.drawCenteredString(minecraft.font, Component.translatable(StaffData.STAFF_RANK).append(" " +
                staffData.getStaffRank().getRank()).withStyle(ChatFormatting.GOLD), titleLabelX + 120, titleLabelY, -1);
        guiGraphics.drawString(minecraft.font, Component.translatable(StaffData.STAFF_FORTITUDE).append(" " +
                staffData.getFortitudeRank().getRank()).withStyle(ChatFormatting.DARK_RED), x, y - 50, -1);
        guiGraphics.drawString(minecraft.font, Component.translatable(StaffData.STAFF_PRUDENCE).append(" " +
                staffData.getPrudenceRank().getRank()).withStyle(ChatFormatting.GRAY), x, y - 36, -1);
        guiGraphics.drawString(minecraft.font, Component.translatable(StaffData.STAFF_TEMPERANCE).append(" " +
                staffData.getTemperanceRank().getRank()).withStyle(ChatFormatting.DARK_PURPLE), x, y - 22, -1);
        guiGraphics.drawString(minecraft.font, Component.translatable(StaffData.STAFF_JUSTICE).append(" " +
                staffData.getJusticeRank().getRank()).withStyle(ChatFormatting.DARK_AQUA), x, y - 4, -1);
        //属性
        guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
        guiGraphics.drawString(minecraft.font, Component.literal("●").append(Component.translatable(Attributes.MAX_HEALTH.value().getDescriptionId()))
                .append(" " + staffData.getMaxHealth()), (x + 4) * 2, (y - 50 + 18) * 2 - 18, -1, false);
        guiGraphics.drawString(minecraft.font, Component.literal("●").append(Component.translatable(ModAttributes.MAX_SANITY.value().getDescriptionId()))
                .append(" " + staffData.getMaxSanity()), (x + 4) * 2, (y - 36 + 18) * 2 - 18, -1, false);
        guiGraphics.drawString(minecraft.font, Component.literal("●").append(Component.translatable(ModAttributes.WORK_SUCCESS.value().getDescriptionId()))
                .append(" " + staffData.getWorkSuccess()), (x + 4) * 2, (y - 22 + 18) * 2 - 18, -1, false);
        guiGraphics.drawString(minecraft.font, Component.literal("●").append(Component.translatable(ModAttributes.WORK_VELOCITY.value().getDescriptionId()))
                .append(" " + staffData.getWorkVelocity()), (x + 4) * 2, (y - 22 + 18) * 2 - 9, -1, false);
        guiGraphics.drawString(minecraft.font, Component.literal("●").append(Component.translatable(ModAttributes.ATTACK_VELOCITY.value().getDescriptionId()))
                .append(" " + staffData.getAttackVelocity()), (x + 4) * 2, (y - 4 + 18) * 2 - 18, -1, false);
        guiGraphics.drawString(minecraft.font, Component.literal("●").append(Component.translatable(ModAttributes.MOVE_VELOCITY.value().getDescriptionId()))
                .append(" " + staffData.getMoveVelocity()), (x + 4) * 2, (y - 4 + 18) * 2 - 9, -1, false);
        guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = leftPos;
        int y = topPos;
        guiGraphics.blit(EQUIPMENT_BACKGROUND, x, y, 0, 0, 256, 256);
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, x - 8, y + 8, x + 75, y + 78,
                30, 0.0625F, mouseX, mouseY, Objects.requireNonNull(Objects.requireNonNull(minecraft).player));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (ModKeyMappings.TOGGLE_EQUIPMENT.get().matches(keyCode, scanCode)) {
            onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
