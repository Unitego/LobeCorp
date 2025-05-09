package net.unitego.lobecorp.client.gui.hud.element;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.client.gui.GuiResource;
import net.unitego.lobecorp.client.gui.hud.BaseElement;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)//经验
public class ExperienceElement extends BaseElement {
    @Override
    public boolean check() {//没隐藏ui&&能受伤
        return !minecraft.options.hideGui &&
                Objects.requireNonNull(minecraft.gameMode).canHurtPlayer();
    }

    @Override
    public void draw(GuiGraphics guiGraphics, float partialTick, int guiWidth, int guiHeight) {
        int experienceLevel = Objects.requireNonNull(minecraft.player).experienceLevel;//获取玩家经验等级
        int xpNeededForNextLevel = minecraft.player.getXpNeededForNextLevel();//获取玩家升级到下一级所需的总经验值
        int experience = Mth.ceil(xpNeededForNextLevel * minecraft.player.experienceProgress);//获取玩家当前已积累的经验值

        int gap = 1;
        int barHeight = 7;
        int txtHeight = 5;

        int x = 0;
        int y = guiHeight - barHeight;

        double ratio = (double) (guiWidth - gap * 2) / xpNeededForNextLevel;
        String expLevelString = "Lv." + experienceLevel;
        String expRatioString = experience + "/" + xpNeededForNextLevel;
        int expLevelOffset = minecraft.font.width(expLevelString) / 2 + 4;
        int expRatioOffset = minecraft.font.width(expRatioString) / 2 + 4;
        int heightOffset = txtHeight - minecraft.font.lineHeight / 2;

        //渲染经验条背景
        drawRect(guiGraphics, x, y, guiWidth, barHeight, GuiResource.BG1);
        drawRect(guiGraphics, x + gap, y + gap, guiWidth - gap * 2, barHeight - gap * 3, GuiResource.BG2);
        //渲染经验条
        drawRect(guiGraphics, x + gap, y + gap, (float) (experience * ratio), barHeight - gap * 3, GuiResource.EXPERIENCE_BAR);
        //渲染经验等级背景
        drawRect(guiGraphics, x + gap, y - txtHeight, expLevelOffset, txtHeight, GuiResource.BG1);
        //渲染经验值比例背景
        drawRect(guiGraphics, guiWidth - expRatioOffset, y - txtHeight, expRatioOffset, txtHeight, GuiResource.BG1);
        //渲染经验等级字体
        guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
        guiGraphics.drawCenteredString(minecraft.font, expLevelString, (x + gap) * 2 + expLevelOffset, (y - txtHeight) * 2 + heightOffset, GuiResource.EXPERIENCE_BAR);
        guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);
        //渲染经验值比例字体
        guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
        guiGraphics.drawCenteredString(minecraft.font, expRatioString, (guiWidth - expRatioOffset) * 2 + expRatioOffset, (y - txtHeight) * 2 + heightOffset, GuiResource.EXPERIENCE_BAR);
        guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);
    }
}
