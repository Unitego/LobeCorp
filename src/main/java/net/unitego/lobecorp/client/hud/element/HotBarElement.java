package net.unitego.lobecorp.client.hud.element;

import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.client.hud.BaseElement;
import net.unitego.lobecorp.client.hud.HUDResource;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)//快捷栏
public class HotBarElement extends BaseElement {
    @Override
    public boolean check() {
        return !minecraft.options.hideGui;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, float partialTick, int guiWidth, int guiHeight) {
        if (Objects.requireNonNull(minecraft.gameMode).getPlayerMode() == GameType.SPECTATOR) {
            minecraft.gui.getSpectatorGui().renderHotbar(guiGraphics);
        } else if (minecraft.getCameraEntity() instanceof Player player) {
            HumanoidArm mainArm = player.getMainArm();//获取玩家主手
            ItemStack offhandItem = player.getOffhandItem();//获取玩家副手物品

            int size = 18;
            int gap = 2;
            int seed = 1;

            int x = guiWidth / 2 - 91;
            int y = guiHeight - 27;
            int width = (gap + size) * 9 + gap;
            int height = gap + size;

            //渲染快捷栏上部分背景
            drawRect(guiGraphics, x, y, width, height, HUDResource.BG1);
            //创造模式时补全渲染快捷栏下部分背景
            if (player.isCreative()) {
                drawRect(guiGraphics, x, y + height, width, height - size, HUDResource.BG1);
            }
            //渲染九个快捷栏栏位
            for (int slot = 0; slot < 9; slot++) {
                drawRect(guiGraphics, x + gap + slot * (size + gap), y + gap, size, size, HUDResource.BG3);
            }
            //渲染当前快捷栏栏位
            drawRect(guiGraphics, x + gap + player.getInventory().selected * (size + gap), y + gap, size, size, HUDResource.BG4);
            //副手不为空时渲染副手栏
            if (!offhandItem.isEmpty()) {
                if (mainArm == HumanoidArm.RIGHT) {
                    drawRect(guiGraphics, x - gap * 3 - size, y, gap * 2 + size, gap + size, HUDResource.BG1);
                    drawRect(guiGraphics, x - gap * 2 - size, y + gap, size, size, HUDResource.BG4);
                    if (player.isCreative()) {
                        drawRect(guiGraphics, x - gap * 3 - size, y + height, gap * 2 + size, gap, HUDResource.BG1);
                    }
                } else if (mainArm == HumanoidArm.LEFT) {
                    drawRect(guiGraphics, x + width + gap, y, gap * 2 + size, gap + size, HUDResource.BG1);
                    drawRect(guiGraphics, x + width + gap * 2, y + gap, size, size, HUDResource.BG4);
                    if (player.isCreative()) {
                        drawRect(guiGraphics, x + width + gap, y + height, gap * 2 + size, gap, HUDResource.BG1);
                    }
                }
            }

            //渲染主手物品
            for (int slot = 0; slot < 9; slot++) {
                minecraft.gui.renderSlot(guiGraphics, x + gap + 1 + slot * (size + gap), y + gap + 1, partialTick, player, player.getInventory().items.get(slot), seed++);
            }
            //副手不为空时渲染副手物品
            if (!offhandItem.isEmpty()) {
                if (mainArm == HumanoidArm.RIGHT) {
                    seed++;
                    minecraft.gui.renderSlot(guiGraphics, x - gap * 2 - size + 1, y + gap + 1, partialTick, player, offhandItem, seed);
                } else if (mainArm == HumanoidArm.LEFT) {
                    seed++;
                    minecraft.gui.renderSlot(guiGraphics, x + width + gap * 2 + 1, y + gap + 1, partialTick, player, offhandItem, seed);
                }
            }

            //渲染攻击指示器
            if (minecraft.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
                float attackStrengthScale = player.getAttackStrengthScale(0.0F);//获取攻击强度量表
                if (attackStrengthScale < 1.0F) {
                    int p = (int) (attackStrengthScale * 19.0F);
                    if (mainArm == HumanoidArm.RIGHT) {
                        guiGraphics.blitSprite(Gui.HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE, x + width + gap * 2, y + gap - 1, size, size);
                        guiGraphics.blitSprite(Gui.HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE, size, size, 0, size - p, x + width + gap * 2, y + gap + size - p - 1, size, p);
                    } else if (mainArm == HumanoidArm.LEFT) {
                        guiGraphics.blitSprite(Gui.HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE, x - gap * 2 - size, y + gap - 1, size, size);
                        guiGraphics.blitSprite(Gui.HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE, size, size, 0, size - p, x - gap * 2 - size, y + gap + size - p - 1, size, p);
                    }
                }
            }
        }
    }
}
