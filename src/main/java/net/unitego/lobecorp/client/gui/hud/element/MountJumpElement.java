package net.unitego.lobecorp.client.gui.hud.element;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.client.gui.GuiResources;
import net.unitego.lobecorp.client.gui.hud.BaseElement;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)//坐骑跳跃
public class MountJumpElement extends BaseElement {
    @Override
    public boolean check() {//没隐藏ui&&能受伤
        return !minecraft.options.hideGui &&
                Objects.requireNonNull(minecraft.gameMode).canHurtPlayer() &&
                Objects.requireNonNull(minecraft.player).getVehicle() instanceof LivingEntity &&
                Objects.requireNonNull(minecraft.player).getJumpRidingScale() > 0.0f;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, float partialTick, int guiWidth, int guiHeight) {
        float jumpRidingScale = Objects.requireNonNull(minecraft.player).getJumpRidingScale();//获取玩家坐骑跳跃值

        int gap = 2;

        int x = guiWidth / 2 - 72;
        int y = guiHeight - 82;
        int width = 144;
        int height = 10;

        //渲染坐骑跳跃条背景
        drawRect(guiGraphics, x, y, width, height, GuiResources.BG1);
        drawRect(guiGraphics, x + gap, y + gap, width - gap * 2, height - gap * 2, GuiResources.BG2);
        //渲染坐骑跳跃条
        drawRect(guiGraphics, x + gap, y + gap, width * jumpRidingScale - gap * 2, height - gap * 2, GuiResources.MOUNT_JUMP_BAR);
    }
}
