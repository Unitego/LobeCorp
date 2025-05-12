package net.unitego.lobecorp.client.gui.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.client.gui.GuiResource;
import net.unitego.lobecorp.client.init.KeyInit;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.menu.EquipmentMenu;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.registry.AttributesRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class EquipmentScreen extends EffectRenderingInventoryScreen<EquipmentMenu> {
    private EGOGiftWidget egoGiftWidget;

    public EquipmentScreen(EquipmentMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        egoGiftWidget = new EGOGiftWidget();
        egoGiftWidget.initialize(leftPos, topPos, menu);
        addRenderableWidget(egoGiftWidget);
        addRenderableWidget(new ImageButton(
                leftPos + 10, topPos + 10, 10, 10, GuiResource.EGO_GIFT_BUTTON,
                button -> {
                    egoGiftWidget.toggleOpen();
                    if (!egoGiftWidget.isOpen()) egoGiftWidget.closeSlot();
                }
        ));
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        egoGiftWidget.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int x = titleLabelX + 73;
        int y = titleLabelY + 60;
        StaffManager staffManager = ((ManagerAccess) Objects.requireNonNull(Objects.requireNonNull(minecraft).player)).lobeCorp$getStaffManager();
        //品质
        guiGraphics.drawCenteredString(minecraft.font, Component.translatable(MiscUtils.STAFF_RANK).append(" " +
                staffManager.getStaffRank().getRank()).withStyle(style -> style.withColor(ChatFormatting.GOLD).withBold(true)), titleLabelX + 117, titleLabelY, GuiResource.TXT);
        guiGraphics.drawString(minecraft.font, Component.translatable(MiscUtils.STAFF_FORTITUDE).append(" " +
                staffManager.getFortitudeRank().getRank()).withStyle(ChatFormatting.DARK_RED), x, y - 50, GuiResource.TXT);
        guiGraphics.drawString(minecraft.font, Component.translatable(MiscUtils.STAFF_PRUDENCE).append(" " +
                staffManager.getPrudenceRank().getRank()).withStyle(ChatFormatting.WHITE), x, y - 36, GuiResource.TXT);
        guiGraphics.drawString(minecraft.font, Component.translatable(MiscUtils.STAFF_TEMPERANCE).append(" " +
                staffManager.getTemperanceRank().getRank()).withStyle(ChatFormatting.DARK_PURPLE), x, y - 22, GuiResource.TXT);
        guiGraphics.drawString(minecraft.font, Component.translatable(MiscUtils.STAFF_JUSTICE).append(" " +
                staffManager.getJusticeRank().getRank()).withStyle(ChatFormatting.AQUA), x, y - 4, GuiResource.TXT);
        //属性
        guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
        guiGraphics.drawString(minecraft.font, formatAttributeValue(Attributes.MAX_HEALTH.value().getDescriptionId(),
                        staffManager.getMaxHealthBaseValue(), (staffManager.getMaxHealth() - staffManager.getMaxHealthBaseValue())),
                (x + 3) * 2, (y - 50 + 18) * 2 - 18, GuiResource.TXT, false);
        guiGraphics.drawString(minecraft.font, formatAttributeValue(AttributesRegistry.MAX_SANITY.value().getDescriptionId(),
                        staffManager.getMaxSanityBaseValue(), (staffManager.getMaxSanity() - staffManager.getMaxSanityBaseValue())),
                (x + 3) * 2, (y - 36 + 18) * 2 - 18, GuiResource.TXT, false);
        guiGraphics.drawString(minecraft.font, formatAttributeValue(AttributesRegistry.WORK_SUCCESS.value().getDescriptionId(),
                        staffManager.getWorkSuccessBaseValue(), (staffManager.getWorkSuccess() - staffManager.getWorkSuccessBaseValue())),
                (x + 3) * 2, (y - 22 + 18) * 2 - 18, GuiResource.TXT, false);
        guiGraphics.drawString(minecraft.font, formatAttributeValue(AttributesRegistry.WORK_VELOCITY.value().getDescriptionId(),
                        staffManager.getWorkVelocityBaseValue(), (staffManager.getWorkVelocity() - staffManager.getWorkVelocityBaseValue())),
                (x + 3) * 2, (y - 22 + 18) * 2 - 9, GuiResource.TXT, false);
        guiGraphics.drawString(minecraft.font, formatAttributeValue(AttributesRegistry.ATTACK_VELOCITY.value().getDescriptionId(),
                        staffManager.getAttackVelocityBaseValue(), (staffManager.getAttackVelocity() - staffManager.getAttackVelocityBaseValue())),
                (x + 3) * 2, (y - 4 + 18) * 2 - 18, GuiResource.TXT, false);
        guiGraphics.drawString(minecraft.font, formatAttributeValue(AttributesRegistry.MOVE_VELOCITY.value().getDescriptionId(),
                        staffManager.getMoveVelocityBaseValue(), (staffManager.getMoveVelocity() - staffManager.getMoveVelocityBaseValue())),
                (x + 3) * 2, (y - 4 + 18) * 2 - 9, GuiResource.TXT, false);
        guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(GuiResource.EQUIPMENT_BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, leftPos - 8, topPos + 8, leftPos + 75, topPos + 78,
                30, 0.0625F, mouseX, mouseY, Objects.requireNonNull(Objects.requireNonNull(minecraft).player));
        egoGiftWidget.renderBg(guiGraphics);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (KeyInit.KEY_TOGGLE_EQUIPMENT.get().matches(keyCode, scanCode)) {
            onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        boolean bl = mouseX < (double) guiLeft || mouseY < (double) guiTop || mouseX >= (double) (guiLeft + imageWidth) || mouseY >= (double) (guiTop + imageHeight);
        return egoGiftWidget.hasClickedOutside(mouseX, mouseY, leftPos, topPos, imageWidth, imageHeight) && bl;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        egoGiftWidget.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        egoGiftWidget.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        egoGiftWidget.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        egoGiftWidget.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    private Component formatAttributeValue(String name, double base, double bonus) {
        MutableComponent prefix = Component.literal("●")
                .withStyle(style -> style.withColor(ChatFormatting.GOLD).withBold(true));
        MutableComponent nameComponent = Component.translatable(name);
        MutableComponent baseValue = Component.literal(": " + base);
        MutableComponent result = prefix.append(nameComponent).append(baseValue);

        if (bonus > 0) {
            MutableComponent bonusValue = Component.literal("+" + bonus)
                    .withStyle(style -> style.withColor(ChatFormatting.BLUE));
            result.append(bonusValue);
        } else if (bonus < 0) {
            MutableComponent penaltyValue = Component.literal(String.valueOf(bonus))
                    .withStyle(style -> style.withColor(ChatFormatting.RED));
            result.append(penaltyValue);
        }
        return result;
    }

    @Override
    public void resize(@NotNull Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        egoGiftWidget.setOpen(true);
    }
}
