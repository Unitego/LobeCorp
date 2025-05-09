package net.unitego.lobecorp.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.client.gui.GuiResource;
import net.unitego.lobecorp.common.component.LobeCorpSlot;
import net.unitego.lobecorp.common.menu.EquipmentMenu;
import org.jetbrains.annotations.NotNull;

//EGO饰品控件
@OnlyIn(Dist.CLIENT)
public class EGOGiftWidget implements Renderable, GuiEventListener, NarratableEntry {
    private static final int VISIBLE_SLOTS = 7;//可见插槽数
    private static final int TOTAL_SLOTS = 14;//总插槽数
    private static final int SLOT_SIZE = 18;//插槽大小
    private static final int SLIDER_WIDTH = 5;//滑块宽度
    private static final int SLIDER_HEIGHT = 62;//滑块高度
    private static final int SCROLL_BAR_HEIGHT = 124;//滚动条高度
    private boolean open;
    private int parentX;
    private int parentY;
    private EquipmentMenu menu;
    private int sliderPosY;//滑块当前位置
    private boolean isSliding;//是否正在滑动

    public void initialize(int parentX, int parentY, EquipmentMenu menu) {
        this.parentX = parentX;
        this.parentY = parentY;
        this.menu = menu;
    }

    //切换打开关闭状态
    public void toggleOpen() {
        setOpen(!isOpen());
    }

    public boolean isOpen() {
        return open;
    }

    protected void setOpen(boolean opened) {
        open = opened;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (isOpen()) {
            //滑动改变插槽位置
            for (int i = 0; i < TOTAL_SLOTS; i++) {
                updateSlotPos(i);
            }
        }
    }

    protected void renderBg(GuiGraphics guiGraphics) {
        if (isOpen()) {
            //参数设置
            int posX = parentX - 32;
            int posY = parentY + 3;
            int width = 32;
            //渲染插槽背景和滑动条背景
            for (int i = 0; i < VISIBLE_SLOTS; i++) {
                guiGraphics.blit(GuiResource.GIFT_BACKGROUND, posX, posY + 7 + i * SLOT_SIZE, 0, 32, width, SLOT_SIZE);
            }
            //绘制顶部边框
            guiGraphics.blit(GuiResource.GIFT_BACKGROUND, posX, posY, 0, 0, width, 8);
            //绘制底部边框
            guiGraphics.blit(GuiResource.GIFT_BACKGROUND, posX, posY + 7 + VISIBLE_SLOTS * SLOT_SIZE - 1, 0, 16, width, 8);
            //绘制滑块
            guiGraphics.blit(GuiResource.GIFT_BACKGROUND, posX + 25 + 1, posY + 7 + 1 + sliderPosY, 32, 0, SLIDER_WIDTH, SLIDER_HEIGHT);
        }
    }

    //更新滑块位置
    private void updateSliderPos(double mouseY) {
        double offset = 0;
        if (mouseY > 0) offset = mouseY - (parentY + 11);
        else if (mouseY < 0) offset = -mouseY + (parentY + 29);
        sliderPosY = (int) Math.max(0, Math.min(offset, SCROLL_BAR_HEIGHT - SLIDER_HEIGHT));
    }

    //更新插槽位置
    private void updateSlotPos(int i) {
        //此处指的9个快捷栏加27个背包栏以及weapon、suit、badge、tool4个，从0开始索引
        Slot slot = menu.getSlot(40 + i);
        int startSlotIndex = Math.max(0, (sliderPosY * VISIBLE_SLOTS) / (SCROLL_BAR_HEIGHT - SLIDER_HEIGHT));
        if (slot instanceof LobeCorpSlot lobecorpSlot) {
            if (i >= startSlotIndex && i < startSlotIndex + VISIBLE_SLOTS) {
                lobecorpSlot.y = 11 + ((i - startSlotIndex) % VISIBLE_SLOTS) * SLOT_SIZE;
                lobecorpSlot.setActive(true);
            } else {
                lobecorpSlot.setActive(false);
            }
        }
    }

    //EGO饰品界面关闭时禁用饰品插槽
    public void closeSlot() {
        for (int i = 0; i < TOTAL_SLOTS; i++) {
            Slot slot = menu.getSlot(40 + i);
            if (slot instanceof LobeCorpSlot lobecorpSlot) lobecorpSlot.setActive(false);
        }
    }

    //检查是否点击到了边界外
    public boolean hasClickedOutside(double mouseX, double mouseY, int x, int y, int backgroundWidth, int backgroundHeight) {
        if (!isOpen()) {
            return true;
        } else {
            boolean bl1 = mouseX < (double) x || mouseY < (double) y || mouseX >= (double) (x + backgroundWidth) || mouseY >= (double) (y + backgroundHeight);
            boolean bl2 = (double) (x - 32) < mouseX && mouseX < (double) x && (double) (y + 3) < mouseY && mouseY < (double) (y + 143);
            return bl1 && !bl2;
        }
    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public void setFocused(boolean focused) {
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0
                && mouseX >= parentX - 6 && mouseX <= parentX - 6 + SLIDER_WIDTH
                && mouseY >= parentY + 11 && mouseY <= parentY + 11 + SCROLL_BAR_HEIGHT) {
            isSliding = true;
            updateSliderPos(mouseY);
            return true;
        }
        return GuiEventListener.super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isSliding = false;
        return GuiEventListener.super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button == 0 && isSliding) {
            updateSliderPos(mouseY);
            return true;
        }
        return GuiEventListener.super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (mouseX >= parentX - 6 - SLOT_SIZE && mouseX <= parentX - 6 + SLIDER_WIDTH
                && mouseY >= parentY + 11 && mouseY <= parentY + 11 + SCROLL_BAR_HEIGHT) {
            updateSliderPos(scrollY);
            return true;
        }
        return GuiEventListener.super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public @NotNull NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput narrationElementOutput) {
    }
}
